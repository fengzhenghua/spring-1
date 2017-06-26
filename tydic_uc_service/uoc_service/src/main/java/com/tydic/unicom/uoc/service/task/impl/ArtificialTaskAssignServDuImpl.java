package com.tydic.unicom.uoc.service.task.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskRuleSpecPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTaskRuleSpecServ;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignDepartServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignOperServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.business.common.interfaces.GetDepartAndOperInfoServDu;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleDepartVo;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.PushMsgToWebAppServDu;
import com.tydic.unicom.uoc.service.log.interfaces.ArtificialTaskRecordServDu;
import com.tydic.unicom.uoc.service.log.vo.ProcInstTaskAssignRecordVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.uoc.service.task.interfaces.ArtificialTaskAssignServDu;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskAssignRuleServDu;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskRuleDepartServDu;
import com.tydic.unicom.uoc.service.task.vo.ProcTaskAssignRuleVo;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ArtificialTaskAssignServDu")
public class ArtificialTaskAssignServDuImpl implements ArtificialTaskAssignServDu {
	Logger logger = Logger.getLogger(ArtificialTaskAssignServDuImpl.class);

	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;

	@Autowired
	private ProcTaskAssignRuleServDu procTaskAssignRuleServDu;

	@Autowired
	private ProcInstTaskAssignDepartServ procInstTaskAssignDepartServ;

	@Autowired
	private ProcInstTaskAssignOperServ procInstTaskAssignOperServ;

	@Autowired
	private ArtificialTaskRecordServDu artificialTaskRecordServDu;

	@Autowired
	private GetIdServDu getIdServDu;

	@Autowired
	private GetDepartAndOperInfoServDu getDepartAndOperInfoServDu;

	@Autowired
	private ProcTaskRuleSpecServ procTaskRuleSpecServ;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;

	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;

	@Autowired
	private ProcTaskRuleDepartServDu procTaskRuleDepartServDu;

	@Autowired
	private PushMsgToWebAppServDu pushMsgToWebAppServDu;

