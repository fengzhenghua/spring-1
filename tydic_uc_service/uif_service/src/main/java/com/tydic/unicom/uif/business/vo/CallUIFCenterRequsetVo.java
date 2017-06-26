package com.tydic.unicom.uif.business.vo;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月10日 下午5:40:26
 * @ClassName EachCenterRequsetVo
 * @Description 中心互调对象
 * @version V1.0
 */
public class CallUIFCenterRequsetVo extends BaseObject {
	
	private static final long serialVersionUID = 6417559472131964750L;
	/**
	 * 来源系统,枚举系统简称
	 */
	private String fromCenterCode;
	/**
	 * 目标系统，枚举系统简称
	 */
	private String toCenterCode;
	
	/**
	 * 调用的参数(json格式，里面可以多个参数，参数名称，参数值)
	 */
	private String params;
	
	public String getFromCenterCode() {
		return fromCenterCode;
	}
	
	public void setFromCenterCode(String fromCenterCode) {
		this.fromCenterCode = fromCenterCode;
	}
	
	public String getToCenterCode() {
		return toCenterCode;
	}
	
	public void setToCenterCode(String toCenterCode) {
		this.toCenterCode = toCenterCode;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
}
