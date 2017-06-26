/*
 * Copyright (C) 2015 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.tydic.unicom.crawlerframe.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tydic.unicom.crawlerframe.exception.NoLogonException;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.util.Config;

/**
 *
 * @author hu
 */
public class MallHttpRequest {

	public static final Logger LOG = LoggerFactory.getLogger(MallHttpRequest.class);

	protected int MAX_REDIRECT = Config.MAX_REDIRECT;
	protected int MAX_RECEIVE_SIZE = Config.MAX_RECEIVE_SIZE;
	protected String method = Config.DEFAULT_HTTP_METHOD;
	protected boolean doinput = true;
	protected boolean dooutput = true;
	protected boolean followRedirects = false;
	protected int timeoutForConnect = Config.TIMEOUT_CONNECT;
	protected int timeoutForRead = Config.TIMEOUT_READ;
	protected byte[] outputData = null;
	public List<NameValuePair> nvps = new ArrayList<NameValuePair>();

	Proxy proxy = null;
	RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
	CloseableHttpClient closeableHttpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

	protected Map<String, List<String>> headerMap = null;

	protected CrawlDatum crawlDatum = null;

	public MallHttpRequest(String url) throws Exception {
		this.crawlDatum = new CrawlDatum(url);
		setUserAgent(Config.DEFAULT_USER_AGENT);
	}

	public MallHttpRequest(String url, Proxy proxy) throws Exception {
		this(url);
		this.proxy = proxy;
	}

	public MallHttpRequest(CrawlDatum crawlDatum) throws Exception {
		this.crawlDatum = crawlDatum;
		setUserAgent(Config.DEFAULT_USER_AGENT);
	}

	public MallHttpRequest(CrawlDatum crawlDatum, Proxy proxy) throws Exception {
		this(crawlDatum);
		this.proxy = proxy;
	}

	@Deprecated
	public MallHttpResponse getResponse() throws Exception {
		return response();
	}

	public MallHttpResponse response() throws Exception {
		URL url = new URL(crawlDatum.url());
		MallHttpResponse mallresponse = new MallHttpResponse(url);
		int code = -1;
		int maxRedirect = Math.max(0, MAX_REDIRECT);
		// HttpURLConnection con = null;
		InputStream is = null;
		try {
			for (int redirect = 0; redirect <= maxRedirect; redirect++) {
				HttpResponse httpResponse;
				if (getMethod().equals("POST")) {
					HttpPost httppost = new HttpPost(crawlDatum.url());
					if (headerMap != null) {
						for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
							String key = entry.getKey();
							List<String> valueList = entry.getValue();
							for (String value : valueList) {
								httppost.addHeader(key, value);
							}
						}
					}
					if (nvps != null)
						httppost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
					LOG.debug("===================================开始 连接网站");
					httpResponse = closeableHttpClient.execute(httppost);
					LOG.debug("===================================完成 网站返回");
				} else {
					HttpGet httpget = new HttpGet(crawlDatum.url());
					if (headerMap != null) {
						for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
							String key = entry.getKey();
							List<String> valueList = entry.getValue();
							for (String value : valueList) {
								httpget.addHeader(key, value);
							}
						}
					}
					httpResponse = closeableHttpClient.execute(httpget);
				}
				mallresponse.sethttpResponse(httpResponse);
				// 得到post请求返回的cookie信息
				mallresponse.cookie = getCookiefromResponse(httpResponse);

				code = httpResponse.getStatusLine().getStatusCode();
				LOG.debug("====完成 网站返回"+crawlDatum.url() + "; code : " +code);
				/* 只记录第一次返回的code */
				if (redirect == 0) {
					mallresponse.code(code);
				}

				if (code == HttpURLConnection.HTTP_NOT_FOUND || code == HttpURLConnection.HTTP_BAD_GATEWAY) {
					mallresponse.setNotFound(true);
					return mallresponse;
				}

				boolean needBreak = false;
				switch (code) {
				case HttpURLConnection.HTTP_MOVED_PERM:
				case HttpURLConnection.HTTP_MOVED_TEMP:
					mallresponse.setRedirect(true);
					if (redirect == MAX_REDIRECT) {
						throw new Exception("redirect to much time");
					}
					httpResponse.getHeaders("Location");
					String location = httpResponse.getFirstHeader("Location").getValue();
					if (location == null) {
						throw new Exception("redirect with no location");
					}
					String originUrl = url.toString();
					url = new URL(url, location);
					mallresponse.setRealUrl(url);
					LOG.debug("redirect from " + originUrl + " to " + url.toString());
					throw new NoLogonException();
					// continue;
				default:
					needBreak = true;
					break;
				}
				if (needBreak) {
					break;
				}
			}
			// is = mallresponse.httpResponse.getEntity().getContent().;
			// 暂时不支持 GZIP 解压
			// if (contentEncoding != null && contentEncoding.equals("gzip")) {
			// is = new GZIPInputStream(is);
			// }
			//
			String content = EntityUtils.toString(mallresponse.httpResponse.getEntity());
			LOG.debug(content);
//			File f = new File("intpu.htm");
//			try {
//				if (f.exists()) {
//					f.delete();
//				}
//				f.createNewFile();
//				FileOutputStream fos = new FileOutputStream(f);
//				fos.write(content.getBytes("UTF-8"));
//				fos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			mallresponse.content(content.getBytes());
			return mallresponse;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	// public HttpURLConnection config(HttpURLConnection con) throws Exception {
	// con.setRequestMethod(method);
	// con.setInstanceFollowRedirects(followRedirects);
	// con.setDoInput(doinput);
	// con.setDoOutput(dooutput);
	// con.setConnectTimeout(timeoutForConnect);
	// con.setReadTimeout(timeoutForRead);
	// if (headerMap != null) {
	// for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
	// String key = entry.getKey();
	// List<String> valueList = entry.getValue();
	// for (String value : valueList) {
	// con.addRequestProperty(key, value);
	// }
	// }
	// }
	// return con;
	// }

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public CrawlDatum getCrawlDatum() {
		return crawlDatum;
	}

