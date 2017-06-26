package com.tydic.unicom.uoc.vo;

import com.tydic.unicom.security.BaseObject;

/**
 *
 * <p>
 * </p>
 * @author heguoyong 2017年4月13日 下午3:00:49
 * @ClassName ExprotOrderInputVo
 * @Description 导出订单输入
 * @version V1.0
 */
public class ExprotOrderInputVo extends BaseObject {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1121422704408870230L;
	/**
	 * 组别，环节
	 */
	private String processLink;
	/**
	 * page_size * 工号
	 */
	private String operNo;

	/**
	 * 年
	 */
	private String year;
	/**
	 * 月
	 */
	private String month;
	/**
	 * 日
	 */
	private String date;

	/**
	 * 开始时间
	 */
	private String accept_time_start;

	/**
	 * 结束时间
	 */
	private String accept_time_end;

	private String jsession_id;

	private String oper_code;

	private String page_no;

	private String page_size;

	public String getProcessLink() {
		return processLink;
	}

	public void setProcessLink(String processLink) {
		this.processLink = processLink;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getJsession_id() {
		return jsession_id;
	}

	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}

	public String getOper_code() {
		return oper_code;
	}

	public void setOper_code(String oper_code) {
		this.oper_code = oper_code;
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

}
