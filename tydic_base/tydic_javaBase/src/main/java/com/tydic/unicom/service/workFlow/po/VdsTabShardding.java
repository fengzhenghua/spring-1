package com.tydic.unicom.service.workFlow.po;

import java.io.Serializable;


public class VdsTabShardding implements Serializable {
	/**常量------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	
	private String owner_name;
	
	private String table_name;
	
	private String partition_id;
	
	private String accept_constraint;

	
    public String getOwner_name() {
    	return this.owner_name;
    }

	
    public void setOwner_name(String owner_name) {
    	this.owner_name = owner_name;
    }

	
    public String getTable_name() {
    	return this.table_name;
    }

	
    public void setTable_name(String table_name) {
    	this.table_name = table_name;
    }

	
    public String getPartition_id() {
    	return this.partition_id;
    }

	
    public void setPartition_id(String partition_id) {
    	this.partition_id = partition_id;
    }

	
    public String getAccept_constraint() {
    	return this.accept_constraint;
    }

	
    public void setAccept_constraint(String accept_constraint) {
    	this.accept_constraint = accept_constraint;
    }
	
}
