package com.tydic.unicom.uoc.base.uochis.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class InfoServiceOrderCollectionHisPo extends BasePo{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String serv_order_no;
	private String ofr_order_no;
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String account_id;
	private String bank_code;
	private String bank_name;
	private String bank_account;
	private String bank_area_code;
	private String bank_area_name;
	private String collection_type;
	private String collection_sn;
	private String save_type;
	private String upper_bank_code;
	
	public String getCollection_type() {
		return collection_type;
	}
	public void setCollection_type(String collection_type) {
		this.collection_type = collection_type;
	}
	public String getSave_type() {
		return save_type;
	}
	public void setSave_type(String save_type) {
		this.save_type = save_type;
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
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public String getBank_area_code() {
		return bank_area_code;
	}
	public void setBank_area_code(String bank_area_code) {
		this.bank_area_code = bank_area_code;
	}
	public String getBank_area_name() {
		return bank_area_name;
	}
	public void setBank_area_name(String bank_area_name) {
		this.bank_area_name = bank_area_name;
	}
	public String getCollection_sn() {
		return collection_sn;
	}
	public void setCollection_sn(String collection_sn) {
		this.collection_sn = collection_sn;
	}
	public String getUpper_bank_code() {
		return upper_bank_code;
	}
	public void setUpper_bank_code(String upper_bank_code) {
		this.upper_bank_code = upper_bank_code;
	}
}
