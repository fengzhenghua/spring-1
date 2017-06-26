package com.tydic.unicom.uoc.business.common.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class LogisticsReportVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
	
	private String jsession_id;
	private String create_time_start;
	private String create_time_end;
	private String accept_depart_no;
	private String page_no;
	private String page_size;
	private String accept_time_start;
	private String accept_time_end;
	
	public String getCreate_time_start() {
		return create_time_start;
	}
	public void setCreate_time_start(String create_time_start) {
		this.create_time_start = create_time_start;
	}
	public String getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(String create_time_end) {
		this.create_time_end = create_time_end;
	}
	public String getAccept_depart_no() {
		return accept_depart_no;
	}
	public void setAccept_depart_no(String accept_depart_no) {
		this.accept_depart_no = accept_depart_no;
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
	public String getJsession_id() {
		return jsession_id;
	}
	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
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
	
}
