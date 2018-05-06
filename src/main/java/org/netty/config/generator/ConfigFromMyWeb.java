/**
 * 
 */
package org.netty.config.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.netty.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import priv.lmoon.shadowsupdate.vo.ConfVO;

/**
 * @author LMoon
 * @date 2018年4月3日
 * 
 */
public class ConfigFromMyWeb implements ConfigListGenerator {

	private static final Logger logger = LoggerFactory.getLogger(ConfigFromMyWeb.class);

//	private static final String MY_WEB_URL = "https://vast-inlet-75928.herokuapp.com/s/info?method=getss";
	
	private static final String MY_WEB_URL = "http://thess-lmoon-project.7e14.starter-us-west-2.openshiftapps.com/s/info?method=getss";
	
	private static ConfigFromMyWeb configFromMyWeb = new ConfigFromMyWeb();
	
	private ConfigFromMyWeb(){};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.netty.config.generator.ConfigListGenerator#getConfListFromServer()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConfVO> getConfListFromServer() {
		List<ConfVO> list = new ArrayList<>();
		try {
			String result = HttpUtil.get(MY_WEB_URL,10000);
			Object jo = JSON.parse(result);
			List<Map<String, Object>> joList = (List<Map<String, Object>>) jo;
			for (Map<String, Object> map : joList) {
				ConfVO vo = new ConfVO();
				vo.setMethod(MapUtils.getString(map, "method", ""));
				vo.setPassword(MapUtils.getString(map, "password", ""));
				vo.setRemarks(MapUtils.getString(map, "remarks", ""));
				vo.setServer(MapUtils.getString(map, "server", ""));
				vo.setServer_port(MapUtils.getIntValue(map, "serverPort", 0));
				list.add(vo);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return list;
	}

	public static void main(String[] args) {
		String result = "[{\"server\":\"a.isxb.bid\",\"password\":\"68180367\",\"method\":\"aes-256-cfb\",\"id\":\"ids0\",\"serverPort\":16746,\"url\":\"ss://YWVzLTI1Ni1jZmI6NjgxODAzNjdAYS5pc3hiLmJpZDoxNjc0Ng==\",\"remarks\":\"ishadowsocks\"},{\"server\":\"b.isxb.bid\",\"password\":\"isx.yt-50389031\",\"method\":\"aes-256-cfb\",\"id\":\"ids1\",\"serverPort\":14429,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTUwMzg5MDMxQGIuaXN4Yi5iaWQ6MTQ0Mjk=\",\"remarks\":\"ishadowsocks\"},{\"server\":\"c.isxb.bid\",\"password\":\"isx.yt-61222354\",\"method\":\"aes-256-cfb\",\"id\":\"ids2\",\"serverPort\":13011,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTYxMjIyMzU0QGMuaXN4Yi5iaWQ6MTMwMTE=\",\"remarks\":\"ishadowsocks\"},{\"server\":\"jpa.isxa.bid\",\"password\":\"get.ishadowx.net-52022949\",\"method\":\"aes-256-cfb\",\"id\":\"ids\",\"serverPort\":15140,\"url\":\"ss://YWVzLTI1Ni1jZmI6Z2V0LmlzaGFkb3d4Lm5ldC01MjAyMjk0OUBqcGEuaXN4YS5iaWQ6MTUxNDA=\",\"remarks\":\"ishadowsocks\"},{\"server\":\"jpb.isxa.bid\",\"password\":\"isx.yt-73101875\",\"method\":\"aes-256-cfb\",\"id\":\"ids4\",\"serverPort\":11814,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTczMTAxODc1QGpwYi5pc3hhLmJpZDoxMTgxNA==\",\"remarks\":\"ishadowsocks\"},{\"server\":\"jpc.isxa.bid\",\"password\":\"isx.yt-95240729\",\"method\":\"aes-256-cfb\",\"id\":\"ids5\",\"serverPort\":16483,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTk1MjQwNzI5QGpwYy5pc3hhLmJpZDoxNjQ4Mw==\",\"remarks\":\"ishadowsocks\"},{\"server\":\"a.isxc.bid\",\"password\":\"isx.yt-45985050\",\"method\":\"aes-256-cfb\",\"id\":\"ids6\",\"serverPort\":15093,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTQ1OTg1MDUwQGEuaXN4Yy5iaWQ6MTUwOTM=\",\"remarks\":\"ishadowsocks\"},{\"server\":\"b.isxc.bid\",\"password\":\"isx.yt-30338782\",\"method\":\"aes-256-cfb\",\"id\":\"ids7\",\"serverPort\":17046,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTMwMzM4NzgyQGIuaXN4Yy5iaWQ6MTcwNDY=\",\"remarks\":\"ishadowsocks\"},{\"server\":\"c.isxc.bid\",\"password\":\"isx.yt-22929755\",\"method\":\"aes-256-cfb\",\"id\":\"ids8\",\"serverPort\":17364,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTIyOTI5NzU1QGMuaXN4Yy5iaWQ6MTczNjQ=\",\"remarks\":\"ishadowsocks\"},{\"server\":\"a.isxc.bid\",\"password\":\"isx.yt-45985050\",\"method\":\"aes-256-cfb\",\"id\":\"ids9\",\"serverPort\":15093,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTQ1OTg1MDUwQGEuaXN4Yy5iaWQ6MTUwOTM=\",\"remarks\":\"ishadowsocks\"},{\"server\":\"jpb.isxa.bid\",\"password\":\"isx.yt-73101875\",\"method\":\"aes-256-cfb\",\"id\":\"ids10\",\"serverPort\":11814,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTczMTAxODc1QGpwYi5pc3hhLmJpZDoxMTgxNA==\",\"remarks\":\"ishadowsocks\"},{\"server\":\"c.isxb.bid\",\"password\":\"isx.yt-61222354\",\"method\":\"aes-256-cfb\",\"id\":\"ids11\",\"serverPort\":13011,\"url\":\"ss://YWVzLTI1Ni1jZmI6aXN4Lnl0LTYxMjIyMzU0QGMuaXN4Yi5iaWQ6MTMwMTE=\",\"remarks\":\"ishadowsocks\"},{\"server\":\"sga.ss8.site\",\"password\":\"59075296\",\"method\":\"aes-256-cfb\",\"id\":\"ids12\",\"serverPort\":15477,\"url\":\"ss://YWVzLTI1Ni1jZmI6NTkwNzUyOTZAc2dhLnNzOC5zaXRlOjE1NDc3\",\"remarks\":\"shadowsocks8\"},{\"server\":\"usa.ss8.site\",\"password\":\"78376845\",\"method\":\"aes-256-cfb\",\"id\":\"ids13\",\"serverPort\":18752,\"url\":\"ss://YWVzLTI1Ni1jZmI6NzgzNzY4NDVAdXNhLnNzOC5zaXRlOjE4NzUy\",\"remarks\":\"shadowsocks8\"},{\"server\":\"rua.ss8.site\",\"password\":\"76770368\",\"method\":\"rc4-md5\",\"id\":\"ids14\",\"serverPort\":15122,\"url\":\"ss://cmM0LW1kNTo3Njc3MDM2OEBydWEuc3M4LnNpdGU6MTUxMjI=\",\"remarks\":\"shadowsocks8\"}]";
		Object jo = JSON.parse(result);
		List<Map<String, Object>> list = (List<Map<String, Object>>) jo;
		// Object bean = JSONObject.toBean(jo, List.class);
		// List<Map<String,Object>> list = (List<Map<String, Object>>) bean;
		System.out.println(list);
	}

	public static ConfigListGenerator getInstance() {
		return configFromMyWeb;
	}

}
