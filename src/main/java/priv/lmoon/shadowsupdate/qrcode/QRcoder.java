/**
 * 
 */
package priv.lmoon.shadowsupdate.qrcode;

/**
 * @author LMoon
 * @date 2017年7月27日
 * 
 */
public interface QRcoder {
	
	void encode(String content,String filepath);
	
	String decode(String urlStr);

}
