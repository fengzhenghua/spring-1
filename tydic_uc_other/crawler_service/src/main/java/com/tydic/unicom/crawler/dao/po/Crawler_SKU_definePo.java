package com.tydic.unicom.crawler.dao.po;

public class Crawler_SKU_definePo extends BasePo{
	private static final long serialVersionUID = 1L;
	private String cra_sku_uuid;
	private String cra_sku_name;
	private String sku_id;
	private String cra_sku_stats;
	private String oper_code;
	
	
	
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public String getCra_sku_name() {
		return cra_sku_name;
	}
	public void setCra_sku_name(String cra_sku_name) {
		this.cra_sku_name = cra_sku_name;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getCra_sku_uuid() {
		return cra_sku_uuid;
	}
	public void setCra_sku_uuid(String cra_sku_uuid) {
		this.cra_sku_uuid = cra_sku_uuid;
	}
	public String getCra_sku_stats() {
		return cra_sku_stats;
	}
	public void setCra_sku_stats(String cra_sku_stats) {
		this.cra_sku_stats = cra_sku_stats;
	}
	
	
}
