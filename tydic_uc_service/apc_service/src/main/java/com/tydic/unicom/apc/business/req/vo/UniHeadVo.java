package com.tydic.unicom.apc.business.req.vo;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("UNI_HEAD")
public class UniHeadVo implements Serializable{
	/** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = -3738709092590074057L;
	private String ORIG_DOMAIN;
	private String SERVICE_NAME;
	private String ACTION_CODE;
	private String TRAN_ID;
	//重庆增加下面三个字段
	private String APP_KEY;
	private String APP_PASSWORD;
	private String API_VERSION;
	private String RSP_CODE;
	private String RSP_DESC;
	
	//（广西）调外网的时候加上加密签名
	private String SIGN;
	
	@XStreamAlias("RESPONSE")
	private ResponseVo RESPONSE;
	
	public String getORIG_DOMAIN() {
		return ORIG_DOMAIN;
	}
	public void setORIG_DOMAIN(String oRIG_DOMAIN) {
		ORIG_DOMAIN = oRIG_DOMAIN;
	}
	public String getSERVICE_NAME() {
		return SERVICE_NAME;
	}
	public void setSERVICE_NAME(String sERVICE_NAME) {
		SERVICE_NAME = sERVICE_NAME;
	}
	public String getACTION_CODE() {
		return ACTION_CODE;
	}
	public void setACTION_CODE(String aCTION_CODE) {
		ACTION_CODE = aCTION_CODE;
	}
	public String getTRAN_ID() {
		return TRAN_ID;
	}
	public void setTRAN_ID(String tRAN_ID) {
		TRAN_ID = tRAN_ID;
	}
	public ResponseVo getRESPONSE() {
		return RESPONSE;
	}
	public void setRESPONSE(ResponseVo rESPONSE) {
		RESPONSE = rESPONSE;
	}
	public String getAPP_KEY() {
		return APP_KEY;
	}
	public void setAPP_KEY(String aPP_KEY) {
		APP_KEY = aPP_KEY;
	}
	public String getAPP_PASSWORD() {
		return APP_PASSWORD;
	}
	public void setAPP_PASSWORD(String aPP_PASSWORD) {
		APP_PASSWORD = aPP_PASSWORD;
	}
	public String getAPI_VERSION() {
		return API_VERSION;
	}
	public void setAPI_VERSION(String aPI_VERSION) {
		API_VERSION = aPI_VERSION;
	}
	public String getRSP_CODE() {
	    return RSP_CODE;
	}
	public void setRSP_CODE(String rSP_CODE) {
	    RSP_CODE = rSP_CODE;
	}
	public String getRSP_DESC() {
	    return RSP_DESC;
	}
	public void setRSP_DESC(String rSP_DESC) {
	    RSP_DESC = rSP_DESC;
	}
	public String getSIGN() {
		return SIGN;
	}
	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}
	
}
