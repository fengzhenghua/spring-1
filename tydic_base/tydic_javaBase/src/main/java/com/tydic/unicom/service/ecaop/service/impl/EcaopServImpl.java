/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月6日 上午11:35:32
 * @Title: EcaopServImpl.java
 * @Package com.tydic.unicom.service.ecaop.service.impl
 * @Description: TODO
 */
package com.tydic.unicom.service.ecaop.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.tydic.unicom.exception.UssExceptionManager;
import com.tydic.unicom.service.ecaop.annotation.EcaopField;
import com.tydic.unicom.service.ecaop.annotation.EcaopField.EcaopFieldType;
import com.tydic.unicom.service.ecaop.constants.Constant;
import com.tydic.unicom.service.ecaop.service.interfaces.EcaopServ;
import com.tydic.unicom.service.ecaop.vo.BaseRequestVo;
import com.tydic.unicom.service.ecaop.vo.BaseResponseVo;
import com.tydic.unicom.service.ecaop.vo.BusiExResponseVo;
import com.tydic.unicom.service.ecaop.vo.ChgRequestVo;
import com.tydic.unicom.service.ecaop.vo.ChgRequestVo.Msg;
import com.tydic.unicom.service.ecaop.vo.ChgRequestVo.ResourcesInfo;
import com.tydic.unicom.service.ecaop.vo.ChgResponseVo;
import com.tydic.unicom.service.ecaop.vo.Para;
import com.tydic.unicom.service.ecaop.vo.SysExResponseVo;

/**
 * <p>
 * </p>
 * @author yangfei 2014年11月6日 上午11:35:32
 * @ClassName EcaopServImpl
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月6日
 * @modify by reason:{方法名}:{原因}
 */
@Service("EcaopServ")
public class EcaopServImpl implements EcaopServ {
	
	private static Logger logger=LoggerFactory.getLogger(EcaopServImpl.class);
	
