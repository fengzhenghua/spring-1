package com.tydic.unicom.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.tydic.unicom.webUtil.BusiMessage;

public class HttpUtil {
	
	// 表示请求器是否已经做了初始化工作
	private boolean hasInit = false;
	
	// 连接超时时间，默认10秒
	private int socketTimeout = 10000;
	
	// 传输超时时间，默认30秒
	private int connectTimeout = 30000;
	
	// 请求器的配置
	private RequestConfig requestConfig;
	
	// HTTP请求器
	private CloseableHttpClient closeableHttpClient;
	
	private static HttpClient httpClient = HttpClientBuilder.create().build();
	private static SSLClient httpsClient = null;

	private static final Logger logger = LoggerFactory
			.getLogger(HttpUtil.class);

	static {
		if (httpClient == null) {
			httpClient = HttpClientBuilder.create().build();
		}
		if (httpsClient == null) {
			try {
				httpsClient = new SSLClient();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	
	public HttpUtil() {
		init();
	}
	
	private void init() {
		if (hasInit) {
			return;
		}
		
		closeableHttpClient = HttpClients.createDefault();
		
		// 根据默认超时限制初始化requestConfig
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
		
		hasInit = true;
	}
	
	
	/**
	 * 执行httpGet请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @param socketTime
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static BusiMessage<String> httpGet(String url, int socketTime,
			Map<String, String> headers) throws ClientProtocolException,
			IOException {
		BusiMessage<String> message = new BusiMessage<>();
		try {
			// 请求URL
			HttpGet httpGet = new HttpGet(url);

			// 配置超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(socketTime).build();

			// 设置请求超时时间
			httpGet.setConfig(requestConfig);

			if (null != headers) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpGet.addHeader(entry.getKey(), entry.getValue());
				}
			}

			// 发送请求
			HttpResponse httpResponse;

			if (url.substring(0, 5).equals("https")) {
				httpResponse = httpsClient.execute(httpGet);
			} else {
				httpResponse = httpClient.execute(httpGet);
			}

			// 获取返回
			HttpEntity httpEntity = httpResponse.getEntity();

			// 获取结果
			String body = EntityUtils.toString(httpEntity);
			// 释放请求
			httpGet.releaseConnection();

			/***** 处理请求异常的返回值 ******/
			if (StringUtils.isEmpty(body)) {
				message.setMsg("返回数据为空");
				return message;
			}

			/**
			 * 请求服务器错误时，包含 <h1>An error occurred.</h1> 字段，所以以此判断是否服务器报错
			 **/
			if (body.indexOf("<h1>An error occurred.</h1>") != -1) {
				message.setData(body);
				message.setCode(ErrorCode.CODE_LINK_FAILURE);
				return message;
			}

			message.setSuccess(true);
			message.setData(body);
			return message;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			message.setMsg("数据请求失败");
			return message;
		}
	}
	
	/**
	 * 
	 * @author heguoyong 2017年5月9日 下午6:13:24
	 * @Method: httpGet 
	 * @Description: 
	 */
	public static BusiMessage<String> httpGet(String url,
			Map<String, String> headers) throws IOException {
		return httpGet(url, 30000, headers);
	}
	
	
	/**
	 * 执行httpGet请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static BusiMessage<String> httpGet(String url)
			throws ClientProtocolException, IOException {
		return httpGet(url, 30000, null);
	}
	
	/**
	 * 执行httpPost请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @param entity
	 * @param contentType
	 * @param socketTimeout
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static BusiMessage<String> httpPost(String url, StringEntity entity,
			String contentType, Map<String, String> headers, int socketTimeout)
			throws ClientProtocolException, IOException {
		BusiMessage<String> message = new BusiMessage<String>();
		try {
			// 请求URL
			HttpPost httpPost = new HttpPost(url);

			if (!StringUtils.isEmpty(contentType)) {
				httpPost.addHeader("content-type", contentType);
			}

			// 请求内容
			httpPost.setEntity(entity);

			// 配置超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(socketTimeout).build();

			if (null != headers) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpPost.addHeader(entry.getKey(), entry.getValue());
				}
			}

			// 设置请求超时时间
			httpPost.setConfig(requestConfig);

			// 发送请求
			HttpResponse httpResponse;

			if (url.substring(0, 5).equals("https")) {
				httpResponse = httpsClient.execute(httpPost);
			} else {
				httpResponse = httpClient.execute(httpPost);
			}

			// 获取返回
			HttpEntity httpEntity = httpResponse.getEntity();

			// 获取结果
			String body = EntityUtils.toString(httpEntity);

			// 释放请求
			httpPost.releaseConnection();

			/***** 处理请求异常的返回值 ******/
			if (StringUtils.isEmpty(body)) {
				message.setMsg("返回数据为空");
				return message;
			}

			/**
			 * 请求服务器错误时，包含 <h1>An error occurred.</h1> 字段，所以以此判断是否服务器报错
			 **/
			if (body.indexOf("<h1>An error occurred.</h1>") != -1) {
				message.setData(body);
				message.setCode(ErrorCode.CODE_LINK_FAILURE);
				return message;
			}

			message.setSuccess(true);
			message.setData(body);
			return message;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			message.setMsg("数据请求失败");
			return message;
		}
	}
	
	/**
	 * 执行httpPost请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @return String
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static BusiMessage<String> httpPost(String url, StringEntity entity)
			throws ClientProtocolException, IOException {
		return httpPost(url, entity, "", 30000);
	}

	/**
	 * 执行httpPost请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @param entity
	 * @param socketTimeout
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static BusiMessage<String> httpPost(String url, StringEntity entity,
			int socketTimeout) throws ClientProtocolException, IOException {
		return httpPost(url, entity, "", socketTimeout);
	}

	/**
	 * 执行httpPost请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @param entity
	 * @param contentType
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static BusiMessage<String> httpPost(String url, StringEntity entity,
			String contentType) throws ClientProtocolException, IOException {
		return httpPost(url, entity, contentType, 30000);
	}

	/**
	 * 执行httpPost请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @param entity
	 * @param contentType
	 * @param socketTimeout
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static BusiMessage<String> httpPost(String url, HttpEntity entity,
			String contentType, int socketTimeout)
			throws ClientProtocolException, IOException {
		BusiMessage<String> message = new BusiMessage<String>();
		try {
			// 请求URL
			HttpPost httpPost = new HttpPost(url);

			if (!StringUtils.isEmpty(contentType)) {
				httpPost.addHeader("content-type", contentType);
			}

			// 请求内容
			httpPost.setEntity(entity);

			// 配置超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(socketTimeout).build();

			// 设置请求超时时间
			httpPost.setConfig(requestConfig);

			HttpResponse httpResponse;
			// 发送请求
			if (url.substring(0, 5).equals("https")) {
				httpResponse = httpsClient.execute(httpPost);
			} else {
				httpResponse = httpClient.execute(httpPost);
			}

			// 获取返回
			HttpEntity httpEntity = httpResponse.getEntity();

			// 获取结果
			String body = EntityUtils.toString(httpEntity,"UTF-8");
			
			//获取返回code
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			// 释放请求
			httpPost.releaseConnection();
			
			if(statusCode == 200){
				message.setSuccess(true);
				message.setData(body);
				return message;

			}

			/***** 处理请求异常的返回值 ******/

			if (statusCode==500||statusCode==503||statusCode==504||statusCode==502||statusCode==501) {
				message.setData(body);
				message.setCode(ErrorCode.CODE_LINK_FAILURE);
				return message;
			}else{
				message.setMsg("请求失败");
				message.setCode(ErrorCode.CODE_LINK_FAILURE);
				return message;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			message.setMsg("数据请求失败");
			return message;
		}
	}

	
	/**
	 * 执行httpPost请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @return String
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static BusiMessage<String> httpPost(String url, HttpEntity entity)
			throws ClientProtocolException, IOException {
		return httpPost(url, entity, "", 30000);
	}

	/**
	 * 执行httpPost请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @param entity
	 * @param socketTimeout
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static BusiMessage<String> httpPost(String url, HttpEntity entity,
			int socketTimeout) throws ClientProtocolException, IOException {
		return httpPost(url, entity, "", socketTimeout);
	}
	/**
	 * 通过http下载文件
	 * 
	 * @param request
	 * @param response
	 * @param downloadUrl
	 *            文件下载url
	 * @param fileName
	 *            指定
	 * @return
	 */
	public static BusiMessage<String> httpDownFile(HttpServletRequest request,
			HttpServletResponse response, String downloadUrl, String fileName) {
		BusiMessage<String> msg = new BusiMessage<String>();
		if (request != null && response != null
				&& !StringUtils.isEmpty(downloadUrl)) {
			InputStream connIs = null;
			// 获取网络输入流
			BufferedInputStream bis = null;
			OutputStream output = null;
			HttpURLConnection connection = null;
			try {
				String userAgent = request.getHeader("User-Agent");
				URL url = new URL(downloadUrl);
				connection = (HttpURLConnection) url.openConnection();
				// 连接指定的资源
				connection.connect();
				connIs = connection.getInputStream();
				// 获取网络输入流
				bis = new BufferedInputStream(connIs);
				// 下载的文件名
				String downloadFileName = connection.getHeaderField("filename");
				if (StringUtils.isEmpty(fileName)) {
					fileName = downloadFileName;
				} else {
					// 后缀名
					String fileExt = downloadFileName
							.substring(downloadFileName.indexOf("."));
					// 指定文件名设置 后缀名
					if (!fileName.endsWith(fileExt)) {
						fileName = fileName + fileExt;
					}
				}
				// 针对IE或者以IE为内核的浏览器：
				if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				} else {
					// 非IE浏览器的处理：
					fileName = new String(fileName.getBytes("UTF-8"),
							"ISO-8859-1");
				}
				response.setHeader("Content-disposition",
						String.format("attachment; filename=\"%s\"", fileName));
				output = response.getOutputStream();
				byte[] bytes = new byte[1024];
				int numReadByte;
				while ((numReadByte = bis.read(bytes, 0, 1024)) > 0) {
					output.write(bytes, 0, numReadByte);
				}
				msg.setSuccess(true);
			} catch (Exception e) {
				msg.setSuccess(false);
				msg.setMsg("下载文件失败:" + e.getMessage());
			} finally {
				try {
					if (output != null) {
						output.close();
					}
				} catch (IOException e) {

				}
				try {
					if (bis != null) {
						bis.close();
					}
				} catch (IOException e) {
				}
				try {
					if (connIs != null) {
						connIs.close();
					}
				} catch (IOException e) {
				}
				connection.disconnect();
			}
		}
		return msg;
	}
	
	
	/**
	 * 执行httpPost请求，并返回结果 若结果为空，请求失败
	 * 
	 * @param url
	 * @param entity
	 * @param contentType
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static BusiMessage<String> httpPost(String url, HttpEntity entity,
			String contentType) throws ClientProtocolException, IOException {
		return httpPost(url, entity, contentType, 30000);
	}

	/**
	 * 
	 * @author heguoyong 
	 * @Method: formHttpPost 
	 * @Description: 表单请求封装
	 * @param url
	 * @param data
	 */
	public static BusiMessage<String> formHttpPost(String url,String data){
		try {
			StringEntity reqEntity = new StringEntity(data,"utf-8");
			return httpPost(url, reqEntity, MediaTypes.APPLICATION_FORM);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 
	 * @author heguoyong 
	 * @Method: jsonHttpPost 
	 * @Description: json请求封装
	 */
	public static BusiMessage<String> jsonHttpPost(String url,String data){
		try {
			StringEntity reqEntity = new StringEntity(data,"utf-8");
			return httpPost(url, reqEntity, MediaTypes.JSON_UTF_8);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * 发送Post数据
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendPost(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		return sendPost(url, params, 0, 0);
	}
	
	public static String sendPost(String url, Map<String, String> params, int socketTimeout, int connectTimeout)
	        throws ClientProtocolException, IOException {
		HttpUtil httpUtil = new HttpUtil();
		
		if (socketTimeout > 0) {
			httpUtil.setSocketTimeout(socketTimeout);
		}
		if (connectTimeout > 0) {
			httpUtil.setConnectTimeout(connectTimeout);
		}
		
		HttpPost httpPost = new HttpPost(url);
		
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		for (String key : params.keySet()) {
			list.add(new BasicNameValuePair(key, params.get(key)));
		}
		
		// url格式编码
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
		httpPost.setEntity(uefEntity);
		
		// 设置请求器的配置
		httpPost.setConfig(httpUtil.getRequestConfig());
		
		HttpResponse response = httpUtil.getHttpClient().execute(httpPost);
		
		HttpEntity entity = response.getEntity();
		
		String result = EntityUtils.toString(entity, "UTF-8");
		
		httpPost.abort();
		
		return result;
	}
	
	public int getSocketTimeout() {
		return socketTimeout;
	}
	
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		resetRequestConfig();
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}
	
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		resetRequestConfig();
	}
	
	public RequestConfig getRequestConfig() {
		return requestConfig;
	}
	
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	
	private void resetRequestConfig() {
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	}
	
	public CloseableHttpClient getHttpClient() {
		return closeableHttpClient;
	}
	
}
