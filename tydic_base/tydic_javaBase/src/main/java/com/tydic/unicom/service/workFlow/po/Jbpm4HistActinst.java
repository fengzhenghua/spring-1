package com.tydic.unicom.service.workFlow.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;


public class Jbpm4HistActinst implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String  dbid_ ;
	private String  proc_ins_id ;
	private String  execution_ ;
	private String	order_status;
	private String  order_status_name;
	private Date	start_date;
	private Date    end_date;
	private String	now_proc_flag;
	private String  execution_type;
	
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getNow_proc_flag() {
		return now_proc_flag;
	}
	public void setNow_proc_flag(String now_proc_flag) {
		this.now_proc_flag = now_proc_flag;
	}
	public String getOrder_status_name() {
		return order_status_name;
	}
	public void setOrder_status_name(String order_status_name) {
		this.order_status_name = order_status_name;
	}
	public String getExecution_type() {
		return execution_type;
	}
	public void setExecution_type(String execution_type) {
		this.execution_type = execution_type;
	}
	
	@Id
	public String getDbid_() {
		return dbid_;
	}
	public void setDbid_(String dbid_) {
		this.dbid_ = dbid_;
	}
	public String getProc_ins_id() {
		return proc_ins_id;
	}
	public void setProc_ins_id(String proc_ins_id) {
		this.proc_ins_id = proc_ins_id;
	}
	public String getExecution_() {
		return execution_;
	}
	public void setExecution_(String execution_) {
		this.execution_ = execution_;
	}
	public Date getStart_date() {
	    return start_date;
    }
	public void setStart_date(Date start_date) {
	    this.start_date = start_date;
    }
	public Date getEnd_date() {
	    return end_date;
    }
	public void setEnd_date(Date end_date) {
	    this.end_date = end_date;
    }
	
}
