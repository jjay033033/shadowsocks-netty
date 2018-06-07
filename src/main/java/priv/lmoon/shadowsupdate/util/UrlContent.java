/**
 * 
 */
package priv.lmoon.shadowsupdate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.lmoon.shadowsupdate.SysConstants;
import priv.lmoon.shadowsupdate.vo.ServerConfigVO;

/**
 * @author guozy
 * @date 2017-1-6
 * 
 */
public class UrlContent {

	private static final Logger logger = LoggerFactory.getLogger(UrlContent.class);

	private static final int CONNECT_TIME_OUT = 3000;

	public static String getURLContent(ServerConfigVO vo) {
		return getURLContent(vo.getUrl(), vo.getBegin(), vo.getEnd(), new UrlHandler() {

			@Override
			public void changeUrl(String oldUrl, String newUrl) {
				FileUtil.writeFileReplaceWord(SysConstants.CONFIG_PATH, oldUrl, newUrl);
//				XmlConfig.resetInstance();
				 logger.info("'config.xml' changed!: "+oldUrl+" to "+newUrl);
			}
		});
	}

	private static String getURLContent(String urlStr, String beginStr, String endStr, UrlHandler urlHandler) {
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			UrlInfo urlInfo = normalizeUrl(urlStr);
			URL url = new URL(urlInfo.getUrl());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			setConnectionProperties(connection, urlInfo.isHttps());
			connection.connect();
			int code = connection.getResponseCode();
			// 301重定向
			if (code == 301) {
				connection.disconnect();
				String urlStrNew = connection.getHeaderField("Location");
				if (StringUtil.isNullOrBlank(urlStrNew)) {
					return null;
				}
				UrlInfo urlInfoNew = normalizeUrl(urlStrNew);
				urlStrNew = urlInfoNew.getUrl();
				urlHandler.changeUrl(urlStr, urlStrNew);
				url = new URL(urlStrNew);
				connection = (HttpURLConnection) url.openConnection();
				setConnectionProperties(connection, urlInfoNew.isHttps());
				connection.connect();
			}

			isr = new InputStreamReader(connection.getInputStream(), "utf-8");
			br = new BufferedReader(isr);
			String buf = null;
			boolean begin = false;
			if (beginStr == null || beginStr.isEmpty()) {
				begin = true;
			}
			while ((buf = br.readLine()) != null) {
				if (begin) {
					sb.append(buf.trim());
					if ((endStr != null && !endStr.isEmpty()) && buf.contains(endStr)) {
						break;
					}
				} else {
					if (buf.contains(beginStr)) {
						begin = true;
						sb.append(buf.trim());
					}
				}
			}
			// System.out.println("sb:"+sb.toString());
			return sb.toString();
		} catch (Exception e) {
			 logger.error("连接失败："+urlStr, e);
			 e.printStackTrace();
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

	private static class UrlInfo {
		private String url;
		private boolean https;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public boolean isHttps() {
			return https;
		}

		public void setHttps(boolean https) {
			this.https = https;
		}

	}

	/**
	 * 规范化url地址，默认http
	 * 
	 * @param url
	 * @return
	 */
	private static UrlInfo normalizeUrl(String url) {
		UrlInfo ui = new UrlInfo();
		String[] urls = url.split("://");
		if (urls.length < 2) {
			ui.setUrl("http://" + url);
			ui.setHttps(false);
		} else {
			if ("http".equals(urls[0])) {
				ui.setHttps(false);
			} else if ("https".equals(urls[0])) {
				ui.setHttps(true);
			}
			ui.setUrl(url);
		}
		return ui;
	}

	public static InputStream getUrlInputStream(String urlStr) {
		UrlInfo urlInfo = normalizeUrl(urlStr);
		try {
			URL url = new URL(urlInfo.getUrl());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			setConnectionProperties(connection, urlInfo.isHttps());
			return connection.getInputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * fixed 2 java connect web bug:<br>
	 * 1.Server returned HTTP response code: 403 for URL<br>
	 * 2.SSL connect error
	 * @param conn
	 * @param isHttps
	 */
	private static void setConnectionProperties(HttpURLConnection conn, boolean isHttps) {
//		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		conn.setRequestProperty("X-Forwarded-For", "121.33.201.170");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.142 Safari/535.19");
		conn.setInstanceFollowRedirects(true);
		conn.setConnectTimeout(CONNECT_TIME_OUT);
		if (isHttps) {
			HttpsURLConnection hsc = (HttpsURLConnection) conn;
			hsc.setSSLSocketFactory(new MySSLSocketFactory());
			// hsc.setHostnameVerifier(new HostnameVerifier() {
			//
			// @Override
			// public boolean verify(String arg0, SSLSession arg1) {
			// // TODO Auto-generated method stub
			// return true;
			// }
			// });
		}
		// connection.setRequestMethod("GET");
	}

}
