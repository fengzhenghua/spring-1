package com.tydic.unicom.uoc.service.order.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoSaleOrderEditVo extends BaseVo{
	
private static final long serialVersionUID = 1L;
	
	private String id;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String edit_type;
	private String state;
	private String edit_system;
	private String oper_no;
	private String edit_time;
	private String edit_desc;
	private String is_compress;
	private String json_url;
	private String json_content;

	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getEdit_type() {
		return edit_type;
	}
	public void setEdit_type(String edit_type) {
		this.edit_type = edit_type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIs_compress() {
		return is_compress;
	}
	public void setIs_compress(String is_compress) {
		this.is_compress = is_compress;
	}
	public String getJson_content() {
		return json_content;
	}
	public void setJson_content(String json_content) {
		this.json_content = json_content;
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
	public String getEdit_system() {
		return edit_system;
	}
	public void setEdit_system(String edit_system) {
		this.edit_system = edit_system;
	}
	public String getOper_no() {
		return oper_no;
	}
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	public String getEdit_time() {
		return edit_time;
	}
	public void setEdit_time(String edit_time) {
		this.edit_time = edit_time;
	}
	public String getEdit_desc() {
		return edit_desc;
	}
	public void setEdit_desc(String edit_desc) {
		this.edit_desc = edit_desc;
	}
	public String getJson_url() {
		return json_url;
	}
	public void setJson_url(String json_url) {
		this.json_url = json_url;
	}
}
