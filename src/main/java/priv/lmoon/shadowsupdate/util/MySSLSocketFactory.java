/**
 * 
 */
package priv.lmoon.shadowsupdate.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author LMoon
 * @date 2018年4月2日
 * 
 */
public class MySSLSocketFactory extends SSLSocketFactory{
	
	private SSLContext sslcontext = null;  
	  
    private SSLContext createSSLContext() {  
        SSLContext sslcontext = null;  
        try {  
            sslcontext = SSLContext.getInstance("SSL");  
            sslcontext.init(null,  
                    new TrustManager[] { new TrustAnyTrustManager() },  
                    new java.security.SecureRandom());  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (KeyManagementException e) {  
            e.printStackTrace();  
        }  
        return sslcontext;  
    }  
  
    private SSLContext getSSLContext() {  
        if (this.sslcontext == null) {  
            this.sslcontext = createSSLContext();  
        }  
        return this.sslcontext;  
    }

	/* (non-Javadoc)
	 * @see javax.net.ssl.SSLSocketFactory#createSocket(java.net.Socket, java.lang.String, int, boolean)
	 */
	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		 return getSSLContext().getSocketFactory().createSocket(s, host,  
	                port, autoClose);  
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.SSLSocketFactory#getDefaultCipherSuites()
	 */
	@Override
	public String[] getDefaultCipherSuites() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.SSLSocketFactory#getSupportedCipherSuites()
	 */
	@Override
	public String[] getSupportedCipherSuites() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.lang.String, int)
	 */
	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException {
		 return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int)
	 */
	@Override
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		 return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int)
	 */
	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
			throws IOException, UnknownHostException {
		 return getSSLContext().getSocketFactory().createSocket(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int, java.net.InetAddress, int)
	 */
	@Override
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws IOException {
		 return getSSLContext().getSocketFactory().createSocket(arg0, arg1, arg2, arg3);
	}
	
	private static class TrustAnyTrustManager implements X509TrustManager {  
		  
        public void checkClientTrusted(X509Certificate[] chain, String authType)  
                throws CertificateException {  
        }  
  
        public void checkServerTrusted(X509Certificate[] chain, String authType)  
                throws CertificateException {  
        }  
  
        public X509Certificate[] getAcceptedIssuers() {  
            return new X509Certificate[] {};  
        }  
    } 

}
