package com.tydic.unicom.util;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月9日 下午6:01:56
 * @ClassName SSLClient
 * @Description SSL连接请求
 * @version V1.0
 */
public class SSLClient extends DefaultHttpClient {
	
	public SSLClient() throws Exception {
		super();
		SSLContext ctx = SSLContext.getInstance("TLS");
		X509TrustManager tm = new X509TrustManager() {
			
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
			        throws java.security.cert.CertificateException {
			}
			
			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
			        throws java.security.cert.CertificateException {
			}
			
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			
		};
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
			
			public void verify(String arg0, SSLSocket arg1) throws IOException {
			}
			
			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
			}
			
			public void verify(String arg0, X509Certificate arg1) throws SSLException {
			}
		};
		ctx.init(null, new TrustManager[] {tm}, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx);
		ssf.setHostnameVerifier(hostnameVerifier);
		ClientConnectionManager ccm = this.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		// 设置要使用的端口，默认是443
		sr.register(new Scheme("https", 443, ssf));
	}
}
