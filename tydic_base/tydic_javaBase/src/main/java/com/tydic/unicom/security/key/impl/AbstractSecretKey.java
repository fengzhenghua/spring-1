package com.tydic.unicom.security.key.impl;

import java.util.Date;

import com.tydic.unicom.security.BaseObject;
import com.tydic.unicom.security.key.ISecretKey;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月5日 下午4:42:32
 * @ClassName AbstractSecretKey
 * @Description 抽象密钥
 * @version V1.0
 */
public abstract class AbstractSecretKey extends BaseObject implements ISecretKey {
	
	private static final long serialVersionUID = -4546975423630471205L;
	
	private Date generatedTime;
	
	@Override
	public Date getGeneratedTime() {
		return generatedTime;
	}
	
	protected void setGeneratedTime(Date generatedTime) {
		this.generatedTime = generatedTime;
	}
	
	public AbstractSecretKey() {
		this.generatedTime = new Date();
	}
}
