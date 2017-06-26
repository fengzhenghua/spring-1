package com.tydic.unicom.uif.service.vo;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午6:07:17
 * @ClassName EnInterfaceType
 * @Description 业务类型
 * @version V1.0
 */
public enum EnInterfaceType {
	/**
	 * 重庆本地接口能力平台调用
	 */
	CALL_LOCAL_ABLIT("100", "callLocalAbilitService"),
	/**
	 * aop接口通过能力平台调用
	 */
	CAll_AOP_ABLIT("200", "callAopAbilitService"),
	
	/**
	 * 调用webservice服务
	 */
	CALL_WEB_SERVICE_ABLIT("400","callWebSerAbilitService"),
	
	/**
	 * 调用東方國信回調接口(默認回調成功)
	 */
	CALL_HD_700_ABLIT("700","callHdAbilitService"),
	
	/**
	 * 调用東方國信回調接口(需要判斷返回值)
	 */
	CALL_HD_702_ABLIT("702","callHdAbilitService"),
	
	/**
	 * 调用本地BSS回調接口(默認回調成功)
	 */
	CALL_HD_701_ABLIT("701","callHdAbilitService"),
	
	/**
	 * 调用本地BSS回調接口(人臉識別回調)
	 */
	CALL_HD_703_ABLIT("703","callHdAbilitService"),


	/**
	 * 调用总部AOP接口
	 */
	CALL_CB_AOP_ABLIT("800","callCbAopAbilitService"),
	
	/**
	 * 国政通身份验证
	 */
	CALL_GZT_ABLIT("900","callGztAbilitService"),
	
	/**
	 * 调用重启本地BSS开户证件上传接口
	 */
	CALL_SEND_PHOTO_ABLIT("1000","callSendPhotoAbilitService"),
	
	/**
	 * 调用东方国信顺丰接口
	 */
	CALL_SF_ORDER_SERVICE_ABLIT("1100","callSfOrderServiceAbilitService"),
	
	
	/**
	 * 海南短信下行
	 */
	CALL_SEND_MESSAGE_ABLIT("1500","callSendMessageAbilitService");
	
	private String interfaceType;
	
	private String interfaceName;
	
	public String getInterfaceType() {
		return interfaceType;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	private EnInterfaceType(String interfaceType, String interfaceName) {
		this.interfaceType = interfaceType;
		this.interfaceName = interfaceName;
	}
	
	public static EnInterfaceType getEnInterfaceTypeByType(String interfaceType) {
		for (EnInterfaceType interType : EnInterfaceType.values()) {
			if (interType.getInterfaceType().equals(interfaceType)) {
				return interType;
			}
		}
		return null;
	}
}
