package com.tydic.unicom.ugc.base.dataBase.po;

import com.tydic.unicom.vdsBase.po.BasePo;

public class GoodsSkuPo extends BasePo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String goods_id;
	private String sku_type;
	private String sku_id;
	private String bind_flag;
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
	public String getSku_type() {
		return sku_type;
	}
	public void setSku_type(String sku_type) {
		this.sku_type = sku_type;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getBind_flag() {
		return bind_flag;
	}
	public void setBind_flag(String bind_flag) {
		this.bind_flag = bind_flag;
	}
	
	

}
