package com.tydic.unicom.crawler.dao.po;

/**
 * 区县
 * @author xx
 */
public class Crawler_Mall_UserPo extends BasePo{
	private static final long serialVersionUID = 1L;
	private String crawmu_uuid; 
	private String crawmu_name; 
	private String crawmu_pwd; 
	private String crawmu_basereqcookie; 
	private String crawmu_rescookie; 
	private String crawmu_sessioncookie;
	private String crawmu_uacname;
	private String crawmu_type;
	public String getCrawmu_uuid() {
		return crawmu_uuid;
	}
	public void setCrawmu_uuid(String crawmu_uuid) {
		this.crawmu_uuid = crawmu_uuid;
	}
	public String getCrawmu_name() {
		return crawmu_name;
	}
	public void setCrawmu_name(String crawmu_name) {
		this.crawmu_name = crawmu_name;
	}
	public String getCrawmu_pwd() {
		return crawmu_pwd;
	}
	public void setCrawmu_pwd(String crawmu_pwd) {
		this.crawmu_pwd = crawmu_pwd;
	}
	public String getCrawmu_basereqcookie() {
		return crawmu_basereqcookie;
	}
	public void setCrawmu_basereqcookie(String crawmu_basereqcookie) {
		this.crawmu_basereqcookie = crawmu_basereqcookie;
	}
	public String getCrawmu_rescookie() {
		return crawmu_rescookie;
	}
	public void setCrawmu_rescookie(String crawmu_rescookie) {
		this.crawmu_rescookie = crawmu_rescookie;
	}
	public String getCrawmu_sessioncookie() {
		return crawmu_sessioncookie;
	}
	public void setCrawmu_sessioncookie(String crawmu_sessioncookie) {
		this.crawmu_sessioncookie = crawmu_sessioncookie;
	}
	public String getCrawmu_uacname() {
		return crawmu_uacname;
	}
	public void setCrawmu_uacname(String crawmu_uacname) {
		this.crawmu_uacname = crawmu_uacname;
	}
	public String getCrawmu_type() {
		return crawmu_type;
	}
	public void setCrawmu_type(String crawmu_type) {
		this.crawmu_type = crawmu_type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
