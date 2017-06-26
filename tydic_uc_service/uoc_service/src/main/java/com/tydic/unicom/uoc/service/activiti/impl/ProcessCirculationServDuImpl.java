package com.tydic.unicom.uoc.service.activiti.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.OrdModDefine;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.sms.TextShortMessage;
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSmsWarnPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoDeliverOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSmsWarnServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.business.common.vo.ProcModTacheVo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ChangeToArtificialServiceServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.CompletePersonalTaskServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCallServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.code.vo.CodeListVo;
import com.tydic.unicom.uoc.service.common.interfaces.ActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcInstTaskInstServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoPayOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderBaseDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.uoc.service.sms.interfaces.IUocSmsService;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.BusiMessage;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ProcessCirculationServDu")
public class ProcessCirculationServDuImpl implements ProcessCirculationServDu{

	Logger logger = Logger.getLogger(ProcessCirculationServDuImpl.class);

	@Autowired
	private CodeListServDu codeListServDu;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private OrdModFunctionServDu ordModFunctionServDu;

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;

	@Autowired
	private InfoServiceOrderHisServDu infoServiceOrderHisServDu;

	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;

	@Autowired
	private InfoPayOrderServDu infoPayOrderServDu;

	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;

	@Autowired
	private ProcModTacheServDu procModTacheServDu;

	@Autowired
	private CompletePersonalTaskServDu completePersonalTaskServDu;

	@Autowired
	private FindMyPersonalTaskServDu findMyPersonalTaskServDu;

	@Autowired
	private InfoServiceOrderBaseDu infoServiceOrderBaseDu;

	@Autowired
	private ActivemqSendServDu activemqSendServDu;

	@Autowired
	private ProcessCallServDu processCallServDu;

	@Autowired
	private ChangeToArtificialServiceServDu changeToArtificialServiceServDu;

	@Autowired
	private InfoSmsWarnServ infoSmsWarnServ;

	@Autowired
	private GetIdServDu getIdServDu;

	@Autowired
	private RedisServiceServ redisServiceServ;

	@Autowired
	private ProcInstTaskInstServDu procInstTaskInstServDu;

	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;

	@Autowired
	private InfoDeliverOrderServ infoDeliverOrderServ;

	@Autowired
	private IUocSmsService uocSmsService;


