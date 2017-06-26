package com.tydic.unicom.service.sequence.po;

import java.io.Serializable;

import javax.persistence.Id;

public class SequenceId implements Serializable {
	
	private String dual_id;

	
    public String getDual_id() {
    	return dual_id;
    }

	
    public void setDual_id(String dual_id) {
    	this.dual_id = dual_id;
    }

	 
    
}