package com.tydic.unicom.uoc.business.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.OrdModApp;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModAppPo;
import com.tydic.unicom.uoc.base.uocinst.po.GetServOrderNoPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderSimCardPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoDeliverOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderSimCardServ;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.common.vo.ProcModTacheVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ServiceOrderServDu;
import com.tydic.unicom.uoc.business.order.service.vo.GetServOrderNoVo;
import com.tydic.unicom.uoc.business.order.service.vo.ServOrderListVo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ChangeToArtificialServiceServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.CheckProcessServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.StartProcessServDu;
import com.tydic.unicom.uoc.service.code.interfaces.ProcTacheRetServDu;
import com.tydic.unicom.uoc.service.code.vo.ProcTacheRetVo;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.CrawlerActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcInstTaskInstServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheLoginServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModVo;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderProdMapHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderProdMapHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderBaseDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderProdMapServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderProdMapVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.CrawlerActivemqSendPo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class ServiceOrderServDuImpl implements ServiceOrderServDu{
	Logger logger = Logger.getLogger(ServiceOrderServDuImpl.class);
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private ProcModTacheServDu procModTacheServDu;
	@Autowired
	private ProcInstTaskInstServDu procInstTaskInstServDu;
	@Autowired
	private ProcModTacheLoginServDu procModTacheLoginServDu;
	@Autowired
	private OperServDu operServDu;
	@Autowired
	private InfoServiceOrderBaseDu infoServiceOrderBaseDu;
	@Autowired
	private OrdModFunctionServDu ordModFunctionServDu;
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	@Autowired
	private FindMyPersonalTaskServDu findMyPersonalTaskServDu;
	@Autowired
	private StartProcessServDu startProcessServDu;
	@Autowired
	private ChangeToArtificialServiceServDu changeToArtificialServiceServDu;
	@Autowired
	private CheckProcessServDu checkProcessServDu;
	@Autowired
	private RedisServiceServ redisServiceServ;
	@Autowired
	private ProcessCirculationServDu processCirculationServDu;
	@Autowired
	private InfoServiceOrderHisServDu infoServiceOrderHisServDu;
	@Autowired
	private InfoServiceOrderProdMapServDu infoServiceOrderProdMapServDu;
	@Autowired
	private InfoServiceOrderProdMapHisServDu infoServiceOrderProdMapHisServDu;
	@Autowired
	private ProcTacheRetServDu procTacheRetServDu;
	@Autowired
	private InfoServiceOrderServ infoServiceOrderServ;
	@Autowired
	private InfoServiceOrderSimCardServ infoServiceOrderSimCardServ;
	@Autowired
	private InfoDeliverOrderServ infoDeliverOrderServ;
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	@Autowired
	private CrawlerActivemqSendServDu crawlerActivemqSendServDu;

	private RespCodeContents respCode;

	private void getBeanDu(){
		if(jsonToBeanServDu == null){
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if(infoServiceOrderServDu == null){
			infoServiceOrderServDu = (InfoServiceOrderServDu) ToolSpring.getBean("InfoServiceOrderServDu");
		}
		if (infoSaleOrderServDu == null) {
			infoSaleOrderServDu = (InfoSaleOrderServDu) ToolSpring.getBean("InfoSaleOrderServDu");
		}
		if(procModTacheServDu == null){
			procModTacheServDu = (ProcModTacheServDu) ToolSpring.getBean("ProcModTacheServDu");
		}
		if(procInstTaskInstServDu == null){
			procInstTaskInstServDu = (ProcInstTaskInstServDu) ToolSpring.getBean("ProcInstTaskInstServDu");
		}
		if(procModTacheLoginServDu == null){
			procModTacheLoginServDu = (ProcModTacheLoginServDu) ToolSpring.getBean("ProcModTacheLoginServDu");
		}
		if(operServDu == null){
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if(infoServiceOrderBaseDu == null){
			infoServiceOrderBaseDu = (InfoServiceOrderBaseDu) ToolSpring.getBean("InfoServiceOrderBaseDu");
		}
		if(ordModFunctionServDu == null){
			ordModFunctionServDu = (OrdModFunctionServDu) ToolSpring.getBean("OrdModFunctionServDu");
		}
		if(abilityPlatformServDu == null){
			abilityPlatformServDu = (AbilityPlatformServDu) ToolSpring.getBean("AbilityPlatformServDu");
		}
		if(findMyPersonalTaskServDu == null){
			findMyPersonalTaskServDu = (FindMyPersonalTaskServDu) ToolSpring.getBean("FindMyPersonalTaskServDu");
		}
		if(startProcessServDu == null){
			startProcessServDu = (StartProcessServDu) ToolSpring.getBean("StartProcessServDu");
		}
		if(changeToArtificialServiceServDu == null){
			changeToArtificialServiceServDu = (ChangeToArtificialServiceServDu) ToolSpring.getBean("ChangeToArtificialServiceServDu");
		}
		if(checkProcessServDu == null){
			checkProcessServDu = (CheckProcessServDu) ToolSpring.getBean("CheckProcessServDu");
		}
		if(redisServiceServ == null){
			redisServiceServ = (RedisServiceServ) ToolSpring.getBean("RedisServiceServ");
		}
		if(processCirculationServDu == null){
			processCirculationServDu = (ProcessCirculationServDu) ToolSpring.getBean("ProcessCirculationServDu");
		}
		if(infoServiceOrderHisServDu == null){
			infoServiceOrderHisServDu = (InfoServiceOrderHisServDu) ToolSpring.getBean("InfoServiceOrderHisServDu");
		}
		if(infoServiceOrderProdMapServDu == null){
			infoServiceOrderProdMapServDu = (InfoServiceOrderProdMapServDu) ToolSpring.getBean("InfoServiceOrderProdMapServDu");
		}
		if(infoServiceOrderProdMapHisServDu == null){
			infoServiceOrderProdMapHisServDu = (InfoServiceOrderProdMapHisServDu) ToolSpring.getBean("InfoServiceOrderProdMapHisServDu");
		}
		if(procTacheRetServDu == null){
			procTacheRetServDu = (ProcTacheRetServDu) ToolSpring.getBean("ProcTacheRetServDu");
		}
		if(infoServiceOrderServ == null){
			infoServiceOrderServ = (InfoServiceOrderServ) ToolSpring.getBean("InfoServiceOrderServ");
		}
		if(infoServiceOrderSimCardServ == null){
			infoServiceOrderSimCardServ = (InfoServiceOrderSimCardServ) ToolSpring.getBean("InfoServiceOrderSimCardServ");
		}
		if(infoDeliverOrderServ == null){
			infoDeliverOrderServ = (InfoDeliverOrderServ) ToolSpring.getBean("InfoDeliverOrderServ");
		}
		if(infoSaleOrderServDu == null){
			infoSaleOrderServDu = (InfoSaleOrderServDu) ToolSpring.getBean("InfoSaleOrderServDu");
		}
		if (crawlerActivemqSendServDu == null) {
			crawlerActivemqSendServDu = (CrawlerActivemqSendServDu) ToolSpring.getBean("CrawlerActivemqSendServDu");
		}
	}
	/*
	 * UOC0008 服务订单处理，同步
	 */
	@Override
	public UocMessage syncServiceOrder(String all_json_info)throws Exception{
		UocMessage uocMessage = new UocMessage();
		ParaVo paraVo = new ParaVo();
		uocMessage = syncServiceOrder(paraVo,all_json_info);
		return uocMessage;
	}

	/*
	 * UOC0008 服务订单处理，异步
	 */
	@Override
	public UocMessage syncNoneServiceOrder(ParaVo paraVo)throws Exception{
		UocMessage uocMessage = new UocMessage();
		String all_json_info = "";
		uocMessage = syncServiceOrder(paraVo,all_json_info);
		return uocMessage;
	}

	//@Override
	@SuppressWarnings({ "unchecked", "static-access" })
	private  UocMessage syncServiceOrder(ParaVo paraVo,String all_json_info) throws Exception{
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		getBeanDu();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name= "";
		String oper_no = "";
		String role_id ="";

		String task_name = "";
		String task_code = "";
		String tache_code = "";
		String proc_inst_id = "";
		String oper_code = "";
		String mod_code="";
		boolean person_task_flag = false;

		String jsession_id = "";
		String oper_type = "";
		String serv_order_no = "";
		String flow_type = "";
		Map<String, String> action_code = null;
		String tache_code_new = "";
		String manual_flag = "";
		String all_json_info_para = paraVo.getAll_json_info();
		Map<String,Object> oper_info = new HashMap<String,Object>();
		if(all_json_info_para != null && !"".equals(all_json_info_para)){
			Map<String,Object> map = jsonToBeanServDu.jsonToMap(all_json_info_para);
			jsession_id = (String) map.get("jsession_id");
			oper_type =  (String) map.get("oper_type");
			serv_order_no =  (String) map.get("serv_order_no");
			flow_type =  (String) map.get("flow_type");
			if(map.containsKey("action_code") && map.get("action_code")!=null && map.get("action_code").equals("")){
				action_code =   (Map<String, String>) map.get("action_code");
			}
			tache_code_new =  (String) map.get("tache_code_new");
			manual_flag = (String) map.get("manual_flag");
		}
		if(all_json_info != null && !"".equals(all_json_info)){
			Map<String,Object> map = jsonToBeanServDu.jsonToMap(all_json_info);
			jsession_id = (String) map.get("jsession_id");
			oper_type =  (String) map.get("oper_type");
			serv_order_no =  (String) map.get("serv_order_no");
			flow_type =  (String) map.get("flow_type");
			if(map.containsKey("action_code") && map.get("action_code")!=null){
				action_code =   (Map<String, String>) map.get("action_code");
			}
			tache_code_new =  (String) map.get("tache_code_new");
			manual_flag = (String) map.get("manual_flag");
		}else{
			jsession_id = paraVo.getJsession_id();
			oper_type = paraVo.getOper_type();
			serv_order_no = paraVo.getServ_order_no();
			flow_type = paraVo.getFlow_type();
			tache_code_new = paraVo.getTache_code_new();
			action_code = paraVo.getAction_code();
			manual_flag = paraVo.getManual_flag();
		}
		 String json_info_ext ="{\"jsession_id\":\""+jsession_id+"\"}";
		if(jsession_id == null ||jsession_id.equals("")){
			uocMessage.setContent("失败:jsession_id为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if( flow_type == null || flow_type.equals("")){
			uocMessage.setContent("失败:flow_type为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(oper_type == null || oper_type.equals("")){
			uocMessage.setContent("失败:oper_type为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(serv_order_no == null || serv_order_no.equals("") ){
			uocMessage.setContent("失败:serv_order_no为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(manual_flag == null || manual_flag.equals("")){
			uocMessage.setContent("失败:manual_flag为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(action_code == null || action_code.equals("")){
			if(flow_type.equals("1")){
				uocMessage.setContent("失败:action_code为必传参数");
				uocMessage.setRespCode(respCode.PARAM_ERROR);
				return uocMessage;
			}
		}

		try {
			if(oper_type.equals("100")){
				//1、如果oper_type是100，调用BASE0008记录接口日志
				//2.  BASE0017
				if(manual_flag.equals("0")){
					UocMessage operMesg = operServDu.isLogin(jsession_id);
					if(operMesg != null){
						if("0000".equals(operMesg.getRespCode())){
							Map<String,Object> operArgsMap = operMesg.getArgs();
							oper_info = (Map<String, Object>) operArgsMap.get("oper_info");
							province_code = (String) oper_info.get("province_code");//
							depart_no = (String) oper_info.get("depart_no");
							depart_name = (String) oper_info.get("depart_name");
							oper_no = (String) oper_info.get("oper_no");
						}else if("1000".equals(operMesg.getRespCode())){
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							uocMessage.setContent("需要登陆");
							return uocMessage;
						}else if("1001".equals(operMesg.getRespCode())){
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							uocMessage.setContent("需要登陆");
							return uocMessage;
						}
					}else{
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						uocMessage.setContent("需要登陆");
						return uocMessage;
					}
				}
				//服务订单表的流程实例
				InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
				infoServiceOrderVo.setServ_order_no(serv_order_no);
				infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
				if(infoServiceOrderVo == null){
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>无订单实例<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					uocMessage.setContent("无订单实例");
					return uocMessage;
				}else{
					if(infoServiceOrderVo.getProc_inst_id()==null && !"".equals(infoServiceOrderVo.getProc_inst_id())){
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>流程实例ID为空<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						uocMessage.setContent("流程实例ID为空");
						return uocMessage;
					}else{
						proc_inst_id = infoServiceOrderVo.getProc_inst_id();
						oper_code = infoServiceOrderVo.getOper_code();
					}
					if(!infoServiceOrderVo.getOrder_state().equals("201")){
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>订单状态有误<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						uocMessage.setContent("订单状态有误");
						return uocMessage;
					}
				}
				/*
				 * 用BASE0012获取当前任务, 取出当前环节
				 */
				//BASE0012
				UocMessage uocMessagePersonalTask = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(proc_inst_id, serv_order_no);
				if(uocMessagePersonalTask == null){
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>当前环节为空<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					uocMessage.setContent("当前环节为空");
					return uocMessage;
				}else{
					//获取当前任务成功，以下流程有异常，可进入人工任务处理
					if("0000".equals(uocMessagePersonalTask.getRespCode())){
						Map<String,Object> argsPersonalTask = uocMessagePersonalTask.getArgs();
						task_code = (String) argsPersonalTask.get("current_task");
						tache_code = (String) argsPersonalTask.get("current_tache");
						task_name =(String) argsPersonalTask.get("current_task_name");
						//转人工任务处理标记
						person_task_flag = true;
					}else{
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>当前环节为空<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						uocMessage.setContent("当前环节为空");
						return uocMessage;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>操作异常,服务订单处理失败<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setContent("操作异常,服务订单处理失败");
			return uocMessage;
		}

		try {
			if(oper_type.equals("100")){
				//取环节配置表
				ProcModTacheVo procModTacheVo =new ProcModTacheVo();
				procModTacheVo.setTache_code(tache_code);
				ProcModTacheVo procModTacheVoRes = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);
				if(procModTacheVoRes != null){
					if(procModTacheVoRes.getIs_need_check().equals("1")){
						//BASE0002
						UocMessage procModTacheCheackUocMessage = infoServiceOrderBaseDu.checkServiceOrderTache(serv_order_no, procModTacheVoRes.getOper_type(), tache_code_new);
						if(procModTacheCheackUocMessage != null){
							if(!"0000".equals(procModTacheCheackUocMessage.getRespCode())){
								uocMessage.setContent(procModTacheCheackUocMessage.getContent());
//								if(person_task_flag){
//									oper_type = "101";
//									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+procModTacheCheackUocMessage.getContent()+",转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//									UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//									if(personTaskMgs != null){
//										logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+personTaskMgs.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//										uocMessage.setContent(procModTacheCheackUocMessage.getContent()+personTaskMgs.getContent());
//									}else{
//										uocMessage.setContent(procModTacheCheackUocMessage.getContent()+",转人工任务失败");
//									}
//								}
								return uocMessage;
							}else if("0000".equals(procModTacheCheackUocMessage.getRespCode())){
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+procModTacheCheackUocMessage.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							}
						}else{
							uocMessage.setContent("获取当前任务失败");
//							if(person_task_flag){
//								oper_type = "101";
//								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>获取当前任务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//								UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//								if(personTaskMgs != null){
//									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+personTaskMgs.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//									uocMessage.setContent("获取当前环节失败"+personTaskMgs.getContent());
//								}else{
//									uocMessage.setContent("获取当前环节失败"+",转人工任务失败");
//								}
//							}
							return uocMessage;
						}
					}
				}
				/*
				 * 4、如果oper_type是100，并且flow_type=0、1、3时调用BASE0022服务校验是否满足流转要求
				 */
				if(flow_type.equals("0") || flow_type.equals("1") || flow_type.equals("3")){
					//BASE0022
					UocMessage checkRes =checkProcessServDu.checkProcess(proc_inst_id, serv_order_no, "0", null);
					if(checkRes != null){
						if(!"0000".equals(checkRes.getRespCode())){
							uocMessage.setContent(checkRes.getContent());
//							if(person_task_flag){
//								oper_type = "101";
//								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+checkRes.getContent()+",转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//								UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//								if(personTaskMgs != null){
//									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+personTaskMgs.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//									uocMessage.setContent(checkRes.getContent()+personTaskMgs.getContent());
//								}else{
//									uocMessage.setContent(checkRes.getContent()+",转人工任务失败");
//								}
//							}
							return uocMessage;
						}else if("0000".equals(checkRes.getRespCode())){
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+checkRes.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						}
					}else{
						uocMessage.setContent("流转要求检验未通过");
//						if(person_task_flag){
//							oper_type = "101";
//							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>流转要求检验未通过,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//							UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//							if(personTaskMgs != null){
//								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+personTaskMgs.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//								uocMessage.setContent("流转要求检验异常"+personTaskMgs.getContent());
//							}else{
//								uocMessage.setContent("流转要求检验异常"+",转人工任务失败");
//							}
//						}
						return uocMessage;
					}
				}
				//屏蔽原有任务校验逻辑
			}
			//6、调用BASE0003订单模板获取服务取到出参模板，这里入参order_type填101，query_type也填101
			UocMessage uocMessageOrdModAttrOut = ordModFunctionServDu.queryOrdMod(serv_order_no, "101", "101");
			if(uocMessageOrdModAttrOut != null){
				if("0000".equals(uocMessageOrdModAttrOut.getRespCode())){
					 mod_code = (String) uocMessageOrdModAttrOut.getArgs().get("mod_code");
					 logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageOrdModAttrOut.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				}else{
//					uocMessage.setContent(uocMessageOrdModAttrOut.getContent());
//					if(person_task_flag){
//						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>获取订单出参模板失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						uocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//						if(personTaskMgs != null){
//							uocMessage.setContent(uocMessageOrdModAttrOut.getContent()+personTaskMgs.getContent());
//						}else{
//							uocMessage.setContent(uocMessageOrdModAttrOut.getContent()+",转人工任务失败");
//						}
//					}
//					return uocMessage;
				}
			}
//			else{
//				uocMessage.setContent("获取订单出参模板失败");
//				if(person_task_flag){
//					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>获取订单出参模板失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//					uocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//					if(personTaskMgs != null){
//						uocMessage.setContent("获取订单出参模板失败"+personTaskMgs.getContent());
//					}else{
//						uocMessage.setContent("获取订单出参模板失败"+",转人工任务失败");
//					}
//				}
//				return uocMessage;
//			}
			//7、接口传入的流程流转参数如果为3，直接跳转第11步
			/*
			 *11. 根据接口传入的流程流转参数不同，处理如下：
		1）传入0、1、3时调用BASE0016流程流转服务，传入1时要把操作编码也同时传入
		2）传入2时不处理
			 */
			UocMessage uocMessageProcessCircu = null;
			if(flow_type.equals("3")){
				//BASE0016
				uocMessageProcessCircu = processCirculationServDu.processCirculation(serv_order_no, "101",flow_type, null,json_info_ext);
				if(uocMessageProcessCircu != null){
					if("0000".equals(uocMessageProcessCircu.getRespCode())){
						uocMessage.setContent(uocMessageProcessCircu.getContent());
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageProcessCircu.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					}else{
						uocMessage.setContent(uocMessageProcessCircu.getContent());
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageProcessCircu.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						if(person_task_flag){
//							oper_type = "101";
//							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用流程流转服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//							UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//							if(personTaskMgs != null){
//								uocMessage.setContent(uocMessageProcessCircu.getContent()+personTaskMgs.getContent());
//							}else{
//								uocMessage.setContent(uocMessageProcessCircu.getContent()+",转人工任务失败");
//							}
//						}
						return uocMessage;
					}
				}else{
					uocMessage.setContent("调用流程流转服务失败");
//					if(person_task_flag){
//						oper_type = "101";
//						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用流程流转服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//						if(personTaskMgs != null){
//							uocMessage.setContent("调用流程流转服务失败"+personTaskMgs.getContent());
//						}else{
//							uocMessage.setContent("调用流程流转服务失败"+",转人工任务失败");
//						}
//					}
					return uocMessage;
				}
			}
			/*
			 * 8、根据上面是否取到模板，走不同分支：
			1）取到模板，则调用BASE0006根据订单模板出库服务，取出interface_type、interface_param_json以及json串，
			再通过这些信息调用BASE0018调用能力平台服务，然后再调用BASE0008服务记录能力平台接口日志；
			2）取不到模板时不做处理
			 */
			if(mod_code != null && !"".equals(mod_code)){
				//BASE0023
				String param_json = operServDu.loginShareParam(oper_info, jsession_id);
				//BASE0006订单模板出库服务
				UocMessage uocMessageOrdModOut = ordModFunctionServDu.outByOrdMod(serv_order_no, mod_code, "101",param_json);
				if(uocMessageOrdModOut != null){
					if("0000".equals(uocMessageOrdModOut.getRespCode())){
						InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
						infoServiceOrderVo.setServ_order_no(serv_order_no);
						infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
						InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
						infoSaleOrderVo.setSale_order_no(infoServiceOrderVo.getSale_order_no());
						infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
						if (infoSaleOrderVo == null) {
							logger.info(">>>>>>>>>>>>>>>无对应的销售订单<<<<<<<<<<<<<<<<<");
							uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
							uocMessage.setContent("无对应的销售订单");
							return uocMessage;
						}
						String callback_url = infoSaleOrderVo.getCallback_url();

						logger.info(">>>>>>>>>>>>>>>>>>" + uocMessageOrdModOut.getContent() + "<<<<<<<<<<<<<<<<<");
						Map<String,Object> argsOrdModOut = uocMessageOrdModOut.getArgs();
						String json_info_out = (String) argsOrdModOut.get("json_info");
						String interfaceType = (String) argsOrdModOut.get("interface_type");
						String interfaceJson = (String) argsOrdModOut.get("interface_param_json");

						UocMessage uocMessageAbilityPlat = new UocMessage();

						// 爬虫订单
						boolean isCrawlOrder = "CRAWLER".equals(infoSaleOrderVo.getAccept_system());
						boolean crawlCallBackFlag = false;

						// BASE0018 调用能力平台服务
						if (("700").equals(interfaceType)) {
							if (StringUtils.isNotEmpty(callback_url)) {
								Map<String, Object> callUrlJson = jsonToBeanServDu.jsonToMap(callback_url);
								if (callUrlJson != null && callUrlJson.containsKey("SERVICE_NAME")) {
									uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info_out, "701", interfaceJson,
											(String) callUrlJson.get("SERVICE_NAME"));
								} else if (isCrawlOrder) {
									// 爬虫订单回调
									uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info_out, "702", interfaceJson,
											callback_url);
								} else {
									// url不为json串
									uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info_out, interfaceType,
											interfaceJson, callback_url);
								}
							} else {
								uocMessageAbilityPlat.setRespCode("1");// 回调但URL为空,不报错
							}
						} else {
							uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info_out, interfaceType, interfaceJson,
									"");
						}

						if(uocMessageAbilityPlat != null){
							if("0000".equals(uocMessageAbilityPlat.getRespCode())){
								/*
								 * 9、如果有调用能力平台接口并且args节点下返回的信息不为空，
								 * 则调用BASE0003订单模板获取服务取到入参模板
								 * ，这里入参order_type填101，query_type填100， 如果查询到数据，
								 * 则调用BASE0005根据订单模板入库服务
								 * ，其中json串参数用args节点下的map信息转换成 json串
								 */
								String code = (String) uocMessageAbilityPlat.getArgs().get("code");
								if(code != null && code.equals("200")){
									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageAbilityPlat.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
									String argsAbilityPlat = (String) uocMessageAbilityPlat.getArgs().get("json_info");
									uocMessage.addArg("abilityPlat", argsAbilityPlat);

									if(argsAbilityPlat != null && !"".equals(argsAbilityPlat)){
										// 爬虫订单回调处理成功
										if (("700").equals(interfaceType) && isCrawlOrder) {
											crawlCallBackFlag = true;
										}

										//BASE0003
										UocMessage uocMessageOrdModAttrOutTwo = ordModFunctionServDu.queryOrdMod(serv_order_no, "100", "101");
										if(uocMessageOrdModAttrOutTwo != null){
											if("0000".equals(uocMessageOrdModAttrOutTwo.getRespCode())){
												logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageOrdModAttrOutTwo.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
												String mod_code_in = (String) uocMessageOrdModAttrOutTwo.getArgs().get("mod_code");
												if(mod_code_in != null && !"".equals(mod_code_in)){
													//BASE0005订单模板入库服务
													OrdModVo ordModVoOutPara = new OrdModVo();
													ordModVoOutPara.setMod_code(mod_code_in);
													ordModVoOutPara.setOrder_no(serv_order_no);
													ordModVoOutPara.setOrder_type("101");
													//其中json串参数用args节点下的map信息转换成 json串 ??
													ordModVoOutPara.setJson_in(argsAbilityPlat);
													ordModVoOutPara.setJsession_id(jsession_id);
													UocMessage uocMessageInsertByOrdMod = ordModFunctionServDu.insertByOrdMod(ordModVoOutPara);
													if(uocMessageInsertByOrdMod != null){
														if("0000".equals(uocMessageInsertByOrdMod.getRespCode())){
															logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageInsertByOrdMod.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
															Map<String,Object> argsInsertByOrdMod = uocMessageInsertByOrdMod.getArgs();
															String argsInsertByOrdModToJson = jsonToBeanServDu.mapToJson(argsInsertByOrdMod);
															uocMessage.addArg("argsInsertByOrdModToJson", argsInsertByOrdModToJson);
														}else{
															uocMessage.setContent(uocMessageInsertByOrdMod.getContent());
															logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageInsertByOrdMod.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
															return uocMessage;
														}
													}else{
														uocMessage.setContent("调用订单模板入库服务失败");
														return uocMessage;
													}

												}
											}
										}else{
											uocMessage.setContent("获取订单入参模板失败");
											return uocMessage;
										}

										/*
										 * 10、判断能力平台服务返回成功，
										 * 则通过当前环节、订单来源查询环节回调表取出接口url，模板，
										 * 若没有数据则不做后续处理
										 * 然后调用BASE0006服务，取出json串,interface_type等参数
										 * 最后调用BASE0018服务
										 */
										ProcTacheRetVo paramVo = new ProcTacheRetVo();
										Map<String, String> strMap = StrUtil.splitStringFromOrderNo(serv_order_no);
										paramVo.setArea_code(strMap.get("area_code"));
										paramVo.setProvince_code(province_code);
										paramVo.setTache_code(tache_code);
										List<ProcTacheRetVo> procTacheRetList = procTacheRetServDu.queryProcTacheRetByVo(paramVo);

										if (procTacheRetList != null && procTacheRetList.size() > 0) {
											// BASE0006订单模板出库服务
											UocMessage ordModOut = ordModFunctionServDu.outByOrdMod(serv_order_no, procTacheRetList.get(0)
													.getCall_ord_mod(), "101", "");
											if (ordModOut != null) {
												if ("0000".equals(ordModOut.getRespCode())) {
													logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>环节回调出库:" + ordModOut.getContent()
															+ "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
													Map<String, Object> ordModOutMap = ordModOut.getArgs();
													String json_out = (String) ordModOutMap.get("json_info");
													String type = (String) ordModOutMap.get("interface_type");
													String interface_param = (String) ordModOutMap.get("interface_param_json");
													// BASE0018 调用能力平台服务
													if (isCrawlOrder) {
														type = "702";
														JSONObject json = JSONObject.fromObject(json_out);
														if (json.get("proc_id") != null) {
															uocMessage.addArg("cb_order_no", json.get("proc_id").toString());
														}
													}
													UocMessage abilityMessage = abilityPlatformServDu.CallAbilityPlatform(json_out, type,
															interface_param, callback_url);
													if (abilityMessage != null) {
														if ("0000".equals(abilityMessage.getRespCode())) {
															String respcode = (String) abilityMessage.getArgs().get("code");
															logger.info("---------------环节回调能力平台返回code：" + respcode);
															// 爬虫订单回调失败
															if ((respcode == null || !respcode.equals("200")) && isCrawlOrder) {
																crawlerCallBack(json_out);
															}
														}
													}
												}
											}
										}

									} else if (("700").equals(interfaceType) && isCrawlOrder && !crawlCallBackFlag) {
										// 爬虫回调失败，写入回调队列
										crawlerCallBack(json_info_out);
									} else {
										uocMessage.setContent(uocMessageAbilityPlat.getContent());
										return uocMessage;
									}
								} else if (("700").equals(interfaceType) && isCrawlOrder && !crawlCallBackFlag) {
									// 爬虫回调失败，写入回调队列
									crawlerCallBack(json_info_out);
								} else {
									Map<String, Object> jsonMap = new HashMap<String, Object>();
									jsonMap=jsonToBeanServDu.jsonToMap(json_info_out);
									String argsAbilityPlat = (String) uocMessageAbilityPlat.getArgs().get("json_info");
									if (StringUtils.isNotEmpty(argsAbilityPlat)) {
										JSONObject json = JSONObject.fromObject(argsAbilityPlat);
										if (jsonMap.get("SERVICE_NAME") != null) {
											String serviceName = jsonMap.get("SERVICE_NAME").toString();
											if (StringUtils.isNotEmpty(argsAbilityPlat)) {
												if ("get_card_data".equals(serviceName) || "operDevInnet".equals(serviceName)
														|| "CancelOperInnet".equals(serviceName)) {
													uocMessage.setContent(json.get("message").toString());
												} else if ("modRealNameCustInfo".equals(serviceName)) {
													uocMessage.setContent(json.get("msg").toString());
												} else if ("checkFaceServ".equals(serviceName)) {
													uocMessage.setContent(json.get("return").toString());
												} else {
													uocMessage.setContent(json.toString());
												}
											}
										}else{
											uocMessage.setContent(json.containsKey("detail") ? json.getString("detail").toString() : json.toString());
										}
									}else{
										uocMessage.setContent(uocMessageAbilityPlat.getContent());
									}


									return uocMessage;
								}

							} else if (("700").equals(interfaceType) && isCrawlOrder && !crawlCallBackFlag) {
								// 爬虫回调失败，写入回调队列
								crawlerCallBack(json_info_out);
							} else if ("1".equals(uocMessageAbilityPlat.getRespCode())) {
								uocMessage.setContent("interface_type=700,callback_url为空");
								logger.info("--------interface_type=700,callback_url为空--------");
							} else {
								uocMessage.setContent(uocMessageAbilityPlat.getContent());
								return uocMessage;
							}
						} else if (("700").equals(interfaceType) && isCrawlOrder && !crawlCallBackFlag) {
							// 爬虫回调失败，写入回调队列
							crawlerCallBack(json_info_out);
						}else{
							uocMessage.setContent("调能力平台失败");
							return uocMessage;
						}

					}else{
						uocMessage.setContent(uocMessageOrdModOut.getContent());
						return uocMessage;
					}
				}else{
					uocMessage.setContent("调用订单模板出库失败");
					return uocMessage;
				}
			}
			//11、根据接口传入的流程流转参数不同，处理如下：
			//1）传入0、1、3时调用BASE0016流程流转服务，传入1时要把操作编码也同时传入
			//2）传入2时不处理
			if(flow_type.equals("0") || flow_type.equals("1")){
				uocMessageProcessCircu = processCirculationServDu.processCirculation(serv_order_no, "101", flow_type, action_code,json_info_ext);
				if(uocMessageProcessCircu != null){
					if("0000".equals(uocMessageProcessCircu.getRespCode())){
						uocMessage.setContent(uocMessageProcessCircu.getContent());
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageProcessCircu.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					}else{
						uocMessage.setContent(uocMessageProcessCircu.getContent());
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageProcessCircu.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						if(person_task_flag){
//							oper_type="101";
//							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用流程流转服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//							UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//							if(personTaskMgs != null){
//								uocMessage.setContent(uocMessageProcessCircu.getContent()+personTaskMgs.getContent());
//							}else{
//								uocMessage.setContent(uocMessageProcessCircu.getContent()+"转人工任务处理失败");
//							}
//						}
						return uocMessage;
					}
				}else{
					uocMessage.setContent("调用流程流转服务失败");
//					if(person_task_flag){
//						oper_type="101";
//						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用流程流转服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//						if(personTaskMgs != null){
//							uocMessage.setContent("调用流程流转服务失败"+personTaskMgs.getContent());
//						}else{
//							uocMessage.setContent("调用流程流转服务失败"+"转人工任务处理失败");
//						}
//					}
					return uocMessage;
				}
			}
		} catch (Exception e) {
			uocMessage.setContent("服务订单处理异常");
//			if(person_task_flag){
//				oper_type="101";
//				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>处理异常,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//				UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//				if(personTaskMgs != null){
//					uocMessage.setContent("服务订单处理异常"+personTaskMgs.getContent());
//				}else{
//					uocMessage.setContent("服务订单处理异常"+"转人工任务处理失败");
//				}
//			}
			e.printStackTrace();
			return uocMessage;
		}
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("服务订单处理成功!");
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单处理成功!<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return uocMessage;
	}

	//人工任务处理
//	private UocMessage personTask(Map<String,Object> oper_info,String serv_order_no,String oper_type,String task_property,String oper_code){
//		UocMessage uocMessage = new UocMessage();
//		uocMessage.setRespCode(respCode.SERVICE_FAIL);
//		uocMessage.setContent("人工任务处理失败");
//		try {
//			UocMessage msg = changeToArtificialServiceServDu.changeToArtificialService(oper_info,serv_order_no, oper_type, task_property,oper_code);//task_property 任务性质
//			if(msg != null){
//				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+msg.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//				if("0000".equals(msg.getRespCode())){
//					uocMessage.setRespCode(respCode.SUCCESS);
//					uocMessage.setContent(msg.getContent());
//				}else{
//					uocMessage.setContent(msg.getContent());
//					return uocMessage;
//				}
//			}else{
//				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>人工任务处理失败<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//				return uocMessage;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>人工任务处理异常<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//			return uocMessage;
//		}
//		return uocMessage;
//	}




	/**
	 * UOC0009 服务订单更改入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */

	public UocMessage changeServiceOrderForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();
		String jsession_id =(String) map.get("jsession_id");
		String serv_order_no =(String) map.get("serv_order_no");
		String oper_type =(String) map.get("oper_type");
		String json_info =(String) map.get("json_info");
		String flow_type =(String) map.get("flow_type");
		String manual_flag =(String) map.get("manual_flag");
		if(map.containsKey("action_code") && map.get("action_code")!=null){
		@SuppressWarnings("unchecked")
		Map<String,String> action_code=(Map<String, String>) map.get("action_code");
		vo.setAction_code(action_code);
		}
		vo.setJsession_id(jsession_id);
		vo.setServ_order_no(serv_order_no);
		vo.setOper_type(oper_type);
		vo.setJson_info(json_info);
		vo.setFlow_type(flow_type);
		vo.setManual_flag(manual_flag);
		UocMessage uocMessage =changeServiceOrder(vo);
		return uocMessage;
	}
	/*
	 * UOC0009 服务订单更改
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage changeServiceOrder(ParaVo paraVo)
			throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		getBeanDu();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name= "";
		String oper_no = "";
		String role_id = "";
		String jsession_id = paraVo.getJsession_id();
		String oper_type = paraVo.getOper_type();
		String serv_order_no = paraVo.getServ_order_no();
		String json_info = paraVo.getJson_info();
		String flow_type = paraVo.getFlow_type();
		String manual_flag = paraVo.getManual_flag();
		Map<String,String> action_code = paraVo.getAction_code();
		Map<String,Object> oper_info = new HashMap<String,Object>();

		String mod_code = "";
		String task_name = "";
		String task_code = "";
		String tache_code = "";
		String proc_inst_id = "";
		String oper_code = "";
		boolean person_task_flag = false;

		if(jsession_id == null ||jsession_id.equals("")){
			uocMessage.setContent("失败:jsession_id为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(json_info == null  || json_info.equals("")){
			uocMessage.setContent("失败:json_info为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if( flow_type == null || flow_type.equals("")){
			uocMessage.setContent("失败:flow_type为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(oper_type == null || oper_type.equals("")){
			uocMessage.setContent("失败:oper_type为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(serv_order_no == null || serv_order_no.equals("") ){
			uocMessage.setContent("失败:serv_order_no为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(manual_flag == null || manual_flag.equals("")){
			uocMessage.setContent("失败:manual_flag为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if(action_code == null || action_code.equals("")){
			if(flow_type.equals("1")){
				uocMessage.setContent("失败:action_code为必传参数");
				uocMessage.setRespCode(respCode.PARAM_ERROR);
				return uocMessage;
			}
		}
		/*
		 * 1、	oper_type如果为100，调用BASE0008记录接口日志
2、	oper_type如果为100，调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
3、	根据服务订单号查询服务订单表，取出相关信息，没有数据则报错， oper_type如果为100则判断当前状态必须为201
4、	如果oper_type是100，并且manual_flag=0，先调用BASE0012取出环节，再通过流程实例、环节查询人工任务实例表，
没数据则报错，有数据则再通过前面获取到的工号权限信息，以及环节查询环节对应角色表，判断当前工号是否有执行当前任务权限，没有权限直接报错
5、	如果oper_type是100，并且flow_type=0、1时，调用BASE0022服务校验是否满足流转要求
6、	调用BASE0003订单模板获取服务取出订单入参模板，这里入参order_type填101，query_type填100
7、	通过传入的json_info 和上面取出的订单模板信息调用BASE0005根据订单模板入库服务，订单类型填服务订单
8、根据接口传入的流程流转参数不同，处理如下：
1）传入0、1时调用BASE0016流程流转服务，传入1时要把操作编码也同时传入
2）传入2时不处理
9、如果前面有报错，且oper_type是100，并且manual_flag=1，则调用BASE0013转人工任务服务
		 */


		try {
			if(oper_type.equals("100")){
				//人工任务处理标志
				person_task_flag = true;

				//BASE0008
				if(manual_flag.equals("0")){
					//BASE0017
					UocMessage operMesg = operServDu.isLogin(jsession_id);
					if(operMesg != null){
						Map<String,Object> operArgsMap = operMesg.getArgs();
						@SuppressWarnings("unchecked")
						Map<String,Object> operInfoMap = (Map<String, Object>) operArgsMap.get("oper_info");
						province_code = (String) operInfoMap.get("province_code");//
						depart_no = (String) operInfoMap.get("depart_no");
						depart_name = (String) operInfoMap.get("depart_name");
						oper_no = (String) operInfoMap.get("oper_no");
					}else{
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						uocMessage.setContent("需要登陆");
						return uocMessage;
					}
				}
			}
			//3
			InfoServiceOrderVo infoServiceOrderVoPara =new InfoServiceOrderVo();
			infoServiceOrderVoPara.setServ_order_no(serv_order_no);
			InfoServiceOrderVo infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVoPara);
			if(infoServiceOrderVo == null){
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				uocMessage.setContent("服务订单为空");
				return uocMessage;
			}else if(infoServiceOrderVo.getProc_inst_id() == null){
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>无流程实例ID<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				uocMessage.setContent("无流程实例ID");
				return uocMessage;
			}else{
				proc_inst_id = infoServiceOrderVo.getProc_inst_id();
				oper_code = infoServiceOrderVo.getOper_code();
			}

			if(oper_type.equals("100")){
				if(!infoServiceOrderVo.getOrder_state().equals("201")){
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单状态有误<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					uocMessage.setContent("服务订单状态有误!");
					return uocMessage;
				}
				//屏蔽原有任务校验逻辑

				//5
				if(flow_type.equals("0") || flow_type.equals("1")){
					//BASE0022
					UocMessage checkRes =checkProcessServDu.checkProcess(proc_inst_id, serv_order_no, "0", null);
					if(checkRes != null){
						if(!"0000".equals(checkRes.getRespCode())){
							uocMessage.setContent("流转要求检验异常");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>流转要求检验异常<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//							if(person_task_flag){
//								oper_type = "101";
//								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>流转要求检验异常,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//								UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//								if(personTaskMgs != null){
//									uocMessage.setContent(checkRes.getContent()+personTaskMgs.getContent());
//								}else{
//									uocMessage.setContent(checkRes.getContent()+",人工任务处理失败");
//								}
//							}
							return uocMessage;
						}else if("0000".equals(checkRes.getRespCode())){
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+checkRes.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						}
					}else{
						uocMessage.setContent("流转要求检验异常");
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>流转要求检验异常<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						if(person_task_flag){
//							oper_type = "101";
//							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>流转要求检验异常,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//							UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//							if(personTaskMgs != null){
//								uocMessage.setContent("流转要求检验异常"+personTaskMgs.getContent());
//							}else{
//								uocMessage.setContent("流转要求检验异常"+",人工任务处理失败");
//							}
//						}
						return uocMessage;
					}
				}
			}
			//6 BASE0003
			UocMessage uocMessageOrdModAttrOut = ordModFunctionServDu.queryOrdMod(serv_order_no, "100", "101");
			if(uocMessageOrdModAttrOut != null){
				if("0000".equals(uocMessageOrdModAttrOut.getRespCode())){
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageOrdModAttrOut.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					mod_code = (String) uocMessageOrdModAttrOut.getArgs().get("mod_code");
				}else{
					uocMessage.setContent(uocMessageOrdModAttrOut.getContent());
//					if(person_task_flag){
//						oper_type = "101";
//						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>获取订单模板入参失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//						if(personTaskMgs != null){
//							uocMessage.setContent(uocMessageOrdModAttrOut.getContent()+personTaskMgs.getContent());
//						}else{
//							uocMessage.setContent(uocMessageOrdModAttrOut.getContent()+",人工任务处理失败");
//						}
//					}
					return uocMessage;
				}
			}else{
				uocMessage.setContent("获取订单模板入参失败");
//				if(person_task_flag){
//					oper_type = "101";
//					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>获取订单模板入参失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//					UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//					if(personTaskMgs != null){
//						uocMessage.setContent("获取订单模板入参失败"+personTaskMgs.getContent());
//					}else{
//						uocMessage.setContent("获取订单模板入参失败"+",人工任务处理失败");
//					}
//				}
				return uocMessage;
			}
			//7 //BASE0005
			OrdModVo ordModVoOutPara = new OrdModVo();
			ordModVoOutPara.setMod_code(mod_code);
			ordModVoOutPara.setOrder_no(serv_order_no);
			ordModVoOutPara.setOrder_type("101");
			ordModVoOutPara.setJson_in(json_info);
			ordModVoOutPara.setJsession_id(jsession_id);
			UocMessage uocMessageInsertByOrdMod = ordModFunctionServDu.insertByOrdMod(ordModVoOutPara);
			if(uocMessageInsertByOrdMod != null){
				if("0000".equals(uocMessageInsertByOrdMod.getRespCode())){
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageInsertByOrdMod.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				}else{
					uocMessage.setContent(uocMessageInsertByOrdMod.getContent());
//					if(person_task_flag){
//						oper_type = "101";
//						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用订单入库服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//						if(personTaskMgs != null){
//							uocMessage.setContent(uocMessageInsertByOrdMod.getContent()+personTaskMgs.getContent());
//						}else{
//							uocMessage.setContent(uocMessageInsertByOrdMod.getContent()+",人工任务处理失败");
//						}
//					}
					return uocMessage;
				}
			}else{
				uocMessage.setContent("调用订单入库服务失败");
//				if(person_task_flag){
//					oper_type = "101";
//					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用订单入库服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//					UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//					if(personTaskMgs != null){
//						uocMessage.setContent("调用订单入库服务失败"+personTaskMgs.getContent());
//					}else{
//						uocMessage.setContent("调用订单入库服务失败"+",人工任务处理失败");
//					}
//				}
				return uocMessage;
			}
			//8 BASE0016
			if(flow_type.equals("0") || flow_type.equals("1")){
				String json_info_ext ="{\"jsession_id\":\""+jsession_id+"\"}";
				UocMessage uocMessageProcessCircu = processCirculationServDu.processCirculation(serv_order_no, "101",flow_type, action_code,json_info_ext);
				if(uocMessageProcessCircu != null){
					if("0000".equals(uocMessageProcessCircu.getRespCode())){
						uocMessage.setContent(uocMessageProcessCircu.getContent());
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageProcessCircu.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					}else{
						uocMessage.setContent(uocMessageProcessCircu.getContent());
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+uocMessageProcessCircu.getContent()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						if(person_task_flag){
//							oper_type = "101";
//							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用流程流转服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//							UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//							if(personTaskMgs != null){
//								uocMessage.setContent(uocMessageProcessCircu.getContent()+personTaskMgs.getContent());
//							}else{
//								uocMessage.setContent(uocMessageProcessCircu.getContent()+",人工任务处理失败");
//							}
//						}
						return uocMessage;
					}
				}else{
					uocMessage.setContent("调用流程流转服务失败");
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用流程流转服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//					if(person_task_flag){
//						oper_type = "101";
//						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用流程流转服务失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//						UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//						if(personTaskMgs != null){
//							uocMessage.setContent("调用流程流转服务失败"+personTaskMgs.getContent());
//						}else{
//							uocMessage.setContent("调用流程流转服务失败"+",人工任务处理失败");
//						}
//					}
					return uocMessage;
				}
			}
		} catch (Exception e) {
			uocMessage.setContent("服务订单修改异常");
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单修改异常<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//			if(person_task_flag){
//				oper_type = "101";
//				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单修改失败,转人工任务处理<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//				UocMessage personTaskMgs = personTask(oper_info,serv_order_no, oper_type, "102",oper_code);
//				if(personTaskMgs != null){
//					uocMessage.setContent("服务订单修改异常"+personTaskMgs.getContent());
//				}else{
//					uocMessage.setContent("服务订单修改异常"+",人工任务处理失败");
//				}
//			}
			e.printStackTrace();
			return uocMessage;
		}
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单修改成功<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("服务订单修改成功");

		return uocMessage;
	}

	/**
	 * UOC0038 服务订单竣工率统计
	 * @param depart_no,oper_no,oper_code,prod_code,start_time,end_time
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getServiceOrderCompletionRate(ParaVo paraVo) throws Exception {
		UocMessage uocMessage=new UocMessage();

		if(StringUtils.isEmpty(paraVo.getJsession_id())){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}

		//1.根据jsession_id调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
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

		//2.需要根据条件查询服务订单竣工数(订单状态等于211，去历史库查历史表)，未竣工订单数(订单状态不等于211)，返回前台，根据产品条件查询还需要关联服务订单产品表
		String depart_no = StringUtils.isEmpty(paraVo.getDepart_no()) ? "" : paraVo.getDepart_no();
		String prod_code = StringUtils.isEmpty(paraVo.getProd_code()) ? "" : paraVo.getProd_code();
		String start_time = StringUtils.isEmpty(paraVo.getStart_time()) ? "" : paraVo.getStart_time();
		String end_time = StringUtils.isEmpty(paraVo.getEnd_time()) ? "" : paraVo.getEnd_time();
		String oper_no = StringUtils.isEmpty(paraVo.getOper_no()) ? "" : paraVo.getOper_no();
		String oper_code = StringUtils.isEmpty(paraVo.getOper_code()) ? "" : paraVo.getOper_code();


		//默认带上当前工号的省份跟地域信息，如果前台传入部门为空时，还要默认带上当前部门信息，若没传起始跟结束时间，则默认取当天0点到24点，并且增加限制起始跟结束时间不能跨月
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code = (String) oper_info.get("province_code");
		String area_code = (String) oper_info.get("area_code");
		depart_no = (String) (StringUtils.isEmpty(depart_no) ? oper_info.get("depart_no") : depart_no);
		start_time=StringUtils.isEmpty(start_time)?DateUtil.dayStartDate(new Date()):start_time;
		end_time=StringUtils.isEmpty(end_time)?DateUtil.dayEndDate(new Date()):end_time;
		Date startDate=DateUtil.strToDate(start_time, DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		Date endDate=DateUtil.strToDate(end_time, DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);

		//开始时间和结束时间跨月报错
		if(!DateUtil.isInSameMonth(startDate,endDate)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("开始时间和结束时间不能跨月");
			return uocMessage;
		}

		int other_num=0;//未竣工订单数
		int finish_num=0;//竣工订单数
		Map<String,String> otherOrderNo=new HashMap<String,String>();
		Map<String,String> finishOrderNo=new HashMap<String,String>();
		Map<String,String> otherHisOrderNo=new HashMap<String,String>();
		Map<String,String> finishHisOrderNo=new HashMap<String,String>();

		//查询服务订单
		InfoServiceOrderVo orderVo=new InfoServiceOrderVo ();
		orderVo.setArea_code(area_code);
		orderVo.setProvince_code(province_code);
		orderVo.setAccept_oper_no(oper_no);
		orderVo.setOper_code(oper_code);
		orderVo.setAccept_depart_no(depart_no);
		List<InfoServiceOrderVo> orderList=infoServiceOrderServDu.queryInfoServiceOrderByVo(orderVo);

		if(orderList==null){
			logger.info(">>>>>>>>>>>>>>>>服务订单表无数据<<<<<<<<<<<<<<<");
		}else{
			for(InfoServiceOrderVo infoServiceOrderVo:orderList){
				String create_time=infoServiceOrderVo.getCreate_time();
				Date createDate=DateUtil.strToDate(create_time, DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
				String order_state=infoServiceOrderVo.getOrder_state();
				if(createDate.before(endDate)&&createDate.after(startDate)){
					if (("211").equals(order_state)) {
						finish_num++;
						finishOrderNo.put("order_no", infoServiceOrderVo.getServ_order_no());
					}else{
						other_num++;
						otherOrderNo.put("order_no", infoServiceOrderVo.getServ_order_no());
					}
				}
			}
		}

		//查询服务订单历史表
		InfoServiceOrderHisVo orderHisVo=new InfoServiceOrderHisVo ();
		orderHisVo.setArea_code(area_code);
		orderHisVo.setProvince_code(province_code);
		orderHisVo.setAccept_oper_no(oper_no);
		orderHisVo.setOper_code(oper_code);
		orderHisVo.setAccept_depart_no(depart_no);
		List<InfoServiceOrderHisVo> orderHisList=infoServiceOrderHisServDu.queryInfoServiceOrderHisByVo(orderHisVo);
		if(orderHisList==null){
			logger.info(">>>>>>>>>>>>>>>>服务订单历史表无数据<<<<<<<<<<<<<<<");
		}else{
			for(InfoServiceOrderHisVo infoServiceOrderHisVo:orderHisList){
				String create_time=infoServiceOrderHisVo.getCreate_time();
				Date createDate=DateUtil.strToDate(create_time, DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
				String order_state=infoServiceOrderHisVo.getOrder_state();
				if(createDate.before(endDate)&&createDate.after(startDate)){
					if (("211").equals(order_state)) {
						finish_num++;
						finishHisOrderNo.put("order_no", infoServiceOrderHisVo.getServ_order_no());
					}else{
						other_num++;
						otherHisOrderNo.put("order_no", infoServiceOrderHisVo.getServ_order_no());
					}
				}
			}
		}

		//根据产品条件查询服务订单产品表
		if(StringUtils.isNotEmpty(prod_code)){
			finish_num=0;
			other_num=0;
			InfoServiceOrderProdMapVo prodVo=new InfoServiceOrderProdMapVo();
			prodVo.setArea_code(area_code);
			prodVo.setProvince_code(province_code);
			prodVo.setProd_code(prod_code);
			List<InfoServiceOrderProdMapVo> prodList=infoServiceOrderProdMapServDu.queryInfoServiceOrderProdMapByVo(prodVo);
			if(prodList==null){
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单产品表无数据，prod_code="+prod_code);
			}else{
				for(InfoServiceOrderProdMapVo infoServiceOrderProdMapVo:prodList){
					if(otherOrderNo.containsValue(infoServiceOrderProdMapVo.getServ_order_no())){
						other_num++;
					}
					if(finishOrderNo.containsValue(infoServiceOrderProdMapVo.getServ_order_no())){
						finish_num++;
					}
				}
			}

			InfoServiceOrderProdMapHisVo prodHisVo=new InfoServiceOrderProdMapHisVo();
			prodHisVo.setArea_code(area_code);
			prodHisVo.setProvince_code(province_code);
			prodHisVo.setProd_code(prod_code);
			List<InfoServiceOrderProdMapHisVo> prodHisList=infoServiceOrderProdMapHisServDu.queryInfoServiceOrderProdHisMapByVo(prodHisVo);
			if(prodHisList==null){
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单产品历史表无数据，prod_code="+prod_code);
			}else{
				for(InfoServiceOrderProdMapHisVo infoServiceOrderProdMapHisVo:prodHisList){
					if(otherHisOrderNo.containsValue(infoServiceOrderProdMapHisVo.getServ_order_no())){
						other_num++;
					}
					if(finishHisOrderNo.containsValue(infoServiceOrderProdMapHisVo.getServ_order_no())){
						finish_num++;
					}
				}
			}
		}


		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单竣工率统计成功<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("服务订单竣工率统计成功");
		uocMessage.addArg("other_num", other_num);
		uocMessage.addArg("finish_num", finish_num);
		uocMessage.addArg("total_num", other_num+finish_num);

		return uocMessage;
	}

	/**
	 * UOC0052 环节接口调用(支持重发)
	 * @param jsession_id
	 * @param serv_order_no
	 * @param tache_code
	 * @return
	 * @throws Exception
	 */
	@Override
	public UocMessage getOrderTacheCall(String jsession_id, String serv_order_no, String tache_code) throws Exception {
		UocMessage uocMessage = new UocMessage();

		if (StringUtils.isEmpty(jsession_id)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}

		if (StringUtils.isEmpty(serv_order_no)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("serv_order_no不能为空");
			return uocMessage;
		}

		// 1、调用BASE0008记录接口日志(暂缓)

		// 2、调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("需要登陆");
			return uocMessage;
		}
		if (("9999").equals(loginMessage.getArgs().get("code"))) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>能力平台调用失败<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			uocMessage.setContent("能力平台调用失败！");
			return uocMessage;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");

		// 3、 根据省份、地域、环节、业务以及获取类型等于101条件，查询订单模板应用表，查出对应的订单模板
		// 先根据服务订单号查询服务订单表
		InfoServiceOrderPo infoServiceOrderPo = new InfoServiceOrderPo();
		infoServiceOrderPo.setServ_order_no(serv_order_no);
		InfoServiceOrderPo infoServiceOrderRes = infoServiceOrderServ.getInfoServiceOrderByServOrderNo(infoServiceOrderPo);
		if (infoServiceOrderRes == null) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("服务订单表无数据");
			return uocMessage;

		}
		OrdModAppPo ordModAppPo = new OrdModAppPo();
		ordModAppPo.setProvince_code(infoServiceOrderRes.getProvince_code());
		ordModAppPo.setArea_code(infoServiceOrderRes.getArea_code());
		ordModAppPo.setOper_code(infoServiceOrderRes.getOper_code());
		ordModAppPo.setMod_used("101");
		ordModAppPo.setTache_id(tache_code);
		ordModAppPo.setTele_type(infoServiceOrderRes.getTele_type());

		UocMessage mes = redisServiceServ.queryDataFromRedis("queryOrdModAppOrderForm");
		String mod_code = "";
		String tele_type =ordModAppPo.getTele_type();
		if (!"0000".equals(mes.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>获取订单模板应用表缓存失败<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		} else {
			RedisData redisData = (RedisData) mes.getArgs().get("RedisDataResult");
			OrdModApp ordModAppPoRes =null;
			if(tele_type!=null && !"".equals(tele_type)){
				// 查询订单模板应用表
				 ordModAppPoRes = (OrdModApp) redisData.getMap().get(
					ordModAppPo.getProvince_code() + "|" + ordModAppPo.getArea_code() + "|" + ordModAppPo.getMod_used() + "|"
							+ ordModAppPo.getOper_code() + "|" + ordModAppPo.getTache_id()+"|"+tele_type);
				if (ordModAppPoRes == null) {
				ordModAppPoRes = (OrdModApp) redisData.getMap().get(
						ordModAppPo.getProvince_code() + "|" + "*" + "|" + ordModAppPo.getMod_used() + "|" + ordModAppPo.getOper_code()
								+ "|" + ordModAppPo.getTache_id()+"|"+tele_type);
				}

			}else{
			// 查询订单模板应用表
				 ordModAppPoRes = (OrdModApp) redisData.getMap().get(
					ordModAppPo.getProvince_code() + "|" + ordModAppPo.getArea_code() + "|" + ordModAppPo.getMod_used() + "|"
							+ ordModAppPo.getOper_code() + "|" + ordModAppPo.getTache_id());
				if (ordModAppPoRes == null) {
				ordModAppPoRes = (OrdModApp) redisData.getMap().get(
						ordModAppPo.getProvince_code() + "|" + "*" + "|" + ordModAppPo.getMod_used() + "|" + ordModAppPo.getOper_code()
								+ "|" + ordModAppPo.getTache_id());
				}
			}
			if (ordModAppPoRes == null) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>获取订单模板无数据<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			} else {
				mod_code = ordModAppPoRes.getMod_code();
			}
		}


		/*
		 * 4、调用BASE0023服务拼出param_json， 然后根据这个值再加上order_type=101，
		 * 调用BASE0006根据订单模板出库服务，取出interface_type、interface_param_json以及json串，
		 * 再通过这些信息调用BASE0018调用能力平台服务，然后再调用BASE0008服务记录能力平台接口日志；
		 */
		if (StringUtils.isNotEmpty(mod_code)) {
			// BASE0023
			String param_json = operServDu.loginShareParam(oper_info, jsession_id);
			// BASE0006订单模板出库服务
			UocMessage uocMessageOrdModOut = ordModFunctionServDu.outByOrdMod(serv_order_no, mod_code, "101", param_json);
			if (uocMessageOrdModOut != null) {
				if ("0000".equals(uocMessageOrdModOut.getRespCode())) {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageOrdModOut.getContent() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					Map<String, Object> argsOrdModOut = uocMessageOrdModOut.getArgs();
					String json_info_out = (String) argsOrdModOut.get("json_info");
					String interface_type = (String) argsOrdModOut.get("interface_type");
					String interface_param_json = (String) argsOrdModOut.get("interface_param_json");
					// BASE0018 调用能力平台服务
					UocMessage uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info_out, interface_type, interface_param_json, "");
					if (uocMessageAbilityPlat != null) {
						if ("0000".equals(uocMessageAbilityPlat.getRespCode())) {
							String code = (String) uocMessageAbilityPlat.getArgs().get("code");
							String argsAbilityPlat = (String) uocMessageAbilityPlat.getArgs().get("json_info");
							JSONObject json = JSONObject.fromObject(argsAbilityPlat);
							if (StringUtils.isNotEmpty(argsAbilityPlat)) {
								if (code != null && code.equals("200")) {
									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageAbilityPlat.getContent() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
									// 5、返回的信息挂在args节点下返回
									uocMessage.addArg("abilityPlatJson", argsAbilityPlat);

									/*
									 * 6、如果args节点下返回的信息不为空，则调用BASE0003订单模板获取服务取到入参模板
									 * ， 这里入参order_type填101，query_type填100，
									 * 如果查询到数据，则调用BASE0005根据订单模板入库服务，
									 * 其中json串参数用args节点下的map信息转换成 json串
									 */
									// BASE0003
									UocMessage uocMessageOrdModAttrOutTwo = ordModFunctionServDu.queryOrdMod(serv_order_no, "100", "101");
									if (uocMessageOrdModAttrOutTwo != null) {
										if ("0000".equals(uocMessageOrdModAttrOutTwo.getRespCode())) {
											logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageOrdModAttrOutTwo.getContent()
													+ "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
											String mod_code_in = (String) uocMessageOrdModAttrOutTwo.getArgs().get("mod_code");
											if (StringUtils.isNotEmpty(mod_code_in)) {
												// BASE0005订单模板入库服务
												OrdModVo ordModVoOutPara = new OrdModVo();
												ordModVoOutPara.setMod_code(mod_code_in);
												ordModVoOutPara.setOrder_no(serv_order_no);
												ordModVoOutPara.setOrder_type("101");
												// 其中json串参数用args节点下的map信息转换成json串
												ordModVoOutPara.setJson_in(argsAbilityPlat);
												ordModVoOutPara.setJsession_id(jsession_id);
												UocMessage uocMessageInsertByOrdMod = ordModFunctionServDu.insertByOrdMod(ordModVoOutPara);
												if (uocMessageInsertByOrdMod != null) {
													if ("0000".equals(uocMessageInsertByOrdMod.getRespCode())) {
														logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageInsertByOrdMod.getContent()
																+ "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
														Map<String, Object> argsInsertByOrdMod = uocMessageInsertByOrdMod.getArgs();
														String argsInsertByOrdModToJson = jsonToBeanServDu.mapToJson(argsInsertByOrdMod);
														uocMessage.addArg("ordModJson", argsInsertByOrdModToJson);
													} else {
														uocMessage.setContent(uocMessageInsertByOrdMod.getContent());
														logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageInsertByOrdMod.getContent()
																+ "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
														return uocMessage;
													}
													} else {
													uocMessage.setContent("调用订单模板入库服务失败");
														return uocMessage;
													}

												}
											}
									} else {
										uocMessage.setContent("获取订单入参模板失败");
										return uocMessage;
										}
								} else {
									uocMessage.setContent(json.containsKey("detail") ? json.getString("detail").toString() : json.toString());
									return uocMessage;
								}
							} else {
								uocMessage.setContent(uocMessageAbilityPlat.getContent());
								return uocMessage;
							}

						} else {
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageAbilityPlat.getContent() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							uocMessage.setContent(uocMessageAbilityPlat.getContent());
							return uocMessage;
						}
					} else {
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>能力平台调用失败<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						uocMessage.setContent("调能力平台失败");
						return uocMessage;
					}
				} else {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>能力平台调用失败<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					uocMessage.setContent(uocMessageOrdModOut.getContent());
					return uocMessage;
				}
			} else {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用订单模板出库失败<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				uocMessage.setContent("调用订单模板出库失败");
				return uocMessage;
			}
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("环节接口调用成功!");
		return uocMessage;
	}


	/**
	 * UOC0053 服务订单撤单率统计
	 * @param depart_no,oper_no,oper_code,prod_code,start_time,end_time
	 * @return
	 * @throws Exception
	 */
	@Override
	public UocMessage getServiceOrderCancelRate(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();

		if (StringUtils.isEmpty(paraVo.getJsession_id())) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}

		// 1.根据jsession_id调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
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

		// 2.需要根据条件查询服务订单表(注意包含历史表)撤单数(cancel_flag=0)，未撤销订单数(cancel_flag=1)，根据产品条件查询还需要关联服务订单产品表
		String depart_no = StringUtils.isEmpty(paraVo.getDepart_no()) ? "" : paraVo.getDepart_no();
		String prod_code = StringUtils.isEmpty(paraVo.getProd_code()) ? "" : paraVo.getProd_code();
		String start_time = StringUtils.isEmpty(paraVo.getStart_time()) ? "" : paraVo.getStart_time();
		String end_time = StringUtils.isEmpty(paraVo.getEnd_time()) ? "" : paraVo.getEnd_time();
		String oper_no = StringUtils.isEmpty(paraVo.getOper_no()) ? "" : paraVo.getOper_no();
		String oper_code = StringUtils.isEmpty(paraVo.getOper_code()) ? "" : paraVo.getOper_code();


		// 默认带上当前工号的省份跟地域信息，如果前台传入部门为空时，还要默认带上当前部门信息，若没传起始跟结束时间，则默认取当天0点到24点，并且增加限制起始跟结束时间不能跨月
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code = (String) oper_info.get("province_code");
		String area_code = (String) oper_info.get("area_code");
		depart_no = (String) (StringUtils.isEmpty(depart_no) ? oper_info.get("depart_no") : depart_no);
		start_time = StringUtils.isEmpty(start_time) ? DateUtil.dayStartDate(new Date()) : start_time;
		end_time = StringUtils.isEmpty(end_time) ? DateUtil.dayEndDate(new Date()) : end_time;
		Date startDate = DateUtil.strToDate(start_time, DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		Date endDate = DateUtil.strToDate(end_time, DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);

		// 开始时间和结束时间跨月报错
		if (!DateUtil.isInSameMonth(startDate, endDate)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("开始时间和结束时间不能跨月");
			return uocMessage;
		}

		int other_num = 0;// 未撤单数
		int cancel_num = 0;// 撤单数
		Map<String, String> otherOrderNo = new HashMap<String, String>();
		Map<String, String> cancelOrderNo = new HashMap<String, String>();
		Map<String, String> otherHisOrderNo = new HashMap<String, String>();
		Map<String, String> cancelHisOrderNo = new HashMap<String, String>();

		// 查询服务订单
		InfoServiceOrderVo orderVo = new InfoServiceOrderVo();
		orderVo.setArea_code(area_code);
		orderVo.setProvince_code(province_code);
		orderVo.setAccept_oper_no(oper_no);
		orderVo.setOper_code(oper_code);
		orderVo.setAccept_depart_no(depart_no);
		List<InfoServiceOrderVo> orderList = infoServiceOrderServDu.queryInfoServiceOrderByVo(orderVo);

		if (orderList == null) {
			logger.info(">>>>>>>>>>>>>>>>服务订单表无数据<<<<<<<<<<<<<<<");
		} else {
			for (InfoServiceOrderVo infoServiceOrderVo : orderList) {
				String create_time = infoServiceOrderVo.getCreate_time();
				Date createDate = DateUtil.strToDate(create_time, DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
				String cancel_flag = infoServiceOrderVo.getCancle_flag();
				if (createDate.before(endDate) && createDate.after(startDate)) {
					if (("0").equals(cancel_flag)) {
						cancel_num++;
						cancelOrderNo.put("order_no", infoServiceOrderVo.getServ_order_no());
					} else {
						other_num++;
						otherOrderNo.put("order_no", infoServiceOrderVo.getServ_order_no());
					}
				}
			}
		}

		// 查询服务订单历史表
		InfoServiceOrderHisVo orderHisVo = new InfoServiceOrderHisVo();
		orderHisVo.setArea_code(area_code);
		orderHisVo.setProvince_code(province_code);
		orderHisVo.setAccept_oper_no(oper_no);
		orderHisVo.setOper_code(oper_code);
		orderHisVo.setAccept_depart_no(depart_no);
		List<InfoServiceOrderHisVo> orderHisList = infoServiceOrderHisServDu.queryInfoServiceOrderHisByVo(orderHisVo);
		if (orderHisList == null) {
			logger.info(">>>>>>>>>>>>>>>>服务订单历史表无数据<<<<<<<<<<<<<<<");
		} else {
			for (InfoServiceOrderHisVo infoServiceOrderHisVo : orderHisList) {
				String create_time = infoServiceOrderHisVo.getCreate_time();
				Date createDate = DateUtil.strToDate(create_time, DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
				String cancel_flag = infoServiceOrderHisVo.getCancle_flag();
				if (createDate.before(endDate) && createDate.after(startDate)) {
					if (("0").equals(cancel_flag)) {
						cancel_num++;
						cancelHisOrderNo.put("order_no", infoServiceOrderHisVo.getServ_order_no());
					} else {
						other_num++;
						otherHisOrderNo.put("order_no", infoServiceOrderHisVo.getServ_order_no());
					}
				}
			}
		}

		// 根据产品条件查询服务订单产品表
		if (StringUtils.isNotEmpty(prod_code)) {
			cancel_num = 0;
			other_num = 0;
			InfoServiceOrderProdMapVo prodVo = new InfoServiceOrderProdMapVo();
			prodVo.setArea_code(area_code);
			prodVo.setProvince_code(province_code);
			prodVo.setProd_code(prod_code);
			List<InfoServiceOrderProdMapVo> prodList = infoServiceOrderProdMapServDu.queryInfoServiceOrderProdMapByVo(prodVo);
			if (prodList == null) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单产品表无数据，prod_code=" + prod_code);
			} else {
				for (InfoServiceOrderProdMapVo infoServiceOrderProdMapVo : prodList) {
					if (otherOrderNo.containsValue(infoServiceOrderProdMapVo.getServ_order_no())) {
						other_num++;
					}
					if (cancelOrderNo.containsValue(infoServiceOrderProdMapVo.getServ_order_no())) {
						cancel_num++;
					}
				}
			}

			InfoServiceOrderProdMapHisVo prodHisVo = new InfoServiceOrderProdMapHisVo();
			prodHisVo.setArea_code(area_code);
			prodHisVo.setProvince_code(province_code);
			prodHisVo.setProd_code(prod_code);
			List<InfoServiceOrderProdMapHisVo> prodHisList = infoServiceOrderProdMapHisServDu
					.queryInfoServiceOrderProdHisMapByVo(prodHisVo);
			if (prodHisList == null) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单产品历史表无数据，prod_code=" + prod_code);
			} else {
				for (InfoServiceOrderProdMapHisVo infoServiceOrderProdMapHisVo : prodHisList) {
					if (otherHisOrderNo.containsValue(infoServiceOrderProdMapHisVo.getServ_order_no())) {
						other_num++;
					}
					if (cancelHisOrderNo.containsValue(infoServiceOrderProdMapHisVo.getServ_order_no())) {
						cancel_num++;
					}
				}
			}
		}

		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>服务订单撤单率统计成功<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("服务订单撤单率统计成功");
		uocMessage.addArg("other_num", other_num);
		uocMessage.addArg("cancel_num", cancel_num);
		uocMessage.addArg("total_num", other_num + cancel_num);

		return uocMessage;
	}

	public UocMessage getServiceOrderNoForAbilityPlatform(String  json_in) throws Exception{
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		GetServOrderNoVo vo=new GetServOrderNoVo();

		if(map.containsKey("jsession_id")){
			String jsession_id=map.get("jsession_id").toString();
			vo.setJsession_id(jsession_id);
		}
		if(map.containsKey("sim_id")){
			String sim_id=map.get("sim_id").toString();
			vo.setSim_id(sim_id);

		}
		if(map.containsKey("acc_nbr")){
			String acc_nbr=map.get("acc_nbr").toString();
			vo.setAcc_nbr(acc_nbr);
		}
		if(map.containsKey("contact_tel")){
			String contact_tel=map.get("contact_tel").toString();
			vo.setContact_tel(contact_tel);
		}
		UocMessage message=getServiceOrderNo(vo);
		return message;
	}
	/*
	 * UOC0060获取服务订单号
	 */
	@Override
	public UocMessage getServiceOrderNo(GetServOrderNoVo vo) throws Exception {
		getBeanDu();
		UocMessage uocMessage = new UocMessage();
		if (StringUtils.isEmpty(vo.getJsession_id())) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(vo.getAcc_nbr())
				&& StringUtils.isEmpty(vo.getSim_id())
				&& StringUtils.isEmpty(vo.getContact_tel())) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("sim_id,contact_tel和acc_nbr不能同时为空");
			return uocMessage;
		}
		UocMessage loginMessage = operServDu.isLogin(vo.getJsession_id());

		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("需要登陆");
			return uocMessage;
		}

		if (("9999").equals(loginMessage.getArgs().get("code"))) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>能力平台调用失败<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			uocMessage.setContent("能力平台调用失败！");
			return uocMessage;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		vo.setArea_code((String) oper_info.get("area_code"));
		vo.setProvince_code((String) oper_info.get("province_code"));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();

		String nowDate = sdf.format(date);
		String month = nowDate.substring(4, 6).trim();
		String part_month = Integer.toString(Integer.parseInt(month));// 获取当前月份

		Calendar calendar = Calendar.getInstance();// 获取当前日历
		calendar.setTime(date);// 设置当前时间的日历
		calendar.add(calendar.MONTH, -1);// 设置前一个月
		Date lastDate = calendar.getTime();// 获取上个月的时间
		String lastMonth = sdf.format(lastDate).substring(4, 6).trim();
		String part_month1 = Integer.toString(Integer.parseInt(lastMonth));// 获取上个月的月份

		List<InfoServiceOrderSimCardPo> simList = new ArrayList<InfoServiceOrderSimCardPo>();

		GetServOrderNoPo po = new GetServOrderNoPo();

		// 订单查询
		po.setArea_code(vo.getArea_code());
		po.setPart_month(part_month1);
		po.setSim_id(vo.getSim_id());
		po.setAcc_nbr(vo.getAcc_nbr());
		po.setContact_tel(vo.getContact_tel());

		// 上月查出SIM卡号对应的订单列表
		List<InfoServiceOrderSimCardPo> simList1 = infoServiceOrderSimCardServ
					.queryInfoServiceOrderSimCardAllSimId(po);
		if (simList1 != null && simList1.size() > 0) {
			simList.addAll(simList1);
		}

		// 本月查出SIM卡号对应的订单列表
		po.setPart_month(part_month);
		List<InfoServiceOrderSimCardPo> simList2 = infoServiceOrderSimCardServ
					.queryInfoServiceOrderSimCardAllSimId(po);
		if (simList2 != null && simList2.size() > 0) {
			simList.addAll(simList2);
		}

		// 取联系电话
		List<ServOrderListVo> servOrderList = new ArrayList<ServOrderListVo>();
		InfoDeliverOrderPo	infoDeliverOrderPo = new InfoDeliverOrderPo();

		// 在支付订单表中查出contact_tel
		for (InfoServiceOrderSimCardPo sim:simList){
			ServOrderListVo servOrderListVo = new ServOrderListVo();

			InfoServiceOrderProdMapVo prodVo = new InfoServiceOrderProdMapVo();
			prodVo.setServ_order_no(sim.getServ_order_no());
			List<InfoServiceOrderProdMapVo> prodList = infoServiceOrderProdMapServDu.queryInfoServiceOrderProdMapByOrderNo(prodVo);

			servOrderListVo.setAcc_nbr(sim.getExt_field_1());
			servOrderListVo.setServ_order_no(sim.getServ_order_no());
			servOrderListVo.setSim_id(sim.getSim_id());
			servOrderListVo.setProd_code(prodList != null ? prodList.get(0).getProd_code() : "");

			infoDeliverOrderPo.setSale_order_no(sim.getSale_order_no());
			List<InfoDeliverOrderPo> infoDeliverOrderPoList = infoDeliverOrderServ
					.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderPo);

			if (infoDeliverOrderPoList != null) {
				String contact_tel = infoDeliverOrderPoList.get(0).getContact_tel();
				servOrderListVo.setContact_tel(contact_tel);
			}

			servOrderList.add(servOrderListVo);
		}

		// 返回数据
		uocMessage.setContent("获取服务订单集合成功");
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.addArg("serv_order_list", servOrderList);
		return uocMessage;
	}

	private UocMessage crawlerCallBack(String json_info) throws Exception {
		CrawlerActivemqSendPo sendPo = new CrawlerActivemqSendPo();
		sendPo.setJson_info(json_info);
		return crawlerActivemqSendServDu.sendActivemqMessage(sendPo, "crawlerCallBackFail");
	}

}
