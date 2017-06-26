package com.tydic.unicom.uoc.business.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderModServDu;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgAppVo;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgDefineVo;
import com.tydic.unicom.uoc.business.order.service.vo.CodeTaskPkgDesignVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleAssignVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleDepartVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcTaskRuleSpecVo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcModAppServDu;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.constants.Constant;
import com.tydic.unicom.uoc.service.mod.interfaces.CodeTaskPkgAppServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.CodeTaskPkgDefineServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.CodeTaskPkgDesignServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAppServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAttrCheckRuleServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAttrDefineServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModCheckRuleServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModDefineServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModParaFieldRelationServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModStateRuleServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModTacheRuleServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheBtnServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheLoginServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServiceServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAppVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrCheckRuleVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrDefineVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModCheckRuleVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModDefineVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModParaFieldRelationVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModStateRuleVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModTacheRuleVo;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskAssignRuleServDu;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskRuleDepartServDu;
import com.tydic.unicom.uoc.service.task.interfaces.ProcTaskRuleSpecServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;


public class OrderModServDuImpl implements OrderModServDu{
	Logger logger = Logger.getLogger(OrderModServDuImpl.class);
	@Autowired
	private OrdModStateRuleServDu ordModStateRuleServDu;
	@Autowired
	private OrdModTacheRuleServDu ordModTacheRuleServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private GetIdServDu getIdServDu;
	@Autowired
	private OrdModAttrCheckRuleServDu ordModAttrCheckRuleServDu;
	@Autowired
	private OrdModAttrDefineServDu ordModAttrDefineServDu;
	@Autowired
	private OrdModCheckRuleServDu ordModCheckRuleServDu;
	@Autowired
	private OrdModDefineServDu ordModDefineServDu;
	@Autowired
	private OrdModParaFieldRelationServDu ordModParaFieldRelationServDu;
	@Autowired
	private OrdModAppServDu ordModAppServDu;
	@Autowired
	private ProcModAppServDu procModAppServDu;
	@Autowired
	private ProcModTacheLoginServDu procModTacheLoginServDu;
	@Autowired
	private ProcModTacheServiceServDu procModTacheServiceServDu;
	@Autowired
	private ProcModTacheBtnServDu procModTacheBtnServDu;
	@Autowired
	private ProcModTacheServDu procModTacheServDu;
	@Autowired
	private OperServDu operServDu;
	@Autowired
	private RedisServiceServ redisServiceServ;
	private RespCodeContents respCode;
	@Autowired
	private CodeTaskPkgDesignServDu codeTaskPkgDesignServDu;
	@Autowired
	private CodeTaskPkgDefineServDu codeTaskPkgDefineServDu;
	@Autowired
	private CodeTaskPkgAppServDu codeTaskPkgAppServDu;
	@Autowired
	private ProcTaskAssignRuleServDu procTaskAssignRuleServDu;
	@Autowired
	private ProcTaskRuleSpecServDu procTaskRuleSpecServDu;
	@Autowired
	private ProcTaskRuleDepartServDu procTaskRuleDepartServDu;