	public void setCrawlDatum(CrawlDatum crawlDatum) {
		this.crawlDatum = crawlDatum;
	}

	static {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			// HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception ex) {
			LOG.info("Exception", ex);
		}
	}

	private void initHeaderMap() {
		if (headerMap == null) {
			headerMap = new HashMap<String, List<String>>();
		}
	}

	public void setUserAgent(String userAgent) {
		setHeader("User-Agent", userAgent);
	}

	public void setCookie(String cookie) {
		setHeader("Cookie", cookie);
	}

	public void addHeader(String key, String value) {
		if (key == null) {
			throw new NullPointerException("key is null");
		}
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		initHeaderMap();
		List<String> valueList = headerMap.get(key);
		if (valueList == null) {
			valueList = new ArrayList<String>();
			headerMap.put(key, valueList);
		}
		valueList.add(value);
	}

	public void removeHeader(String key) {
		if (key == null) {
			throw new NullPointerException("key is null");
		}

		if (headerMap != null) {
			headerMap.remove(key);
		}
	}

	public void setHeader(String key, String value) {
		if (key == null) {
			throw new NullPointerException("key is null");
		}
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		initHeaderMap();
		List<String> valueList = new ArrayList<String>();
		valueList.add(value);
		headerMap.put(key, valueList);
	}

	public int getMAX_REDIRECT() {
		return MAX_REDIRECT;
	}

	public void setMAX_REDIRECT(int MAX_REDIRECT) {
		this.MAX_REDIRECT = MAX_REDIRECT;
	}

	public int getMAX_RECEIVE_SIZE() {
		return MAX_RECEIVE_SIZE;
	}

	public void setMAX_RECEIVE_SIZE(int MAX_RECEIVE_SIZE) {
		this.MAX_RECEIVE_SIZE = MAX_RECEIVE_SIZE;
	}

	public Map<String, List<String>> getHeaders() {
		return headerMap;
	}

	public List<String> getHeader(String key) {
		if (headerMap == null) {
			return null;
		}
		return headerMap.get(key);
	}

	public String getFirstHeader(String key) {
		if (headerMap == null) {
			return null;
		}
		List<String> valueList = headerMap.get(key);
		if (valueList.size() > 0) {
			return valueList.get(0);
		} else {
			return null;
		}
	}

	public boolean isDoinput() {
		return doinput;
	}

	public void setDoinput(boolean doinput) {
		this.doinput = doinput;
	}

	public boolean isDooutput() {
		return dooutput;
	}

	public void setDooutput(boolean dooutput) {
		this.dooutput = dooutput;
	}

	public int getTimeoutForConnect() {
		return timeoutForConnect;
	}

	public void setTimeoutForConnect(int timeoutForConnect) {
		this.timeoutForConnect = timeoutForConnect;
	}

	public int getTimeoutForRead() {
		return timeoutForRead;
	}

	public void setTimeoutForRead(int timeoutForRead) {
		this.timeoutForRead = timeoutForRead;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public Map<String, List<String>> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, List<String>> headerMap) {
		this.headerMap = headerMap;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	public byte[] getOutputData() {
		return outputData;
	}

	public void setOutputData(byte[] outputData) {
		this.outputData = outputData;
		this.dooutput = true;
	}

	public void setPar(String k, String val) {
		nvps.add(new BasicNameValuePair(k, val));
	}

	public String getCookiefromResponse(String cookie) {
		Map<String, String> cookieMap = new HashMap<String, String>(64);
		String cookies[] = cookie.split(";");
		for (String c : cookies) {
			c = c.trim();
			String key = "";
			String val = "";
			if (c.indexOf("=") > 0) {
				key = c.substring(0, c.indexOf("="));
				if (key.equals("path") || key.equals("expires") || key.equals("domain"))
					continue;
				val = c.substring(c.indexOf("=") + 1);
				cookieMap.put(key, val);
			}
		}
		String cookiesTmp = "";
		for (String key : cookieMap.keySet()) {
			cookiesTmp += key + "=" + cookieMap.get(key) + ";";
		}
		return cookiesTmp;
	}

	// 从响应信息中获取cookie
	public String getCookiefromResponse(HttpResponse httpResponse) {
		Header headers[] = httpResponse.getHeaders("Set-Cookie");
		if (headers == null || headers.length == 0) {
			return null;
		}
		String cookie = "";
		for (int i = 0; i < headers.length; i++) {
			cookie += headers[i].getValue();
			if (i != headers.length - 1) {
				cookie += ";";
			}
		}
		LOG.info("===========================" + cookie);
		return getCookiefromResponse(cookie);
	}
}
