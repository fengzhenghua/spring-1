package com.tydic.unicom.ugc.base.dataBase.po;

import com.tydic.unicom.vdsBase.po.BasePo;
/**
 * 供商品信息查询用
 * @author Administrator
 *
 */
public class GoodsInfo extends BasePo{

	private static final long serialVersionUID = 1L;

	private String province_code;
	private String area_code;
	private String role_id;

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
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

}
