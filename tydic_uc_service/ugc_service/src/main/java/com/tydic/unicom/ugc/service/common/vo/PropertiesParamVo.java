package com.tydic.unicom.ugc.service.common.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("propertiesParamVo")
public class PropertiesParamVo {

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

}
