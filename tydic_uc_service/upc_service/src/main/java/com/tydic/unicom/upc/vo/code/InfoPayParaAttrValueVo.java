package com.tydic.unicom.upc.vo.code;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoPayParaAttrValueVo extends BaseVo {

	private static final long serialVersionUID = -1025254666466586338L;
	//微信的参数
	private String appid;
	private String mchid;
	private String appsecret;
	private String signkey;
	private String certname;
	private String certpassword;
	
	//支付宝的参数
	private String privateKey;
	private String publicKey;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getSignkey() {
		return signkey;
	}
	public void setSignkey(String signkey) {
		this.signkey = signkey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getCertname() {
		return certname;
	}
	public void setCertname(String certname) {
		this.certname = certname;
	}
	public String getCertpassword() {
		return certpassword;
	}
	public void setCertpassword(String certpassword) {
		this.certpassword = certpassword;
	}
	
	
}
