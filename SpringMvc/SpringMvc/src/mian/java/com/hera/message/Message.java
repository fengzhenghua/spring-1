package com.hera.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String Content;
	private String RespCode;
	private Map<String,Object> args = new HashMap<String, Object>();
	
	public Message() {
		super();
	}
	
	public Message(String content, String respCode) {
		super();
		Content = content;
		RespCode = respCode;
	}

	public Message(String content, String respCode, Map<String, Object> args) {
		super();
		Content = content;
		RespCode = respCode;
		this.args = args;
	}
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public void addArgs(String key,Object arg){
		if(this.args == null){
			args = new HashMap<String,Object>();
		}
		args.put(key, arg);
	}
	public Map<String, Object> getArgs() {
		return args;
	}
	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}
	@Override
	public String toString() {
		return "Message [Content=" + Content + ", RespCode=" + RespCode
				+ ", args=" + String.valueOf(args) + "]";
	}
	
	
}
