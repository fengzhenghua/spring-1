package com.tydic.unicom.uoc.business.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.sale.interfaces.InfoSaleOrderBusinessServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderCancelApplyServDu;
import com.tydic.unicom.uoc.business.order.service.vo.OrderCancelApplyVo;
import com.tydic.unicom.uoc.service.activiti.interfaces.CheckProcessServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOrderCancelServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoOrderCancelVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.uoc.service.task.interfaces.CheckArtificialTaskServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;
public class OrderCancelApplyServDuImpl implements OrderCancelApplyServDu{
	Logger logger = Logger.getLogger(OrderCancelApplyServDuImpl.class);

	@Autowired
	private OperServDu operServDu;
	@Autowired
	private CheckArtificialTaskServDu checkArtificialTaskServDu;
	@Autowired
	private InfoOrderCancelServDu infoOrderCancelServDu;
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;
	@Autowired
	private GetIdServDu getIdServDu;
	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;
	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private CheckProcessServDu checkProcessServDu;
	@Autowired
	private ProcessCirculationServDu processCirculationServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private InfoSaleOrderBusinessServDu infoSaleOrderBusinessServDu;

	public void getBeanDu(){
		if(operServDu==null){
			operServDu=(OperServDu) ToolSpring.getBean("OperServDu");
		}
		if(checkArtificialTaskServDu==null){
			checkArtificialTaskServDu=(CheckArtificialTaskServDu) ToolSpring.getBean("CheckArtificialTaskServDu");
		}
		if(infoOrderCancelServDu==null){
			infoOrderCancelServDu=(InfoOrderCancelServDu) ToolSpring.getBean("InfoOrderCancelServDu");
		}
		if(infoSaleOrderServDu==null){
			infoSaleOrderServDu=(InfoSaleOrderServDu) ToolSpring.getBean("InfoSaleOrderServDu");
		}
		if(procInstTaskInstServ==null){
			procInstTaskInstServ=(ProcInstTaskInstServ) ToolSpring.getBean("ProcInstTaskInstServ");
		}
		if(getIdServDu==null){
			getIdServDu=(GetIdServDu) ToolSpring.getBean("GetIdServDu");
		}
		if(procInstTaskDealRecordServ==null){
			procInstTaskDealRecordServ=(ProcInstTaskDealRecordServ) ToolSpring.getBean("ProcInstTaskDealRecordServ");
		}
		if(checkProcessServDu==null){
			checkProcessServDu=(CheckProcessServDu) ToolSpring.getBean("CheckProcessServDu");
		}
		if(processCirculationServDu==null){
			processCirculationServDu=(ProcessCirculationServDu) ToolSpring.getBean("ProcessCirculationServDu");
		}
		if(infoServiceOrderServDu==null){
			infoServiceOrderServDu=(InfoServiceOrderServDu) ToolSpring.getBean("InfoServiceOrderServDu");
		}
		if(jsonToBeanServDu == null){
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if(infoSaleOrderBusinessServDu == null){
			infoSaleOrderBusinessServDu=(InfoSaleOrderBusinessServDu) ToolSpring.getBean("InfoSaleOrderBusinessServDu"); 
		}
	}
	/**
	 * 撤销订单申请UOC0057入参组装
	 * @throws Exception
	 */
	public UocMessage createOrderCancelForAbilityPlatform(String json_in) throws Exception{
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		OrderCancelApplyVo vo=new OrderCancelApplyVo();
		if(map.containsKey("jsession_id")){
			String jsession_id = map.get("jsession_id").toString();
			vo.setJsession_id(jsession_id);
		}
		if(map.containsKey("order_no")){
			String order_no = map.get("order_no").toString();
			vo.setOrder_no(order_no);
		}
		if(map.containsKey("order_type")){
			String order_type = map.get("order_type").toString();
			vo.setOrder_type(order_type);
		}
		if(map.containsKey("tache_code")){
			String tache_code = map.get("tache_code").toString();
			vo.setTache_code(tache_code);
		}
		if(map.containsKey("cancel_type")){
			String cancel_type = map.get("cancel_type").toString();
			vo.setCancel_type(cancel_type);
		}
		if(map.containsKey("cancel_reason")){
			String cancel_reason = map.get("cancel_reason").toString();
			vo.setCancel_reason(cancel_reason);
		}

		UocMessage message=createOrderCancelApply(vo);
		return message;
	}
	/**
	 * UOC0057撤销订单申请
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage createOrderCancelApply(OrderCancelApplyVo vo) throws Exception {
		logger.info("rest----------- createOrderCancelApply UOC00057");
		getBeanDu();
		UocMessage message=new UocMessage();
		if (StringUtils.isEmpty(vo.getJsession_id())) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		if (StringUtils.isEmpty(vo.getOrder_no())) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_no不能为空");
			return message;
		}
		if (StringUtils.isEmpty(vo.getOrder_type())) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_type不能为空");
			return message;
		}
		if (StringUtils.isEmpty(vo.getTache_code())) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("tache_code不能为空");
			return message;
		}
		if (StringUtils.isEmpty(vo.getCancel_type())) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("cancel_type不能为空");
			return message;
		}

		InfoOrderCancelVo orderCancelApplyVo = new InfoOrderCancelVo();
		Date dt=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cancel_time=sdf.format(dt);
		//效验登录信息
		UocMessage loginMessage = operServDu.isLogin(vo.getJsession_id());
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取工号信息错误");
			return message;
		}

		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		orderCancelApplyVo.setArea_code((String) oper_info.get("area_code"));
		orderCancelApplyVo.setProvince_code((String) oper_info.get("province_code"));
		orderCancelApplyVo.setPart_month((String) oper_info.get("part_moth"));
		orderCancelApplyVo.setCancel_oper_no((String) oper_info.get("oper_no"));
		orderCancelApplyVo.setOrder_no(vo.getOrder_no());
		orderCancelApplyVo.setOrder_type(vo.getOrder_type());
		orderCancelApplyVo.setCancel_reason(vo.getCancel_reason());
		orderCancelApplyVo.setCancel_type(vo.getCancel_type());
		orderCancelApplyVo.setCancel_time(cancel_time);
		orderCancelApplyVo.setCancel_tache_code(vo.getTache_code());
		InfoOrderCancelVo existVo = infoOrderCancelServDu.queryInfoOrderCancel(orderCancelApplyVo);
		if (existVo != null) {
			boolean updateInfoOrderCanceFlag = infoOrderCancelServDu.updateInfoOrderCancel(orderCancelApplyVo);
			if (!updateInfoOrderCanceFlag) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("更新撤单记录表记录失败");
				throw new UocException(message);
			}
		} else {
			boolean createInfoOrderCanceFlag = infoOrderCancelServDu.createInfoOrderCancel(orderCancelApplyVo);
			if (!createInfoOrderCanceFlag) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("创建撤单记录表记录失败");
				throw new UocException(message);
			}
		}


		// 4、更新人工任务实例表的任务状态为已处理，填写完成时间，还要记录人工任务操作记录表，记录传入的处理动作、处理描述、处理系统编码，注意这里处理任务时用tache_code=E00001,不要用传入的tache_code
		ProcInstTaskInstPo paramPo = new ProcInstTaskInstPo();
		paramPo.setOrder_no(vo.getOrder_no());
		List<ProcInstTaskInstPo> list = procInstTaskInstServ.queryTaskInstByTask(paramPo);
		if (list == null || list.size() <= 0) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("人工任务实例为空");
			return message;
		}
		ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
		String id = getIdServDu.getId("createLogId", list.get(0).getProvince_code(), "*", "");
		procInstTaskDealRecordPo.setId(id);
		procInstTaskDealRecordPo.setTask_id(list.get(0).getTask_id());
		procInstTaskDealRecordPo.setProvince_code(list.get(0).getProvince_code());
		procInstTaskDealRecordPo.setArea_code(list.get(0).getArea_code());
		procInstTaskDealRecordPo.setPart_month(list.get(0).getPart_month());
		procInstTaskDealRecordPo.setDeal_time(cancel_time);
		procInstTaskDealRecordPo.setDeal_oper_no((String) oper_info.get("oper_no"));
		procInstTaskDealRecordPo.setDeal_system_no("");
		procInstTaskDealRecordPo.setDeal_code("");
		procInstTaskDealRecordPo.setDeal_desc("");
		procInstTaskDealRecordPo.setOrder_no(vo.getOrder_no());
		procInstTaskDealRecordPo.setOrder_type(vo.getOrder_type());
		procInstTaskDealRecordPo.setTache_code("E00001");
		procInstTaskDealRecordPo.setOper_code(list.get(0).getOper_code());
		procInstTaskDealRecordPo.setCreate_time(list.get(0).getCreate_time());
		procInstTaskDealRecordPo.setProd_code(list.get(0).getProd_code());
		if (procInstTaskDealRecordServ == null) {
			logger.info("====================procInstTaskDealRecordServ is null============================" + procInstTaskDealRecordServ);
			procInstTaskDealRecordServ = (ProcInstTaskDealRecordServ) ToolSpring.getBean("ProcInstTaskDealRecordServ");
		}
		boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
		if (!flag) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("创建人工任务操作记录失败");
			throw new UocException(message);
		}

		// 5、调用BASE0016服务进行服务订单的流程流转，oper_type传101，flow_type传0
		UocMessage proMessage = processCirculationServDu.processCirculation(vo.getOrder_no(), "101", "0", null, "");

		if (!"0000".equals(proMessage.getRespCode())) {
			throw new UocException(proMessage);
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("订单撤销申请成功");
		return message;
	}
	public UocMessage checkInfoOrderCancelForAbilityPlatform(String json_in) throws Exception{
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo paraVo=new ParaVo();
		if(map.containsKey("jsession_id")){
			String jsession_id = map.get("jsession_id").toString();
			paraVo.setJsession_id(jsession_id);
		}
		if(map.containsKey("order_no")){
			String order_no = map.get("order_no").toString();
			paraVo.setOrder_no(order_no);
		}
		if(map.containsKey("order_type")){
			String order_type = map.get("order_type").toString();
			paraVo.setOrder_type(order_type);
		}
		if(map.containsKey("deal_code")){
			String deal_code = map.get("deal_code").toString();
			paraVo.setDeal_code(deal_code);
		}
		if(map.containsKey("deal_system_no")){
			String deal_system_no = map.get("deal_system_no").toString();
			paraVo.setDeal_system_no(deal_system_no);
		}
		if(map.containsKey("audit_flag")){
			String audit_flag=map.get("audit_flag").toString();
			paraVo.setAudit_flag(audit_flag);
		}
		if(map.containsKey("audit_desc")){
			String audit_desc=map.get("audit_desc").toString();
			paraVo.setAudit_desc(audit_desc);
		}
		if(map.containsKey("deal_desc")){
			String deal_desc=map.get("deal_desc").toString();
			paraVo.setAudit_desc(deal_desc);	
		}
	
		UocMessage message=checkInfoOrderCancel(paraVo);
		return message;
	}
	@Override
	public UocMessage checkInfoOrderCancel(ParaVo paraVo) throws Exception {
		getBeanDu();
		
		UocMessage uocMessage = new UocMessage();
		
		if (StringUtils.isEmpty(paraVo.getJsession_id())) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(paraVo.getOrder_no())) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("order_no不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(paraVo.getOrder_type())) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("order_type不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(paraVo.getAudit_flag())) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("audit_flag不能为空");
			return uocMessage;
		}

		// 1、调用BASE0008记录接口日志(暂缓)

		// 2、根据jsession_id调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		UocMessage loginMessage = operServDu.isLogin(paraVo.getJsession_id());
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
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");

		String oper_no = (String) oper_info.get("oper_no");
		String order_no = paraVo.getOrder_no();
		String order_type = paraVo.getOrder_type();
		String audit_flag = paraVo.getAudit_flag();

		// 3、初始化当前环节tache_code=E00002，然后调用BASE0025进行任务校验
		UocMessage checkMessage = checkArtificialTaskServDu.checkArtificialTaskProcess(order_no, "E00002", oper_info);
		if (!checkMessage.getRespCode().equals(RespCodeContents.SUCCESS)) {
			return checkMessage;
		}

		// 4、查询根据传入的订单号，订单类型条件查询info_order_cancel表是否有记录，取出撤单环节编码cancel_tache_code
		logger.info("---------查询info_order_cancel，order_no=" + order_no + "order_type=" + order_type);
		InfoOrderCancelVo vo = new InfoOrderCancelVo();
		vo.setOrder_no(order_no);
		vo.setOrder_type(order_type);
		InfoOrderCancelVo existInfoOrderCancel = infoOrderCancelServDu.queryInfoOrderCancel(vo);
		String cancel_tache_code = "";
		if (existInfoOrderCancel == null) {
			logger.info("---------info_order_cancel无order_no=" + order_no + "order_type=" + order_type);
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("info_order_cancel查询为空");
			return uocMessage;
		} else {
			cancel_tache_code = existInfoOrderCancel.getCancel_tache_code();
		}

		// 5、初始化action_code字段：
		// 1)如果audit_flag=1则action_code={“condParam1”:“S00009”}
		// 2)如果audit_flag=2则action_code={“condParam1”:cancel_tache_code(从第4点中取出)}
		// 然后调用BASE0022服务校验是否满足流转要求,flow_type传1
		Map<String, String> action_code = new HashMap<String, String>();
		if (audit_flag.equals("1")) {
			action_code.put("condParam1", "S00009");
		} else if (audit_flag.equals("2")) {
			action_code.put("condParam1", cancel_tache_code);
		}

		String proc_inst_id = "";
		if (order_type.equals("100")) {
			// 销售订单表的流程实例
			InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(order_no);
			infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if (infoSaleOrderVo == null) {
				logger.info(">>>>>>>>>>>>>>>无对应的销售订单<<<<<<<<<<<<<<<<<");
				uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				uocMessage.setContent("无对应的销售订单");
				return uocMessage;
			}
			proc_inst_id = infoSaleOrderVo.getProc_inst_id();
		}

		if (order_type.equals("101")) {
			// 服务订单表的流程实例
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(order_no);
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			if (infoServiceOrderVo == null) {
				logger.info(">>>>>>>>>>>>>>>>无对应的服务订单<<<<<<<<<<<<<<<<<<<<");
				uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				uocMessage.setContent("无对应的服务订单");
				return uocMessage;
			}
			proc_inst_id = infoServiceOrderVo.getProc_inst_id();
		}

		UocMessage checkRes = checkProcessServDu.checkProcess(proc_inst_id, order_no, "1", action_code);
		if (!"0000".equals(checkRes.getRespCode())) {
			logger.info(">>>>>>>>>>>>>>>BASE0022服务流程流转校验失败<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("服务流转校验失败");
			return uocMessage;
		}

		// 6、若audit_flag=1 ，调用UOC0007服务进行订单撤销
		if (audit_flag.equals("1")) {
			UocMessage cancelMsg = infoSaleOrderBusinessServDu.cancelOrder(paraVo);
			if (!"0000".equals(cancelMsg.getRespCode())) {
				throw new UocException(cancelMsg);
			}
			// 9、调用BASE0016服务进行流程流转，flow_type传1，action_code从第5点中取出,oper_type=105已撤销服务订单
			uocMessage = processCirculationServDu.processCirculation(order_no, "101", "1", action_code, "");
			if (!"0000".equals(uocMessage.getRespCode())) {
				throw new UocException(uocMessage);
			}
		} else if (audit_flag.equals("2")) {
			// 9、调用BASE0016服务进行流程流转，flow_type传1，action_code从第5点中取出,oper_type=105已撤销服务订单
			uocMessage = processCirculationServDu.processCirculation(order_no, "101", "1", action_code, "");
			if (!"0000".equals(uocMessage.getRespCode())) {
				throw new UocException(uocMessage);
			}
		}

		// 7、 更新info_order_cancel表的对应字段，包含审核状态、时间、工号、描述等等，其中时间取当前系统时间，工号取当前登录工号
		String date = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		existInfoOrderCancel.setAudit_state(audit_flag);
		existInfoOrderCancel.setAudti_time(date);
		existInfoOrderCancel.setAudit_oper_no(oper_no);
		existInfoOrderCancel.setAudit_desc(StringUtils.isEmpty(paraVo.getAudit_desc()) ? "" : paraVo.getAudit_desc());
		boolean updateFlag = infoOrderCancelServDu.updateInfoOrderCancel(existInfoOrderCancel);
		if (!updateFlag) {
			logger.info(">>>>>>>>>>>>>>>更新订单撤单记录表失败<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("更新订单撤单记录表失败");
			throw new UocException(uocMessage);
		}

		// 8、更新人工任务实例表的任务状态为已处理，填写完成时间，还要记录人工任务操作记录表，记录传入的处理动作、处理描述、处理系统编码
		List<ProcInstTaskInstPo> list = procInstTaskInstServ.querytaskInstByOrderNoAndTacheCode(vo.getOrder_no(), "");
		if (list == null || list.size() <= 0) {
			logger.info(">>>>>>>>>>>>>>>人工任务实例表查询为空<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("人工任务实例表查询为空");
			return uocMessage;
		}
		ProcInstTaskInstPo taskInstPo = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(list.get(0), taskInstPo);
		taskInstPo.setTask_state("102");
		taskInstPo.setFinish_time(date);

		ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
		String id = getIdServDu.getId("createLogId", list.get(0).getProvince_code(), "*", "");
		procInstTaskDealRecordPo.setId(id);
		procInstTaskDealRecordPo.setTask_id(list.get(0).getTask_id());
		procInstTaskDealRecordPo.setProvince_code(list.get(0).getProvince_code());
		procInstTaskDealRecordPo.setArea_code(list.get(0).getArea_code());
		procInstTaskDealRecordPo.setPart_month(list.get(0).getPart_month());
		procInstTaskDealRecordPo.setDeal_time(date);
		procInstTaskDealRecordPo.setDeal_oper_no(oper_no);
		procInstTaskDealRecordPo.setDeal_system_no(StringUtils.isEmpty(paraVo.getDeal_system_no()) ? "" : paraVo.getDeal_system_no());
		procInstTaskDealRecordPo.setDeal_code(StringUtils.isEmpty(paraVo.getDeal_code()) ? "" : paraVo.getDeal_code());
		procInstTaskDealRecordPo.setDeal_desc(StringUtils.isEmpty(paraVo.getDeal_desc()) ? "" : paraVo.getDeal_desc());
		procInstTaskDealRecordPo.setOrder_no(vo.getOrder_no());
		procInstTaskDealRecordPo.setOrder_type(vo.getOrder_type());
		procInstTaskDealRecordPo.setTache_code(list.get(0).getTache_code());
		procInstTaskDealRecordPo.setOper_code(list.get(0).getOper_code());
		procInstTaskDealRecordPo.setCreate_time(list.get(0).getCreate_time());
		procInstTaskDealRecordPo.setProd_code(list.get(0).getProd_code());
		boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
		if (!flag) {
			logger.info(">>>>>>>>>>>>>>>创建人工任务操作记录失败<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("创建人工任务操作记录失败");
			throw new UocException(uocMessage);
		}
		boolean flagUpdate = procInstTaskInstServ.updateByOrderNo(taskInstPo);
		if (!flagUpdate) {
			logger.info(">>>>>>>>>>>>>>>更新人工任务实例表失败<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("更新人工任务实例表失败");
			throw new UocException(uocMessage);
		}

		logger.info(">>>>>>>>>>>>>>>订单撤销审核通过<<<<<<<<<<<<<<<");
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("订单撤销审核通过");

		return uocMessage;
	}

}
