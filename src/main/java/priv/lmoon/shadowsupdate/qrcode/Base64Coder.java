/**
 * 
 */
package priv.lmoon.shadowsupdate.qrcode;

import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Base64.Decoder;

/**
 * @author guozy
 * @date 2016-7-22
 * 
 */
public class Base64Coder {
	
	public static void main(String[] args) throws Exception {
		//ss://YmYtY2ZiOnRlc3RAMTkyLjE2OC4xMDAuMTo4ODg4
		//YmYtY2ZiOnRlc3RAMTkyLjE2OC4xMDAuMTo4ODg4
//		byte[] input = "bf-cfb:test@192.168.100.1:8888".getBytes();
//		System.out.println(encodeBase64(input));
//		ss://cmM0LW1kNTo3MTk3MzU1NkAxMzguNjguNjEuNDI6MjM0NTYK
//		String input = "ss://YWVzLTI1Ni1jZmI6MTM3ODQ5QDE5Mi4zLjEyLjE2NzozMzMzMw";
//		System.out.println(decodeBase64ForSS(input));
//		System.out.println(decodeBase64("YWVzLTI1Ni1jZmI6MTM3ODQ5QDE5Mi4zLjEyLjE2NzozMzMzMw"));
		byte[] decode = Base64.getDecoder().decode("YWVzLTI1Ni1jZmI6MTM3ODQ5QDE5Mi4zLjEyLjE2NzozMzMzMw");
		System.out.println(new String(decode));
	}
	
	public static String encodeBase64(String input) throws Exception{
		return encodeBase64(input.getBytes());
	}
	
	/*** 
     * encode by Base64 
     */  
    public static String encodeBase64(byte[]input) throws Exception{  
//    	Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
//        Method mainMethod= clazz.getMethod("encode", byte[].class);  
//        mainMethod.setAccessible(true);  
//         Object retObj=mainMethod.invoke(null, new Object[]{input});  
//         return (String)retObj;  
    	return Base64.getEncoder().encodeToString(input);
    } 
    
    public static byte[] decodeBase64(String input) throws Exception{
//    	Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
//        Method mainMethod= clazz.getMethod("decode", String.class);  
//        mainMethod.setAccessible(true);  
//         Object retObj=mainMethod.invoke(null, input);  
//         return (byte[])retObj;  
    	return Base64.getDecoder().decode(input);
    }
 
    
    public static void createQRCodePic4Base64(String input,String filepath) throws Exception{
    	QRcoder qRcoder = new ZxingQRcoder();
    	qRcoder.encode("ss://"+encodeBase64(input), filepath);
    }
    
    public static String decodeBase64ForSS(String input) throws Exception{
    	return new String(decodeBase64(input.replaceFirst("ss://", "")));
    }

}
