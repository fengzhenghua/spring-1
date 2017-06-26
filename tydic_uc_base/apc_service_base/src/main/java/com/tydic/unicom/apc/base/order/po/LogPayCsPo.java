/**
 * @ProjectName: uss_service_order
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author Michale
 * @date: 2014年9月27日 下午2:51:57
 * @Title: InfoOrderFee.java
 * @Package com.tydic.unicom.order.po
 * @Description: TODO
 */
package com.tydic.unicom.apc.base.order.po;

import com.tydic.unicom.vdsBase.po.BasePo;

/**
 * <p>
 * </p>
 * 
 * @author hangweixing 2014年9月27日 下午2:51:57
 * @ClassName InfoOrderFee
 * @Description TODO 订单费用汇总信息po
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月27日
 * @modify by reason:{方法名}:{原因}
 */
public class LogPayCsPo extends BasePo {

	private static final long serialVersionUID = 1L;
	/** 常量------------------------------------------------------------------ */
	/* 订单ID */
	private String pay_cs_id;
	
	private String oper_sn;
	/* 支付方式 */
	private String tele_type;
	/* 总费用 */
	private String device_number;
	/* 总减免金额 */
	private String pay_type;
	/*字典值说明*/
	private String code_name;
	/* 总实收费用 */
	private String cs_order_id;
	/* 是否需要发票 */
	private String total_fee;

	private String discount_fee;
	
	private String payed_fee;
	private String invoice_flag;
	private String flag;
	private String oper_id;
	private String create_date;
	private String end_date;
	
	private String pay_commission_fee_type;
	private String commission_fee;
	private String agent_pay_fee;
	private String app_pay_fee;
	private String serv_code;
	private String serv_order_no;
	//add by fudh
	private String oper_id_ab;
	
	private String oper_name;
	private String area_id;
	private String area_name;
	private String any_dog1;
	private String any_dog2;
	private String any_dog3;
	private String any_dog4;
	private String any_dog5;


	private String mcht_flag;
	private String qry_date_type;
	
	
	public String getMcht_flag() {
		return mcht_flag;
	}
	public void setMcht_flag(String mcht_flag) {
		this.mcht_flag = mcht_flag;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getQry_date_type() {
		return qry_date_type;
	}
	public void setQry_date_type(String qry_date_type) {
		this.qry_date_type = qry_date_type;
	}
	public String getPay_cs_id() {
		return pay_cs_id;
	}
	public void setPay_cs_id(String pay_cs_id) {
		this.pay_cs_id = pay_cs_id;
	}
	public String getTele_type() {
		return tele_type;
	}
	public void setTele_type(String tele_type) {
		this.tele_type = tele_type;
	}
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getCs_order_id() {
		return cs_order_id;
	}
	public void setCs_order_id(String cs_order_id) {
		this.cs_order_id = cs_order_id;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(String discount_fee) {
		this.discount_fee = discount_fee;
	}
	public String getPayed_fee() {
		return payed_fee;
	}
	public void setPayed_fee(String payed_fee) {
		this.payed_fee = payed_fee;
	}
	public String getInvoice_flag() {
		return invoice_flag;
	}
	public void setInvoice_flag(String invoice_flag) {
		this.invoice_flag = invoice_flag;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getPay_commission_fee_type() {
		return pay_commission_fee_type;
	}
	public void setPay_commission_fee_type(String pay_commission_fee_type) {
		this.pay_commission_fee_type = pay_commission_fee_type;
	}
	public String getCommission_fee() {
		return commission_fee;
	}
	public void setCommission_fee(String commission_fee) {
		this.commission_fee = commission_fee;
	}
	public String getAgent_pay_fee() {
		return agent_pay_fee;
	}
	public void setAgent_pay_fee(String agent_pay_fee) {
		this.agent_pay_fee = agent_pay_fee;
	}
	public String getApp_pay_fee() {
		return app_pay_fee;
	}
	public void setApp_pay_fee(String app_pay_fee) {
		this.app_pay_fee = app_pay_fee;
	}
	public String getOper_sn() {
		return oper_sn;
	}
	public void setOper_sn(String oper_sn) {
		this.oper_sn = oper_sn;
	}
	public String getOper_id_ab() {
		return oper_id_ab;
	}
	public void setOper_id_ab(String oper_id_ab) {
		this.oper_id_ab = oper_id_ab;
	}
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getAny_dog1() {
		return any_dog1;
	}
	public void setAny_dog1(String any_dog1) {
		this.any_dog1 = any_dog1;
	}
	public String getAny_dog2() {
		return any_dog2;
	}
	public void setAny_dog2(String any_dog2) {
		this.any_dog2 = any_dog2;
	}
	public String getAny_dog3() {
		return any_dog3;
	}
	public void setAny_dog3(String any_dog3) {
		this.any_dog3 = any_dog3;
	}
	public String getAny_dog4() {
		return any_dog4;
	}
	public void setAny_dog4(String any_dog4) {
		this.any_dog4 = any_dog4;
	}
	public String getAny_dog5() {
		return any_dog5;
	}
	public void setAny_dog5(String any_dog5) {
		this.any_dog5 = any_dog5;
	}
	public String getServ_code() {
		return serv_code;
	}
	public void setServ_code(String serv_code) {
		this.serv_code = serv_code;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getServ_order_no() {
		return serv_order_no;
	}
	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}
	
	
}
