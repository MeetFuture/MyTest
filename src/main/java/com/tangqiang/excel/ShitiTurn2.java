package com.tangqiang.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShitiTurn2 {
	private Logger logger = LoggerFactory.getLogger(ShitiTurn2.class);

	private String fileIn = "D:\\datafile\\ExcelImport\\HeiheCeshiku.xls";
	private String fileTemplate = "D:\\datafile\\ExcelImport\\shiti.xls";

	public static void main(String[] args) {
		ShitiTurn2 turn = new ShitiTurn2();
		List<Map<String, String>> listData = turn.getDataList();
		turn.writeData(listData);
	}

	private void writeData(List<Map<String, String>> listData) {
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			File template = new File(fileTemplate);

			fs = new POIFSFileSystem(new FileInputStream(template));
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet1 = wb.getSheetAt(1);
			HSSFSheet sheet2 = wb.getSheetAt(2);
			HSSFSheet sheet3 = wb.getSheetAt(3);
			for (int i = 0; i < listData.size(); i++) {
				Map<String, String> map = listData.get(i);
				String type = map.get("type");
				if ("1".equals(type)) {
					int rows = sheet1.getLastRowNum() + 1;
					HSSFRow row = sheet1.createRow(rows);
					row.createCell(0, CellType.STRING).setCellValue(map.get("content"));
					row.createCell(1, CellType.STRING).setCellValue(map.get("answerA"));
					row.createCell(2, CellType.STRING).setCellValue(map.get("answerB"));
					row.createCell(3, CellType.STRING).setCellValue(map.get("answerC"));
					row.createCell(4, CellType.STRING).setCellValue(map.get("answerD"));
					row.createCell(5, CellType.STRING).setCellValue(map.get("answerE"));
					row.createCell(6, CellType.STRING).setCellValue(map.get("rightAnswer"));
				} else if ("2".equals(type)) {
					int rows = sheet2.getLastRowNum() + 1;
					HSSFRow row = sheet2.createRow(rows);
					row.createCell(0, CellType.STRING).setCellValue(map.get("content"));
					row.createCell(1, CellType.STRING).setCellValue(map.get("answerA"));
					row.createCell(2, CellType.STRING).setCellValue(map.get("answerB"));
					row.createCell(3, CellType.STRING).setCellValue(map.get("answerC"));
					row.createCell(4, CellType.STRING).setCellValue(map.get("answerD"));
					row.createCell(5, CellType.STRING).setCellValue(map.get("answerE"));
					row.createCell(6, CellType.STRING).setCellValue(map.get("rightAnswer"));
				} else if ("3".equals(type)) {
					int rows = sheet3.getLastRowNum() + 1;
					HSSFRow row = sheet3.createRow(rows);
					row.createCell(0, CellType.STRING).setCellValue(map.get("content"));
					row.createCell(1, CellType.STRING).setCellValue(map.get("rightAnswer"));
				}
			}
			File fileResult = new File(template.getParent() + "\\ShitiHeihe" + System.currentTimeMillis() + ".xls");
			wb.write(fileResult);
		} catch (Exception e) {
			logger.error("writeData error ! ", e);
		} finally {
			try {
				fs.close();
				wb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private List<Map<String, String>> getDataList() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			// 得到Excel工作表对象
			fs = new POIFSFileSystem(new FileInputStream(fileIn));
			// 得到Excel工作簿对象
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getLastRowNum();
			for (int i = 2; i <= rows; i++) {
				// 得到Excel工作表的行
				HSSFRow row = sheet.getRow(i);
				// 得到Excel工作表指定行的单元格
				Integer type = (int) row.getCell(1).getNumericCellValue();
				String contentStr = row.getCell(3).getStringCellValue();
				String rightAnswer = row.getCell(5).getStringCellValue();

				logger.info("Type:" + type + "	Content:" + contentStr + "	RightAnswer:" + rightAnswer);
				Map<String, String> map = new HashMap<String, String>();
				String content = "";
				String answers = "";
				if (1 == type || 2 == type) {
					int index = Math.max(contentStr.indexOf("A、"), Math.max(contentStr.indexOf("A．"), contentStr.indexOf("A.")));
					content = contentStr.substring(0, index);
					content = content.replaceAll("<BR>", "").replaceAll("<br>", "").replaceAll("<br>", "");
					answers = contentStr.substring(index);
					answers = answers.replaceAll("&nbsp;", " ").replaceAll("&nb", "").replaceAll("<[^>]+>", "");
					answers = answers.replaceAll("-&gt;", "->");

					int indexA = Math.max(answers.indexOf("A、"), Math.max(answers.indexOf("A．"), answers.indexOf("A.")));
					int indexB = Math.max(answers.indexOf("B、"), Math.max(answers.indexOf("B．"), answers.indexOf("B.")));
					int indexC = Math.max(answers.indexOf("C、"), Math.max(answers.indexOf("C．"), answers.indexOf("C.")));
					int indexD = Math.max(answers.indexOf("D、"), Math.max(answers.indexOf("D．"), answers.indexOf("D.")));
					int indexE = Math.max(answers.indexOf("E、"), Math.max(answers.indexOf("E．"), answers.indexOf("E.")));
					String answerA = answers.substring(indexA + 2, indexB).trim();
					String answerB = answers.substring(indexB + 2, indexC).trim();
					String answerC = "";
					String answerD = "";
					if (indexC > 0) {
						answerC = answers.substring(indexC + 2, indexD > 0 ? indexD : answers.length()).trim();
					}

					if (indexD > 0) {
						answerD = answers.substring(indexD + 2, indexE > 0 ? indexE : answers.length()).trim();
					}

					map.put("answerA", answerA);
					map.put("answerB", answerB);
					map.put("answerC", answerC);
					map.put("answerD", answerD);
					logger.info("answerA:" + answerA + "	answerB:" + answerB + "	answerC:" + answerC + "	answerD:" + answerD);
				} else if (3 == type) {
					content = contentStr;
					rightAnswer = "B".equals(rightAnswer) ? "错" : "对";
				}

				logger.info("Content:" + content);
				logger.info("Answers:" + answers);

				map.put("rightAnswer", rightAnswer);
				map.put("type", "" + type);
				map.put("content", content);
				map.put("answers", answers);
				result.add(map);
			}

		} catch (Exception e) {
			logger.error("getDataList error ! ", e);
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
				if (fs != null) {
					fs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
