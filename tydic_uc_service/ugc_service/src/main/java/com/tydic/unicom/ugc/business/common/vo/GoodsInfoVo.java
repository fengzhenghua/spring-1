package com.tydic.unicom.ugc.business.common.vo;

import com.tydic.unicom.vdsBase.po.BasePo;

public class GoodsInfoVo extends BasePo{
	
	private static final long serialVersionUID = 1L;
	
	private String goods_id;
	private String goods_name;
	private String goods_desc;
	private String goods_type;
	//private String role_id;
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
	
	
}
