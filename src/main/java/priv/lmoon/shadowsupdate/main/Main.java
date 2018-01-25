package priv.lmoon.shadowsupdate.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import priv.lmoon.shadowsupdate.config.ConfigList;
import priv.lmoon.shadowsupdate.config.ConfigListFactory;
import priv.lmoon.shadowsupdate.config.XmlConfig;
import priv.lmoon.shadowsupdate.qrcode.QRcodeUtil;
import priv.lmoon.shadowsupdate.util.ConfListUtil;
import priv.lmoon.shadowsupdate.util.FileUtil;
import priv.lmoon.shadowsupdate.util.WinCmdUtil;
import priv.lmoon.shadowsupdate.vo.ConfVO;

public class Main {

//	static {
//		PropertyConfigurator.configure(System.getProperty("user.dir") + "/res/log4j.properties");
//	}

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	private static final String HOME_PATH = "res/";

	private static final String PATH_NAME = HOME_PATH + "gui-config.json";
	private static final String EXE_NAME = "Shadowsocks.exe";
	private static final String EXE_PATH = HOME_PATH + EXE_NAME;
	private static final String QRCODE_PATH = HOME_PATH + "QRCode/";

	private static final String SLEEP_TIME = "sleepTime";
	
	private static final String LOCAL_PORT = "localPort";

	public static void main(String[] args) {

		// kill("Shadowsocks.exe");
		if (WinCmdUtil.checkExeHasDone(EXE_NAME)) {
			System.out.println("已运行" + EXE_NAME);
			logger.info("已运行" + EXE_NAME);
			return;
		}
		try {
			long sleepTime = Long.parseLong(XmlConfig.getInstance().getValue(SLEEP_TIME));
			int localPort = Integer.parseInt(XmlConfig.getInstance().getValue(LOCAL_PORT));
			WinCmdUtil.restartExe(EXE_PATH);
			while (true) {
				if (!WinCmdUtil.isExistProcess()) {
					break;
				}
				doChange();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}

	}
	
	public static void doChange(){
		List<ConfVO> newList = getConfListFromServer();
		List<ConfVO> oldList = getConfListFromJson(FileUtil.readFile(PATH_NAME));
		Map<String, Object> compareMap = ConfListUtil.compareList(oldList, newList);
		boolean isChange = (Boolean) compareMap.get("isChange");
		if (isChange) {
			logger.debug("password changed!");
			List<ConfVO> list = (List<ConfVO>) compareMap.get("confList");
			String content = buildContent(list);
			FileUtil.writeFile(content, PATH_NAME);
			QRcodeUtil.createQRCode(list, QRCODE_PATH);
			WinCmdUtil.restartExe(EXE_PATH);
		} else {
			logger.debug("password ok!");
		}
	}
	
//	public static void main(String[] args) {
//		System.out.println(getConfListFromServer());
//	}

	private static List<ConfVO> getConfListFromServer() {
		List<ConfVO> list = new ArrayList<ConfVO>();
		ConfigList c;

		Map<String, ConfigList> cMap = ConfigListFactory.getInstance().getConfigListMap();
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

	public static String buildContent(List<ConfVO> list) {
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

	public static List<ConfVO> getConfListFromJson(String jsonStr) {
		try {
			if (jsonStr == null || jsonStr.isEmpty()) {
				return null;
			}
			JSONObject jo = JSONObject.fromObject(jsonStr);
			JSONArray confJa = jo.getJSONArray("configs");
			if (confJa == null || confJa.isEmpty()) {
				return null;
			}
			List<ConfVO> list = (List<ConfVO>) JSONArray.toCollection(confJa, ConfVO.class);
			return list;
		} catch (Exception e) {
			logger.error("getConfListFromJson:", e);
			e.printStackTrace();
			return null;
		}

	}

}