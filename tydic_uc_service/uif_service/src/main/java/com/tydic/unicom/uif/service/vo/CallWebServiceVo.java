package com.tydic.unicom.uif.service.vo;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午9:22:10
 * @ClassName CallWebServiceVo
 * @Description 调用webser接口入参
 * @version V1.0
 */
public class CallWebServiceVo extends BaseObject {
	
	private static final long serialVersionUID = 4140447240577935033L;
	
	private String url;
	
	private String actionName;
	
	private String xmlStr;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public String getXmlStr() {
		return xmlStr;
	}
	
	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}
	
}
