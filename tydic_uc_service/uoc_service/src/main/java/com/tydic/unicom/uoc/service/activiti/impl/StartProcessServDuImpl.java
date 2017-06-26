package com.tydic.unicom.uoc.service.activiti.impl;

import java.io.InputStream;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.service.activiti.interfaces.ProcModAppServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.StartProcessServDu;
import com.tydic.unicom.uoc.service.activiti.vo.ProcModAppVo;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoPayOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Service("StartProcessServDu")
public class StartProcessServDuImpl implements StartProcessServDu{

	Logger logger = Logger.getLogger(StartProcessServDuImpl.class);
	
	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	
	@Autowired
	private ProcModAppServDu procModAppServDu;
	
	@Autowired
	private InfoPayOrderServDu infoPayOrderServDu;
	
	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;
	
	@Autowired
	private ProcessCirculationServDu processCirculationServDu;
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	
	@Override
	public UocMessage startProcess(String order_no, String oper_type,String jsession_id) throws Exception {
		
		logger.info("===================调用流程启动服务===============order_no:"+order_no+" oper_type:"+oper_type);
		UocMessage uocMessage = new UocMessage();
		String ProcModCode = "";
		boolean result = false;

		//销售订单(oper_type=100)或者预销售订单(oper_type=104)
		if("100".equals(oper_type) || "104".equals(oper_type)){
			logger.info("=========================销售订单或者预销售订单>>>>>>>>oper_type:"+oper_type);
			InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(order_no);
			//根据订单号查询销售订单表
			infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			logger.info("==========>>>>>Province_code:"+infoSaleOrderVo.getProvince_code()+" Area_code:"+infoSaleOrderVo.getArea_code()+"  Order_type:"+infoSaleOrderVo.getOrder_type());
			ProcModAppVo procModAppVo = new ProcModAppVo();
			procModAppVo.setProvince_code(infoSaleOrderVo.getProvince_code());
			procModAppVo.setArea_code(infoSaleOrderVo.getArea_code());
			procModAppVo.setOper_code(infoSaleOrderVo.getOrder_type());
			//根据省份、地域、业务条件查询流程应用表
			procModAppVo = procModAppServDu.queryProcModAppByProvinceCodeAndAreaCodeAndOperCodeFromRedis(procModAppVo);
			ProcModCode = procModAppVo.getProc_mod_code();
			logger.info("===============>>>>>>ProcModCode:"+ProcModCode);
			//调用工作流程启动服务生成流程实例
			JSONObject resultJsonObj = startProcessByActiviti(order_no,ProcModCode);
			logger.info("===================>>>>>>resultJsonObj:"+resultJsonObj);
			if("104".endsWith(oper_type)){
				infoSaleOrderVo.setOrder_state("101");
				infoSaleOrderVo.setProc_inst_id(resultJsonObj.getString("instanceId"));
			}
			if("100".equals(oper_type)){
				infoSaleOrderVo.setOrder_state("201");
				infoSaleOrderVo.setProc_inst_id(resultJsonObj.getString("instanceId"));
			}
			//更新预销售订单数据
			result = infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
			logger.info("================>>>>>>>>>>>>>更新预销售订单数据"+result);
			if(!result){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("更新预销售订单数据出错");
				return uocMessage;
			}
			//调用流程流转服务
			logger.info("========================>>>>>>调用流程流转服务");
			String json_info_ext ="{\"jsession_id\":\""+jsession_id+"\"}";
			return processCirculationServDu.processCirculation(order_no, oper_type, "0", null,json_info_ext);
		}
		//服务订单(oper_type=101)
		else if(oper_type.equals("101")){
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(order_no);
			//根据订单号查询服务订单表
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);		
			ProcModAppVo procModAppVo = new ProcModAppVo();
			procModAppVo.setProvince_code(infoServiceOrderVo.getProvince_code());
			procModAppVo.setArea_code(infoServiceOrderVo.getArea_code());
			procModAppVo.setOper_code(infoServiceOrderVo.getOper_code());
			//根据省份、地域、业务条件查询流程应用表
			procModAppVo = procModAppServDu.queryProcModAppByProvinceCodeAndAreaCodeAndOperCodeFromRedis(procModAppVo);
			ProcModCode = procModAppVo.getProc_mod_code();
			
			//调用工作流程启动服务生成流程实例
			JSONObject resultJsonObj = startProcessByActiviti(order_no,ProcModCode);
			
			infoServiceOrderVo.setOrder_state("201");
			infoServiceOrderVo.setProc_inst_id(resultJsonObj.getString("instanceId"));
			//更新服务订单数据
			result = infoServiceOrderServDu.updateInfoServiceOrder(infoServiceOrderVo);
			if(!result){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("更新服务订单数据出错");
				return uocMessage;
			}
			String json_info_ext ="{\"jsession_id\":\""+jsession_id+"\"}";
			//调用流程流转服务
			UocMessage uocProcessCirculation = processCirculationServDu.processCirculation(order_no, oper_type, "0", null,json_info_ext);
			if(!"0000".equals(uocProcessCirculation.getRespCode())){
				//抛出业务异常
				throw new UocException(uocProcessCirculation);
			}
			else{
				return uocProcessCirculation;
			}
		}
		