	/**
	 * BASE0024 人工任务默认分配
	 * @param order_id
	 * @param accept_oper
	 * @return
	 * @throws Exception
	 */
	@Override
	public UocMessage taskDefaultAssignment(ProcInstTaskInstVo procInstTaskInstVo) throws Exception {
		UocMessage message = new UocMessage();
		String order_no = procInstTaskInstVo.getOrder_no();
		String accept_depart = procInstTaskInstVo.getAccept_depart_no();

		if (StringUtils.isEmpty(order_no)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_no为空！");
			return message;
		}
		if (StringUtils.isEmpty(accept_depart)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("accept_depart为空！");
			return message;
		}

		// 如果当前环节是S00005，并且关联查询交付订单表存在数据并且delivery_dept_no字段不为空,直接将该任务分配到delivery_dept_no部门下
		if ("S00005".equals(procInstTaskInstVo.getTache_code())) {
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(procInstTaskInstVo.getOrder_no());
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			if (infoServiceOrderVo == null) {
				logger.info("----------无关联服务订单-----------,order_no=" + procInstTaskInstVo.getOrder_no());
			}

			// 查询交付订单表
			InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
			infoDeliverOrderVo.setSale_order_no(infoServiceOrderVo.getSale_order_no());
			List<InfoDeliverOrderVo> infoDeliverOrderVos = infoDeliverOrderServDu.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
			if (infoDeliverOrderVos != null && infoDeliverOrderVos.size() > 0
					&& StringUtils.isNotEmpty(infoDeliverOrderVos.get(0).getDelivery_dept_no())) {
				procInstTaskInstVo.setAccept_oper_no("");
				procInstTaskInstVo.setAccept_depart_no(infoDeliverOrderVos.get(0).getDelivery_dept_no());
				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("任务默认分配成功");
				return message;
			}
		}

		// 查询task_inst表task_state ='102'的该订单
		logger.info("---------开始查询task_inst，order_no=" + procInstTaskInstVo.getOrder_no());
		ProcInstTaskInstPo po = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(procInstTaskInstVo, po);
		List<ProcInstTaskInstPo> procInstTaskInstPoList = procInstTaskInstServ.queryTaskInstByTask(po);
		// 查询任务分配规则表(proc_task_assign_rule)
		logger.info("---------开始查询proc_task_assign_rule----------");
		ProcTaskAssignRuleVo procTaskAssignRuleVo = procTaskAssignRuleServDu.queryTaskAssignRuleByTaskInstFromRedis(procInstTaskInstVo);

		if (procTaskAssignRuleVo != null) {
			// 对应任务的订单号调用BASE0028服务分配到目标部门或者目标工号上
			if (StringUtils.isNotEmpty(procTaskAssignRuleVo.getTarget_oper_no())) {
				logger.info("----------目标工号不为空----------target_oper_no=" + procTaskAssignRuleVo.getTarget_oper_no());

				// 分配给目标工号之前，判断目标工号是否在特殊分配规则表的规则ID中
				String new_oper_no = getOperFromSpecRule(procTaskAssignRuleVo.getTarget_oper_no());
				if (StringUtils.isNotEmpty(new_oper_no)) {
					procInstTaskInstVo.setAccept_oper_no(new_oper_no);
				} else {
					ProcTaskRuleDepartVo departVo = new ProcTaskRuleDepartVo();
					departVo.setRule_id(procTaskAssignRuleVo.getTarget_oper_no());
					List<ProcTaskRuleDepartVo> procTaskRuleDepartList = procTaskRuleDepartServDu.queryProcTaskRuleDepartByVo(departVo);
					if (procTaskRuleDepartList != null && procTaskRuleDepartList.size() > 0) {
						// 如果目标工号不在特殊分配规则表中但是在部门任务规则表(proc_task_rule_depart)中时，直接将任务分配到对应部门；
						procInstTaskInstVo.setAccept_depart_no(procTaskRuleDepartList.get(0).getDepart_no());
						procInstTaskInstVo.setAccept_oper_no("");
						message.setRespCode(RespCodeContents.SUCCESS);
						message.setContent("任务默认分配成功");
						return message;
					} else {
						new_oper_no = procTaskAssignRuleVo.getTarget_oper_no();
						procInstTaskInstVo.setAccept_oper_no(procTaskAssignRuleVo.getTarget_oper_no());
					}
				}

				logger.info("--------调用能力平台查询该工号部门--------oper_no=" + new_oper_no);
				UocMessage departMsg = getDepartAndOperInfoServDu.getDepartInfoByOperNo(new_oper_no);
				if (RespCodeContents.SUCCESS.equals(departMsg.getRespCode())) {
					String json_info = (String) departMsg.getArgs().get("json_info");
					if (StringUtils.isEmpty(json_info)) {
						logger.info("--------未查询到该工号所在部门--------oper_no=" + new_oper_no);
						message.setRespCode(RespCodeContents.PARAM_ERROR);
						message.setContent("未查询到该工号所在部门");
						return message;
					}
					Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
					procInstTaskInstVo.setAccept_depart_no((String) map.get("depart_no"));
				} else {
					return departMsg;
				}

			} else if (StringUtils.isNotEmpty(procTaskAssignRuleVo.getTarget_depart_no())) {
				logger.info("----------目标部门不为空----------target_depart_no=" + procTaskAssignRuleVo.getTarget_depart_no());

				procInstTaskInstVo.setAccept_depart_no(procTaskAssignRuleVo.getTarget_depart_no());
				procInstTaskInstVo.setAccept_oper_no("");
			}
		} else {
			logger.info("----------proc_task_assign_rule查询为空----------");

			if (procInstTaskInstPoList == null) {
				logger.info("---------task_inst查询为空,不存在task_state为'102'的该订单--------");
				procInstTaskInstVo.setAccept_depart_no(accept_depart);
				procInstTaskInstVo.setAccept_oper_no("");
			} else {
				logger.info("--------存在task_state为'102'的该订单,默认分配给上一次的工号-------");

				procInstTaskInstVo.setAccept_oper_no(procInstTaskInstPoList.get(0).getAccept_oper_no());
				procInstTaskInstVo.setAccept_depart_no(procInstTaskInstPoList.get(0).getAccept_depart_no());
			}
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("任务默认分配成功");

		return message;
	}

	/**
	 * BASE0028 任务分配
	 * @param order_id
	 * @param target_oper
	 * @param target_oper_depart
	 * @param target_depart
	 * @param accept_oper
	 * @return
	 * @throws Exception
	 */
	@Override
	public UocMessage taskOperateAssignment(String order_id, String target_oper, String target_oper_depart, String target_depart,
			String accept_oper, String flag) throws Exception {
		UocMessage uocMessage = new UocMessage();

		if (StringUtils.isEmpty(order_id)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("order_id不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(target_oper) && StringUtils.isEmpty(target_depart)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("target_depart、target_oper都为空");
			return uocMessage;
		}
		// 先根据传入的order_id，以及状态为100、101条件查询task_inst表
		logger.info("---------开始查询task_inst，order_no=" + order_id);
		List<ProcInstTaskInstPo> procInstTaskInstPoList = procInstTaskInstServ.querytaskInstByOrderNoAndTacheCode(order_id, "");

		if (procInstTaskInstPoList != null && procInstTaskInstPoList.size() > 0) {
			ProcInstTaskInstPo procInstTaskInstPo = procInstTaskInstPoList.get(0);
			// 把对应任务的订单号分配到目标部门或者目标工号上，并记录分配日志
			if (StringUtils.isNotEmpty(target_depart)) {
				// 任务分配到部门时，需要记录任务实例表部门，工号字段留空
				procInstTaskInstPo.setAccept_depart_no(target_depart);
				procInstTaskInstPo.setAccept_oper_no("");
				procInstTaskInstServ.updateByOrderNo(procInstTaskInstPo);

				insertArtificialTaskRecord(procInstTaskInstPo, target_depart, "", accept_oper);
			}

			if (StringUtils.isNotEmpty(target_oper)) {
				// 非强制指派任务flag为空
				if (StringUtils.isEmpty(flag) && StringUtils.isNotEmpty(procInstTaskInstPo.getAccept_oper_no())) {
					logger.info("--------该任务已分配，task_inst accept_oper_no不为空，order_no=" + order_id);
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("该任务已分配");
					return uocMessage;
				}

				// 任务分配到工号时，如果传入工号部门为空，则需要先调用BASE0029服务，查询出对应工号部门，再记录工号字段跟部门字段
				if (StringUtils.isEmpty(target_oper_depart)) {
					logger.info("--------调用能力平台查询该工号部门--------");
					UocMessage departMsg = getDepartAndOperInfoServDu.getDepartInfoByOperNo(target_oper);
					if (RespCodeContents.SUCCESS.equals(departMsg.getRespCode())) {
						String json_info = (String) departMsg.getArgs().get("json_info");
						if (StringUtils.isEmpty(json_info)) {
							uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
							uocMessage.setContent("未查询到该工号所在部门");
							return uocMessage;
						}
						Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
						target_oper_depart = (String) map.get("depart_no");
					} else {
						return departMsg;
					}
				}

				procInstTaskInstPo.setAccept_oper_no(target_oper);
				procInstTaskInstPo.setAccept_depart_no(target_oper_depart);
				procInstTaskInstServ.updateByOrderNo(procInstTaskInstPo);

				insertArtificialTaskRecord(procInstTaskInstPo, "", target_oper, accept_oper);
			}

		} else {
			logger.info("---------task_inst查询为空,不存在该order_no--------");
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("task_inst不存在该order_no");
			return uocMessage;
		}

		uocMessage.setContent("任务分配成功");
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		return uocMessage;
	}

	// 任务分配写入日志
	private void insertArtificialTaskRecord(ProcInstTaskInstPo procInstTaskInstPo, String depart_no, String oper_no, String assign_oper_no)
			throws Exception {
		ProcInstTaskAssignRecordVo procInstTaskAssignRecordVo = new ProcInstTaskAssignRecordVo();
		String id = getIdServDu.getId("createLogId", procInstTaskInstPo.getProvince_code(), "*", "");
		String date = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		procInstTaskAssignRecordVo.setId(id);
		procInstTaskAssignRecordVo.setTask_id(procInstTaskInstPo.getTask_id());
		procInstTaskAssignRecordVo.setOrder_no(procInstTaskInstPo.getOrder_no());
		procInstTaskAssignRecordVo.setAssign_time(date);
		procInstTaskAssignRecordVo.setPart_month(procInstTaskInstPo.getPart_month());
		procInstTaskAssignRecordVo.setProvince_code(procInstTaskInstPo.getProvince_code());
		procInstTaskAssignRecordVo.setArea_code(procInstTaskInstPo.getArea_code());
		procInstTaskAssignRecordVo.setAssign_oper_no(assign_oper_no);
		procInstTaskAssignRecordVo.setTo_depart_no(StringUtils.isNotEmpty(depart_no) ? depart_no : "");
		procInstTaskAssignRecordVo.setTo_oper_no(StringUtils.isNotEmpty(oper_no) ? oper_no : "");
		artificialTaskRecordServDu.insertArtificialTaskRecord(procInstTaskAssignRecordVo);
	}

	// 根据权重得到特殊规则表中的工号
	private String getOperFromSpecRule(String target_oper_no) throws Exception {
		String new_oper_no = "";

		ProcTaskRuleSpecPo po = new ProcTaskRuleSpecPo();
		po.setRule_id(target_oper_no);

		logger.info("--------查询proc_task_rule_spec-------rule_id=" + target_oper_no);
		List<ProcTaskRuleSpecPo> rules = procTaskRuleSpecServ.queryProcTaskRuleSpecByPo(po);

		// 取出对应规则(一般为多条)，并且取出权重占比字段，计算出分配给每个工号的概率，然后按计算出的概率分配给其中一个工号
		if (rules != null && rules.size() > 0) {
			int[] weightArray = new int[rules.size() + 1];
			for (int i = 0; i < rules.size(); i++) {
				Integer weight = Integer.valueOf(rules.get(i).getProportion());
				weightArray[i + 1] = weight + weightArray[i];
			}
			double randomNum = weightArray[weightArray.length - 1] * Math.random();
			int index = binarySearch(weightArray, randomNum);
			new_oper_no = index == -1 ? "" : rules.get(index).getTarget_oper_no();

		} else {
			logger.info("--------proc_task_rule_spec查询为空-------target_oper_no=" + target_oper_no);
		}

		return new_oper_no;
	}

	private int binarySearch(int[] desArray, double des) {
		int low = 0;
		int high = desArray.length - 1;
		while (low <= high) {
			int middle = (low + high) / 2;
			if (des > desArray[middle] && des <= desArray[middle + 1]) {
				return middle;
			} else if (des < desArray[middle]) {
				high = middle - 1;
			} else {
				low = middle + 1;
			}
		}
		return -1;
	}
}
