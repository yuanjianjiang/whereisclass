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

		checkArguments(args);

		String[] copyArgs = new String[3];

		if (!args[0].startsWith("-") && args.length == 2) {
			copyArgs[0] = "-s";
			copyArgs[1] = args[0];
			copyArgs[2] = args[1];
		}

		String type = copyArgs[0].substring(1);

		if (!isSingle(type) && !isMutiple(type)) {
			usage();
		}

		String folder = copyArgs[1];
		String className = copyArgs[2];
		WhereIsClass whereisclass = new WhereIsClass();

		if (isSingle(type)) {
			whereisclass.findClassInSingleFolder(folder, className);
		}

		if (isMutiple(type)) {
			whereisclass.searchInMutiplePath(folder, className);
		}
	}

	// ================================================================================================================
	/**
	 * 检查输入参数，不符合的提示使用方法
	 * 
	 * @param args
	 */
	private static void checkArguments(String[] args) {
		if (args[0].startsWith("-") && args.length < 3) {
			usage();
		}

		if (!args[0].startsWith("-") && args.length < 2) {
			usage();
		}

		if (args.length < 0 || args.length > 3) {
			usage();
		}
	}

	// ================================================================================================================
	/**
	 * 判断是否是多目录情况
	 * 
	 * @param type
	 * @return
	 */
	private static boolean isMutiple(String type) {
		boolean isMutiple = false;
		isMutiple = type.equalsIgnoreCase("M");
		if (!isMutiple)
			isMutiple = type.equalsIgnoreCase("Multiple");
		return isMutiple;
	}

	// ===============================================================================================================
	/**
	 * 判断是否是单目录情况
	 * 
	 * @param type
	 * @return
	 */
	private static boolean isSingle(String type) {
		boolean isSingle = false;
		isSingle = type.equalsIgnoreCase("S");
		if (!isSingle)
			isSingle = type.equalsIgnoreCase("Single");
		return isSingle;
	}

	// ===============================================================================================================
	/**
	 * 使用方法提示
	 */
	private static void usage() {
		System.out.println("使用方法: ");
		System.out.print("1、在单个路径下查找: ");
		System.out
				.println("java -jar WhereIsClass.jar -S|-s|Single|single <dir> classname");
		System.out.print("2、在多个路径下查找: ");
		System.out
				.println("java -jar WhereIsClass.jar -M|-m|Multiple|multiple <dir;dirs;dir> classname");
		System.out
				.println("3、无 -参数的为单路径查找 : java -jar WhereIsClass.jar <dir> classname");
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
