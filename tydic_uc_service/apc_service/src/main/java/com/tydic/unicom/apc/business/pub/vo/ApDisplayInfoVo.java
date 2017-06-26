package com.tydic.unicom.apc.business.pub.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ApDisplayInfoVo extends BaseVo {

	private static final long serialVersionUID = 1L;

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
	private String developer_no;
	private String developer_name;
	private String goods_id;
	private String goods_name;
	/** 地区编码 */
	private String region_id;
	/** 地区名称 */
	private String region_name;
	/** 渠道编码 */
	private String chnl_code;
	/** 渠道名称 */
	private String chnl_name;
	/** 触点生效时间 */
	private String eff_date;
	/** 触点失效时间 */
	private String exp_date;

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getChnl_code() {
		return chnl_code;
	}

	public void setChnl_code(String chnl_code) {
		this.chnl_code = chnl_code;
	}
	public String getChnl_name() {
		return chnl_name;
	}

	public void setChnl_name(String chnl_name) {
		this.chnl_name = chnl_name;
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

	public String getDeveloper_no() {
		return developer_no;
	}

	public void setDeveloper_no(String developer_no) {
		this.developer_no = developer_no;
	}

	public String getDeveloper_name() {
		return developer_name;
	}

	public void setDeveloper_name(String developer_name) {
		this.developer_name = developer_name;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

}
