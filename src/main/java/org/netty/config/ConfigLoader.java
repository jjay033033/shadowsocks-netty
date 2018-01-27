package org.netty.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.netty.manager.RemoteServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import priv.lmoon.shadowsupdate.config.ConfigList;
import priv.lmoon.shadowsupdate.config.ConfigListFactory;
import priv.lmoon.shadowsupdate.config.XmlConfig;
import priv.lmoon.shadowsupdate.qrcode.QRcodeUtil;
import priv.lmoon.shadowsupdate.util.ConfListUtil;
import priv.lmoon.shadowsupdate.util.FileUtil;
import priv.lmoon.shadowsupdate.vo.ConfVO;

public class ConfigLoader {
	
	private static Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
	
	private static final String SLEEP_TIME = "sleepTime";

	private static final String LOCAL_PORT = "localPort";
	
	private static String outPath,qrcodePath,jsonFilePathName;
	
	private static List<ConfVO> oldList = null;
	
	private static List<RemoteServer> remoteList = new ArrayList<>();
	
	private static int localPort;
	
	/**
	 * 检查ss账号密码间隔时间（秒）
	 */
	private static long sleepTime;
	
	private ConfigLoader(){
		
	}
	
	/**
	 * 需先初始化值
	 * @param configPath
	 */
	public static void init(String outPath,String qrcodePath,String jsonFilePathName){
		ConfigLoader.outPath = outPath;
		ConfigLoader.qrcodePath = qrcodePath;
		ConfigLoader.jsonFilePathName = jsonFilePathName;
		buildFilePath();
		localPort = Integer.parseInt(XmlConfig.getValue(LOCAL_PORT));
		sleepTime = Long.parseLong(XmlConfig.getValue(SLEEP_TIME));
		start();
	}
	
	private static void start(){
		
		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				try {
					load();
				} catch (Exception e) {
					logger.error("load pac error", e);
				}
			}
		}, 0L, sleepTime, TimeUnit.SECONDS);

	}
	
	private static void load(){
		Config config = new Config();
		logger.info("get config list started!");
		List<ConfVO> newList = getConfListFromServer();
//		List<ConfVO> oldList = getConfListFromJson(FileUtil.readFile(PATH_NAME));
		Map<String, Object> compareMap = ConfListUtil.compareList(oldList, newList);
		boolean isChange = (Boolean) compareMap.get("isChange");
		logger.info("get config list finished!");
		if (isChange) {
			logger.info("password changed!");
			oldList = (List<ConfVO>) compareMap.get("confList");
			
			setRemoteList(oldList);			
			config.set_localPort(localPort);
			config.setRemoteList(remoteList);;
			RemoteServerManager.init(config);
			
			String content = buildContent(oldList);
			FileUtil.writeFile(content, jsonFilePathName);
			QRcodeUtil.createQRCode(oldList, qrcodePath);
//			WinCmdUtil.restartExe(EXE_PATH);
		} else {
			logger.info("password ok!");
		}
	}
	
	private static void setRemoteList(List<ConfVO> list){
		remoteList.clear();
		for(ConfVO vo:list){
			RemoteServer rs = new RemoteServer();
			rs.set_ipAddr(vo.getServer());
			rs.set_method(vo.getMethod());
			rs.set_password(vo.getPassword());
			rs.set_port(vo.getServer_port());
			remoteList.add(rs);
		}
	}
	
	public static int getLocalPort(){
		return localPort;
	}
	
	private static void buildFilePath(){
		File file = new File(outPath);
		if(!file.isDirectory()){
			file.mkdirs();
		}
	}
	
	private static List<ConfVO> getConfListFromServer() {
		List<ConfVO> list = new ArrayList<ConfVO>();
		ConfigList c;
		Map<String, ConfigList> cMap = ConfigListFactory.getConfigListMap();
		for (Iterator<Entry<String, ConfigList>> it = cMap.entrySet().iterator(); it.hasNext();) {
			c = it.next().getValue();
			if (c != null) {
				List<ConfVO> cList = c.getConfigList();
				if (cList != null && !cList.isEmpty()) {
					list.addAll(cList);
				}
			}
		}

		return list;
	}
	
	private static String buildContent(List<ConfVO> list) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("configs", list);
		map.put("strategy", "com.shadowsocks.strategy.ha");
		map.put("index", -1);
		map.put("global", false);
		map.put("enabled", true);
		map.put("shareOverLan", false);
		map.put("isDefault", false);
		map.put("localPort", 1080);
		map.put("pacUrl", null);
		map.put("useOnlinePac", false);
		JSONObject jo = JSONObject.fromObject(map);
		return jo.toString();
	}

}
