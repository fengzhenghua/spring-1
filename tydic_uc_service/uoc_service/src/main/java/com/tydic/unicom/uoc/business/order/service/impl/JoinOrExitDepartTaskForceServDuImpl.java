package com.tydic.unicom.uoc.business.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ArtificialTaskServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.JoinOrExitDepartTaskForceServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderModServDu;
import com.tydic.unicom.uoc.business.order.service.vo.ArtificialTaskVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcInstTaskInstListVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleDepartVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleSpecVo;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskRuleDepartServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class JoinOrExitDepartTaskForceServDuImpl  implements JoinOrExitDepartTaskForceServDu{

	Logger logger = Logger.getLogger(JoinOrExitDepartTaskForceServDuImpl.class);


	@Autowired
	private OperServDu operServDu;

	@Autowired
	private ProcTaskRuleDepartServDu procTaskRuleDepartServDu;

	@Autowired
	private OrderModServDu orderModServDu;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private ArtificialTaskServDu artificialTaskServDu;




	@Override
	@SuppressWarnings("unchecked")
	public UocMessage createJoinOrExitDepartTaskForce(String jsession_id,String oper_type,String quit_type)throws Exception{

		UocMessage message= new UocMessage();

		logger.info("^^^^^^^^^^^^^UOC00079==jsession_id========"+jsession_id);
		logger.info("^^^^^^^^^^^^^UOC00079==oper_type========"+oper_type);
		logger.info("^^^^^^^^^^^^^UOC00079==quit_type========"+quit_type);

		if(jsession_id == null || jsession_id.equals("")){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("失败:jsession_id为必传参数");
			return message;
		}
		if(oper_type == null || oper_type.equals("")){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("失败:oper_type为必传参数");
			return message;
		}


		// BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if (!"0000".equals(operMesg.getRespCode())) {
			return operMesg;
		}

		Map<String, Object> oper_info = (Map<String, Object>) operMesg.getArgs().get("oper_info");
		String departNo=oper_info.get("depart_no").toString();
		String operNo=oper_info.get("oper_no").toString();
		//根据部门编码查询部门任务规则表
		ProcTaskRuleDepartVo procTaskRuleDepartVo = new ProcTaskRuleDepartVo();
		procTaskRuleDepartVo.setDepart_no(departNo);
		List<ProcTaskRuleDepartVo> procTaskRuleDepartVoList=procTaskRuleDepartServDu.queryProcTaskRuleDepartByVo(procTaskRuleDepartVo);
		if(procTaskRuleDepartVoList ==null || procTaskRuleDepartVoList.size()==0){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("uoc0079找不到部门任务规则");
			return message;
		}

		//调用UOC0066
		String pageNo="1";
		String pageSize = "100";
		Map<String, Object> procTaskRuleSpec= new HashMap<String, Object>();
		Map<String, Object> jsonInfo= new HashMap<String, Object>();
		procTaskRuleSpec.put("target_oper_no", operNo);
		jsonInfo.put("procTaskRuleSpec", procTaskRuleSpec);
		jsonInfo.put("pageNo", pageNo);
		jsonInfo.put("pageSize", pageSize);
		String json=jsonToBeanServDu.mapToJson(jsonInfo).toString();
		ParaVo paraVo = new ParaVo();
		paraVo.setJsession_id(jsession_id);
		paraVo.setJson_info(json);
		UocMessage uocMessage =orderModServDu.searchTaskAssignRule(paraVo);

		if(!"0000".equals(uocMessage.getRespCode())){
			return uocMessage;
		}
		Map<String,Object> json_out =(Map<String, Object>) uocMessage.getArgs().get("json_info");
		List<ProcTaskRuleSpecVo> procTaskRuleSpecList = new ArrayList<ProcTaskRuleSpecVo>();
		if(oper_type.equals("1")){
			if(json_out.get("procTaskRuleSpec")!=null && !"".equals(json_out.get("procTaskRuleSpec"))){
				procTaskRuleSpecList =(List<ProcTaskRuleSpecVo>) json_out.get("procTaskRuleSpec");
				if(procTaskRuleSpecList ==null || procTaskRuleSpecList.size()==0){
					//循环调用UOC0065新增
					for(ProcTaskRuleDepartVo vo :procTaskRuleDepartVoList){
						Map<String, Object> map1= new HashMap<String, Object>();
						Map<String, Object> map2= new HashMap<String, Object>();
						String rule_id=vo.getRule_id().substring(vo.getRule_id().indexOf("_")+1, vo.getRule_id().length());
						map1.put("oper_type", "100");
						map1.put("rule_id", rule_id);
						map1.put("target_oper_no",operNo);
						map1.put("proportion", vo.getProportion());

						map2.put("procTaskRuleSpec", map1);
						String procJson=jsonToBeanServDu.mapToJson(map2).toString();
						ParaVo paraVo1 = new ParaVo();
						paraVo1.setJsession_id(jsession_id);
						paraVo1.setJson_info(procJson);
						paraVo1.setOper_type("100");
						UocMessage pocMessage =orderModServDu.syncTaskAssignRule(paraVo1);
						if(!"0000".equals(pocMessage.getRespCode())){
							return pocMessage;
						}
					}
				}else{
					//循环调用UOC0065更新
					for(ProcTaskRuleSpecVo vo :procTaskRuleSpecList){
						Map<String, Object> map1= new HashMap<String, Object>();
						Map<String, Object> map2= new HashMap<String, Object>();
						map1.put("oper_type", "101");
						map1.put("id", vo.getId());
						map1.put("rule_id", vo.getRule_id());
						map1.put("target_oper_no",operNo);
						map1.put("proportion", vo.getProportion());
						map1.put("flag","0");

						map2.put("procTaskRuleSpec", map1);
						String procJson=jsonToBeanServDu.mapToJson(map2).toString();
						ParaVo paraVo1 = new ParaVo();
						paraVo1.setJsession_id(jsession_id);
						paraVo1.setJson_info(procJson);
						paraVo1.setOper_type("101");
						UocMessage pocMessage1 =orderModServDu.syncTaskAssignRule(paraVo1);
						if(!"0000".equals(pocMessage1.getRespCode())){
							return pocMessage1;
						}
					}
				}
			}else{
				//循环调用UOC0065新增
				for(ProcTaskRuleDepartVo vo :procTaskRuleDepartVoList){
					Map<String, Object> map1= new HashMap<String, Object>();
					Map<String, Object> map2= new HashMap<String, Object>();
					String rule_id=vo.getRule_id().substring(vo.getRule_id().indexOf("_")+1, vo.getRule_id().length());
					map1.put("oper_type", "100");
					map1.put("rule_id", rule_id);
					map1.put("target_oper_no",operNo);
					map1.put("proportion", vo.getProportion());

					map2.put("procTaskRuleSpec", map1);
					String procJson=jsonToBeanServDu.mapToJson(map2).toString();
					ParaVo paraVo1 = new ParaVo();
					paraVo1.setJsession_id(jsession_id);
					paraVo1.setJson_info(procJson);
					paraVo1.setOper_type("100");
					UocMessage pocMessage =orderModServDu.syncTaskAssignRule(paraVo1);
					if(!"0000".equals(pocMessage.getRespCode())){
						return pocMessage;
					}
				}
			}



		}else  if(oper_type.equals("2")){
			if(json_out.get("procTaskRuleSpec")!=null && !"".equals(json_out.get("procTaskRuleSpec"))){
				procTaskRuleSpecList =(List<ProcTaskRuleSpecVo>) json_out.get("procTaskRuleSpec");
				if(procTaskRuleSpecList ==null || procTaskRuleSpecList.size()==0){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("proc_task_rule_spec无数据");
					return message;
				}else{
					//循环调用UOC0065更新
					for(ProcTaskRuleSpecVo vo :procTaskRuleSpecList){
						Map<String, Object> map1= new HashMap<String, Object>();
						Map<String, Object> map2= new HashMap<String, Object>();
						map1.put("oper_type", "101");
						map1.put("id", vo.getId());
						map1.put("rule_id", vo.getRule_id());
						map1.put("target_oper_no",operNo);
						map1.put("proportion", vo.getProportion());
						map1.put("flag","1");

						map2.put("procTaskRuleSpec", map1);
						String procJson=jsonToBeanServDu.mapToJson(map2).toString();
						ParaVo paraVo1 = new ParaVo();
						paraVo1.setJsession_id(jsession_id);
						paraVo1.setJson_info(procJson);
						paraVo1.setOper_type("101");
						UocMessage pocMessage1 =orderModServDu.syncTaskAssignRule(paraVo1);
						if(!"0000".equals(pocMessage1.getRespCode())){
							return pocMessage1;
						}
					}
				}
			}else{
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("proc_task_rule_spec无数据");
				return message;
			}


			if(quit_type.equals("1")){
				//查询人工任务列表，默认查询两个月时间
				String accept_time_end=DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
				logger.info("^^^^^^^^^^^^^UOC00079查询人工任务列表accept_time_end========"+accept_time_end);
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
				Date date5 = sdf3.parse(accept_time_end);
				//获取上个月的第一天时间
				Date date4 = new Date();
				Calendar calendar4 = Calendar.getInstance(); //得到日历
				calendar4.setTime(date5);//把当前时间赋给日历
				calendar4.add(calendar4.MONTH, -1);  //设置为前1月
				calendar4.set(calendar4.DAY_OF_MONTH,1);
				date4 = calendar4.getTime();   //得到前1月的时间
				String accept_time_start = sdf2.format(date4);    //格式化前1月的时间


				String page_no="1";
				String page_size="100";

				ArtificialTaskVo artificialTaskVo = new ArtificialTaskVo();
				logger.info("^^^^^^^^^^^^^UOC00079查询人工任务列表jsession_id========"+jsession_id);
				artificialTaskVo.setJsession_id(jsession_id);
				logger.info("^^^^^^^^^^^^^UOC00079查询人工任务列表operNo========"+operNo);
				artificialTaskVo.setAccept_oper_no(operNo);
				artificialTaskVo.setPage_no(page_no);
				artificialTaskVo.setPage_size(page_size);
				logger.info("^^^^^^^^^^^^^UOC00079查询人工任务列表accept_time_start========"+accept_time_start);
				artificialTaskVo.setAccept_time_start(accept_time_start);
				logger.info("^^^^^^^^^^^^^UOC00079查询人工任务列表accept_time_end========"+accept_time_end);
				artificialTaskVo.setAccept_time_end(accept_time_end);

				UocMessage arMesg=artificialTaskServDu.getArtificialTaskList(artificialTaskVo);
				if(!"0000".equals(arMesg.getRespCode())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("UOC0079查询人工任务列表出错");
					return message;
				}

				Map<String,Object> listMap= new HashMap<String,Object>();
				listMap=(Map<String, Object>) arMesg.getArgs().get("task_list");
				String totalNum=listMap.get("total_num").toString();
				logger.info("^^^^^^^^^^^^^UOC00079查询人工任务列表totalNum========"+totalNum);
				if(Integer.parseInt(totalNum)>100){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("UOC0079人工任务太多，请手动退出");
					return message;
				}

				if(Integer.parseInt(totalNum)>0){
					List<ProcInstTaskInstListVo> taskList = new ArrayList<ProcInstTaskInstListVo>();
					taskList=(List<ProcInstTaskInstListVo>) listMap.get("task_list");
					if(taskList!=null && taskList.size()>0){
						for(ProcInstTaskInstListVo vo:taskList){
							ParaVo paraVo2= new ParaVo();
							paraVo2.setJsession_id(jsession_id);
							logger.info("^^^^^^^^^^^^^UOC00079任务分配order_no========"+vo.getOrder_no());
							paraVo2.setOrder_no(vo.getOrder_no());
							paraVo2.setOper_type("200");//部门任务解锁

							//调用UOC0034任务分配
							UocMessage prMesg=artificialTaskServDu.createTaskAssignment(paraVo2);
							if(!"0000".equals(prMesg.getRespCode())){
								message.setRespCode(RespCodeContents.SERVICE_FAIL);
								message.setContent("UOC0079调用UOC0034任务分配出错");
								return message;
							}

						}
					}

				}

			}

		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("加入/退出部门任务组成功");
		return message;
	}


}
