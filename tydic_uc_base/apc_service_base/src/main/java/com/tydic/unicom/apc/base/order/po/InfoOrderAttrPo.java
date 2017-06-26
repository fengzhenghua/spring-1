package com.tydic.unicom.apc.base.order.po;

import javax.persistence.Id;

import com.tydic.unicom.vdsBase.po.BasePo;


public class InfoOrderAttrPo extends BasePo{
	
	private static final long serialVersionUID = 1L;

	private String order_attr_id;
	
	private String order_id;
	
	private String attr_type;
	
	private String attr_id;
	
	private String attr_value;
	
	private String oper_id;
	
	private String dept_id;
	
	@Id
    public String getOrder_attr_id() {
    	return order_attr_id;
    }

	
    public void setOrder_attr_id(String order_attr_id) {
    	this.order_attr_id = order_attr_id;
    }

	
    public String getOrder_id() {
    	return order_id;
    }

	
    public void setOrder_id(String order_id) {
    	this.order_id = order_id;
    }

	
    public String getAttr_type() {
    	return attr_type;
    }

	
    public void setAttr_type(String attr_type) {
    	this.attr_type = attr_type;
    }

	
    public String getAttr_id() {
    	return attr_id;
    }

	
    public void setAttr_id(String attr_id) {
    	this.attr_id = attr_id;
    }

	
    public String getAttr_value() {
    	return attr_value;
    }

	
    public void setAttr_value(String attr_value) {
    	this.attr_value = attr_value;
    }


	public String getOper_id() {
		return oper_id;
	}


	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}


	public String getDept_id() {
		return dept_id;
	}


	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
    
    

}
