/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年10月15日 上午9:51:07
 * @Title: UssExceptionManager.java
 * @Package com.tydic.unicom.exception
 * @Description: TODO
 */
package com.tydic.unicom.exception;


/**
 * <p></p>
 * @author yangfei 2014年10月15日 上午9:51:07
 * @ClassName UssExceptionManager
 * @Description TODO 统一销售平台异常管理类，如果其他项目有类似的，请自己新增该管理类
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年10月15日
 * @modify by reason:{方法名}:{原因}
 */
public class UssExceptionManager {
	
	private static String SYSTEM = "uss";
	/**
	 * 配置在java_base里的exception的key值
	 */
	private static String KEY_USS_ORDINARY_EXCEPTION = "uss_ordinary_exception";
	//3G普通预提交异常key值
	private static String KEY_ESS_NORMAL_PRESUBMIT_EXCEPTION = "key_ess_normal_preSubmit";
	//3G普通计费异常key值
	private static String KEY_ESS_NORMAL_CHARGES_EXCEPTION = "key_ess_normal_charges_exception";
	//3Giphone预提交异常key值
	private static String KEY_ESS_IPHONE_PRESUBMIT_EXCEPTION = "key_ess_iphone_preSubmit_exception";
	//3G终端计费异常
	private static String KEY_ESS_TERMINAL_CHARGES_EXCEPTION = "key_ess_terminal_charges_exception";
	//3G终端预提交异常key值
	private static String KEY_ESS_TERMINAL_PRESUBMIT_EXCEPTION = "key_ess_terminal_preSubmit_exception";
	//3Giphone提交异常key值
	private static String KEY_ESS_IPHONE_CHARGES_EXCEPTION = "key_ess_iphone_charges_exception";
	private UssExceptionManager(){
		
	}
	/**
	 * 
	 * @author yangfei 2014年10月15日 上午9:54:50
	 * @Method: throwOrdinaryException 
	 * @Description: TODO USS普通异常类
	 * @param message
	 * @throws Exception 
	 * @throws
	 */
	public static void throwOrdinaryException(String message) throws Exception{
		ExceptionManager.instance().throwException(SYSTEM,KEY_USS_ORDINARY_EXCEPTION, message);
	}
	
	/**
	 * @author huangweixing 2014年10月16日 上午10:35:50
	 * @Method: throwESSNormalPreSubmit 
	 * @Description: TODO  ESS普通预提交异常类
	 * @param message
	 * @throws Exception 
	 * @throws 
	 */
	public static void throwESSNormalPreSubmitException(String message) throws Exception {
		ExceptionManager.instance().throwException(SYSTEM,KEY_ESS_NORMAL_PRESUBMIT_EXCEPTION, message);
	}
	/**
	 * 
	 * @author fanbaoquan 2014年10月18日 上午10:52:16
	 * @Method: throwESSNormalChargesException 
	 * @Description: ess普通计费异常
	 * @param message
	 * @throws Exception 
	 * @throws
	 */
	public static void throwESSNormalChargesException(String message) throws Exception{
	    ExceptionManager.instance().throwException(SYSTEM,KEY_ESS_NORMAL_CHARGES_EXCEPTION, message);
	}
	/**
	 * 
	 * @author fanbaoquan 2014年10月21日 上午10:03:11
	 * @Method: throwEssIphoneChargesException 
	 * @Description: ess iphone计费异常
	 * @param message
	 * @throws Exception 
	 * @throws
	 */
	public static void throwEssIphoneChargesException(String message) throws Exception{
	    ExceptionManager.instance().throwException(SYSTEM,KEY_ESS_IPHONE_CHARGES_EXCEPTION, message);
	}
	/**
	 * 
	 * @author fanbaoquan 2014年10月27日 上午9:40:35
	 * @Method: throwEssTerminalChargesException 
	 * @Description: ess 终端计费异常
	 * @param message
	 * @throws Exception 
	 * @throws
	 */
	public static void throwEssTerminalChargesException(String message) throws Exception{
	    ExceptionManager.instance().throwException(KEY_ESS_TERMINAL_CHARGES_EXCEPTION, message);
	}
	/**
	 * @author huangweixing 2014年10月17日 下午7:51:40
	 * @Method: throwESSIphonePreSubmitException 
	 * @Description: TODO ESSiphone预提交异常类
	 * @param message
	 * @throws Exception 
	 * @throws 
	 */
	public static void throwESSIphonePreSubmitException(String message) throws Exception {
		ExceptionManager.instance().throwException(SYSTEM,KEY_ESS_IPHONE_PRESUBMIT_EXCEPTION, message);
	}
	/**
	 * @author huangweixing 2014年10月17日 下午7:51:42
	 * @Method: throwESSTerminalPreSubmitException 
	 * @Description: TODO  ESS终端预提交异常类
	 * @param message
	 * @throws Exception 
	 * @throws 
	 */
	public static void throwESSTerminalPreSubmitException(String message) throws Exception {
		ExceptionManager.instance().throwException(SYSTEM,KEY_ESS_TERMINAL_PRESUBMIT_EXCEPTION, message);
	}
}
