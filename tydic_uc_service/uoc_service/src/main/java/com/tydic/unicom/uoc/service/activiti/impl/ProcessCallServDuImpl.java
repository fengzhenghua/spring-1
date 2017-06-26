package com.tydic.unicom.uoc.service.activiti.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.uoc.pub.common.service.po.TaskInst;
import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCallServDu;
import com.tydic.unicom.uoc.service.common.interfaces.ActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.interfaces.FunctionReflecServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServiceServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheServiceVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ProcessCallServDu")
public class ProcessCallServDuImpl implements ProcessCallServDu{

	Logger logger = Logger.getLogger(ProcessCallServDuImpl.class);

	@Autowired
	private FunctionReflecServDu functionReflecServDu;

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;

	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;

	@Autowired
	private FindMyPersonalTaskServDu findMyPersonalTaskServDu;

	@Autowired
	private ProcModTacheServiceServDu procModTacheServiceServDu;

	@Autowired
	private ActivemqSendServDu activemqSendServDu;

	@Autowired
	private OrdModFunctionServDu ordModFunctionServDu;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Override
	public UocMessage processCall(String orderNo, String operType,String callType,String jsonInfoExt,ProcInstTaskInstVo procInstTaskInstVo) throws Exception {

		logger.info("===============环节调用服务==============");
		UocMessage uocMessage = new UocMessage();
		String ProcessInstanceId = "";
		//销售订单(operType=100)
		if("100".equals(operType)){
			InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(orderNo);
			//根据订单号查询销售订单表
			infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			ProcessInstanceId = infoSaleOrderVo.getProc_inst_id();
			//获取当前环节
			UocMessage findTaskMsg = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(ProcessInstanceId, orderNo);
			if(!"0000".equals(findTaskMsg.getRespCode())){
				return findTaskMsg;
			}
			ProcModTacheServiceVo procModTacheServiceVo = new ProcModTacheServiceVo();
			procModTacheServiceVo.setTache_code(findTaskMsg.getArgs().get("current_tache").toString());
			procModTacheServiceVo.setProvince_code(infoSaleOrderVo.getProvince_code());
			procModTacheServiceVo.setArea_code(infoSaleOrderVo.getArea_code());
			procModTacheServiceVo.setOper_code(infoSaleOrderVo.getOrder_type());
			//根据环节编码，地域和省份，查询环节关联服务表
			List<ProcModTacheServiceVo> procModTacheServiceVoResultList = procModTacheServiceServDu.queryProcModTacheServiceByTacheCodeAndProvinceAndAreaAndOperCodeFromRedis(procModTacheServiceVo);
			//没有结果，直接返回
			if(procModTacheServiceVoResultList != null && procModTacheServiceVoResultList.size()>0){
				for(int i=0;i<procModTacheServiceVoResultList.size();i++){
					ProcModTacheServiceVo procModTacheServiceVoResult = procModTacheServiceVoResultList.get(i);
					String JsonIn ="";
					//调用Base006接口，得到JsonIn的值
					UocMessage omfMessage = ordModFunctionServDu.outByOrdMod(orderNo, procModTacheServiceVoResult.getInput_str(), operType, "");
					if(!"0000".equals(omfMessage.getRespCode())){
						return omfMessage;
					}
					JsonIn = omfMessage.getArgs().get("json_info").toString();
					//json_info_ext不为空,拼接额外的json数据到前面解析后的入参json中
					if(jsonInfoExt !=null && (!jsonInfoExt.equals(""))){
						JsonIn = addJsonInfoExt(JsonIn,jsonInfoExt);
					}
					//如果callType为0或空，最后调用服务发消息队列
					if(callType == null || "".equals(callType) || "0".equals(callType)){
						TaskInst taskInst = new TaskInst();
						ActivemqSendPo activemqSendPo = new ActivemqSendPo();
						if(procInstTaskInstVo == null ){
							ProcInstTaskInstVo procInstTaskInstVoTemp = new ProcInstTaskInstVo();
							procInstTaskInstVoTemp.setOrder_no(orderNo);
							procInstTaskInstVoTemp.setOrder_type(operType);
							BeanUtils.copyProperties(procInstTaskInstVoTemp, taskInst);
						}
						else{
							BeanUtils.copyProperties(procInstTaskInstVo, taskInst);
						}
						activemqSendPo.setOrder_id(orderNo);
						activemqSendPo.setOrder_type(operType);
						activemqSendPo.setProvince_code(infoSaleOrderVo.getProvince_code());
						activemqSendPo.setJson_in(JsonIn);
						activemqSendPo.setService_code(procModTacheServiceVoResult.getService_name());
						activemqSendPo.setTaskInst(taskInst);
						//调用写消息队列服务
						UocMessage asMessage = activemqSendServDu.createMessageQueue(activemqSendPo, procModTacheServiceVoResult.getQueue_id());
						if(!"0000".equals(asMessage.getRespCode())){
							return asMessage;
						}
					}
					else if("1".equals(callType)){
						Map<String,String> serviceMap = getValueFromProperties(procModTacheServiceVoResult.getService_name());
						String className = serviceMap.get(procModTacheServiceVoResult.getService_name()+"Class");
						String methodName = serviceMap.get(procModTacheServiceVoResult.getService_name()+"Method");
						Object[] paramValues = new String[]{JsonIn};
						Class[] paramTypes = new Class[]{String.class};
						UocMessage frMessage;
						try {
							frMessage = (UocMessage) functionReflecServDu.invokeMethod(className, methodName, paramValues, paramTypes);
							if(!"0000".equals(frMessage.getRespCode())){
								return frMessage;
							}
						} catch (Throwable e) {
							e.printStackTrace();
							uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
							uocMessage.setContent("反射调用接口错误");
							return uocMessage;
						}
					}
					else{
						uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
						uocMessage.setContent("传入的callType有误");
						return uocMessage;
					}
				}
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				return uocMessage;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到环节相关服务");
				return uocMessage;
			}
		}
		//服务订单(oper_type=101)
		else if("101".equals(operType)){
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(orderNo);
			//根据订单号查询服务订单表
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			ProcessInstanceId = infoServiceOrderVo.getProc_inst_id();
			//获取当前环节
			UocMessage findTaskMsg = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(ProcessInstanceId, orderNo);
			if(!"0000".equals(findTaskMsg.getRespCode())){
				return findTaskMsg;
			}

			ProcModTacheServiceVo procModTacheServiceVo = new ProcModTacheServiceVo();
			procModTacheServiceVo.setTache_code(findTaskMsg.getArgs().get("current_tache").toString());
			procModTacheServiceVo.setProvince_code(infoServiceOrderVo.getProvince_code());
			procModTacheServiceVo.setArea_code(infoServiceOrderVo.getArea_code());
			procModTacheServiceVo.setOper_code(infoServiceOrderVo.getOper_code());
			//根据环节编码，地域和省份，查询环节关联服务表
			List<ProcModTacheServiceVo> procModTacheServiceVoResultList = procModTacheServiceServDu.queryProcModTacheServiceByTacheCodeAndProvinceAndAreaAndOperCodeFromRedis(procModTacheServiceVo);
			//没有结果，直接返回
			if(procModTacheServiceVoResultList != null && procModTacheServiceVoResultList.size()>0){
				for(int i=0;i<procModTacheServiceVoResultList.size();i++){
					ProcModTacheServiceVo procModTacheServiceVoResult = procModTacheServiceVoResultList.get(i);
					String JsonIn ="";
					//调用Base006接口，得到JsonIn的值
					UocMessage omfMessage = ordModFunctionServDu.outByOrdMod(orderNo, procModTacheServiceVoResult.getInput_str(),operType,"");
					if(!"0000".equals(omfMessage.getRespCode())){
						return omfMessage;
					}
					JsonIn = omfMessage.getArgs().get("json_info").toString();
					//json_info_ext不为空,拼接额外的json数据到前面解析后的入参json中
					if(jsonInfoExt !=null && (!jsonInfoExt.equals(""))){
						JsonIn = addJsonInfoExt(JsonIn,jsonInfoExt);
					}
					//如果callType为0或空，最后调用服务发消息队列
					if(callType == null || "".equals(callType) || "0".equals(callType)){
						TaskInst taskInst = new TaskInst();
						ActivemqSendPo activemqSendPo = new ActivemqSendPo();
						if(procInstTaskInstVo == null ){
							ProcInstTaskInstVo procInstTaskInstVoTemp = new ProcInstTaskInstVo();
							procInstTaskInstVoTemp.setOrder_no(orderNo);
							procInstTaskInstVoTemp.setOrder_type(operType);
							BeanUtils.copyProperties(procInstTaskInstVoTemp, taskInst);
						}
						else{
							BeanUtils.copyProperties(procInstTaskInstVo, taskInst);
						}
						activemqSendPo.setOrder_id(orderNo);
						activemqSendPo.setOrder_type(operType);
						activemqSendPo.setProvince_code(infoServiceOrderVo.getProvince_code());
						activemqSendPo.setJson_in(JsonIn);
						activemqSendPo.setService_code(procModTacheServiceVoResult.getService_name());
						activemqSendPo.setTaskInst(taskInst);
						//调用写消息队列服务
						UocMessage asMessage = activemqSendServDu.createMessageQueue(activemqSendPo, procModTacheServiceVoResult.getQueue_id());
						if(!"0000".equals(asMessage.getRespCode())){
							return asMessage;
						}
					}
					//直接用反射方式调用配置的服务
					else if("1".equals(callType)){
						Map<String,String> serviceMap = getValueFromProperties(procModTacheServiceVoResult.getService_name());
						String className = serviceMap.get(procModTacheServiceVoResult.getService_name()+"Class");
						String methodName = serviceMap.get(procModTacheServiceVoResult.getService_name()+"Method");
						Object[] paramValues = new String[]{JsonIn};
						Class[] paramTypes = new Class[]{String.class};
						try {
							UocMessage frMessage = (UocMessage) functionReflecServDu.invokeMethod(className, methodName, paramValues, paramTypes);
							if(!"0000".equals(frMessage.getRespCode())){
								return frMessage;
							} else {
								return frMessage;
							}
						} catch (Throwable e) {
							e.printStackTrace();
							uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
							uocMessage.setContent("反射调用接口异常");
							return uocMessage;
						}
					}
					else{
						uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
						uocMessage.setContent("传入的callType有误");
						return uocMessage;
					}
				}
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				return uocMessage;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到环节相关服务");
				return uocMessage;
			}
		}
		//输入的操作类型错误
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("输入的操作类型错误");
			return uocMessage;
		}
	}

	/**
	 * 获取配置文件的参数
	 * */
	public Map<String,String> getValueFromProperties(String key) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		Properties props = new Properties();
		InputStream in = ProcessCallServDuImpl.class.getResourceAsStream("/serviceCode.properties");
		props.load(in);
		map.put(key+"Class", props.getProperty(key+"Class"));
		map.put(key+"Method", props.getProperty(key+"Method"));
		return map;
	}

	/**
	 * 将jsonInfoExt拼接到jsonIn中
	 * */
	public String addJsonInfoExt(String jsonIn,String jsonInfoExt) throws Exception{
		String resultJsonIn = "";
		Map<String,Object> jsonInMap = jsonToBeanServDu.jsonToMap(jsonIn);
		Map<String,Object> jsonInfoExtMap = jsonToBeanServDu.jsonToMap(jsonInfoExt);
		Iterator iterator = jsonInfoExtMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			jsonInMap.put(key, jsonInfoExtMap.get(key));
		}
		resultJsonIn = jsonToBeanServDu.mapToJson(jsonInMap);
		return resultJsonIn;
	}
}
