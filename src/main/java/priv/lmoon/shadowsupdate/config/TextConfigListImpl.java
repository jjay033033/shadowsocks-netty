/**
 * 
 */
package priv.lmoon.shadowsupdate.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.lmoon.shadowsupdate.util.UrlContent;
import priv.lmoon.shadowsupdate.vo.ConfParam;
import priv.lmoon.shadowsupdate.vo.ConfVO;
import priv.lmoon.shadowsupdate.vo.ServerConfigVO;

/**
 * @author guozy
 * @date 2017-1-6
 * 
 */
public class TextConfigListImpl implements ConfigList {

	private static final Logger logger = LoggerFactory.getLogger(TextConfigListImpl.class);

	// private static final String FREE_URL = "https://www.ishadowsocks.xyz";
	// private static final String beginStr = "<section id=\"free\">";
	// private static final String endStr = "</section>";

	// private static final String id = "ishadowsocks";

	private ServerConfigVO vo;

	public TextConfigListImpl(ServerConfigVO vo) {
		this.vo = vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see priv.lmoon.shadowsupdate.config.ConfigList#getConfigList()
	 */
	@Override
	public List<ConfVO> getConfigList() {
		// TODO Auto-generated method stub
		return getConf(UrlContent.getURLContent(vo));
	}

	private List<ConfVO> getConf(String content) {
		// System.out.println(content);
		List<ConfVO> list = new ArrayList<ConfVO>();
		if (StringUtils.isBlank(content)) {
			return list;
		}
		try {
			while (content.length() > 0) {
				ConfVO confVo = new ConfVO();
				confVo.setRemarks(vo.getId());
				Map<Integer,ConfParam> params = vo.getParams();
				int beginIdx = 0;
				int endIdx = 0;
				for(int i=1;i<5;i++) {
					ConfParam cp = params.get(i);
					String name = cp.getName();
					beginIdx = indexOfRegex(content, cp.getBegin());
					if (i==1&&beginIdx == -1) {
						return list;
					}
					endIdx = content.indexOf(cp.getEnd(), beginIdx);
					String paramStr = content.substring(beginIdx, endIdx);
					if("serverIp".equals(name)) {
						if(!paramStr.matches("^[\\w\\.]+$")){
							return list;
						}
						confVo.setServer(paramStr);
					}else if("serverPort".equals(name)) {
						String serverPort = paramStr;
						confVo.setServer_port(StringUtils.isBlank(serverPort) ? 0 : Integer.parseInt(serverPort));
					}else if("password".equals(name)) {
						confVo.setPassword(paramStr);
					}else if("encryption".equals(name)) {
						confVo.setMethod(paramStr);
					}
					content = content.substring(endIdx);
				}
//				int serverIpIdx = indexOfRegex(content, vo.getServerIpBegin());
//
//				if (serverIpIdx == -1) {
//					break;
//				}
//
//				int serverIpEndIds = content.indexOf(vo.getServerIpEnd(), serverIpIdx);
//
//				int serverPortIdx = indexOfRegex(content, vo.getServerPortBegin());
//				int serverPortEndIdx = content.indexOf(vo.getServerPortEnd(), serverPortIdx);
//
//				int passwordIdx = indexOfRegex(content, vo.getPasswordBegin());
//				int passwordEndIdx = content.indexOf(vo.getPasswordEnd(), passwordIdx);
//
//				int encryptionIdx = indexOfRegex(content, vo.getEncryptionBegin());
//				int encryptionEndIdx = content.indexOf(vo.getEncryptionEnd(), encryptionIdx);

//				confVo.setServer(content.substring(serverIpIdx, serverIpEndIds));
//				String serverPort = content.substring(serverPortIdx, serverPortEndIdx);
//				confVo.setServer_port(StringUtils.isBlank(serverPort) ? 0 : Integer.parseInt(serverPort));
//				confVo.setPassword(content.substring(passwordIdx, passwordEndIdx));
//				confVo.setMethod(content.substring(encryptionIdx, encryptionEndIdx));
//				confVo.setRemarks(vo.getId());
				if (StringUtils.isNotBlank(confVo.getMethod()) && StringUtils.isNotBlank(confVo.getPassword())
						&& StringUtils.isNotBlank(confVo.getServer()) && confVo.getServer_port() != 0) {
					list.add(confVo);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(content);
			logger.error("",e);
			logger.error(content);
		}

		return list;
	}

	private static int indexOfRegex(String input, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return m.end();
		}
		return -1;
	}

	public static void main(String[] args) {
		// System.out.println(new
		// TextConfigList("ishadowsocks").getConfigList());
//		String c = "This software supported on most common devices like Windows/iPhone/iPad/Android/Macbook. There are only two steps to get it working. First, download and install it. Second, open it and scan QR image. Servers will be configured automatically. Then surfing any websites you like";
//		System.out.println(c.indexOf("it"));
//		System.out.println(indexOfRegex(c, "it"));
		System.out.println("107.173.171.12".matches("^[\\w\\.]+$"));
	}

}
