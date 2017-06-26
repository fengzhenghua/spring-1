package com.tydic.unicom.apc.business.pub.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ApDefineVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private String jsession_id;
	private String ap_id;
	private String ap_name;
	private String ap_desc;
	private String ap_url;
	private String province_code;
	private String area_code;
	private String state;
	private String bind_oper;
	private String create_staff;
	private String create_time;
	private String region;
	private String chnl_code;
	private String eff_date;
	private String exp_date;
	private String ap_type;
	
	@Override
	public String toString() {
		return "ApDefineVo [jsession_id=" + jsession_id + ", ap_id=" + ap_id + ", ap_name=" + ap_name + ", ap_desc="
				+ ap_desc + ", ap_url=" + ap_url + ", province_code=" + province_code + ", area_code=" + area_code
				+ ", state=" + state + ", bind_oper=" + bind_oper + ", create_staff=" + create_staff + ", create_time="
				+ create_time + ", region=" + region + ", chnl_code=" + chnl_code + ", eff_date=" + eff_date
				+ ", exp_date=" + exp_date + ", ap_type=" + ap_type + "]";
	}
	public String getAp_type() {
		return ap_type;
	}
	public void setAp_type(String ap_type) {
		this.ap_type = ap_type;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getChnl_code() {
		return chnl_code;
	}
	public void setChnl_code(String chnl_code) {
		this.chnl_code = chnl_code;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getExp_date() {
		return exp_date;
	}
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
	public String getAp_id() {
		return ap_id;
	}
	public void setAp_id(String ap_id) {
		this.ap_id = ap_id;
	}
	public String getAp_name() {
		return ap_name;
	}
	public void setAp_name(String ap_name) {
		this.ap_name = ap_name;
	}
	public String getAp_desc() {
		return ap_desc;
	}
	public void setAp_desc(String ap_desc) {
		this.ap_desc = ap_desc;
	}
	public String getAp_url() {
		return ap_url;
	}
	public void setAp_url(String ap_url) {
		this.ap_url = ap_url;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBind_oper() {
		return bind_oper;
	}
	public void setBind_oper(String bind_oper) {
		this.bind_oper = bind_oper;
	}
	public String getCreate_staff() {
		return create_staff;
	}
	public void setCreate_staff(String create_staff) {
		this.create_staff = create_staff;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
