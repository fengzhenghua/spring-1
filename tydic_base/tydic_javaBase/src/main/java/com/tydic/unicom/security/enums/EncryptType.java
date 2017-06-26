package com.tydic.unicom.security.enums;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月5日 下午4:27:13
 * @ClassName EncryptType
 * @Description 加密方式枚举
 * @version V1.0
 */
public enum EncryptType {
	AES_ECB_PKCS5Padding("AES/ECB/PKCS5Padding"), 
	AES_CBC_PKCS5Padding("AES/CBC/PKCS5Padding"), 
	AES_CBC_NOPadding("AES/CBC/NoPadding"), 
	AES_CBC_PKCS7Padding("AES/CBC/PKCS7Padding");
	
	private String type;
	
	private EncryptType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
