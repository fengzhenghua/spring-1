package com.tydic.unicom.uif.service.impl.ablit.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.open.rest.api.sdk.client.OrsClient;
import com.open.rest.api.sdk.client.OrsException;
import com.open.rest.api.sdk.client.impl.DefaultOrsClient;
import com.open.rest.api.sdk.request.DataRequest;
import com.open.rest.api.sdk.response.OrsResponse;
import com.tydic.unicom.uif.service.interfaces.IAbilitProvider;
import com.tydic.unicom.uif.service.vo.CallLocalAbilitPlatVo;
import com.tydic.unicom.uif.service.vo.TapServiceVo;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午6:47:09
 * @ClassName CallLocalAbilityPlatProvider
 * @Description 调用本地能力平台
 * @version V1.0
 */
public class CallLocalAbilityPlatProvider implements IAbilitProvider<CallLocalAbilitPlatVo> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private OrsClient orsClient;
	
	@Override
	public String callAblit(CallLocalAbilitPlatVo callVo) {
		OrsResponse response = buildRequest(callVo);
		logger.info("调用本地能力平台返回报文"+response.getResponseMsg());
		return JSON.toJSON(response.getResponseMsg()).toString();
	}
	
	/**
	 * 
	 * @Method: buildRequest
	 * @Description: 能力平台调用
	 */
	public OrsResponse buildRequest(CallLocalAbilitPlatVo callVo) throws OrsException {
		logger.info("调用本地能力平台请求报文"+callVo);
		DataRequest dataRequest = new DataRequest();
		
		TapServiceVo tapService = callVo.getTapService();
		
		dataRequest.setData(callVo.getInfoJson());
		dataRequest.setToken(tapService.getToken());
		Integer connectTimeout = tapService.getConnectTimeout();
		Integer readTimeout = tapService.getReadTimeout();
		if (StringUtils.isEmpty(connectTimeout) || StringUtils.isEmpty(readTimeout)) {
			orsClient = new DefaultOrsClient(tapService.getCommUrl(), tapService.getAppKey(), tapService.getSecret());
		} else {
			orsClient = new DefaultOrsClient(tapService.getCommUrl(), tapService.getAppKey(), tapService.getSecret(),
			        connectTimeout, readTimeout);
		}
		return orsClient.execute(dataRequest);
	}
}
