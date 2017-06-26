package com.tydic.unicom.uoc.business.order.service.vo;


import com.tydic.unicom.webUtil.BaseVo;

public class ArtificialTaskVo extends BaseVo{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String jsession_id;
	private String order_no;
	private String oper_type;		//业务类型
	private String deal_code;
	private String deal_desc;
	private String deal_system_no;
	private String accept_oper_no;
	private String accept_oper_name;
	private String accept_depart_no;
	private String accept_depart_name;
	private String order_type;
	private String accept_time_start;
	private String accept_time_end;
	private String create_time;
	private String finish_time;
	private String limit_time;				//逾期时间
	private String global_limit_time;		//全局逾期时间
	private String call_type;
	private String area_code;
	private String person_flag; //个人任务标识：0个人任务1部门任务
	private String tache_name;
	private String tache_code;	//环节编码
	private String cust_name;	//客户名称
	private String cert_no;		//客户证件
	private String acc_nbr;		//业务号码
	private String prod_code;	//产品编码
	private String accept_no;	//前台受理流水
	private String page_no;		//当前页码
	private String page_size;	//每页条数
	private String oper_code;
	private String late_flag;
	private String part_month;
	private String sort_type;
	private String depart_no;
	private String oper_no;
	private String start_time;
	private String end_time;
	private String json_info_ext;
	private String sim_id;
	private String flow_skip;
	private String prod_group;

	private String state;//任务状态0、未完成、1、已完成、2全部任务


	public String getProd_group() {
		return prod_group;
	}

	public void setProd_group(String prod_group) {
		this.prod_group = prod_group;
	}

	public String getFlow_skip() {
		return flow_skip;
	}

	public void setFlow_skip(String flow_skip) {
		this.flow_skip = flow_skip;
	}
	public String getSim_id() {
		return sim_id;
	}
	public void setSim_id(String sim_id) {
		this.sim_id = sim_id;
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
	public String getLimit_time() {
		return limit_time;
	}
	public void setLimit_time(String limit_time) {
		this.limit_time = limit_time;
	}
	public String getGlobal_limit_time() {
		return global_limit_time;
	}
	public void setGlobal_limit_time(String global_limit_time) {
		this.global_limit_time = global_limit_time;
	}
	public String getTache_name() {
		return tache_name;
	}
	public void setTache_name(String tache_name) {
		this.tache_name = tache_name;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getAccept_no() {
		return accept_no;
	}
	public void setAccept_no(String accept_no) {
		this.accept_no = accept_no;
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
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getProd_code() {
		return prod_code;
	}
	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
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
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getAccept_time_start() {
		return accept_time_start;
	}
	public void setAccept_time_start(String accept_time_start) {
		this.accept_time_start = accept_time_start;
	}
	public String getAccept_time_end() {
		return accept_time_end;
	}
	public void setAccept_time_end(String accept_time_end) {
		this.accept_time_end = accept_time_end;
	}
	public String getCall_type() {
		return call_type;
	}
	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getPerson_flag() {
		return person_flag;
	}
	public void setPerson_flag(String person_flag) {
		this.person_flag = person_flag;
	}
	public String getOper_code() {
		return oper_code;
	}
	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
	}
	public String getLate_flag() {
		return late_flag;
	}
	public void setLate_flag(String late_flag) {
		this.late_flag = late_flag;
	}
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	public String getSort_type() {
		return sort_type;
	}
	public void setSort_type(String sort_type) {
		this.sort_type = sort_type;
	}
	public String getDepart_no() {
		return depart_no;
	}
	public void setDepart_no(String depart_no) {
		this.depart_no = depart_no;
	}
	public String getOper_no() {
		return oper_no;
	}
	public void setOper_no(String oper_no) {
		this.oper_no = oper_no;
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
	public String getJson_info_ext() {
		return json_info_ext;
	}
	public void setJson_info_ext(String json_info_ext) {
		this.json_info_ext = json_info_ext;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArtificialTaskVo [jsession_id=");
		builder.append(jsession_id);
		builder.append(", order_no=");
		builder.append(order_no);
		builder.append(", oper_type=");
		builder.append(oper_type);
		builder.append(", deal_code=");
		builder.append(deal_code);
		builder.append(", deal_desc=");
		builder.append(deal_desc);
		builder.append(", deal_system_no=");
		builder.append(deal_system_no);
		builder.append(", accept_oper_no=");
		builder.append(accept_oper_no);
		builder.append(", accept_oper_name=");
		builder.append(accept_oper_name);
		builder.append(", accept_depart_no=");
		builder.append(accept_depart_no);
		builder.append(", accept_depart_name=");
		builder.append(accept_depart_name);
		builder.append(", order_type=");
		builder.append(order_type);
		builder.append(", accept_time_start=");
		builder.append(accept_time_start);
		builder.append(", accept_time_end=");
		builder.append(accept_time_end);
		builder.append(", create_time=");
		builder.append(create_time);
		builder.append(", finish_time=");
		builder.append(finish_time);
		builder.append(", limit_time=");
		builder.append(limit_time);
		builder.append(", global_limit_time=");
		builder.append(global_limit_time);
		builder.append(", call_type=");
		builder.append(call_type);
		builder.append(", area_code=");
		builder.append(area_code);
		builder.append(", person_flag=");
		builder.append(person_flag);
		builder.append(", tache_name=");
		builder.append(tache_name);
		builder.append(", tache_code=");
		builder.append(tache_code);
		builder.append(", cust_name=");
		builder.append(cust_name);
		builder.append(", cert_no=");
		builder.append(cert_no);
		builder.append(", acc_nbr=");
		builder.append(acc_nbr);
		builder.append(", prod_code=");
		builder.append(prod_code);
		builder.append(", accept_no=");
		builder.append(accept_no);
		builder.append(", page_no=");
		builder.append(page_no);
		builder.append(", page_size=");
		builder.append(page_size);
		builder.append(", oper_code=");
		builder.append(oper_code);
		builder.append(", late_flag=");
		builder.append(late_flag);
		builder.append(", part_month=");
		builder.append(part_month);
		builder.append(", sort_type=");
		builder.append(sort_type);
		builder.append(", depart_no=");
		builder.append(depart_no);
		builder.append(", oper_no=");
		builder.append(oper_no);
		builder.append(", start_time=");
		builder.append(start_time);
		builder.append(", end_time=");
		builder.append(end_time);
		builder.append(", json_info_ext=");
		builder.append(json_info_ext);
		builder.append(", sim_id=");
		builder.append(sim_id);
		builder.append(", flow_skip=");
		builder.append(flow_skip);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}



}
