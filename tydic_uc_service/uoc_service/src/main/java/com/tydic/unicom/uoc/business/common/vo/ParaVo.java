package com.tydic.unicom.uoc.business.common.vo;

import java.util.List;
import java.util.Map;

import com.tydic.unicom.webUtil.BaseVo;

public class ParaVo  extends BaseVo{

	private static final long serialVersionUID = 1L;

	private String jsession_id;
	private String order_type;
	private String accept_no;
	private String accept_type;
	private String accept_system;
	private String accept_time;
	private String province_code;
	private String area_code;
	private String oper_no;
	private String sale_order_no;
	private String pay_flag;
	private String pay_type;
	private String express_flag;
	private String edit_time;
	private String edit_desc;
	private String edit_system;
	private String serv_order_no;
	private String oper_code;
	private String state_code_new;
	private String tache_code_new;
	private String order_no;
	private String oper_type;
	private String flow_type;
	private String flow_flag;
	private String update_type;
	private String real_pay_sn;
	private String pay_system_code;
	private String pay_time;
	private String query_type;
	private String manual_flag;
	private String json_info;
	private String all_json_info;
	private String finish_flag;
	private String master_query;
	private String auto_confirm;
	private String deal_code;
	private String deal_desc;
	private String deal_system_no;
	private String depart_no;
	private String prod_code;
	private String tache_code;
	private String start_time;
	private String end_time;
	private String target_oper_depart;
	private String audit_flag;
	private String audit_desc;
	private String pkg_name;
	private String page_no;
	private String page_size;
	private String state;
	private String total_num;
	private String remainder;
	private String callback_url;

	private Map<String,String> action_code;
	private List<String> serv_order_no_list;
	private String json_info_list;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	public String getPkg_name() {
		return pkg_name;
	}

	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}

	public String getAudit_flag() {
		return audit_flag;
	}

	public void setAudit_flag(String audit_flag) {
		this.audit_flag = audit_flag;
	}

	public String getAudit_desc() {
		return audit_desc;
	}

	public void setAudit_desc(String audit_desc) {
		this.audit_desc = audit_desc;
	}

	public String getTarget_oper_depart() {
		return target_oper_depart;
	}

	public void setTarget_oper_depart(String target_oper_depart) {
		this.target_oper_depart = target_oper_depart;
	}

	public String getDepart_no() {
		return depart_no;
	}

	public void setDepart_no(String depart_no) {
		this.depart_no = depart_no;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getJsession_id() {
		return jsession_id;
	}

	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getAccept_no() {
		return accept_no;
	}

	public void setAccept_no(String accept_no) {
		this.accept_no = accept_no;
	}

	public String getAccept_type() {
		return accept_type;
	}

	public void setAccept_type(String accept_type) {
		this.accept_type = accept_type;
	}

	public String getAccept_system() {
		return accept_system;
	}

	public void setAccept_system(String accept_system) {
		this.accept_system = accept_system;
	}

	public String getAccept_time() {
		return accept_time;
	}

	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
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

	public String getOper_no() {
		return oper_no;
	}

	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
	}

	public String getSale_order_no() {
		return sale_order_no;
	}

	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
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

	public String getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(String edit_time) {
		this.edit_time = edit_time;
	}

	public String getEdit_desc() {
		return edit_desc;
	}

	public void setEdit_desc(String edit_desc) {
		this.edit_desc = edit_desc;
	}

	public String getEdit_system() {
		return edit_system;
	}

	public void setEdit_system(String edit_system) {
		this.edit_system = edit_system;
	}

	public String getServ_order_no() {
		return serv_order_no;
	}

	public void setServ_order_no(String serv_order_no) {
		this.serv_order_no = serv_order_no;
	}

	public String getOper_code() {
		return oper_code;
	}

	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}

	public String getState_code_new() {
		return state_code_new;
	}

	public void setState_code_new(String state_code_new) {
		this.state_code_new = state_code_new;
	}

	public String getTache_code_new() {
		return tache_code_new;
	}

	public void setTache_code_new(String tache_code_new) {
		this.tache_code_new = tache_code_new;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOper_type() {
		return oper_type;
	}

	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}

	public String getFlow_type() {
		return flow_type;
	}

	public void setFlow_type(String flow_type) {
		this.flow_type = flow_type;
	}

	public String getUpdate_type() {
		return update_type;
	}

	public void setUpdate_type(String update_type) {
		this.update_type = update_type;
	}

	public String getReal_pay_sn() {
		return real_pay_sn;
	}

	public void setReal_pay_sn(String real_pay_sn) {
		this.real_pay_sn = real_pay_sn;
	}

	public String getPay_system_code() {
		return pay_system_code;
	}

	public void setPay_system_code(String pay_system_code) {
		this.pay_system_code = pay_system_code;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}


	public String getJson_info() {
		return json_info;
	}

	public void setJson_info(String json_info) {
		this.json_info = json_info;
	}

	public List<String> getServ_order_no_list() {
		return serv_order_no_list;
	}

	public void setServ_order_no_list(List<String> serv_order_no_list) {
		this.serv_order_no_list = serv_order_no_list;
	}



	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}


	public String getFlow_flag() {
		return flow_flag;
	}

	public void setFlow_flag(String flow_flag) {
		this.flow_flag = flow_flag;
	}

	public String getManual_flag() {
		return manual_flag;
	}

	public void setManual_flag(String manual_flag) {
		this.manual_flag = manual_flag;
	}

	public String getAll_json_info() {
		return all_json_info;
	}

	public void setAll_json_info(String all_json_info) {
		this.all_json_info = all_json_info;
	}

	public String getJson_info_list() {
		return json_info_list;
	}

	public void setJson_info_list(String json_info_list) {
		this.json_info_list = json_info_list;
	}

	public Map<String, String> getAction_code() {
		return action_code;
	}

	public void setAction_code(Map<String, String> action_code) {
		this.action_code = action_code;
	}

	public String getFinish_flag() {
		return finish_flag;
	}

	public void setFinish_flag(String finish_flag) {
		this.finish_flag = finish_flag;
	}

	public String getMaster_query() {
		return master_query;
	}

	public void setMaster_query(String master_query) {
		this.master_query = master_query;
	}

	public String getAuto_confirm() {
		return auto_confirm;
	}

	public void setAuto_confirm(String auto_confirm) {
		this.auto_confirm = auto_confirm;
	}

	public String getDeal_code() {
		return deal_code;
	}

	public void setDeal_code(String deal_code) {
		this.deal_code = deal_code;
	}

	public String getDeal_desc() {
		return deal_desc;
	}

	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}

	public String getDeal_system_no() {
		return deal_system_no;
	}

	public void setDeal_system_no(String deal_system_no) {
		this.deal_system_no = deal_system_no;
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
