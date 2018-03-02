package com.tangqiang.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件分割工具
 * 
 * @author rdopc0903
 * 
 */
public class FileSplitUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		int fileCount = 100;
		String filePath = "D:\\temp\\20160804\\MQServerLog.2016-08-04_0908.log";
		new FileSplitUtil().splitFile(filePath, fileCount);
	}

	/**
	 * 分割文件
	 * 
	 * @param filePath
	 * @param fileCount
	 */
	private void splitFile(String filePath, int fileCount) {
		logger.info("FileSplitUtil begin split file, count[" + fileCount + "] file:" + filePath);
		long beginTime = System.currentTimeMillis();
		try {
			File file = new File(filePath);
			long fileSize = file.length();
			long perFileSize = fileSize / fileCount;

			ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 4, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1000));

			for (int i = 1; i <= fileCount; i++) {
				executor.execute(new CopyPartFileThread(file, i, (i - 1) * perFileSize, i != fileCount ? i * perFileSize : fileSize));
			}
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("FileSplitUtil split file used Time:" + (System.currentTimeMillis() - beginTime) + "/ms, count[" + fileCount + "] file:" + filePath);
	}

	/**
	 * 复制文件
	 */
	private class CopyPartFileThread extends Thread {
		private File file;
		private int threadId;
		private long beginIndex;
		private long endIndex;

		public CopyPartFileThread(File file, int threadId, long beginIndex, long endIndex) {
			this.file = file;
			this.threadId = threadId;
			this.beginIndex = beginIndex;
			this.endIndex = endIndex;
		}

		@Override
		public void run() {
			RandomAccessFile randomAccessFile = null;
			FileOutputStream fos = null;
			try {
				long beginTime = System.currentTimeMillis();
				String newFilePath = file.getParent() + File.separatorChar + file.getName() + "." + threadId;
				File newFile = new File(newFilePath);
				logger.info("FileSplitUtil CopyPartFileThread Thrad id[" + threadId + "] copy:" + beginIndex + " <-> " + endIndex + "(" + (endIndex - beginIndex) + ")  New File:" + newFilePath);
				randomAccessFile = new RandomAccessFile(file, "r");
				fos = new FileOutputStream(newFile, true);

				long fileLength = randomAccessFile.length();
				beginIndex = beginIndex > fileLength ? fileLength : beginIndex;
				endIndex = endIndex > fileLength ? fileLength : endIndex;
				randomAccessFile.seek(beginIndex);

				int byteSize = 102400;
				byte[] bytes = new byte[byteSize];
				int byteRead = 0;
				while ((byteRead = randomAccessFile.read(bytes)) != -1) {
					fos.write(bytes, 0, byteRead);
					long currentIndex = randomAccessFile.getFilePointer();

					if (currentIndex + byteSize >= endIndex) {
						byteSize = (int) (endIndex - currentIndex);
						bytes = new byte[byteSize];
					}
					if (currentIndex >= endIndex) {
						break;
					}
				}
				fos.flush();
				fos.close();

				logger.info("FileSplitUtil CopyPartFileThread Thrad id[" + threadId + "] copy file size[" + newFile.length() + "] used time:" + (System.currentTimeMillis() - beginTime) + "/ms");
			} catch (Exception e) {
				logger.error("FileSplitUtil CopyPartFileThread error ", e);
			} finally {
				try {
					randomAccessFile.close();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
