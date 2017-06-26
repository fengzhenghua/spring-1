package com.tydic.unicom.webUtil;

import com.tydic.unicom.webUtil.UocMessage;

public class UocException extends Exception{

	private static final long serialVersionUID = 1L;
	private UocMessage uocMessage;
	
	public UocException(){
		super();
	}
	
	public UocException(String msg){
		super(msg);
	}
	
	public UocException(String msg, Throwable cause){
		super(msg,cause);
	}
	
	public UocException(Throwable cause){
		super(cause);
	}
	
	public UocException(UocMessage uocMessage){
		this.uocMessage = uocMessage;
	}
	
	public UocMessage getUocMessage(){
		return this.uocMessage;
	}
}