		//支付订单(oper_type=102)
		else if("102".equals(oper_type)){
			InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
			infoPayOrderVo.setPay_order_no(order_no);
			//根据订单号查询支付订单表
			infoPayOrderVo = infoPayOrderServDu.getInfoPayOrderByPayOrderNo(infoPayOrderVo);
			ProcModAppVo procModAppVo = new ProcModAppVo();
			procModAppVo.setProvince_code(infoPayOrderVo.getProvince_code());
			procModAppVo.setArea_code(infoPayOrderVo.getArea_code());
			//根据省份、地域条件查询流程应用表
			procModAppVo = procModAppServDu.queryProcModAppByProvinceCodeAndAreaCodeAndOperCodeFromRedis(procModAppVo);
			ProcModCode = procModAppVo.getProc_mod_code();
			
			//调用工作流程启动服务生成流程实例
			JSONObject resultJsonObj = startProcessByActiviti(order_no,ProcModCode);
			
			infoPayOrderVo.setPay_state("201");
			infoPayOrderVo.setProc_inst_id(resultJsonObj.getString("instanceId"));
			//更新支付订单数据
			result = infoPayOrderServDu.updateInfoPayOrder(infoPayOrderVo);
			if(!result){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("更新支付订单数据出错");
				return uocMessage;
			}
			String json_info_ext ="{\"jsession_id\":\""+jsession_id+"\"}";
			//调用流程流转服务
			UocMessage uocProcessCirculation = processCirculationServDu.processCirculation(order_no, oper_type, "0", null,json_info_ext);
			if(!"0000".equals(uocProcessCirculation.getRespCode())){
				throw new UocException(uocProcessCirculation);
			}
			else{
				return uocProcessCirculation;
			}
		}
		//交付订单(oper_type=103)
		else if("103".equals(oper_type)){
			InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
			infoDeliverOrderVo.setDeliver_order_no(order_no);
			//根据订单号查询交付订单表
			infoDeliverOrderVo = infoDeliverOrderServDu.getInfoDeliverOrderByPayDeliverOrderNo(infoDeliverOrderVo);
			ProcModAppVo procModAppVo = new ProcModAppVo();
			procModAppVo.setProvince_code(infoDeliverOrderVo.getProvince_code());
			procModAppVo.setArea_code(infoDeliverOrderVo.getArea_code());
			//根据省份、地域条件查询流程应用表
			procModAppVo = procModAppServDu.queryProcModAppByProvinceCodeAndAreaCodeAndOperCodeFromRedis(procModAppVo);
			ProcModCode = procModAppVo.getProc_mod_code();
			
			//调用工作流程启动服务生成流程实例
			JSONObject resultJsonObj = startProcessByActiviti(order_no,ProcModCode);
			
			infoDeliverOrderVo.setDeliver_state("201");
			infoDeliverOrderVo.setProc_inst_id(resultJsonObj.getString("instanceId"));
			//更新交付订单数据
			result = infoDeliverOrderServDu.updateInfoDeliverOrder(infoDeliverOrderVo);
			if(!result){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("更新支付订单数据出错");
				return uocMessage;
			}
			String json_info_ext ="{\"jsession_id\":\""+jsession_id+"\"}";
			//调用流程流转服务
			UocMessage uocProcessCirculation = processCirculationServDu.processCirculation(order_no, oper_type, "0", null,json_info_ext);
			if(!"0000".equals(uocProcessCirculation.getRespCode())){
				throw new UocException(uocProcessCirculation);
			}
			else{
				return uocProcessCirculation;
			}
		}
		//错误的oper_type值
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("流转类型错误");
			return uocMessage;
		}
	}

	/**
	 * 根据订单号与模板号启动流程
	 * */
	public JSONObject startProcessByActiviti(String order_no,String ProcModCode) throws Exception{
		int modId = Math.abs(order_no.hashCode()%2);
		String url = getValueFromProperties(modId);
		JSONObject requestJsonObj = new JSONObject();
		requestJsonObj.put("processDefinitionKey", ProcModCode);
		String resultJsonStr = HttpUtil.httpClient(url,requestJsonObj.toString());
		logger.info("============================调用工作流启动流程，返回结果："+resultJsonStr);
		JSONObject resultJsonObj = ActivitiUtils.StringToJson(resultJsonStr);
		return resultJsonObj;
	}
	
	/**
	 * 获取配置文件参数的值
	 * */
	public String getValueFromProperties(int modId) throws Exception{
		String value = "";
		
		if(modId == 0){
			value = propertiesParamVo.getStartProcess_0();
		}
		if(modId == 1){
			value = propertiesParamVo.getStartProcess_1();
		}
		logger.info("================>>>>>>>>>>获取配置文件参数的值"+value);
		return value;
	}
}
