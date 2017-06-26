package com.tydic.unicom.service.workFlow.po;

import java.io.Serializable;

import javax.persistence.Id;


public class Jbpm4Dual implements Serializable {
	/**常量------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	
	private String jbpm_dual_id;
	
	private String jbpm_name;
	
	private String key_id_name;
	
	private String result_flag;

	@Id
    public String getJbpm_dual_id() {
    	return this.jbpm_dual_id;
    }

	
    public void setJbpm_dual_id(String jbpm_dual_id) {
    	this.jbpm_dual_id = jbpm_dual_id;
    }

	
    public String getJbpm_name() {
    	return this.jbpm_name;
    }

	
    public void setJbpm_name(String jbpm_name) {
    	this.jbpm_name = jbpm_name;
    }

	
    public String getKey_id_name() {
    	return this.key_id_name;
    }

	
    public void setKey_id_name(String key_id_name) {
    	this.key_id_name = key_id_name;
    }


	public String getResult_flag() {
	    return result_flag;
    }


	public void setResult_flag(String result_flag) {
	    this.result_flag = result_flag;
    }
	
}
