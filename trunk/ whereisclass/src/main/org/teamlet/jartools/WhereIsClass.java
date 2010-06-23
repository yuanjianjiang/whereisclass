/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.teamlet.jartools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author <a href="mailto:teamlet@gmail.com">David</a>
 * @since 2007-01-12
 * 
 *        WhereIsClass 用于在指定的单个或多个目录中查找jar中是否存在指定的class文件
 * 
 *        $Id
 */
public class WhereIsClass {

	private String className = null;

	private int resultCounts = 0;

	// =================================================================================================================
	/**
	 * 主函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < 0 || args.length > 2) {
			usage();
		}

		String folders = args[0];
		String className = args[1];
		boolean isSingle = true;
		if (folders.indexOf(";") > 0) {
			isSingle = false;
		}
		WhereIsClass whereisclass = new WhereIsClass();

		if (isSingle) {
			whereisclass.findClassInSingleFolder(folders, className);
		}

		else {
			whereisclass.searchInMutiplePath(folders, className);
		}
	}

	// ===============================================================================================================
	/**
	 * 使用方法提示
	 */
	private static void usage() {
		System.out.println("使用方法: ");
		System.out.println("java -jar WhereIsClass.jar <dir[;dir]> <classname>");
		System.exit(1);
	}

	// ===============================================================================================================
	/**
	 * 在多个路径下查找class
	 * 
	 * @param classToFind
	 */
	public void searchInMutiplePath(String folders, String classToFind) {

		String targetFolders[] = folders.split(";");
		for (int i = 0; i < targetFolders.length; i++) {
			findClassInSingleFolder(targetFolders[i], classToFind);
		}
	}

	// ===============================================================================================================
	/**
	 * 在单个路径下查找class
	 * 
	 * @param baseDir
	 * @param classToFind
	 */
	public void findClassInSingleFolder(String baseDir, String classToFind) {

		System.out.println("\n*** 查找路径: " + baseDir);
		// this.baseDir = baseDir;
		className = classToFind;
		className = className.replaceAll("\\.", "/");

		File rootFolder = new File(baseDir);

		System.out.println("*** 目标类名: " + className);
		System.out.println("\n查找结果：");
		findHelper(rootFolder, 1);
		System.out.println(baseDir + "中共有  " + resultCounts + "  个");
		resultCounts = 0;
	}

	// ===============================================================================================================
	/**
	 * 循环遍历目录下的子目录
	 * 
	 * @param rootFolder
	 * @param level
	 */
	private void findHelper(File rootFolder, int level) {

		File[] subFiles = rootFolder.listFiles();

		for (int i = 0; i < subFiles.length; i++) {
			if (subFiles[i].isFile()) {
				if (subFiles[i].getName().toLowerCase().indexOf(".jar") != -1) {
					searchInJar(subFiles[i].getAbsolutePath());
				}
			} else {
				findHelper(subFiles[i], level + 1);
			}
		}

	}

	// ===============================================================================================================
	/**
	 * 遍历jar内文件名称
	 * 
	 * @param jarFile
	 */
	private void searchInJar(String jarFile) {
		try {
			FileInputStream fInStream = new FileInputStream(jarFile);
			BufferedInputStream bInStream = new BufferedInputStream(fInStream);
			ZipInputStream zInStream = new ZipInputStream(bInStream);
			ZipEntry zipEntry = null;

			while ((zipEntry = zInStream.getNextEntry()) != null) {
				if (zipEntry.isDirectory()) {
					continue;
				}

				if (zipEntry.getName().indexOf(className) != -1) {
					System.out.println(" " + zipEntry.getName() + "\n (在"
							+ jarFile + " 文件中)");
					resultCounts++;
				}
			}
			zInStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ===============================================================================================================
}
