package com.tydic.unicom.webUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class Message implements Serializable{

	private Message.Type type;

	private String content;

	private Map<String, Object> args;

	public Message() {
	}

	public Message(Type type, String content) {
		this.type = type;
		this.content = SpringBean.getMessage(content);
	}
	public Message(Type type, String content,String errordesc, Map<String, Object> args) {
		this.type = type;
		if(getInfoFromText.getNewMessage(errordesc) == null || "".equals(getInfoFromText.getNewMessage(errordesc))){
			this.content = SpringBean.getMessage(content);
		} else {
			this.content = SpringBean.getMessage(content)+":"+getInfoFromText.getNewMessage(errordesc);
		}
		this.args = args;
	}
	public Message(Type type, String content, Map<String, Object> args) {
		this.type = type;
		this.content = SpringBean.getMessage(content);
		this.args = args;
	}

	public static Message success(String content) {
		return new Message(Message.Type.success, content,"", null);
	}

	public static Message warn(String content) {
		return new Message(Message.Type.warn, content,"", null);
	}

	public static Message error(String content) {
		Map<String, Object> argsTmp = new HashMap<String, Object>();
		argsTmp.put("resp_code", content);
		
		return new Message(Message.Type.error, content,"", argsTmp);
	}
	
	public static Message error(String content,String errordesc) {
		Map<String, Object> argsTmp = new HashMap<String, Object>();
		argsTmp.put("resp_code", content);
		
		return new Message(Message.Type.error, content,errordesc, argsTmp);
	}

	public static Message success(String content, Map<String, Object> args) {
		return new Message(Message.Type.success, content,"", args);
	}

	public static Message warn(String content, Map<String, Object> args) {
		return new Message(Message.Type.warn, content,"", args);
	}

	public static Message error(String content, Map<String, Object> args) {
		args.put("resp_code", content);
		return new Message(Message.Type.error, content,"", args);
	}
	
	public static Message needLogin() {
		Map<String, Object> argsTmp = new HashMap<String, Object>();
		argsTmp.put("resp_code", "rest.need.reLogin");
		return new Message(Message.Type.needLogin, "com.tydic.unicom.rest.needLogin","", argsTmp);
	}

	public Message.Type getType() {
		return type;
	}

	public void setType(Message.Type type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public void setErrorDetail(String detail) {
		this.content = content+detail;
	}


	/*public String toString() {
		//return JSONObject.fromObject( this).toString();
		return SpringBean.getMessage(this.content);
	}*/

	public Map<String, Object> getArgs() {
		return args;
	}

	public enum Type {
		success, warn, error,needLogin;
	}

	public void addArg(String key, Object arg) {
		if(this.args == null){
			args = new HashMap<String, Object>();
		}
		args.put(key, arg);
	}
}
