package com.tydic.unicom.uif.service.vo;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午6:00:31
 * @ClassName AbilitReqestVo
 * @Description 能力平台调用入参
 * @version V1.0
 */
public class AbilitReqestVo extends BaseObject {
	
	private static final long serialVersionUID = 8892033279992331922L;
	
	public String json_info;
	
	public String interface_type;
	
	public String interface_param_json;
	
	public String interface_url;
	
	public String getJson_info() {
		return json_info;
	}
	
	public void setJson_info(String json_info) {
		this.json_info = json_info;
	}
	
	public String getInterface_type() {
		return interface_type;
	}
	
	public void setInterface_type(String interface_type) {
		this.interface_type = interface_type;
	}
	
	public String getInterface_param_json() {
		return interface_param_json;
	}
	
	public void setInterface_param_json(String interface_param_json) {
		this.interface_param_json = interface_param_json;
	}
	
	public String getInterface_url() {
		return interface_url;
	}
	
	public void setInterface_url(String interface_url) {
		this.interface_url = interface_url;
	}
	
}
