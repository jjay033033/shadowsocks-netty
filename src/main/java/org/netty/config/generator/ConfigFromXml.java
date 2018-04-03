/**
 * 
 */
package org.netty.config.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import priv.lmoon.shadowsupdate.config.ConfigList;
import priv.lmoon.shadowsupdate.config.ConfigListFactory;
import priv.lmoon.shadowsupdate.vo.ConfVO;

/**
 * @author LMoon
 * @date 2018年4月3日
 * 
 */
public class ConfigFromXml implements ConfigListGenerator{
	
	private static ConfigFromXml configFromXml = new ConfigFromXml();
	
	private ConfigFromXml(){};
	
	@Override
	public List<ConfVO> getConfListFromServer() {
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

	public static ConfigListGenerator getInstance() {
		return configFromXml;
	}

}
