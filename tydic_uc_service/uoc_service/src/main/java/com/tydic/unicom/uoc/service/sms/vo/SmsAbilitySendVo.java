package com.tydic.unicom.uoc.service.sms.vo;

import com.tydic.unicom.security.BaseObject;

public class SmsAbilitySendVo extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String appKey;
	private String mobile;
	private String content;
	private String subPort;
	private String sendTime;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubPort() {
		return subPort;
	}

	public void setSubPort(String subPort) {
		this.subPort = subPort;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
