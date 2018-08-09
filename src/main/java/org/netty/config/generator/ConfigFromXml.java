/**
 * 
 */
package org.netty.config.generator;

import java.util.List;

import top.lmoon.shadowsupdate.Shadowsupdate;
import top.lmoon.shadowsupdate.vo.ConfVO;

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
		return Shadowsupdate.getInstance().getConfList();
	}

	public static ConfigListGenerator getInstance() {
		return configFromXml;
	}

}
