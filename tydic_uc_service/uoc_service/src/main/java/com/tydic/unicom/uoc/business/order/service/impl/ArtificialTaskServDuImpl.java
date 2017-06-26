package com.tydic.unicom.uoc.business.order.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.CodeList;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTacheLogin;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgAppPo;
import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgDefinePo;
import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgDesignPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgAppServ;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgDefineServ;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgDesignServ;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskInstHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskInstHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.ArtificialTaskPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPersionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignDepartServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignOperServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.common.vo.ProcModTacheVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ArtificialTaskServDu;
import com.tydic.unicom.uoc.business.order.service.vo.ArtificialTaskVo;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgAppVo;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgDesignVo;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcInstTaskInstListVo;
import com.tydic.unicom.uoc.business.order.service.vo.TaskDetailVo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ChangeToArtificialServiceServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCallServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.common.impl.StrUtil;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.log.interfaces.ArtificialTaskRecordServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheBtnServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheBtnVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderPersionServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderPersionServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderProdMapServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderSimCardServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPersionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderProdMapVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderSimCardVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.uoc.service.task.interfaces.ArtificialTaskAssignServDu;
import com.tydic.unicom.uoc.service.task.interfaces.CheckArtificialTaskServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class ArtificialTaskServDuImpl  implements ArtificialTaskServDu{

	Logger logger = Logger.getLogger(ArtificialTaskServDuImpl.class);

	@Autowired
	private OperServDu operServDu;

	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;

	@Autowired
	private GetIdServDu getIdServDu;

	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;

	@Autowired
	private ProcessCallServDu processCallServDu;

	@Autowired
	private RedisServiceServ redisServiceServ;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private ProcInstTaskAssignDepartServ procInstTaskAssignDepartServ;

	@Autowired
	private ProcInstTaskAssignOperServ procInstTaskAssignOperServ;

	@Autowired
	private CheckArtificialTaskServDu checkArtificialTaskServDu;

	@Autowired
	private ArtificialTaskRecordServDu artificialTaskRecordServDu;

	@Autowired
	private ProcModTacheBtnServDu procModTacheBtnServDu;

	@Autowired
	private InfoServiceOrderPersionServDu infoServiceOrderPersionServDu;

	@Autowired
	private InfoServiceOrderProdMapServDu infoServiceOrderProdMapServDu;

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;

	@Autowired
	private InfoSaleOrderPersionServDu infoSaleOrderPersionServDu;

	@Autowired
	private OrdModFunctionServDu ordModFunctionServDu;

	@Autowired
	private InfoServiceOrderServ infoServiceOrderServ;

	@Autowired
	private InfoServiceOrderPersionServ infoServiceOrderPersionServ;

	@Autowired
	private InfoServiceOrderProdMapServ infoServiceOrderProdMapServ;

	@Autowired
	private CodeListServDu codeListServDu;

	@Autowired
	private InfoSaleOrderPersionServ infoSaleOrderPersionServ;

	@Autowired
	private ArtificialTaskAssignServDu artificialTaskAssignServDu;

	@Autowired
	private CodeTaskPkgAppServ codeTaskPkgAppServ;

	@Autowired
	private CodeTaskPkgDefineServ codeTaskPkgDefineServ;

	@Autowired
	private CodeTaskPkgDesignServ codeTaskPkgDesignServ;

	@Autowired
	private ChangeToArtificialServiceServDu changeToArtificialServiceServDu;

	@Autowired
	private ProcModTacheServDu procModTacheServDu;

	@Autowired
	private ProcessCirculationServDu processCirculationServDu;

	@Autowired
	private ProcInstTaskInstHisServ procInstTaskInstHisServ;

	@Autowired
	private InfoServiceOrderSimCardServDu infoServiceOrderSimCardServDu;

	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;

	private void getBean(){
		if(processCirculationServDu == null){
			processCirculationServDu = (ProcessCirculationServDu) ToolSpring.getBean("ProcessCirculationServDu");
		}
		if(operServDu == null){
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if(checkArtificialTaskServDu == null){
			checkArtificialTaskServDu = (CheckArtificialTaskServDu) ToolSpring.getBean("CheckArtificialTaskServDu");
		}
		if(procInstTaskInstServ == null){
			procInstTaskInstServ = (ProcInstTaskInstServ) ToolSpring.getBean("ProcInstTaskInstServ");
		}
		if(procInstTaskDealRecordServ == null){
			procInstTaskDealRecordServ = (ProcInstTaskDealRecordServ) ToolSpring.getBean("ProcInstTaskDealRecordServ");
		}
		if(getIdServDu == null){
			getIdServDu = (GetIdServDu) ToolSpring.getBean("GetIdServDu");
		}
		if(codeListServDu == null){
			codeListServDu = (CodeListServDu) ToolSpring.getBean("CodeListServDu");
		}
		if (abilityPlatformServDu == null) {
			abilityPlatformServDu = (AbilityPlatformServDu) ToolSpring.getBean("AbilityPlatformServDu");
		}
	}

	private String getS(Object obj){
		synchronized (obj) {
			if(obj == null)
				return "";
			return obj.toString();
		}

	}

	/**
	 * 人工任务处理UOC0015入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */

	public UocMessage createHandingArtificialTaskForAbilityPlatform(String  json_in) throws Exception{
		logger.info("^^^^^^^^^^^^^UOC00015==json_in========"+json_in);
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
//		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ArtificialTaskVo vo =new ArtificialTaskVo();
		vo =(ArtificialTaskVo) jsonToBeanServDu.jsonInfoToBean(json_in, ArtificialTaskVo.class);
//		String jsession_id = getS(map.get("jsession_id")).toString();
//		String order_no = getS(map.get("order_no")).toString();
//		String oper_type = getS(map.get("oper_type")).toString();
//		String deal_code = getS(map.get("deal_code")).toString();
//		String deal_desc = getS(map.get("deal_desc")).toString();
//		String deal_system_no = getS(map.get("deal_system_no")).toString();
//		String call_type = getS(map.get("call_type")).toString();
//		String tache_code= getS(map.get("tache_code")).toString();
//		String json_info_ext= getS(map.get("json_info_ext")).toString();
//		vo.setJsession_id(jsession_id);
//		vo.setOrder_no(order_no);
//		vo.setOper_type(oper_type);
//		vo.setDeal_code(deal_code);
//		vo.setDeal_desc(deal_desc);
//		vo.setDeal_system_no(deal_system_no);
//		vo.setCall_type(call_type);
//		vo.setTache_code(tache_code);
//		vo.setJson_info_ext(json_info_ext);
		UocMessage message =createHandingArtificialTask(vo);
		return message;
	}

	/**
	 * 通过能力平台传入 <br>
	 * 传入：参见 重庆联通中台-订单中心接口规范 6.9
	 * 接受规范：订单中心服务 UOC0015
	 *
	 * 创建一个 人工任务，外部系统通过能力平台访问
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage createArtificialTaskForExternalSystem(String  json_in) throws Exception{
		logger.info("^^^^^^^^^^^^^UOC00015==json_in2========"+json_in);
		getBean();
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		logger.info("====================json_info===--==="+json_in);
//		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ArtificialTaskVo vo =new ArtificialTaskVo();
		vo =(ArtificialTaskVo) jsonToBeanServDu.jsonInfoToBean(json_in, ArtificialTaskVo.class);

//		String jsession_id = getS(map.get("jsession_id")).toString();
//		String order_no = getS(map.get("order_no")).toString();
//		String oper_type = getS(map.get("order_type")).toString();
//		String deal_code = getS(map.get("deal_code")).toString();
//		String call_type = getS(map.get("call_type")).toString();
//		String tache_code= getS(map.get("tache_code")).toString();
//		String flow_skip= getS(map.get("flow_skip")).toString();
//		String flow_skip = getS(map.get("deal_desc")).toString();
//		String deal_system_no = getS(map.get("deal_system_no")).toString();
//		String json_info_ext= getS(map.get("json_info_ext")).toString();

//		vo.setJsession_id(jsession_id);
//		vo.setOrder_no(order_no);
//		vo.setOper_type(oper_type);
//		vo.setDeal_code(deal_code);
//		vo.setCall_type(call_type);
//		vo.setTache_code(tache_code);
//		vo.setFlow_skip(flow_skip);
//		vo.setJson_info_ext(json_info_ext);
		UocMessage message =createHandingArtificialTask(vo);
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage createHandingArtificialTask(ArtificialTaskVo vo) throws Exception{
		UocMessage message=new UocMessage();

		if(vo==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("参数为空");
			return message;
		}
		logger.info("^^^^^^^^^^^^^UOC00015=========="+vo.toString());
		if(vo.getJsession_id()==null ||  "".equals(vo.getJsession_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		if(vo.getOrder_no()==null ||  "".equals(vo.getOrder_no())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_no不能为空");
			return message;
		}
		if(vo.getOper_type()==null ||  "".equals(vo.getOper_type())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("oper_type不能为空");
			return message;
		}
		if (vo.getTache_code() == null || "".equals(vo.getTache_code())) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("tache_code不能为空");
			return message;
		}

		//TODO 调用BASE0008记录接口日志
		//效验登录信息
		if(operServDu == null){
			logger.info("====================operServDu is null============================"+operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		UocMessage loginMessage=operServDu.isLogin(vo.getJsession_id());
		if(!"0000".equals(loginMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("效验登录信息失败");
			return message;
		}

		Map<String,Object> oper_info=(Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String oper_no=(String) oper_info.get("oper_no");

		//查询未处理的人工任务
		ProcInstTaskInstPo po = new ProcInstTaskInstPo();
		po.setOrder_no(vo.getOrder_no());
		po.setOrder_type(vo.getOper_type());
		po.setTache_code(vo.getTache_code());
		if(procInstTaskInstServ == null){
			logger.info("====================procInstTaskInstServ is null============================"+procInstTaskInstServ);
			procInstTaskInstServ = (ProcInstTaskInstServ) ToolSpring.getBean("ProcInstTaskInstServ");
		}

		//新增校验逻辑，调用BASE0025进行任务校验
		UocMessage checkMessage = checkArtificialTaskServDu.checkArtificialTaskProcess(vo.getOrder_no(), vo.getTache_code(), oper_info);
		if(!checkMessage.getRespCode().equals(RespCodeContents.SUCCESS)){
			return checkMessage;
		}

		List<ProcInstTaskInstPo> list = procInstTaskInstServ.querytaskInstByOrderNoAndTacheCode(vo.getOrder_no(), vo.getTache_code());
		if (list == null || list.size() <= 0) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("人工任务实例为空");
			return message;
		}
		BeanUtils.copyProperties(list.get(0), po);
		ProcInstTaskInstVo taskInstVo = new ProcInstTaskInstVo();
		BeanUtils.copyProperties(list.get(0), taskInstVo);
		String date = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		po.setTask_state("102");
		po.setFinish_time(date);

		boolean flagTem = procInstTaskInstServ.updateByOrderNo(po);
		if (!flagTem) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("更新人工任务实例表失败");
			throw new UocException(message);
		}

		// 5、根据传入的flow_skip判断，如果flow_skip等于0或空，则继续后面的逻辑，
		// 如果flow_skip等于1，则直接调用BASE0016服务，并跳过后面的逻辑，这里入参action_code用deal_code字段，
		// 并且deal_code不为空时，flow_type填1，否则填0，拼json_info_ext入参的逻辑如下{“jsession_id”:”传入jsession_id”｝
		if ("1".equals(vo.getFlow_skip())) {
			Map<String, String> actionCode = new HashMap<String, String>();
			actionCode.put("condParam1", vo.getDeal_code());
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			if (StringUtils.isNotEmpty(vo.getJson_info_ext())) {
				jsonMap = jsonToBeanServDu.jsonToMap(vo.getJson_info_ext());
			}
			jsonMap.put("jsession_id", vo.getJsession_id());
			String jsonInfoExt = jsonToBeanServDu.mapToJson(jsonMap);
			UocMessage proMessage = processCirculationServDu.processCirculation(vo.getOrder_no(), vo.getOper_type(),
					StringUtils.isNotEmpty(vo.getDeal_code()) ? "1" : "0", actionCode, jsonInfoExt);

			if (!"0000".equals(proMessage.getRespCode())) {
				throw new UocException(proMessage);
			}
			// 消息推送工号
			Map<String, Object> resMap = proMessage.getArgs();
			if (resMap != null && resMap.get("pushMsgOperNo") != null) {
				message.addArg("pushMsgOperNo", resMap.get("pushMsgOperNo").toString());
			}
		}else{
			ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
			if (getIdServDu == null) {
				logger.info("====================getIdServDu is null============================" + getIdServDu);
				getIdServDu = (GetIdServDu) ToolSpring.getBean("GetIdServDu");
			}
			String id = getIdServDu.getId("createLogId", list.get(0).getProvince_code(), "*", "");
			procInstTaskDealRecordPo.setId(id);
			procInstTaskDealRecordPo.setTask_id(list.get(0).getTask_id());
			procInstTaskDealRecordPo.setProvince_code(list.get(0).getProvince_code());
			procInstTaskDealRecordPo.setArea_code(list.get(0).getArea_code());
			procInstTaskDealRecordPo.setPart_month(list.get(0).getPart_month());
			procInstTaskDealRecordPo.setDeal_time(date);
			procInstTaskDealRecordPo.setDeal_oper_no(oper_no);
			procInstTaskDealRecordPo.setDeal_system_no(StringUtils.isEmpty(vo.getDeal_system_no()) ? "" : vo.getDeal_system_no());
			procInstTaskDealRecordPo.setDeal_code(StringUtils.isEmpty(vo.getDeal_code()) ? "" : vo.getDeal_code());
			procInstTaskDealRecordPo.setDeal_desc(StringUtils.isEmpty(vo.getDeal_desc()) ? "" : vo.getDeal_desc());
			procInstTaskDealRecordPo.setOrder_no(vo.getOrder_no());
			procInstTaskDealRecordPo.setOrder_type(vo.getOrder_type());
			procInstTaskDealRecordPo.setTache_code(list.get(0).getTache_code());
			procInstTaskDealRecordPo.setOper_code(list.get(0).getOper_code());
			procInstTaskDealRecordPo.setCreate_time(list.get(0).getCreate_time());
			procInstTaskDealRecordPo.setProd_code(list.get(0).getProd_code());
			if (procInstTaskDealRecordServ == null) {
				logger.info("====================procInstTaskDealRecordServ is null============================"
						+ procInstTaskDealRecordServ);
				procInstTaskDealRecordServ = (ProcInstTaskDealRecordServ) ToolSpring.getBean("ProcInstTaskDealRecordServ");
			}
			boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
			if (!flag) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("创建人工任务操作记录失败");
				throw new UocException(message);
			}

			if (jsonToBeanServDu == null) {
				logger.info("====================jsonToBeanServDu is null============================" + jsonToBeanServDu);
				jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
			}
			String jsonInfoExt = "";
			if (vo.getJson_info_ext() != null && !"".equals(vo.getJson_info_ext())) {
				Map<String, Object> jsonMap = jsonToBeanServDu.jsonToMap(vo.getJson_info_ext());
				jsonMap.put("jsession_id", vo.getJsession_id());
				if(!(jsonMap.containsKey("manual_flag"))){
					jsonMap.put("manual_flag", "0");
				}
				if (vo.getDeal_code() != null && !"".equals(vo.getDeal_code())) {
					jsonMap.put("action_code", vo.getDeal_code());
				}
				jsonInfoExt = jsonToBeanServDu.mapToJson(jsonMap);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("jsession_id", vo.getJsession_id());
				if(!(map.containsKey("manual_flag"))){
					map.put("manual_flag", "0");
				}
				if (vo.getDeal_code() != null && !"".equals(vo.getDeal_code())) {
					map.put("action_code", vo.getDeal_code());
				}
				jsonInfoExt = jsonToBeanServDu.mapToJson(map);
			}

			// 环节调用
			if (processCallServDu == null) {
				logger.info("====================processCallServDu is null============================" + processCallServDu);
				processCallServDu = (ProcessCallServDu) ToolSpring.getBean("ProcessCallServDu");
			}
			UocMessage proMessage = processCallServDu.processCall(vo.getOrder_no(), vo.getOper_type(), vo.getCall_type(), jsonInfoExt,
					taskInstVo);
			if (!"0000".equals(proMessage.getRespCode().toString())) {
				ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
				procInstTaskInstVo.setOrder_no(vo.getOrder_no());
				procInstTaskInstVo.setOrder_type(vo.getOper_type());
				changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "");
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent(proMessage.getContent().toString());
				throw new UocException(message);
			} else {
				if (proMessage.getArgs() != null && proMessage.getArgs().get("cb_order_no") != null) {
					message.addArg("cb_order_no", proMessage.getArgs().get("cb_order_no").toString());
				}
			}

		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("人工任务处理成功");
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getArtificialTaskList(ArtificialTaskVo vo) throws Exception{

		Map<String,Object> listMap= new HashMap<String,Object>();
		UocMessage message=new UocMessage();

		if(vo==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("参数为空");
			return message;
		}
		if(vo.getJsession_id()==null ||  "".equals(vo.getJsession_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		if(vo.getAccept_time_start()==null ||  "".equals(vo.getAccept_time_start())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("accept_time_start不能为空");
			return message;
		}
		if(vo.getAccept_time_end()==null ||  "".equals(vo.getAccept_time_end())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("accept_time_end不能为空");
			return message;
		}
		if(vo.getPage_no()==null ||  "".equals(vo.getPage_no())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("page_no不能为空");
			return message;
		}
		if(vo.getPage_size()==null ||  "".equals(vo.getPage_size())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("page_size不能为空");
			return message;
		}


		int pageNo=Integer.parseInt(vo.getPage_no());
		int pageSize=Integer.parseInt(vo.getPage_size());
		int totalNumServ=0;
		int totalNumSale=0;
		int totalNumServ1=0;
		int totalNumSale1=0;
		int totalNum=0;

		String sysDate3="";
		String sysDate4="";
		String partMonth1="";

		ArtificialTaskPo artificialTaskPo = new ArtificialTaskPo();
		ArtificialTaskPo artificialTaskPo1 = new ArtificialTaskPo();
		BeanUtils.copyProperties(vo,artificialTaskPo);
		//如果排序字段为空，默认反向排序  01：正向     02：反向
		if(vo.getSort_type()==null ||  "".equals(vo.getSort_type()) || "02".equals(vo.getSort_type())){
			artificialTaskPo.setSort_type_desc("02");
		}else if("01".equals(vo.getSort_type())){
			artificialTaskPo.setSort_type_asc("01");
		}

		//效验登录信息
		UocMessage loginMessage = operServDu.isLogin(vo.getJsession_id());
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取工号信息错误");
			return message;
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf2.parse(vo.getAccept_time_start());
		Date date2 = sdf2.parse(vo.getAccept_time_end());
		Date date5 = sdf3.parse(vo.getAccept_time_end());
		String acceptTimeStartTemp=sdf1.format(date1).toString().trim();
		String acceptTimeEndTemp=sdf1.format(date2).toString().trim();
		String acceptTimeStart=acceptTimeStartTemp.substring(0, 6);
		String acceptTmeEnd=acceptTimeEndTemp.substring(0, 6);


		//获取结束时间的上一个月时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date2);//把当前时间赋给日历
		calendar.add(calendar.MONTH, -1);  //设置为前1月
		dBefore = calendar.getTime();   //得到前1月的时间
		String defaultStartDate = sdf1.format(dBefore);    //格式化前1月的时间
		String defaultStartDateTemp=defaultStartDate.substring(0, 6);
		if(!acceptTmeEnd.equals(acceptTimeStart) && !defaultStartDateTemp.equals(acceptTimeStart)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("查询人工任务时间段不能跨两个月");
			return message;
		}

		//如果查询时间是跨月的
		if(defaultStartDateTemp.equals(acceptTimeStart)){
			//获取上一个月的最后一天时间
			Date date3 = new Date();
			Calendar calendar3 = Calendar.getInstance(); //得到日历
			calendar3.setTime(date5);//把当前时间赋给日历
			calendar3.add(calendar3.MONTH, -1);  //设置为前1月
			int today=calendar3.get(calendar3.DAY_OF_MONTH);
			if(today==1){
			  calendar3.add(calendar3.DAY_OF_MONTH, 1);
			}
			calendar3.add(calendar3.SECOND, -1);  //设置为前1月
			calendar3.set(calendar3.DAY_OF_MONTH,calendar3.getActualMaximum(Calendar.DAY_OF_MONTH));
			date3 = calendar3.getTime();   //得到前1月的时间
			sysDate3 = sdf2.format(date3);    //格式化前1月的时间

			//获取这个月的第一天时间
			Date date4 = new Date();
			Calendar calendar4 = Calendar.getInstance(); //得到日历
			calendar4.setTime(date5);//把当前时间赋给日历
			calendar4.add(calendar4.MONTH, 0);  //设置为前1月
			calendar4.set(calendar4.DAY_OF_MONTH,1);
			date4 = calendar4.getTime();   //得到前1月的时间
			sysDate4 = sdf2.format(date4);    //格式化前1月的时间

			//获取上个月的part_month
			String month1=defaultStartDateTemp.substring(4, 6);
			partMonth1=Integer.toString(Integer.parseInt(month1));
		}


		Date dNow = new Date();   //当前时间
		String sysdate = sdf2.format(dNow);    //格式化前3月的时间
		logger.info("====================sysdate============================"+sysdate);
		artificialTaskPo.setNow_time(sysdate);
		List<ProcInstTaskInstPo> servlist = new ArrayList<ProcInstTaskInstPo>();
		List<ProcInstTaskInstPo> list = new ArrayList<ProcInstTaskInstPo>();
		List<ProcInstTaskInstPo> salelist = new ArrayList<ProcInstTaskInstPo>();
		List<ProcInstTaskInstPo> servlist1 = new ArrayList<ProcInstTaskInstPo>();
		List<ProcInstTaskInstPo> salelist1 = new ArrayList<ProcInstTaskInstPo>();
		List<ProcInstTaskInstListVo> taskList = new ArrayList<ProcInstTaskInstListVo>();
		String month=acceptTmeEnd.substring(4, 6);
		String partMonth=Integer.toString(Integer.parseInt(month));

		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String roleIdList=(String) oper_info.get("role_id");
		List<String> role_id_list =StrUtil.strToList(roleIdList);
		String area_code="";

		if(vo.getArea_code()==null ||  "".equals(vo.getArea_code())){
			area_code = (String) oper_info.get("area_code");
		}else{
			area_code=vo.getArea_code();
		}

		//从缓存获取订单参数入库定义表配置
		UocMessage paraMessage = redisServiceServ.queryDataFromRedis("proc_tache_login");
		if(!"0000".equals(paraMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取环节关联工号表缓存失败");
			return message;
		}
		RedisData redisDataPara=(RedisData) paraMessage.getArgs().get("RedisDataResult");
		Map<String,Object> map=redisDataPara.getMap();
		List<ProcModTacheLogin> procModTacheLoginPoList = (List<ProcModTacheLogin>) map.get("proc_tache_login");
		StringBuffer sb1= new StringBuffer();
		if(role_id_list!=null && procModTacheLoginPoList!=null){
			for(ProcModTacheLogin po:procModTacheLoginPoList){
				for(String roleId:role_id_list){
					if(roleId.equals(po.getRole_id())){
						sb1.append("'");
						sb1.append(po.getTache_code());
						sb1.append("'");
						sb1.append(",");
					}
				}
			}
		}

		// 增加按产品组过滤 通过code_list表type_code=product_element and parent_code_id =
		// prod_group找到对应的产品编码
		if (StringUtils.isNotEmpty(vo.getProd_group())) {
			CodeList codeList = new CodeList();
			codeList.setType_code("product_element");
			codeList.setParent_code_id(vo.getProd_group());
			List<CodeList> codeLists = codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);
			String prodCodeStr = "";
			if (codeLists != null && codeLists.size() > 0) {
				for (CodeList prodCode : codeLists) {
					prodCodeStr = prodCodeStr + "'" + prodCode.getCode_id() + "'" + ",";
				}
				artificialTaskPo.setProd_code(prodCodeStr.substring(0, prodCodeStr.length() - 1));
			} else {
				// 未找到该产品组下的产品
				logger.info("---------------未找到该产品组下的产品----------------");
				listMap.put("page_no", pageNo);
				listMap.put("page_size", pageSize);
				listMap.put("total_num", 0);
				listMap.put("task_list", taskList);
				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("人工任务列表获取成功");
				message.addArg("task_list", listMap);
				return message;
			}
		}

		String ls1=sb1.toString();
		//String ls1="'S00011','S00005','B00001','S00002','B00002',";
		if(ls1.length()!=0){
			artificialTaskPo.setTache_code(ls1.substring(0,ls1.length()-1));
			if(vo.getTache_code()!=null && !"".equals(vo.getTache_code())){
				artificialTaskPo.setTache_code1(vo.getTache_code());
			}

			//artificialTaskPo.setPart_month(partMonth);
			artificialTaskPo.setArea_code(area_code);
			if(artificialTaskPo.getAcc_nbr()==null){
				artificialTaskPo.setAcc_nbr("");
			}
			if(artificialTaskPo.getCert_no()==null){
				artificialTaskPo.setCert_no("");
			}
			if(artificialTaskPo.getCust_name()==null){
				artificialTaskPo.setCust_name("");
			}
			if(artificialTaskPo.getAccept_oper_no()==null){
				artificialTaskPo.setAccept_oper_no("");
			}
			if(artificialTaskPo.getAccept_depart_no()==null){
				artificialTaskPo.setAccept_depart_no("");
			}
			if(artificialTaskPo.getAccept_no()==null){
				artificialTaskPo.setAccept_no("");
			}

			if(acceptTimeStart.equals(acceptTmeEnd)){
				artificialTaskPo.setPart_month(partMonth);
				totalNumServ=procInstTaskInstServ.queryProcInstTaskInstListCountByServOrderNo(artificialTaskPo);
				totalNumSale=procInstTaskInstServ.queryProcInstTaskInstListCountBySaleOrderNo(artificialTaskPo);

			}else if(defaultStartDateTemp.equals(acceptTimeStart)){//如果查询时间段跨月，查询上一个月的数据
				artificialTaskPo.setPart_month(partMonth);
				artificialTaskPo.setAccept_time_start(sysDate4);
				artificialTaskPo.setAccept_time_end(vo.getAccept_time_end());
				totalNumServ=procInstTaskInstServ.queryProcInstTaskInstListCountByServOrderNo(artificialTaskPo);
				totalNumSale=procInstTaskInstServ.queryProcInstTaskInstListCountBySaleOrderNo(artificialTaskPo);

				BeanUtils.copyProperties(artificialTaskPo,artificialTaskPo1);
				artificialTaskPo1.setPart_month(partMonth1);
				artificialTaskPo1.setAccept_time_start(vo.getAccept_time_start());
				artificialTaskPo1.setAccept_time_end(sysDate3);
				totalNumServ1=procInstTaskInstServ.queryProcInstTaskInstListCountByServOrderNo(artificialTaskPo1);
				totalNumSale1=procInstTaskInstServ.queryProcInstTaskInstListCountBySaleOrderNo(artificialTaskPo1);
			}
			totalNum =totalNumServ + totalNumSale+totalNumServ1+totalNumSale1;
			if(totalNumServ!=0 || totalNumSale!=0 || totalNumServ1!=0 || totalNumSale1!=0){
				if(artificialTaskPo.getSort_type_desc()!=null && !"".equals(artificialTaskPo.getSort_type_desc()) && "02".equals(artificialTaskPo.getSort_type_desc()) && artificialTaskPo.getSort_type_asc()==null){
					if(totalNumServ>=pageNo*pageSize){
						int n=(pageNo-1)*pageSize;
						servlist = procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,n,pageSize);
						if(servlist!=null){
							list.addAll(servlist);
						}

					}else if(totalNumServ<pageNo*pageSize){
						if(totalNumServ>(pageNo-1)*pageSize){
							if((totalNumServ+totalNumSale)>=pageNo*pageSize){
								int n=(pageNo-1)*pageSize;
								servlist = procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,n,pageSize);
								int y=0;
								if(servlist!=null){
									list.addAll(servlist);
									y=pageSize-servlist.size();
								}else{
									y=pageSize;
								}
								int x=0;
								salelist = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,x,y);

								if(salelist!=null){
									list.addAll(salelist);
								}
							}else if((totalNumServ+totalNumSale)<pageNo*pageSize){
								if((totalNumServ+totalNumSale+totalNumServ1)>=pageNo*pageSize){
									int n=(pageNo-1)*pageSize;
									servlist = procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,n,pageSize);
									int y=0;
									if(servlist!=null){
										list.addAll(servlist);
										y=pageSize-servlist.size();
									}else{
										y=pageSize;
									}
									int x=0;
									salelist = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,x,y);
									int b=0;
									if(salelist!=null){
										list.addAll(salelist);
										b=y-salelist.size();
									}else{
										b=y;
									}
									if(totalNumServ1!=0){
										int a=0;
										servlist1=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,a,b);
										if(servlist1!=null){
											list.addAll(servlist1);
										}
									}
								}else if((totalNumServ+totalNumSale+totalNumServ1)<pageNo*pageSize){
									int n=(pageNo-1)*pageSize;
									servlist = procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,n,pageSize);
									int y=0;
									if(servlist!=null){
										list.addAll(servlist);
										y=pageSize-servlist.size();
									}else{
										y=pageSize;
									}
									int x=0;
									salelist = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,x,y);
									int b=0;
									if(salelist!=null){
										list.addAll(salelist);
										b=y-salelist.size();
									}else{
										b=y;
									}
									int d=0;
									if(totalNumServ1!=0){
										int a=0;
										servlist1=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,a,b);

										if(servlist1!=null){
											list.addAll(servlist1);
											d=b-servlist1.size();
										}else{
											d=b;
										}
									}else{
										d=b;
									}
									if(totalNumSale1!=0){
										int c=0;
										salelist1= procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,c,d);
										if(salelist1!=null){
											list.addAll(salelist1);
										}
									}
								}
							}
						}else if(totalNumServ<=(pageNo-1)*pageSize){
							if((totalNumServ+totalNumSale)>(pageNo-1)*pageSize){
								if((totalNumServ+totalNumSale)>=pageNo*pageSize){
									int n=(pageNo-1)*pageSize-totalNumServ;
									salelist = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,n,pageSize);
									if(salelist!=null){
										list.addAll(salelist);
									}
								}else if((totalNumServ+totalNumSale)<pageNo*pageSize){
									if((totalNumServ+totalNumSale+totalNumServ1)>=pageNo*pageSize){
										int n=(pageNo-1)*pageSize-totalNumServ;
										salelist = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,n,pageSize);
										int b=0;
										if(salelist!=null){
											list.addAll(salelist);
											b=pageSize-salelist.size();
										}else{
											b=pageSize;
										}
										if(totalNumServ1!=0){
											int a=0;
											servlist1=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,a,b);
											if(servlist1!=null){
												list.addAll(servlist1);
											}
										}
									}else if((totalNumServ+totalNumSale+totalNumServ1)<pageNo*pageSize){
										int n=(pageNo-1)*pageSize-totalNumServ;
										salelist = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,n,pageSize);
										int b=0;
										if(salelist!=null){
											list.addAll(salelist);
											b=pageSize-salelist.size();
										}else{
											b=pageSize;
										}
										int y=0;
										if(totalNumServ1!=0){
											int a=0;
											servlist1=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,a,b);

											if(servlist1!=null){
												list.addAll(servlist1);
												y=b-servlist1.size();
											}else{
												y=b;
											}
										}else{
											y=b;
										}
										if(totalNumSale1!=0){
											int x=0;
											salelist1= procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,x,y);
											if(salelist1!=null){
												list.addAll(salelist1);
											}
										}
									}
								}
							}else if((totalNumServ+totalNumSale)<=(pageNo-1)*pageSize){
								if((totalNumServ+totalNumSale+totalNumServ1)>(pageNo-1)*pageSize){
									if((totalNumServ+totalNumSale+totalNumServ1)>=pageNo*pageSize){
										if(totalNumServ1!=0){
											int n=(pageNo-1)*pageSize-(totalNumServ+totalNumSale);
											servlist1=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,n,pageSize);
											if(servlist1!=null){
												list.addAll(servlist1);
											}
										}
									}else if((totalNumServ+totalNumSale+totalNumServ1)<pageNo*pageSize){
										int y=0;
										if(totalNumServ1!=0){
											int n=(pageNo-1)*pageSize-(totalNumServ+totalNumSale);
											servlist1=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,n,pageSize);
											if(servlist1!=null){
												list.addAll(servlist1);
												y=pageSize-servlist1.size();
											}else{
												y=pageSize;
											}
										}else{
											y=pageSize;
										}
										if(totalNumSale1!=0){
											int x=0;
											salelist1= procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,x,y);
											if(salelist1!=null){
												list.addAll(salelist1);
											}
										}
									}
								}else if((totalNumServ+totalNumSale+totalNumServ1)<=(pageNo-1)*pageSize){
									if(totalNumSale1!=0){
										int n=(pageNo-1)*pageSize-(totalNumServ+totalNumSale+totalNumServ1);
										salelist1= procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,n,pageSize);
										if(salelist1!=null){
											list.addAll(salelist1);
										}
									}

								}
							}

						}

					}
				}else if(artificialTaskPo.getSort_type_asc()!=null && !"".equals(artificialTaskPo.getSort_type_asc()) && "01".equals(artificialTaskPo.getSort_type_asc()) && artificialTaskPo.getSort_type_desc()==null){
					if(totalNumServ1>=pageNo*pageSize){
						int n=(pageNo-1)*pageSize;
						servlist1 = procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,n,pageSize);
						if(servlist1!=null){
							list.addAll(servlist1);
						}

					}else if(totalNumServ1<pageNo*pageSize){
						if(totalNumServ1>(pageNo-1)*pageSize){
							if((totalNumServ1+totalNumSale1)>=pageNo*pageSize){
								int n=(pageNo-1)*pageSize;
								servlist1 = procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,n,pageSize);
								int y=0;
								if(servlist1!=null){
									list.addAll(servlist1);
									y=pageSize-servlist1.size();
								}else{
									y=pageSize;
								}
								int x=0;
								salelist1 = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,x,y);
								if(salelist1!=null){
									list.addAll(salelist1);
								}
							}else if((totalNumServ1+totalNumSale1)<pageNo*pageSize){
								if((totalNumServ+totalNumSale1+totalNumServ1)>=pageNo*pageSize){
									int n=(pageNo-1)*pageSize;
									servlist1 = procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,n,pageSize);
									int y=0;
									if(servlist1!=null){
										list.addAll(servlist1);
										y=pageSize-servlist1.size();
									}else{
										y=pageSize;
									}
									int x=0;
									salelist1 = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,x,y);
									int b=0;
									if(salelist1!=null){
										list.addAll(salelist1);
										b=y-salelist1.size();
									}else{
										b=y;
									}
									int a=0;
									servlist=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,a,b);
									if(servlist!=null){
										list.addAll(servlist);
									}
								}else if((totalNumServ+totalNumSale1+totalNumServ1)<pageNo*pageSize){
									int n=(pageNo-1)*pageSize;
									servlist1 = procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo1,n,pageSize);
									int y=0;
									if(servlist1!=null){
										list.addAll(servlist1);
										y=pageSize-servlist1.size();
									}else{
										y=pageSize;
									}
									int x=0;
									salelist1 = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,x,y);
									int b=0;
									if(salelist1!=null){
										list.addAll(salelist1);
										b=y-salelist1.size();
									}else{
										b=y;
									}
									int a=0;
									servlist=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,a,b);
									int d=0;
									if(servlist!=null){
										list.addAll(servlist);
										d=b-servlist.size();
									}else{
										d=b;
									}
									int c=0;
									salelist= procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,c,d);
									if(salelist!=null){
										list.addAll(salelist);
									}
								}
							}
						}else if(totalNumServ1<=(pageNo-1)*pageSize){
							if((totalNumServ1+totalNumSale1)>(pageNo-1)*pageSize){
								if((totalNumServ1+totalNumSale1)>=pageNo*pageSize){
									int n=(pageNo-1)*pageSize-totalNumServ1;
									salelist1 = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,n,pageSize);
									if(salelist1!=null){
										list.addAll(salelist1);
									}
								}else if((totalNumServ1+totalNumSale1)<pageNo*pageSize){
									if((totalNumServ+totalNumSale1+totalNumServ1)>=pageNo*pageSize){
										int n=(pageNo-1)*pageSize-totalNumServ1;
										salelist1 = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,n,pageSize);
										int b=0;
										if(salelist1!=null){
											list.addAll(salelist1);
											b=pageSize-salelist1.size();
										}else{
											b=pageSize;
										}
										int a=0;
										servlist=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,a,b);
										if(servlist!=null){
											list.addAll(servlist);
										}
									}else if((totalNumServ+totalNumSale1+totalNumServ1)<pageNo*pageSize){
										int n=(pageNo-1)*pageSize-totalNumServ1;
										salelist1 = procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo1,n,pageSize);
										int b=0;
										if(salelist1!=null){
											list.addAll(salelist1);
											b=pageSize-salelist1.size();
										}else{
											b=pageSize;
										}
										int a=0;
										servlist=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,a,b);
										int y=0;
										if(servlist!=null){
											list.addAll(servlist);
											y=b-servlist.size();
										}else{
											y=b;
										}
										int x=0;
										salelist= procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,x,y);
										if(salelist!=null){
											list.addAll(salelist);
										}
									}
								}
							}else if((totalNumServ1+totalNumSale1)<=(pageNo-1)*pageSize){
								if((totalNumServ+totalNumSale1+totalNumServ1)>(pageNo-1)*pageSize){
									if((totalNumServ+totalNumSale1+totalNumServ1)>=pageNo*pageSize){
										int n=(pageNo-1)*pageSize-(totalNumServ1+totalNumSale1);
										servlist=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,n,pageSize);
										if(servlist!=null){
											list.addAll(servlist);
										}
									}else if((totalNumServ+totalNumSale1+totalNumServ1)<pageNo*pageSize){
										int n=(pageNo-1)*pageSize-(totalNumServ1+totalNumSale1);
										servlist=procInstTaskInstServ.queryProcInstTaskInstListByServOrderNo(artificialTaskPo,n,pageSize);
										int y=0;
										if(servlist!=null){
											list.addAll(servlist);
											y=pageSize-servlist.size();
										}else{
											y=pageSize;
										}
										int x=0;
										salelist= procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,x,y);
										if(salelist!=null){
											list.addAll(salelist);
										}
									}
								}else if((totalNumServ+totalNumSale1+totalNumServ1)<=(pageNo-1)*pageSize){
									int n=(pageNo-1)*pageSize-(totalNumServ1+totalNumSale1+totalNumServ);
									salelist= procInstTaskInstServ.queryProcInstTaskInstListBySaleOrderNo(artificialTaskPo,n,pageSize);
									if(salelist!=null){
										list.addAll(salelist);
									}

								}
							}

						}

					}
				}
				if(list!=null){
					for(ProcInstTaskInstPo po :list){
						ProcInstTaskInstListVo procInstTaskInstListVo = new ProcInstTaskInstListVo();
						procInstTaskInstListVo.setOrder_no(po.getOrder_no());
						procInstTaskInstListVo.setTask_name(po.getTask_name());
						if(po.getCreate_time()!=null && !"".equals(po.getCreate_time())){
							Date date3 = sdf2.parse(po.getCreate_time());
							String createTime=sdf2.format(date3).toString().trim();
							procInstTaskInstListVo.setCreate_time(createTime);
						}
						procInstTaskInstListVo.setFinish_time(po.getFinish_time());
						procInstTaskInstListVo.setLimit_time(po.getLimit_time());
						procInstTaskInstListVo.setGlobal_limit_time(po.getGlobal_limit_time());
						procInstTaskInstListVo.setTache_code(po.getTache_code());
						procInstTaskInstListVo.setOrder_type(po.getOrder_type());
						procInstTaskInstListVo.setProd_code(po.getProd_code());
						procInstTaskInstListVo.setAccept_oper_no(po.getAccept_oper_no());
						procInstTaskInstListVo.setAccpet_depart_no(po.getAccept_depart_no());

						if(po.getOrder_type().equals("101")){
							InfoServiceOrderPo infoServiceOrderPo = new InfoServiceOrderPo();
							infoServiceOrderPo.setServ_order_no(po.getOrder_no());
							InfoServiceOrderPo infoServiceOrderPoTemp=infoServiceOrderServ.getInfoServiceOrderByServOrderNo(infoServiceOrderPo);
							if(infoServiceOrderPoTemp!=null){
								procInstTaskInstListVo.setOper_code(infoServiceOrderPoTemp.getOper_code());
								procInstTaskInstListVo.setAcc_nbr(infoServiceOrderPoTemp.getAcc_nbr());
							}

							InfoServiceOrderPersionPo infoServiceOrderPersionPo = new InfoServiceOrderPersionPo();
							infoServiceOrderPersionPo.setServ_order_no(po.getOrder_no());
							List<InfoServiceOrderPersionPo> infoServiceOrderPersionPoTemp=infoServiceOrderPersionServ.queryInfoServiceOrderPersionByOrderNo(infoServiceOrderPersionPo);
							if(infoServiceOrderPersionPoTemp!=null){
								procInstTaskInstListVo.setCust_name(infoServiceOrderPersionPoTemp.get(0).getCust_name());
								procInstTaskInstListVo.setCert_no(infoServiceOrderPersionPoTemp.get(0).getCert_no());
							}

							/*InfoServiceOrderProdMapPo infoServiceOrderProdMapPo = new InfoServiceOrderProdMapPo();
							infoServiceOrderProdMapPo.setServ_order_no(po.getOrder_no());
							List<InfoServiceOrderProdMapPo> infoServiceOrderProdMapPoTemp=infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapPo);
							if(infoServiceOrderProdMapPoTemp!=null){
								procInstTaskInstListVo.setProd_code(infoServiceOrderProdMapPoTemp.get(0).getProd_code());
							}*/
						}else if(po.getOrder_type().equals("100")){
							InfoSaleOrderPersionPo infoSaleOrderPersionPo = new InfoSaleOrderPersionPo();
							infoSaleOrderPersionPo.setSale_order_no(po.getOrder_no());
							InfoSaleOrderPersionPo infoSaleOrderPersionPoTemp=infoSaleOrderPersionServ.getInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersionPo);
							if(infoSaleOrderPersionPoTemp!=null){
								procInstTaskInstListVo.setCust_name(infoSaleOrderPersionPoTemp.getCust_name());
								procInstTaskInstListVo.setCert_no(infoSaleOrderPersionPoTemp.getCert_no());
							}
						}
						procInstTaskInstListVo.setPerson_flag(artificialTaskPo.getPerson_flag());
						procInstTaskInstListVo.setLate_flag(artificialTaskPo.getLate_flag());
						taskList.add(procInstTaskInstListVo);
					}
				}
			}
		}
		listMap.put("page_no", pageNo);
		listMap.put("page_size", pageSize);
		listMap.put("total_num", totalNum);
		listMap.put("task_list", taskList);
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("人工任务列表获取成功");
		message.addArg("task_list", listMap);
		return message;

	}

	/**
	 * UOC0034 任务分配
	 * @param jsession_id	  登陆验证串
	 * @param order_no		  订单编号
	 * @param oper_type	  操作类型：100部门共享任务锁定,200部门共享任务解锁,300分配任务给目标工号,400 分配任务给目标部门
	 * @param target_oper	 		 oper_type =300时必填
	 * @param target_oper_depart	 oper_type =300时选填
	 * @param target_depart 	 	 oper_type =400时必填
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage createTaskAssignment(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();

		if (jsonToBeanServDu == null) {
			logger.info("====================jsonToBeanServDu is null============================" + jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if (paraVo == null) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("参数为空");
			return uocMessage;
		}

		String jsession_id = paraVo.getJsession_id();
		String order_no = paraVo.getOrder_no();
		String oper_type = paraVo.getOper_type();
		String target_oper = paraVo.getOper_no();
		String target_depart = paraVo.getDepart_no();
		String target_oper_depart = paraVo.getTarget_oper_depart();

		if (StringUtils.isEmpty(jsession_id)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(order_no)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("order_no不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(oper_type)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("oper_type不能为空");
			return uocMessage;
		}
		if (oper_type.equals("300") && StringUtils.isEmpty(target_oper)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("target_oper不能为空");
			return uocMessage;
		}
		if (oper_type.equals("400") && StringUtils.isEmpty(target_depart)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("target_depart不能为空");
			return uocMessage;
		}

		// TODO 1.调用BASE0008记录接口日志

		// 2.根据jsession_id调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		if (operServDu == null) {
			logger.info("====================operServDu is null============================" + operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("需要登陆");
			return uocMessage;
		}
		if (("9999").equals(loginMessage.getArgs().get("code"))) {
			uocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			uocMessage.setContent("能力平台调用失败！");
			return uocMessage;
		}

		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String oper_no = (String) oper_info.get("oper_no");
		String depart_no = (String) oper_info.get("depart_no");

		switch (oper_type) {
		// 将当前部门任务转移到当前工号
		case "100":
			uocMessage = artificialTaskAssignServDu.taskOperateAssignment(order_no, oper_no, depart_no, "", oper_no,
					paraVo.getExpress_flag());
			break;
		// 将当前工号任务转移到当前部门
		case "200":
			uocMessage = artificialTaskAssignServDu.taskOperateAssignment(order_no, "", "", depart_no, oper_no, paraVo.getExpress_flag());
			break;
		// 将任务转移到新工号上
		case "300":
			uocMessage = artificialTaskAssignServDu.taskOperateAssignment(order_no, target_oper, target_oper_depart, "", oper_no,
					paraVo.getExpress_flag());
			break;
		// 将任务转移到新部门上
		case "400":
			uocMessage = artificialTaskAssignServDu.taskOperateAssignment(order_no, "", "", target_depart, oper_no,
					paraVo.getExpress_flag());
			break;
		default:
			break;
		}

		if (!uocMessage.getRespCode().equals(RespCodeContents.SUCCESS)) {
			logger.info("----------分配任务失败-------------");
			// 抛出业务异常
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocExceptionMsg.setContent("分配任务失败");
			throw new UocException(uocExceptionMsg);
		}

		return uocMessage;
	}

	/**
	 * UOC0036 获取任务详情
	 * @param jsession_id	  登陆验证串
	 * @param order_no		  订单编号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getTaskDetailInfo(String jsession_id, String order_no) throws Exception {
		UocMessage uocMessage=new UocMessage();

		if(StringUtils.isEmpty(jsession_id)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		if(StringUtils.isEmpty(order_no)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("order_no不能为空");
			return uocMessage;
		}

		//1.根据jsession_id调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		if (operServDu == null) {
			logger.info("====================operServDu is null============================" + operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("需要登陆");
			return uocMessage;
		}
		if ("9999".equals(loginMessage.getArgs().get("code"))) {
			uocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			uocMessage.setContent("能力平台调用失败！");
			return uocMessage;
		}

		//2.通过订单号，任务状态=100、101条件关联查询人工任务实例表、环节功能表
		logger.info("-----查询人工任务实例表-----，order_no="+order_no);
		List<ProcInstTaskInstPo> poList = procInstTaskInstServ.querytaskInstByOrderNoAndTacheCode(order_no, "");
		if(poList==null){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("人工任务实例表查询为空");
			return uocMessage;
		}

		TaskDetailVo taskDetailVo=new TaskDetailVo();
		ProcInstTaskInstPo po=poList.get(0);

		ProcModTacheBtnVo paramVo = new ProcModTacheBtnVo();
		paramVo.setArea_code(po.getArea_code());
		paramVo.setProvince_code(po.getProvince_code());
		paramVo.setTache_code(po.getTache_code());
		List<ProcModTacheBtnVo> procModTacheBtnPoList = procModTacheBtnServDu.queryProcModTacheBtnList(paramVo, 1, 99);
		taskDetailVo.setOrder_no(order_no);
		taskDetailVo.setTask_name(po.getTask_name());
		taskDetailVo.setCreate_time(po.getCreate_time());
		taskDetailVo.setFinish_time(StringUtils.isEmpty(po.getFinish_time()) ? "" : po.getFinish_time());
		taskDetailVo.setLimit_time(StringUtils.isEmpty(po.getLimit_time()) ? "" : po.getLimit_time());
		taskDetailVo.setGlobal_limit_time(StringUtils.isEmpty(po.getGlobal_limit_time()) ? "" : po.getGlobal_limit_time());
		taskDetailVo.setOper_code(StringUtils.isEmpty(po.getOper_code()) ? "" : po.getOper_code());
		taskDetailVo.setTache_code(po.getTache_code());
		if(procModTacheBtnPoList!=null){
			taskDetailVo.setTask_url(StringUtils.isEmpty(procModTacheBtnPoList.get(0).getCall_url()) ? "": procModTacheBtnPoList.get(0).getCall_url());
		}
		// order_type是101服务订单，则还需要关联查询服务订单客户信息表、服务订单信息表，取出客户信息、产品信息、号码信息
		if (po.getOrder_type().equals("101")) {
			// 取服务订单客户信息表
			InfoServiceOrderPersionVo persionParamVo = new InfoServiceOrderPersionVo();
			persionParamVo.setServ_order_no(order_no);
			List<InfoServiceOrderPersionVo> infoServiceOrderPersionList = infoServiceOrderPersionServDu.queryInfoServiceOrderPersionByOrderNo(persionParamVo);
			if (infoServiceOrderPersionList == null) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单客户信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				taskDetailVo.setCert_no("");
				taskDetailVo.setCust_name("");
			} else {
				InfoServiceOrderPersionVo infoServiceOrderPersionVo = infoServiceOrderPersionList.get(0);
				taskDetailVo.setCert_no(infoServiceOrderPersionVo.getCert_no());
				taskDetailVo.setCust_name(infoServiceOrderPersionVo.getCust_name());
			}

			String prod_name = "";
			if (StringUtils.isNotEmpty(po.getProd_code())) {
				CodeList paramCode = new CodeList();
				paramCode.setCode_id(po.getProd_code());
				CodeList prod = codeListServDu.queryCodeListByCodeIdFromRedis(paramCode);
				if (prod != null) {
					prod_name = prod.getCode_name();
				} else {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("SERVICE_NAME", "querySkuDefineAndSkuAttr");
					Map<String, String> map = new HashMap<String, String>();
					map.put("jsession_id", jsession_id);
					map.put("json_info", "{\"sku_define\":{\"sku_id\" : \"'" + po.getProd_code() + "'\",\"sku_type\":\"01\"}}");
					jsonObj.put("params", map);
					logger.info("-------通过能力平台查询UGC商品名称---------");

					UocMessage abilityMessage = abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "1200", "", "");
					if ("0000".equals(abilityMessage.getRespCode()) && "200".equals(abilityMessage.getArgs().get("code").toString())) {
						if (abilityMessage.getArgs().get("json_info") != null) {
							String json_info_out = (String) abilityMessage.getArgs().get("json_info");
							Map<String, Object> outMap = jsonToBeanServDu.jsonToMap(json_info_out);
							Map<String, Object> jsonMap = new HashMap<String, Object>();
							if (outMap.get("args") instanceof String) {
								jsonMap = jsonToBeanServDu.jsonToMap((String) outMap.get("args"));
							} else {
								jsonMap = (Map<String, Object>) outMap.get("args");
							}
							if (jsonMap != null && jsonMap.get("skuDefineVo") != null) {
								Map<String, String> skuMap = (Map<String, String>) jsonMap.get("skuDefineVo");
								prod_name = skuMap.get("sku_name");
							} else {
								logger.info("-------UGC sku_define表未定义该sku---------");
							}
						}
					}
				}
			}

			if (StringUtils.isNotEmpty(prod_name)) {
				taskDetailVo.setProd_name(prod_name);
			}
			taskDetailVo.setProd_code(StringUtils.isNotEmpty(po.getProd_code()) ? po.getProd_code() : "");

			// 取服务订单
			InfoServiceOrderVo infoParamVo = new InfoServiceOrderVo();
			infoParamVo.setServ_order_no(order_no);
			List<InfoServiceOrderVo> infoServiceOrderList = infoServiceOrderServDu.queryInfoServiceOrderByOrderNo(infoParamVo);
			if (infoServiceOrderList == null) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				taskDetailVo.setAcc_nbr("");
			} else {
				taskDetailVo.setAcc_nbr(infoServiceOrderList.get(0).getAcc_nbr());
			}

			InfoServiceOrderSimCardVo simCardVo = new InfoServiceOrderSimCardVo();
			simCardVo.setServ_order_no(order_no);
			List<InfoServiceOrderSimCardVo> cardList=infoServiceOrderSimCardServDu.queryInfoServiceOrderSimCardByOrderNo(simCardVo);
			if(cardList != null && cardList.size() > 0){
				taskDetailVo.setIccid(cardList.get(0).getSim_id());
			}else{
				taskDetailVo.setIccid("");
			}

		}

		// order_type是100销售订单，则还需要关联查询销售订单客户信息表，取出客户信息即可，产品跟号码信息留空
		if (po.getOrder_type().equals("100")) {
			// 取销售订单客户信息表
			InfoSaleOrderPersionVo salePersionParamVo = new InfoSaleOrderPersionVo();
			salePersionParamVo.setSale_order_no(order_no);
			InfoSaleOrderPersionVo infoSaleOrderPersionVo = infoSaleOrderPersionServDu.getInfoSaleOrderPersionBySaleOrderNo(salePersionParamVo);
			if (infoSaleOrderPersionVo == null) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单客户信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				taskDetailVo.setCert_no("");
				taskDetailVo.setCust_name("");
				taskDetailVo.setProd_code("");
				taskDetailVo.setAcc_nbr("");
			} else {
				taskDetailVo.setCert_no(infoSaleOrderPersionVo.getCert_no());
				taskDetailVo.setCust_name(infoSaleOrderPersionVo.getCust_name());
				taskDetailVo.setProd_code("");
				taskDetailVo.setAcc_nbr("");
			}
		}

		// 3.调用BASE0003订单模板获取服务取到出参模板
		UocMessage result_mod_code = ordModFunctionServDu.queryOrdMod(order_no, "102", po.getOrder_type());
		if ("0000".equals(result_mod_code.getRespCode())) {
			// 若取到模板，调用BASE0006根据订单模板出库服务，取出json串，
			String mod_code = (String) result_mod_code.getArgs().get("mod_code");
			logger.info("------------mod_code---------" + mod_code);
			UocMessage messageOrdModOut = ordModFunctionServDu.outByOrdMod(order_no, mod_code,po.getOrder_type(), "");
			Map<String, Object> argsOrdModOut = new HashMap<String, Object>();
			if ("0000".equals(messageOrdModOut.getRespCode())) {
				argsOrdModOut = messageOrdModOut.getArgs();
				String json_info_out = (String) argsOrdModOut.get("json_info");
				taskDetailVo.setTask_json(json_info_out);
			} else {
				taskDetailVo.setTask_json("");
			}
		}
		//获取首月付费标识
		InfoServiceOrderProdMapVo infoServiceOrderProdMapVo =new InfoServiceOrderProdMapVo();
		if("101".equals(po.getOrder_type())){
			infoServiceOrderProdMapVo.setServ_order_no(order_no);
		}else if("100".equals(po.getOrder_type())){
			infoServiceOrderProdMapVo.setSale_order_no(order_no);
		}
		List<InfoServiceOrderProdMapVo> listVo =infoServiceOrderProdMapServDu.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapVo);
		if(listVo !=null && listVo.size()>0){
			taskDetailVo.setFirst_month_rent(listVo.get(0).getFirst_month_rent());
		}

		logger.info("------------获取任务详情成功！---------");
		uocMessage.setContent("获取任务详情成功！");
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.addArg("task_detail", taskDetailVo);

		return uocMessage;
	}

	/**
	 * UOC0032 根据type_code查询业务（operCode）、产品 （prodCode）
	 * @param jsession_id	  登陆验证串
	 * @param type_code
	 * @return
	 * @throws Exception
	 */
	@Override
	public UocMessage getTaskQueryInfo(String jsession_id,String type_code) throws Exception {
		UocMessage uocMessage=new UocMessage();

		//1.根据jsession_id调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		if (operServDu == null) {
			logger.info("====================operServDu is null============================" + operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("需要登陆");
			return uocMessage;
		}
		if ("9999".equals(loginMessage.getArgs().get("code"))) {
			uocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			uocMessage.setContent("能力平台调用失败！");
			return uocMessage;
		}

		CodeList codeList =new CodeList();
		codeList.setType_code(type_code);
		List<CodeList> codeLists =codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);

		if(codeLists!=null&&codeLists.size()>0){
			uocMessage.setContent("查询成功");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("code_list", codeLists);
		}else{
			uocMessage.setContent("未查询到数据");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
		}
		return uocMessage;
	}

	/**
	 * UOC0046 获取可选任务包
	 * @param jsession_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getOptionalTaskPkgList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();

		if (StringUtils.isEmpty(paraVo.getJsession_id())) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		int pageNo = Integer.parseInt(paraVo.getPage_no());
		int pageSize = Integer.parseInt(paraVo.getPage_size());
		int pkgCount = 0;
		int pkgPage = 1;

		// 1.根据jsession_id调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		if (operServDu == null) {
			logger.info("====================operServDu is null============================" + operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		UocMessage loginMessage = operServDu.isLogin(paraVo.getJsession_id());
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("需要登陆");
			return uocMessage;
		}
		if ("9999".equals(loginMessage.getArgs().get("code"))) {
			uocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			uocMessage.setContent("能力平台调用失败！");
			return uocMessage;
		}

		CodeTaskPkgAppPo paramPo = new CodeTaskPkgAppPo();

		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String role_idStr = (String) oper_info.get("role_id");
		List<String> role_id_list = StrUtil.strToList(role_idStr);

		// 2.关联任务包使用表 code_task_pkg_app，查出对应的可选包
		StringBuffer sb1 = new StringBuffer();
		if (role_id_list != null) {
			for (String roleId : role_id_list) {
				sb1.append("'");
				sb1.append(roleId);
				sb1.append("'");
				sb1.append(",");
			}
		}

		String roleStr = sb1.toString();
		paramPo.setRole_id(roleStr.substring(0, roleStr.length() - 1));

		List<CodeTaskPkgAppPo> pkgList = codeTaskPkgAppServ.queryCodeTaskPkgApp(paramPo, pageNo, pageSize);
		if (pkgList == null) {
			logger.info("-------------code_task_pkg_app查询为空--------------");
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("code_task_pkg_app查询为空");
			return uocMessage;
		}
		pkgCount = codeTaskPkgAppServ.queryCodeTaskPkgAppCount(paramPo);
		pkgPage = (pkgCount + pageSize - 1) / pageSize;

		// 3.关联任务包定义表code_task_pkg_define，查出任务名称
		List<CodeTaskPkgVo> task_pkg_list = new ArrayList<CodeTaskPkgVo>();
		Map<String, String> pkgIdMap = new HashMap<String, String>();
		for (CodeTaskPkgAppPo codeTaskPkgAppPo : pkgList) {
			if (pkgIdMap.containsValue(codeTaskPkgAppPo.getPkg_id())) {
				continue;
			}
			pkgIdMap.put(codeTaskPkgAppPo.getId(), codeTaskPkgAppPo.getPkg_id());

			CodeTaskPkgDefinePo defineParamPo = new CodeTaskPkgDefinePo();
			defineParamPo.setPkg_id(codeTaskPkgAppPo.getPkg_id());
			defineParamPo.setPkg_name(paraVo.getPkg_name());
			List<CodeTaskPkgDefinePo> defineList = codeTaskPkgDefineServ.queryCodeTaskPkgDefineByPo(defineParamPo);

			if (defineList == null) {
				logger.info("-------------code_task_pkg_define查询为空--------------,pkg_id = " + codeTaskPkgAppPo.getPkg_id());
				continue;
			}

			CodeTaskPkgDesignPo designParmPo = new CodeTaskPkgDesignPo();
			designParmPo.setPkg_id(defineList.get(0).getPkg_id());
			designParmPo.setOper_code(paraVo.getOper_code());
			designParmPo.setProduct_id(paraVo.getProd_code());
			designParmPo.setTache_code(paraVo.getTache_code());
			List<CodeTaskPkgDesignPo> taskPkgDesignList = codeTaskPkgDesignServ.queryCodeTaskPkgDesignByPo(designParmPo);
			if (taskPkgDesignList == null) {
				logger.info("----------code_task_pkg_design查询为空-------------");
				continue;
			}

			List<CodeTaskPkgDesignVo> designList = new ArrayList<CodeTaskPkgDesignVo>();
			for (CodeTaskPkgDesignPo design : taskPkgDesignList) {
				CodeTaskPkgDesignVo designVo = new CodeTaskPkgDesignVo();
				BeanUtils.copyProperties(design, designVo);

				// 取环节配置表
				if (StringUtils.isNotEmpty(designVo.getTache_code())) {
					ProcModTacheVo procModTacheVo = new ProcModTacheVo();
					procModTacheVo.setTache_code(designVo.getTache_code());
					ProcModTacheVo procModTacheVoRes = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);

					designVo.setTache_name(procModTacheVoRes.getTache_name());
				} else {
					designVo.setTache_name("");
				}
				designList.add(designVo);
			}

			CodeTaskPkgVo codeTaskPkgVo = new CodeTaskPkgVo();
			codeTaskPkgVo.setPkg_id(defineList.get(0).getPkg_id());
			codeTaskPkgVo.setCreate_time(defineList.get(0).getCreate_time());
			codeTaskPkgVo.setPkg_name(defineList.get(0).getPkg_name());
			codeTaskPkgVo.setDesign_list(designList);
			List<CodeTaskPkgAppPo> roleList = codeTaskPkgAppServ.queryCodeTaskPkgAppById(codeTaskPkgAppPo);
			List<CodeTaskPkgAppVo> app_list = new ArrayList<CodeTaskPkgAppVo>();
			for (CodeTaskPkgAppPo taskPackage : roleList) {
				CodeTaskPkgAppVo appVo = new CodeTaskPkgAppVo();
				BeanUtils.copyProperties(taskPackage, appVo);
				app_list.add(appVo);
			}
			codeTaskPkgVo.setApp_list(app_list);
			task_pkg_list.add(codeTaskPkgVo);
		}

		// 按create_time排序
		Collections.sort(task_pkg_list, new Comparator<CodeTaskPkgVo>() {
			@Override
			public int compare(CodeTaskPkgVo arg0, CodeTaskPkgVo arg1) {
				return arg1.getCreate_time().compareTo(arg0.getCreate_time());
			}
		});

		uocMessage.addArg("task_pkg_list", task_pkg_list);
		uocMessage.addArg("page_no", pageNo);
		uocMessage.addArg("page_size", pageSize);
		uocMessage.addArg("total_num", pkgCount);
		uocMessage.addArg("total_page", pkgPage);
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("获取可选任务包成功");
		return uocMessage;
	}


	/**
	 * UOC0047 领取任务包
	 * @param jsession_id
	 * @param pkg_id
	 * @param pkg_num
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getTaskPackage(String jsession_id, String pkg_id, String pkg_num) throws Exception {
		UocMessage uocMessage = new UocMessage();

		if (StringUtils.isEmpty(jsession_id)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(pkg_id)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("pkg_id不能为空");
			return uocMessage;
		}

		// 1.根据jsession_id调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		if (operServDu == null) {
			logger.info("====================operServDu is null============================" + operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("需要登陆");
			return uocMessage;
		}
		if ("9999".equals(loginMessage.getArgs().get("code"))) {
			uocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			uocMessage.setContent("能力平台调用失败！");
			return uocMessage;
		}
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String oper_no = (String) oper_info.get("oper_no");
		String depart_no = (String) oper_info.get("depart_no");
		String area_code = (String) oper_info.get("area_code");

		// 2.根据传入的任务包ID，查询任务包设计表，取出对应的产品，环节，业务配置
		CodeTaskPkgDesignPo paramPo = new CodeTaskPkgDesignPo();
		paramPo.setPkg_id(pkg_id);
		List<CodeTaskPkgDesignPo> taskPkgDesignList = codeTaskPkgDesignServ.queryCodeTaskPkgDesignByPo(paramPo);
		if (taskPkgDesignList == null) {
			logger.info("----------code_task_pkg_design查询为空-------------");
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("code_task_pkg_design查询为空");
			return uocMessage;
		}
		List<ProcInstTaskInstPo> taskList=null;
		int taskCount = 0;
		for (CodeTaskPkgDesignPo po : taskPkgDesignList) {
			// 过滤出部门任务，然后按取出的数量的对应任务
			ProcInstTaskInstPo paramVo = new ProcInstTaskInstPo();
			paramVo.setArea_code(area_code);
			paramVo.setProd_code(po.getProduct_id());
			paramVo.setTache_code(po.getTache_code());
			paramVo.setOper_code(po.getOper_code());
			taskList = procInstTaskInstServ.queryDepartTaskInstByCode(paramVo);
			if (taskList == null) {
				logger.info("----------task_inst查询为空，无关联部门任务-------------");
				continue;
			}

			int pkgNum = 1;
			// 3.如果传入的任务包数量不为空并且大于1，则需要将分配任务数量乘以相应的倍数
			if (StringUtils.isNotEmpty(pkg_num)) {
				pkgNum = Integer.parseInt(pkg_num);
			}
			int taskNum = pkgNum * Integer.parseInt(StringUtils.isNotEmpty(po.getNum()) ? po.getNum() : "1");
			// 取出的数量的对应任务,调用BASE0028服务分配给当前工号,在符合条件的前提下，优先分配创建时间较早的任务
			for (int i = 0; i < taskNum && i < taskList.size(); i++) {
				UocMessage respMessage = artificialTaskAssignServDu.taskOperateAssignment(taskList.get(i).getOrder_no(), oper_no,
						depart_no, "", oper_no, "");
				taskCount++;
				if (!respMessage.getRespCode().equals(RespCodeContents.SUCCESS)) {
					logger.info("----------分配任务失败-------------");
					// 抛出业务异常
					UocMessage uocExceptionMsg = new UocMessage();
					uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocExceptionMsg.setContent("分配任务失败");
					throw new UocException(uocExceptionMsg);
				}
			}
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("领取任务包成功");
		uocMessage.addArg("taskCount", taskCount);
		return uocMessage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getTaskGroupByTacheCode(ArtificialTaskVo vo) throws Exception{
		UocMessage message = new UocMessage();
		if (StringUtils.isEmpty(vo.getJsession_id())) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		int total_num=0;
		Map<String,Object> map= new HashMap<String,Object>();
		String month="";
		String partMonth="";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(vo.getStart_time()!=null && !"".equals(vo.getStart_time()) && vo.getEnd_time()!=null && !"".equals(vo.getEnd_time())){
			Date date1 = sdf2.parse(vo.getStart_time());
			Date date2 = sdf2.parse(vo.getEnd_time());
			String acceptTimeStartTemp=sdf1.format(date1).toString().trim();
			String acceptTimeEndTemp=sdf1.format(date2).toString().trim();
			String acceptTimeStart=acceptTimeStartTemp.substring(0, 6);
			String acceptTmeEnd=acceptTimeEndTemp.substring(0, 6);

			if(!acceptTmeEnd.equals(acceptTimeStart)){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("UOC0055任务环节统计查询时间段不能跨月");
				return message;
			}
			month=acceptTmeEnd.substring(4, 6);
			partMonth=Integer.toString(Integer.parseInt(month));
		}else{
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			vo.setStart_time(sdf2.format(c1.getTime()));
			Calendar c2 = Calendar.getInstance();
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			c2.set(Calendar.SECOND, 59);
			vo.setEnd_time(sdf2.format(c2.getTime()));
			String nowDate=sdf1.format(c1.getTime()).trim();
			month=nowDate.substring(4, 6);
			partMonth=Integer.toString(Integer.parseInt(month));
		}

		vo.setPart_month(partMonth);
		ArtificialTaskPo artificialTaskPo = new ArtificialTaskPo();
		BeanUtils.copyProperties(vo,artificialTaskPo);

		//效验登录信息
		UocMessage loginMessage = operServDu.isLogin(vo.getJsession_id());
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取工号信息错误");
			return message;
		}
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		artificialTaskPo.setProvince_code((String) oper_info.get("province_code"));
		artificialTaskPo.setArea_code((String) oper_info.get("area_code"));
		if(vo.getDepart_no()==null || "".equals(vo.getDepart_no())){
			artificialTaskPo.setDepart_no((String) oper_info.get("depart_no"));
		}

		List<ProcInstTaskInstPo> list=procInstTaskInstServ.queryAllTaskInstGroupByTacheCode(artificialTaskPo);
		total_num=procInstTaskInstServ.queryCountAllTaskInstGroupByTacheCode(artificialTaskPo);

		/**
		 * 统计环节占比时，筛选条件增加未完成任务、已完成任务、全部任务，默认选择“全部任务”，
		 * 并修改服务UOC0055增加state入参，注意统计已完成任务跟全部任务时需要同时去历史表中查询对应数据
		 */
		String state=artificialTaskPo.getState();
		if(!StringUtils.isEmpty(state)&&("1".equals(state)||"2".equals(state))){
			ProcInstTaskInstHisPo procInstTaskInstHisPo=new ProcInstTaskInstHisPo();
			BeanUtils.copyProperties(artificialTaskPo,procInstTaskInstHisPo);
			total_num=total_num+procInstTaskInstHisServ.queryCountAllTaskInstHisGroupByTacheCode(procInstTaskInstHisPo);
			List<ProcInstTaskInstHisPo> hisList=procInstTaskInstHisServ.queryAllTaskInstHisGroupByTacheCode(procInstTaskInstHisPo);
			if(hisList!=null&&hisList.size()>0){
				if(list!=null&&list.size()>0){
					logger.info("开始："+hisList);
					List<ProcInstTaskInstPo> listOut=new ArrayList<ProcInstTaskInstPo>();
					for(int i=0;i<list.size();i++){
						for(int j=0;j<hisList.size();j++){
							//根据环节编码判断，相等的话，在原来基础上数量累加，否则设置新值到前台展示
							String tachCode=list.get(i).getTache_code();
							String tachCodeHis=hisList.get(j).getTache_code();
							int countInst=Integer.parseInt(list.get(i).getTache_count());
							int countHis=Integer.parseInt(hisList.get(j).getTache_count());
							if(tachCodeHis.equals(tachCode)){
								String count=String.valueOf(countHis+countInst);
								list.get(i).setTache_count(count);
								//剩下的是不相同的环节
								hisList.remove(hisList.get(j));
							}
						}
					}
					logger.info("剩下："+hisList);
					for(ProcInstTaskInstHisPo l:hisList){
						ProcInstTaskInstPo taskPo = new ProcInstTaskInstPo();
						BeanUtils.copyProperties(l,taskPo);
						list.add(taskPo);
					}
					listOut.addAll(list);
					list=listOut;
				}else {
					for(ProcInstTaskInstHisPo l:hisList){
						ProcInstTaskInstPo taskPo = new ProcInstTaskInstPo();
						BeanUtils.copyProperties(l,taskPo);
						list.add(taskPo);
					}
				}
			}

		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("任务环节统计成功");
		message.addArg("tache_num_list", list);
		message.addArg("total_num", total_num);
		return message;
	}

}
