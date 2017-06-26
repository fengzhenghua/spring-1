package com.tydic.unicom.upc.vo.code;


import java.util.Date;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoBusiVo extends BaseVo {

	private static final long serialVersionUID = 5632363757782879065L;

	private String busi_id;
	private String busi_name;
	private String state;
	private String rsa_private_key;
	private String rsa_public_key;
	private String sign_key;
	private Date create_time;
	private Date update_time;
	private String cashier_theme;
	private String contact_person;
	private String contact_tel;
	private String contact_email;
	private String notify_url;
	private String encrypt; //判断时候需要对数据进行加密 减密（1 加减密  0不需要加减密）
	private String aopname;// 重庆能力平台必要字段（转发至相应的接口）
	
	
	public String getAopname() {
		return aopname;
	}
	public void setAopname(String aopname) {
		this.aopname = aopname;
	}
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	public String getBusi_id() {
		return busi_id;
	}
	public void setBusi_id(String busi_id) {
		this.busi_id = busi_id;
	}
	public String getBusi_name() {
		return busi_name;
	}
	public void setBusi_name(String busi_name) {
		this.busi_name = busi_name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRsa_private_key() {
		return rsa_private_key;
	}
	public void setRsa_private_key(String rsa_private_key) {
		this.rsa_private_key = rsa_private_key;
	}
	public String getRsa_public_key() {
		return rsa_public_key;
	}
	public void setRsa_public_key(String rsa_public_key) {
		this.rsa_public_key = rsa_public_key;
	}
	public String getSign_key() {
		return sign_key;
	}
	public void setSign_key(String sign_key) {
		this.sign_key = sign_key;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getCashier_theme() {
		return cashier_theme;
	}
	public void setCashier_theme(String cashier_theme) {
		this.cashier_theme = cashier_theme;
	}
	public String getContact_person() {
		return contact_person;
	}
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}
	public String getContact_tel() {
		return contact_tel;
	}
	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}
	public String getContact_email() {
		return contact_email;
	}
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
	
}
