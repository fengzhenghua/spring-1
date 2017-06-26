package com.tydic.unicom.webUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class UssMessage implements Serializable{

	private UssMessage.Type type;

	private String content;

	private Map<String, Object> args;

	public UssMessage() {
	}

	public UssMessage(Type type, String content) {
		this.type = type;
		this.content = SpringBean.getMessage(content);
	}
	public UssMessage(Type type, String content,String errordesc, Map<String, Object> args) {
		this.type = type;
		if(getInfoFromText.getNewMessage(errordesc) == null || "".equals(getInfoFromText.getNewMessage(errordesc))){
			this.content = SpringBean.getMessage(content);
		} else {
			this.content = SpringBean.getMessage(content)+":"+getInfoFromText.getNewMessage(errordesc);
		}
		this.args = args;
	}
	public UssMessage(Type type, String content, Map<String, Object> args) {
		this.type = type;
		this.content = SpringBean.getMessage(content);
		this.args = args;
	}

	public static UssMessage success(String content) {
		return new UssMessage(UssMessage.Type.success, content,"", null);
	}

	public static UssMessage warn(String content) {
		return new UssMessage(UssMessage.Type.warn, content,"", null);
	}

	public static UssMessage error(String content) {
		Map<String, Object> argsTmp = new HashMap<String, Object>();
		argsTmp.put("resp_code", content);
		
		return new UssMessage(UssMessage.Type.error, content,"", argsTmp);
	}
	
	public static UssMessage error(String content,String errordesc) {
		Map<String, Object> argsTmp = new HashMap<String, Object>();
		argsTmp.put("resp_code", content);
		
		return new UssMessage(UssMessage.Type.error, content,errordesc, argsTmp);
	}

	public static UssMessage success(String content, Map<String, Object> args) {
		return new UssMessage(UssMessage.Type.success, content,"", args);
	}

	public static UssMessage warn(String content, Map<String, Object> args) {
		return new UssMessage(UssMessage.Type.warn, content,"", args);
	}

	public static UssMessage error(String content, Map<String, Object> args) {
		args.put("resp_code", content);
		return new UssMessage(UssMessage.Type.error, content,"", args);
	}
	
	public static UssMessage needLogin() {
		Map<String, Object> argsTmp = new HashMap<String, Object>();
		argsTmp.put("resp_code", "rest.need.reLogin");
		return new UssMessage(UssMessage.Type.needLogin, "com.tydic.unicom.rest.needLogin","", argsTmp);
	}

	public UssMessage.Type getType() {
		return type;
	}

	public void setType(UssMessage.Type type) {
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