	@Override
	public UocMessage processCirculation(String orderNo, String operType,String flowType, Map<String,String> actionCode,String jsonInfoExt) throws Exception {
		logger.info("<<<<<<<<====================流程流转服务==================>>>>>>>>");
		UocMessage uocMessage = new UocMessage();
		String processInstanceId = "";
		String tacheCode = "";
		//销售订单
		if("100".equals(operType) || "104".equals(operType)){
			logger.info("<<<<<<==============销售订单/预销售订单==============>>>>>>>>");
			InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(orderNo);
			//根据订单号查询销售订单表
			infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if(infoSaleOrderVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到销售订单表相应信息");
				return uocMessage;
			}
			processInstanceId = infoSaleOrderVo.getProc_inst_id();
			//任务提交，进入下一环节
			//按操作编码流转
			UocMessage completeMsg = new UocMessage();
			if("1".equals(flowType)){
				logger.info("<<<<<<<<<================按操作编码流转，提交任务==================>>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, actionCode);
			}
			//自动流转
			else{
				logger.info("<<<<<<<<<================自动流转，提交任务==================>>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, null);
			}
			if(!"0000".equals(completeMsg.getRespCode())){
				logger.info("<<<<<<<<================提交任务失败=============>>>>>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(completeMsg.getContent());
				return uocMessage;
			}
			//获取当前任务
			UocMessage findMsg = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(processInstanceId, orderNo);
			if(!"0000".equals(findMsg.getRespCode())){
				logger.info("<<<<<<<<================查询任务失败=============>>>>>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(findMsg.getContent());
				return uocMessage;
			}
			tacheCode = findMsg.getArgs().get("current_tache").toString();
			logger.info("<<<<<<<==============查询任务成功，环节编码为:"+tacheCode+"==============>>>>>>>>");
			ProcModTacheVo procModTacheVo = new ProcModTacheVo();
			procModTacheVo.setTache_code(tacheCode);
			//查询环节配置表
			procModTacheVo = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
			if(procModTacheVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到环节配置信息");
				return uocMessage;
			}
			String orderCompletePreCode =  getOrderCompleteCode("orderCompletePre");
			String orderCompleteSaleCode = getOrderCompleteCode("orderCompleteSale");
			//判断当前环节为竣工
			if(orderCompletePreCode.equals(procModTacheVo.getTache_code()) || orderCompleteSaleCode.equals(procModTacheVo.getTache_code())){
				logger.info("<<<<<<<<=================调用竣工服务=============>>>>>>>>>");
				return infoServiceOrderBaseDu.createServiceOrderComplete(orderNo, operType);
			}
			else{
				// 自动环节、自动(同步调用)
				if ("100".equals(procModTacheVo.getTache_type()) || "103".equals(procModTacheVo.getTache_type())) {
					logger.info("<<<<<<<<=================调用Base0013记录任务日志=============>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					procInstTaskInstVo.setTask_state("102");
					UocMessage chngMesg=changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "104");
					if(!"0000".equals(chngMesg.getRespCode())){
						return chngMesg;
					}

					ProcInstTaskInstVo vo =procInstTaskInstServDu.queryProcInstTaskInstByOrderNoAndTaskState(procInstTaskInstVo);
					if (vo != null) {
						ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
						String id = getIdServDu.getId("createLogId", vo.getProvince_code(), "*", "");
						procInstTaskDealRecordPo.setId(id);
						procInstTaskDealRecordPo.setTask_id(vo.getTask_id());
						procInstTaskDealRecordPo.setProvince_code(vo.getProvince_code());
						procInstTaskDealRecordPo.setArea_code(vo.getArea_code());
						procInstTaskDealRecordPo.setPart_month(vo.getPart_month());
						procInstTaskDealRecordPo.setDeal_time(DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss));
						procInstTaskDealRecordPo.setDeal_oper_no("Auto");
						procInstTaskDealRecordPo.setDeal_system_no("");
						procInstTaskDealRecordPo.setDeal_code("");
						procInstTaskDealRecordPo.setDeal_desc("系统自动处理");
						procInstTaskDealRecordPo.setOrder_no(procInstTaskInstVo.getOrder_no());
						procInstTaskDealRecordPo.setOrder_type(procInstTaskInstVo.getOrder_type());
						procInstTaskDealRecordPo.setTache_code(vo.getTache_code());
						procInstTaskDealRecordPo.setOper_code(vo.getOper_code());
						procInstTaskDealRecordPo.setCreate_time(vo.getCreate_time());
						procInstTaskDealRecordPo.setProd_code(vo.getProd_code());
						boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
						if (!flag) {
							uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocMessage.setContent("创建人工任务操作记录失败");
							return uocMessage;
						}
					}

					logger.info("<<<<<<<<=================调用环节流转服务=============>>>>>>>>>");
					// 如果是自动(同步调用)则call_type填1，否则填0
					if ("103".equals(procModTacheVo.getTache_type())) {
						return processCallServDu.processCall(orderNo, operType, "1", jsonInfoExt, null);
					} else {
						return processCallServDu.processCall(orderNo, operType, "0", jsonInfoExt, null);
					}
				}
				//人工环节
				else if("101".equals(procModTacheVo.getTache_type())){
					logger.info("<<<<<<<<=================调用转人工服务（人工）=============>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					return changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "100");
				}
				// 等待
				else if ("102".equals(procModTacheVo.getTache_type())) {
					logger.info("<<<<<<<===============调用转人工服务（等待）============>>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					return changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "103");
				}
				else{
					uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
					uocMessage.setContent("环节配置错误，配置信息不为自动或人工环节");
					return uocMessage;
				}
			}
		}
		//服务订单
		else if("101".equals(operType)){
			logger.info("<<<<<<==============服务订单==============>>>>>>>>");
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(orderNo);
			//根据订单号查询服务订单表
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			if(infoServiceOrderVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到服务订单表相应数据");
				return uocMessage;
			}
			processInstanceId = infoServiceOrderVo.getProc_inst_id();
			//获取当前任务
			UocMessage findMsg = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(processInstanceId, orderNo);
			if(!"0000".equals(findMsg.getRespCode())){
				logger.info("<<<<<<==================查询当前任务失败===========>>>>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(findMsg.getContent());
				return uocMessage;
			}
			tacheCode = findMsg.getArgs().get("current_tache").toString();
			//流转前查看短信模版 短信发送
			sendSms(orderNo, operType, tacheCode, infoServiceOrderVo.getProvince_code(), infoServiceOrderVo.getArea_code(), infoServiceOrderVo.getPart_month(),
					infoServiceOrderVo.getOper_code(), infoServiceOrderVo.getAcc_nbr(), infoServiceOrderVo.getAccept_oper_no(), infoServiceOrderVo.getAccept_depart_no(),
					infoServiceOrderVo.getSale_order_no());

			//任务提交，进入下一环节
			//按操作编码流转
			UocMessage completeMsg = new UocMessage();
			if("1".equals(flowType)){
				logger.info("<<<<<<<<<================按操作编码流转，提交任务==================>>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, actionCode);
			}
			//自动流转
			else{
				logger.info("<<<<<<<<<================自动流转，提交任务==================>>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, null);
			}
			if(!"0000".equals(completeMsg.getRespCode())){
				logger.info("<<<<<<<<<================提交任务失败==================>>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(completeMsg.getContent());
				return uocMessage;
			}
			//获取当前任务
			 findMsg = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(processInstanceId, orderNo);
			if(!"0000".equals(findMsg.getRespCode())){
				logger.info("<<<<<<<<<================获取当前任务失败==================>>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(findMsg.getContent());
				return uocMessage;
			}
			tacheCode = findMsg.getArgs().get("current_tache").toString();
			logger.info("<<<<<<<<<================获取当前任务成功，当前环节编码为："+tacheCode+"==================>>>>>>>>");
			ProcModTacheVo procModTacheVo = new ProcModTacheVo();
			procModTacheVo.setTache_code(tacheCode);
			//查询环节配置表
			procModTacheVo = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
			if(procModTacheVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到环节配置信息");
				return uocMessage;
			}

			String orderCompleteServiceCode = getOrderCompleteCode("orderCompleteService");
			//当前环节为竣工
			if(orderCompleteServiceCode.equals(procModTacheVo.getTache_code())){
				//调用竣工服务
				logger.info("<<<<<<<<<==================调用竣工服务================>>>>>>>>");
				return infoServiceOrderBaseDu.createServiceOrderComplete(orderNo, operType);
			}
			//当前环节不为竣工
			else{
				// 自动环节、自动(同步调用)
				if ("100".equals(procModTacheVo.getTache_type()) || "103".equals(procModTacheVo.getTache_type())) {
					logger.info("<<<<<<<<=================调用Base0013记录任务日志=============>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					procInstTaskInstVo.setTask_state("102");
					UocMessage chngMesg=changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "104");
					if(!"0000".equals(chngMesg.getRespCode())){
						return chngMesg;
					}

					ProcInstTaskInstVo vo =procInstTaskInstServDu.queryProcInstTaskInstByOrderNoAndTaskState(procInstTaskInstVo);
					if (vo != null) {
						ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
						String id = getIdServDu.getId("createLogId", vo.getProvince_code(), "*", "");
						procInstTaskDealRecordPo.setId(id);
						procInstTaskDealRecordPo.setTask_id(vo.getTask_id());
						procInstTaskDealRecordPo.setProvince_code(vo.getProvince_code());
						procInstTaskDealRecordPo.setArea_code(vo.getArea_code());
						procInstTaskDealRecordPo.setPart_month(vo.getPart_month());
						procInstTaskDealRecordPo.setDeal_time(DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss));
						procInstTaskDealRecordPo.setDeal_oper_no("Auto");
						procInstTaskDealRecordPo.setDeal_system_no("");
						procInstTaskDealRecordPo.setDeal_code("");
						procInstTaskDealRecordPo.setDeal_desc("系统自动处理");
						procInstTaskDealRecordPo.setOrder_no(procInstTaskInstVo.getOrder_no());
						procInstTaskDealRecordPo.setOrder_type(procInstTaskInstVo.getOrder_type());
						procInstTaskDealRecordPo.setTache_code(vo.getTache_code());
						procInstTaskDealRecordPo.setOper_code(vo.getOper_code());
						procInstTaskDealRecordPo.setCreate_time(vo.getCreate_time());
						procInstTaskDealRecordPo.setProd_code(vo.getProd_code());
						boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
						if (!flag) {
							uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocMessage.setContent("创建人工任务操作记录失败");
							return uocMessage;
						}
					}

					logger.info("<<<<<<<<=================调用环节流转服务=============>>>>>>>>>");
					// 如果是自动(同步调用)则call_type填1，否则填0
					if ("103".equals(procModTacheVo.getTache_type())) {
						return processCallServDu.processCall(orderNo, operType, "1", jsonInfoExt, null);
					} else {
						return processCallServDu.processCall(orderNo, operType, "0", jsonInfoExt, null);
					}
				}
				//人工环节
				else if("101".equals(procModTacheVo.getTache_type())){
					logger.info("<<<<<<<<<==================调用转人工服务（人工）================>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					return changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo,"100");
				}
				// 等待
				else if ("102".equals(procModTacheVo.getTache_type())) {
					logger.info("<<<<<<<<<==================调用转人工服务(等待)================>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					return changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "103");
				}
				else{
					uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
					uocMessage.setContent("环节配置错误，配置信息不为自动或人工环节");
					return uocMessage;
				}
			}
		}
		//支付订单
		else if("102".equals(operType)){
			logger.info("<<<<<<==================支付订单===========>>>>>>>>>>");
			InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
			infoPayOrderVo.setPay_order_no(orderNo);
			//根据订单号查询支付订单表
			infoPayOrderVo = infoPayOrderServDu.getInfoPayOrderByPayOrderNo(infoPayOrderVo);
			if(infoPayOrderVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到支付订单表相应信息");
				return uocMessage;
			}
			processInstanceId = infoPayOrderVo.getProc_inst_id();
			//任务提交，进入下一环节
			//按操作编码流转
			UocMessage completeMsg = new UocMessage();
			if("1".equals(flowType)){
				logger.info("<<<<<<==================按操作编码流转，提交任务===========>>>>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, actionCode);
			}
			//自动流转
			else{
				logger.info("<<<<<<==================自动流转，提交任务===========>>>>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, null);
			}
			if(!"0000".equals(completeMsg.getRespCode())){
				logger.info("<<<<<<==================提交任务失败===========>>>>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(completeMsg.getContent());
				return uocMessage;
			}
			//获取当前任务
			UocMessage findMsg = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(processInstanceId, orderNo);
			if(!"0000".equals(findMsg.getRespCode())){
				logger.info("<<<<<<==================查询当前任务失败===========>>>>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(findMsg.getContent());
				return uocMessage;
			}
			tacheCode = findMsg.getArgs().get("current_tache").toString();
			logger.info("<<<<<<==================查询当前任务成功，当前环节编码为："+tacheCode+"===========>>>>>>>>>>");
			ProcModTacheVo procModTacheVo = new ProcModTacheVo();
			procModTacheVo.setTache_code(tacheCode);
			//查询环节配置表
			procModTacheVo = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
			if(procModTacheVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到环节配置信息");
				return uocMessage;
			}
			String orderCompletePayCode = getOrderCompleteCode("orderCompletePay");
			//当前环节为竣工
			if(orderCompletePayCode.equals(procModTacheVo.getTache_code())){
				//调用竣工服务
				logger.info("<<<<<<<===============调用竣工服务============>>>>>>>>>>");
				return infoServiceOrderBaseDu.createServiceOrderComplete(orderNo, operType);
			}
			//当前环节不为竣工
			else{
				// 自动环节、自动(同步调用)
				if ("100".equals(procModTacheVo.getTache_type()) || "103".equals(procModTacheVo.getTache_type())) {
					logger.info("<<<<<<<<=================调用Base0013记录任务日志=============>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					procInstTaskInstVo.setTask_state("102");
					UocMessage chngMesg=changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "104");
					if(!"0000".equals(chngMesg.getRespCode())){
						return chngMesg;
					}

					ProcInstTaskInstVo vo =procInstTaskInstServDu.queryProcInstTaskInstByOrderNoAndTaskState(procInstTaskInstVo);
					if (vo != null) {
						ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
						String id = getIdServDu.getId("createLogId", vo.getProvince_code(), "*", "");
						procInstTaskDealRecordPo.setId(id);
						procInstTaskDealRecordPo.setTask_id(vo.getTask_id());
						procInstTaskDealRecordPo.setProvince_code(vo.getProvince_code());
						procInstTaskDealRecordPo.setArea_code(vo.getArea_code());
						procInstTaskDealRecordPo.setPart_month(vo.getPart_month());
						procInstTaskDealRecordPo.setDeal_time(DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss));
						procInstTaskDealRecordPo.setDeal_oper_no("Auto");
						procInstTaskDealRecordPo.setDeal_system_no("");
						procInstTaskDealRecordPo.setDeal_code("");
						procInstTaskDealRecordPo.setDeal_desc("系统自动处理");
						procInstTaskDealRecordPo.setOrder_no(procInstTaskInstVo.getOrder_no());
						procInstTaskDealRecordPo.setOrder_type(procInstTaskInstVo.getOrder_type());
						procInstTaskDealRecordPo.setTache_code(vo.getTache_code());
						procInstTaskDealRecordPo.setOper_code(vo.getOper_code());
						procInstTaskDealRecordPo.setCreate_time(vo.getCreate_time());
						procInstTaskDealRecordPo.setProd_code(vo.getProd_code());
						boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
						if (!flag) {
							uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocMessage.setContent("创建人工任务操作记录失败");
							return uocMessage;
						}
					}

					logger.info("<<<<<<<<=================调用环节流转服务=============>>>>>>>>>");
					// 如果是自动(同步调用)则call_type填1，否则填0
					if ("103".equals(procModTacheVo.getTache_type())) {
						return processCallServDu.processCall(orderNo, operType, "1", jsonInfoExt, null);
					} else {
						return processCallServDu.processCall(orderNo, operType, "0", jsonInfoExt, null);
					}
				}
				//人工环节
				else if("101".equals(procModTacheVo.getTache_type())){
					logger.info("<<<<<<<===============调用转人工服务（人工）============>>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					return changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo,"100");
				}
				// 等待
				else if ("102".equals(procModTacheVo.getTache_type())) {
					logger.info("<<<<<<<===============调用转人工服务（等待）============>>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					return changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "103");
				}
				else{
					uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
					uocMessage.setContent("环节配置错误，配置信息不为自动或人工环节");
					return uocMessage;
				}
			}
		}
		//交付订单
		else if("103".equals(operType)){
			logger.info("<<<<<<<===============交付订单=============>>>>>>>");
			InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
			infoDeliverOrderVo.setDeliver_order_no(orderNo);
			//根据订单号查询交付订单表
			infoDeliverOrderVo = infoDeliverOrderServDu.getInfoDeliverOrderByPayDeliverOrderNo(infoDeliverOrderVo);
			if(infoDeliverOrderVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("为查询到交付订单表相应信息");
				return uocMessage;
			}
			processInstanceId = infoDeliverOrderVo.getProc_inst_id();
			//任务提交，进入下一环节
			//按操作编码流转
			UocMessage completeMsg = new UocMessage();
			if("1".equals(flowType)){
				logger.info("<<<<<<<===============按操作编码流转，提交任务=============>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, actionCode);
			}
			//自动流转
			else{
				logger.info("<<<<<<<===============自动流转，提交任务=============>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, null);
			}
			if(!"0000".equals(completeMsg.getRespCode())){
				logger.info("<<<<<<<===============提交任务失败=============>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(completeMsg.getContent());
				return uocMessage;
			}
			//获取当前任务
			UocMessage findMsg = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(processInstanceId, orderNo);
			if(!"0000".equals(findMsg.getRespCode())){
				logger.info("<<<<<<<===============获取当前任务失败=============>>>>>>>");
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(findMsg.getContent());
				return uocMessage;
			}
			tacheCode = findMsg.getArgs().get("current_tache").toString();
			logger.info("<<<<<<<===============获取当前任务成功，当前环节编码为："+tacheCode+"=============>>>>>>>");
			ProcModTacheVo procModTacheVo = new ProcModTacheVo();
			procModTacheVo.setTache_code(tacheCode);
			//查询环节配置表
			procModTacheVo = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
			if(procModTacheVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到环节配置信息");
				return uocMessage;
			}
			String orderCompleteDeliverCode = getOrderCompleteCode("orderCompleteDeliver");
			//当前环节为竣工
			if(orderCompleteDeliverCode.equals(procModTacheVo.getTache_code())){
				//调用竣工服务
				logger.info("<<<<<<<===============调用竣工服务=============>>>>>>>");
				return infoServiceOrderBaseDu.createServiceOrderComplete(orderNo, operType);
			}
			//当前环节不为竣工
			else{
				// 自动环节、自动(同步调用)
				if ("100".equals(procModTacheVo.getTache_type()) || "103".equals(procModTacheVo.getTache_type())) {
					logger.info("<<<<<<<<=================调用Base0013记录任务日志=============>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					procInstTaskInstVo.setTask_state("102");
					UocMessage chngMesg=changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "104");
					if(!"0000".equals(chngMesg.getRespCode())){
						return chngMesg;
					}

					ProcInstTaskInstVo vo =procInstTaskInstServDu.queryProcInstTaskInstByOrderNoAndTaskState(procInstTaskInstVo);
					if (vo != null) {
						ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
						String id = getIdServDu.getId("createLogId", vo.getProvince_code(), "*", "");
						procInstTaskDealRecordPo.setId(id);
						procInstTaskDealRecordPo.setTask_id(vo.getTask_id());
						procInstTaskDealRecordPo.setProvince_code(vo.getProvince_code());
						procInstTaskDealRecordPo.setArea_code(vo.getArea_code());
						procInstTaskDealRecordPo.setPart_month(vo.getPart_month());
						procInstTaskDealRecordPo.setDeal_time(DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss));
						procInstTaskDealRecordPo.setDeal_oper_no("Auto");
						procInstTaskDealRecordPo.setDeal_system_no("");
						procInstTaskDealRecordPo.setDeal_code("");
						procInstTaskDealRecordPo.setDeal_desc("系统自动处理");
						procInstTaskDealRecordPo.setOrder_no(procInstTaskInstVo.getOrder_no());
						procInstTaskDealRecordPo.setOrder_type(procInstTaskInstVo.getOrder_type());
						procInstTaskDealRecordPo.setTache_code(vo.getTache_code());
						procInstTaskDealRecordPo.setOper_code(vo.getOper_code());
						procInstTaskDealRecordPo.setCreate_time(vo.getCreate_time());
						procInstTaskDealRecordPo.setProd_code(vo.getProd_code());
						boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
						if (!flag) {
							uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocMessage.setContent("创建人工任务操作记录失败");
							return uocMessage;
						}
					}

					logger.info("<<<<<<<<=================调用环节流转服务=============>>>>>>>>>");
					// 如果是自动(同步调用)则call_type填1，否则填0
					if ("103".equals(procModTacheVo.getTache_type())) {
						return processCallServDu.processCall(orderNo, operType, "1", jsonInfoExt, null);
					} else {
						return processCallServDu.processCall(orderNo, operType, "0", jsonInfoExt, null);
					}
				}
				//人工环节
				else if("101".equals(procModTacheVo.getTache_type())){
					logger.info("<<<<<<<===============调用转人工服务（人工）=============>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					return changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "100");
				}
				// 等待
				else if ("102".equals(procModTacheVo.getTache_type())) {
					logger.info("<<<<<<<===============调用转人工服务（等待）============>>>>>>>>>>");
					ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(orderNo);
					procInstTaskInstVo.setOrder_type(operType);
					return changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "103");
				}
				else{
					uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
					uocMessage.setContent("环节配置错误，配置信息不为自动或人工环节");
					return uocMessage;
				}
			}
		} else if ("105".equals(operType)) {
			logger.info("<<<<<<==============已撤销服务订单==============>>>>>>>>");
			InfoServiceOrderHisVo infoServiceOrderVo = new InfoServiceOrderHisVo();
			infoServiceOrderVo.setServ_order_no(orderNo);
			// 根据订单号查询服务订单历史表
			infoServiceOrderVo = infoServiceOrderHisServDu.queryInfoServiceOrderHisByOrderNo(infoServiceOrderVo).get(0);
			if (infoServiceOrderVo == null) {
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("未查询到服务订单历史表相应数据");
				return uocMessage;
			}
			processInstanceId = infoServiceOrderVo.getProc_inst_id();
			// 任务提交，进入下一环节
			// 按操作编码流转
			UocMessage completeMsg = new UocMessage();
			if ("1".equals(flowType)) {
				logger.info("<<<<<<<<<================按操作编码流转，提交任务==================>>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, actionCode);
			}
			// 自动流转
			else {
				logger.info("<<<<<<<<<================自动流转，提交任务==================>>>>>>>>");
				completeMsg = completePersonalTaskServDu.completePersonalTask(processInstanceId, orderNo, null);
			}
			return completeMsg;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("输入的流转类型错误");
			return uocMessage;
		}
	}

	/**
	 * 根据typeCode查询code_list表code_id信息
	 * */
	public String getOrderCompleteCode(String typeCode) throws Exception{
		String result = "";
		CodeListVo codeListVo = new CodeListVo();
		codeListVo.setType_code(typeCode);
		codeListVo = codeListServDu.queryCodeListByTypeCode(codeListVo);
		if(codeListVo != null){
			result = codeListVo.getCode_id();
		}
		return result;
	}

	/**
	 * 生成短信内容
	 * */
	public String getSendMsg(String modCode,String orderNo,String operType) throws Exception{
		String msg = "";
		UocMessage ordMessage=redisServiceServ.queryDataFromRedis("queryordModDefineByModCode");
		if(RespCodeContents.SUCCESS.equals(ordMessage.getRespCode())){
			RedisData redisDataPoRes=(RedisData) ordMessage.getArgs().get("RedisDataResult");
			OrdModDefine ordModDefineRes=(OrdModDefine) redisDataPoRes.getMap().get(modCode);
			if(ordModDefineRes!=null){
				msg = ordModDefineRes.getJson_module().toString();
				UocMessage omfMsgSecond = ordModFunctionServDu.queryOrdMod(orderNo, "105", operType);
				if(RespCodeContents.SUCCESS.equals(omfMsgSecond.getRespCode())){
					UocMessage uotbyOrdModMsg = ordModFunctionServDu.outByOrdMod(orderNo, omfMsgSecond.getArgs().get("mod_code").toString(), operType, "");
					if("0000".equals(uotbyOrdModMsg.getRespCode())){
						String json_info=uotbyOrdModMsg.getArgs().get("json_info").toString();
						Map<String,Object> map = jsonToBeanServDu.jsonToMap(json_info);
						//将短信模板里面的值替换掉
						Iterator<String> iterator = map.keySet().iterator();
						while(iterator.hasNext()){
							String key = iterator.next().toString();
							msg = msg.replaceAll(key, map.get(key).toString());
						}
					}
				}
			}
		}
		return msg.trim();
	}

	/**发送短信*/
	public boolean sendSms(String orderNo, String operType, String tacheCode, String provinceCode, String areaCode, String partMonth, String operCode, String accNbr,
			String acceptOperNo, String acceptDepartNo, String saleOrderNo) throws Exception {
		UocMessage omfMsgFirst = ordModFunctionServDu.queryOrdMod(orderNo, "104", operType);
		boolean isSuccess = false;
		if(RespCodeContents.SUCCESS.equals(omfMsgFirst.getRespCode())){
			String msg = getSendMsg(omfMsgFirst.getArgs().get("mod_code").toString(),orderNo,operType);
			if(!"".equals(msg)){
				//将短信内容写入短信表
				InfoSmsWarnPo po= new InfoSmsWarnPo();
				po.setOrder_no(orderNo);
				po.setOrder_type(operType);
			    po.setProvince_code(provinceCode);
			    po.setArea_code(areaCode);
			    po.setPart_month(partMonth);
				po.setTache_code(tacheCode);
				po.setAcc_nbr(accNbr);
				po.setSms_content(msg);
				po.setAccept_oper_no(acceptOperNo);
				po.setAccept_depart_no(acceptDepartNo);
				po.setRemarks("acc_nbr");
				String date = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
				po.setInsert_time(date);
				String id = getIdServDu.getId("createLogId", provinceCode, "*", "");
				po.setId(id);

				// 调用发短信接口
				TextShortMessage textMsg = new TextShortMessage();
				textMsg.setDesMobile(accNbr);
				textMsg.setMsgContent(msg);
				textMsg.setMsgFromSystem("UOC");
				textMsg.setBusinessType(operCode);
				BusiMessage<String> resultMsg = uocSmsService.sendShortMessage(textMsg);
				isSuccess = resultMsg.getSuccess();

				if (isSuccess) {
					po.setSend_status(resultMsg.getCode());
					logger.info("------发送短信到能力平台成功!------");
				} else {
					po.setSend_status(resultMsg.getCode());
					logger.info("------发送短信到能力平台失败：" + resultMsg.getMsg());
				}

				infoSmsWarnServ.createInfoSmsWarn(po);
			}
		}
		UocMessage omfMsgSecond = ordModFunctionServDu.queryOrdMod(orderNo, "106", operType);
		if(RespCodeContents.SUCCESS.equals(omfMsgSecond.getRespCode())){
			String msg = getSendMsg(omfMsgSecond.getArgs().get("mod_code").toString(),orderNo,operType);
			InfoDeliverOrderPo infoDeliverOrderPoQuery = new InfoDeliverOrderPo();
			infoDeliverOrderPoQuery.setSale_order_no(saleOrderNo);
			List<InfoDeliverOrderPo> list = infoDeliverOrderServ.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderPoQuery);
			if(list != null && list.size()>0){
				String contactTel = list.get(0).getContact_tel();
				if(!"".equals(msg)){
					//将短信内容写入短信表
					InfoSmsWarnPo smsPo = new InfoSmsWarnPo();
					smsPo.setOrder_no(orderNo);
					smsPo.setOrder_type(operType);
					smsPo.setProvince_code(provinceCode);
					smsPo.setArea_code(areaCode);
					smsPo.setPart_month(partMonth);
					smsPo.setTache_code(tacheCode);
					smsPo.setAcc_nbr(contactTel);
					smsPo.setSms_content(msg);
					smsPo.setAccept_oper_no(acceptOperNo);
					smsPo.setAccept_depart_no(acceptDepartNo);
					smsPo.setRemarks("contact_tel");
					String date = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
					smsPo.setInsert_time(date);
					String id = getIdServDu.getId("createLogId", provinceCode, "*", "");
					smsPo.setId(id);

					// 调用发短信接口
					TextShortMessage textMsg = new TextShortMessage();
					textMsg.setMsgId(id);
					textMsg.setDesMobile(contactTel);
					textMsg.setMsgContent(msg);
					textMsg.setMsgFromSystem("UOC");
					textMsg.setBusinessType(operCode);
					BusiMessage<String> resultMsg = uocSmsService.sendShortMessage(textMsg);
					isSuccess = resultMsg.getSuccess();

					if (isSuccess) {
						smsPo.setSend_status("1");
						logger.info("------发送短信到能力平台成功!------");
					} else {
						smsPo.setSend_status("2");
						logger.info("------发送短信到能力平台失败：" + resultMsg.getMsg());
					}

					infoSmsWarnServ.createInfoSmsWarn(smsPo);
				}
			}
		}

		return isSuccess;
	}
}
