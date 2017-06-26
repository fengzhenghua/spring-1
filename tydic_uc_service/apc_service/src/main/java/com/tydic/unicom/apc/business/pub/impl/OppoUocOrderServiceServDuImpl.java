package com.tydic.unicom.apc.business.pub.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.business.common.interfaces.GetInfoServDu;
import com.tydic.unicom.apc.business.order.vo.LogPayCsVo;
import com.tydic.unicom.apc.business.pub.interfaces.OppoUocOrderServiceServDu;
import com.tydic.unicom.apc.business.pub.vo.ApcUocOrderVo;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.interfaces.ApcRedisServiceServDu;
import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.apc.service.order.interfaces.LogPayCsServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OppoUocOrderServiceServDuImpl implements OppoUocOrderServiceServDu {

	private static Logger logger = Logger.getLogger(OppoUocOrderServiceServDuImpl.class);
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Autowired
	private GetInfoServDu getInfoServDu;
	
	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;
	
	@Autowired
	private ApcRedisServiceServDu apcRedisServiceServDu;
	
	@Autowired
	private LogPayCsServDu logPayCsServDu;
	
	@Override
	public UocMessage createUocOrder(ApcUocOrderVo apcUocOrderVo)throws Exception {
		UocMessage message = new UocMessage();
		try{
			
			String province_code = "";
			String area_code = "";
			
			UocMessage operInfoResult = getInfoServDu.getOperInfo(apcUocOrderVo.getOper_no());
			if(!RespCodeContents.SUCCESS.equals(operInfoResult.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("获取工号信息失败："+operInfoResult.getContent());
				return message;
			}
			else{
				String strData = (String) operInfoResult.getArgs().get("json_info");
				JSONObject jsonObj = JSONObject.fromObject(strData);
				Map<String, Object> jsonInfomap = (Map<String, Object>) jsonObj;
				Map<String, Object> dataMap = (Map<String, Object>) jsonInfomap.get("oper_info");
				province_code = dataMap.get("province_code").toString();
				area_code = dataMap.get("area_code").toString();
			}
			
			JSONObject json_param = new JSONObject();
			JSONObject params = new JSONObject();
			params.put("jsession_id", apcUocOrderVo.getOper_no());
			params.put("order_type", "A103");
			if(!StringUtils.isEmpty(apcUocOrderVo.getAccept_no())){
				params.put("accept_no", apcUocOrderVo.getAccept_no());
			}else {
				params.put("accept_no", apcUocOrderVo.getOrder_id());
			}
			params.put("flow_flag", "100");
			params.put("accept_type", "");
			if(!StringUtils.isEmpty(apcUocOrderVo.getAccept_system())){
				params.put("accept_system", apcUocOrderVo.getAccept_system());
			}else{
				params.put("accept_system", "000");
			}
			params.put("accept_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			params.put("province_code", province_code);
			params.put("area_code", area_code);
			params.put("pay_flag", apcUocOrderVo.getPay_type());
			params.put("pay_type", apcUocOrderVo.getPay_type());
			params.put("express_flag", "10");
			params.put("auto_confirm", "1");
			if(!StringUtils.isEmpty(apcUocOrderVo.getCallback_url())){
				params.put("callback_url", apcUocOrderVo.getCallback_url());
			}else{
				params.put("callback_url", "");
			}
			
			JSONObject jsonInfo = new JSONObject();
			jsonInfo.put("cod_charge", apcUocOrderVo.getCod_charge());
	        jsonInfo.put("serial_number", apcUocOrderVo.getSerial_number());
	        jsonInfo.put("recom_person_id", apcUocOrderVo.getRecom_person_id());
	        jsonInfo.put("recom_person_name", apcUocOrderVo.getRecom_person_name());
	        jsonInfo.put("first_mon_bill_mode", apcUocOrderVo.getFirst_mon_bill_mode());
	        jsonInfo.put("product_id", apcUocOrderVo.getProduct_id());
	        if(!StringUtils.isEmpty(apcUocOrderVo.getActivity_id())){
	        	jsonInfo.put("activity_id", apcUocOrderVo.getActivity_id());
	        }
	        if(!StringUtils.isEmpty(apcUocOrderVo.getReward_oper_no())){
	        	jsonInfo.put("reward_oper_no", apcUocOrderVo.getReward_oper_no());
	        }
	        if(!StringUtils.isEmpty(apcUocOrderVo.getAp_type())){
	        	jsonInfo.put("ap_type", apcUocOrderVo.getAp_type());
	        }
	        if(!StringUtils.isEmpty(apcUocOrderVo.getTb_account())){
	        	jsonInfo.put("tb_account", apcUocOrderVo.getAp_type());
	        }
	        if(!StringUtils.isEmpty(apcUocOrderVo.getFee_info())){
	        	jsonInfo.put("fee_info", apcUocOrderVo.getFee_info());
	        }else {
	        	jsonInfo.put("fee_info", "[]");
			}
	        if(!StringUtils.isEmpty(apcUocOrderVo.getCod_bank_no())){
	        	jsonInfo.put("cod_bank_no", apcUocOrderVo.getCod_bank_no());
	        }
	        jsonInfo.put("ser_type", "1");
	        
	        jsonInfo.put("cert_address", apcUocOrderVo.getCert_address());
	        jsonInfo.put("cert_expire_date", apcUocOrderVo.getCert_expire_date());
	        jsonInfo.put("customer_name", apcUocOrderVo.getCustomer_name());
	        jsonInfo.put("customer_sex", apcUocOrderVo.getCustomer_sex());
	        
	        jsonInfo.put("cert_num", apcUocOrderVo.getCert_num());
	        jsonInfo.put("cert_type", apcUocOrderVo.getCert_type());
	        jsonInfo.put("contact_address", apcUocOrderVo.getContact_address());
	        
	        jsonInfo.put("contact_phone", apcUocOrderVo.getContact_phone());
	        jsonInfo.put("oper_code", apcUocOrderVo.getOper_code());
	        
	        jsonInfo.put("receive_name", apcUocOrderVo.getReceive_name());
	        jsonInfo.put("receive_phone", apcUocOrderVo.getReceive_phone());
	        jsonInfo.put("receive_province", apcUocOrderVo.getReceive_province());
	        jsonInfo.put("receive_city", apcUocOrderVo.getReceive_city());
	        jsonInfo.put("receive_area", apcUocOrderVo.getReceive_area());
	        jsonInfo.put("receive_address", apcUocOrderVo.getReceive_address());
	        jsonInfo.put("total_fee", apcUocOrderVo.getTotal_fee());
	        jsonInfo.put("pay_type", apcUocOrderVo.getPay_type());
	        jsonInfo.put("ywb_id", apcUocOrderVo.getYwb_id());
	        
	        params.put("json_info", jsonInfo.toString());
	        
	        json_param.put("params", params.toString());
	        
	        json_param.put("SERVICE_NAME", "createSaleOrder");
	        
	        UocMessage uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(json_param.toString(), "", "UOC");
	        
	        //这里只是调能力平台成功
	        if(RespCodeContents.SUCCESS.equals(uocMessage.getRespCode())){
	        	//创建订单成功
	        	String code = (String)uocMessage.getArgs().get("code");
	        	if("200".equals(code)){
	        		String order_info = (String)uocMessage.getArgs().get("json_info");
	        		JSONObject jsonObject = JSONObject.fromObject(order_info);
	        		//没有返回服务订单号，应该要报错，一般跟所传流水号accept_no重复有关
	        		if(StringUtils.isEmpty(jsonObject.get("serv_order_no_list"))){
	        			message.setRespCode(RespCodeContents.SERVICE_FAIL);
		        		message.setContent("创建订单失败，流水号重复!");
		        		return message;
	        		}
	        		String sale_order_no = jsonObject.get("sale_order_no").toString();
	        		@SuppressWarnings("unchecked")
					List<String> serv_order_no_list = (List<String>) jsonObject.get("serv_order_no_list");
	        		String serv_order_no = serv_order_no_list.get(0);
	        		
	        		//查看支付表日志
	        		LogPayCsVo logPayCsVo = new LogPayCsVo();
	        		logPayCsVo =logPayCsServDu.queryLogPayCsByCsOrderId(apcUocOrderVo.getOrder_id());
	        		//更新支付表服务订单号
	        		if(logPayCsVo!=null){
	        			logPayCsVo.setServ_order_no(serv_order_no);
	        			logPayCsServDu.updateLogPayCs(logPayCsVo);
	        		}
	        		message.addArg("sale_order_no", sale_order_no);
	        		message.addArg("serv_order_no", serv_order_no);
	        		
	        		message.setRespCode(RespCodeContents.SUCCESS);
	        		
	        		if(!StringUtils.isEmpty(apcUocOrderVo.getAp_id())){
	        			//公众号二维码
		        		UocMessage redisInfo=apcRedisServiceServDu.queryForApAttr(apcUocOrderVo.getAp_id());
		        		if(RespCodeContents.SUCCESS.equals(redisInfo.getRespCode())){
		        			@SuppressWarnings("unchecked")
		        			Map<String, Object> map=(Map<String, Object>) redisInfo.getArgs().get("apAttrInfo");
		        			String wx_public_qr_code = (String) map.get("wx_public_qr_code");
		        			message.addArg("wxPublicQrCode", wx_public_qr_code);
		        		}else {
		        			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			        		message.setContent("从缓存中获取微信配置失败！!");
		        		}
	        		}else {
	        			message.addArg("wxPublicQrCode", "");
					}
	        		
	        	}else {
	        		 message.setRespCode(RespCodeContents.SERVICE_FAIL);
	        		 message.setContent("生成中台订单中心数据失败!");
				}
	        	/*JSONObject argsJson = resJson.getJSONObject("args");
	            String order_no = argsJson.getString("sale_order_no");
	            //保存请求订单号到attr表
	            List<InfoOrderAttrVo> attrInfoList = new ArrayList<>();
				InfoOrderAttrVo orderNoAttr = new InfoOrderAttrVo();
				orderNoAttr.setOrder_id(apcUocOrderVo.getOrder_id());
				orderNoAttr.setAttr_type("200");
				orderNoAttr.setAttr_id("sale_order_no");
				orderNoAttr.setAttr_value(order_no);
				attrInfoList.add(orderNoAttr);
				
				JSONArray serv_order_no_list = argsJson.getJSONArray("serv_order_no_list");
				String serv_order_no = serv_order_no_list.getString(0);
				InfoOrderAttrVo servOrderNoAttr = new InfoOrderAttrVo();
				servOrderNoAttr.setOrder_id(apcUocOrderVo.getOrder_id());
				servOrderNoAttr.setAttr_type("200");
				servOrderNoAttr.setAttr_id("serv_order_no");
				servOrderNoAttr.setAttr_value(serv_order_no);
				attrInfoList.add(servOrderNoAttr);
				orderAttrServDu.addOrderAttrList(attrInfoList);*/
	        }
	        else{
	        	message.setRespCode(RespCodeContents.SERVICE_FAIL);
	        	message.setContent(uocMessage.getContent());
	        }
	        
	        return message;
		}catch(Exception e){
			logger.error("生成中台订单中心数据失败!"+e.getMessage(), e);
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("生成中台订单中心数据失败!"+e.getMessage());
			return message;
		}
	}

}
