package org.netty.manager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.telnet.TelnetClient;
import org.netty.config.Config;
import org.netty.config.RemoteServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 远程服务器管理器
 * 
 * @author hui.zhao.cfs
 *
 */
public class RemoteServerManager {

	private static Logger logger = LoggerFactory.getLogger(RemoteServerManager.class);

	/** 可用 **/
	public static final int AVAILABLE = 1;
	/** 不可用 **/
	public static final int UNAVAILABLE = 0;

	private static final long CHECK_STATUS_DELAY_TIME = 300L;

	private static Config config;
	private static Random random = new Random();

	static {
		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				try {
					checkStatus();
				} catch (Exception e) {
					logger.error("checkStatus error", e);
				}
			}
		}, CHECK_STATUS_DELAY_TIME, CHECK_STATUS_DELAY_TIME, TimeUnit.SECONDS);
	}

	public static void init(Config config) {
		RemoteServerManager.config = config;
		checkStatus();
	}

	/**
	 * 获取一台可用的远程服务器
	 * 
	 * @return
	 */
	public static RemoteServer getRemoteServer() {
		List<RemoteServer> availableList = new ArrayList<RemoteServer>();
		List<RemoteServer> remoteList = config.getRemoteList();
		for (RemoteServer remoteServer : remoteList) {
			if (remoteServer.getStatus() == AVAILABLE) {
				availableList.add(remoteServer);
			}
		}
		logger.info("available remoteServer size = " + availableList.size());
		if (availableList.size() > 0) {
			return availableList.get(random.nextInt(availableList.size()));
		}
		return remoteList.get(random.nextInt(remoteList.size()));
	}

	/**
	 * 检测远程服务器是否可以连接
	 */
	private static void checkStatus() {
		if (config == null) {
			return;
		}
		List<RemoteServer> remoteList = config.getRemoteList();
		if (remoteList == null) {
			return;
		}
		for (RemoteServer remoteServer : remoteList) {
			if (isConnected(remoteServer)) {
				remoteServer.setStatus(AVAILABLE);
			} else {
				remoteServer.setStatus(UNAVAILABLE);
			}
		}
	}

	/**
	 * telnet 检测是否能连通
	 * 
	 * @param remoteServer
	 * @return
	 */
	private static boolean isConnected(RemoteServer remoteServer) {
		try {
			TelnetClient client = new TelnetClient();
			client.setDefaultTimeout(3000);
			client.setConnectTimeout(3000);
			client.connect(remoteServer.get_ipAddr(), remoteServer.get_port());
			return true;
		} catch (Exception e) {
			logger.warn("remote server: " + remoteServer.toString() + " telnet failed");
		}
		return false;
	}

	private static boolean test(String urlStr) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			/**
			 * public int getResponseCode()throws IOException 从 HTTP 响应消息获取状态码。
			 * 例如，就以下状态行来说： HTTP/1.0 200 OK HTTP/1.0 401 Unauthorized 将分别返回 200
			 * 和 401。 如果无法从响应中识别任何代码（即响应不是有效的 HTTP），则返回 -1。
			 * 
			 * 返回 HTTP 状态码或 -1
			 */
			conn.setConnectTimeout(3000);
			int state = conn.getResponseCode();
			System.out.println(state);
			if (state == 200) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	
	public static boolean isReachable(String remoteInetAddr) {
        boolean reachable = false; 
        try {   
            InetAddress address = InetAddress.getByName(remoteInetAddr); 
            reachable = address.isReachable(3000);  
            } catch (Exception e) {  
            e.printStackTrace();  
            }  
        return reachable;
    }

	public static void main(String[] args) {
		RemoteServer r = new RemoteServer();
		String _ipAddr = "global.ishadowx.net";
//		String _ipAddr = "www.google.com";
		String urlStr = "https://"+_ipAddr;
		r.set_ipAddr(_ipAddr);
		r.set_port(443);
		long t1 = System.currentTimeMillis();
		boolean connected = isConnected(r);
		long t2 = System.currentTimeMillis();
		
		boolean testt = test(urlStr);
		long t3 = System.currentTimeMillis();
		long t4 = t3;
		boolean ir = isReachable(_ipAddr);
		long t5 = System.currentTimeMillis();

		System.out.println(connected);
		System.out.println(testt);
		System.out.println(ir);
		System.out.println(t2 - t1);
		System.out.println(t3 - t2);
		System.out.println(t5 - t4);
	}

}
