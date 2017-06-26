package com.tydic.unicom.ugc.base.dataBase.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class GoodsRolePo  extends BasePo{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String goods_id;
	private String role_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	
}
