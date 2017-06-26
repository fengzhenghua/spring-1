package com.tydic.unicom.ugc.service.common.impl;

import net.sf.json.JSONObject;

import com.open.rest.api.sdk.client.OrsClient;
import com.open.rest.api.sdk.client.OrsException;
import com.open.rest.api.sdk.client.impl.DefaultOrsClient;
import com.open.rest.api.sdk.request.DataRequest;
import com.open.rest.api.sdk.response.OrsResponse;

public class TapServiceClient {
	private OrsClient orsClient ;

	/**
	 * ??з??????????JSON
	 * 
	 * @param data
	 *            ??????
	 * @return
	 */
	public JSONObject performRequest(JSONObject data) throws OrsException {
		OrsResponse response = buildRequest(data);
		return JSONObject.fromObject(response.getResponseMsg());
	}
	
	/**
	 * ??з???????OrsResponse
	 * 
	 * @param data
	 *            ??????
	 * @return
	 */
	public OrsResponse buildRequest(JSONObject data) throws OrsException {
		return buildRequest(data, null);
	}
	
	/**
	 * ??з???????OrsResponse
	 * 
	 * @param data
	 *            ??????
	 * @param callback
	 *            ?????????????л?????????д????
	 * @return
	 */
	public OrsResponse buildRequest(JSONObject data, String callback) throws OrsException {
		DataRequest dataRequest = new DataRequest();
		
		if (data != null) {
			String token = "";
			String serviceUrl="";
			String appKey="";
			String secret="";
			String commUrl="";
			String serviceName="";
			Integer connectTimeout=null;
			Integer readTimeout=null;
			
			JSONObject tapService = data.getJSONObject("TapSerivce");//???????????????JSON
			token = tapService.getString("TOKEN");
			commUrl = tapService.getString("COMMON_URL");
			//serviceName = tapService.getString("SERIVCE_NAME");
			appKey = tapService.getString("APP_KEY");
			secret = tapService.getString("SECRET");
			connectTimeout = tapService.getInt("CONNECT_TIMEOUT");//??λ???? ????д????10000
			readTimeout = tapService.getInt("READ_TIMEOUT");//??λ???? ????д????40000
			serviceUrl= commUrl;
			
			data.remove("TapSerivce");
			dataRequest.setData(data.toString());
			dataRequest.setToken(token);
			if (connectTimeout==null || readTimeout==null){
				orsClient = new DefaultOrsClient(serviceUrl, appKey, secret);
			}
			else {
				orsClient = new DefaultOrsClient(serviceUrl, appKey, secret, connectTimeout, readTimeout);
			}
		}
		dataRequest.setCallback(callback);
		return orsClient.execute(dataRequest);
	}


}
