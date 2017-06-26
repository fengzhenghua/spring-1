package com.tydic.unicom.uif.service.vo;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午9:20:48
 * @ClassName CallLocalAbilitPlatVo
 * @Description 本地能力平台调用入参
 * @version V1.0
 */
public class CallLocalAbilitPlatVo extends BaseObject {
	
	private static final long serialVersionUID = -7013709047030145468L;
	
	private String infoJson;
	
	private TapServiceVo tapService;
	
	public TapServiceVo getTapService() {
		return tapService;
	}
	
	public void setTapService(TapServiceVo tapService) {
		this.tapService = tapService;
	}
	
	public String getInfoJson() {
		return infoJson;
	}
	
	public void setInfoJson(String infoJson) {
		this.infoJson = infoJson;
	}
	
}
