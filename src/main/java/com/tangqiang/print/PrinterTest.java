package com.tangqiang.print;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.PrintService;

import org.apache.log4j.Logger;

/**
 * JAVA打印测试
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-28 上午9:15:21
 * 
 * @version 1.0 2014-7-28 tqiang create
 * 
 */
public class PrinterTest {
	private Logger logger = Logger.getLogger(PrinterTest.class);

	public static void main(String[] args) {
		PrinterTest pt = new PrinterTest();
		pt.printString("This is a Test!");
	}

	private void printString(String s) {
		try {
			logger.info("开始打印字符串:" + s);
			PrinterJob job = PrinterJob.getPrinterJob();
			PrintService printService = job.getPrintService();
			logger.info("PrintService Name :" + printService.getName());
			job.setPrintable(new Printable() {
				private final int OVAL_WIDTH = 130;
				private final int OVAL_HEIGHT = 130;

				@Override
				public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
					if (pageIndex > 0) {
						return Printable.NO_SUCH_PAGE;
					}

					int x = (int) pageFormat.getImageableHeight();
					int y = (int) pageFormat.getImageableWidth();
					g.drawString("This is a test!", 10, 10);

					return Printable.PAGE_EXISTS;
				}
			});
			job.setJobName("Testing");

			job.print();
			logger.info("打印完成！");
		} catch (Exception e) {
			logger.error("打印出现异常!", e);
		}
	}

}