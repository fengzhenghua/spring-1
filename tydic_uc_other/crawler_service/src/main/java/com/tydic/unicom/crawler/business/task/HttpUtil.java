package com.tydic.unicom.crawler.business.task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	/**
     * 发送Post数据
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String sendPost(String url, Map<String, String> params) throws ClientProtocolException, IOException{
    	return sendPost(url, params, 0, 0);
    }
    
    public static String sendPost(String url, Map<String, String> params, int socketTimeout, int connectTimeout) throws ClientProtocolException, IOException{
    	HttpUtil httpUtil = new HttpUtil();
    	
    	if(socketTimeout > 0){
    		httpUtil.setSocketTimeout(socketTimeout);
    	}
    	if(connectTimeout > 0){
    		httpUtil.setConnectTimeout(connectTimeout);
    	}
    	
    	HttpPost httpPost = new HttpPost(url);
    	
    	List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
    	for(String key : params.keySet()){
    		list.add(new BasicNameValuePair(key, params.get(key)));
    	}
        
        //url格式编码
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(uefEntity);

        //设置请求器的配置
        httpPost.setConfig(httpUtil.getRequestConfig());
    	
        HttpResponse response = httpUtil.getHttpClient().execute(httpPost);

        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "UTF-8");
        
        httpPost.abort();
        
        return result;
    }

	//表示请求器是否已经做了初始化工作
    private boolean hasInit = false;

    //连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;
    
    public HttpUtil(){
    	init();
    }
    
    private void init(){
    	if(hasInit){
    		return;
    	}
    	
    	httpClient = HttpClients.createDefault();
    	
    	//根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

    	hasInit = true;
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
	
	private void resetRequestConfig(){
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	
}
