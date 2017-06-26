package com.tydic.unicom.sms;

import java.util.Date;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月24日 下午6:01:29
 * @ClassName ShortMessage
 * @Description 短消息对象
 * @version V1.0
 */
public abstract class ShortMessage<T> extends BaseObject {
	
	private static final long serialVersionUID = 7727034030433186428L;
	
	/**
	 * 短信id
	 */
	private String msgId;
	/**
	 * 短信来源
	 */
	private String msgFromSystem;
	/**
	 * 业务类型
	 */
	private String businessType;
	/**
	 * 发送者信息
	 */
	private String sender;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	
	/**
	 * 消息存活有效期
	 */
	private Date validTime;
	
	/**
	 * 消息内容
	 */
	private T msgContent;
	
	/**
	 * 目标号码
	 */
	private String desMobile;
	
	/**
	 * 获取短信内容长度
	 * 
	 * @return
	 */
	public abstract int getMsgLength();
	
	/**
	 * 获取短信类型
	 * 
	 * @return
	 */
	public abstract EnShortMessageType getMsgType();
	
	/**
	 * 获取短信内容字符串
	 * 
	 * @return
	 */
	public abstract String msgContentToString();
	
	public String getMsgId() {
		return msgId;
	}
	
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	public String getMsgFromSystem() {
		return msgFromSystem;
	}
	
	public void setMsgFromSystem(String msgFromSystem) {
		this.msgFromSystem = msgFromSystem;
	}
	
	public String getBusinessType() {
		return businessType;
	}
	
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public Date getSendTime() {
		return sendTime;
	}
	
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	public Date getValidTime() {
		return validTime;
	}
	
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	
	public T getMsgContent() {
		return msgContent;
	}
	
	public void setMsgContent(T msgContent) {
		this.msgContent = msgContent;
	}
	
	public String getDesMobile() {
		return desMobile;
	}
	
	public void setDesMobile(String desMobile) {
		this.desMobile = desMobile;
	}
	
}
