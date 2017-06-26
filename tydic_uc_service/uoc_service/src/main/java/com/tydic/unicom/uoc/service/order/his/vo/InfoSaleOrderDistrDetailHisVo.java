package com.tydic.unicom.uoc.service.order.his.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoSaleOrderDistrDetailHisVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String sale_order_no;
	private String province_code;
	private String part_month;
	private String area_code;
	private String article_code;
	private String article_imsi;
	private String article_name;
	private int article_num;
	private String article_desc;

	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSale_order_no() {
		return sale_order_no;
	}
	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getArticle_code() {
		return article_code;
	}
	public void setArticle_code(String article_code) {
		this.article_code = article_code;
	}
	public String getArticle_imsi() {
		return article_imsi;
	}
	public void setArticle_imsi(String article_imsi) {
		this.article_imsi = article_imsi;
	}
	public String getArticle_name() {
		return article_name;
	}
	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}
	public int getArticle_num() {
		return article_num;
	}
	public void setArticle_num(int article_num) {
		this.article_num = article_num;
	}
	public String getArticle_desc() {
		return article_desc;
	}
	public void setArticle_desc(String article_desc) {
		this.article_desc = article_desc;
	}
}
