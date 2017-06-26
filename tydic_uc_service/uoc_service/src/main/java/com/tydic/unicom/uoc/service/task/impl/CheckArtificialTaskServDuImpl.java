package com.tydic.unicom.uoc.service.task.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignOperServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.service.common.impl.StrUtil;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheLoginServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheLoginVo;
import com.tydic.unicom.uoc.service.task.interfaces.CheckArtificialTaskServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("CheckArtificialTaskServDu")
public class CheckArtificialTaskServDuImpl implements CheckArtificialTaskServDu {
	Logger logger = Logger.getLogger(CheckArtificialTaskServDuImpl.class);

	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;

	@Autowired
	private ProcModTacheLoginServDu procModTacheLoginServDu;

	@Autowired
	private ProcInstTaskAssignOperServ procInstTaskAssignOperServ;

	@Override
	public UocMessage checkArtificialTaskProcess(String order_no, String tache_code, Map<String, Object> oper_info) throws Exception {
		UocMessage uocMessage = new UocMessage();
		// 1、通过订单号、环节编码（环节编码为空时则不用关联此条件）查询人工任务实例表，没有状态为100、101的数据则报错，取出环节；
		logger.info("----------查询人工任务实例表--------，order_no=" + order_no + "tache_code=" + tache_code);
		List<ProcInstTaskInstPo> procInstTaskInstList = procInstTaskInstServ.querytaskInstByOrderNoAndTacheCode(order_no, tache_code);
		if (procInstTaskInstList == null) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("订单" + order_no + "无状态为100或101，tache_code为" + tache_code + "的人工任务实例");
			logger.info("订单" + order_no + "无状态为100或101，tache_code为" + tache_code + "的人工任务实例");
			return uocMessage;
		}

		if (procInstTaskInstList.size() > 0 && !"103".equals(procInstTaskInstList.get(0).getTask_property())) {
			List<ProcModTacheLoginVo> procModTacheLoginList = null;
			String role_idStr = (String) oper_info.get("role_id");
			List<String> role_id_list = StrUtil.strToList(role_idStr);
			for (String role_id : role_id_list) {
				ProcModTacheLoginVo procModTacheLoginVo = new ProcModTacheLoginVo();
				procModTacheLoginVo.setRole_id(role_id);
				procModTacheLoginVo.setTache_code(procInstTaskInstList.get(0).getTache_code());
				logger.info("----------查询环节对应角色表--------，role_id=" + role_id + ",tache_code=" + procInstTaskInstList.get(0).getTache_code());
				procModTacheLoginList = procModTacheLoginServDu.queryProcModTacheLoginByTacheCode(procModTacheLoginVo);
				if (procModTacheLoginList != null) {
					break;
				}
			}

			// 2、查询环节对应角色表，判断当前工号是否有执行当前任务权限，没有权限直接报错
			if (procModTacheLoginList == null) {
				uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				uocMessage.setContent("当前工号无权限");
				logger.info("----------当前工号无权限---------");
				return uocMessage;
			} else {
				// 3、如果任务属性task_property不是103等待任务,还要根据传入的工号信息判断实例表的工号字段，必须是相同工号才能处理
				if (!"103".equals(procInstTaskInstList.get(0).getTask_property())) {
					String oper_no = (String) oper_info.get("oper_no");
					if (procInstTaskInstList.get(0).getAccept_oper_no().equals(oper_no)) {
						uocMessage.setRespCode(RespCodeContents.SUCCESS);
						uocMessage.setContent("人工任务处理校验通过");
						logger.info("----------人工任务处理校验通过---------");
					} else {
						uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocMessage.setContent("传入的工号信息和实例表的工号字段不同");
						logger.info("----------传入的工号信息和实例表的工号字段不同---------");
						return uocMessage;
					}
				} else {
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.setContent("人工任务处理校验通过");
					logger.info("----------人工任务处理校验通过---------");
				}
			}
		} else {
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			logger.info("------等待任务，校验通过-----");
			return uocMessage;
		}

		return uocMessage;
	}

}
