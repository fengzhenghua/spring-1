package com.tydic.unicom.apc.business.ofr.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.business.common.interfaces.GetInfoServDu;
import com.tydic.unicom.apc.business.ofr.interfaces.OppoNumberServiceServDu;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.pub.interfaces.OppoNumberServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OppoNumberServiceServDuImpl implements OppoNumberServiceServDu{

	private static Logger logger = Logger.getLogger(OppoNumberServiceServDuImpl.class);
	
	@Autowired
	private GetInfoServDu getInfoServDu;
	
	@Autowired
	private OppoNumberServDu oppoNumberServDu;
	
	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;
	
	@Override
	public UocMessage oppoSelectNumber(String oper_no,String fuzzy_query,String page_info,String tele_type,String ser_type,String good_num_flag) throws Exception {
		UocMessage uocMessage = new UocMessage();
		List<Map<String,String>> number_list = new ArrayList<Map<String,String>>();
		String province_code = "";
		String area_code = "";
		String district = "";
		String channel_id = "";
		String channel_type = "";
		
		//参数校验
		if(StringUtils.isEmpty(oper_no)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("操作员编码不能为空!!!");
			return uocMessage;
		}
		if(StringUtils.isEmpty(tele_type)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("电信类型不能为空!!!");
			return uocMessage;
		}
		if(StringUtils.isEmpty(ser_type)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("受理类型不能为空!!!");
			return uocMessage;
		}
		
		//获取工号信息
		UocMessage operInfoResult = getInfoServDu.getOperInfo(oper_no);
		if(!RespCodeContents.SUCCESS.equals(operInfoResult.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取工号信息失败!!!");
			return uocMessage;
		}
		else{
			String strData = (String) operInfoResult.getArgs().get("json_info");
			JSONObject jsonObj = new JSONObject(strData);
			JSONObject operInfojsonObj =jsonObj.getJSONObject("oper_info");
			province_code = operInfojsonObj.get("province_code").toString();
			area_code = operInfojsonObj.get("area_code").toString();
			district = operInfojsonObj.get("district").toString();
			channel_id = operInfojsonObj.get("channel_id").toString();
			channel_type = operInfojsonObj.get("channel_type").toString();
		}
		
		if("4G".equals(tele_type)){
			JSONObject jsonObj = new JSONObject();
			JSONObject json_info = new JSONObject();
			JSONObject interface_param_json = new JSONObject();
			JSONObject params = new JSONObject();
			
			json_info.put("operatorId", oper_no);
			json_info.put("province", province_code);
			json_info.put("city", area_code);
			json_info.put("district", district);
			json_info.put("channelId", channel_id);
			json_info.put("channelType", channel_type);
			json_info.put("serType", ser_type);
			json_info.put("is3G", "1");
			json_info.put("busType", "1");
			json_info.put("groupFlag", "3");
			
			List<String> queryParas = new ArrayList<String>();
			if(!StringUtils.isEmpty(fuzzy_query)){
				if(fuzzy_query.indexOf("*")>0){
					String[] strArray = null;   
					StringBuilder bf = new StringBuilder();
					strArray = fuzzy_query.split("\\*");
					for(int i=0; i<strArray.length; i++) { 		    	    
			    	    bf.append(strArray[i]);
			    	   }
					JSONObject queryParasJson = new JSONObject();
					queryParasJson.put("queryType", "02");
					queryParasJson.put("queryPara", bf.toString());
					queryParas.add(queryParasJson.toString());
					json_info.put("queryParas", queryParas);
				}else{
					JSONObject queryParasJson = new JSONObject();
					queryParasJson.put("queryType", "03");
					queryParasJson.put("queryPara", fuzzy_query);
					queryParas.add(queryParasJson.toString());
					json_info.put("queryParas", queryParas);
				}
			}
			if(queryParas.size() <= 0){
				JSONObject queryParasJson = new JSONObject();
				
				//根据入参判断是否支持靓号,0表示不可选靓号,只查询普号出来
				if("0".equals(good_num_flag)){
					queryParasJson.put("queryType", "04");
					queryParasJson.put("queryPara", "6");
					queryParas.add(queryParasJson.toString());
					json_info.put("queryParas", queryParas);
				}else {
					queryParasJson.put("queryType", "01");
					queryParas.add(queryParasJson.toString());
					json_info.put("queryParas", queryParas);
				}
			}
			
			interface_param_json.put("bizkey", "TS-3G-012");
			interface_param_json.put("method", "ecaop.trades.query.comm.simpsnres.qry");
			
			params.put("json_info", json_info.toString());
			params.put("interface_param_json", interface_param_json.toString());
			params.put("interface_type", "200");
			params.put("interface_url", "");
			
			jsonObj.put("center_code", "APC");
			jsonObj.put("params", params.toString());
			
			logger.debug("=================通过能力平台去uif选号(报文):"+jsonObj.toString());
			UocMessage abilityResult = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "200", "UIF");
			
			if(RespCodeContents.SUCCESS.equals(abilityResult.getRespCode())){
				Map<String,Object> dataMap = abilityResult.getArgs();
				if("200".equals(dataMap.get("code"))){
					JSONObject dataJson= new JSONObject(dataMap.get("json_info").toString());
					JSONObject jsonInfoObject = dataJson.getJSONObject("json_info");
					JSONArray numJsonArray = jsonInfoObject.getJSONArray("numInfo");
					for(int i=0;i<numJsonArray.length();i++){
						
						JSONObject numJsonObject = numJsonArray.getJSONObject(i);

						String numId = numJsonObject.get("numId").toString();
						String numPreFee = numJsonObject.get("advancePay").toString();
						String numLevel = numJsonObject.get("lowCostPro").toString();
						String numTime = "";
						
//						 空，费用为0，else 费用单位 ’分‘ 转化 为 ’元‘
						if (StringUtils.isEmpty(numPreFee)) {
							numPreFee = "0.00";
						} else {
							BigDecimal numPreFeeBD = new BigDecimal(numPreFee).divide(new BigDecimal("1000")).setScale(2,BigDecimal.ROUND_HALF_UP);
							numPreFee = numPreFeeBD.toString();
						}
						if (StringUtils.isEmpty(numLevel)) {
							numLevel = "0";
						} else {
							BigDecimal numLevelBD = new BigDecimal(numLevel).divide(new BigDecimal(1000)).setScale(2,BigDecimal.ROUND_HALF_UP);
							numLevel = numLevelBD.longValue()+"";
						}
						Map<String, String> map = new HashMap<String, String>();
						map.put("acc_nbr", numId);
						map.put("acc_nbr_fee", numPreFee);
						map.put("low_fee", numLevel);
						map.put("on_net",numTime);
						map.put("class_id",numJsonObject.get("classId").toString()==null?"":numJsonObject.get("classId").toString());
						map.put("timedurpro",numJsonObject.get("timeDurPro").toString()==null?"":numJsonObject.get("timeDurPro").toString());
						number_list.add(map);
					}
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.setContent("选号成功");
					uocMessage.addArg("number_list", number_list);
					return uocMessage;
				}
				else{
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("选号失败");
					return uocMessage;
				}
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("选号失败");
				return uocMessage;
			}
		}
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("电信类型有误!!!");
			return uocMessage;
		}
	}

	@Override
	public UocMessage oppoOccupyNumber(String oper_no, String acc_nbr,
			String ser_type, String tele_type, String occupiedFlag) throws Exception {
		
		UocMessage uocMessage = new UocMessage();
		String province_code = "";
		String area_code = "";
		String district = "";
		String channel_id = "";
		String channel_type = "";
		
		//校验参数
		if(StringUtils.isEmpty(oper_no)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("操作员编码不能为空");
			return uocMessage;
		}
		
		if(StringUtils.isEmpty(tele_type)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("电信类型不能为空");
			return uocMessage;
		}
		if(StringUtils.isEmpty(ser_type)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("受理类型不能为空");
			return uocMessage;
		}
		if(StringUtils.isEmpty(acc_nbr)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("号码不能为空");
			return uocMessage;
		}
		if(StringUtils.isEmpty(occupiedFlag)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("号码状态标示不能为空");
			return uocMessage;
		}
		
		//获取工号信息
		UocMessage operInfoResult = getInfoServDu.getOperInfo(oper_no);
		if(!RespCodeContents.SUCCESS.equals(operInfoResult.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取工号信息失败");
			return uocMessage;
		}
		else{
			String strData = (String) operInfoResult.getArgs().get("json_info");
			JSONObject jsonObj = new JSONObject(strData);
//			Map<String, Object> jsonInfomap = (Map<String, Object>) jsonObj;
//			Map<String, Object> dataMap = (Map<String, Object>) jsonInfomap.get("oper_info");
			JSONObject dataMap = jsonObj.getJSONObject("oper_info");
			province_code = dataMap.get("province_code").toString();
			area_code = dataMap.get("area_code").toString();
			district = dataMap.get("district").toString();
			channel_id = dataMap.get("channel_id").toString();
			channel_type = dataMap.get("channel_type").toString();
		}
		
		//4G号码预占
		if("4G".equals(tele_type)){
			//拼装请求入参
			JSONObject jsonObj = new JSONObject();
			JSONObject json_info = new JSONObject();
			JSONObject resources=new JSONObject();
			ArrayList<String> resourcesArr=new ArrayList<String>();
			JSONObject resourcesInfo=new JSONObject();
			JSONObject interface_param_json = new JSONObject();
			JSONObject params = new JSONObject();
			
			json_info.put("province", province_code);
			json_info.put("city", area_code);
			json_info.put("district", district);
			json_info.put("channelId", channel_id);
			json_info.put("channelType", channel_type);
			json_info.put("operatorId", oper_no);
			json_info.put("serType", ser_type);
			
			resourcesInfo.put("resourcesType", "02");
			resourcesInfo.put("occupiedFlag", "1");
			resourcesInfo.put("resourcesCode", acc_nbr);
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, 30);
			DateFormat dateFormat= new SimpleDateFormat("yyyyMMddHHmmss");
			String occupiedTime= dateFormat.format(calendar.getTime());
			resourcesInfo.put("occupiedTime",occupiedTime);
			if( !StringUtils.isEmpty( occupiedFlag ) ){
				resourcesInfo.put("keyChangeTag", "0");
				resourcesInfo.put("proKey", "0");
				resourcesInfo.put("proKeyMode", "0");
				resourcesInfo.put("occupiedFlag", occupiedFlag);
				if ("1".equals(occupiedFlag)) {
					calendar.add(Calendar.MINUTE, 30);
					resourcesInfo.put("occupiedTime",dateFormat.format(calendar.getTime()));
				}
				else if ("3".equals(occupiedFlag)) {
					calendar.add(Calendar.MONTH, 1);
					resourcesInfo.put("occupiedTime",dateFormat.format(calendar.getTime()));
				}				
			}
			resourcesArr.add(resourcesInfo.toString());
			//resources.put("resourcesInfo", resourcesArr);
			
			interface_param_json.put("bizkey", "TS-3G-012");
			interface_param_json.put("method", "ecaop.trades.query.comm.simpsnres.chg");
			
			json_info.put("resourcesInfo", resourcesArr);
			params.put("json_info", json_info.toString());
			params.put("interface_param_json", interface_param_json.toString());
			params.put("interface_type", "200");
			params.put("interface_url", "");
			
			jsonObj.put("center_code", "APC");
			jsonObj.put("params", params.toString());
			logger.debug("=================通过能力平台去uif获取可选营业厅信息(报文):"+jsonObj.toString());
			uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "200", "UIF");
			
			//先判断调用能力平台是否成功
			if(RespCodeContents.SUCCESS.equals(uocMessage.getRespCode())){
				String code = (String)uocMessage.getArgs().get("code");
				String jsonInfo = (String)uocMessage.getArgs().get("json_info");
        		JSONObject jsonObject = new JSONObject(jsonInfo);
        		JSONObject rspInfo=(JSONObject) jsonObject.get("json_info");
        		String aopCode=(String)jsonObject.get("code");
				//判断是否预占成功
				if("200".equals(code)){
					if("200".equals(aopCode)){
						JSONArray resourcesRspList = rspInfo.getJSONArray("resourcesRsp");
						JSONObject resourcesRsp=resourcesRspList.getJSONObject(0);
						String rscStateCode = resourcesRsp.get("rscStateCode").toString();
						String rscStateDesc = resourcesRsp.get("rscStateDesc").toString();
						if("0000".equals(rscStateCode)){
							uocMessage.setRespCode(RespCodeContents.SUCCESS);
							uocMessage.setContent(rscStateDesc);
			    			return uocMessage;
						}else{
							uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocMessage.setContent(rscStateDesc);
							return uocMessage;
						}
					}else {
						String detail = (String)rspInfo.get("detail");
						uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocMessage.setContent(detail);
						return uocMessage;
					}
				}else{
					String detail = (String)rspInfo.get("detail");
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent(detail);
					return uocMessage;
				}
			}else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(uocMessage.getContent());
				return uocMessage;
			}
			
		}
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("电信类型有误");
			return uocMessage;
		}
	}

}
