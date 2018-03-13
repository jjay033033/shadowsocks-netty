package priv.lmoon.shadowsupdate.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import priv.lmoon.shadowsupdate.SysConstants;
import priv.lmoon.shadowsupdate.vo.ServerConfigVO;

public class ConfigListFactory {
	
	private static Map<String,ConfigList> configMap = new HashMap<String, ConfigList>();
	
//	private static ConfigListFactory configListFactory;
	
	private static ConfigList firstConfigList;
	
	private ConfigListFactory(){
		
	}
	
	public static void init(){
		configMap.clear();
		Map<String,ServerConfigVO> map = XmlConfig.getServerConfigMap();
		int i = 0;
		for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
			String key = it.next();
			ServerConfigVO vo = map.get(key);
			switch(vo.getType()){
			case SysConstants.ServerType.TEXT:
				configMap.put(vo.getId(), new TextConfigListImpl(vo));
				break;
			case SysConstants.ServerType.PIC:
				configMap.put(vo.getId(), new PicConfigListImpl(vo));
				break;
			default:
				break;
			}
			if(i==0){
				firstConfigList = configMap.get(vo.getId());
			}
			i++;
		}
	}
	
	public static ConfigList getConfigListObj(String id){
		return configMap.get(id);
	}
	
	public static ConfigList getFirstConfigList(){
		return firstConfigList;
	}
	
	public static Map<String,ConfigList> getConfigListMap(){
		return configMap;
	}

}
