package com.tydic.unicom.webUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年5月26日 上午10:34:28
 * @ClassName UIFMessage
 * @Description uif信息，扩展来自UocMessage
 * @version V1.0
 */
public class UIFMessage extends UocMessage{
	
private static final long serialVersionUID = 1L;
	
	private JSONObject args;
	
	public JSONObject getArgs() {
		return args;
	}
	
	public void setArgs(JSONObject args) {
		this.args = args;
	}
}
