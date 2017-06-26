/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月6日 上午11:25:37
 * @Title: EcaopServ.java
 * @Package com.tydic.unicom.service.ecaop.service.interfaces
 * @Description: TODO
 */
package com.tydic.unicom.service.ecaop.service.interfaces;

import com.tydic.unicom.service.ecaop.vo.BaseRequestVo;
import com.tydic.unicom.service.ecaop.vo.BaseResponseVo;


/**
 * <p></p>
 * @author yangfei 2014年11月6日 上午11:25:37
 * @ClassName EcaopServ
 * @Description TODO ecaop接口类
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月6日
 * @modify by reason:{方法名}:{原因}
 */
public interface EcaopServ {
	/**
	 * @param <T>
	 * 
	 * @author yangfei 2014年11月6日 上午11:41:49
	 * @Method: remoteCall 
	 * @Description: TODO ecaop远程调用，
	 * @param baseRequestRo
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public  BaseResponseVo remoteCall(BaseRequestVo baseRequestVo,Class clazz) throws Exception;
	
	/**
	 * @param <T>
	 * 
	 * @author YUYISHI 2014年11月6日 上午11:41:49
	 * @Method: remoteCall 
	 * @Description: TODO 智慧沃家ecaop远程调用，
	 * @param baseRequestRo
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public  BaseResponseVo remoteWoCall(BaseRequestVo baseRequestVo,Class clazz,String bizkey) throws Exception;

	/**
	 * @param <T>
	 * 
	 * @author gaowei 2016年04月19日 上午11:41:49
	 * @Method: remoteCall 
	 * @Description: TODO 4G客户资料查询ecaop远程调用，
	 * @param baseRequestRo
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public  BaseResponseVo remoteQry4GCallAbility(BaseRequestVo baseRequestVo,Class clazz,String bizkey) throws Exception;
	
	/**
	 * @param <T>
	 * 
	 * @author gaowei 2016年04月19日 上午11:41:49
	 * @Method: remoteCall 
	 * @Description: TODO 4G欠费查询ecaop远程调用，
	 * @param baseRequestRo
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public  BaseResponseVo remoteQry4GOweFeeCallAbility(BaseRequestVo baseRequestVo,Class clazz,String bizkey) throws Exception;
	/**
	 * @param <T>
	 * 
	 * @author gaowei 2016年04月19日 上午11:41:49
	 * @Method: remoteCall 
	 * @Description: TODO 4G月查询ecaop远程调用，
	 * @param baseRequestRo
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public  BaseResponseVo remoteQry4GBalanceCallAbility(BaseRequestVo baseRequestVo,Class clazz,String bizkey) throws Exception;

}
