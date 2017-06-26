package com.tydic.unicom.uoc.business.order.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskInstHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskInstHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.business.common.interfaces.GetDepartAndOperInfoServDu;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.common.vo.ProcModTacheVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ProcModServDu;
import com.tydic.unicom.uoc.business.order.service.vo.ProcInstTaskDealRecordVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcLogVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcViewVo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcModAppServDu;
import com.tydic.unicom.uoc.service.activiti.vo.ProcModAppVo;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.constants.Constant;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAppServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAttrCheckRuleServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAttrDefineServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModCheckRuleServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModDefineServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModParaFieldRelationServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheBtnServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheLoginServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServiceServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcViewInfoServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheBtnVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheLoginVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheServiceVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcViewInfoVo;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOrderCancelServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.ProcInstTaskDealRecordServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoOrderCancelVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class ProcModServDuImpl implements ProcModServDu{

	Logger logger = Logger.getLogger(ProcModServDuImpl.class);
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
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private InfoServiceOrderHisServDu infoServiceOrderHisServDu;
	@Autowired
	private ProcViewInfoServDu procViewInfoServDu;
	@Autowired
	private ProcInstTaskDealRecordServDu procInstTaskDealRecordServDu;
	@Autowired
	private RedisServiceServ redisServiceServ;
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	@Autowired
	private InfoSaleOrderHisServDu infoSaleOrderHisServDu;
	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;
	@Autowired
	private ProcInstTaskInstHisServ procInstTaskInstHisServ;
	@Autowired
	private InfoOrderCancelServDu infoOrderCancelServDu;
	@Autowired
	private GetDepartAndOperInfoServDu getDepartAndOperInfoServDu;

	private RespCodeContents respCode;


	/**
	 * UOC0017 流程应用维护
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage syncProcOrdApp(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		String jsession_id = paraVo.getJsession_id();
//		String type = paraVo.getType();
		String oper_type = paraVo.getOper_type();
		String json_info = paraVo.getJson_info();
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
//		uocMessage operMesg = operServDu.isLogin(jsession_id);
//		if("success".equals(operMesg.getType().toString())){
//			Map<String,Object> operArgsMap = operMesg.getArgs();
//			@SuppressWarnings("unchecked")
//			Map<String,String> operInfoMap = (Map<String, String>) operArgsMap.get("oper_info");
//			province_code = operInfoMap.get("province_code");
//			area_code = operInfoMap.get("area_code");
//			depart_no = operInfoMap.get("depart_no");
//			depart_name = operInfoMap.get("depart_name");
//			oper_no = operInfoMap.get("oper_no");
//		}
//		else{
//			return operMesg;
//		}
		try {
			Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

			Set<Entry<String, Object>> entries = mapObj.entrySet();
			if(entries == null){
				uocMessage.setContent("条件不足");
				return uocMessage;
			}
			List<ProcModAppVo> list = new ArrayList<ProcModAppVo>();
			for (Entry<String, Object> entry : entries) {
				if(entry.getKey().equals(Constant.PROC_MOD_APP)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						ProcModAppVo procModAppVo = new ProcModAppVo();
						BeanUtils.populate(procModAppVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							String id = getIdServDu.getId("createLogId", province_code, "*","");
							procModAppVo.setId(id);
							boolean result = procModAppServDu.create(procModAppVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = procModAppServDu.update(procModAppVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = procModAppServDu.delete(procModAppVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						list.add(procModAppVo);
					}
				}
			}
			Map<String,Object> json_out = new HashMap<String,Object>();
//			if(oper_type.equals(Constant.QUERY)){
//				List<ProcModAppVo> listProcModAppVo = null;
//				int pageNo = (int) mapObj.get("pageNo");
//				int pageSize = (int) mapObj.get("pageSize");
//				int totalNume = 0;
//				ProcModAppVo ordVo = new ProcModAppVo();
//				Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
//				BeanUtils.populate(ordVo, mapVo);
//				listProcModAppVo = procModAppServDu.queryProcModAppList(ordVo, pageNo, pageSize);
//				totalNume = procModAppServDu.queryProcModAppListCount(ordVo);
//				if(listProcModAppVo == null){
//					uocMessage.setContent("查找结果为空");
//					uocMessage.setType(uocMessage.Type.success);
//					return uocMessage;
//				}
//				int totalPages = (totalNume + (pageSize -1)) / pageSize;
	//
//				json_out.put("pageNo", pageNo);
//				json_out.put("pageSize", pageSize);
//				json_out.put("totalPages", totalPages);
//				json_out.put("totalNume", totalNume);
//				json_out.put("procModApp", listProcModAppVo);
//				uocMessage.addArg("json_info", json_out);
//			}
			//BASE0007
			//Map<String,Object> logMap = null;
			json_out.put("procModApp", list);
			uocMessage.addArg("json_info", json_out);
			uocMessage.setRespCode(respCode.SUCCESS);
			uocMessage.setContent("操作成功");
		} catch (Exception e) {
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
			uocExceptionMsg.setContent("操作记录失败");
			throw new UocException(uocExceptionMsg);
		}
		return uocMessage;
	}

	/**
	 * UOC0018 环节配置
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage syncProcModTache(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		String jsession_id = paraVo.getJsession_id();
//		String type = paraVo.getType();
		String oper_type = paraVo.getOper_type();
		String json_info = paraVo.getJson_info();
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
//		uocMessage operMesg = operServDu.isLogin(jsession_id);
//		if("success".equals(operMesg.getType().toString())){
//			Map<String,Object> operArgsMap = operMesg.getArgs();
//			@SuppressWarnings("unchecked")
//			Map<String,String> operInfoMap = (Map<String, String>) operArgsMap.get("oper_info");
//			province_code = operInfoMap.get("province_code");
//			area_code = operInfoMap.get("area_code");
//			depart_no = operInfoMap.get("depart_no");
//			depart_name = operInfoMap.get("depart_name");
//			oper_no = operInfoMap.get("oper_no");
//		}
//		else{
//			return operMesg;
//		}

		try {
			Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);

			Set<Entry<String, Object>> entries = mapObj.entrySet();
			if(entries.size() == 0){
				uocMessage.setContent("条件不足");
				return uocMessage;
			}
			List<ProcModTacheLoginVo> procModTacheLoginList = new ArrayList<ProcModTacheLoginVo>();
			List<ProcModTacheServiceVo> procModTacheServiceList = new ArrayList<ProcModTacheServiceVo>();
			List<ProcModTacheBtnVo> procModTacheBtnList = new ArrayList<ProcModTacheBtnVo>();
			List<ProcModTacheVo> procModTacheList = new ArrayList<ProcModTacheVo>();
			for (Entry<String, Object> entry : entries) {
				if(entry.getKey().equals(Constant.PROC_MOD_TACHE_LOGIN)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						ProcModTacheLoginVo procModTacheLoginVo = new ProcModTacheLoginVo();
						BeanUtils.populate(procModTacheLoginVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							String id = getIdServDu.getId("createLogId", province_code, "*","");
							procModTacheLoginVo.setId(id);
							boolean result = procModTacheLoginServDu.create(procModTacheLoginVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = procModTacheLoginServDu.update(procModTacheLoginVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = procModTacheLoginServDu.delete(procModTacheLoginVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						procModTacheLoginList.add(procModTacheLoginVo);
					}
				}
				 if(entry.getKey().equals(Constant.PROC_MOD_TACHE_SERVICE)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						ProcModTacheServiceVo procModTacheServiceVo = new ProcModTacheServiceVo();
						BeanUtils.populate(procModTacheServiceVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							boolean result = procModTacheServiceServDu.create(procModTacheServiceVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = procModTacheServiceServDu.update(procModTacheServiceVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = procModTacheServiceServDu.delete(procModTacheServiceVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						procModTacheServiceList.add(procModTacheServiceVo);
					}
				}
				 if(entry.getKey().equals(Constant.PROC_MOD_TACHE_BTN)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						ProcModTacheBtnVo procModTacheBtnVo = new ProcModTacheBtnVo();
						BeanUtils.populate(procModTacheBtnVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							String id = getIdServDu.getId("createLogId", province_code, "*","");
							procModTacheBtnVo.setId(id);
							boolean result = procModTacheBtnServDu.create(procModTacheBtnVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = procModTacheBtnServDu.update(procModTacheBtnVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = procModTacheBtnServDu.delete(procModTacheBtnVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						procModTacheBtnList.add(procModTacheBtnVo);
					}
				}
				 if(entry.getKey().equals(Constant.PROC_MOD_TACHE)){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> mapVos = (List<Map<String, Object>>) entry.getValue();
					for(Map<String,Object> mapVo : mapVos){
						ProcModTacheVo procModTacheVo = new ProcModTacheVo();
						BeanUtils.populate(procModTacheVo, mapVo);
						if(oper_type.equals(Constant.ADD)){
							boolean result = procModTacheServDu.create(procModTacheVo);
							if(!result){
								uocMessage.setContent("新增失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.UPDATE)){
							boolean result = procModTacheServDu.update(procModTacheVo);
							if(!result){
								uocMessage.setContent("修改失败");
								return uocMessage;
							}
						}else if(oper_type.equals(Constant.DELETE)){
							boolean result = procModTacheServDu.delete(procModTacheVo);
							if(!result){
								uocMessage.setContent("删除失败");
								return uocMessage;
							}
						}
						procModTacheList.add(procModTacheVo);
					}
				}
			}
			Map<String,Object> json_out = new HashMap<String,Object>();
//			if(oper_type.equals(Constant.QUERY)){

//				ProcModTacheLoginVo procModTacheLogin = new ProcModTacheLoginVo();
//				ProcModTacheServiceVo procModTacheService = new ProcModTacheServiceVo();
//				ProcModTacheBtnVo procModTacheBtn = new ProcModTacheBtnVo();
//				ProcModTacheVo procModTache = new ProcModTacheVo();
	//
//				int pageNo = (int) mapObj.get("pageNo");
//				int pageSize = (int) mapObj.get("pageSize");
//				int procModTacheLoginListCount = 0;
//				int procModTacheServiceListCount = 0;
//				int procModTacheBtnListCount = 0;
//				int procModTacheListCount = 0;
//				Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
//				BeanUtils.populate(procModTache, mapVo);
//				BeanUtils.populate(procModTacheLogin, mapVo);
//				BeanUtils.populate(procModTacheService, mapVo);
//				BeanUtils.populate(procModTacheBtn, mapVo);
	//
//				procModTacheLoginList = procModTacheLoginServDu.queryProcModTacheLoginList(procModTacheLogin, pageNo, pageSize);
//				procModTacheLoginListCount = procModTacheLoginServDu.queryProcModTacheLoginListCount(procModTacheLogin);
	//
//				procModTacheServiceList = procModTacheServiceServDu.queryProcModTacheServiceList(procModTacheService, pageNo, pageSize);
//				procModTacheServiceListCount = procModTacheServiceServDu.queryProcModTacheServiceListCount(procModTacheService);
	//
//				procModTacheBtnList = procModTacheBtnServDu.queryProcModTacheBtnList(procModTacheBtn, pageNo, pageSize);
//				procModTacheBtnListCount = procModTacheBtnServDu.queryProcModTacheBtnListCount(procModTacheBtn);
	//
//				procModTacheList = procModTacheServDu.queryProcModTacheList(procModTache, pageNo, pageSize);
//				procModTacheListCount = procModTacheServDu.queryProcModTacheListCount(procModTache);
	//
//				int totalPagesTacheLogin = (procModTacheLoginListCount + pageSize -1) / pageSize;
//				int totalPagesTacheService = (procModTacheServiceListCount + pageSize -1) / pageSize;
//				int totalPagesTacheBtn = (procModTacheBtnListCount + pageSize -1) / pageSize;
//				int totalPagesTache = (procModTacheListCount + pageSize -1) / pageSize;
	//
	//
//				json_out.put("pageNo", pageNo);
//				json_out.put("pageSize", pageSize);
//				if(procModTacheLoginListCount > 0){
//					json_out.put("totalPagesProcModTacheLogin", totalPagesTacheLogin);
//					json_out.put("totalNumeProcModTacheLogin", procModTacheLoginListCount);
//					json_out.put("procModTacheLogin", procModTacheLoginList);
//				}
//				if(procModTacheServiceListCount > 0){
//					json_out.put("totalPagesProcModTacheService", totalPagesTacheService);
//					json_out.put("totalNumeProcModTacheService", procModTacheServiceListCount);
//					json_out.put("procModTacheService", procModTacheServiceList);
//				}
//				if(procModTacheBtnListCount > 0){
//					json_out.put("totalPagesProcModTacheBtn", totalPagesTacheBtn);
//					json_out.put("totalNumeProcModTacheBtn", procModTacheBtnListCount);
//					json_out.put("procModTacheBtn", procModTacheBtnList);
//				}
//				if(procModTacheListCount > 0){
//					json_out.put("totalPagesProcModTache", totalPagesTache);
//					json_out.put("totalNumeProcModTache", procModTacheListCount);
//					json_out.put("procModTache", procModTacheList);
//				}
//				uocMessage.addArg("json_info", json_out);
	//
//			}
			//BASE0007
			//Map<String,Object> logMap = null;
			json_out.put("procModTacheLogin", procModTacheLoginList);
			json_out.put("procModTacheService", procModTacheServiceList);
			json_out.put("procModTacheBtn", procModTacheBtnList);
			json_out.put("procModTache", procModTacheList);
			uocMessage.addArg("json_info", json_out);
			uocMessage.setRespCode(respCode.SUCCESS);
			uocMessage.setContent("操作成功");
		} catch (Exception e) {
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(respCode.SYSTEM_EXCEPTION);
			uocExceptionMsg.setContent("操作记录失败");
			throw new UocException(uocExceptionMsg);
		}
		return uocMessage;
	}

	/**
	 * UOC0030 流程应用查询
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchProcModApp(ParaVo paraVo) throws Exception {
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
//		uocMessage operMesg = operServDu.isLogin(jsession_id);
//		if("success".equals(operMesg.getType().toString())){
//			Map<String,Object> operArgsMap = operMesg.getArgs();
//			@SuppressWarnings("unchecked")
//			Map<String,String> operInfoMap = (Map<String, String>) operArgsMap.get("oper_info");
//			province_code = operInfoMap.get("province_code");
//			area_code = operInfoMap.get("area_code");
//			depart_no = operInfoMap.get("depart_no");
//			depart_name = operInfoMap.get("depart_name");
//			oper_no = operInfoMap.get("oper_no");
//		}
//		else{
//			return operMesg;
//		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);
		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		List<ProcModAppVo> listProcModAppVo = null;
		int totalNume = 0;
		ProcModAppVo ordVo = new ProcModAppVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);
		listProcModAppVo = procModAppServDu.queryProcModAppList(ordVo, pageNo, pageSize);
		totalNume = procModAppServDu.queryProcModAppListCount(ordVo);
		if(listProcModAppVo == null){
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
		json_out.put("procModApp", listProcModAppVo);
		uocMessage.addArg("json_info", json_out);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		return uocMessage;
	}

	/**
	 * UOC0031 环节查询
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchProcMod(ParaVo paraVo) throws Exception {
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
//		uocMessage operMesg = operServDu.isLogin(jsession_id);
//		if("success".equals(operMesg.getType().toString())){
//			Map<String,Object> operArgsMap = operMesg.getArgs();
//			@SuppressWarnings("unchecked")
//			Map<String,String> operInfoMap = (Map<String, String>) operArgsMap.get("oper_info");
//			province_code = operInfoMap.get("province_code");
//			area_code = operInfoMap.get("area_code");
//			depart_no = operInfoMap.get("depart_no");
//			depart_name = operInfoMap.get("depart_name");
//			oper_no = operInfoMap.get("oper_no");
//		}
//		else{
//			return operMesg;
//		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);
		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");

		Set<Entry<String, Object>> entries = mapObj.entrySet();
		if(entries.size() == 0){
			uocMessage.setContent("条件不足");
			return uocMessage;
		}

		int procModTacheLoginListCount = 0;
		int procModTacheServiceListCount = 0;
		int procModTacheBtnListCount = 0;
		int procModTacheListCount = 0;
		Map<String,Object> json_out = new HashMap<String,Object>();

		for (Entry<String, Object> entry : entries) {
			if(entry.getKey().equals(Constant.PROC_MOD_TACHE_LOGIN)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo=(Map<String, Object>)entry.getValue();
				ProcModTacheLoginVo procModTacheLogin = new ProcModTacheLoginVo();
				BeanUtils.populate(procModTacheLogin, mapVo);
				List<ProcModTacheLoginVo> procModTacheLoginList = procModTacheLoginServDu.queryProcModTacheLoginList(procModTacheLogin, pageNo, pageSize);
				procModTacheLoginListCount = procModTacheLoginServDu.queryProcModTacheLoginListCount(procModTacheLogin);
				int totalPagesTacheLogin = (procModTacheLoginListCount + pageSize -1) / pageSize;
				if(procModTacheLoginListCount > 0){
					json_out.put("totalPagesProcModTacheLogin", totalPagesTacheLogin);
					json_out.put("totalNumeProcModTacheLogin", procModTacheLoginListCount);
					json_out.put("procModTacheLogin", procModTacheLoginList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}

			}
			 if(entry.getKey().equals(Constant.PROC_MOD_TACHE_SERVICE)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo=(Map<String, Object>)entry.getValue();
				ProcModTacheServiceVo procModTacheService = new ProcModTacheServiceVo();
				BeanUtils.populate(procModTacheService, mapVo);
				List<ProcModTacheServiceVo> procModTacheServiceList = procModTacheServiceServDu.queryProcModTacheServiceList(procModTacheService, pageNo, pageSize);
				procModTacheServiceListCount = procModTacheServiceServDu.queryProcModTacheServiceListCount(procModTacheService);
				int totalPagesTacheService = (procModTacheServiceListCount + pageSize -1) / pageSize;
				if(procModTacheServiceListCount > 0){
					json_out.put("totalPagesProcModTacheService", totalPagesTacheService);
					json_out.put("totalNumeProcModTacheService", procModTacheServiceListCount);
					json_out.put("procModTacheService", procModTacheServiceList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}
			}
			 if(entry.getKey().equals(Constant.PROC_MOD_TACHE_BTN)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo=(Map<String, Object>)entry.getValue();
				ProcModTacheBtnVo procModTacheBtn = new ProcModTacheBtnVo();
				BeanUtils.populate(procModTacheBtn, mapVo);
				List<ProcModTacheBtnVo> procModTacheBtnList = procModTacheBtnServDu.queryProcModTacheBtnList(procModTacheBtn, pageNo, pageSize);
				procModTacheBtnListCount = procModTacheBtnServDu.queryProcModTacheBtnListCount(procModTacheBtn);
				int totalPagesTacheBtn = (procModTacheBtnListCount + pageSize -1) / pageSize;
				if(procModTacheBtnListCount > 0){
					json_out.put("totalPagesProcModTacheBtn", totalPagesTacheBtn);
					json_out.put("totalNumeProcModTacheBtn", procModTacheBtnListCount);
					json_out.put("procModTacheBtn", procModTacheBtnList);
				}else{
					uocMessage.setContent("查找结果为空");
					uocMessage.setRespCode(respCode.SUCCESS);
					return uocMessage;
				}

			}
			 if(entry.getKey().equals(Constant.PROC_MOD_TACHE)){
				@SuppressWarnings("unchecked")
				Map<String,Object> mapVo=(Map<String, Object>)entry.getValue();
				ProcModTacheVo procModTache = new ProcModTacheVo();
				BeanUtils.populate(procModTache, mapVo);
				List<ProcModTacheVo> procModTacheList = procModTacheServDu.queryProcModTacheList(procModTache, pageNo, pageSize);
				procModTacheListCount = procModTacheServDu.queryProcModTacheListCount(procModTache);
				int totalPagesTache = (procModTacheListCount + pageSize -1) / pageSize;
				if(procModTacheListCount > 0){
					json_out.put("totalPagesProcModTache", totalPagesTache);
					json_out.put("totalNumeProcModTache", procModTacheListCount);
					json_out.put("procModTache", procModTacheList);
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
	 * UOC0037 获取流程图信息
	 */
	@Override
	public UocMessage getProcViewInfo(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);

		String jsession_id = paraVo.getJsession_id();
		String order_no = paraVo.getOrder_no();
		String order_type = paraVo.getOrder_type();
		String cur_tache_code = StringUtils.isNotEmpty(paraVo.getTache_code()) ? paraVo.getTache_code() : "";
		String oper_code = "";
		String sale_order_no = "";
		if (jsession_id == null || jsession_id.equals("")) {
			uocMessage.setContent("jsession_id为必传参数");
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			return uocMessage;
		}
		if (order_no == null || order_no.equals("")) {
			uocMessage.setContent("order_no为必传参数");
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			return uocMessage;
		}
		if (order_type == null || order_type.equals("")) {
			uocMessage.setContent("order_type为必传参数");
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			return uocMessage;
		}

		// 1.调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
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

		// 5、如果是已竣工订单，数据需要从历史表中取
		if ("1".equals(paraVo.getFinish_flag())) {
			if (order_type.equals("100")) {
				InfoSaleOrderHisVo saleOrder = new InfoSaleOrderHisVo();
				saleOrder.setSale_order_no(order_no);
				InfoSaleOrderHisVo infoSaleOrder = infoSaleOrderHisServDu.getInfoSaleOrderHisBySaleOrderNo(saleOrder);
				if (infoSaleOrder == null) {
					logger.info("---------销售订单历史表查询为空---------order_no=" + order_no + ",order_type=" + order_type);
					uocMessage.setContent("销售订单历史表查询为空");
					return uocMessage;
				}
				sale_order_no = order_no;
				oper_code = infoSaleOrder.getOrder_type();
			} else if (order_type.equals("101")) {
				InfoServiceOrderHisVo serviceOrder = new InfoServiceOrderHisVo();
				serviceOrder.setServ_order_no(order_no);
				List<InfoServiceOrderHisVo> infoServiceOrderList = infoServiceOrderHisServDu
						.queryInfoServiceOrderHisByOrderNo(serviceOrder);
				if (infoServiceOrderList == null) {
					logger.info("---------服务订单历史表查询为空---------order_no=" + order_no + ",order_type=" + order_type);
					uocMessage.setContent("服务订单历史表查询为空");
					return uocMessage;
				}
				sale_order_no = infoServiceOrderList.get(0).getSale_order_no();
				oper_code = infoServiceOrderList.get(0).getOper_code();
			}

			if (StringUtils.isNotEmpty(oper_code)) {
				List<ProcViewInfoVo> procViewInfoList = queryProcViewList(oper_code);

				if (procViewInfoList != null && procViewInfoList.size() > 0) {
					// 3.取出流程图信息表数据后，需要再关联环节、订单号数据查询人工任务日志表(proc_inst_task_deal_record_h)
					List<ProcViewVo> proc_view_list = new ArrayList<ProcViewVo>();
					List<ProcLogVo> proc_log_list = new ArrayList<ProcLogVo>();
					String lastSeqNo = "0";
					for (ProcViewInfoVo procTemp : procViewInfoList) {
						String tache_code = procTemp.getTache_code();
						// 取环节配置表
						ProcModTacheVo procModTacheVo = new ProcModTacheVo();
						procModTacheVo.setTache_code(tache_code);
						ProcModTacheVo procModTacheVoRes = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);

						ProcInstTaskDealRecordVo lastRecordVo = queryLastTaskDealRecord(order_no, tache_code, order_type, sale_order_no,
								"1", "");
						if (lastRecordVo != null) {
							// 流程图信息
							ProcViewVo procViewVo = new ProcViewVo();
							procViewVo.setTache_code(tache_code);
							procViewVo.setSeq_no(procTemp.getSeq_no());
							procViewVo.setDeal_time(lastRecordVo.getDeal_time());
							procViewVo.setTache_name(procModTacheVoRes.getTache_name());
							proc_view_list.add(procViewVo);

							// 4.人工任务日志表信息
							ProcLogVo procLog = new ProcLogVo();
							procLog.setTache_code(tache_code);
							procLog.setTache_name(procModTacheVoRes.getTache_name());
							procLog.setOper_no(lastRecordVo.getDeal_oper_no());
							procLog.setDeal_code(lastRecordVo.getDeal_code());
							procLog.setDeal_desc(lastRecordVo.getDeal_desc());
							procLog.setDeal_time(lastRecordVo.getDeal_time());
							procLog.setCreate_time(lastRecordVo.getCreate_time());
							proc_log_list.add(procLog);
						} else {
							logger.info("----------人工任务日志历史表查询为空---------order_no=" + order_no + ",tache_code=" + tache_code);

							if (lastSeqNo.equals(procTemp.getSeq_no())) {
								continue;
							}

							// 查询是否撤单
							ProcInstTaskDealRecordVo cancelDealRecord = queryLastTaskDealRecord(order_no, "E00001", order_type,
									sale_order_no, "1", "");
							ProcInstTaskDealRecordVo cancelCheckDealRecord = queryLastTaskDealRecord(order_no, "E00002", order_type,
									sale_order_no, "1", "");
							if (cancelDealRecord != null && cancelCheckDealRecord != null) {
								// 流程图信息
								ProcViewVo cancelViewVo = new ProcViewVo();
								cancelViewVo.setTache_code("E00001");
								cancelViewVo.setSeq_no(String.valueOf((Integer.valueOf(lastSeqNo) + 1)));
								cancelViewVo.setDeal_time(cancelDealRecord.getDeal_time());
								cancelViewVo.setTache_name("撤单待审核");
								proc_view_list.add(cancelViewVo);

								ProcViewVo cancelCheckViewVo = new ProcViewVo();
								cancelCheckViewVo.setTache_code("E00002");
								cancelCheckViewVo.setSeq_no(String.valueOf((Integer.valueOf(lastSeqNo) + 2)));
								cancelCheckViewVo.setDeal_time(cancelCheckDealRecord.getDeal_time());
								cancelCheckViewVo.setTache_name("撤单审核通过");
								proc_view_list.add(cancelCheckViewVo);

								ProcViewVo finishViewVo = new ProcViewVo();
								finishViewVo.setTache_code("S00009");
								finishViewVo.setSeq_no(String.valueOf((Integer.valueOf(lastSeqNo) + 3)));
								finishViewVo.setDeal_time("");
								finishViewVo.setTache_name("服务订单竣工");
								proc_view_list.add(finishViewVo);

								// 4.人工任务日志表信息
								ProcLogVo cancelLog = new ProcLogVo();
								cancelLog.setTache_code("E00001");
								cancelLog.setTache_name("撤单待审核");
								cancelLog.setOper_no(cancelDealRecord.getDeal_oper_no());
								cancelLog.setDeal_code(cancelDealRecord.getDeal_code());
								cancelLog.setDeal_desc(cancelDealRecord.getDeal_desc());
								cancelLog.setDeal_time(cancelDealRecord.getDeal_time());
								cancelLog.setCreate_time(cancelDealRecord.getCreate_time());
								proc_log_list.add(cancelLog);

								ProcLogVo cancelCheckLog = new ProcLogVo();
								cancelCheckLog.setTache_code("E00002");
								cancelCheckLog.setTache_name("撤单审核通过");
								cancelCheckLog.setOper_no(cancelCheckDealRecord.getDeal_oper_no());
								cancelCheckLog.setDeal_code(cancelCheckDealRecord.getDeal_code());
								cancelCheckLog.setDeal_desc(cancelCheckDealRecord.getDeal_desc());
								cancelCheckLog.setDeal_time(cancelCheckDealRecord.getDeal_time());
								cancelCheckLog.setCreate_time(cancelCheckDealRecord.getCreate_time());
								proc_log_list.add(cancelCheckLog);

								break;
							} else {
								// 流程图信息
								ProcViewVo procViewVo = new ProcViewVo();
								procViewVo.setTache_code(tache_code);
								procViewVo.setSeq_no(procTemp.getSeq_no());
								procViewVo.setDeal_time("");
								procViewVo.setTache_name(procModTacheVoRes.getTache_name());
								proc_view_list.add(procViewVo);
							}

						}

						// 记录环节序号
						lastSeqNo = procTemp.getSeq_no();
					}
					uocMessage.addArg("proc_view_list", proc_view_list);
					uocMessage.addArg("proc_log_list", proc_log_list);

				} else {
					uocMessage.setContent("流程图信息表查询为空");
					return uocMessage;
				}
			} else {
				logger.info("---------业务类型查询为空---------");
				uocMessage.setContent("业务类型查询为空");
				return uocMessage;
			}
		} else {
			// 2.需要先从销售订单表或者服务订单表查询出业务类型，再关联流程应用表取出流程模板，最后取出流程图信息表数据
			if (order_type.equals("100")) {
				InfoSaleOrderVo saleOrder = new InfoSaleOrderVo();
				saleOrder.setSale_order_no(order_no);
				InfoSaleOrderVo infoSaleOrder = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(saleOrder);
				if (infoSaleOrder == null) {
					logger.info("---------销售订单表查询为空---------order_no=" + order_no + ",order_type=" + order_type);
					uocMessage.setContent("销售订单表查询为空");
					return uocMessage;
				}
				sale_order_no = order_no;
				oper_code = infoSaleOrder.getOrder_type();
			} else if (order_type.equals("101")) {
				InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
				infoServiceOrderVo.setServ_order_no(order_no);
				List<InfoServiceOrderVo> infoServiceOrderList = infoServiceOrderServDu.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
				if (infoServiceOrderList == null) {
					logger.info("---------服务订单表查询为空---------order_no=" + order_no + ",order_type=" + order_type);
					uocMessage.setContent("服务订单表查询为空");
					return uocMessage;
				}
				sale_order_no = infoServiceOrderList.get(0).getSale_order_no();
				oper_code = infoServiceOrderList.get(0).getOper_code();
			}

			if (StringUtils.isNotEmpty(oper_code)) {
				List<ProcViewInfoVo> procViewInfoList = queryProcViewList(oper_code);

				if (procViewInfoList != null && procViewInfoList.size() > 0) {
					// 3.取出流程图信息表数据后，需要再关联环节、订单号数据查询人工任务日志表(proc_inst_task_deal_record)
					List<ProcViewVo> proc_view_list = new ArrayList<ProcViewVo>();
					List<ProcLogVo> proc_log_list = new ArrayList<ProcLogVo>();
					List<ProcViewVo> cancel_view_list = new ArrayList<ProcViewVo>();
					List<ProcLogVo> cancel_log_list = new ArrayList<ProcLogVo>();
					Integer lastSeqNo = 0;
					Integer procSeqNo = 0;
					String cancel_tache_code = "";

					InfoOrderCancelVo cancelVo = new InfoOrderCancelVo();
					cancelVo.setOrder_no(order_no);
					cancelVo.setOrder_type(order_type);
					cancelVo = infoOrderCancelServDu.queryInfoOrderCancel(cancelVo);
					if (cancelVo != null) {
						cancel_tache_code = cancelVo.getCancel_tache_code();
					}
					ProcInstTaskDealRecordVo secondRecordVo = queryLastTaskDealRecord(order_no, cancel_tache_code, order_type, sale_order_no, "0", "");

					ProcInstTaskDealRecordVo cancelDealRecord = queryLastTaskDealRecord(order_no, "E00001", order_type, sale_order_no, "0", "");
					ProcInstTaskDealRecordVo cancelCheckDealRecord = queryLastTaskDealRecord(order_no, "E00002", order_type, sale_order_no,	"0", "");


					// 撤单不通过返回原环节 特殊处理
					if (!("S00009").equals(cur_tache_code) && !("E00002").equals(cur_tache_code) && cancelDealRecord != null && cancelCheckDealRecord != null) {

						if ("".equals(cancel_tache_code)) {
							uocMessage.setContent("撤单表中未查到撤单环节");
							return uocMessage;
						}

						ProcModTacheVo procModTacheVo = new ProcModTacheVo();
						procModTacheVo.setTache_code(cancel_tache_code);
						ProcModTacheVo cancelTacheVoRes = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);

						ProcViewVo firstViewVo = new ProcViewVo();
						firstViewVo.setTache_code(cancel_tache_code);
						firstViewVo.setSeq_no(String.valueOf(procSeqNo + 1));
						firstViewVo.setDeal_time(cancelDealRecord.getDeal_time());
						firstViewVo.setTache_name(cancelTacheVoRes.getTache_name());
						cancel_view_list.add(firstViewVo);

						ProcViewVo cancelViewVo = new ProcViewVo();
						cancelViewVo.setTache_code("E00001");
						cancelViewVo.setSeq_no(String.valueOf(procSeqNo + 2));
						cancelViewVo.setDeal_time(cancelDealRecord.getDeal_time());
						cancelViewVo.setTache_name("撤单待审核");
						cancel_view_list.add(cancelViewVo);

						ProcViewVo cancelCheckViewVo = new ProcViewVo();
						cancelCheckViewVo.setTache_code("E00002");
						cancelCheckViewVo.setSeq_no(String.valueOf(procSeqNo + 3));
						cancelCheckViewVo.setDeal_time(cancelCheckDealRecord.getDeal_time());
						cancelCheckViewVo.setTache_name("撤单审核不通过");
						cancel_view_list.add(cancelCheckViewVo);


						ProcViewVo secondViewVo = new ProcViewVo();
						secondViewVo.setTache_code(cancel_tache_code);
						secondViewVo.setSeq_no(String.valueOf(procSeqNo + 4));
						secondViewVo.setDeal_time(secondRecordVo != null && !"Auto".equals(secondRecordVo.getDeal_oper_no()) ? secondRecordVo.getDeal_time() : "");
						secondViewVo.setTache_name(cancelTacheVoRes.getTache_name());
						cancel_view_list.add(secondViewVo);

						// 人工任务日志表信息
						ProcLogVo firstLog = new ProcLogVo();
						firstLog.setTache_code(cancel_tache_code);
						firstLog.setTache_name(cancelTacheVoRes.getTache_name());
						firstLog.setOper_no("Auto");
						firstLog.setDeal_code("");
						firstLog.setDeal_desc("系统自动处理");
						firstLog.setDeal_time(cancelDealRecord.getDeal_time());
						firstLog.setCreate_time(cancelDealRecord.getCreate_time());
						cancel_log_list.add(firstLog);

						ProcLogVo cancelLog = new ProcLogVo();
						cancelLog.setTache_code("E00001");
						cancelLog.setTache_name("撤单待审核");
						cancelLog.setOper_no(cancelDealRecord.getDeal_oper_no());
						cancelLog.setDeal_code(cancelDealRecord.getDeal_code());
						cancelLog.setDeal_desc(cancelDealRecord.getDeal_desc());
						cancelLog.setDeal_time(cancelDealRecord.getDeal_time());
						cancelLog.setCreate_time(cancelDealRecord.getCreate_time());
						cancel_log_list.add(cancelLog);

						ProcLogVo cancelCheckLog = new ProcLogVo();
						cancelCheckLog.setTache_code("E00002");
						cancelCheckLog.setTache_name("撤单审核不通过");
						cancelCheckLog.setOper_no(cancelCheckDealRecord.getDeal_oper_no());
						cancelCheckLog.setDeal_code(cancelCheckDealRecord.getDeal_code());
						cancelCheckLog.setDeal_desc(cancelCheckDealRecord.getDeal_desc());
						cancelCheckLog.setDeal_time(cancelCheckDealRecord.getDeal_time());
						cancelCheckLog.setCreate_time(cancelCheckDealRecord.getCreate_time());
						cancel_log_list.add(cancelCheckLog);

						if (secondRecordVo != null && !"Auto".equals(secondRecordVo.getDeal_oper_no())) {
							ProcLogVo secondLog = new ProcLogVo();
							secondLog.setTache_code(cancel_tache_code);
							secondLog.setTache_name(cancelTacheVoRes.getTache_name());
							secondLog.setOper_no(secondRecordVo.getDeal_oper_no());
							secondLog.setDeal_code(secondRecordVo.getDeal_code());
							secondLog.setDeal_desc(secondRecordVo.getDeal_desc());
							secondLog.setDeal_time(secondRecordVo.getDeal_time());
							secondLog.setCreate_time(secondRecordVo.getCreate_time());
							cancel_log_list.add(secondLog);
						}

						lastSeqNo = lastSeqNo + 4;
					}

					for (ProcViewInfoVo procTemp : procViewInfoList) {
						String tache_code = procTemp.getTache_code();

						if (cancel_tache_code.equals(tache_code)) {
							proc_view_list.addAll(cancel_view_list);
							proc_log_list.addAll(cancel_log_list);
							continue;
						}

						// 取环节配置表
						ProcModTacheVo procModTacheVo = new ProcModTacheVo();
						procModTacheVo.setTache_code(tache_code);
						ProcModTacheVo procModTacheVoRes = procModTacheServDu.queryProcModTacheVoByTacheCode(procModTacheVo);

						ProcInstTaskDealRecordVo lastRecordVo = queryLastTaskDealRecord(order_no, tache_code, order_type, sale_order_no, "0", "");
						if (lastRecordVo != null) {
							// 流程图信息
							ProcViewVo procViewVo = new ProcViewVo();
							procViewVo.setTache_code(tache_code);
							procViewVo.setSeq_no(procTemp.getSeq_no());
							procViewVo.setDeal_time(lastRecordVo.getDeal_time());
							procViewVo.setTache_name(procModTacheVoRes.getTache_name());
							proc_view_list.add(procViewVo);

							// 4.人工任务日志表信息
							ProcLogVo procLog = new ProcLogVo();
							procLog.setTache_code(tache_code);
							procLog.setTache_name(procModTacheVoRes.getTache_name());
							procLog.setOper_no(lastRecordVo.getDeal_oper_no());
							procLog.setDeal_code(lastRecordVo.getDeal_code());
							procLog.setDeal_desc(lastRecordVo.getDeal_desc());
							procLog.setDeal_time(lastRecordVo.getDeal_time());
							procLog.setCreate_time(lastRecordVo.getCreate_time());
							proc_log_list.add(procLog);
							// 记录环节序号
							lastSeqNo = lastSeqNo + 1;
							procSeqNo = Integer.valueOf(procTemp.getSeq_no());
						} else {
							logger.info("----------人工任务日志表查询为空---------order_no=" + order_no + ",tache_code=" + tache_code);

							if (order_type.equals("101")) {
								if (("E00002").equals(cur_tache_code)) {
									if (cancelDealRecord == null) {
										logger.info("人工任务日志表未查询到环节为E00001的订单order_no=" + order_no);
										uocMessage.setContent("人工任务日志表未查询到环节为E00001的订单");
										return uocMessage;
									}

									// 流程图信息
									ProcViewVo cancelViewVo = new ProcViewVo();
									cancelViewVo.setTache_code("E00001");
									cancelViewVo.setSeq_no(String.valueOf(lastSeqNo + 1));
									cancelViewVo.setDeal_time(cancelDealRecord.getDeal_time());
									cancelViewVo.setTache_name("撤单待审核");
									proc_view_list.add(cancelViewVo);

									ProcViewVo cancelCheckViewVo = new ProcViewVo();
									cancelCheckViewVo.setTache_code(cur_tache_code);
									cancelCheckViewVo.setSeq_no(String.valueOf(lastSeqNo + 2));
									cancelCheckViewVo.setDeal_time("");
									cancelCheckViewVo.setTache_name("撤单审核");
									proc_view_list.add(cancelCheckViewVo);

									ProcViewVo finishViewVo = new ProcViewVo();
									finishViewVo.setTache_code("S00009");
									finishViewVo.setSeq_no(String.valueOf(lastSeqNo + 3));
									finishViewVo.setDeal_time("");
									finishViewVo.setTache_name("服务订单竣工");
									proc_view_list.add(finishViewVo);

									// 4.人工任务日志表信息
									ProcLogVo cancelLog = new ProcLogVo();
									cancelLog.setTache_code("E00001");
									cancelLog.setTache_name("撤单待审核");
									cancelLog.setOper_no(cancelDealRecord.getDeal_oper_no());
									cancelLog.setDeal_code(cancelDealRecord.getDeal_code());
									cancelLog.setDeal_desc(cancelDealRecord.getDeal_desc());
									cancelLog.setDeal_time(cancelDealRecord.getDeal_time());
									cancelLog.setCreate_time(cancelDealRecord.getCreate_time());
									proc_log_list.add(cancelLog);

									lastSeqNo = lastSeqNo + 3;

									break;
								} else if (("S00009").equals(cur_tache_code) && cancelDealRecord != null && cancelCheckDealRecord != null) {
									// 流程图信息
									ProcViewVo cancelViewVo = new ProcViewVo();
									cancelViewVo.setTache_code("E00001");
									cancelViewVo.setSeq_no(String.valueOf(lastSeqNo + 1));
									cancelViewVo.setDeal_time(cancelDealRecord.getDeal_time());
									cancelViewVo.setTache_name("撤单待审核");
									proc_view_list.add(cancelViewVo);

									ProcViewVo cancelCheckViewVo = new ProcViewVo();
									cancelCheckViewVo.setTache_code("E00002");
									cancelCheckViewVo.setSeq_no(String.valueOf(lastSeqNo + 2));
									cancelCheckViewVo.setDeal_time(cancelCheckDealRecord.getDeal_time());
									cancelCheckViewVo.setTache_name("撤单审核通过");
									proc_view_list.add(cancelCheckViewVo);

									ProcViewVo finishViewVo = new ProcViewVo();
									finishViewVo.setTache_code("S00009");
									finishViewVo.setSeq_no(String.valueOf(lastSeqNo + 3));
									finishViewVo.setDeal_time("");
									finishViewVo.setTache_name("服务订单竣工");
									proc_view_list.add(finishViewVo);

									// 4.人工任务日志表信息
									ProcLogVo cancelLog = new ProcLogVo();
									cancelLog.setTache_code("E00001");
									cancelLog.setTache_name("撤单待审核");
									cancelLog.setOper_no(cancelDealRecord.getDeal_oper_no());
									cancelLog.setDeal_code(cancelDealRecord.getDeal_code());
									cancelLog.setDeal_desc(cancelDealRecord.getDeal_desc());
									cancelLog.setDeal_time(cancelDealRecord.getDeal_time());
									cancelLog.setCreate_time(cancelDealRecord.getCreate_time());
									proc_log_list.add(cancelLog);

									ProcLogVo cancelCheckLog = new ProcLogVo();
									cancelCheckLog.setTache_code("E00002");
									cancelCheckLog.setTache_name("撤单审核通过");
									cancelCheckLog.setOper_no(cancelCheckDealRecord.getDeal_oper_no());
									cancelCheckLog.setDeal_code(cancelCheckDealRecord.getDeal_code());
									cancelCheckLog.setDeal_desc(cancelCheckDealRecord.getDeal_desc());
									cancelCheckLog.setDeal_time(cancelCheckDealRecord.getDeal_time());
									cancelCheckLog.setCreate_time(cancelCheckDealRecord.getCreate_time());
									proc_log_list.add(cancelCheckLog);

									break;
								} else if ((tache_code).equals(cur_tache_code)) {
									lastSeqNo = procSeqNo == (Integer.valueOf(procTemp.getSeq_no())) ? lastSeqNo : (lastSeqNo + 1);
									procSeqNo = Integer.valueOf(procTemp.getSeq_no());
									// 流程图信息
									ProcViewVo procViewVo = new ProcViewVo();
									procViewVo.setTache_code(tache_code);
									procViewVo.setSeq_no(String.valueOf(lastSeqNo));
									procViewVo.setDeal_time("");
									procViewVo.setTache_name(procModTacheVoRes.getTache_name());
									proc_view_list.add(procViewVo);
									continue;
								} else if (procSeqNo==(Integer.valueOf(procTemp.getSeq_no()))) {
									continue;
								}
							}

							// 记录环节序号
							lastSeqNo = procSeqNo == (Integer.valueOf(procTemp.getSeq_no())) ? lastSeqNo : (lastSeqNo + 1);
							procSeqNo = Integer.valueOf(procTemp.getSeq_no());

							// 流程图信息
							ProcViewVo procViewVo = new ProcViewVo();
							procViewVo.setTache_code(tache_code);
							procViewVo.setSeq_no(String.valueOf(lastSeqNo));
							procViewVo.setDeal_time("");
							procViewVo.setTache_name(procModTacheVoRes.getTache_name());
							proc_view_list.add(procViewVo);
						}
					}
					uocMessage.addArg("proc_view_list", proc_view_list);
					uocMessage.addArg("proc_log_list", proc_log_list);

				} else {
					uocMessage.setContent("流程图信息表查询为空");
					return uocMessage;
				}
			} else {
				logger.info("---------业务类型查询为空---------");
				uocMessage.setContent("业务类型查询为空");
				return uocMessage;
			}
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("获取流程图信息成功");
		return uocMessage;
	}

	private List<ProcViewInfoVo> queryProcViewList(String oper_code) throws Exception {
		List<ProcViewInfoVo> procViewInfoList=null;
		ProcModAppVo procMapAppVo = new ProcModAppVo();
		procMapAppVo.setOper_code(oper_code);
		procMapAppVo = procModAppServDu.queryProcModAppByOperCode(procMapAppVo);
		if (procMapAppVo != null && StringUtils.isNotEmpty(procMapAppVo.getProc_mod_code())) {
			ProcViewInfoVo procViewinfo = new ProcViewInfoVo();
			procViewinfo.setProc_mod_code(procMapAppVo.getProc_mod_code());
			procViewInfoList = procViewInfoServDu.queryProcViewInfoByProc(procViewinfo);

			// 按seq_no排序
			Collections.sort(procViewInfoList, new Comparator<ProcViewInfoVo>() {
				@Override
				public int compare(ProcViewInfoVo arg0, ProcViewInfoVo arg1) {
					if (Integer.valueOf(arg0.getSeq_no()).compareTo(Integer.valueOf(arg1.getSeq_no())) == 0) {
						return Integer.valueOf(arg0.getId()).compareTo(Integer.valueOf(arg1.getId()));
					} else {
						return Integer.valueOf(arg0.getSeq_no()).compareTo(Integer.valueOf(arg1.getSeq_no()));
					}
				}
			});
		}else{
			logger.info("---------流程应用表流程模板查询为空---------oper_code=" + oper_code);
		}

		return procViewInfoList;
	}

	@Override
	public ProcInstTaskDealRecordVo queryLastTaskDealRecord(String order_no, String tache_code, String order_type, String sale_order_no,
			String finish_flag, String oper_code) throws Exception {
		ProcInstTaskDealRecordVo resultVo = new ProcInstTaskDealRecordVo();

		ProcInstTaskDealRecordVo paramRecordVo = new ProcInstTaskDealRecordVo();
		paramRecordVo.setOrder_no(order_no);
		paramRecordVo.setTache_code(tache_code);
		paramRecordVo.setOrder_type(order_type);
		if (StringUtils.isNotEmpty(oper_code)) {
			paramRecordVo.setOper_code(oper_code);
		}
		List<ProcInstTaskDealRecordVo> procInstTaskDealRecordList =null;
		if("1".equals(finish_flag)){
			procInstTaskDealRecordList = procInstTaskDealRecordServDu.queryTaskDealRecordHisByOrderNo(paramRecordVo);
		}else{
			procInstTaskDealRecordList = procInstTaskDealRecordServDu.queryProcInstTaskDealRecordByOrderNo(paramRecordVo);
		}

		// 如果传入order_type=101，并且查人工任务日志表时如果查不到数据，则需要根据服务订单号取出销售订单号后，再额外多查询一次
		if (procInstTaskDealRecordList == null && ("101").equals(order_type)) {
			paramRecordVo.setOrder_no(sale_order_no);
			procInstTaskDealRecordList = procInstTaskDealRecordServDu.queryProcInstTaskDealRecordByOrderNo(paramRecordVo);
		}

		if (procInstTaskDealRecordList == null) {
			if ("1".equals(finish_flag)) {
				ProcInstTaskInstHisPo taskInstParam = new ProcInstTaskInstHisPo();
				taskInstParam.setOrder_no(("101").equals(order_type) ? order_no : sale_order_no);
				taskInstParam.setTache_code(tache_code);
				taskInstParam.setTask_state("102");
				if (StringUtils.isNotEmpty(oper_code)) {
					taskInstParam.setOper_code(oper_code);
				}
				List<ProcInstTaskInstHisPo> taskInstList = procInstTaskInstHisServ.queryTaskInstHisByOrderNo1(taskInstParam);
				if (taskInstList != null && taskInstList.size() > 0) {
					BeanUtils.copyProperties(resultVo, taskInstList.get(0));
					resultVo.setDeal_oper_no("Auto");
					resultVo.setDeal_depart_no("System");
					resultVo.setDeal_desc("系统自动处理");
					resultVo.setDeal_time(taskInstList.get(0).getCreate_time());
				} else {
					return null;
				}
			} else {
				ProcInstTaskInstPo taskInstParam = new ProcInstTaskInstPo();
				taskInstParam.setOrder_no(("101").equals(order_type) ? order_no : sale_order_no);
				taskInstParam.setTache_code(tache_code);
				taskInstParam.setTask_state("102");
				if (StringUtils.isNotEmpty(oper_code)) {
					taskInstParam.setOper_code(oper_code);
				}
				List<ProcInstTaskInstPo> taskInstList = procInstTaskInstServ.queryTaskInstByOrderNo1(taskInstParam);
				if (taskInstList != null && taskInstList.size() > 0) {
					BeanUtils.copyProperties(resultVo, taskInstList.get(0));
					resultVo.setDeal_oper_no("Auto");
					resultVo.setDeal_depart_no("System");
					resultVo.setDeal_desc("系统自动处理");
					resultVo.setDeal_time(taskInstList.get(0).getCreate_time());
				} else {
					return null;
				}
			}
		} else {
			if (procInstTaskDealRecordList.size() > 1) {
				for (ProcInstTaskDealRecordVo vo : procInstTaskDealRecordList) {
					if (!"Auto".equals(vo.getDeal_oper_no())) {
						BeanUtils.copyProperties(resultVo, vo);

						UocMessage departMsg = getDepartAndOperInfoServDu.getDepartInfoByOperNo(resultVo.getDeal_oper_no());
						if (RespCodeContents.SUCCESS.equals(departMsg.getRespCode())) {
							String json_info = (String) departMsg.getArgs().get("json_info");
							if (StringUtils.isEmpty(json_info)) {
								logger.info("--------未查询到该工号所在部门--------oper_no=" + resultVo.getDeal_oper_no());
								resultVo.setDeal_depart_no("");
							} else {
								Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
								resultVo.setDeal_depart_no((String) map.get("depart_no"));
							}
						} else {
							logger.info("--------未查询到该工号所在部门--------oper_no=" + resultVo.getDeal_oper_no());
							resultVo.setDeal_depart_no("");
						}

						return resultVo;
					}
				}
			} else {
				BeanUtils.copyProperties(resultVo, procInstTaskDealRecordList.get(0));

				if ("Auto".equals(resultVo.getDeal_oper_no())) {
					resultVo.setDeal_depart_no("System");
				} else {
					UocMessage departMsg = getDepartAndOperInfoServDu.getDepartInfoByOperNo(resultVo.getDeal_oper_no());
					if (RespCodeContents.SUCCESS.equals(departMsg.getRespCode())) {
						String json_info = (String) departMsg.getArgs().get("json_info");
						if (StringUtils.isEmpty(json_info)) {
							logger.info("--------未查询到该工号所在部门--------oper_no=" + resultVo.getDeal_oper_no());
							resultVo.setDeal_depart_no("");
						} else {
							Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
							if (map.get("depart_no") != null) {
								resultVo.setDeal_depart_no((String) map.get("depart_no"));
							} else {
								logger.info("--------未查询到该工号所在部门--------oper_no=" + resultVo.getDeal_oper_no());
								resultVo.setDeal_depart_no("");
							}
						}
					} else {
						logger.info("--------未查询到该工号所在部门--------oper_no=" + resultVo.getDeal_oper_no());
						resultVo.setDeal_depart_no("");
					}
				}
			}

		}

		return resultVo;
	}


	/**
	 * UOCXXXX 环节关联工号表 查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchProcModTacheLoginList(ParaVo paraVo) throws Exception {
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
//		uocMessage operMesg = operServDu.isLogin(jsession_id);
//		if("success".equals(operMesg.getType().toString())){
//			Map<String,Object> operArgsMap = operMesg.getArgs();
//			@SuppressWarnings("unchecked")
//			Map<String,String> operInfoMap = (Map<String, String>) operArgsMap.get("oper_info");
//			province_code = operInfoMap.get("province_code");
//			area_code = operInfoMap.get("area_code");
//			depart_no = operInfoMap.get("depart_no");
//			depart_name = operInfoMap.get("depart_name");
//			oper_no = operInfoMap.get("oper_no");
//		}
//		else{
//			return operMesg;
//		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);
		List<ProcModTacheLoginVo> listlogin = null;
		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		ProcModTacheLoginVo ordVo = new ProcModTacheLoginVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);

		listlogin = procModTacheLoginServDu.queryProcModTacheLoginList(ordVo, pageNo, pageSize);
		totalNume = procModTacheLoginServDu.queryProcModTacheLoginListCount(ordVo);
		if(listlogin == null){
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
		json_out.put("procModTacheLogin", listlogin);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}

	/**
	 * UOCXXXX 环节关联服务表查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchProcModTacheServiceList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		String jsession_id = paraVo.getJsession_id();
//		String type = paraVo.getType();
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
//		uocMessage operMesg = operServDu.isLogin(jsession_id);
//		if("success".equals(operMesg.getType().toString())){
//			Map<String,Object> operArgsMap = operMesg.getArgs();
//			@SuppressWarnings("unchecked")
//			Map<String,String> operInfoMap = (Map<String, String>) operArgsMap.get("oper_info");
//			province_code = operInfoMap.get("province_code");
//			area_code = operInfoMap.get("area_code");
//			depart_no = operInfoMap.get("depart_no");
//			depart_name = operInfoMap.get("depart_name");
//			oper_no = operInfoMap.get("oper_no");
//		}
//		else{
//			return operMesg;
//		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);
		List<ProcModTacheServiceVo> listService = null;
		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		ProcModTacheServiceVo ordVo = new ProcModTacheServiceVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);


		listService = procModTacheServiceServDu.queryProcModTacheServiceList(ordVo, pageNo, pageSize);
		totalNume = procModTacheServiceServDu.queryProcModTacheServiceListCount(ordVo);
		if(listService == null){
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
		json_out.put("procModTacheService", listService);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}

	/**
	 * UOCXXXX 环节关联功能表查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchProcModTacheBtnList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		String jsession_id = paraVo.getJsession_id();
//		String type = paraVo.getType();
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
//		uocMessage operMesg = operServDu.isLogin(jsession_id);
//		if("success".equals(operMesg.getType().toString())){
//			Map<String,Object> operArgsMap = operMesg.getArgs();
//			@SuppressWarnings("unchecked")
//			Map<String,String> operInfoMap = (Map<String, String>) operArgsMap.get("oper_info");
//			province_code = operInfoMap.get("province_code");
//			area_code = operInfoMap.get("area_code");
//			depart_no = operInfoMap.get("depart_no");
//			depart_name = operInfoMap.get("depart_name");
//			oper_no = operInfoMap.get("oper_no");
//		}
//		else{
//			return operMesg;
//		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);
		List<ProcModTacheBtnVo> listBtn = null;
		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		ProcModTacheBtnVo ordVo = new ProcModTacheBtnVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);


		listBtn = procModTacheBtnServDu.queryProcModTacheBtnList(ordVo, pageNo, pageSize);
		totalNume = procModTacheBtnServDu.queryProcModTacheBtnListCount(ordVo);
		if(listBtn == null){
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
		json_out.put("procModTacheBtn", listBtn);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}


	/**
	 * UOCXXXX 环节配置表查询，分页
	 */
	@SuppressWarnings("static-access")
	@Override
	public UocMessage searchProcModTacheList(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");

		String jsession_id = paraVo.getJsession_id();
//		String type = paraVo.getType();
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
//		uocMessage operMesg = operServDu.isLogin(jsession_id);
//		if("success".equals(operMesg.getType().toString())){
//			Map<String,Object> operArgsMap = operMesg.getArgs();
//			@SuppressWarnings("unchecked")
//			Map<String,String> operInfoMap = (Map<String, String>) operArgsMap.get("oper_info");
//			province_code = operInfoMap.get("province_code");
//			area_code = operInfoMap.get("area_code");
//			depart_no = operInfoMap.get("depart_no");
//			depart_name = operInfoMap.get("depart_name");
//			oper_no = operInfoMap.get("oper_no");
//		}
//		else{
//			return operMesg;
//		}

		Map<String,Object> mapObj = jsonToBeanServDu.jsonToMap(json_info);
		List<ProcModTacheVo> listTache = null;

		int pageNo = (int) mapObj.get("pageNo");
		int pageSize = (int) mapObj.get("pageSize");
		int totalNume = 0;
		ProcModTacheVo ordVo = new ProcModTacheVo();
		@SuppressWarnings("unchecked")
		Map<String,Object> mapVo=(Map<String, Object>) mapObj.get("param");
		BeanUtils.populate(ordVo, mapVo);


		listTache = procModTacheServDu.queryProcModTacheList(ordVo, pageNo, pageSize);
		totalNume = procModTacheServDu.queryProcModTacheListCount(ordVo);
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
		json_out.put("procModTache", listTache);
		uocMessage.setRespCode(respCode.SUCCESS);
		uocMessage.setContent("操作成功");
		uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}

	private void getBeanDu(){
		if(operServDu == null){
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if(jsonToBeanServDu == null){
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if(getIdServDu == null){
			getIdServDu = (GetIdServDu) ToolSpring.getBean("GetIdServDu");
		}
		if(ordModAttrCheckRuleServDu == null){
			ordModAttrCheckRuleServDu = (OrdModAttrCheckRuleServDu) ToolSpring
					.getBean("OrdModAttrCheckRuleServDu");
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
		if(procModTacheServiceServDu == null){
			procModTacheServiceServDu = (ProcModTacheServiceServDu) ToolSpring.getBean("ProcModTacheServiceServDu");
		}
		if(procModTacheBtnServDu == null){
			procModTacheBtnServDu = (ProcModTacheBtnServDu) ToolSpring.getBean("ProcModTacheBtnServDu");
		}
		if(procModTacheServDu == null){
			procModTacheServDu = (ProcModTacheServDu) ToolSpring.getBean("ProcModTacheServDu");
		}
		if(procModAppServDu == null){
			procModAppServDu = (ProcModAppServDu) ToolSpring.getBean("ProcModAppServDu");
		}
		if(procModTacheLoginServDu == null){
			procModTacheLoginServDu = (ProcModTacheLoginServDu) ToolSpring.getBean("ProcModTacheLoginServDu");
		}
		if(infoServiceOrderServDu == null){
			infoServiceOrderServDu = (InfoServiceOrderServDu) ToolSpring.getBean("InfoServiceOrderServDu");
		}
		if(infoServiceOrderHisServDu == null){
			infoServiceOrderHisServDu = (InfoServiceOrderHisServDu) ToolSpring.getBean("InfoServiceOrderHisServDu");
		}
		if(procViewInfoServDu == null){
			procViewInfoServDu = (ProcViewInfoServDu) ToolSpring.getBean("ProcViewInfoServDu");
		}
		if (procInstTaskDealRecordServDu == null) {
			procInstTaskDealRecordServDu = (ProcInstTaskDealRecordServDu) ToolSpring.getBean("ProcInstTaskDealRecordServDu");
		}
		if (procInstTaskInstServ == null) {
			procInstTaskInstServ = (ProcInstTaskInstServ) ToolSpring.getBean("ProcInstTaskInstServ");
		}
		if (procInstTaskInstHisServ == null) {
			procInstTaskInstHisServ = (ProcInstTaskInstHisServ) ToolSpring.getBean("ProcInstTaskInstHisServ");
		}
	}


}
