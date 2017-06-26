package com.tydic.unicom.upc.web.vo;

import java.util.Map;

public class BusiReqDataVo {

	private String busiId;
	private Map<String, Object> contentMap;
	private boolean isSign;
	public String getBusiId() {
		return busiId;
	}
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}
	public Map<String, Object> getContentMap() {
		return contentMap;
	}
	public void setContentMap(Map<String, Object> contentMap) {
		this.contentMap = contentMap;
	}
	public boolean isSign() {
		return isSign;
	}
	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}
}
