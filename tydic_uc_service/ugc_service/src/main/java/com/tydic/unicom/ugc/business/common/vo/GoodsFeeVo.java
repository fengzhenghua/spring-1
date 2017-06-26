package com.tydic.unicom.ugc.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class GoodsFeeVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String goods_id;
	private String fee_item_code;
	private String fee_item_name;
	private String fee_item_type;
	private String total_fee;
	private String max_discount_fee;
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
	public String getFee_item_code() {
		return fee_item_code;
	}
	public void setFee_item_code(String fee_item_code) {
		this.fee_item_code = fee_item_code;
	}
	public String getFee_item_name() {
		return fee_item_name;
	}
	public void setFee_item_name(String fee_item_name) {
		this.fee_item_name = fee_item_name;
	}
	public String getFee_item_type() {
		return fee_item_type;
	}
	public void setFee_item_type(String fee_item_type) {
		this.fee_item_type = fee_item_type;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getMax_discount_fee() {
		return max_discount_fee;
	}
	public void setMax_discount_fee(String max_discount_fee) {
		this.max_discount_fee = max_discount_fee;
	}
	
	
	
}
