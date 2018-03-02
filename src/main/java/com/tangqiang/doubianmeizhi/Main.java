package com.tangqiang.doubianmeizhi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
	private Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		Main m = new Main();
		m.excute();
	}

	private void excute() {
		for (int i = 1; i <= 10000000; i++) {
			System.out.println(i);
			List<String> list = get(i);
			if (list != null && list.size() != 0) {
				for (String s : list) {
					String type = s.substring(s.lastIndexOf("."));
					if (s.indexOf("http://") == -1) {
						s = "http://www.dbmeizi.com" + s;
					}
					// 保存的目录名称，可以修改
					FileHelper.downloadWebFile(s, UUID.randomUUID() + type,"E:/Fun/Doubian");
				}
			}

		}
	}

	private List<String> get(int i) {
		InputStreamReader reader = null;
		BufferedReader in = null;
		try {
			URL url = new URL("http://www.dbmeizi.com/?p=" + i);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "MSIE");
			connection.setConnectTimeout(10000);
			reader = new InputStreamReader(connection.getInputStream(), "utf-8");
			in = new BufferedReader(reader);
			String line = null; // 每行内容
			int lineFlag = 0; // 标记: 判断有没有数据
			StringBuilder content = new StringBuilder();
			while ((line = in.readLine()) != null) {
				content.append(line);
				lineFlag++;
			}
			if (lineFlag >= 1) {
				return TextHelper.getTextImageSrc(content.toString());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
