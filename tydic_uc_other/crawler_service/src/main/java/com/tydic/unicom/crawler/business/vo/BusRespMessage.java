package com.tydic.unicom.crawler.business.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawlerframe.util.getInfoFromText;
import com.tydic.unicom.webUtil.SpringBean;

public class BusRespMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String SUCCESS = "0000"; // 成功
	public static final String SYSTEM_EXCEPTION = "1000";// 系统异常
	public static final String SERVICE_FAIL = "1001";// 服务调用失败
	public static final String ABILITY_PLATFORM_FAIL = "1002";// 能力平台调用失败
	public static final String PARAM_ERROR = "1003";// 入参不正确
	public static final String INTERFACE_REPEAT = "1004";// 接口重复
	
	public static final String LOGINERR = "9998";// 登录失败
	public static final String ERR = "9999";// 调用失败
	private String respCode;
	
	private String content = "";
	
	private Map<String, Object> args = new HashMap<String, Object>();

	public BusRespMessage(){
	}
	
	public BusRespMessage(String respCode,String content){
		this.respCode = respCode;
		this.content = SpringBean.getMessage(content);
	}
	
	public BusRespMessage(String respCode,String content,Map<String, Object> args){
		this.respCode = respCode;
		this.content = SpringBean.getMessage(content);
		this.args = args;
	}
	
	public BusRespMessage(String respCode, String content,String errordesc, Map<String, Object> args) {
		this.respCode = respCode;
		if(getInfoFromText.getNewMessage(errordesc) == null || "".equals(getInfoFromText.getNewMessage(errordesc))){
			this.content = SpringBean.getMessage(content);
		} else {
			this.content = SpringBean.getMessage(content)+":"+getInfoFromText.getNewMessage(errordesc);
		}
		this.args = args;
	}
	
	public static BusRespMessage success(String content){
		return new BusRespMessage(SUCCESS,content,"",null);
	}
	
	public static BusRespMessage success(String content,Map<String, Object> args){
		return new BusRespMessage(SUCCESS,content,"",args);
	}
	
	public static BusRespMessage error(String respCode,String content){
		return new BusRespMessage(respCode,content,"",null);
	}
	
	public static BusRespMessage error(String respCode,String content,Map<String, Object> args){
		return new BusRespMessage(respCode,content,"",args);
	}
	
	@Override
	public String toString() {
		return "UocMessage [respCode=" + respCode + ", content=" + content + ", args=" + args.toString() + "]";
//		return "UocMessage [respCode=" + respCode + ", content=" + content + ", args=" + String.valueOf(args) + "]";
	}

	public Map<String, Object> getArgs() {
		return args;
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