	/**
	 * UOC0001	服务订单状态关系维护
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage syncOrdModStateRule(ParaVo paraVo)throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		try {
			//对应工号信息
			String province_code = "";
			@SuppressWarnings("unused")
			String area_code = "";
			area_code="83";
			province_code="83";
			@SuppressWarnings("unused")
			String depart_no = "";
			@SuppressWarnings("unused")
			String depart_name = "";
			@SuppressWarnings("unused")
			String oper_no = "";
			String jsession_id = paraVo.getJsession_id();
//			String type = paraVo.getType();
			String oper_type = paraVo.getOper_type();
			String json_info = paraVo.getJson_info();

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
			if(oper_type == null  || oper_type.equals("")){
				uocMessage.setContent("失败:oper_type为必传参数");
				uocMessage.setRespCode(respCode.PARAM_ERROR);
				return uocMessage;
			}
			getBeanDu();

			//BASE0017
			UocMessage operMesg = operServDu.isLogin(jsession_id);
			if("0000".equals(operMesg.getRespCode())){
				@SuppressWarnings("unchecked")
				Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
				province_code = (String) oper_info.get("province_code");
			}else{
				return operMesg;
			}


			List<OrdModStateRuleVo> list = new ArrayList<OrdModStateRuleVo>();
//			List<OrdModStateRuleVo> listState = null;
			Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

			Set<Entry<String, Object>> entries = mapObj.entrySet();
			if(entries.size() != 0){
				for (Entry<String, Object> entry : entries) {
					if(entry.getKey().equals(Constant.ORD_MOD_STATE_RULE)){
						@SuppressWarnings("unchecked")
						List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();

						for(Map<String,Object> mapVo :mapVos){
							OrdModStateRuleVo ordVo = new OrdModStateRuleVo();
							BeanUtils.populate(ordVo, mapVo);
							if(oper_type.equals(Constant.ADD)){
								String id = getIdServDu.getId("createLogId", province_code, "*","");
								ordVo.setId(id);
//								logger.info("生成服务订单号>>>>>>>>>>>>"+id);
								boolean result = ordModStateRuleServDu.create(ordVo);
								if(!result){
									uocMessage.setContent("新增失败");
									return uocMessage;
								}
							}
							else if(oper_type.equals(Constant.UPDATE)){
								boolean result=ordModStateRuleServDu.update(ordVo);
								if(!result){
									uocMessage.setContent("修改失败");
									return uocMessage;
								}

							}else if(oper_type.equals(Constant.DELETE)){
								boolean result=ordModStateRuleServDu.delete(ordVo);
								if(!result){
									uocMessage.setContent("删除失败");
									return uocMessage;
								}
							}
							list.add(ordVo);
						}
					}
				}
			}else{
				uocMessage.setContent("条件不足");
				return uocMessage;
			}
			Map<String,Object> json_out = new HashMap<String,Object>();
//			if(oper_type.equals(Constant.QUERY)){
//				int pageNo = (int) mapObj.get("pageNo");
//				int pageSize = (int) mapObj.get("pageSize");
//				int totalNume = 0;
//				OrdModStateRuleVo ordVo=new OrdModStateRuleVo();
//				Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
//				BeanUtils.populate(ordVo, mapVo);
//
//				listState = ordModStateRuleServDu.getOrdModStateRuleList(ordVo,pageNo,pageSize);
//				totalNume = ordModStateRuleServDu.queryOrdModStateRuleCount(ordVo);
//				if(listState == null){
//					uocMessage.setContent("查找结果为空");
//					uocMessage.setType(uocMessage.Type.success);
//					return uocMessage;
//				}
//
//				int totalPages = (totalNume + pageSize -1) / pageSize;
//
//				json_out.put("pageNo", pageNo);
//				json_out.put("pageSize", pageSize);
//				json_out.put("totalPages", totalPages);
//				json_out.put("totalNume", totalNume);
//				json_out.put("ordModStateRule", listState);
//				uocMessage.addArg("json_info", json_out);
//			}

			//BASE0007
			//uocMessage uocMessageLog = null;
			json_out.put("ordModStateRule", list);
			uocMessage.addArg("json_info", json_out);
			uocMessage.setRespCode(respCode.SUCCESS);
			uocMessage.setContent("操作成功");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			uocMessage.setRespCode(respCode.SYSTEM_EXCEPTION);
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
			uocExceptionMsg.setContent("操作记录失败");
			throw new UocException(uocExceptionMsg);

		}
		return uocMessage;
	}


	/**
	 * UOC0002	服务订单环节关系维护
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage syncOrdModTacheRule(ParaVo paraVo) throws Exception{
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		try {
			//对应工号信息
			String province_code = "";
			@SuppressWarnings("unused")
			String area_code = "";
			area_code="83";
			province_code="83";
			@SuppressWarnings("unused")
			String depart_no = "";
			@SuppressWarnings("unused")
			String depart_name = "";
			@SuppressWarnings("unused")
			String oper_no = "";

			String jsession_id = paraVo.getJsession_id();
			String oper_type = paraVo.getOper_type();
			String json_info = paraVo.getJson_info();

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
			if(oper_type == null  || oper_type.equals("")){
				uocMessage.setContent("失败:oper_type为必传参数");
				uocMessage.setRespCode(respCode.PARAM_ERROR);
				return uocMessage;
			}
			getBeanDu();
			//BASE0017
			UocMessage operMesg = operServDu.isLogin(jsession_id);
			if("0000".equals(operMesg.getRespCode())){
				@SuppressWarnings("unchecked")
				Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
				province_code = (String) oper_info.get("province_code");
			}else{
				return operMesg;
			}

			List<OrdModTacheRuleVo> list = new ArrayList<OrdModTacheRuleVo>();
			Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);
			Set<Entry<String, Object>> entries = mapObj.entrySet();
			if(entries != null){
				for (Entry<String, Object> entry : entries) {
					if(entry.getKey().equals(Constant.ORD_MOD_TACHE_RULE)){
						@SuppressWarnings("unchecked")
						List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
						for(Map<String,Object> mapVo :mapVos){
							OrdModTacheRuleVo ordVo = new OrdModTacheRuleVo();
							BeanUtils.populate(ordVo, mapVo);
							if(oper_type.equals(Constant.ADD)){
								String id = getIdServDu.getId("createLogId", province_code, "*","");
//								logger.info("生成服务订单号>>>>>>>>>>>>"+id);
								ordVo.setId(id);
								boolean result = ordModTacheRuleServDu.create(ordVo);
								if(!result){
									uocMessage.setContent("新增失败");
									return uocMessage;
								}
							}
							else if(oper_type.equals(Constant.UPDATE)){
								boolean result=ordModTacheRuleServDu.update(ordVo);
								if(!result){
									uocMessage.setContent("修改失败");
									return uocMessage;
								}


							}else if(oper_type.equals(Constant.DELETE)){
								boolean result=ordModTacheRuleServDu.delete(ordVo);
								if(!result){
									uocMessage.setContent("删除失败");
									return uocMessage;
								}

							}
							list.add(ordVo);
						}
					}

				}
			}else{
				uocMessage.setContent("条件不足");
				return uocMessage;
			}
//			List<OrdModTacheRuleVo> listTache = null;
//			if(oper_type.equals(Constant.QUERY)){
//				int pageNo = (int) mapObj.get("pageNo");
//				int pageSize = (int) mapObj.get("pageSize");
//				int totalNume = 0;
//				OrdModTacheRuleVo ordVo = new OrdModTacheRuleVo();
//				Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
//				BeanUtils.populate(ordVo, mapVo);
//
//				listTache = ordModTacheRuleServDu.queryOrdModTacheRuleList(ordVo, pageNo, pageSize);
//				totalNume = ordModTacheRuleServDu.queryOrdModTacheRuleListConut(ordVo);
//				if(listTache == null){
//					uocMessage.setContent("查找结果为空");
//					uocMessage.setType(uocMessage.Type.success);
//					return uocMessage;
//				}
//
//
//				int totalPages = (totalNume + (pageSize -1)) / pageSize;
//				Map<String,Object> json_out = new HashMap<String,Object>();
//				json_out.put("pageNo", pageNo);
//				json_out.put("pageSize", pageSize);
//				json_out.put("totalPages", totalPages);
//				json_out.put("totalNume", totalNume);
//				json_out.put("ordModTacheRule", listTache);
//				uocMessage.addArg("json_info", json_out);
//			}

			Map<String,Object> json_out = new HashMap<String,Object>();
			json_out.put("ordModTacheRule", list);
			//BASE0007
			//uocMessage uocMessageLog = null;
			uocMessage.addArg("json_info", json_out);
			uocMessage.setRespCode(respCode.SUCCESS);
			uocMessage.setContent("操作成功");


		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			uocMessage.setRespCode(respCode.SYSTEM_EXCEPTION);
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
			uocExceptionMsg.setContent("操作记录失败");
			throw new UocException(uocExceptionMsg);
		}
		return uocMessage;
	}

	/**
	 * UOC00012 订单模板维护
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage syncOrdMod(ParaVo paraVo) throws Exception{
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		try {
			//对应工号信息
			String province_code = "";
			@SuppressWarnings("unused")
			String area_code = "";
			area_code="83";
			province_code="83";
			@SuppressWarnings("unused")
			String depart_no = "";
			@SuppressWarnings("unused")
			String depart_name = "";
			@SuppressWarnings("unused")
			String oper_no = "";

			String jsession_id = paraVo.getJsession_id();
			String oper_type = paraVo.getOper_type();
			String json_info = paraVo.getJson_info();

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
			if(oper_type == null  || oper_type.equals("")){
				uocMessage.setContent("失败:oper_type为必传参数");
				uocMessage.setRespCode(respCode.PARAM_ERROR);
				return uocMessage;
			}
			getBeanDu();
			//BASE0017
			UocMessage operMesg = operServDu.isLogin(jsession_id);
			if("0000".equals(operMesg.getRespCode())){
				@SuppressWarnings("unchecked")
				Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
				province_code = (String) oper_info.get("province_code");
			}else{
				return operMesg;
			}


			Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);
			Set<Entry<String, Object>> entries = mapObj.entrySet();
			if(entries.size() == 0){
				uocMessage.setContent("条件不足");
				return uocMessage;
			}
			List<OrdModAttrCheckRuleVo> ordModAttrCheckRuleList = new ArrayList<OrdModAttrCheckRuleVo>();
			List<OrdModAttrDefineVo> ordModAttrDefineList = new ArrayList<OrdModAttrDefineVo>();
			List<OrdModCheckRuleVo> ordModCheckRuleList = new ArrayList<OrdModCheckRuleVo>();
			List<OrdModDefineVo> ordModDefineList = new ArrayList<OrdModDefineVo>();
			List<OrdModParaFieldRelationVo> ordModParaFieldRelationList = new ArrayList<OrdModParaFieldRelationVo>();

			for (Entry<String, Object> entry : entries) {
				if(entry.getKey().equals(Constant.ORD_MOD_ATTR_CHECK_RULE)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						OrdModAttrCheckRuleVo ordModAttrCheckRuleVo = new OrdModAttrCheckRuleVo();
						BeanUtils.populate(ordModAttrCheckRuleVo, mapVo);

						if(oper_type.equals(Constant.ADD)){
							String id = getIdServDu.getId("createLogId", province_code, "*","");
							ordModAttrCheckRuleVo.setId(id);
							boolean result = ordModAttrCheckRuleServDu.create(ordModAttrCheckRuleVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = ordModAttrCheckRuleServDu.update(ordModAttrCheckRuleVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = ordModAttrCheckRuleServDu.delete(ordModAttrCheckRuleVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						ordModAttrCheckRuleList.add(ordModAttrCheckRuleVo);
					}
				}
				if(entry.getKey().equals(Constant.ORD_MOD_ATTR_DEFINE)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						OrdModAttrDefineVo ordModAttrDefineVo = new OrdModAttrDefineVo();
						BeanUtils.populate(ordModAttrDefineVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							String id = getIdServDu.getId("createLogId",province_code, "*","");
							ordModAttrDefineVo.setId(id);
							boolean result = ordModAttrDefineServDu.create(ordModAttrDefineVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = ordModAttrDefineServDu.update(ordModAttrDefineVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = ordModAttrDefineServDu.delete(ordModAttrDefineVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						ordModAttrDefineList.add(ordModAttrDefineVo);
					}
				}
				if(entry.getKey().equals(Constant.ORD_MOD_CHECK_RULE)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						OrdModCheckRuleVo ordModCheckRuleVo = new OrdModCheckRuleVo();
						BeanUtils.populate(ordModCheckRuleVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							String id = getIdServDu.getId("createLogId", province_code, "*","");
							ordModCheckRuleVo.setId(id);
							boolean result = ordModCheckRuleServDu.create(ordModCheckRuleVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = ordModCheckRuleServDu.update(ordModCheckRuleVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = ordModCheckRuleServDu.delete(ordModCheckRuleVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						ordModCheckRuleList.add(ordModCheckRuleVo);
					}
				}
				if(entry.getKey().equals(Constant.ORD_MOD_DEFINE)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						OrdModDefineVo ordModDefineVo = new OrdModDefineVo();
						BeanUtils.populate(ordModDefineVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							boolean result = ordModDefineServDu.create(ordModDefineVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = ordModDefineServDu.update(ordModDefineVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = ordModDefineServDu.delete(ordModDefineVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						ordModDefineList.add(ordModDefineVo);
					}
				}
				if(entry.getKey().equals(Constant.ORD_MOD_PARA_FIELD_RELATION)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						OrdModParaFieldRelationVo ordModParaFieldRelationVo = new OrdModParaFieldRelationVo();
						BeanUtils.populate(ordModParaFieldRelationVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							String id = getIdServDu.getId("createLogId", province_code, "*","");
							ordModParaFieldRelationVo.setId(id);
							boolean result = ordModParaFieldRelationServDu.create(ordModParaFieldRelationVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = ordModParaFieldRelationServDu.update(ordModParaFieldRelationVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = ordModParaFieldRelationServDu.delete(ordModParaFieldRelationVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						ordModParaFieldRelationList.add(ordModParaFieldRelationVo);
					}
				}

			}
//			if(oper_type.equals(Constant.QUERY)){
//				List<OrdModAttrCheckRuleVo> ordModAttrCheckRuleList = null;
//				List<OrdModParaFieldRelationVo> ordModParaFieldRelationList = null;
//				List<OrdModAttrDefineVo> ordModAttrDefineList = null;
//				List<OrdModCheckRuleVo> ordModCheckRuleList = null;
//				List<OrdModDefineVo> ordModDefineList = null;
//				OrdModAttrCheckRuleVo ordModAttrCheckRuleVo = new OrdModAttrCheckRuleVo();
//				OrdModParaFieldRelationVo ordModParaFieldRelationVo = new OrdModParaFieldRelationVo();
//				OrdModAttrDefineVo ordModAttrDefineVo = new OrdModAttrDefineVo();
//				OrdModCheckRuleVo ordModCheckRuleVo = new OrdModCheckRuleVo();
//				OrdModDefineVo ordModDefineVo = new OrdModDefineVo();
//
//				int pageNo = (int) mapObj.get("pageNo");
//				int pageSize = (int) mapObj.get("pageSize");
//				int ordModAttrCheckRuleListCount = 0;
//				int ordModParaFieldRelationListCount = 0;
//				int ordModAttrDefineListCount = 0;
//				int ordModCheckRuleListCount = 0;
//				int ordModDefineListCount = 0;
//				@SuppressWarnings("unchecked")
//				Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
//				BeanUtils.populate(ordModAttrCheckRuleVo, mapVo);
//				BeanUtils.populate(ordModParaFieldRelationVo, mapVo);
//				BeanUtils.populate(ordModAttrDefineVo, mapVo);
//				BeanUtils.populate(ordModCheckRuleVo, mapVo);
//				BeanUtils.populate(ordModDefineVo, mapVo);
//
//				ordModAttrCheckRuleList = ordModAttrCheckRuleServDu.queryOrdModAttrCheckRuleList(ordModAttrCheckRuleVo, pageNo, pageSize);
//				ordModAttrCheckRuleListCount = ordModAttrCheckRuleServDu.queryOrdModAttrCheckRuleListCount(ordModAttrCheckRuleVo);
//
//				ordModParaFieldRelationList = ordModParaFieldRelationServDu.queryOrdModParaFieldRelationList(ordModParaFieldRelationVo, pageNo, pageSize);
//				ordModParaFieldRelationListCount = ordModParaFieldRelationServDu.queryOrdModParaFieldRelationListCount(ordModParaFieldRelationVo);
//
//				ordModAttrDefineList = ordModAttrDefineServDu.queryOrdModAttrDefineList(ordModAttrDefineVo, pageNo, pageSize);
//				ordModAttrDefineListCount = ordModAttrDefineServDu.queryOrdModAttrDefineListCount(ordModAttrDefineVo);
//
//				ordModCheckRuleList = ordModCheckRuleServDu.queryOrdModCheckRuleList(ordModCheckRuleVo, pageNo, pageSize);
//				ordModCheckRuleListCount = ordModCheckRuleServDu.queryOrdModCheckRuleListCount(ordModCheckRuleVo);
//
//				ordModDefineList = ordModDefineServDu.queryOrdModDefineList(ordModDefineVo, pageNo, pageSize);
//				ordModDefineListCount = ordModDefineServDu.queryOrdModDefineListCount(ordModDefineVo);
//
//				int totalPagesAttrCheck = (ordModAttrCheckRuleListCount + pageSize -1) / pageSize;
//				int totalPagesParaField = (ordModParaFieldRelationListCount + pageSize -1) / pageSize;
//				int totalPagesAttrDefine = (ordModAttrDefineListCount + pageSize -1) / pageSize;
//				int totalPagesCheck = (ordModCheckRuleListCount + pageSize -1) / pageSize;
//				int totalPagesDefine = (ordModDefineListCount + pageSize -1) / pageSize;
//
//				Map<String,Object> json_out = new HashMap<String,Object>();
//				json_out.put("pageNo", pageNo);
//				json_out.put("pageSize", pageSize);
//				if(ordModAttrCheckRuleListCount > 0){
//					json_out.put("totalPagesOrdModAttrCheckRule", totalPagesAttrCheck);
//					json_out.put("totalNumeOrdModAttrCheckRule", ordModAttrCheckRuleListCount);
//					json_out.put("ordModAttrCheckRule", ordModAttrCheckRuleList);
//				}
//				if(ordModParaFieldRelationListCount > 0){
//					json_out.put("totalPagesOrdModParaFieldRelation", totalPagesParaField);
//					json_out.put("totalNumeOrdModParaFieldRelation", ordModParaFieldRelationListCount);
//					json_out.put("ordModParaFieldRelation", ordModParaFieldRelationList);
//				}
//				if(ordModAttrDefineListCount > 0){
//					json_out.put("totalPagesOrdModAttrDefine", totalPagesAttrDefine);
//					json_out.put("totalNumeOrdModAttrDefine", ordModAttrDefineListCount);
//					json_out.put("ordModAttrDefine", ordModAttrDefineList);
//				}
//				if(ordModCheckRuleListCount > 0){
//					json_out.put("totalPagesOrdModCheckRule", totalPagesCheck);
//					json_out.put("totalNumeOrdModCheckRule", ordModCheckRuleListCount);
//					json_out.put("ordModCheckRule", ordModCheckRuleList);
//				}
//				if(ordModDefineListCount > 0){
//					json_out.put("totalPagesOrdModCheckRule", totalPagesDefine);
//					json_out.put("totalNumeOrdModCheckRule", ordModDefineListCount);
//					json_out.put("ordModDefine", ordModDefineList);
//				}
//				uocMessage.addArg("json_info", json_out);
//			}

			//BASE0007
			//uocMessage uocMessageLog = null;
			Map<String,Object> json_out = new HashMap<String,Object>();
			json_out.put("ordModAttrCheckRule", ordModAttrCheckRuleList);
			json_out.put("ordModParaFieldRelation", ordModParaFieldRelationList);
			json_out.put("ordModAttrDefine", ordModAttrDefineList);
			json_out.put("ordModCheckRule", ordModCheckRuleList);
			json_out.put("ordModDefine", ordModDefineList);
			uocMessage.addArg("json_info", json_out);
			uocMessage.setRespCode(respCode.SUCCESS);
			uocMessage.setContent("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			uocMessage.setRespCode(respCode.SYSTEM_EXCEPTION);
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
			uocExceptionMsg.setContent("操作记录失败");
			throw new UocException(uocExceptionMsg);
		}
		return uocMessage;
	}


	/**
	 * UOC0013 订单模板应用表维护
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage syncOrdModApp(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		try {
			//对应工号信息
			String province_code = "";
			@SuppressWarnings("unused")
			String area_code = "";
			area_code="83";
			province_code="83";
			@SuppressWarnings("unused")
			String depart_no = "";
			@SuppressWarnings("unused")
			String depart_name = "";
			@SuppressWarnings("unused")
			String oper_no = "";

			String jsession_id = paraVo.getJsession_id();
//			String type = paraVo.getType();
			String oper_type = paraVo.getOper_type();
			String json_info = paraVo.getJson_info();

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
			if(oper_type == null  || oper_type.equals("")){
				uocMessage.setContent("失败:oper_type为必传参数");
				uocMessage.setRespCode(respCode.PARAM_ERROR);
				return uocMessage;
			}
			getBeanDu();
			//BASE0017
			UocMessage operMesg = operServDu.isLogin(jsession_id);
			if("0000".equals(operMesg.getRespCode())){
				@SuppressWarnings("unchecked")
				Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
				province_code = (String) oper_info.get("province_code");
			}else{
				return operMesg;
			}

			Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

			Set<Entry<String, Object>> entries = mapObj.entrySet();
			if(entries == null){
				uocMessage.setContent("条件不足");
				return uocMessage;
			}
			List<OrdModAppVo> list = new ArrayList<OrdModAppVo>();
			for (Entry<String, Object> entry : entries) {
				if(entry.getKey().equals(Constant.ORD_MOD_APP)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						OrdModAppVo ordModAppVo = new OrdModAppVo();
						BeanUtils.populate(ordModAppVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							String id = getIdServDu.getId("createLogId",province_code, "*","");
							ordModAppVo.setId(id);
							boolean result = ordModAppServDu.create(ordModAppVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = ordModAppServDu.update(ordModAppVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = ordModAppServDu.delete(ordModAppVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						list.add(ordModAppVo);
					}
				}
			}
			Map<String,Object> json_out = new HashMap<String,Object>();
//			if(oper_type.equals(Constant.QUERY)){
//				List<OrdModAppVo> listApp = null;
//
//				int pageNo = (int) mapObj.get("pageNo");
//				int pageSize = (int) mapObj.get("pageSize");
//				int totalNume = 0;
//				OrdModAppVo ordVo = new OrdModAppVo();
//				@SuppressWarnings("unchecked")
//				Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
//				BeanUtils.populate(ordVo, mapVo);
//
//
//				listApp = ordModAppServDu.queryOrdModAppList(ordVo, pageNo, pageSize);
//				totalNume = ordModAppServDu.queryOrdModAppListCount(ordVo);
//				if(listApp == null){
//					uocMessage.setContent("查找结果为空");
//					uocMessage.setType(uocMessage.Type.success);
//					return uocMessage;
//				}
//
//
//				int totalPages = (totalNume + (pageSize -1)) / pageSize;
//				json_out.put("pageNo", pageNo);
//				json_out.put("pageSize", pageSize);
//				json_out.put("totalPages", totalPages);
//				json_out.put("totalNume", totalNume);
//				json_out.put("ordModApp", listApp);
//				uocMessage.addArg("json_info", json_out);
//			}
			//BASE0007
			//uocMessage uocMessageLog = null;
			json_out.put("ordModApp", list);
			uocMessage.addArg("json_info", json_out);
			uocMessage.setRespCode(respCode.SUCCESS);
			uocMessage.setContent("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			uocMessage.setRespCode(respCode.SYSTEM_EXCEPTION);
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
			uocExceptionMsg.setContent("操作记录失败");
			throw new UocException(uocExceptionMsg);
		}
		return uocMessage;
	}

	@SuppressWarnings("static-access")
	@Override
	public UocMessage syncCodeTaskPkg(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		String jsession_id = paraVo.getJsession_id();
		String oper_type = paraVo.getOper_type();
		String json_info = paraVo.getJson_info();

		if (jsession_id == null || jsession_id.equals("")) {
			uocMessage.setContent("失败:jsession_id为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if (json_info == null || json_info.equals("")) {
			uocMessage.setContent("失败:json_info为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if (oper_type == null || oper_type.equals("")) {
			uocMessage.setContent("失败:oper_type为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		getBeanDu();
		// BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if (!"0000".equals(operMesg.getRespCode())) {
			return operMesg;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) operMesg.getArgs().get("oper_info");
		String province_code = (String) oper_info.get("province_code");

		Map<String, Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		Set<Entry<String, Object>> entries = mapObj.entrySet();
		if (entries == null) {
			uocMessage.setContent("条件不足");
			return uocMessage;
		}

		List<CodeTaskPkgDefineVo> codeTaskPkgDefineList = new ArrayList<CodeTaskPkgDefineVo>();
		List<CodeTaskPkgDesignVo> codeTaskPkgDesignList = new ArrayList<CodeTaskPkgDesignVo>();
		List<CodeTaskPkgAppVo> codeTaskPkgAppList = new ArrayList<CodeTaskPkgAppVo>();

		for (Entry<String, Object> entry : entries) {
			if (entry.getKey().equals(Constant.CODE_TASK_PKG_DEFINE)) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
				for (Map<String, Object> mapVo : mapVos) {
					CodeTaskPkgDefineVo codeTaskPkgDefineVo = new CodeTaskPkgDefineVo();
					BeanUtils.populate(codeTaskPkgDefineVo, mapVo);
					if (oper_type.equals(Constant.ADD)) {
						boolean result = codeTaskPkgDefineServDu.create(codeTaskPkgDefineVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("新增失败");
							throw new UocException(uocExceptionMsg);
						}
					} else if (oper_type.equals(Constant.UPDATE)) {
						boolean result = codeTaskPkgDefineServDu.update(codeTaskPkgDefineVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("修改失败");
							throw new UocException(uocExceptionMsg);
						}
					} else if (oper_type.equals(Constant.DELETE)) {
						boolean result = codeTaskPkgDefineServDu.delete(codeTaskPkgDefineVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("删除失败");
							throw new UocException(uocExceptionMsg);
						}
							}
					codeTaskPkgDefineList.add(codeTaskPkgDefineVo);
						}
			}

			if (entry.getKey().equals(Constant.CODE_TASK_PKG_DESIGN)) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
				if (oper_type.equals(Constant.UPDATE)) {
					CodeTaskPkgDesignVo codeTaskPkgDesignVo = new CodeTaskPkgDesignVo();
					BeanUtils.populate(codeTaskPkgDesignVo, mapVos.get(0));
					boolean result = codeTaskPkgDesignServDu.delete(codeTaskPkgDesignVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("删除失败");
						throw new UocException(uocExceptionMsg);
					}
				}
				for (Map<String, Object> mapVo : mapVos) {
					CodeTaskPkgDesignVo codeTaskPkgDesignVo = new CodeTaskPkgDesignVo();
					BeanUtils.populate(codeTaskPkgDesignVo, mapVo);
					if (oper_type.equals(Constant.ADD)) {
						String id = getIdServDu.getId("createLogId", province_code, "*", "");
						codeTaskPkgDesignVo.setId(id);
						boolean result = codeTaskPkgDesignServDu.create(codeTaskPkgDesignVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("新增失败");
							throw new UocException(uocExceptionMsg);
						}
					} else if (oper_type.equals(Constant.UPDATE)) {
						String id = getIdServDu.getId("createLogId", province_code, "*", "");
						codeTaskPkgDesignVo.setId(id);
						boolean result = codeTaskPkgDesignServDu.create(codeTaskPkgDesignVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("修改失败");
							throw new UocException(uocExceptionMsg);
						}
					} else if (oper_type.equals(Constant.DELETE)) {
						boolean result = codeTaskPkgDesignServDu.delete(codeTaskPkgDesignVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("删除失败");
							throw new UocException(uocExceptionMsg);
						}
					}
					codeTaskPkgDesignList.add(codeTaskPkgDesignVo);
				}
			}

			if (entry.getKey().equals(Constant.CODE_TASK_PKG_APP)) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
				if (oper_type.equals(Constant.UPDATE)) {
					CodeTaskPkgAppVo codeTaskPkgAppVo = new CodeTaskPkgAppVo();
					BeanUtils.populate(codeTaskPkgAppVo, mapVos.get(0));
					boolean result = codeTaskPkgAppServDu.delete(codeTaskPkgAppVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("删除失败");
						throw new UocException(uocExceptionMsg);
					}
				}
				for (Map<String, Object> mapVo : mapVos) {
					CodeTaskPkgAppVo codeTaskPkgAppVo = new CodeTaskPkgAppVo();
					BeanUtils.populate(codeTaskPkgAppVo, mapVo);
					if (oper_type.equals(Constant.ADD)) {
						String id = getIdServDu.getId("createLogId", province_code, "*", "");
						codeTaskPkgAppVo.setId(id);
						boolean result = codeTaskPkgAppServDu.create(codeTaskPkgAppVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("新增失败");
							throw new UocException(uocExceptionMsg);
						}
					} else if (oper_type.equals(Constant.UPDATE)) {
						String id = getIdServDu.getId("createLogId", province_code, "*", "");
						codeTaskPkgAppVo.setId(id);
						boolean result = codeTaskPkgAppServDu.create(codeTaskPkgAppVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("修改失败");
							throw new UocException(uocExceptionMsg);
						}
					} else if (oper_type.equals(Constant.DELETE)) {
						boolean result = codeTaskPkgAppServDu.delete(codeTaskPkgAppVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("删除失败");
							throw new UocException(uocExceptionMsg);
						}
					}
					codeTaskPkgAppList.add(codeTaskPkgAppVo);
				}
			}
		}

		Map<String, Object> json_out = new HashMap<String, Object>();
		json_out.put("codeTaskPkgDefine", codeTaskPkgDefineList);
		json_out.put("codeTaskPkgDesign", codeTaskPkgDesignList);
		json_out.put("codeTaskPkgApp", codeTaskPkgAppList);
		uocMessage.addArg("json_info", json_out);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");

		return uocMessage;
	}

	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchCodeTaskPkg(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
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
		getBeanDu();
		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if (!"0000".equals(operMesg.getRespCode())) {
			return operMesg;
		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);


		Set<Entry<String, Object>> entries = mapObj.entrySet();
		if(entries.size() == 0){
			uocMessage.setContent("条件不足");
			return uocMessage;
		}
		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");

		int codeTaskPkgAppCount = 0;
		int codeTaskPkgDefineCount = 0;
		int codeTaskPkgDesignCount = 0;

		Map<String,Object> json_out = new HashMap<String,Object>();
		for (Entry<String, Object> entry : entries) {
			if(entry.getKey().equals(Constant.CODE_TASK_PKG_DEFINE)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				CodeTaskPkgDefineVo codeTaskPkgDefineVo = new CodeTaskPkgDefineVo();
				BeanUtils.populate(codeTaskPkgDefineVo, mapVo);
				List<CodeTaskPkgDefineVo> codeTaskPkgDefineList = codeTaskPkgDefineServDu.queryCodeTaskPkgDefine(codeTaskPkgDefineVo, pageNo, pageSize);
				codeTaskPkgDefineCount = codeTaskPkgDefineServDu.queryCodeTaskPkgDefineCount(codeTaskPkgDefineVo);
				int totalCodeTaskPkgDefine = (codeTaskPkgDefineCount+pageSize -1) / pageSize;
				if(codeTaskPkgDefineCount > 0){
					json_out.put("totalPagesCodeTaskPkgDefine", totalCodeTaskPkgDefine);
					json_out.put("totalNumeCodeTaskPkgDefine", codeTaskPkgDefineCount);
					json_out.put("codeTaskPkgDefine", codeTaskPkgDefineList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					return uocMessage;
				}
			}

			if(entry.getKey().equals(Constant.CODE_TASK_PKG_DESIGN)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				CodeTaskPkgDesignVo codeTaskPkgDesignVo = new CodeTaskPkgDesignVo();
				BeanUtils.populate(codeTaskPkgDesignVo, mapVo);
				List<CodeTaskPkgDesignVo> codeTaskPkgDesignList = codeTaskPkgDesignServDu.queryCodeTaskPkgDesign(codeTaskPkgDesignVo, pageNo, pageSize);
				codeTaskPkgDesignCount  = codeTaskPkgDesignServDu.queryCodeTaskPkgDesignCount(codeTaskPkgDesignVo);
				int totalCodeTaskPkgDesign = (codeTaskPkgDesignCount + pageSize -1) /pageSize;
				if(codeTaskPkgDesignCount > 0){
					json_out.put("totalPagesCodeTaskPkgDesign", totalCodeTaskPkgDesign);
					json_out.put("totalNumeCodeTaskPkgDesign", codeTaskPkgDesignCount);
					json_out.put("codeTaskPkgDesign", codeTaskPkgDesignList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					return uocMessage;
				}
			}

			if(entry.getKey().equals(Constant.CODE_TASK_PKG_APP)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				CodeTaskPkgAppVo codeTaskPkgAppVo = new CodeTaskPkgAppVo();
				BeanUtils.populate(codeTaskPkgAppVo, mapVo);
				List<CodeTaskPkgAppVo> codeTaskPkgAppList = codeTaskPkgAppServDu.queryCodeTaskPkgApp(codeTaskPkgAppVo, pageNo, pageSize);
				codeTaskPkgAppCount = codeTaskPkgAppServDu.queryCodeTaskPkgAppCount(codeTaskPkgAppVo);
				int totalCodeTaskPkgApp = (codeTaskPkgAppCount + pageSize -1) / pageSize;
				if(codeTaskPkgAppCount > 0){
					json_out.put("totalPagesCodeTaskPkgApp", totalCodeTaskPkgApp);
					json_out.put("totalNumeCodeTaskPkgApp", codeTaskPkgAppCount);
					json_out.put("codeTaskPkgApp", codeTaskPkgAppList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}
			}
		}
		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);

		uocMessage.addArg("json_info", json_out);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		return uocMessage;
	}


	/**
	 * UOC0026	服务订单状态关系查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchOrdModStateRuleList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

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
		getBeanDu();
		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if("0000".equals(operMesg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		}else{
			return operMesg;
		}

		List<OrdModStateRuleVo> listState = null;
		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		OrdModStateRuleVo ordVo=new OrdModStateRuleVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);

		listState = ordModStateRuleServDu.getOrdModStateRuleList(ordVo,pageNo,pageSize);
		totalNume = ordModStateRuleServDu.queryOrdModStateRuleCount(ordVo);
		if(listState == null){
			uocMessage.setContent("查找结果为空");
			uocMessage.setRespCode(respCode.SUCCESS);
			return uocMessage;
		}


		int totalPages = (totalNume + pageSize -1) / pageSize;
		Map<String,Object> json_out = new HashMap<String,Object>();
		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);
		json_out.put("totalPages", totalPages);
		json_out.put("totalNume", totalNume);
		json_out.put("ordModStateRule", listState);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}

	/**
	 * UOC0027	服务订单环节关系查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchOrdModTacheRuleList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

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
		getBeanDu();
		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if("0000".equals(operMesg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		}else{
			return operMesg;
		}

		List<OrdModTacheRuleVo> listTache = new ArrayList<OrdModTacheRuleVo>();
		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		OrdModTacheRuleVo ordVo = new OrdModTacheRuleVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);
//		String oper_code_a = (String) mapVo.get("oper_code_a");
//		String tache_code_a = (String) mapVo.get("tache_code_a");
//		ordVo.setOper_code_a(oper_code_a);
//		ordVo.setTache_code_a(tache_code_a);

		listTache = ordModTacheRuleServDu.queryOrdModTacheRuleList(ordVo, pageNo, pageSize);
		totalNume = ordModTacheRuleServDu.queryOrdModTacheRuleListConut(ordVo);
		if(listTache == null){
			uocMessage.setContent("查找结果为空");
			uocMessage.setRespCode(respCode.SUCCESS);
			return uocMessage;
		}

		int totalPages = (totalNume + (pageSize -1)) / pageSize;
		Map<String,Object> json_out = new HashMap<String,Object>();
		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);
		json_out.put("totalPages", totalPages);
		json_out.put("totalNume", totalNume);
		json_out.put("ordModTacheRule", listTache);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}

	/**
	 * UOC0028	订单模板查询查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchOrdMod(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

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
		getBeanDu();
		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if("0000".equals(operMesg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		}else{
			return operMesg;
		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);


		Set<Entry<String, Object>> entries = mapObj.entrySet();
		if(entries.size() == 0){
			uocMessage.setContent("条件不足");
			return uocMessage;
		}
		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");

		int ordModAttrCheckRuleListCount = 0;
		int ordModParaFieldRelationListCount = 0;
		int ordModAttrDefineListCount = 0;
		int ordModCheckRuleListCount = 0;
		int ordModDefineListCount = 0;
		Map<String,Object> json_out = new HashMap<String,Object>();
		for (Entry<String, Object> entry : entries) {
			if(entry.getKey().equals(Constant.ORD_MOD_ATTR_CHECK_RULE)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				OrdModAttrCheckRuleVo ordModAttrCheckRuleVo = new OrdModAttrCheckRuleVo();
				BeanUtils.populate(ordModAttrCheckRuleVo, mapVo);
				List<OrdModAttrCheckRuleVo> ordModAttrCheckRuleList = ordModAttrCheckRuleServDu.queryOrdModAttrCheckRuleList(ordModAttrCheckRuleVo, pageNo, pageSize);
				ordModAttrCheckRuleListCount = ordModAttrCheckRuleServDu.queryOrdModAttrCheckRuleListCount(ordModAttrCheckRuleVo);
				int totalPagesAttrCheck = (ordModAttrCheckRuleListCount + pageSize -1) / pageSize;
				if(ordModAttrCheckRuleListCount > 0){
					json_out.put("totalPagesOrdModAttrCheckRule", totalPagesAttrCheck);
					json_out.put("totalNumeOrdModAttrCheckRule", ordModAttrCheckRuleListCount);
					json_out.put("ordModAttrCheckRule", ordModAttrCheckRuleList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}

			}
			if(entry.getKey().equals(Constant.ORD_MOD_ATTR_DEFINE)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				OrdModAttrDefineVo ordModAttrDefineVo = new OrdModAttrDefineVo();
				BeanUtils.populate(ordModAttrDefineVo, mapVo);
				List<OrdModAttrDefineVo> ordModAttrDefineList = ordModAttrDefineServDu.queryOrdModAttrDefineList(ordModAttrDefineVo, pageNo, pageSize);
				ordModAttrDefineListCount = ordModAttrDefineServDu.queryOrdModAttrDefineListCount(ordModAttrDefineVo);
				int totalPagesAttrDefine = (ordModAttrDefineListCount + pageSize -1) / pageSize;
				if(ordModAttrDefineListCount > 0){
					json_out.put("totalPagesOrdModAttrDefine", totalPagesAttrDefine);
					json_out.put("totalNumeOrdModAttrDefine", ordModAttrDefineListCount);
					json_out.put("ordModAttrDefine", ordModAttrDefineList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}
			}
			if(entry.getKey().equals(Constant.ORD_MOD_CHECK_RULE)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				OrdModCheckRuleVo ordModCheckRuleVo = new OrdModCheckRuleVo();
				BeanUtils.populate(ordModCheckRuleVo, mapVo);
				List<OrdModCheckRuleVo> ordModCheckRuleList = ordModCheckRuleServDu.queryOrdModCheckRuleList(ordModCheckRuleVo, pageNo, pageSize);
				ordModCheckRuleListCount = ordModCheckRuleServDu.queryOrdModCheckRuleListCount(ordModCheckRuleVo);
				int totalPagesCheck = (ordModCheckRuleListCount + pageSize -1) / pageSize;
				if(ordModCheckRuleListCount > 0){
					json_out.put("totalPagesOrdModCheckRule", totalPagesCheck);
					json_out.put("totalNumeOrdModCheckRule", ordModCheckRuleListCount);
					json_out.put("ordModCheckRule", ordModCheckRuleList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}
			}
			if(entry.getKey().equals(Constant.ORD_MOD_DEFINE)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				OrdModDefineVo ordModDefineVo = new OrdModDefineVo();
				BeanUtils.populate(ordModDefineVo, mapVo);
				List<OrdModDefineVo> ordModDefineList = ordModDefineServDu.queryOrdModDefineList(ordModDefineVo, pageNo, pageSize);
				ordModDefineListCount = ordModDefineServDu.queryOrdModDefineListCount(ordModDefineVo);
				int totalPagesDefine = (ordModDefineListCount + pageSize -1) / pageSize;
				if(ordModDefineListCount > 0){
					json_out.put("totalPagesOrdModDefine", totalPagesDefine);
					json_out.put("totalNumeOrdModDefine", ordModDefineListCount);
					json_out.put("ordModDefine", ordModDefineList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}
			}
			if(entry.getKey().equals(Constant.ORD_MOD_PARA_FIELD_RELATION)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				OrdModParaFieldRelationVo ordModParaFieldRelationVo = new OrdModParaFieldRelationVo();
				BeanUtils.populate(ordModParaFieldRelationVo, mapVo);
				List<OrdModParaFieldRelationVo> ordModParaFieldRelationList = ordModParaFieldRelationServDu.queryOrdModParaFieldRelationList(ordModParaFieldRelationVo, pageNo, pageSize);
				ordModParaFieldRelationListCount = ordModParaFieldRelationServDu.queryOrdModParaFieldRelationListCount(ordModParaFieldRelationVo);
				int totalPagesParaField = (ordModParaFieldRelationListCount + pageSize -1) / pageSize;
				if(ordModParaFieldRelationListCount > 0){
					json_out.put("totalPagesOrdModParaFieldRelation", totalPagesParaField);
					json_out.put("totalNumeOrdModParaFieldRelation", ordModParaFieldRelationListCount);
					json_out.put("ordModParaFieldRelation", ordModParaFieldRelationList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}
			}
		}

		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);

		uocMessage.addArg("json_info", json_out);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		return uocMessage;
	}



	/**
	 * UOCXXXX	订单参数与数据库表字段关系表查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchOrdModParaFieldRelationList(ParaVo paraVo)
			throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

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
		getBeanDu();
		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if("0000".equals(operMesg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		}else{
			return operMesg;
		}

		List<OrdModParaFieldRelationVo> listField = null;
		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		OrdModParaFieldRelationVo ordVo = new OrdModParaFieldRelationVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);


		listField = ordModParaFieldRelationServDu.queryOrdModParaFieldRelationList(ordVo, pageNo, pageSize);
		totalNume = ordModParaFieldRelationServDu.queryOrdModParaFieldRelationListCount(ordVo);
		if(listField == null){
			uocMessage.setContent("查找结果为空");
			uocMessage.setRespCode(respCode.SUCCESS);
			return uocMessage;
		}


		int totalPages = (totalNume + (pageSize -1)) / pageSize;
		Map<String,Object> json_out = new HashMap<String,Object>();
		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);
		json_out.put("totalPages", totalPages);
		json_out.put("totalNume", totalNume);
		json_out.put("ordModParaFieldRelation", listField);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}


	/**
	 * UOCXXXX	订单模板应用表查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchOrdModAppList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

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
		getBeanDu();
		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if("0000".equals(operMesg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		}else{
			return operMesg;
		}

		List<OrdModAppVo> listApp = null;
		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		OrdModAppVo ordVo = new OrdModAppVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);


		listApp = ordModAppServDu.queryOrdModAppList(ordVo, pageNo, pageSize);
		totalNume = ordModAppServDu.queryOrdModAppListCount(ordVo);
		if(listApp == null){
			uocMessage.setContent("查找结果为空");
			uocMessage.setRespCode(respCode.SYSTEM_EXCEPTION);
			return uocMessage;
		}


		int totalPages = (totalNume + (pageSize -1)) / pageSize;
		Map<String,Object> json_out = new HashMap<String,Object>();
		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);
		json_out.put("totalPages", totalPages);
		json_out.put("totalNume", totalNume);
		json_out.put("ordModApp", listApp);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}


	/**
	 * UOCXXXX	订单模板参数校验规则表查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchOrdModAttrCheckRuleList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

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

		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if("0000".equals(operMesg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		}else{
			return operMesg;
		}

		List<OrdModAttrCheckRuleVo> listAttr = null;

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		OrdModAttrCheckRuleVo ordVo = new OrdModAttrCheckRuleVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);


		listAttr = ordModAttrCheckRuleServDu.queryOrdModAttrCheckRuleList(ordVo, pageNo, pageSize);
		totalNume = ordModAttrCheckRuleServDu.queryOrdModAttrCheckRuleListCount(ordVo);
		if(listAttr == null){
			uocMessage.setContent("查找结果为空");
			uocMessage.setRespCode(respCode.SUCCESS);
			return uocMessage;
		}


		int totalPages = (totalNume + (pageSize -1)) / pageSize;
		Map<String,Object> json_out = new HashMap<String,Object>();
		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);
		json_out.put("totalPages", totalPages);
		json_out.put("totalNume", totalNume);
		json_out.put("ordModAttrCheckRule", listAttr);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}


	/**
	 * UOCXXXX	模板参数定义表查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchOrdModAttrDefineList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

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
		getBeanDu();
		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if("0000".equals(operMesg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		}else{
			return operMesg;
		}

		List<OrdModAttrDefineVo> listAttrDefine = null;

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		OrdModAttrDefineVo ordVo = new OrdModAttrDefineVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);


		listAttrDefine = ordModAttrDefineServDu.queryOrdModAttrDefineList(ordVo, pageNo, pageSize);
		totalNume = ordModAttrDefineServDu.queryOrdModAttrDefineListCount(ordVo);
		if(listAttrDefine == null){
			uocMessage.setContent("查找结果为空");
			uocMessage.setRespCode(respCode.SUCCESS);
			return uocMessage;
		}


		int totalPages = (totalNume + (pageSize -1)) / pageSize;
		Map<String,Object> json_out = new HashMap<String,Object>();
		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);
		json_out.put("totalPages", totalPages);
		json_out.put("totalNume", totalNume);
		json_out.put("ordModAttrDefine", listAttrDefine);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}



	/**
	 * UOCXXXX	订单模板校验规则表查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchOrdModCheckRuleList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		getBeanDu();
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
		//对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

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
		//BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if("0000".equals(operMesg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String,Object> oper_info = 	(Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		}else{
			return operMesg;
		}

		List<OrdModCheckRuleVo> listCheckRule = null;
		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		OrdModCheckRuleVo ordVo = new OrdModCheckRuleVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);


		listCheckRule = ordModCheckRuleServDu.queryOrdModCheckRuleList(ordVo, pageNo, pageSize);
		totalNume = ordModCheckRuleServDu.queryOrdModCheckRuleListCount(ordVo);
		if(listCheckRule == null){
			uocMessage.setContent("查找结果为空");
			uocMessage.setRespCode(respCode.SUCCESS);
			return uocMessage;
		}

		int totalPages = (totalNume + (pageSize -1)) / pageSize;
		Map<String,Object> json_out = new HashMap<String,Object>();
		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);
		json_out.put("totalPages", totalPages);
		json_out.put("totalNume", totalNume);
		json_out.put("ordModCheckRule", listCheckRule);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}

	/**
	 * UOC0065	任务默认分配规则维护
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public UocMessage syncTaskAssignRule(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		String jsession_id = paraVo.getJsession_id();
		String oper_type = paraVo.getOper_type();
		String json_info = paraVo.getJson_info();

		if (jsession_id == null || jsession_id.equals("")) {
			uocMessage.setContent("失败:jsession_id为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if (json_info == null || json_info.equals("")) {
			uocMessage.setContent("失败:json_info为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if (oper_type == null || oper_type.equals("")) {
			uocMessage.setContent("失败:oper_type为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		getBeanDu();
		// BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if (!"0000".equals(operMesg.getRespCode())) {
			return operMesg;
		}

		Map<String, Object> oper_info = (Map<String, Object>) operMesg.getArgs().get("oper_info");
		String province_code = (String) oper_info.get("province_code");
		String area_code = (String) oper_info.get("area_code");

		Map<String, Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

		Set<Entry<String, Object>> entries = mapObj.entrySet();
		if (entries == null) {
			uocMessage.setContent("条件不足");
			return uocMessage;
		}

		for (Entry<String, Object> entry : entries) {
			if (Constant.PROC_TASK_ASSIGN_RULE.equals(entry.getKey())) {
				Map<String, Object> mapVo = (Map<String, Object>) entry.getValue();
				ProcTaskRuleAssignVo procTaskRuleAssignVo = new ProcTaskRuleAssignVo();
				BeanUtils.populate(procTaskRuleAssignVo, mapVo);
				if (Constant.ADD.equals(oper_type)) {
					procTaskRuleAssignVo.setProvince_code(province_code);
					procTaskRuleAssignVo.setArea_code(area_code);
					ProcTaskRuleAssignVo existRuleVo = procTaskAssignRuleServDu.queryProcTaskAssignRuleByVo(procTaskRuleAssignVo);
					if (existRuleVo != null) {
						uocMessage.setRespCode(respCode.PARAM_ERROR);
						uocMessage.setContent("规则重复");
						return uocMessage;
					}
					String id = getIdServDu.getId("createLogId", province_code, "*", "");
					procTaskRuleAssignVo.setId(id);
					boolean result = procTaskAssignRuleServDu.create(procTaskRuleAssignVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("新增失败");
						throw new UocException(uocExceptionMsg);
					}
				} else if (Constant.DELETE.equals(oper_type)) {
					boolean result = procTaskAssignRuleServDu.delete(procTaskRuleAssignVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("删除失败");
						throw new UocException(uocExceptionMsg);
					}
				} else if (Constant.UPDATE.equals(oper_type)) {
					procTaskRuleAssignVo.setProvince_code(province_code);
					procTaskRuleAssignVo.setArea_code(area_code);
					ProcTaskRuleAssignVo existRuleVo = procTaskAssignRuleServDu.queryProcTaskAssignRuleByVo(procTaskRuleAssignVo);
					if (existRuleVo != null && !existRuleVo.getId().equals(procTaskRuleAssignVo.getId())) {
						uocMessage.setRespCode(respCode.PARAM_ERROR);
						uocMessage.setContent("规则重复");
						return uocMessage;
					}
					// 先根据id查询，更新要更新的部分字段，原来的不变
					ProcTaskRuleAssignVo ruleVo = procTaskAssignRuleServDu.queryProcTaskAssignRuleById(procTaskRuleAssignVo);
					if (ruleVo == null) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("修改失败,数据库中没有该条记录");
						throw new UocException(uocExceptionMsg);
					} else {
						if (procTaskRuleAssignVo.getTarget_depart_no() != null && !"".equals(procTaskRuleAssignVo.getTarget_depart_no())) {
							ruleVo.setTarget_depart_no(procTaskRuleAssignVo.getTarget_depart_no());
						}
						if (procTaskRuleAssignVo.getTarget_oper_no() != null && !"".equals(procTaskRuleAssignVo.getTarget_oper_no())) {
							ruleVo.setTarget_oper_no(procTaskRuleAssignVo.getTarget_oper_no());
						} else {
							ruleVo.setTarget_oper_no("");
						}
						if (procTaskRuleAssignVo.getTache_code() != null && !"".equals(procTaskRuleAssignVo.getTache_code())) {
							ruleVo.setTache_code(procTaskRuleAssignVo.getTache_code());
						}
						if (procTaskRuleAssignVo.getOper_code() != null && !"".equals(procTaskRuleAssignVo.getOper_code())) {
							ruleVo.setOper_code(procTaskRuleAssignVo.getOper_code());
						}
						if (procTaskRuleAssignVo.getAccept_oper_no() != null && !"".equals(procTaskRuleAssignVo.getAccept_oper_no())) {
							ruleVo.setAccept_oper_no(procTaskRuleAssignVo.getAccept_oper_no());
						}
						if (procTaskRuleAssignVo.getAccept_depart_no() != null && !"".equals(procTaskRuleAssignVo.getAccept_depart_no())) {
							ruleVo.setAccept_depart_no(procTaskRuleAssignVo.getAccept_depart_no());
						}
						if (procTaskRuleAssignVo.getExt_cond1() != null && !"".equals(procTaskRuleAssignVo.getExt_cond1())) {
							ruleVo.setExt_cond1(procTaskRuleAssignVo.getExt_cond1());
						} else {
							ruleVo.setExt_cond1("");
						}
						if (procTaskRuleAssignVo.getExt_cond2() != null && !"".equals(procTaskRuleAssignVo.getExt_cond2())) {
							ruleVo.setExt_cond2(procTaskRuleAssignVo.getExt_cond2());
						} else {
							ruleVo.setExt_cond2("");
						}
						if (procTaskRuleAssignVo.getExt_cond3() != null && !"".equals(procTaskRuleAssignVo.getExt_cond3())) {
							ruleVo.setExt_cond3(procTaskRuleAssignVo.getExt_cond3());
						} else {
							ruleVo.setExt_cond3("");
						}
						boolean result = procTaskAssignRuleServDu.update(ruleVo);
						if(!result){
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("修改失败");
							throw new UocException(uocExceptionMsg);
						}
					}
				}

			} else if (Constant.PROC_TASK_RULE_SPEC.equals(entry.getKey())) {
				Map<String, Object> mapVo = (Map<String, Object>) entry.getValue();
				ProcTaskRuleSpecVo procTaskRuleSpecVo = new ProcTaskRuleSpecVo();
				BeanUtils.populate(procTaskRuleSpecVo, mapVo);
				if (Constant.ADD.equals(oper_type)) {
					String[] propotions = procTaskRuleSpecVo.getProportion().split(",");
					String[] tagerOperNos = procTaskRuleSpecVo.getTarget_oper_no().split(",");

					for (int i = 0; i < tagerOperNos.length && StringUtils.isNotEmpty(tagerOperNos[0]); i++) {
						ProcTaskRuleSpecVo addVo = new ProcTaskRuleSpecVo();
						String id = getIdServDu.getId("createLogId", province_code, "*", "");
						addVo.setId(id);
						addVo.setFlag("0");
						addVo.setRule_id("group_" + procTaskRuleSpecVo.getRule_id());
						addVo.setProportion(propotions[i]);
						addVo.setTarget_oper_no(tagerOperNos[i].trim());
						boolean result = procTaskRuleSpecServDu.create(addVo);

						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("新增失败");
							throw new UocException(uocExceptionMsg);
						}
					}

					if (StringUtils.isNotEmpty(procTaskRuleSpecVo.getBelong_depart_no())) {
						ProcTaskRuleDepartVo deleteDepartVo = new ProcTaskRuleDepartVo();
						deleteDepartVo.setDepart_no(procTaskRuleSpecVo.getBelong_depart_no());
						boolean result = procTaskRuleDepartServDu.delete(deleteDepartVo);

						ProcTaskRuleDepartVo addDepartVo = new ProcTaskRuleDepartVo();
						addDepartVo.setDepart_no(procTaskRuleSpecVo.getBelong_depart_no());
						addDepartVo.setRule_id("group_" + procTaskRuleSpecVo.getRule_id());
						addDepartVo.setProportion(procTaskRuleSpecVo.getDepartProportion());
						result = procTaskRuleDepartServDu.create(addDepartVo);

						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("新增失败");
							throw new UocException(uocExceptionMsg);
						}
					}
				} else if (Constant.DELETE.equals(oper_type)) {
					boolean result = true;
					if (StringUtils.isNotEmpty(procTaskRuleSpecVo.getTarget_oper_no())) {
						result = procTaskRuleSpecServDu.delete(procTaskRuleSpecVo);
					}

					if (StringUtils.isNotEmpty(procTaskRuleSpecVo.getBelong_depart_no())) {
						ProcTaskRuleDepartVo deleteDepartVo = new ProcTaskRuleDepartVo();
						deleteDepartVo.setDepart_no(procTaskRuleSpecVo.getBelong_depart_no());
						deleteDepartVo.setProportion(procTaskRuleSpecVo.getDepartProportion());
						result = procTaskRuleDepartServDu.delete(deleteDepartVo);
					}

					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("删除失败");
						throw new UocException(uocExceptionMsg);
					}
				} else if (Constant.UPDATE.equals(oper_type)) {
					boolean result = true;
					if (StringUtils.isNotEmpty(procTaskRuleSpecVo.getTarget_oper_no())) {
						// 先根据id查询，更新要更新的部分字段，原来的不变
						ProcTaskRuleSpecVo specRuleVo = procTaskRuleSpecServDu.queryProcTaskRuleSpecById(procTaskRuleSpecVo);
						if (specRuleVo == null) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("修改失败,数据库中没有该条记录");
							throw new UocException(uocExceptionMsg);
						} else {
							if (procTaskRuleSpecVo.getRule_id() != null && !"".equals(procTaskRuleSpecVo.getRule_id())) {
								specRuleVo.setRule_id(procTaskRuleSpecVo.getRule_id());
							}
							if (procTaskRuleSpecVo.getTarget_oper_no() != null && !"".equals(procTaskRuleSpecVo.getTarget_oper_no())) {
								specRuleVo.setTarget_oper_no(procTaskRuleSpecVo.getTarget_oper_no());
							}
							if (procTaskRuleSpecVo.getProportion() != null && !"".equals(procTaskRuleSpecVo.getProportion())) {
								specRuleVo.setProportion(procTaskRuleSpecVo.getProportion());
							}
							if (procTaskRuleSpecVo.getFlag() != null && !"".equals(procTaskRuleSpecVo.getFlag())) {
								specRuleVo.setFlag(procTaskRuleSpecVo.getFlag());
							}
							result = procTaskRuleSpecServDu.update(specRuleVo);
						}
					}

					if (StringUtils.isNotEmpty(procTaskRuleSpecVo.getBelong_depart_no())) {
						ProcTaskRuleDepartVo deleteDepartVo = new ProcTaskRuleDepartVo();
						deleteDepartVo.setDepart_no(procTaskRuleSpecVo.getBelong_depart_no());
						result = procTaskRuleDepartServDu.delete(deleteDepartVo);

						ProcTaskRuleDepartVo updateDepartVo = new ProcTaskRuleDepartVo();
						updateDepartVo.setDepart_no(procTaskRuleSpecVo.getBelong_depart_no());
						updateDepartVo.setProportion(procTaskRuleSpecVo.getDepartProportion());
						updateDepartVo.setRule_id(procTaskRuleSpecVo.getRule_id());
						result = procTaskRuleDepartServDu.create(updateDepartVo);
					}

					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("修改失败");
						throw new UocException(uocExceptionMsg);
					}
				}

			} else if (Constant.PROC_TASK_RULE_DEPART.equals(entry.getKey())) {
				Map<String, Object> mapVo = (Map<String, Object>) entry.getValue();
				ProcTaskRuleDepartVo procTaskRuleDepartVo = new ProcTaskRuleDepartVo();
				BeanUtils.populate(procTaskRuleDepartVo, mapVo);
				if (Constant.ADD.equals(oper_type)) {
					boolean result = procTaskRuleDepartServDu.create(procTaskRuleDepartVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("新增失败");
						throw new UocException(uocExceptionMsg);
					}
				} else if (Constant.DELETE.equals(oper_type)) {
					boolean result = procTaskRuleDepartServDu.delete(procTaskRuleDepartVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("删除失败");
						throw new UocException(uocExceptionMsg);
					}
				} else if (Constant.UPDATE.equals(oper_type)) {
					ProcTaskRuleDepartVo departRuleVo = procTaskRuleDepartServDu.queryProcTaskRuleDepartByVo(procTaskRuleDepartVo).get(0);
					if (departRuleVo == null) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
						uocExceptionMsg.setContent("修改失败,数据库中没有该条记录");
						throw new UocException(uocExceptionMsg);
					} else {
						boolean result = procTaskRuleDepartServDu.update(procTaskRuleDepartVo);
						if(!result){
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
							uocExceptionMsg.setContent("修改失败");
							throw new UocException(uocExceptionMsg);
						}
					}
				}
			}

		}

		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");

		return uocMessage;

	}

	/**
	 *  UOC0066	任务默认分配规则查询
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public UocMessage searchTaskAssignRule(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		String jsession_id = paraVo.getJsession_id();
		String json_info = paraVo.getJson_info();
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
		getBeanDu();
		// BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if (!"0000".equals(operMesg.getRespCode())) {
			return operMesg;
		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);


		Set<Entry<String, Object>> entries = mapObj.entrySet();
		if(entries==null){
			uocMessage.setContent("条件不足");
			return uocMessage;
		}
		int pageNo = Integer.parseInt((String) mapObj.get("pageNo"));
		int pageSize = Integer.parseInt((String) mapObj.get("pageSize"));

		int procTaskAssignRuleCount=0;
		int procTaskRuleSpecCount=0;
		int procTaskRuleDepartCount = 0;

		Map<String,Object> json_out = new HashMap<String,Object>();

		for (Entry<String, Object> entry : entries) {
			if(Constant.PROC_TASK_ASSIGN_RULE.equals(entry.getKey())){
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				ProcTaskRuleAssignVo procTaskRuleAssignVo = new ProcTaskRuleAssignVo();
				BeanUtils.populate(procTaskRuleAssignVo, mapVo);
				List<ProcTaskRuleAssignVo> procTaskAssignRuleList = procTaskAssignRuleServDu.queryProcTaskAssignRuleByPage(procTaskRuleAssignVo, pageNo, pageSize);
				procTaskAssignRuleCount = procTaskAssignRuleServDu.queryProcTaskAssignRuleCount(procTaskRuleAssignVo);
				int totalprocTaskAssignRule = (procTaskAssignRuleCount+pageSize -1) / pageSize;
				if(procTaskAssignRuleCount > 0){
					json_out.put("totalPagesProcTaskAssignRule", totalprocTaskAssignRule);
					json_out.put("totalNumeProcTaskAssignRule", procTaskAssignRuleCount);
					json_out.put("procTaskAssignRule", procTaskAssignRuleList);
				}else{
					logger.info("-----------proc_task_assign_rule表查找结果为空-------------");
				}
			} else if (Constant.PROC_TASK_RULE_SPEC.equals(entry.getKey())) {
				Map<String,Object> mapVo = (Map<String, Object>) entry.getValue();
				ProcTaskRuleSpecVo procTaskRuleSpecVo = new ProcTaskRuleSpecVo();
				BeanUtils.populate(procTaskRuleSpecVo, mapVo);
				List<ProcTaskRuleSpecVo> resultList = new ArrayList<ProcTaskRuleSpecVo>();
				List<ProcTaskRuleDepartVo> resultDepartList = new ArrayList<ProcTaskRuleDepartVo>();
				String tagetOperNos = "";
				if (StringUtils.isNotEmpty(procTaskRuleSpecVo.getTarget_oper_no())) {
					for (String tagetOperNo : procTaskRuleSpecVo.getTarget_oper_no().split(",")) {
						tagetOperNos = tagetOperNos + "'" + tagetOperNo.trim() + "'" + ",";
					}
					tagetOperNos = tagetOperNos.substring(0, tagetOperNos.length() - 1);
				}

				if (StringUtils.isEmpty(procTaskRuleSpecVo.getBelong_depart_no())) {
					procTaskRuleSpecVo.setTarget_oper_no(tagetOperNos);
					List<ProcTaskRuleSpecVo> procTaskRuleSpecList = procTaskRuleSpecServDu.queryProcTaskRuleSpecByWeb(procTaskRuleSpecVo);
					procTaskRuleSpecCount = procTaskRuleSpecServDu.queryProcTaskRuleSpecCount(procTaskRuleSpecVo);

					if (procTaskRuleSpecCount > 0) {
						for (ProcTaskRuleSpecVo specVo : procTaskRuleSpecList) {
							ProcTaskRuleDepartVo departVo = new ProcTaskRuleDepartVo();
							departVo.setRule_id(specVo.getRule_id());
							List<ProcTaskRuleDepartVo> procTaskRuleDepartList = procTaskRuleDepartServDu
									.queryProcTaskRuleDepartByVo(departVo);
							if (procTaskRuleDepartList != null && procTaskRuleDepartList.size() > 0) {
								specVo.setBelong_depart_no(procTaskRuleDepartList.get(0).getDepart_no());
								specVo.setDepartProportion(procTaskRuleDepartList.get(0).getProportion());
							} else {
								specVo.setBelong_depart_no("");
								specVo.setDepartProportion("");
							}
							resultList.add(specVo);
						}


					} else {
						logger.info("-----------proc_task_rule_spec表查找结果为空-------------");
					}
				} else {
					ProcTaskRuleDepartVo departVo = new ProcTaskRuleDepartVo();
					departVo.setRule_id(procTaskRuleSpecVo.getRule_id());
					departVo.setDepart_no(procTaskRuleSpecVo.getBelong_depart_no());
					List<ProcTaskRuleDepartVo> procTaskRuleDepartList = procTaskRuleDepartServDu.queryProcTaskRuleDepartByVo(departVo);

					if (procTaskRuleDepartList != null && procTaskRuleDepartList.size() > 0) {
						ProcTaskRuleSpecVo newSpecVo = new ProcTaskRuleSpecVo();
						newSpecVo.setRule_id(procTaskRuleDepartList.get(0).getRule_id());
						newSpecVo.setTarget_oper_no(tagetOperNos);
						List<ProcTaskRuleSpecVo> procTaskRuleSpecList = procTaskRuleSpecServDu.queryProcTaskRuleSpecByWeb(newSpecVo);
						procTaskRuleSpecCount = procTaskRuleSpecServDu.queryProcTaskRuleSpecCount(newSpecVo);

						if (procTaskRuleSpecCount > 0) {
							for (ProcTaskRuleSpecVo specVo : procTaskRuleSpecList) {
								specVo.setBelong_depart_no(procTaskRuleDepartList.get(0).getDepart_no());
								specVo.setDepartProportion(procTaskRuleDepartList.get(0).getProportion());

								resultList.add(specVo);
							}

						} else {
							logger.info("-----------proc_task_rule_spec表查找结果为空-------------");
						}
					} else {
						logger.info("-----------proc_task_rule_depart表查找结果为空-------------");
					}
				}

				// 查询proc_task_rule_depart表
				ProcTaskRuleDepartVo procTaskRuleDepartVo = new ProcTaskRuleDepartVo();
				procTaskRuleDepartVo.setDepart_no(procTaskRuleSpecVo.getBelong_depart_no());
				procTaskRuleDepartVo.setRule_id(procTaskRuleSpecVo.getRule_id());
				List<ProcTaskRuleDepartVo> departList = procTaskRuleDepartServDu.queryProcTaskRuleDepartByWeb(procTaskRuleDepartVo);
				procTaskRuleDepartCount = procTaskRuleDepartServDu.queryProcTaskRuleDepartCount(procTaskRuleDepartVo);
				if (procTaskRuleDepartCount > 0) {
					for (ProcTaskRuleDepartVo depVo : departList) {
						boolean isRuleExist = false;
						for (ProcTaskRuleSpecVo specVo : resultList) {
							if (specVo.getRule_id().equals(depVo.getRule_id())) {
								isRuleExist = true;
								break;
							}
						}

						if (isRuleExist) {
							continue;
						} else {
							logger.info("rule_id不在proc_task_rule_spec表,在proc_task_rule_depart表存在");
							resultDepartList.add(depVo);
						}
					}

				} else {
					logger.info("-----------proc_task_rule_depart表查找结果为空-------------");
				}

				int totalProcTaskRuleSpec = (procTaskRuleSpecCount + resultDepartList.size() + pageSize - 1) / pageSize;
				json_out.put("totalPagesProcTaskRuleSpec", totalProcTaskRuleSpec);
				json_out.put("totalNumeProcTaskRuleSpec", procTaskRuleSpecCount + resultDepartList.size());
				json_out.put("procTaskRuleSpec", resultList);
				json_out.put("procTaskRuleDepart", resultDepartList);
			} else if (Constant.PROC_TASK_RULE_DEPART.equals(entry.getKey())) {
				Map<String, Object> mapVo = (Map<String, Object>) entry.getValue();
				ProcTaskRuleDepartVo procTaskRuleDepartVo = new ProcTaskRuleDepartVo();
				BeanUtils.populate(procTaskRuleDepartVo, mapVo);
				List<ProcTaskRuleDepartVo> procTaskRuleDepartList = procTaskRuleDepartServDu
						.queryProcTaskRuleDepartByWeb(procTaskRuleDepartVo);
				procTaskRuleDepartCount = procTaskRuleDepartServDu.queryProcTaskRuleDepartCount(procTaskRuleDepartVo);
				int totalProcTaskRuleDepart = (procTaskRuleSpecCount + pageSize - 1) / pageSize;
				if (procTaskRuleSpecCount > 0) {
					json_out.put("totalPagesProcTaskRuleDepart", totalProcTaskRuleDepart);
					json_out.put("totalNumeProcTaskRuleDepart", procTaskRuleDepartCount);
					json_out.put("procTaskRuleDepart", procTaskRuleDepartList);
				} else {
					logger.info("-----------查找结果为空-------------");
				}
			}
		}

		json_out.put("pageNo", pageNo);
		json_out.put("pageSize", pageSize);

		uocMessage.addArg("json_info", json_out);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		return uocMessage;
	}

	@Override
	public UocMessage queryOperNoGroup(String rule_id) throws Exception {
		UocMessage uocMessage = new UocMessage();

		List<ProcTaskRuleSpecVo> resultList = procTaskRuleSpecServDu.queryTaskRuleSpecGroupByRuleId(rule_id);
		if (resultList != null && resultList.size() > 0) {
			uocMessage.addArg("resultList", resultList);
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("查询工号组成功");
			return uocMessage;
		} else {
			uocMessage.addArg("resultList", "");
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("工号组查询为空");
			return uocMessage;
		}

	}

	private void getBeanDu(){
		if(operServDu == null){
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if(ordModStateRuleServDu == null){
			ordModStateRuleServDu = (OrdModStateRuleServDu) ToolSpring.getBean("OrdModStateRuleServDu");
		}
		if(ordModTacheRuleServDu == null){
			ordModTacheRuleServDu = (OrdModTacheRuleServDu) ToolSpring.getBean("OrdModTacheRuleServDu");
		}
		if(jsonToBeanServDu == null){
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if(getIdServDu == null){
			getIdServDu = (GetIdServDu) ToolSpring.getBean("GetIdServDu");
		}
		if(ordModAttrCheckRuleServDu == null){
			ordModAttrCheckRuleServDu = (OrdModAttrCheckRuleServDu) ToolSpring.getBean("OrdModAttrCheckRuleServDu");
		}
		if(ordModAttrDefineServDu == null){
			ordModAttrDefineServDu = (OrdModAttrDefineServDu) ToolSpring.getBean("OrdModAttrDefineServDu");
		}
		if(ordModCheckRuleServDu == null){
			ordModCheckRuleServDu = (OrdModCheckRuleServDu) ToolSpring.getBean("OrdModCheckRuleServDu");
		}
		if(ordModDefineServDu == null){
			ordModDefineServDu = (OrdModDefineServDu) ToolSpring.getBean("OrdModDefineServDu");
		}
		if(ordModParaFieldRelationServDu == null){
			ordModParaFieldRelationServDu = (OrdModParaFieldRelationServDu) ToolSpring.getBean("OrdModParaFieldRelationServDu");
		}
		if(ordModAppServDu == null){
			ordModAppServDu = (OrdModAppServDu) ToolSpring.getBean("OrdModAppServDu");
		}
		if(procModAppServDu == null){
			procModAppServDu = (ProcModAppServDu) ToolSpring.getBean("ProcModAppServDu");
		}
		if(procModTacheLoginServDu == null){
			procModTacheLoginServDu = (ProcModTacheLoginServDu) ToolSpring.getBean("ProcModTacheLoginServDu");
		}
		if(procModTacheServiceServDu == null){
			procModTacheServiceServDu = (ProcModTacheServiceServDu) ToolSpring.getBean("ProcModTacheServiceServDu");
		}
		if(procModTacheBtnServDu == null){
			procModTacheBtnServDu = (ProcModTacheBtnServDu) ToolSpring.getBean("ProcModTacheBtnServDu");
		}
		if(procModTacheServDu == null){
			procModTacheServDu = (ProcModTacheServDu) ToolSpring.getBean("ProcModTacheServDu");
		}
		if(redisServiceServ == null){
			redisServiceServ = (RedisServiceServ) ToolSpring.getBean("RedisServiceServ");
		}
		if(codeTaskPkgDesignServDu == null){
			codeTaskPkgDesignServDu = (CodeTaskPkgDesignServDu) ToolSpring.getBean("CodeTaskPkgDesignServDu");
		}
		if(codeTaskPkgDefineServDu == null){
			codeTaskPkgDefineServDu = (CodeTaskPkgDefineServDu) ToolSpring.getBean("CodeTaskPkgDefineServDu");
		}
		if(codeTaskPkgAppServDu == null){
			codeTaskPkgAppServDu = (CodeTaskPkgAppServDu) ToolSpring.getBean("CodeTaskPkgAppServDu");
		}
		if(procTaskAssignRuleServDu == null){
			procTaskAssignRuleServDu = (ProcTaskAssignRuleServDu) ToolSpring.getBean("ProcTaskAssignRuleServDu");
		}
		if(procTaskRuleSpecServDu == null){
			procTaskRuleSpecServDu = (ProcTaskRuleSpecServDu) ToolSpring.getBean("ProcTaskRuleSpecServDu");
		}
		if (procTaskRuleDepartServDu == null) {
			procTaskRuleDepartServDu = (ProcTaskRuleDepartServDu) ToolSpring.getBean("ProcTaskRuleDepartServDu");
		}
	}

}
