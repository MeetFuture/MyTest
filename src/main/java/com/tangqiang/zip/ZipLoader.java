package com.tangqiang.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipLoader {

	static int byteLength = 1024;

	public ZipLoader() {
	}

	/**
	 * 压缩文件
	 * 
	 * @param files
	 *            需要压缩的文件/目录
	 * @param outFileStr
	 *            压缩输出地zip文件的名字
	 * @return 压缩是否成功
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean zip(String srcFileStr, String outFileStr) throws FileNotFoundException, IOException {
		File outFile = new File(outFileStr);
		String zipPath = outFile.getParent();

		// 创建压缩文件路径
		File zipFilePath = new File(zipPath);
		if (!zipFilePath.exists()) {
			zipFilePath.mkdirs();
		}
		// 创建压缩文件
		if (!outFile.exists()) {
			outFile.createNewFile();
		}

		File srcFile = new File(srcFileStr);
		File[] fileArr = null;
		String srcPath = "";
		if (srcFile.isFile()) {
			srcPath = srcFile.getParent();
			fileArr = new File[] { srcFile };
		} else if (srcFile.isDirectory()) {
			srcPath = srcFile.getAbsolutePath();
			fileArr = srcFile.listFiles();
		}

		FileOutputStream fos = new FileOutputStream(outFileStr);
		ZipOutputStream zos = new ZipOutputStream(fos);
		// 压缩
		pack(srcPath, fileArr, zos);

		zos.flush();
		zos.close();
		return true;
	}

	/**
	 * 打包文件/目录
	 * 
	 * @param srcPath
	 *            zip文件的绝对路径
	 * @param files
	 *            要打包的文件/目录
	 * @param zos
	 *            已连接到zip文件的zip输入流实例
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void pack(String srcPath, File[] files, ZipOutputStream zos) throws FileNotFoundException, IOException {
		BufferedInputStream bis;
		ZipEntry ze;
		byte[] data = new byte[byteLength];

		for (File f : files) {
			// 递归压缩目录
			if (f.isDirectory()) {
				pack(srcPath, f.listFiles(), zos);
			} else if (f.getName().indexOf(".Ds_Store") != -1) {
				continue;
			} else {
				// 获取被压缩文件相对zip文件的路径
				String subPath = f.getAbsolutePath();
				int index = subPath.indexOf(srcPath);
				if (index != -1) {
					subPath = subPath.substring(srcPath.length() + File.separator.length());
				}
				// 压缩文件
				ze = new ZipEntry(subPath);
				zos.putNextEntry(ze);
				bis = new BufferedInputStream(new FileInputStream(f));
				while (bis.read(data, 0, byteLength) != -1) {
					zos.write(data);
				}
				bis.close();
				zos.closeEntry();
			}
		}
	}

	/**
	 * 解压zip文件
	 * 
	 * @param zipFile
	 *            要解压的zip文件对象
	 * @param unzipFilePath
	 *            解压目的绝对路径
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void unzip(File zipFile, String unzipFilePath) throws IOException {
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
		ZipEntry ze;
		String unzipEntryPath;
		String unzipEntryDirPath;
		int index;
		File unzipEntryDir;
		BufferedOutputStream bos;
		BufferedInputStream bis;
		byte[] data = new byte[byteLength];

		// 创建解压后的文件夹
		File unzipDir = new File(unzipFilePath);
		if (!unzipDir.exists() || !unzipDir.isDirectory()) {
			unzipDir.mkdir();
		}

		// 解压
		while (entries.hasMoreElements()) {
			// 获取下一个解压文件
			ze = (ZipEntry) entries.nextElement();
			unzipEntryPath = unzipFilePath + File.separator + ze.getName();
			index = unzipEntryPath.lastIndexOf(File.separator);
			// 获取解压文件上层目录
			if (index != -1) {
				unzipEntryDirPath = unzipEntryPath.substring(0, index);
			} else {
				unzipEntryDirPath = "";
			}
			// 创建解压文件上层目录
			unzipEntryDir = new File(unzipEntryDirPath);
			if (!unzipEntryDir.exists() || !unzipEntryDir.isDirectory()) {
				unzipEntryDir.mkdir();
			}
			// 写出解压文件
			bos = new BufferedOutputStream(new FileOutputStream(unzipEntryPath));
			bis = new BufferedInputStream(zip.getInputStream(ze));
			while (bis.read(data, 0, byteLength) != -1) {
				bos.write(data);
			}
			bis.close();
			bos.flush();
			bos.close();
		}
		zip.close();
	}

	/**
	 * 读取zip文件中制定文件的内容
	 * 
	 * @param zipFile
	 *            目标zip文件对象
	 * @param readFileName
	 *            目标读取文件名字
	 * @return 文件内容
	 * @throws ZipException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String getZipFileContent(File zipFile, String readFileName) throws ZipException, IOException {
		StringBuilder content = new StringBuilder();
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();

		ZipEntry ze;
		// 枚举zip文件内的文件/
		while (entries.hasMoreElements()) {
			ze = entries.nextElement();
			// 读取目标对象
			if (ze.getName().equals(readFileName)) {
				Scanner scanner = new Scanner(zip.getInputStream(ze));
				while (scanner.hasNextLine()) {
					content.append(scanner.nextLine());
				}
				scanner.close();
				break;
			}
		}
		zip.close();

		return content.toString();
	}

}