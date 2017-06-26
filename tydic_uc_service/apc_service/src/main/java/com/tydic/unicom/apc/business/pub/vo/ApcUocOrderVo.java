package com.tydic.unicom.apc.business.pub.vo;

import java.io.Serializable;

public class ApcUocOrderVo implements Serializable{

	private static final long serialVersionUID = -7080104664895480244L;
	
	private String oper_no;
	private String order_type;
	private String order_id;
	private String flow_flag;
	private String accept_system;
	private String province_code;
	private String area_code;
	private String pay_flag;
	private String pay_type;
	private String express_flag;
	private String auto_confirm;
	private String serial_number;
	private String recom_person_id;
	private String recom_person_name;
	private String first_mon_bill_mode;
	private String product_id;
	private String ser_type;
	private String cert_address;
	private String cert_expire_date;
	private String customer_name;
	private String customer_sex;
	private String cert_num;
	private String cert_type;
	private String contact_address;
	private String contact_phone;
	private String oper_code;
	private String receive_name;
	private String receive_phone;
	private String receive_province;
	private String receive_city;
	private String receive_area;
	private String receive_address;
	private String total_fee;
	private String cod_charge;
	private String activity_id;
	private String ap_id;
	private String reward_oper_no;
	private String ap_type;
	private String fee_info;
	private String tb_account;
	private String ywb_id;
	private String accept_no;
	private String callback_url;
	private String cod_bank_no;
	
	public String getCod_bank_no() {
		return cod_bank_no;
	}
	public void setCod_bank_no(String cod_bank_no) {
		this.cod_bank_no = cod_bank_no;
	}
	public String getCallback_url() {
		return callback_url;
	}
	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}
	public String getAccept_no() {
		return accept_no;
	}
	public void setAccept_no(String accept_no) {
		this.accept_no = accept_no;
	}
	public String getYwb_id() {
		return ywb_id;
	}
	public void setYwb_id(String ywb_id) {
		this.ywb_id = ywb_id;
	}
	public String getTb_account() {
		return tb_account;
	}
	public void setTb_account(String tb_account) {
		this.tb_account = tb_account;
	}
	public String getFee_info() {
		return fee_info;
	}
	public void setFee_info(String fee_info) {
		this.fee_info = fee_info;
	}
	public String getAp_type() {
		return ap_type;
	}
	public void setAp_type(String ap_type) {
		this.ap_type = ap_type;
	}
	public String getReward_oper_no() {
		return reward_oper_no;
	}
	public void setReward_oper_no(String reward_oper_no) {
		this.reward_oper_no = reward_oper_no;
	}
	public String getAp_id() {
		return ap_id;
	}
	public void setAp_id(String ap_id) {
		this.ap_id = ap_id;
	}
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	public String getCod_charge() {
		return cod_charge;
	}
	public void setCod_charge(String cod_charge) {
		this.cod_charge = cod_charge;
	}
	public String getCert_type() {
		return cert_type;
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}
	public String getCert_num() {
		return cert_num;
	}
	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}
	
	public String getOper_no() {
		return oper_no;
	}
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getFlow_flag() {
		return flow_flag;
	}
	public void setFlow_flag(String flow_flag) {
		this.flow_flag = flow_flag;
	}
	public String getAccept_system() {
		return accept_system;
	}
	public void setAccept_system(String accept_system) {
		this.accept_system = accept_system;
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
	public String getPay_flag() {
		return pay_flag;
	}
	public void setPay_flag(String pay_flag) {
		this.pay_flag = pay_flag;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getExpress_flag() {
		return express_flag;
	}
	public void setExpress_flag(String express_flag) {
		this.express_flag = express_flag;
	}
	public String getAuto_confirm() {
		return auto_confirm;
	}
	public void setAuto_confirm(String auto_confirm) {
		this.auto_confirm = auto_confirm;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getRecom_person_id() {
		return recom_person_id;
	}
	public void setRecom_person_id(String recom_person_id) {
		this.recom_person_id = recom_person_id;
	}
	public String getRecom_person_name() {
		return recom_person_name;
	}
	public void setRecom_person_name(String recom_person_name) {
		this.recom_person_name = recom_person_name;
	}
	public String getFirst_mon_bill_mode() {
		return first_mon_bill_mode;
	}
	public void setFirst_mon_bill_mode(String first_mon_bill_mode) {
		this.first_mon_bill_mode = first_mon_bill_mode;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getSer_type() {
		return ser_type;
	}
	public void setSer_type(String ser_type) {
		this.ser_type = ser_type;
	}
	public String getCert_address() {
		return cert_address;
	}
	public void setCert_address(String cert_address) {
		this.cert_address = cert_address;
	}
	public String getCert_expire_date() {
		return cert_expire_date;
	}
	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_sex() {
		return customer_sex;
	}
	public void setCustomer_sex(String customer_sex) {
		this.customer_sex = customer_sex;
	}
	public String getContact_address() {
		return contact_address;
	}
	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public String getReceive_name() {
		return receive_name;
	}
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}
	public String getReceive_phone() {
		return receive_phone;
	}
	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}
	public String getReceive_province() {
		return receive_province;
	}
	public void setReceive_province(String receive_province) {
		this.receive_province = receive_province;
	}
	public String getReceive_city() {
		return receive_city;
	}
	public void setReceive_city(String receive_city) {
		this.receive_city = receive_city;
	}
	public String getReceive_area() {
		return receive_area;
	}
	public void setReceive_area(String receive_area) {
		this.receive_area = receive_area;
	}
	public String getReceive_address() {
		return receive_address;
	}
	public void setReceive_address(String receive_address) {
		this.receive_address = receive_address;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	@Override
	public String toString() {
		return "ApcUocOrderVo [oper_no=" + oper_no + ", order_type="
				+ order_type + ", order_id=" + order_id + ", flow_flag="
				+ flow_flag + ", accept_system=" + accept_system
				+ ", province_code=" + province_code + ", area_code="
				+ area_code + ", pay_flag=" + pay_flag + ", pay_type="
				+ pay_type + ", express_flag=" + express_flag
				+ ", auto_confirm=" + auto_confirm + ", serial_number="
				+ serial_number + ", recom_person_id=" + recom_person_id
				+ ", recom_person_name=" + recom_person_name
				+ ", first_mon_bill_mode=" + first_mon_bill_mode
				+ ", product_id=" + product_id + ", ser_type=" + ser_type
				+ ", cert_address=" + cert_address + ", cert_expire_date="
				+ cert_expire_date + ", customer_name=" + customer_name
				+ ", customer_sex=" + customer_sex + ", cert_num=" + cert_num
				+ ", cert_type=" + cert_type + ", contact_address="
				+ contact_address + ", contact_phone=" + contact_phone
				+ ", oper_code=" + oper_code + ", receive_name=" + receive_name
				+ ", receive_phone=" + receive_phone + ", receive_province="
				+ receive_province + ", receive_city=" + receive_city
				+ ", receive_area=" + receive_area + ", receive_address="
				+ receive_address + ", total_fee=" + total_fee
				+ ", cod_charge=" + cod_charge + ", activity_id=" + activity_id
				+ ", ap_id=" + ap_id + ", reward_oper_no=" + reward_oper_no
				+ ", ap_type=" + ap_type + ", fee_info=" + fee_info
				+ ", tb_account=" + tb_account + ", ywb_id=" + ywb_id
				+ ", accept_no=" + accept_no + ", callback_url=" + callback_url
				+ ", cod_bank_no=" + cod_bank_no + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
}
