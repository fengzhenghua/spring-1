package com.tydic.unicom.uoc.service.order.his.vo;

import com.tydic.unicom.service.ecaop.annotation.EcaopField;
import com.tydic.unicom.service.ecaop.annotation.EcaopField.EcaopFieldType;
import com.tydic.unicom.service.ecaop.vo.BaseResponseVo;
import com.tydic.unicom.webUtil.BaseVo;

public class InfoServiceOrderAttrHisVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EcaopField(type = EcaopFieldType.INT)
	private int id;
	@EcaopField(type = EcaopFieldType.STRING)
	private String serv_order_no;
	@EcaopField(type = EcaopFieldType.STRING)
	private String sale_order_no;
	@EcaopField(type = EcaopFieldType.STRING)
	private String province_code;
	@EcaopField(type = EcaopFieldType.STRING)
	private String area_code;
	@EcaopField(type = EcaopFieldType.STRING)
	private String attr_code;
	@EcaopField(type = EcaopFieldType.INT)
	private int attr_type;
	@EcaopField(type = EcaopFieldType.STRING)
	private String attr_after_value;
	@EcaopField(type = EcaopFieldType.STRING)
	private String attr_before_value;
	@EcaopField(type = EcaopFieldType.INT)
	private int curr_seq;
	@EcaopField(type = EcaopFieldType.INT)
	private int upper_seq;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServ_order_no() {
		return serv_order_no;
	}
	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
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
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public int getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(int attr_type) {
		this.attr_type = attr_type;
	}
	public String getAttr_after_value() {
		return attr_after_value;
	}
	public void setAttr_after_value(String attr_after_value) {
		this.attr_after_value = attr_after_value;
	}
	public String getAttr_before_value() {
		return attr_before_value;
	}
	public void setAttr_before_value(String attr_before_value) {
		this.attr_before_value = attr_before_value;
	}
	public int getCurr_seq() {
		return curr_seq;
	}
	public void setCurr_seq(int curr_seq) {
		this.curr_seq = curr_seq;
	}
	public int getUpper_seq() {
		return upper_seq;
	}
	public void setUpper_seq(int upper_seq) {
		this.upper_seq = upper_seq;
	}
	
}
