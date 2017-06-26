package com.tydic.unicom.uoc.base.uochis.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoServiceOrderDeveloperHisPo  extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String serv_order_no;
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String acc_nbr;
	private String developer_code;
	private String developer_name;
	private String develop_depart_id;
	private String develop_depart_name;
	private String develop_type;
	private String develop_target_code;
	private String develop_time;
	
	public String getDevelop_type() {
		return develop_type;
	}
	public void setDevelop_type(String develop_type) {
		this.develop_type = develop_type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getDevelop_depart_id() {
		return develop_depart_id;
	}
	public void setDevelop_depart_id(String develop_depart_id) {
		this.develop_depart_id = develop_depart_id;
	}
	public String getDevelop_depart_name() {
		return develop_depart_name;
	}
	public void setDevelop_depart_name(String develop_depart_name) {
		this.develop_depart_name = develop_depart_name;
	}
	public String getServ_order_no() {
		return serv_order_no;
	}
	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}
	public String getOfr_order_no() {
		return ofr_order_no;
	}
	public void setOfr_order_no(String ofr_order_no) {
		this.ofr_order_no = ofr_order_no;
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
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getDeveloper_code() {
		return developer_code;
	}
	public void setDeveloper_code(String developer_code) {
		this.developer_code = developer_code;
	}
	public String getDeveloper_name() {
		return developer_name;
	}
	public void setDeveloper_name(String developer_name) {
		this.developer_name = developer_name;
	}
	public String getDevelop_target_code() {
		return develop_target_code;
	}
	public void setDevelop_target_code(String develop_target_code) {
		this.develop_target_code = develop_target_code;
	}
	public String getDevelop_time() {
		return develop_time;
	}
	public void setDevelop_time(String develop_time) {
		this.develop_time = develop_time;
	}
}
