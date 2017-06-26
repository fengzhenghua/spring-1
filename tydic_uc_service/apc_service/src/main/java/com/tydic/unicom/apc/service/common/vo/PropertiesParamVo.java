package com.tydic.unicom.apc.service.common.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("propertiesParamVo")
public class PropertiesParamVo {
	//本地临时文件存储路径
		/*@Value("${fileService.localTempFilePath}")
		private String localTempFilePath;*/
		//文件服务器地址
		@Value("${fileService.fileServicePath}")
		private String fileServicePath;
	@Value("${abilityPlatForm.uoc_url}")
	private String uocUrl;
	
	@Value("${abilityPlatForm.uoc_token}")
	private String uocToken;

	@Value("${abilityPlatForm.urc_url}")
	private String urcUrl;
	
	@Value("${abilityPlatForm.urc_token}")
	private String urcToken;
	
	@Value("${abilityPlatForm.ugc_url}")
	private String ugcUrl;
	
	@Value("${abilityPlatForm.ugc_token}")
	private String ugcToken;
	
	@Value("${abilityPlatForm.uac_url}")
	private String uacUrl;
	
	@Value("${abilityPlatForm.uac_token}")
	private String uacToken;
	
	@Value("${abilityPlatForm.new_appkey}")
	private String newAppkey;
	
	@Value("${abilityPlatForm.secret}")
	private String secret;
	
	@Value("${abilityPlatForm.uif_url}")
	private String uifUrl;
	
	@Value("${abilityPlatForm.uif_token}")
	private String uifToken;
	
	@Value("${oppo.picture.path}")
	private String oppoPicPath;//存放OPPO图片和视频的路径
	
	@Value("${oppo.picture.http}")
	private String oppoPicHttp;//存放OPPO图片和视频的HTTP路径
	
	@Value("${oppo.live.sim}")//活体校验相似度
	private String oppoLiveSim;
	
	@Value("${face_appid}")
	private String faceAppid;
	
	@Value("${face_bucket}")
	private String faceBucket;

	/**新增**/
	@Value("${pay_expire}")
	private String payExpire;
	@Value("${send_ip}")
	private String sendIp;
	@Value("${FILE_DOWNLOAD_URL}")
	private String fileDownLoadUrl;
	@Value("${JSAPI_URL}")
	private String jsApiUrl;
	@Value("${ACCESS_TOKEN_URL_2}")
	private String accessTokenUrl2;
	@Value("${REFUND_API}")
	private String refundApi;
	@Value("${PAY_QUERY_API}")
	private String payQueryApi;
	@Value("${notify_url_pc}")
	private String notifyUrlPc;
	@Value("${notify_url_mobile}")
	private String notifyUrlMobile;
	@Value("${UNIFIED_ORDER_API}")
	private String unifiedOrderApi;
	@Value("${uss_url}")
	private String uss_url;
	@Value("${min_sim}")
	private String min_sim;
	@Value("${max_sim}")
	private String max_sim;
	@Value("${callback_url}")
	private String callback_url;
	
	/*public String getLocalTempFilePath() {
		return localTempFilePath;
	}*/

	
	public String getUss_url() {
		return uss_url;
	}

	public String getCallback_url() {
		return callback_url;
	}

	public String getMin_sim() {
		return min_sim;
	}

	public String getMax_sim() {
		return max_sim;
	}

	public String getFileServicePath() {
		return fileServicePath;
	}

	public String getUnifiedOrderApi() {
		return unifiedOrderApi;
	}

	public String getNotifyUrlPc() {
		return notifyUrlPc;
	}

	public String getNotifyUrlMobile() {
		return notifyUrlMobile;
	}

	public String getPayQueryApi() {
		return payQueryApi;
	}

	public String getRefundApi() {
		return refundApi;
	}

	public String getAccessTokenUrl2() {
		return accessTokenUrl2;
	}

	public String getJsApiUrl() {
		return jsApiUrl;
	}

	public String getFileDownLoadUrl() {
		return fileDownLoadUrl;
	}

	public String getPayExpire() {
		return payExpire;
	}

	public String getSendIp() {
		return sendIp;
	}

	public String getFaceAppid() {
		return faceAppid;
	}

	public String getFaceBucket() {
		return faceBucket;
	}

	public String getOppoLiveSim() {
		return oppoLiveSim;
	}

	public String getOppoPicHttp() {
		return oppoPicHttp;
	}

	public String getOppoPicPath() {
		return oppoPicPath;
	}

	public String getUocUrl() {
		return uocUrl;
	}	

	public String getUocToken() {
		return uocToken;
	}

	public String getUrcUrl() {
		return urcUrl;
	}

	public String getUrcToken() {
		return urcToken;
	}

	public String getUgcUrl() {
		return ugcUrl;
	}

	public String getUgcToken() {
		return ugcToken;
	}

	public String getUacUrl() {
		return uacUrl;
	}

	public String getUacToken() {
		return uacToken;
	}

	public String getNewAppkey() {
		return newAppkey;
	}

	public String getSecret() {
		return secret;
	}

	public String getUifUrl() {
		return uifUrl;
	}

	public String getUifToken() {
		return uifToken;
	}

	

}
