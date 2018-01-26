package priv.lmoon.shadowsupdate.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.lmoon.shadowsupdate.SysConstants;
import priv.lmoon.shadowsupdate.util.XmlUtil;
import priv.lmoon.shadowsupdate.vo.ServerConfigVO;

public class XmlConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(XmlConfig.class);
	
//	private static XmlConfig xmlConfig;
	
	private static Map map;
	
	private static Map<String,ServerConfigVO> serverMap = new LinkedHashMap<String, ServerConfigVO>();
	
	private static String configPath;
	
	private XmlConfig(){
		
	}
	
	public static void init(String configPath){
		try {
			XmlConfig.configPath = configPath;
//			XmlMap xm = new XmlMap(configPath);
//			map = xm.getConfigMap();
			FileInputStream fis = new FileInputStream(new File(configPath));
			map = XmlUtil.toMap(fis);
			if (map == null || map.isEmpty()) {
				throw new FileNotFoundException();
			}
			initServerMap();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("config.xml初始化失败:", e);
		}
		
	}
	
	private static void initServerMap(){
		try {
			Map servers = (Map) map.get("servers");
			if (servers == null || servers.isEmpty()) {
				throw new Exception("没有servers项！");
			}
			Object o = servers.get("server");
			if(o == null){
				throw new Exception("没有server项！");
			}
			List items;
			if(o instanceof List){
				items = (List) o;
			}else{
				items = new ArrayList();
				items.add((Map)o);
			}
			
			if (items == null || items.isEmpty()) {
				throw new Exception("没有server项！");
			}
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				Map item = (Map) iter.next();
				if (item != null && item.get("id") != null) {
					ServerConfigVO vo = new ServerConfigVO();
					vo.setBegin((String) item.get("begin"));
					vo.setEnd((String) item.get("end"));
					vo.setId((String) item.get("id"));
					vo.setUrl((String) item.get("url"));
					vo.setType(Integer.parseInt((String) item.get("type")));
					if(vo.getType()==SysConstants.ServerType.TEXT){
						vo.setServerIpBegin((String) item.get("serverIpBegin"));
						vo.setServerIpEnd((String) item.get("serverIpEnd"));
						vo.setServerPortBegin((String) item.get("serverPortBegin"));
						vo.setServerPortEnd((String) item.get("serverPortEnd"));
						vo.setPasswordBegin((String) item.get("passwordBegin"));
						vo.setPasswordEnd((String) item.get("passwordEnd"));
						vo.setEncryptionBegin((String) item.get("encryptionBegin"));
						vo.setEncryptionEnd((String) item.get("encryptionEnd"));
					}else if(vo.getType()==SysConstants.ServerType.PIC){
						vo.setPicUrlBegin((String) item.get("picUrlBegin"));
						vo.setPicUrlEnd((String) item.get("picUrlEnd"));
						vo.setSeverPicFlag((String) item.get("severPicFlag"));
					}
					serverMap.put((String)item.get("id"), vo);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("servers初始化失败:", e);
		}
	}
	
//	public static XmlConfig getInstance(){
//		if(xmlConfig == null){
//			xmlConfig = new XmlConfig();
//
//		}
//		return xmlConfig;
//	}
	
	public static void resetInstance(){
//		xmlConfig = null;
		init(configPath);
	}
	
//	public ServerConfigVo getServerConfigVo(String id){
//		ServerConfigVo vo = serverMap.get(id);
//		if(vo == null){
//			vo = new ServerConfigVo();
//		}
//		return vo;
//	}
	
	/**
	 * 整个xml文件的map
	 * @return
	 */
	public static Map getMap(){
		return map;
	}
	
	/**
	 * 整个xml文件map根据key取value
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		if(map!=null){
			return (String) map.get(key);
		}
		return null;
	}
	
	/**
	 * 服务器配置（servers标签下）的map
	 * @return
	 */
	public static Map<String, ServerConfigVO> getServerConfigMap(){
		return serverMap;
	}

}
