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
 *        WhereIsClass 用于查找目录中包含的所有jar是否存在指定的class文件
 * 
 *        $Id
 */
public class WhereIsClass {

	private int resultCounts = 0;

	// =================================================================================================================
	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 2) {
			usage();
		}

		new WhereIsClass(args[0], args[1]);

	}

	public WhereIsClass(String folders, String className) {
		router(folders, className);
	}

	// ===============================================================================================================
	/**
	 * 
	 * @param folders
	 * @param className
	 */
	private void router(String folders, String className) {
		searchInMutiplePath(folders, className);
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
		if (!isValidedFolder(baseDir)) {
			log("\n*** Error:invalidated folder\n");
			usage();
		}
		classToFind = classToFind.replaceAll("\\.", "/");

		File rootFolder = new File(baseDir);

		log("Result：");
		log("=======");
		new FindHandler(classToFind).find(rootFolder, 1);
		log("=======");
		log("Path  : " + baseDir);
		log("Target: " + classToFind);
		log("Counts: " + resultCounts + "\n");
		resultCounts = 0;
	}

	// ===============================================================================================================
	/**
	 * 检查输入的文件夹参数是否有效
	 * 
	 * @param folder
	 * @return
	 */
	private boolean isValidedFolder(String folder) {
		File dir = new File(folder);
		return dir.isDirectory() && dir.exists();
	}

	// ================================================================================================================
	/**
	 * 输出信息
	 * 
	 * @param info
	 */
	protected void log(String info) {
		System.out.println(info);
	}

	// ===============================================================================================================
	/**
	 * 使用方法提示
	 */
	private static void usage() {
		System.out.println("Usage: ");
		System.out.println("java -jar WhereIsClass.jar dir[;dir] className");
		System.exit(1);
	}

	// ===============================================================================================================
	class FindHandler {

		private String className = null;

		public FindHandler(String className) {
			this.className = className;
		}

		// ==========================================================================================================
		/**
		 * 循环遍历目录下的子目录
		 * 
		 * @param rootFolder
		 * @param level
		 */
		public void find(File rootFolder, int level) {

			File[] subFiles = rootFolder.listFiles();

			for (int i = 0; i < subFiles.length; i++) {
				if (subFiles[i].isFile()) {
					if (subFiles[i].getName().toLowerCase().indexOf(".jar") != -1) {
						searchInJar(subFiles[i].getAbsolutePath());
					}
				} else {
					find(subFiles[i], level + 1);
				}
			}

		}

		// ===========================================================================================================
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
						log(zipEntry.getName() + "   ->   (" + jarFile + ")");
						resultCounts++;
					}
				}
				zInStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// ===============================================================================================================
}
