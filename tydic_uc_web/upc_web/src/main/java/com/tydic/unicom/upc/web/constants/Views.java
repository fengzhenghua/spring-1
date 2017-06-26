/**
 * @ProjectName: uss_web
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yuhao
 * @date: 2017年01月12日 上午10:52:18
 * @Title: Views.java
 * @Package com.tydic.unicom.crm.web.uss.constants
 * @Description: TODO
 */
package com.tydic.unicom.upc.web.constants;


/**
 * <p></p>
 * @author yuhao 2017年01月12日 上午10:52:18
 * @ClassName Views
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月15日
 * @modify by reason:{方法名}:{原因}
 */
public class Views {
	/**常量------------------------------------------------------------------*/
	
	public static final String UPC_BUSI_PAY_CHOICE = ControllerMappings.UPC_BUSI + "/cashierDesk";
	
	
	public static final String UPC_BUSI_WX_SCAN_PAY = ControllerMappings.UPC_BUSI + "/payWeixinBarCode";
	public static final String UPC_BUSI_WX_QR_PAY = ControllerMappings.UPC_BUSI + "/payWeixinQrCode";
	public static final String UPC_BUSI_ALI_SCAN_PAY = ControllerMappings.UPC_BUSI + "/payZhifubaoBarCode";
	public static final String UPC_BUSI_ALI_QR_PAY = ControllerMappings.UPC_BUSI + "/payZhifubaoQrCode";
	public static final String UPC_BUSI_CASHIER_REDIRECT = ControllerMappings.UPC_BUSI + "/cashierRedirect";
	
	
	/** APP端的界面*/
	public static final String UPC_APP_PAY_CHOICE = ControllerMappings.APP_BUSI + "/appCashierDesk";
	public static final String UPC_APP_WX_SCAN_PAY = ControllerMappings.APP_BUSI + "/appPayWeixinBarCode";
	public static final String UPC_APP_WX_QR_PAY = ControllerMappings.APP_BUSI + "/appPayWeixinQrCode";
	public static final String UPC_APP_ALI_SCAN_PAY = ControllerMappings.APP_BUSI + "/appPayZhifubaoBarCode";
	public static final String UPC_APP_ALI_QR_PAY = ControllerMappings.APP_BUSI + "/appPayZhifubaoQrCode";
	
}
