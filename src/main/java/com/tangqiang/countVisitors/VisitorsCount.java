package com.tangqiang.countVisitors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class VisitorsCount {

	public static void main(String[] args) {
		new VisitorsCount().execute();
	}

	private void execute() {
		String filePath = "D:\\Download\\feelview";
		File[] files = new File(filePath).listFiles();
		for (File file : files) {
			try {
				int count = 0;
				Set<String> ips = new HashSet<String>();
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt.indexOf("/feelview/flex/Index.html") >= 0) {
						count++;
						String[] arr = lineTxt.split(" ");
						ips.add(arr[0]);
					}
				}
				read.close();
				String[] fileArr = file.getName().split("\\.");
				System.out.println(fileArr[1] + "	" + count + "	" + ips);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
