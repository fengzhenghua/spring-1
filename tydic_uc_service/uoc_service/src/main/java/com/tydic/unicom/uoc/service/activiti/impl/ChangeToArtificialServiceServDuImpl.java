package com.tydic.unicom.uoc.service.activiti.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.uoc.business.common.vo.ProcModTacheVo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ChangeToArtificialServiceServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcModAppServDu;
import com.tydic.unicom.uoc.service.activiti.vo.ProcModAppVo;
import com.tydic.unicom.uoc.service.common.impl.StrUtil;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcInstTaskInstServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoPayOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.uoc.service.task.interfaces.ArtificialTaskAssignServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ChangeToArtificialServiceServDu")
public class ChangeToArtificialServiceServDuImpl implements ChangeToArtificialServiceServDu{

	Logger logger = Logger.getLogger(ChangeToArtificialServiceServDuImpl.class);

	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;

	@Autowired
	private InfoPayOrderServDu infoPayOrderServDu;

	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;

	@Autowired
	private ProcInstTaskInstServDu procInstTaskInstServDu;

	@Autowired
	private GetIdServDu getIdServDu;
	@Autowired
	private FindMyPersonalTaskServDu findMyPersonalTaskServDu;
	@Autowired
	private ProcModTacheServDu procModTacheServDu;
	@Autowired
	private ProcModAppServDu procModAppServDu;

	@Autowired
	private ArtificialTaskAssignServDu artificialTaskAssignServDu;

	@Autowired
	private InfoServiceOrderProdMapServ infoServiceOrderProdMapServ;



	@Override

