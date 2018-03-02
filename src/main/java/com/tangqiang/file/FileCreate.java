package com.tangqiang.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import com.tangqiang.zip.ZipLoader;

public class FileCreate {

	public static void main(String[] args) throws Exception {
		// new FileCreate().renameFiles();
		long begin = System.currentTimeMillis();
		ZipLoader.zip("D:\\GrgBanking\\FSNFiles\\2017\\06\\27", "D:\\GrgBanking\\FSNBacks\\20170627.zip");
		long end = System.currentTimeMillis();
		System.out.println("Zip File Success,Time:" + (end - begin) + " ms" + (end - begin) / 1000 + " s");
		// String s = ZipLoader.getZipFileContent(new File("D:\\datafile\\feelview\\test\\ZipFile.zip"), "files\\File1.txt");
		// System.out.println(s);
	}

	private void createFile() {
		String filePath = "D:\\datafile\\feelview\\test\\files";
		try {
			File fileDir = new File(filePath);
			fileDir.mkdirs();

			for (int i = 0; i < 100; i++) {
				String fileName = "File" + (i + 1) + ".txt";
				System.out.println("Create FIle:" + fileName);
				File file = new File(filePath + File.separatorChar + fileName);
				FileOutputStream fos = new FileOutputStream(file);
				String encoding = "UTF-8";
				OutputStreamWriter writer = new OutputStreamWriter(fos, encoding);// 考虑到编码格式
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				int lines = 1 + (int) (Math.random() * 100);
				for (int j = 0; j < lines; j++) {
					bufferedWriter.write("lines" + (j + 1) + "\n");
				}
				bufferedWriter.flush();
				bufferedWriter.close();
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void renameFiles() {
		String fileDir = "D:\\GrgBanking\\FSNFiles";
		try {
			boolean run = true;
			while (run) {
				run = false;
				File[] files = new File(fileDir).listFiles();
				int count = files.length;
				for (File file : files) {
					if (file.isFile()) {
						run = true;

						String filePath = file.getParent();
						String fileName = file.getName();
						String[] arr = fileName.split("_|#");
						String fileDate = arr[2];
						String termCode = arr[arr.length - 4];
						String fileNameNew = arr[arr.length - 1];

						String filePathNew = filePath + File.separatorChar + fileDate.substring(0, 4) + File.separatorChar + fileDate.substring(4, 6) + File.separatorChar + fileDate.substring(6, 8)
								+ File.separatorChar + termCode;
						File filePathNewDir = new File(filePathNew);
						filePathNewDir.mkdirs();

						File fileNew = new File(filePathNew + File.separatorChar + fileNameNew);
						boolean bool = file.renameTo(fileNew);
						if (!bool) {
							fileNew = new File(filePathNew + File.separatorChar + (int) (Math.random() * 10) + fileNameNew);
							bool = file.renameTo(fileNew);
						}

						System.out.println(--count + "	filePath:" + filePath + "	fileName:" + fileName + "	bool:" + bool);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
