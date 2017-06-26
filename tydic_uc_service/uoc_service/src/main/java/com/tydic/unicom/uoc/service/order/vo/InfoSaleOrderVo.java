package com.tydic.unicom.uoc.service.order.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class InfoSaleOrderVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
	
	private String sale_order_no;
	private String province_code;
	private String area_code;
	private String part_month;
	private String accept_time;
	private String order_type;
	private String accept_no;
	private String accept_type;
	private String accept_system;
	private String accept_oper_no;
	private String accept_oper_name;
	private String accept_depart_no;
	private String accept_depart_name;
	private String pay_flag;
	private String pay_type;
	private String express_flag;
	private String cancle_flag;
	private String order_state;
	private String create_time;
	private String finish_time;
	private String ord_mod_code;
	private String proc_inst_id;
	private String pre_proc_inst_id;
	private String accept_depart_type;
	private String callback_url;
	
	private String total_num;
	private String remainder;

	public String getAccept_depart_type() {
		return accept_depart_type;
	}
	public void setAccept_depart_type(String accept_depart_type) {
		this.accept_depart_type = accept_depart_type;
	}
	public String getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getProc_inst_id() {
		return proc_inst_id;
	}
	public void setProc_inst_id(String proc_inst_id) {
		this.proc_inst_id = proc_inst_id;
	}
	public String getPre_proc_inst_id() {
		return pre_proc_inst_id;
	}
	public void setPre_proc_inst_id(String pre_proc_inst_id) {
		this.pre_proc_inst_id = pre_proc_inst_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getAccept_type() {
		return accept_type;
	}
	public void setAccept_type(String accept_type) {
		this.accept_type = accept_type;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_flag() {
		return pay_flag;
	}
	public void setPay_flag(String pay_flag) {
		this.pay_flag = pay_flag;
	}
	public String getExpress_flag() {
		return express_flag;
	}
	public void setExpress_flag(String express_flag) {
		this.express_flag = express_flag;
	}
	public String getCancle_flag() {
		return cancle_flag;
	}
	public void setCancle_flag(String cancle_flag) {
		this.cancle_flag = cancle_flag;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getOrd_mod_code() {
		return ord_mod_code;
	}
	public void setOrd_mod_code(String ord_mod_code) {
		this.ord_mod_code = ord_mod_code;
	}
	public String getSale_order_no() {
		return sale_order_no;
	}
	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
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
	public String getAccept_no() {
		return accept_no;
	}
	public void setAccept_no(String accept_no) {
		this.accept_no = accept_no;
	}
	public String getAccept_system() {
		return accept_system;
	}
	public void setAccept_system(String accept_system) {
		this.accept_system = accept_system;
	}
	public String getAccept_oper_no() {
		return accept_oper_no;
	}
	public void setAccept_oper_no(String accept_oper_no) {
		this.accept_oper_no = accept_oper_no;
	}
	public String getAccept_oper_name() {
		return accept_oper_name;
	}
	public void setAccept_oper_name(String accept_oper_name) {
		this.accept_oper_name = accept_oper_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}
	public String getAccept_depart_no() {
		return accept_depart_no;
	}
	public void setAccept_depart_no(String accept_depart_no) {
		this.accept_depart_no = accept_depart_no;
	}
	public String getAccept_depart_name() {
		return accept_depart_name;
	}
	public void setAccept_depart_name(String accept_depart_name) {
		this.accept_depart_name = accept_depart_name;
	}
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	public String getRemainder() {
		return remainder;
	}
	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}
	public String getCallback_url() {
		return callback_url;
	}
	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}
}