	public UocMessage changeToArtificialService(ProcInstTaskInstVo procInstTaskInstVo,String task_property) throws Exception {

		boolean result = false;
		UocMessage uocMessage = new UocMessage();
		String taskId = "";
		//环节有效期
		String limit_time = "";
		//流程有效期
		String global_limit_time = "";
		String oper_no = "";
		String depart_no = "";
		String depart_name = "";
		String oper_code = "";
		String prod_code="";
		if(procInstTaskInstVo == null){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("转人工服务传入参数错误");
			return uocMessage;
		}
		//存在人工实例数据
		if(!StringUtils.isEmpty(procInstTaskInstVo.getTache_code())){
			procInstTaskInstVo.setTask_state("101");
			procInstTaskInstVo.setTask_property("102");
			result = procInstTaskInstServDu.updateByOrderNo(procInstTaskInstVo);
			if(result){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				return uocMessage;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("更新人工任务实例状态失败");
				return uocMessage;
			}
		}

		ProcModTacheVo procModTacheVo = new ProcModTacheVo();
		ProcInstTaskInstVo procInstTaskInstVoInsert = new ProcInstTaskInstVo();

		//销售订单
		if("100".equals(procInstTaskInstVo.getOrder_type())){
			InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(procInstTaskInstVo.getOrder_no());
			//根据订单号查询销售订单表
			infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if(infoSaleOrderVo != null){
				InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
				infoServiceOrderVo.setSale_order_no(procInstTaskInstVo.getOrder_no());
				//根据订单号查询服务订单表
				List<InfoServiceOrderVo> listServiceOrder = infoServiceOrderServDu.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderVo);
				oper_no = infoSaleOrderVo.getAccept_oper_no();
				depart_no = infoSaleOrderVo.getAccept_depart_no();
				depart_name = infoSaleOrderVo.getAccept_depart_name();
				if(listServiceOrder != null){
					oper_code = listServiceOrder.get(0).getOper_code();
					if(!"".equals(oper_code)){
						ProcModAppVo procMapAppVo = new ProcModAppVo();
						procMapAppVo.setOper_code(oper_code);
						procMapAppVo = procModAppServDu.queryProcModAppByOperCode(procMapAppVo);
						if(procMapAppVo.getUsed_time_len() !=null && !"".equals(procMapAppVo.getUsed_time_len())){
							global_limit_time = getLimitTime(procMapAppVo.getUsed_time_len());
						}
					}
				}
			}

			//BASE0012服务获取当前环节
			UocMessage fmtMessage = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(infoSaleOrderVo.getProc_inst_id(), procInstTaskInstVo.getOrder_no());
			if(!"0000".equals(fmtMessage.getRespCode())){
				return fmtMessage;
			}
			Map<String,Object> fmtMap = fmtMessage.getArgs();
			taskId = getIdServDu.getId("createLogId", infoSaleOrderVo.getProvince_code(), "*", "");
			procModTacheVo.setTache_code(fmtMap.get("current_tache").toString());
			procModTacheVo = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
			if(procModTacheVo !=null && procModTacheVo.getUsed_time_len() !=null && !"".equals(procModTacheVo.getUsed_time_len())){
				limit_time = getLimitTime(procModTacheVo.getUsed_time_len());
			}
			//拼装要插入的数据
			procInstTaskInstVoInsert = setProcInstTaskInstVoData(oper_no, depart_no, depart_name, oper_code, procInstTaskInstVo.getOrder_no(), procInstTaskInstVo.getOrder_type(),
					fmtMap.get("current_task").toString(), fmtMap.get("current_task_name").toString(), fmtMap.get("current_tache")
							.toString(), infoSaleOrderVo.getProc_inst_id(), infoSaleOrderVo.getProvince_code(),
					infoSaleOrderVo.getArea_code(), taskId, limit_time, global_limit_time, prod_code,task_property,procInstTaskInstVo.getTask_state());

			UocMessage taskMessage = artificialTaskAssignServDu.taskDefaultAssignment(procInstTaskInstVoInsert);
			if (RespCodeContents.SUCCESS.equals(taskMessage.getRespCode())) {
				// 插入数据到人工任务实例表
				result = procInstTaskInstServDu.create(procInstTaskInstVoInsert);
				if (!result) {
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("插入数据到人工任务实例表出错");
					return uocMessage;
				}
			} else {
				return taskMessage;
			}

		}
		//服务订单
		else if("101".equals(procInstTaskInstVo.getOrder_type())){
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(procInstTaskInstVo.getOrder_no());
			//根据订单号查询服务订单表
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			if(infoServiceOrderVo != null){
				oper_no = infoServiceOrderVo.getAccept_oper_no();
				depart_no = infoServiceOrderVo.getAccept_depart_no();
				depart_name = infoServiceOrderVo.getAccept_depart_name();
				oper_code = infoServiceOrderVo.getOper_code();
				if(!"".equals(oper_code)){
					ProcModAppVo procMapAppVo = new ProcModAppVo();
					procMapAppVo.setOper_code(oper_no);
					procMapAppVo = procModAppServDu.queryProcModAppByOperCode(procMapAppVo);
					if(procMapAppVo !=null && procModTacheVo.getUsed_time_len() !=null && !"".equals(procMapAppVo.getUsed_time_len())){
						global_limit_time = getLimitTime(procMapAppVo.getUsed_time_len());
					}
				}
			}

			//BASE0012服务获取当前环节
			UocMessage fmtMessage = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(infoServiceOrderVo.getProc_inst_id(), procInstTaskInstVo.getOrder_no());
			if(!"0000".equals(fmtMessage.getRespCode())){
				return fmtMessage;
			}
			Map<String,Object> fmtMap = fmtMessage.getArgs();
			taskId = getIdServDu.getId("createLogId", infoServiceOrderVo.getProvince_code(), "*", "");
			procModTacheVo.setTache_code(fmtMap.get("current_tache").toString());
			procModTacheVo = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
			if(procModTacheVo!=null && procModTacheVo.getUsed_time_len() !=null && !"".equals(procModTacheVo.getUsed_time_len())){
				limit_time = getLimitTime(procModTacheVo.getUsed_time_len());
			}
			//服务订单人工任务实例表增加产品字段更新
			InfoServiceOrderProdMapPo infoServiceOrderProdMapPo = new InfoServiceOrderProdMapPo();
			infoServiceOrderProdMapPo.setServ_order_no(procInstTaskInstVo.getOrder_no());
			List<InfoServiceOrderProdMapPo> infoServiceOrderProdMapPoTemp=infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapPo);
			if(infoServiceOrderProdMapPoTemp!=null){
				prod_code=infoServiceOrderProdMapPoTemp.get(0).getProd_code();
			}
			//拼装要插入的数据
			procInstTaskInstVoInsert = setProcInstTaskInstVoData(oper_no, depart_no, depart_name, oper_code, procInstTaskInstVo.getOrder_no(), procInstTaskInstVo.getOrder_type(),
					fmtMap.get("current_task").toString(), fmtMap.get("current_task_name").toString(), fmtMap.get("current_tache")
							.toString(), infoServiceOrderVo.getProc_inst_id(), infoServiceOrderVo.getProvince_code(),
					infoServiceOrderVo.getArea_code(), taskId, limit_time, global_limit_time, prod_code,task_property,procInstTaskInstVo.getTask_state());

