package com.tydic.unicom.uif.service.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("uifPropertiesParamVo")
public class UifPropertiesParamVo {

	@Value("${abilityPlatForm.local_url}")
	private String localUrl;

	@Value("${abilityPlatForm.aop_url}")
	private String aopUrl;

	@Value("${abilityPlatForm.wo_url}")
	private String woUrl;

	@Value("${abilityPlatForm.new_appkey}")
	private String newAppkey;

	@Value("${abilityPlatForm.secret}")
	private String secret;

	@Value("${abilityPlatForm.local_token}")
	private String localToken;

	@Value("${abilityPlatForm.aop_token}")
	private String aopToken;

	@Value("${abilityPlatForm.wo_token}")
	private String woToken;

	@Value("${abilityPlatForm.v}")
	private String v;

	@Value("${abilityPlatForm.USS_ACTION}")
	private String ussAction;

	@Value("${abilityPlatForm.USS_USL_AJAX_HN}")
	private String ussUslAjaxHn;

	@Value("${abilityPlatForm.sf_url}")
	private String sfUrl;

	@Value("${abilityPlatForm.hn_aop_url}")
	private String hnAopUrl;

	@Value("${abilityPlatForm.hd_url}")
	private String hdUrl;

	@Value("${abilityPlatForm.hd_token}")
	private String hdToken;

	@Value("${abilityPlatForm.hd_appkey}")
	private String hdAppKey;

	@Value("${abilityPlatForm.df_url}")
	private String dfUrl;

	@Value("${abilityPlatForm.df_token}")
	private String dfToken;

	@Value("${abilityPlatForm.df_appkey}")
	private String dfAppkey;


	@Value("${abilityPlatForm.gzt_url}")
	private String gztUrl;

	@Value("${abilityPlatForm.gzt_token}")
	private String gztToken;

	//开户证件上传
	@Value("${abilityPlatForm.sendPhoto_url}")
	private String sendPhotoUrl;

	@Value("${abilityPlatForm.sendPhoto_token}")
	private String sendPhotoToken;

	@Value("${abilityPlatForm.localSf_url}")
	private String localSfUrl;

	@Value("${abilityPlatForm.localSf_token}")
	private String localSfToken;
	
	
	@Value("${abilityPlatForm.qryUserNumber_url}")
	private String qryUserNumberUrl;

	@Value("${abilityPlatForm.qryUserNumber_token}")
	private String qryUserNumberToken;
	
	
	@Value("${abilityPlatForm.sendMessage_url}")
	private String sendMessageUrl;
	
	@Value("${abilityPlatForm.sendMessage_token}")
	private String sendMessageToken;
	
	
	

	public String getLocalUrl() {
		return localUrl;
	}

	public String getAopUrl() {
		return aopUrl;
	}

	public String getWoUrl() {
		return woUrl;
	}

	public String getNewAppkey() {
		return newAppkey;
	}

	public String getSecret() {
		return secret;
	}

	public String getLocalToken() {
		return localToken;
	}

	public String getAopToken() {
		return aopToken;
	}

	public String getWoToken() {
		return woToken;
	}

	public String getV() {
		return v;
	}

	public String getUssAction() {
		return ussAction;
	}

	public String getUssUslAjaxHn() {
		return ussUslAjaxHn;
	}

	public String getSfUrl() {
		return sfUrl;
	}

	public String getHnAopUrl() {
		return hnAopUrl;
	}

	public String getHdUrl() {
		return hdUrl;
	}


	public String getHdToken() {
		return hdToken;
	}


	public String getHdAppKey() {
		return hdAppKey;
	}

	public String getDfUrl() {
		return dfUrl;
	}

	public String getDfToken() {
		return dfToken;
	}

	public String getDfAppkey() {
		return dfAppkey;
	}

	public String getGztUrl() {
		return gztUrl;
	}

	public String getGztToken() {
		return gztToken;
	}

	public String getSendPhotoUrl() {
		return sendPhotoUrl;
	}

	public String getSendPhotoToken() {
		return sendPhotoToken;
	}

	public String getLocalSfUrl() {
		return localSfUrl;
	}

	public String getLocalSfToken() {
		return localSfToken;
	}

	public String getQryUserNumberUrl() {
		return qryUserNumberUrl;
	}

	public String getQryUserNumberToken() {
		return qryUserNumberToken;
	}
	
	public String getSendMessageUrl() {
		return sendMessageUrl;
	}

	
	public String getSendMessageToken() {
		return sendMessageToken;
	}
	

}
