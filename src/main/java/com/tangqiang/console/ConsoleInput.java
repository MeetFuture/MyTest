package com.tangqiang.console;

import java.io.Console;

public class ConsoleInput {

	public static void main(String[] args) {
		Console console = System.console();
		if (console == null) {
			throw new IllegalStateException("Console is not available!");
		}

		while (true) {
			String username = console.readLine("Username: ");
			char[] password = console.readPassword("Password: ");

			if (username.equals("Chris") && String.valueOf(password).equals("GoHead")) {
				console.printf("Welcome to Java Application %1$s.\n", username);
				// 使用后应立即将数组清空，以减少其在内存中占用的时间，增强安全性
				password = null;
				System.exit(-1);
			} else {
				console.printf("Invalid username or password.\n");
			}
		}
	}

}
