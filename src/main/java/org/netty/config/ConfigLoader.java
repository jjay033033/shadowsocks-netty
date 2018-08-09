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

import org.netty.config.generator.ConfigFromMyWeb;
import org.netty.config.generator.ConfigFromXml;
import org.netty.config.generator.ConfigListGenerator;
import org.netty.manager.RemoteServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import top.lmoon.shadowsupdate.config.ConfigList;
import top.lmoon.shadowsupdate.config.ConfigListFactory;
import top.lmoon.shadowsupdate.config.XmlConfig;
import top.lmoon.shadowsupdate.qrcode.QRcodeUtil;
import top.lmoon.shadowsupdate.util.ConfListUtil;
import top.lmoon.shadowsupdate.util.FileUtil;
import top.lmoon.shadowsupdate.vo.ConfVO;

public class ConfigLoader {

	private static Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

	private static final int FROM_MY_WEB = 1;

	private static final int FROM_XML = 0;

	private static String outPath, qrcodePath, jsonFilePathName;

	private static List<ConfVO> oldList = null;

	private static List<RemoteServer> remoteList = new ArrayList<>();

	private ConfigLoader() {

	}

	/**
	 * 需先初始化值
	 * 
	 * @param configPath
	 */
	public static void init(String outPath, String qrcodePath, String jsonFilePathName) {
		ConfigLoader.outPath = outPath;
		ConfigLoader.qrcodePath = qrcodePath;
		ConfigLoader.jsonFilePathName = jsonFilePathName;
		buildFilePath();
		start();
	}

	private static void start() {

		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				try {
					load();
				} catch (Exception e) {
					logger.error("load pac error", e);
				}
			}
		}, 0L, XmlConfig.getSleepTime(), TimeUnit.SECONDS);

	}

	private static void load() {
		Config config = new Config();
		logger.info("get config list started!");
		ConfigListGenerator glg = null;
		if (XmlConfig.getFrom() == FROM_MY_WEB) {
			glg = ConfigFromMyWeb.getInstance();
		}else{
			glg = ConfigFromXml.getInstance();
		}
		List<ConfVO> newList = glg.getConfListFromServer();
		// List<ConfVO> oldList =
		// getConfListFromJson(FileUtil.readFile(PATH_NAME));
		Map<String, Object> compareMap = ConfListUtil.compareList(oldList, newList);
		boolean isChange = (Boolean) compareMap.get("isChange");
		logger.info("get config list finished!");
		if (isChange) {
			logger.info("password changed!");
			oldList = (List<ConfVO>) compareMap.get("confList");

			setRemoteList(oldList);
			config.set_localPort(XmlConfig.getLocalPort());
			config.setRemoteList(remoteList);
			;
			RemoteServerManager.init(config);

			String content = buildContent(oldList);
			FileUtil.writeFile(content, jsonFilePathName);
			QRcodeUtil.createQRCode(oldList, qrcodePath);
			// WinCmdUtil.restartExe(EXE_PATH);
		} else {
			logger.info("password as before!");
		}
	}

	private static void setRemoteList(List<ConfVO> list) {
		remoteList.clear();
		for (ConfVO vo : list) {
			RemoteServer rs = new RemoteServer();
			rs.set_ipAddr(vo.getServer());
			rs.set_method(vo.getMethod());
			rs.set_password(vo.getPassword());
			rs.set_port(vo.getServer_port());
			remoteList.add(rs);
		}
	}

	private static void buildFilePath() {
		File file = new File(outPath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
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
