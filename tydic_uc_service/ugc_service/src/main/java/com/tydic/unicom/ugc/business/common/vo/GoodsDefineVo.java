package com.tydic.unicom.ugc.business.common.vo;

import com.tydic.unicom.security.BaseObject;

public class GoodsDefineVo extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String goods_id;
	private String goods_desc;
	private String goods_type;
	private String goods_name;
	private String province_code;
	private String area_code;
	private String state;
	private String create_staff;
	private String create_time;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_desc() {
		return goods_desc;
	}

	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
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

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

}
