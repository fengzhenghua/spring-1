package com.tydic.unicom.apc.business.pub.vo;

import java.util.List;

import com.tydic.unicom.webUtil.BaseVo;

public class TariffInfoVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tariff_id;
	private String tariff_name;
	private String tariff_desc;
	private String fee_item_code;
	private String fee_item_name;
	private String fee_item_type;
	private String total_fee;
	private String max_discount_fee;
	private String goods_id;
	private String pic_url;
	private String tele_type;
	private String pay_flag;
	private String oper_code;
	private String activity_id;
	private String product_id;
	/**
	 * 可选靓号标识：0不可选靓号、1可选靓号
	 */
	private String good_num_flag;
	/**
	 * 收货区域标识：0本省、1全国
	 */
	private String receive_area_flag;
	/**
	 * 普通快递发货标识：0不支持、1支持
	 */
	private String send_type0_flag;
	/**
	 * 货到付款发货标识：0不支持、1支持
	 */
	private String send_type1_flag;
	/**
	 * 自提发货标识：0不支持、1支持
	 */
	private String send_type2_flag;
	
	
	private TariffShowDetailVo tariff_show_detail;
	private List<FeeInfoVo> fee_list;
	public String getTariff_id() {
		return tariff_id;
	}
	public void setTariff_id(String tariff_id) {
		this.tariff_id = tariff_id;
	}
	public String getTariff_name() {
		return tariff_name;
	}
	public void setTariff_name(String tariff_name) {
		this.tariff_name = tariff_name;
	}
	public String getTariff_desc() {
		return tariff_desc;
	}
	public void setTariff_desc(String tariff_desc) {
		this.tariff_desc = tariff_desc;
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
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getTele_type() {
		return tele_type;
	}
	public void setTele_type(String tele_type) {
		this.tele_type = tele_type;
	}
	public String getPay_flag() {
		return pay_flag;
	}
	public void setPay_flag(String pay_flag) {
		this.pay_flag = pay_flag;
	}
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public TariffShowDetailVo getTariff_show_detail() {
		return tariff_show_detail;
	}
	public void setTariff_show_detail(TariffShowDetailVo tariff_show_detail) {
		this.tariff_show_detail = tariff_show_detail;
	}
	public List<FeeInfoVo> getFee_list() {
		return fee_list;
	}
	public void setFee_list(List<FeeInfoVo> fee_list) {
		this.fee_list = fee_list;
	}
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	public String getGood_num_flag() {
		return good_num_flag;
	}
	
	public void setGood_num_flag(String good_num_flag) {
		this.good_num_flag = good_num_flag;
	}
	
	public String getReceive_area_flag() {
		return receive_area_flag;
	}
	
	public void setReceive_area_flag(String receive_area_flag) {
		this.receive_area_flag = receive_area_flag;
	}
	
	public String getSend_type0_flag() {
		return send_type0_flag;
	}
	
	public void setSend_type0_flag(String send_type0_flag) {
		this.send_type0_flag = send_type0_flag;
	}
	
	public String getSend_type1_flag() {
		return send_type1_flag;
	}
	
	public void setSend_type1_flag(String send_type1_flag) {
		this.send_type1_flag = send_type1_flag;
	}
	
	public String getSend_type2_flag() {
		return send_type2_flag;
	}
	
	public void setSend_type2_flag(String send_type2_flag) {
		this.send_type2_flag = send_type2_flag;
	}

	
}
