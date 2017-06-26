package com.tydic.unicom.uoc.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoIDCardVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String jsession_id;
	
	private String id_seq; // 序列
	private String cert_no; // 身份证
	private String id_addr; // 证件地址
	private String id_police; // 签发地址
	private String cust_name; // 客户姓名
	private String custom_sex; // 客户性别
	private String custom_birth; // 出生日期
	private String start_date; // 生效时间
	private String custom_nation; // 民族
	private String end_date; // 实效时间
	private String photo_code; // 照片编码
	private String order_id; // 订单id
	
	private String idcard_path;// 图片路径
	
	private String pic_path;// 身份证图片路径
	
	private String oper_no;
	private String uni_oper_no; // 录入统一工号
	
	private String serv_order_no;
	
	
	public String getJsession_id() {
		return jsession_id;
	}

	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}


	public String getId_addr() {
		return id_addr;
	}
	
	public void setId_addr(String id_addr) {
		this.id_addr = id_addr;
	}
	
	public String getId_police() {
		return id_police;
	}
	
	public void setId_police(String id_police) {
		this.id_police = id_police;
	}
	

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCustom_sex() {
		return custom_sex;
	}
	
	public void setCustom_sex(String custom_sex) {
		this.custom_sex = custom_sex;
	}
	
	public String getCustom_birth() {
		return custom_birth;
	}
	
	public void setCustom_birth(String custom_birth) {
		this.custom_birth = custom_birth;
	}
	
	public String getStart_date() {
		return start_date;
	}
	
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	public String getCustom_nation() {
		return custom_nation;
	}
	
	public void setCustom_nation(String custom_nation) {
		this.custom_nation = custom_nation;
	}
	
	public String getEnd_date() {
		return end_date;
	}
	
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public String getPhoto_code() {
		return photo_code;
	}
	
	public void setPhoto_code(String photo_code) {
		this.photo_code = photo_code;
	}
	
	public String getId_seq() {
		return id_seq;
	}
	
	public void setId_seq(String id_seq) {
		this.id_seq = id_seq;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getIdcard_path() {
		return idcard_path;
	}
	
	public void setIdcard_path(String idcard_path) {
		this.idcard_path = idcard_path;
	}
	
	public String getPic_path() {
		return pic_path;
	}
	
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	public String getOper_no() {
		return oper_no;
	}

	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}

	public String getUni_oper_no() {
		return uni_oper_no;
	}

	public void setUni_oper_no(String uni_oper_no) {
		this.uni_oper_no = uni_oper_no;
	}

	public String getServ_order_no() {
		return serv_order_no;
	}

	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}
	
}
