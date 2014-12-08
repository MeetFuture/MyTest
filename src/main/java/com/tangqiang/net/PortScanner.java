package com.tangqiang.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.*;

/**
 * 端口扫描程序
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-9-9 下午1:33:12
 * 
 * @version 1.0 2014-9-9 tqiang create
 * 
 */
public class PortScanner {
	private int corePoolSize = 500;
	private int maximumPoolSize = 570;
	private long keepAliveTime = 2000;
	private BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
	private ExecutorService threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, workQueue);

	
	public static void main(String[] args) {
		try {
			PortScanner portScanner = new PortScanner();
			portScanner.scan("10.1.33.82", 1, 10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// portScanner.scan("74.125.143.115",1,10000);
	}
	
	
	public void scan(String ip, int start, int end) throws Exception {
		InetAddress inetAddress = InetAddress.getByName(ip);
		if (!inetAddress.isReachable(5000)) {
			System.err.println(inetAddress.getHostAddress() + " is not reachable.");
			return;
		}

		for (int i = start; i <= end; i++) {

			while (workQueue.size() > maximumPoolSize) {
				Thread.sleep(500);
			}
			threadPoolExecutor.execute(new ScanWorker(inetAddress, i));
		}
		threadPoolExecutor.shutdown();
		while (!threadPoolExecutor.isTerminated()) {
			Thread.sleep(100);
		}
		System.out.println("Done.");
	}

	private class ScanWorker implements Runnable {
		InetAddress inetAddress;
		int port;

		public ScanWorker(InetAddress inetAddress, int port) {
			this.inetAddress = inetAddress;
			this.port = port;
		}

		@Override
		public void run() {
			Socket socket = null;
			try {
				socket = new Socket(inetAddress, port);
				String serviceName = getServiceName(socket.getPort());
				System.out.println("Addr:" + inetAddress.getHostAddress() +":"+socket.getPort()+ "	ServiceName:" + serviceName );
			} catch (Exception e) {
				
			} finally {
				if (socket != null && !socket.isClosed()) {
					try {
						socket.close();
						socket = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		private String getServiceName(int port) {
			String name;
			switch (port) {
			case 21:
				name = "FTP";
				break;
			case 23:
				name = "TELNET";
				break;
			case 25:
				name = "SMTP";
				break;
			case 80:
				name = "HTTP";
				break;
			case 110:
				name = "POP";
				break;
			case 135:
				name = "RPC";
				break;
			case 139:
				name = "netBIOS";
				break;
			case 443:
				name = "HTTPS";
				break;
			case 1433:
				name = "SQL server";
				break;
			case 3389:
				name = "Terminal Service";
				break;
			case 1521:
				name = "Oracle";
				break;
			case 3306:
				name = "MySql";
				break;
			case 8080:
				name = "Tomcat";
				break;
			default:
				name = "Unknown";
			}
			return name;
		}
	}


}