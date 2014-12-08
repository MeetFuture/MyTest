package com.tangqiang.grgbanking.test.ocrmina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * mina 冠字号性能测试
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-8-6 上午9:48:00
 * 
 * @version 1.0 2014-8-6 tqiang create
 * 
 */
public class OcrMinaClientThread extends Thread {
	private Logger logger = Logger.getLogger(OcrMinaClientThread.class);

	private String host = "10.1.33.21";

	private int port = 8181;

	public void run() {
		while (true) {
//			String msg = new OcrMsgBuild().buildOcrMsg();
			String msg = "10008117310100000000920140806110448532011851254313108585806885853900039053201185125431310Xiangqing13865421892VTM1632238:0:CNY:100:0:0:0$VTM1645286:0:CNY:100:1:1:1$MOTO982461:0:CNY:100:2:2:2$FSED1261354:0:CNY:100:3:0:3$AMLI693134:0:CNY:100:0:1:0$CRSE8795132:0:CNY:100:1:2:1$VTM1645238:0:CNY:100:2:0:2$AMLI695134:0:CNY:100:3:1:3$VTM1612238:0:CNY:100:0:2:0$CDMN985445:0:CNY:100:1:0:1$DASL196423:0:CNY:100:2:1:2$MOTO982461:0:CNY:100:3:2:3$VTM1645223:0:CNY:100:0:0:0$JOSN741302:0:CNY:100:1:1:1$MOTO988361:0:CNY:100:2:2:2$CDMN935462:0:CNY:100:3:0:3$CDMN985462:0:CNY:100:0:1:0$A012365989:0:CNY:100:1:2:1$VTM1645286:0:CNY:100:2:0:2$VTM1645286:0:CNY:100:3:1:3$CRSE8995132:0:CNY:100:0:2:0$AMLI695834:0:CNY:100:1:0:1$JOSN741682:0:CNY:100:2:1:2$AMLI698134:0:CNY:100:3:2:3$DASL836423:0:CNY:100:0:0:0$JOSN741682:0:CNY:100:1:1:1$AMLI695834:0:CNY:100:2:2:2$DASL836423:0:CNY:100:3:0:3$DASL196423:0:CNY:100:0:1:0$LMGK232515:0:CNY:100:1:2:1$FSED9351354:0:CNY:100:2:0:2$AMLI693134:0:CNY:100:3:1:3$CDMN935462:0:CNY:100:0:2:0$AMLI698134:0:CNY:100:1:0:1$MOTO918461:0:CNY:100:2:1:2$FGGD9864354:0:CNY:100:3:2:3$A012365989:0:CNY:100:0:0:0$MOTO918461:0:CNY:100:1:1:1$CDMN987562:0:CNY:100:2:2:2$";
			// 创建一个非阻塞的客户端程序
			IoConnector connector = new NioSocketConnector();
			try {
				// 设置链接超时时间
				// connector.setConnectTimeout(300);
				// 添加过滤器
				connector.getFilterChain().addLast("codec",
						new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));
				// 添加业务逻辑处理器类
				connector.setHandler(new OcrMimaClientHandler());
				IoSession session = null;

				logger.debug("开始连接至服务器[" + host + ":" + port + "]");
				ConnectFuture future = connector.connect(new InetSocketAddress(host, port));// 创建连接
				future.awaitUninterruptibly();// 等待连接创建完成
				session = future.getSession();// 获得session
				logger.info("开始发送消息:" + msg);
				session.write(msg);
				logger.info("消息发送完成!");
				
			} catch (Exception e) {
				logger.error("客户端链接异常...", e);
			}
			// session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
			connector.dispose();
		}
	}

}