package com.tydic.unicom.upc.service.capacity;

import com.open.rest.api.sdk.client.OrsClient;
import com.open.rest.api.sdk.client.OrsException;
import com.open.rest.api.sdk.client.impl.DefaultOrsClient;
import com.open.rest.api.sdk.request.DataRequest;
import com.open.rest.api.sdk.response.OrsResponse;

import net.sf.json.JSONObject;

public abstract class AbstractServiceClient {


	protected OrsClient orsClient = null;

	/**
	 * 获取服务名称
	 * 
	 * @return
	 */
	public abstract String getGrand();

	/**
	 * 获取服务令牌
	 * 
	 * @return
	 */
	public abstract String getToken();

	/**
	 * 获取服务地址
	 * 
	 * @return
	 */
	public String getServiceUrl() {
		return CapacityConfigs.COMMON_URL + this.getGrand();
	}

	protected AbstractServiceClient() {
		this.orsClient = new DefaultOrsClient(this.getServiceUrl(), CapacityConfigs.APP_KEY, CapacityConfigs.SECRET, CapacityConfigs.FORMAT);
	}

	protected AbstractServiceClient(int connectTimeout, int readTimeout) {
		this.orsClient = new DefaultOrsClient(this.getServiceUrl(), CapacityConfigs.APP_KEY, CapacityConfigs.SECRET, CapacityConfigs.FORMAT, connectTimeout, readTimeout);
	}
	protected AbstractServiceClient(String app_key,String fromat,String common_url,String secret,String token,String grand){
		this.orsClient = new DefaultOrsClient(common_url+ grand, app_key,secret,fromat);
		
	}

	/**
	 * 执行服务，返回OrsResponse
	 * 
	 * @param data
	 *            业务数据
	 * @return
	 */
	public OrsResponse buildRequest(JSONObject data) throws OrsException {
		return buildRequest(data, null);
	}

	/**
	 * 执行服务，返回OrsResponse
	 * 
	 * @param data
	 *            业务数据
	 * @param callback
	 *            回调地址，对于有回调的服务执行此方法
	 * @return
	 */
	public OrsResponse buildRequest(JSONObject data, String callback) throws OrsException {
		DataRequest dataRequest = new DataRequest();
		if (data != null) {
			dataRequest.setData(data.toString());
		}
		dataRequest.setToken(this.getToken());
		dataRequest.setCallback(callback);
		return this.orsClient.execute(dataRequest);
	}

	/**
	 * 执行服务，返回响应JSON
	 * 
	 * @param data
	 *            业务数据
	 * @return
	 */
	public JSONObject performRequest(JSONObject data) throws OrsException {
		OrsResponse response = buildRequest(data);
		return JSONObject.fromObject(response.getResponseMsg());
	}

	/**
	 * 执行服务，返回响应JSON
	 * 
	 * @param data
	 *            业务数据
	 * @param callback
	 *            回调地址，对于有回调的服务执行此方法
	 * @return
	 */
	public JSONObject performRequest(JSONObject data, String callback) throws OrsException {
		OrsResponse response = buildRequest(data, callback);
		return JSONObject.fromObject(response.getResponseMsg());
	}


}
