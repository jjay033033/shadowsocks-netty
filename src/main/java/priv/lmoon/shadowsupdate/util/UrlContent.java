/**
 * 
 */
package priv.lmoon.shadowsupdate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.lmoon.shadowsupdate.SysConstants;
import priv.lmoon.shadowsupdate.config.XmlConfig;
import priv.lmoon.shadowsupdate.vo.ServerConfigVO;

/**
 * @author guozy
 * @date 2017-1-6
 * 
 */
public class UrlContent {
	
	private static final Logger logger = LoggerFactory.getLogger(UrlContent.class);
	
	public static String getURLContent(ServerConfigVO vo){
		return getURLContent(vo.getUrl(), vo.getBegin(), vo.getEnd(),new UrlHandler() {
			
			@Override
			public void changeUrl(String oldUrl, String newUrl) {
				FileUtil.writeFileReplaceWord(SysConstants.CONFIG_PATH, oldUrl, newUrl);
				XmlConfig.resetInstance();
				logger.info("'config.xml' changed!: "+oldUrl+" to "+newUrl);
			}
		});
	}
	
	private static String getURLContent(String urlStr,String beginStr,String endStr,UrlHandler urlHandler) {
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			setConnectionProperties(connection);
			connection.connect();  
			int code = connection.getResponseCode();
			//301重定向
			if(code==301){				
				connection.disconnect();
				String urlStrNew = connection.getHeaderField("Location"); 
				if(StringUtil.isNullOrBlank(urlStrNew)){
					return null;
				}
				urlHandler.changeUrl(urlStr, urlStrNew);				
				url = new URL(urlStrNew);
				connection = (HttpURLConnection)url.openConnection();
				setConnectionProperties(connection);
				connection.connect();
			}
            
			isr = new InputStreamReader(connection.getInputStream(), "utf-8");
			br = new BufferedReader(isr);
			String buf = null;
			boolean begin = false;
			if(beginStr==null||beginStr.isEmpty()){
				begin = true;
			}
			while ((buf = br.readLine()) != null) {
				if(begin){
					sb.append(buf.trim());
					if((endStr!=null&&!endStr.isEmpty()) && buf.contains(endStr)){
						break;
					}
				}else{
					if (buf.contains(beginStr)) {
						begin = true;
						sb.append(buf.trim());
					} 
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getURLContent:"+urlStr, e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private static void setConnectionProperties(HttpURLConnection conn){
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		conn.setInstanceFollowRedirects(true);   
//		connection.setRequestMethod("GET"); 
	}

}
