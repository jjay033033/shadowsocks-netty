/**
 * 
 */
package org.netty.config.generator;

import java.util.List;

import top.lmoon.shadowsupdate.vo.ConfVO;

/**
 * @author LMoon
 * @date 2018年4月3日
 * 
 */
public interface ConfigListGenerator {
	
	List<ConfVO> getConfListFromServer();

}