			UocMessage taskMessage = artificialTaskAssignServDu.taskDefaultAssignment(procInstTaskInstVoInsert);
			if (RespCodeContents.SUCCESS.equals(taskMessage.getRespCode())) {
				// 插入数据到人工任务实例表
				result = procInstTaskInstServDu.create(procInstTaskInstVoInsert);
				logger.info("插入数据到人工任务实例表---result="+result);
				logger.info("插入数据到人工任务实例表---procInstTaskInstVoInsert="+procInstTaskInstVoInsert.toString());
				if (!result) {
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("插入数据到人工任务实例表出错");
					return uocMessage;
				}
			} else {
				return taskMessage;
			}
		}
		//支付订单
		else if("102".equals(procInstTaskInstVo.getOrder_type())){
			InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
			infoPayOrderVo.setPay_order_no(procInstTaskInstVo.getOrder_no());
			//根据订单号查询支付订单表
			infoPayOrderVo = infoPayOrderServDu.getInfoPayOrderByPayOrderNo(infoPayOrderVo);
			if(infoPayOrderVo != null){
				InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
				if("100".equals(infoPayOrderVo.getRela_order_type())){
					infoServiceOrderVo.setSale_order_no(procInstTaskInstVo.getOrder_no());
				}else if("101".equals(infoPayOrderVo.getRela_order_type())){
					infoServiceOrderVo.setOfr_order_no(procInstTaskInstVo.getOrder_no());
				}else if("102".equals(infoPayOrderVo.getRela_order_type())){
					infoServiceOrderVo.setServ_order_no(procInstTaskInstVo.getOrder_no());
				}
				//根据订单号查询服务订单表
				List<InfoServiceOrderVo> ServiceOrder = infoServiceOrderServDu.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
				oper_no = infoPayOrderVo.getAccept_oper_no();
				depart_no = infoPayOrderVo.getAccept_depart_no();
				depart_name = infoPayOrderVo.getAccept_depart_name();
				if(ServiceOrder != null){
					oper_code = ServiceOrder.get(0).getOper_code();
					if(!"".equals(oper_code)){
						ProcModAppVo procMapAppVo = new ProcModAppVo();
						procMapAppVo.setOper_code(oper_no);
						procMapAppVo = procModAppServDu.queryProcModAppByOperCode(procMapAppVo);
						if(procModTacheVo!=null && procModTacheVo.getUsed_time_len() !=null && !"".equals(procMapAppVo.getUsed_time_len())){
							global_limit_time = getLimitTime(procMapAppVo.getUsed_time_len());
						}
					}
				}
			}

			//BASE0012服务获取当前环节
			UocMessage fmtMessage = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(infoPayOrderVo.getProc_inst_id(), procInstTaskInstVo.getOrder_no());
			if(!"0000".equals(fmtMessage.getRespCode())){
				return fmtMessage;
			}
			Map<String,Object> fmtMap = fmtMessage.getArgs();
			taskId = getIdServDu.getId("createLogId", infoPayOrderVo.getProvince_code(), "*", "");
			procModTacheVo.setTache_code(fmtMap.get("current_tache").toString());
			procModTacheVo = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
			if(procModTacheVo!=null && procModTacheVo.getUsed_time_len() !=null &&!"".equals(procModTacheVo.getUsed_time_len())){
				limit_time = getLimitTime(procModTacheVo.getUsed_time_len());
			}
			//拼装要插入的数据
			procInstTaskInstVoInsert = setProcInstTaskInstVoData(oper_no, depart_no, depart_name, oper_code, procInstTaskInstVo.getOrder_no(), procInstTaskInstVo.getOrder_type(),
					fmtMap.get("current_task").toString(), fmtMap.get("current_task_name").toString(), fmtMap.get("current_tache")
							.toString(), infoPayOrderVo.getProc_inst_id(), infoPayOrderVo.getProvince_code(),
					infoPayOrderVo.getArea_code(), taskId, limit_time, global_limit_time, prod_code,task_property,procInstTaskInstVo.getTask_state());

			UocMessage taskMessage = artificialTaskAssignServDu.taskDefaultAssignment(procInstTaskInstVoInsert);
			if (RespCodeContents.SUCCESS.equals(taskMessage.getRespCode())) {
				// 插入数据到人工任务实例表
				result = procInstTaskInstServDu.create(procInstTaskInstVoInsert);
				if (!result) {
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("插入数据到人工任务实例表出错");
					return uocMessage;
				}
			} else {
				return taskMessage;
			}
		}
		//交付订单
		else if("103".equals(procInstTaskInstVo.getOrder_type())){
			InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
			infoDeliverOrderVo.setDeliver_order_no(procInstTaskInstVo.getOrder_no());
			//根据订单号查询交付订单表
			infoDeliverOrderVo = infoDeliverOrderServDu.getInfoDeliverOrderByPayDeliverOrderNo(infoDeliverOrderVo);
			if(infoDeliverOrderVo != null){
				InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
				infoServiceOrderVo.setSale_order_no(infoDeliverOrderVo.getSale_order_no());
				//根据订单号查询服务订单表
				List<InfoServiceOrderVo> listServiceOrder = infoServiceOrderServDu.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderVo);
				oper_no = infoDeliverOrderVo.getAccept_oper_no();
				depart_no = infoDeliverOrderVo.getAccept_depart_no();
				depart_name =infoDeliverOrderVo.getAccept_depart_name();
			    if(listServiceOrder != null){
					oper_code = listServiceOrder.get(0).getOper_code();
					if(!"".equals(oper_code)){
						ProcModAppVo procMapAppVo = new ProcModAppVo();
						procMapAppVo.setOper_code(oper_no);
						procMapAppVo = procModAppServDu.queryProcModAppByOperCode(procMapAppVo);
						if(procMapAppVo!=null && procMapAppVo.getUsed_time_len()!=null&&!"".equals(procMapAppVo.getUsed_time_len())){
							global_limit_time = getLimitTime(procMapAppVo.getUsed_time_len());
						}
					}
			    }
			}

			//BASE0012服务获取当前环节
			UocMessage fmtMessage = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(infoDeliverOrderVo.getProc_inst_id(), procInstTaskInstVo.getOrder_no());
			if(!"0000".equals(fmtMessage.getRespCode())){
				return fmtMessage;
			}
			Map<String,Object> fmtMap = fmtMessage.getArgs();
			taskId = getIdServDu.getId("createLogId", infoDeliverOrderVo.getProvince_code(), "*", "");
			procModTacheVo.setTache_code(fmtMap.get("current_tache").toString());
			procModTacheVo = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
			if(procModTacheVo !=null && procModTacheVo.getUsed_time_len() !=null && !"".equals(procModTacheVo.getUsed_time_len())){
				limit_time = getLimitTime(procModTacheVo.getUsed_time_len());
			}
			//拼装要插入的数据
			procInstTaskInstVoInsert = setProcInstTaskInstVoData(oper_no, depart_no, depart_name, oper_code, procInstTaskInstVo.getOrder_no(), procInstTaskInstVo.getOrder_type(),
					fmtMap.get("current_task").toString(), fmtMap.get("current_task_name").toString(), fmtMap.get("current_tache")
							.toString(), infoDeliverOrderVo.getProc_inst_id(), infoDeliverOrderVo.getProvince_code(),
					infoDeliverOrderVo.getArea_code(), taskId, limit_time, global_limit_time, prod_code,task_property,procInstTaskInstVo.getTask_state());

			UocMessage taskMessage = artificialTaskAssignServDu.taskDefaultAssignment(procInstTaskInstVoInsert);
			if (RespCodeContents.SUCCESS.equals(taskMessage.getRespCode())) {
				// 插入数据到人工任务实例表
				result = procInstTaskInstServDu.create(procInstTaskInstVoInsert);
				if (!result) {
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("插入数据到人工任务实例表出错");
					return uocMessage;
				}
			} else {
				return taskMessage;
			}
		}
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("传入的操作类型错误");
			return uocMessage;
		}

		if (!StringUtils.isEmpty(procInstTaskInstVoInsert.getAccept_oper_no())) {
			uocMessage.addArg("pushMsgOperNo", procInstTaskInstVoInsert.getAccept_oper_no());
		}
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("人工任务创建成功");
		return uocMessage;
	}

	/**
	 * 获取当前日期时间
	 * */
	public String getCurrentDateTime() throws Exception{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 获取有效期
	 * */
	public String getLimitTime(String used_time_len) throws Exception{
		Date currentTime = new Date();
		long  currentTimeLong = currentTime.getTime() / 1000;
		long currentTimeSum = Long.valueOf(used_time_len) + currentTimeLong;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date globalLimitTime = new Date(currentTimeSum * 1000);
		String dateLimit = formatter.format(globalLimitTime);
		return dateLimit;
	}

	/**
	 * 设置要插入到人工实例表的数据
	 * */
	public ProcInstTaskInstVo setProcInstTaskInstVoData(String operNo,String departNo,String departName,String operCode,String orderNo,String operType,String taskCode,String taskName,String tacheCode,String procInstId,
			String provinceCode,String areaCode,String taskId,String limitTime,String globalLimitTime,String prodCode,String taskProperty,String taskState) throws Exception{

		ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
		Map<String,String> stringMap =StrUtil.splitStringFromOrderNo(orderNo);
		String partMonth =stringMap.get("part_month");
		if(taskProperty != null && !taskProperty.equals("")){
			procInstTaskInstVo.setTask_property(taskProperty);
		}
		procInstTaskInstVo.setPart_month(partMonth);
		procInstTaskInstVo.setTask_id(taskId);
		procInstTaskInstVo.setTask_code(taskCode);
		procInstTaskInstVo.setTask_name(taskName);
		procInstTaskInstVo.setTache_code(tacheCode);
		procInstTaskInstVo.setProvince_code(provinceCode);
		procInstTaskInstVo.setArea_code(areaCode);
		procInstTaskInstVo.setAccept_time(getCurrentDateTime());
		procInstTaskInstVo.setOrder_no(orderNo);
		procInstTaskInstVo.setOrder_type(operType);
		procInstTaskInstVo.setProc_inst_id(procInstId);
		if(null != taskState && !"".equals(taskState)){
			procInstTaskInstVo.setTask_state(taskState);
		}else{
			procInstTaskInstVo.setTask_state("100");
		}
		procInstTaskInstVo.setOper_code(operCode);
		procInstTaskInstVo.setLimit_time(limitTime);
		if(null != globalLimitTime && !"".equals(globalLimitTime)){
			procInstTaskInstVo.setGlobal_limit_time(globalLimitTime);
		}
		procInstTaskInstVo.setAccept_oper_no(operNo);
		procInstTaskInstVo.setAccept_depart_no(departNo);
		procInstTaskInstVo.setAccept_depart_name(departName);
		procInstTaskInstVo.setCreate_time(getCurrentDateTime());
		procInstTaskInstVo.setProd_code(prodCode);

		return procInstTaskInstVo;
	}
}
