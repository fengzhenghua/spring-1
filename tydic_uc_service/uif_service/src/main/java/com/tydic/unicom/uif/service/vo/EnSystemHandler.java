package com.tydic.unicom.uif.service.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年5月10日 下午7:10:31
 * @ClassName EnSystemHandler
 * @Description 枚举系统的接口方法
 * @version V1.0
 */
public enum EnSystemHandler {
	/**
	 * 订单中心
	 */
	UOC("callUocCenterHandler","json_info"),
	/**
	 * 商品中心
	 */
	UGC("callUgcCenterHandler","json_info"),
	/**
	 * 触点中心
	 */
	APC("callApcCenterHandler","json_info"),
	/**
	 * 能力平台
	 */
	ABILIT("callAbilitCenterHandler","json_info"),
	/**
	 * 认证中心
	 */
	UAC("callUacCenterHandler","json_info");
	
	/**
	 * 句柄名称
	 */
	private String handler;
	
	private List<String> ucSystemParms;
	
	private EnSystemHandler(String handler,String ...ucSystemParm){
		this.handler = handler;
		if(ucSystemParm !=null){
			ucSystemParms =  new ArrayList<String>();
			for (int i = 0; i < ucSystemParm.length; i++) {
				ucSystemParms.add(ucSystemParm[i]);
			}
		}
	}
	
	/**
	 * 获取支持的参数
	 */
	public List<String> getucSystemParms() {
		return ucSystemParms;
	}
	
	public String getHandler() {
		return handler;
	}
	
	public void setHandler(String handler) {
		this.handler = handler;
	}
}
