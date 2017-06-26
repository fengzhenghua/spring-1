package com.tydic.unicom.apc.business.order.vo;

import com.tydic.unicom.webUtil.BaseVo;

/**
 * <p></p>
 * @author hangweixing 2014年9月22日 下午4:43:16
 * @ClassName InfoOrderVo
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月22日
 * @modify by reason:{方法名}:{原因}
 */
public class LogPayCsVo extends BaseVo{
	private static final long serialVersionUID = 1L;
	private String pay_cs_id;
	private String oper_sn;
	/* 支付方式 */
	private String tele_type;
	/* 总费用 */
	private String device_number;
	/* 总减免金额 */
	private String pay_type;
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
	private String serv_code;
	
	
	private String pay_commission_fee_type;
	private String commission_fee;
	private String agent_pay_fee;
	private String app_pay_fee;
	
	private String total_pay_charge;
	
	
	private String oper_id_ab;
	private String oper_name;
	private String area_id;
	private String area_name;
	private String any_dog1;
	private String any_dog2;
	private String any_dog3;
	private String any_dog4;
	private String any_dog5;
	/*19付的回调参数*/
	private String order_id;
	private String status;
	private String mcht_flag;
	private String serv_order_no;
	
	public String getServ_order_no() {
		return serv_order_no;
	}
	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}
	public String getMcht_flag() {
		return mcht_flag;
	}
	public void setMcht_flag(String mcht_flag) {
		this.mcht_flag = mcht_flag;
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
	public String getTotal_pay_charge() {
		return total_pay_charge;
	}
	public void setTotal_pay_charge(String total_pay_charge) {
		this.total_pay_charge = total_pay_charge;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getServ_code() {
		return serv_code;
	}
	public void setServ_code(String serv_code) {
		this.serv_code = serv_code;
	}
	
	
	
}