	/**
	 * 
	 * @author yangfei 2014年11月6日 上午11:41:49
	 * @Method: remoteCall
	 * @Description: TODO ecaop远程调用
	 * @param baseRequestVo
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public BaseResponseVo remoteCall(BaseRequestVo baseRequestVo, Class clazz) throws Exception {
		/* 省份接口调用方式，通过分支走不通的接口 */
		String branchCode = "";
		branchCode = Constant.USS_AOP_BRANCH_CODE;//分支判断统过能力平台调用接口还是直接调用接口
		if ("abilityTerrace".equals(branchCode)) {
			return remoteCallAbility(baseRequestVo, clazz);
		} else {
			return remoteCallDefault(baseRequestVo, clazz);
		}
//		logger.info("-----------ECAOP接口调用请求报文-----------");
//		logger.info(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
//		logger.info("-----------ECAOP接口调用请求报文-----------");
//		URL url = new URL(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
//		//URLConnection rulConnection = url.openConnection();
//		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
//		HttpURLConnection connet = null;
//		try {
//			connet = (HttpURLConnection)url.openConnection();
//			connet.setConnectTimeout(80000);
//			/*connet.setRequestMethod("POST"); 
//			connet.setDoOutput(true);
//			connet.setDoInput(true);
//			connet.setUseCaches(false);
//			connet.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//			connet.setRequestProperty("accept","text/xml;text/html");
//			connet.connect();
//			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
//			out.print(baseRequestVo.requestMsg());
//	        out.flush();
//	        out.close(); */
//			BufferedReader brd = null;
//			try {
//	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
//            } catch (Exception e) {
//            	brd = new BufferedReader(new InputStreamReader(connet.getErrorStream()));
//            }
//			String result = brd.readLine();
//			JSONObject jsonobject = JSONObject.fromObject(result);
//			logger.info("-----------ECAOP接口调用返回报文-----------");
//			logger.info(result);
//			logger.info("-----------ECAOP接口调用返回报文-----------");
//			logger.info("connet.getResponseCode()="+connet.getResponseCode());
//			if (connet.getResponseCode() == 560) {
//				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
//			} else if (connet.getResponseCode() == 600) {
//				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
//			} else if (connet.getResponseCode() == 200) {
//				return (BaseResponseVo) parse(jsonobject, clazz);
//			} else {
//				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
//		} finally {
//			if (null != connet) {
//				connet.disconnect();
//			}
//		}
//		return null;
	}
	
	@Override
	public BaseResponseVo remoteWoCall(BaseRequestVo baseRequestVo, Class clazz,String bizkey)
			throws Exception {

		/* 省份接口调用方式，通过分支走不通的接口 */
		String branchCode = "";
		branchCode = Constant.USS_AOP_BRANCH_CODE;//分支判断统过能力平台调用接口还是直接调用接口
		if ("abilityTerrace".equals(branchCode)) {
			return remoteWoCallAbility(baseRequestVo, clazz, bizkey);
		} else {
			return remoteWoCallDefault(baseRequestVo, clazz, bizkey);
		}

//		logger.info("-----------ECAOP接口调用请求报文-----------");
//		logger.info(Constant.ECAOP_URL_TEST+"?"+baseRequestVo.requestLanMsg(bizkey));
//		logger.info("-----------ECAOP接口调用请求报文-----------");
//		//String zht="http://132.35.81.217:8000/aop/test?timestamp=2015-11-24 11:44:43&apptx=1820151124114443925&appkey=hbpre.sub&bizkey=TS-MI-001&method=ecaop.trades.sell.mix.newu.open.sub&msg={'subOrderInfo':[{'feeInfo':[{'feeId':'100005','feeCategory':'2','reliefFee':'0','origFee':'30000','realFee':'30000','feeDes':'%5B%E9%A2%84%E5%AD%98%5D%E8%90%A5%E4%B8%9A%E5%8E%85%E6%94%B6%E5%85%A5%28%E8%90%A5%E4%B8%9A%E7%BC%B4%E8%B4%B9%29_%E6%99%AE%E9%80%9A%E9%A2%84%E5%AD%98%E6%AC%BE%28%E4%B8%8D%E5%8F%AF%E6%B8%85%E9%80%80%29%29'}],'subOrderId':'1815120156334900'},{'feeInfo':[{}],'subOrderId':'1815120156334902'}],'channelType':'1020300','operatorId':'gaojj9','payInfo':{'payType':'10'},'channelId':'18b0kw8','origTotalFee':'30000','provOrderId':'1815120156334900','province':'18','district':'18a05r','city':'188'}";
//		
//
//	//	String yht="http://132.35.81.217:8000/aop/test?timestamp=2015-12-7 09:45:36&apptx=1820151124114336490&appkey=hbpre.sub&bizkey=TS-MI-001&method=ecaop.trades.sell.mix.newu.open.app&msg={'channelType':'1020300','operatorId':'gaojj9','channelId':'18b0kw8','province':'18','district':'18a05r','ordersId':'1115112400666404','city':'188','mixTemplate':{'mixCustInfo':{'customerName':'玉义仕','certAdress':'广西南宁市邕宁区','custId':'1815070311756660','certExpireDate':'20180225','certNum':'130185198812033137','certType':'02','checkType':'03','custType':'1'},'productInfo':[{'productMode':'0','firstMonBillMode':'01','productId':'30000001'},{'productMode':'1','firstMonBillMode':'01','productId':'89014705'}]},'phoneTemplate':[{'phoneRecomInfo':{'recomPersonName':'%E5%BC%A0%E7%8E%AE','recomPersonId':'1803183619','recomDepartId':'18b0gng'},'phoneCustInfo':{'customerName':'玉义是','certAdress':'广西南宁南','contactPhone':'123','certExpireDate':'20180225','certNum':'130185198812033137','certType':'02','checkType':'03','contactPerson':'123','contactAddress':'%E6%B2%B3%E5%8C%97%E7%9C%81%E9%B9%BF%E6%B3%89%E5%B8%82%E4%B8%8A%E5%AF%A8%E4%B9%A1%E5%8C%97%E5%AF%A8%E6%9D%91%E4%B8%AD%E5%BF%83%E8%B7%AF123%E5%8F%B7','custType':'0'},'installMode':'0','mainNumberTag':'0','serialNumber':'18633031400','phoneProduct':[{'productMode':'1','productId':'89002922'}],'serType':'2'}]}";
//
//		//URL url = new URL(yht);
//		
//		
//		URL url = new URL(Constant.ECAOP_URL_TEST+"?"+baseRequestVo.requestLanMsg(bizkey));
//		
//		//URLConnection rulConnection = url.openConnection();
//		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
//		HttpURLConnection connet = null;
//		try {
//			connet = (HttpURLConnection)url.openConnection();
//			connet.setConnectTimeout(60000);
//			/*connet.setRequestMethod("POST"); 
//			connet.setDoOutput(true);
//			connet.setDoInput(true);
//			connet.setUseCaches(false);
//			connet.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//			connet.setRequestProperty("accept","text/xml;text/html");
//			connet.connect();
//			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
//			out.print(baseRequestVo.requestMsg());
//	        out.flush();
//	        out.close(); */
//			BufferedReader brd = null;
//			try {
//	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
//            } catch (Exception e) {
//            	brd = new BufferedReader(new InputStreamReader(connet.getErrorStream()));
//            }
//			String result = brd.readLine();
//			JSONObject jsonobject = JSONObject.fromObject(result);
//			logger.info("-----------ECAOP接口调用返回报文-----------");
//			logger.info(result);
//			logger.info("-----------ECAOP接口调用返回报文-----------");
//			logger.info("connet.getResponseCode()="+connet.getResponseCode());
//			if (connet.getResponseCode() == 560) {
//				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
//			} else if (connet.getResponseCode() == 600) {
//				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
//			} else if (connet.getResponseCode() == 200) {
//				return (BaseResponseVo) parse(jsonobject, clazz);
//			} else {
//				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
//		} finally {
//			if (null != connet) {
//				connet.disconnect();
//			}
//		}
//		return null;
	}
	/**
	 * 智慧沃家默认调接口方式
	 * @param baseRequestVo
	 * @param clazz
	 * @param bizkey
	 * @return
	 * @throws Exception
	 */
	public BaseResponseVo remoteWoCallDefault(BaseRequestVo baseRequestVo, Class clazz,String bizkey)
			throws Exception {

		logger.info("-----------ECAOP接口调用请求报文-----------");
		logger.info(Constant.ECAOP_URL_TEST+"?"+baseRequestVo.requestLanMsg(bizkey));
		logger.info("-----------ECAOP接口调用请求报文-----------");
		//String zht="http://132.35.81.217:8000/aop/test?timestamp=2015-11-24 11:44:43&apptx=1820151124114443925&appkey=hbpre.sub&bizkey=TS-MI-001&method=ecaop.trades.sell.mix.newu.open.sub&msg={'subOrderInfo':[{'feeInfo':[{'feeId':'100005','feeCategory':'2','reliefFee':'0','origFee':'30000','realFee':'30000','feeDes':'%5B%E9%A2%84%E5%AD%98%5D%E8%90%A5%E4%B8%9A%E5%8E%85%E6%94%B6%E5%85%A5%28%E8%90%A5%E4%B8%9A%E7%BC%B4%E8%B4%B9%29_%E6%99%AE%E9%80%9A%E9%A2%84%E5%AD%98%E6%AC%BE%28%E4%B8%8D%E5%8F%AF%E6%B8%85%E9%80%80%29%29'}],'subOrderId':'1815120156334900'},{'feeInfo':[{}],'subOrderId':'1815120156334902'}],'channelType':'1020300','operatorId':'gaojj9','payInfo':{'payType':'10'},'channelId':'18b0kw8','origTotalFee':'30000','provOrderId':'1815120156334900','province':'18','district':'18a05r','city':'188'}";
		

	//	String yht="http://132.35.81.217:8000/aop/test?method=ecaop.trades.sell.mix.newu.open.app&appkey=uss.sub&apptx=5843807417524836045&timestamp=2016-3-8 14:45:37&bizkey=TS-MI-001&msg={'channelId':'59a0051','channelType':'1010400','city':'591','district':'592004','mixTemplate':{'acctInfo':[{'accountPayType':'10','createOrExtendsAcct':'0'}],'mixCustInfo':{'certAdress':'广西宾阳县芦圩镇北街村委会三十二队18号','certExpireDate':'20151206','certNum':'452123198906185518','certType':'02','custId':'5915120913087269','custType':'1','customerName':'谭帮振'},'productInfo':[{'firstMonBillMode':'02','productId':'30000001','productMode':'0'},{'firstMonBillMode':'02','productId':'89014705','productMode':'1'}]},'operatorId':'NNPT00','ordersId':'20151223112437101934','phoneTemplate':[{'installMode':'0','mainNumberTag':'1','phoneCustInfo':{'certAdress':'广西宾阳县芦圩镇北街村委会三十二队18号','certExpireDate':'20151206','certNum':'452123198906185518','certType':'02','contactAddress':'广西宾阳县芦圩镇北街村委会三十二队18号','contactPerson':'谭帮振','contactPhone':'07712354789','custId':'5915120913087269','custType':'1','customerName':'谭帮振'},'phoneProduct':[{'productId':'89002922','productMode':'1'}],'phoneRecomInfo':{'recomDepartId':'59a0035','recomPersonId':'5902261464','recomPersonName':'廖娟'},'serType':'1','serialNumber':'18587589740'}],'province':'59'}";
		
		//String ho="http://132.35.81.217:8000/aop/aopservlet?method=ecaop.trades.check.oldu.check&appkey=uss.sub&apptx=7021084243750913220&timestamp=2016-03-08 09:29:10&bizkey=CS-003&msg={'bipType':'2','channelId':'59a0051','channelType':'1010400','city':'591','district':'592004','numId':'18607710028','opeSysType':'1','operatorId':'NNPT00','productInfo':[{'productId':'89016253'}],'province':'59'}";
		logger.info("-----------ECAOP接口调用请求报文-----------");
		//logger.info(yht);
		//URL url = new URL(yht);
		
		
		URL url = new URL(Constant.ECAOP_URL_TEST+"?"+baseRequestVo.requestLanMsg(bizkey));
		
		//URLConnection rulConnection = url.openConnection();
		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
		HttpURLConnection connet = null;
		try {
			connet = (HttpURLConnection)url.openConnection();
			connet.setConnectTimeout(60000);
			/*connet.setRequestMethod("POST"); 
			connet.setDoOutput(true);
			connet.setDoInput(true);
			connet.setUseCaches(false);
			connet.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			connet.setRequestProperty("accept","text/xml;text/html");
			connet.connect();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
			out.print(baseRequestVo.requestMsg());
	        out.flush();
	        out.close(); */
			BufferedReader brd = null;
			try {
	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
            } catch (Exception e) {
            	brd = new BufferedReader(new InputStreamReader(connet.getErrorStream()));
            }
			String result = brd.readLine();
			JSONObject jsonobject = JSONObject.fromObject(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info("connet.getResponseCode()="+connet.getResponseCode());
			if (connet.getResponseCode() == 560) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
			} else if (connet.getResponseCode() == 600) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			} else if (connet.getResponseCode() == 200) {
				return (BaseResponseVo) parse(jsonobject, clazz);
			} else {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
		} finally {
			if (null != connet) {
				connet.disconnect();
			}
		}
		return null;
	
	}
	
	/**
	 * 智慧沃家通过能力平台调接口方式
	 * @param baseRequestVo
	 * @param clazz
	 * @param bizkey
	 * @return
	 * @throws Exception
	 */
	public BaseResponseVo remoteWoCallAbility(BaseRequestVo baseRequestVo, Class clazz,String bizkey)
			throws Exception {
		logger.info("-----------ECAOP接口调用请求报文-----------");
		//logger.info(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
//		URL url = new URL(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
		//URLConnection rulConnection = url.openConnection();
		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
		baseRequestVo.setBizkey(bizkey);
		Map<String,Object> parm = new HashMap<String, Object>();
		parm.put("app_key",Constant.USS_APP_KEY);
		parm.put("app_password",Constant.USS_APP_PWD);
		parm.put("api_version",Constant.USS_APP_VER);
		JSONObject parmJsonObject = JSONObject.fromObject(baseRequestVo);
		baseRequestVo.removeEmpNodesObj(parmJsonObject);
		parmJsonObject.put("opername", Constant.USS_OPER_NAME);
		parm.put("params",parmJsonObject.toString());
		
		String parmStr = JSON.json(parm);
		
		logger.info("json:" + parmStr);
		
		URL url = new URL(Constant.USS_URL);
		HttpURLConnection connet = null;
		try {
			connet = (HttpURLConnection)url.openConnection();
			connet.setConnectTimeout(60000);
			connet.setRequestMethod("POST"); 
			connet.setDoOutput(true);
			connet.setDoInput(true);
			connet.setUseCaches(false);
			connet.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			//connet.setRequestProperty("accept","text/xml;text/html");
			connet.connect();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
			out.print(parmStr);
	        out.flush();
	        out.close(); 
			BufferedReader brd = null;
			try {
	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
            } catch (Exception e) {
            	e.printStackTrace();
            	System.out.println("errorr-----------------------------");
            	brd = new BufferedReader(new InputStreamReader(connet.getErrorStream()));
            }
			String result = brd.readLine();
			JSONObject jsonobject = JSONObject.fromObject(result);
			
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info("connet.getResponseCode()="+connet.getResponseCode());
			if (connet.getResponseCode() == 560) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
			} else if (connet.getResponseCode() == 600) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			} else if (connet.getResponseCode() == 200) {
				if(!"".equals(jsonobject.get("code"))  && jsonobject.get("code") != null){//http state 200 有可能存在保存信息的情况，需要判断是否存在code
					logger.info("返回异常: 编码：" + jsonobject.get("code") + " 错误信息:" + jsonobject.get("detail"));
					return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
				}else{
					return (BaseResponseVo) parse(jsonobject, clazz);
				}
			} else {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
		} finally {
			if (null != connet) {
				connet.disconnect();
			}
		}
		return null;
	
	}
	
	/**
	 * 4G用户信息通过能力平台调接口方式
	 * @param baseRequestVo
	 * @param clazz
	 * @param bizkey
	 * @return
	 * @throws Exception
	 */
	public BaseResponseVo remoteQry4GCallAbility(BaseRequestVo baseRequestVo, Class clazz,String bizkey)
			throws Exception {
		logger.info("-----------ECAOP接口调用请求报文-----------");
		//logger.info(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
//		URL url = new URL(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
		//URLConnection rulConnection = url.openConnection();
		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
		baseRequestVo.setBizkey(bizkey);
		Map<String,Object> parm = new HashMap<String, Object>();
		parm.put("app_key",Constant.USS_APP_KEY);
		parm.put("app_password",Constant.USS_APP_PWD);
		parm.put("api_version",Constant.USS_APP_VER);
		JSONObject parmJsonObject = JSONObject.fromObject(baseRequestVo);
		baseRequestVo.removeEmpNodesObj(parmJsonObject);
		parmJsonObject.put("opername", Constant.USS_OPER_NAME);
		parm.put("params",parmJsonObject.toString());
		
		String parmStr = JSON.json(parm);
		
		logger.info("json:" + parmStr);
		
		URL url = new URL(Constant.USS_URL);
		HttpURLConnection connet = null;
		try {
			connet = (HttpURLConnection)url.openConnection();
			connet.setConnectTimeout(60000);
			connet.setRequestMethod("POST"); 
			connet.setDoOutput(true);
			connet.setDoInput(true);
			connet.setUseCaches(false);
			connet.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			//connet.setRequestProperty("accept","text/xml;text/html");
			connet.connect();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
			out.print(parmStr);
	        out.flush();
	        out.close(); 
			BufferedReader brd = null;
			try {
	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
            } catch (Exception e) {
            	e.printStackTrace();
            	System.out.println("errorr-----------------------------");
            	brd = new BufferedReader(new InputStreamReader(connet.getErrorStream()));
            }
			String result = brd.readLine();
			JSONObject jsonobject = JSONObject.fromObject(result);
			
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info("connet.getResponseCode()="+connet.getResponseCode());
			if (connet.getResponseCode() == 560) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
			} else if (connet.getResponseCode() == 600) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			} else if (connet.getResponseCode() == 200) {
				if(!"".equals(jsonobject.get("code"))  && jsonobject.get("code") != null){//http state 200 有可能存在保存信息的情况，需要判断是否存在code
					logger.info("返回异常: 编码：" + jsonobject.get("code") + " 错误信息:" + jsonobject.get("detail"));
					return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
				}else{
					return (BaseResponseVo) parse(jsonobject, clazz);
				}
			} else {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
		} finally {
			if (null != connet) {
				connet.disconnect();
			}
		}
		return null;
	
	}
	
	/**
	 * 4G用户欠费余额通过能力平台调接口方式
	 * @param baseRequestVo
	 * @param clazz
	 * @param bizkey
	 * @return
	 * @throws Exception
	 */
	@Override
	public BaseResponseVo remoteQry4GOweFeeCallAbility(BaseRequestVo baseRequestVo, Class clazz, String bizkey)
			throws Exception {
		logger.info("-----------ECAOP接口调用请求报文-----------");
		//logger.info(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
//		URL url = new URL(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
		//URLConnection rulConnection = url.openConnection();
		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
		baseRequestVo.setBizkey(bizkey);
		Map<String,Object> parm = new HashMap<String, Object>();
		parm.put("app_key",Constant.USS_APP_KEY);
		parm.put("app_password",Constant.USS_APP_PWD);
		parm.put("api_version",Constant.USS_APP_VER);
		JSONObject parmJsonObject = JSONObject.fromObject(baseRequestVo);
		baseRequestVo.removeEmpNodesObj(parmJsonObject);
		parmJsonObject.put("opername", Constant.USS_OPER_NAME);
		parm.put("params",parmJsonObject.toString());
		
		String parmStr = JSON.json(parm);
		
		logger.info("json:" + parmStr);
		
		URL url = new URL(Constant.USS_URL);
		HttpURLConnection connet = null;
		try {
			connet = (HttpURLConnection)url.openConnection();
			connet.setConnectTimeout(60000);
			connet.setRequestMethod("POST"); 
			connet.setDoOutput(true);
			connet.setDoInput(true);
			connet.setUseCaches(false);
			connet.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			//connet.setRequestProperty("accept","text/xml;text/html");
			connet.connect();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
			out.print(parmStr);
	        out.flush();
	        out.close(); 
			BufferedReader brd = null;
			try {
	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
            } catch (Exception e) {
            	e.printStackTrace();
            	System.out.println("errorr-----------------------------");
            	brd = new BufferedReader(new InputStreamReader(connet.getErrorStream()));
            }
			String result = brd.readLine();
			JSONObject jsonobject = JSONObject.fromObject(result);
			
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info("connet.getResponseCode()="+connet.getResponseCode());
			if (connet.getResponseCode() == 560) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
			} else if (connet.getResponseCode() == 600) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			} else if (connet.getResponseCode() == 200) {
				if(!"".equals(jsonobject.get("code"))  && jsonobject.get("code") != null){//http state 200 有可能存在保存信息的情况，需要判断是否存在code
					logger.info("返回异常: 编码：" + jsonobject.get("code") + " 错误信息:" + jsonobject.get("detail"));
					return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
				}else{
					return (BaseResponseVo) parse(jsonobject, clazz);
				}
			} else {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
		} finally {
			if (null != connet) {
				connet.disconnect();
			}
		}
		return null;
	
	}
	
	/**
	 * 4G用户欠费余额通过能力平台调接口方式
	 * @param baseRequestVo
	 * @param clazz
	 * @param bizkey
	 * @return
	 * @throws Exception
	 */
	@Override
	public BaseResponseVo remoteQry4GBalanceCallAbility(BaseRequestVo baseRequestVo, Class clazz, String bizkey)
			throws Exception {
		logger.info("-----------ECAOP接口调用请求报文-----------");
		//logger.info(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
//		URL url = new URL(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
		//URLConnection rulConnection = url.openConnection();
		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
		baseRequestVo.setBizkey(bizkey);
		Map<String,Object> parm = new HashMap<String, Object>();
		parm.put("app_key",Constant.USS_APP_KEY);
		parm.put("app_password",Constant.USS_APP_PWD);
		parm.put("api_version",Constant.USS_APP_VER);
		JSONObject parmJsonObject = JSONObject.fromObject(baseRequestVo);
		baseRequestVo.removeEmpNodesObj(parmJsonObject);
		parmJsonObject.put("opername", Constant.USS_OPER_NAME);
		parm.put("params",parmJsonObject.toString());
		
		String parmStr = JSON.json(parm);
		
		logger.info("json:" + parmStr);
		
		URL url = new URL(Constant.USS_URL);
		HttpURLConnection connet = null;
		try {
			connet = (HttpURLConnection)url.openConnection();
			connet.setConnectTimeout(60000);
			connet.setRequestMethod("POST"); 
			connet.setDoOutput(true);
			connet.setDoInput(true);
			connet.setUseCaches(false);
			connet.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			//connet.setRequestProperty("accept","text/xml;text/html");
			connet.connect();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
			out.print(parmStr);
	        out.flush();
	        out.close(); 
			BufferedReader brd = null;
			try {
	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
            } catch (Exception e) {
            	e.printStackTrace();
            	System.out.println("errorr-----------------------------");
            	brd = new BufferedReader(new InputStreamReader(connet.getErrorStream()));
            }
			String result = brd.readLine();
			JSONObject jsonobject = JSONObject.fromObject(result);
			
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info("connet.getResponseCode()="+connet.getResponseCode());
			if (connet.getResponseCode() == 560) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
			} else if (connet.getResponseCode() == 600) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			} else if (connet.getResponseCode() == 200) {
				if(!"".equals(jsonobject.get("code"))  && jsonobject.get("code") != null){//http state 200 有可能存在保存信息的情况，需要判断是否存在code
					logger.info("返回异常: 编码：" + jsonobject.get("code") + " 错误信息:" + jsonobject.get("detail"));
					return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
				}else{
					return (BaseResponseVo) parse(jsonobject, clazz);
				}
			} else {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
		} finally {
			if (null != connet) {
				connet.disconnect();
			}
		}
		return null;
	
	}
	
	/**
	 * 
	 * @author yangfei 2014年11月6日 下午4:40:14
	 * @Method: parse 
	 * @Description: TODO 解析JSON
	 * @param jsonobject
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * @throws
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
    private Object parse(JSONObject jsonobject, Class clazz) throws Exception{
		Object object = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.getAnnotations();
			EcaopField ecaopField = field.getAnnotation(EcaopField.class);
			if (null == ecaopField) {
				continue;
			}
			String filedName = field.getName();
			String type = ecaopField.type();
			String subType = ecaopField.subType();
			String className = ecaopField.className();
			StringBuilder sb = new StringBuilder("set");
			sb.append(filedName.substring(0, 1).toUpperCase());
			sb.append(filedName.substring(1, filedName.length()));
			Class c = String.class;
			if(EcaopFieldType.STRING.equals(type)){
				c = String.class;
			}else if(EcaopFieldType.LIST.equals(type)){
				c = List.class;
			}else{
				c = Class.forName(className);
			}
			Method method = clazz.getMethod(sb.toString(),c);
			if(EcaopFieldType.STRING.equals(type)){
				try {
	                method.invoke(object, jsonobject.getString(filedName));
                } catch (Exception e) {
                	 method.invoke(object, "");
                }
			}else if(EcaopFieldType.INT.equals(type)){
				try {
					method.invoke(object, jsonobject.getInt(filedName));
                } catch (Exception e) {
                	 method.invoke(object, 0);
                }
			}else if(EcaopFieldType.LONG.equals(type)){
				try {
					method.invoke(object, jsonobject.getLong(filedName));
                } catch (Exception e) {
                	 method.invoke(object, 0l);
                }
			}else if(EcaopFieldType.DOUBLE.equals(type)){
				try {
					method.invoke(object, jsonobject.getDouble(filedName));
                } catch (Exception e) {
                	 method.invoke(object, 0.0);
                }
			}else if(EcaopFieldType.BOOLEAN.equals(type)){
				try {
					method.invoke(object, jsonobject.getBoolean(filedName));
                } catch (Exception e) {
                	 method.invoke(object, false);
                }
			}else if(EcaopFieldType.OBJECT.equals(type)){
				try {
					method.invoke(object, parse(jsonobject.getJSONObject(filedName),Class.forName(className)));
                } catch (Exception e) {
                    try {
                	method.invoke(object, Class.forName(className).newInstance());
		    } catch (Exception e2) {
			method.invoke(object, new Object());
		    }
                	
                }
			}else if(EcaopFieldType.LIST.equals(type)){
				try {
					method.invoke(object, parse(jsonobject.getJSONArray(filedName),subType,Class.forName(className)));
                } catch (Exception e) {
                	method.invoke(object, new ArrayList());
                }
			}
		}
		return object;
	}
	/**
	 * 
	 * @author yangfei 2014年11月7日 上午10:19:38
	 * @Method: parse 
	 * @Description: TODO LIST解析
	 * @param jsonArray
	 * @param subType
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * @throws
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
    private Object parse(JSONArray jsonArray,String subType,Class clazz) throws Exception{
		List list = new ArrayList();
		for(Object o : jsonArray){
			if(EcaopFieldType.STRING.equals(subType)
					|| EcaopFieldType.INT.equals(subType)
					|| EcaopFieldType.LONG.equals(subType)
					|| EcaopFieldType.DOUBLE.equals(subType)
					|| EcaopFieldType.BOOLEAN.equals(subType)){
				list.add(o);
			}else if(EcaopFieldType.OBJECT.equals(subType)){
				list.add(parse((JSONObject) o ,clazz));
			}else if(EcaopFieldType.LIST.equals(subType)){
				list.add(parse((JSONArray) o,EcaopFieldType.OBJECT ,clazz));
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
	
		try {
	       // new EcaopServImpl().remoteWoCall(null, BaseResponseVo.class,"TS-MI-001");
			new EcaopServImpl().remoteWoCallDefault(null, BaseResponseVo.class,"TS-MI-001");
        } catch (Exception e) {
	        /** TODO Auto-generated catch block */
	        e.printStackTrace();
        }
    }
	/**
	 * 能力平台调用AOP接口
	 * @param baseRequestVo
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public BaseResponseVo remoteCallAbility(BaseRequestVo baseRequestVo, Class clazz) throws Exception{
		logger.info("-----------ECAOP接口调用请求报文-----------");
		//logger.info(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
//		URL url = new URL(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
		//URLConnection rulConnection = url.openConnection();
		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
		Map<String,Object> parm = new HashMap<String, Object>();
		parm.put("app_key",Constant.USS_APP_KEY);
		parm.put("app_password",Constant.USS_APP_PWD);
		parm.put("api_version",Constant.USS_APP_VER);
		JSONObject parmJsonObject = JSONObject.fromObject(baseRequestVo);
		baseRequestVo.removeEmpNodesObj(parmJsonObject);
		parmJsonObject.put("opername", Constant.USS_OPER_NAME);
		parm.put("params",parmJsonObject.toString());
		
		String parmStr = JSON.json(parm);
		
		logger.info("json:" + parmStr);
		
		URL url = new URL(Constant.USS_URL);
		HttpURLConnection connet = null;
		try {
			connet = (HttpURLConnection)url.openConnection();
			connet.setConnectTimeout(60000);
			connet.setRequestMethod("POST"); 
			connet.setDoOutput(true);
			connet.setDoInput(true);
			connet.setUseCaches(false);
			connet.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			//connet.setRequestProperty("accept","text/xml;text/html");
			connet.connect();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
			out.print(parmStr);
	        out.flush();
	        out.close(); 
			BufferedReader brd = null;
			try {
	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
            } catch (Exception e) {
            	e.printStackTrace();
            	System.out.println("errorr-----------------------------");
            	brd = new BufferedReader(new InputStreamReader(connet.getErrorStream()));
            }
			String result = brd.readLine();
			JSONObject jsonobject = JSONObject.fromObject(result);
			
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info("connet.getResponseCode()="+connet.getResponseCode());
			if (connet.getResponseCode() == 560) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
			} else if (connet.getResponseCode() == 600) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			} else if (connet.getResponseCode() == 200) {
				if(!"".equals(jsonobject.get("code"))  && jsonobject.get("code") != null){//http state 200 有可能存在保存信息的情况，需要判断是否存在code
					logger.info("返回异常: 编码：" + jsonobject.get("code") + " 错误信息:" + jsonobject.get("detail"));
					return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
				}else{
					return (BaseResponseVo) parse(jsonobject, clazz);
				}
			} else {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
		} finally {
			if (null != connet) {
				connet.disconnect();
			}
		}
		return null;
	}
	/**
	 * 默认调用AOP方式
	 * @param baseRequestVo
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public BaseResponseVo remoteCallDefault(BaseRequestVo baseRequestVo, Class clazz) throws Exception{

		logger.info("-----------ECAOP接口调用请求报文-----------");
		logger.info(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
		logger.info("-----------ECAOP接口调用请求报文-----------");
		URL url = new URL(Constant.ECAOP_URL+"?"+baseRequestVo.requestMsg());
		//URLConnection rulConnection = url.openConnection();
		//HttpURLConnection connet = (HttpURLConnection) rulConnection; 
		HttpURLConnection connet = null;
		try {
			connet = (HttpURLConnection)url.openConnection();
			connet.setConnectTimeout(60000);
			/*connet.setRequestMethod("POST"); 
			connet.setDoOutput(true);
			connet.setDoInput(true);
			connet.setUseCaches(false);
			connet.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			connet.setRequestProperty("accept","text/xml;text/html");
			connet.connect();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(connet.getOutputStream()));
			out.print(baseRequestVo.requestMsg());
	        out.flush();
	        out.close(); */
			BufferedReader brd = null;
			try {
	            brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
            } catch (Exception e) {
        	InputStream inputStream = connet.getErrorStream();
        	if(inputStream!=null){
        	    brd = new BufferedReader(new InputStreamReader(inputStream));
        	}
        	else{
        	    logger.error(e.getMessage());
        	}
            }
			String result = brd.readLine();
			JSONObject jsonobject = JSONObject.fromObject(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info("connet.getResponseCode()="+connet.getResponseCode());
			if (connet.getResponseCode() == 560) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, BusiExResponseVo.class);
			} else if (connet.getResponseCode() == 600) {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			} else if (connet.getResponseCode() == 200) {
				return (BaseResponseVo) parse(jsonobject, clazz);
			} else {
				return (BaseResponseVo)JSONObject.toBean(jsonobject, SysExResponseVo.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
		} finally {
			if (null != connet) {
				connet.disconnect();
			}
		}
		return null;

	}

}
