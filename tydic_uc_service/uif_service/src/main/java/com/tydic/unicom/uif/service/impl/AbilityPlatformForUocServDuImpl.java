package com.tydic.unicom.uif.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.open.rest.api.sdk.client.OrsException;
import com.tydic.unicom.exception.UssExceptionManager;
import com.tydic.unicom.uif.service.interfaces.AbilityPlatformForUocServDu;
import com.tydic.unicom.uif.service.interfaces.UifJsonToBeanServDu;
import com.tydic.unicom.uif.service.vo.UifPropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("AbilityPlatformForUocServDu")
public class AbilityPlatformForUocServDuImpl implements AbilityPlatformForUocServDu{
	
	Logger logger = Logger.getLogger(AbilityPlatformForUocServDuImpl.class);

	@Autowired
	private UifJsonToBeanServDu uifJsonToBeanServDu;

	
	@Autowired
	@Qualifier("uifPropertiesParamVo")
	private UifPropertiesParamVo uifPropertiesParamVo;
	
	


	@Override
	public UocMessage CallAbilityPlatformForUoc(String json_info,String interface_type,String interface_param_json,String interface_url)throws Exception{

		logger.info("*******************能力平台调用***********************");
		logger.info("*******************能力平台调用入参***********************");
		logger.info("*******************能力平台调用入参json_info***********************"+json_info);
		logger.info("*******************能力平台调用入参interface_type***********************"+interface_type);
		logger.info("*******************能力平台调用入参interface_param_json***********************"+interface_param_json);
		logger.info("*******************能力平台调用入参interface_url***********************"+interface_url);
		logger.info("*******************能力平台调用入参***********************");
		logger.info("*******************能力平台调用***********************");

		UocMessage message = new UocMessage();
		if("100".equals(interface_type) || "200".equals(interface_type) ||"300".equals(interface_type)  ||"600".equals(interface_type) ||"700".equals(interface_type) ||"900".equals(interface_type) ||"1000".equals(interface_type) ||"1100".equals(interface_type) ||"1300".equals(interface_type)){
			String url="";
			String token="";
			//String appKey = readPropertiesUtils.getPropertiesForUrl("new_appkey");
			String appKey = uifPropertiesParamVo.getNewAppkey();
			SimpleDateFormat sm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestamp1 = sm1.format(new Date());
			//String secret = readPropertiesUtils.getPropertiesForUrl("secret");
			String secret = uifPropertiesParamVo.getSecret();
			JSONObject data = new JSONObject();	
			String serviceName="";
			//重庆本地接口能力平台调用
			if("100".equals(interface_type)){
				//url = readPropertiesUtils.getPropertiesForUrl("local_url");
				url = uifPropertiesParamVo.getLocalUrl();
				//token = readPropertiesUtils.getPropertiesForUrl("local_token");
				token =uifPropertiesParamVo.getLocalToken();
				data=JSONObject.fromObject(json_info);
				serviceName=data.get("SERVICE_NAME").toString();
				logger.info("----------serviceName---------"+serviceName);
			}else if("200".equals(interface_type)){//aop接口通过能力平台调用
				String apptx = String.valueOf(apptx(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase())).replaceAll(
						"-", "");
				//url = readPropertiesUtils.getPropertiesForUrl("aop_url");
				url =uifPropertiesParamVo.getAopUrl();
				//token = readPropertiesUtils.getPropertiesForUrl("aop_token");
				token =uifPropertiesParamVo.getAopToken();
				Map<String, Object> map = new HashMap<String, Object>();
				map=uifJsonToBeanServDu.jsonToMap(interface_param_json);
				String method=(String)map.get("method");
				String bizkey=(String)map.get("bizkey");	
				data.put("method", method);
				data.put("timestamp", timestamp1);
				data.put("sign", "1");
				data.put("apptx", apptx);
				data.put("bizkey", bizkey);
				data.put("msg", JSONObject.fromObject(json_info));

			}else if("300".equals(interface_type)){//云销售
				//url = readPropertiesUtils.getPropertiesForUrl("wo_url");
				url =uifPropertiesParamVo.getWoUrl();
				//token = readPropertiesUtils.getPropertiesForUrl("wo_token");
				token =uifPropertiesParamVo.getWoToken();
				data=JSONObject.fromObject(json_info);
			}else if("600".equals(interface_type)){//东方国信接口
				//url = readPropertiesUtils.getPropertiesForUrl("df_url");
				url =uifPropertiesParamVo.getDfUrl();
				//token = readPropertiesUtils.getPropertiesForUrl("df_token");
				token=uifPropertiesParamVo.getDfToken();
				//String dfAppKey = readPropertiesUtils.getPropertiesForUrl("df_appkey");
				String dfAppKey = uifPropertiesParamVo.getDfAppkey();
				Map<String, Object> map = new HashMap<String, Object>();
				map=uifJsonToBeanServDu.jsonToMap(interface_param_json);
				String method=(String)map.get("method");				
				data.put("method", method);
				data.put("timestamp", timestamp1);
				data.put("appkey", dfAppKey);
				data.put("busiData", JSONObject.fromObject(json_info));				
			}else if("700".equals(interface_type)){//回调接口
				//url = readPropertiesUtils.getPropertiesForUrl("hd_url");
				url =uifPropertiesParamVo.getHdUrl();
				//token = readPropertiesUtils.getPropertiesForUrl("hd_token");
				token = uifPropertiesParamVo.getHdToken();
				//String dfAppKey = readPropertiesUtils.getPropertiesForUrl("hd_appkey");
				String dfAppKey =uifPropertiesParamVo.getHdAppKey();
				Map<String, Object> map = new HashMap<String, Object>();
				map=uifJsonToBeanServDu.jsonToMap(interface_param_json);
				String method=(String)map.get("method");
				JSONObject strategy = new JSONObject();
				strategy.put("method", method);
				strategy.put("timestamp", timestamp1);
				strategy.put("appkey", dfAppKey);
				strategy.put("busiData", JSONObject.fromObject(json_info));	
				data.put("sendUrl", interface_url);
				data.put("strategy", strategy);
			}else if("900".equals(interface_type)){//国政通身份证验证
				url = uifPropertiesParamVo.getGztUrl();
				token =uifPropertiesParamVo.getGztToken();
				data=JSONObject.fromObject(json_info);
			}else if("1000".equals(interface_type)){//开户证件上传接口
				url = uifPropertiesParamVo.getSendPhotoUrl();
				token =uifPropertiesParamVo.getSendPhotoToken();
				data=JSONObject.fromObject(json_info);
			}else if("1100".equals(interface_type)){//本地调用东方国信顺丰接口
				url = uifPropertiesParamVo.getLocalSfUrl();
				token =uifPropertiesParamVo.getLocalSfToken();
				data=JSONObject.fromObject(json_info);
			}else if("1300".equals(interface_type)){//本地调用东方国信顺丰接口
				url = uifPropertiesParamVo.getQryUserNumberUrl();
				token =uifPropertiesParamVo.getQryUserNumberToken();
				data=JSONObject.fromObject(json_info);
			}else{
				logger.info("无接口类型参数");
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("无接口类型参数");
				return message;
			}
			// 获取请求能力平台的配置数据

			String connectTimeout = "40000";
			String readTimeout = "40000";

			//组装发送数据
			//公共参数
			JSONObject tapService = new JSONObject();
			tapService.put("TOKEN",token);
			tapService.put("APP_KEY",appKey);
			tapService.put("COMMON_URL",url);
			tapService.put("SECRET",secret);
			if (connectTimeout!=null && !connectTimeout.equals("")){
				tapService.put("CONNECT_TIMEOUT",Integer.valueOf(connectTimeout));  //毫秒
			}else {
				tapService.put("CONNECT_TIMEOUT",10000);  //毫秒
			}
			if (readTimeout!=null && !readTimeout.equals("")){
				tapService.put("READ_TIMEOUT",Integer.valueOf(readTimeout)); //毫秒
			}else {
				tapService.put("READ_TIMEOUT",40000); //毫秒
			}

			data.put("TapSerivce", tapService);

			TapServiceClient tapServiceClient = new TapServiceClient();
			JSONObject response = new JSONObject();
			String result = null;
			String code=null;
			try {
				logger.info(data);
				response = tapServiceClient.performRequest(data);
				result=response.toString();
				logger.info("-----------能力平台接口调用返回报文-----------");
				logger.info(result);
				logger.info("-----------能力平台接口调用返回报文-----------");
				if("200".equals(interface_type)){
					if(response.get("code") !=null && !"".equals(response.get("code").toString())){
						code=response.get("code").toString();
					}else{
						code="200";
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", result);
					message.addArg("code", code);
				}else if("300".equals(interface_type)){
					if("0000".equals(response.get("respCode").toString())){
						code="200";
					}else{
						code="9999";
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", response.get("args").toString());
					message.addArg("code", code);
					message.addArg("msg", response.get("content").toString());
				}else if("100".equals(interface_type)){
					if("qryBusiFee".equals(serviceName)){		
						String respCode="";
						if(response.get("RspCode") != null){
							respCode = response.get("RspCode").toString();
						}
						if("0000".equals(respCode)){
							code="200";
						}else{
							code="9999";
						}
					}else if("modRealNameCustInfo".equals(serviceName)){
						String respCode="";
						if(response.get("code") != null){
							respCode = response.get("code").toString();
						}
						if("0000".equals(respCode)){
							code="200";
						}else{
							code="9999";
						}
						
					}else if("get_card_data".equals(serviceName)){
						String respCode="";
						if(response.get("status") != null){
							respCode = response.get("status").toString();
						}
						if("1".equals(respCode)){
							code="200";
						}else{
							code="9999";
						}
						
					}
					else{
						String respCode="";
						if(response.get("RespCode") != null){
							respCode = response.get("RespCode").toString();
						}
						if("0000".equals(respCode)){
							code="200";
						}else{
							code="9999";
						}
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", result);
					message.addArg("code", code);
				}else if("600".equals(interface_type)){
					if("00".equals(response.get("Resp_code").toString())){
						code="200";
					}else{
						code="9999";
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", result);
					message.addArg("code", code);				
				}else if("700".equals(interface_type)){
					if("00".equals(response.get("Resp_code").toString())){
						code="200";
					}else{
						code="9999";
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", result);
					message.addArg("code", code);				
				}else if("900".equals(interface_type)){
					if("200".equals(response.get("httpstatuscode").toString())){
						code="200";
					}else{
						code="9999";
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", result);
					message.addArg("code", code);				
				}else if("1000".equals(interface_type)){
					if("0000".equals(response.get("Resp_code").toString())){
						code="200";
					}else{
						code="9999";
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", result);
					message.addArg("code", code);				
				}else if("1100".equals(interface_type)){
					if("1".equals(response.get("code").toString())){
						code="200";
					}else{
						code="9999";
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", result);
					message.addArg("code", code);				
				}else if("1300".equals(interface_type)){
					if("1".equals(response.get("status").toString())){
						code="200";
					}else{
						code="9999";
					}
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("调用能力平台成功");
					message.addArg("json_info", result);
					message.addArg("code", code);				
				}

			} catch (JSONException e) {
				code="9999";
				response.put("code", "9999");
				response.put("detail", "响应报文异常，非json格式：" + e.getMessage());
				result=response.toString();
				logger.info(result);
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("调用能力平台失败");
				message.addArg("json_info", result);
				message.addArg("code", code);
				return message;
			} catch (OrsException e) {
				code="9999";
				response.put("code", "9999");
				response.put("detail", "响应报文异常：" + e.getMessage());
				result=response.toString();
				logger.info(result);
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("调用能力平台失败");
				message.addArg("json_info", result);
				message.addArg("code", code);
				return message;
			} catch (Exception e) {
				code="9999";
				response.put("code", "9999");
				response.put("detail", "异常信息：" + e.getMessage());
				result=response.toString();
				logger.info(result);
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("调用能力平台失败");
				message.addArg("json_info", result);
				message.addArg("code", code);
				return message;
			}
		}else if("400".equals(interface_type) || "500".equals(interface_type)){
			String code=null;
			String url="";
			String actionName="";
			if("400".equals(interface_type)){//海南本地
				//url= readPropertiesUtils.getPropertiesForUrl("USS_USL_AJAX_HN");
				url =uifPropertiesParamVo.getUssUslAjaxHn();
				//actionName=readPropertiesUtils.getPropertiesForUrl("USS_ACTION");
				actionName=uifPropertiesParamVo.getUssAction();
			}else if("500".equals(interface_type)){//顺丰接口
				//url= readPropertiesUtils.getPropertiesForUrl("sf_url");
				url =uifPropertiesParamVo.getSfUrl();
				//actionName=readPropertiesUtils.getPropertiesForUrl("USS_ACTION");
				actionName=uifPropertiesParamVo.getUssAction();
			}
			try{
				String returnUniBssXmlStr = callWebServiceByAxis2(url, actionName, json_info);
				returnUniBssXmlStr=returnUniBssXmlStr.replace("__", "_").replace("&lt;", '<'+"").replace("&gt;", '>'+"").replace("&quot;", '"'+"");
				//用新的字符编码生成字符串
				byte[] bs = returnUniBssXmlStr.getBytes();
				returnUniBssXmlStr=new String(bs,"utf-8");
				logger.info("==============能力平台返回结果报文===============");
				logger.info(returnUniBssXmlStr);
				logger.info("==============能力平台返回结果报文===============");
				if(returnUniBssXmlStr != null && !returnUniBssXmlStr.equals("")){
					if("400".equals(interface_type)){
						JSONObject response =  JSONObject.fromObject(returnUniBssXmlStr);
						String uss_head=(String) response.get("UNI_HEAD");
						JSONObject uss_head_response =  JSONObject.fromObject(uss_head);
						String rsp_code=(String) uss_head_response.get("RSP_CODE");
						if("00000".equals(rsp_code)){
							code="200";
						}else{
							code="9999";
						}
						message.setRespCode(RespCodeContents.SUCCESS);
						message.setContent("调用能力平台成功");
						message.addArg("json_info", returnUniBssXmlStr);
						message.addArg("code", code);
					}else if("500".equals(interface_type)){
						int startHead=returnUniBssXmlStr.indexOf("<Head>");
						int endHead=returnUniBssXmlStr.indexOf("</Head>");
						String resultHead = returnUniBssXmlStr.substring(startHead+6,endHead);
						logger.info("=============resultHead============="+resultHead);
						if("OK".equals(resultHead)){
							code="200";
						}else{
							code="9999";
						}
						message.setRespCode(RespCodeContents.SUCCESS);
						message.setContent("调用能力平台成功");
						message.addArg("json_info", returnUniBssXmlStr);
						message.addArg("code", code);
					}

				}else{
					code="9999";
					message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
					message.setContent("返回数据为空");
					message.addArg("code", code);
					return message;
				}
			}catch (AxisFault ex) {
				code="9999";
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("调用能力平台异常");
				message.addArg("code", code);
				return message;
			} catch (Exception e) {
				code="9999";
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("调用能力平台异常");
				message.addArg("code", code);
				return message;
			}


		}else if("800".equals(interface_type)){//海南接口直接调用AOP
			String code=null;
			//String url=readPropertiesUtils.getPropertiesForUrl("hn_aop_url");
			String url=uifPropertiesParamVo.getHnAopUrl();
			try{
				String result = remoteCallDefault(url, json_info);
				JSONObject jsonobject = JSONObject.fromObject(result);
				if(jsonobject.get("code") !=null && !"".equals(jsonobject.get("code").toString())){
					code=jsonobject.get("code").toString();
				}else{
					code="200";
				}
				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("调用能力平台成功");
				message.addArg("json_info", result);
				message.addArg("code", code);
			}catch (AxisFault ex) {
				code="9999";
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("调用能力平台异常");
				message.addArg("code", code);
				return message;
			} catch (Exception e) {
				code="9999";
				message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
				message.setContent("调用能力平台异常");
				message.addArg("code", code);
				return message;
			}


		}else{
			logger.info("无接口类型参数");
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("无接口类型参数");
			return message;
		}
		return message;
	}

	private Long apptx(String s) {
		Long sum = 0l;
		int tmp = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				tmp = c - '0';
			} else
				if (c >= 'A' && c <= 'F') {
					tmp = c - 'A' + 10;
				} else {
					break;
				}
			sum = sum * 16 + tmp;
		}
		return sum;
	}


	public String callWebServiceByAxis2(String URL, String actionName, String xmlStr) throws Exception{
		String returnXmlStr = "";
		logger.info("\n888888888888888888888888888888888888------USS能力平台请求报文------888888888888888888888888888888888888888888888\n"
				+"请求地址："+URL+"\n"
				+ xmlStr
				+ "\n888888888888888888888888888888888888------USS能力平台请求报文------888888888888888888888888888888888888888888888\n");
		try {
			EndpointReference endpointReference = new EndpointReference(URL);

			OMElement method = (new StAXOMBuilder(new ByteArrayInputStream(xmlStr.getBytes("utf-8")))).getDocumentElement();

			// 设置参数
			Options options = new Options();
			options.setAction(actionName);
			options.setTo(endpointReference);

			options.setTimeOutInMilliSeconds(200000);

			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);

			// 发送并得到结果，至此，调用成功，并得到了结果
			OMElement result = sender.sendReceive(method);

			returnXmlStr = result.toString();
		} catch (AxisFault ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("\n888888888888888888888888888888888888------USS能力平台返回报文------888888888888888888888888888888888888888888888\n"
				+ returnXmlStr
				+ "\n888888888888888888888888888888888888------USS能力平台返回报文------888888888888888888888888888888888888888888888\n");
		return returnXmlStr;
	}
	
	public String remoteCallDefault(String URL,String json_info) throws Exception{

		logger.info("-----------海南ECAOP接口调用请求报文-----------");
		logger.info(URL+"?"+json_info);
		logger.info("-----------海南ECAOP接口调用请求报文-----------");
		String result="";
		URL url = new URL(URL+"?"+json_info);
		HttpURLConnection connet = null;
		try {
			connet = (HttpURLConnection)url.openConnection();
			connet.setConnectTimeout(60000);
			connet.setReadTimeout(35000);
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
		    result = brd.readLine();
			logger.info("-----------ECAOP接口调用返回报文-----------");
			logger.info(result);
			logger.info("-----------ECAOP接口调用返回报文-----------");			
		} catch (Exception e) {
			e.printStackTrace();
			UssExceptionManager.throwOrdinaryException("EcaopServ调用失败.");
		} finally {
			if (null != connet) {
				connet.disconnect();
			}
		}
		return result;

	}

	

}
