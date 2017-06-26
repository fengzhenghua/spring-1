package com.tydic.unicom.webUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UocMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	private String respCode;
	
	private String content;
	
	private Map<String, Object> args;

	public UocMessage(){
	}
	
	public UocMessage(String respCode,String content){
		this.respCode = respCode;
		this.content = SpringBean.getMessage(content);
	}
	
	public UocMessage(String respCode,String content,Map<String, Object> args){
		this.respCode = respCode;
		this.content = SpringBean.getMessage(content);
		this.args = args;
	}
	
	public UocMessage(String respCode, String content,String errordesc, Map<String, Object> args) {
		this.respCode = respCode;
		if(getInfoFromText.getNewMessage(errordesc) == null || "".equals(getInfoFromText.getNewMessage(errordesc))){
			this.content = SpringBean.getMessage(content);
		} else {
			this.content = SpringBean.getMessage(content)+":"+getInfoFromText.getNewMessage(errordesc);
		}
		this.args = args;
	}
	
	public static UocMessage success(String content){
		return new UocMessage(RespCodeContents.SUCCESS,content,"",null);
	}
	
	public static UocMessage success(String content,Map<String, Object> args){
		return new UocMessage(RespCodeContents.SUCCESS,content,"",args);
	}
	
	public static UocMessage error(String respCode,String content){
		return new UocMessage(respCode,content,"",null);
	}
	
	public static UocMessage error(String respCode,String content,Map<String, Object> args){
		return new UocMessage(respCode,content,"",args);
	}
	
	@Override
	public String toString() {
		return "UocMessage [respCode=" + respCode + ", content=" + content + ", args=" + String.valueOf(args) + "]";
	}

	public Map<String, Object> getArgs() {
		return args;
	}
	
	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}

	public void addArg(String key, Object arg) {
		if(this.args == null){
			args = new HashMap<String, Object>();
		}
		args.put(key, arg);
	}
	
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	} 
}
