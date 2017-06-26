package com.tydic.unicom.service.cache.service.redis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisInitServ;
import com.tydic.unicom.service.cache.service.redis.po.CodeList;
import com.tydic.unicom.service.cache.service.redis.po.CodeListMap;
import com.tydic.unicom.service.cache.service.redis.po.CodeListTabField;
import com.tydic.unicom.service.cache.service.redis.po.CodeRegion;
import com.tydic.unicom.service.cache.service.redis.po.CodeType;
import com.tydic.unicom.service.cache.service.redis.po.OrdModApp;
import com.tydic.unicom.service.cache.service.redis.po.OrdModAttrCheckRule;
import com.tydic.unicom.service.cache.service.redis.po.OrdModAttrDefine;
import com.tydic.unicom.service.cache.service.redis.po.OrdModCheckRule;
import com.tydic.unicom.service.cache.service.redis.po.OrdModDefine;
import com.tydic.unicom.service.cache.service.redis.po.OrdModMutiTable;
import com.tydic.unicom.service.cache.service.redis.po.OrdModOperSplitRule;
import com.tydic.unicom.service.cache.service.redis.po.OrdModParaFieldRelation;
import com.tydic.unicom.service.cache.service.redis.po.OrdModStateRule;
import com.tydic.unicom.service.cache.service.redis.po.OrdModTacheRule;
import com.tydic.unicom.service.cache.service.redis.po.ProcFlowParam;
import com.tydic.unicom.service.cache.service.redis.po.ProcModApp;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTache;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTacheBtn;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTacheLogin;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTacheService;
import com.tydic.unicom.service.cache.service.redis.po.ProcTaskAssignRule;
import com.tydic.unicom.service.cache.service.redis.po.ProcViewInfo;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.service.cache.service.redis.po.RuleAttrDefine;
import com.tydic.unicom.service.cache.service.redis.po.RuleDefine;
import com.tydic.unicom.service.cache.service.redis.po.RuleInstance;
import com.tydic.unicom.service.cache.service.redis.po.RuleType;

public class RedisInitServImpl implements RedisInitServ{

	private static Logger logger = Logger.getLogger(RedisInitServImpl.class);

	private String[] keys = {"code_list","code_list_map","code_list_tab_field","code_region","code_type","ord_mod_app",
			"ord_attr_check_rule","ord_attr_define","ord_check_rule","ord_mod_define","proc_task_assign_rule",
			"ord_oper_split_rule","ord_para_field_relation","ord_state_rule","ord_tache_rule",
			"proc_flow_param","proc_mod_app","proc_tache","proc_tache_btn","proc_tache_login",
			"proc_tache_service","proc_view_info","rule_attr_define","rule_define","rule_instance","rule_type"};

	@SuppressWarnings("unchecked")
	@Override
	public boolean initCreateDataToRedis() {
		logger.info("================初始化缓存数据到redis==============");

		//缓存code_list表数据
		List<CodeList> codeListList = S.get(CodeList.class).query(null);
		if(codeListList != null && codeListList.size()>0){
			logger.info("=======================codeList表需要插入的数据条数为："+codeListList.size());
			Map<String,Object> codeListMap = new HashMap<String,Object>();
			codeListMap.put("code_list", codeListList);
			for(int i=0;i<codeListList.size();i++){
				codeListMap.put(codeListList.get(i).getCode_id(), codeListList.get(i));
				if(codeListMap.containsKey(codeListList.get(i).getType_code())){
					List<CodeList> codeListTypeCodeUpdateList = (List<CodeList>) codeListMap.get(codeListList.get(i).getType_code());
					codeListTypeCodeUpdateList.add(codeListList.get(i));
					codeListMap.put(codeListList.get(i).getType_code(), codeListTypeCodeUpdateList);
				} else {
					List<CodeList> codeListTypeCodeAddList = new ArrayList<CodeList>();
					codeListTypeCodeAddList.add(codeListList.get(i));
					codeListMap.put(codeListList.get(i).getType_code(), codeListTypeCodeAddList);
				}

				if (codeListMap.containsKey(codeListList.get(i).getType_code() + "_" + codeListList.get(i).getParent_code_id())) {
					List<CodeList> codeListTypeCodeUpdateList = (List<CodeList>) codeListMap.get(codeListList.get(i).getType_code() + "_"
							+ codeListList.get(i).getParent_code_id());
					codeListTypeCodeUpdateList.add(codeListList.get(i));
					codeListMap.put(codeListList.get(i).getType_code() + "_" + codeListList.get(i).getParent_code_id(),
							codeListTypeCodeUpdateList);
				} else {
					List<CodeList> codeListTypeCodeAddList = new ArrayList<CodeList>();
					codeListTypeCodeAddList.add(codeListList.get(i));
					codeListMap.put(codeListList.get(i).getType_code() + "_" + codeListList.get(i).getParent_code_id(),
							codeListTypeCodeAddList);
				}
			}
			writeDataToRedis(codeListMap,"code_list");
		}
		else{
			logger.info("=================没有查询到codelist中的数据============");
		}
		//缓存code_list_map表数据
		List<CodeListMap> CodeListMapList = S.get(CodeListMap.class).query(null);
		if(CodeListMapList != null && CodeListMapList.size()>0){
			logger.info("=======================code_list_map表需要插入的数据条数为："+CodeListMapList.size());
			Map<String,Object> codeListMapMap = new HashMap<String,Object>();
			codeListMapMap.put("code_list_map", CodeListMapList);
			writeDataToRedis(codeListMapMap,"code_list_map");
		}
		else{
			logger.info("=================没有查询到code_list_map中的数据============");
		}
		//缓存code_list_tab_field表数据
		List<CodeListTabField> CodeListTabFieldList = S.get(CodeListTabField.class).query(null);
		if(CodeListTabFieldList != null && CodeListTabFieldList.size()>0){
			logger.info("=======================code_list_tab_field表需要插入的数据条数为："+CodeListTabFieldList.size());
			Map<String,Object> codeListTabFieldMap = new HashMap<String,Object>();
			codeListTabFieldMap.put("code_list_tab_field", CodeListTabFieldList);
			writeDataToRedis(codeListTabFieldMap,"code_list_tab_field");
		}
		else{
			logger.info("=================没有查询到code_list_tab_field中的数据============");
		}
		//缓存proc_task_assign_rule表数据
		List<ProcTaskAssignRule> procTaskAssignRuleList = S.get(ProcTaskAssignRule.class).query(null);
		if(procTaskAssignRuleList != null && procTaskAssignRuleList.size()>0){
			logger.info("=======================proc_task_assign_rule表需要插入的数据条数为："+procTaskAssignRuleList.size());
			Map<String,Object> procTaskAssignRuleMap = new HashMap<String,Object>();
			procTaskAssignRuleMap.put("proc_task_assign_rule", procTaskAssignRuleList);
			for(int i=0;i<procTaskAssignRuleList.size();i++){
				procTaskAssignRuleMap.put(procTaskAssignRuleList.get(i).getProvince_code() + "_"
						+ procTaskAssignRuleList.get(i).getArea_code() + "_" + procTaskAssignRuleList.get(i).getTache_code() + "_"
						+ procTaskAssignRuleList.get(i).getOper_code() + "_" + procTaskAssignRuleList.get(i).getAccept_oper_no() + "_"
						+ procTaskAssignRuleList.get(i).getAccept_depart_no()+ "_"
						+ (StringUtils.isEmpty(procTaskAssignRuleList.get(i).getExt_cond1()) ? "*" : procTaskAssignRuleList.get(i).getExt_cond1()),
						procTaskAssignRuleList.get(i));
			}
			writeDataToRedis(procTaskAssignRuleMap,"proc_task_assign_rule");
		}
		else{
			logger.info("=================没有查询到proc_task_assign_rule中的数据============");
		}

		//缓存code_region表数据
		List<CodeRegion> CodeRegionList = S.get(CodeRegion.class).query(null);
		if(CodeRegionList != null && CodeRegionList.size()>0){
			logger.info("=======================code_region表需要插入的数据条数为："+CodeRegionList.size());
			Map<String,Object> codeRegionMap = new HashMap<String,Object>();
			codeRegionMap.put("code_region", CodeRegionList);
			for(int i=0;i<CodeRegionList.size();i++){
				codeRegionMap.put("RegionCode"+CodeRegionList.get(i).getRegion_code(), CodeRegionList.get(i));
				//根据UpperRegionId缓存数据
				if(codeRegionMap.containsKey("UpperRegionId"+CodeRegionList.get(i).getUpper_region_id())){
					List<CodeRegion> CodeRegionUpdateList = (List<CodeRegion>) codeRegionMap.get("UpperRegionId"+CodeRegionList.get(i).getUpper_region_id());
					CodeRegionUpdateList.add(CodeRegionList.get(i));
					codeRegionMap.put("UpperRegionId"+CodeRegionList.get(i).getUpper_region_id(), CodeRegionUpdateList);
				}
				else{
					List<CodeRegion> CodeRegionAddList = new ArrayList<CodeRegion>();
					CodeRegionAddList.add(CodeRegionList.get(i));
					codeRegionMap.put("UpperRegionId"+CodeRegionList.get(i).getUpper_region_id(), CodeRegionAddList);
				}
				//根据RegionLevel缓存数据
				if(codeRegionMap.containsKey("RegionLevel"+CodeRegionList.get(i).getRegion_level())){
					List<CodeRegion> CodeRegionUpdateRegionLevelList = (List<CodeRegion>) codeRegionMap.get("RegionLevel"+CodeRegionList.get(i).getRegion_level());
					CodeRegionUpdateRegionLevelList.add(CodeRegionList.get(i));
					codeRegionMap.put("RegionLevel"+CodeRegionList.get(i).getRegion_level(), CodeRegionUpdateRegionLevelList);
				}
				else{
					List<CodeRegion> CodeRegionAddRegionLevelList = new ArrayList<CodeRegion>();
					CodeRegionAddRegionLevelList.add(CodeRegionList.get(i));
					codeRegionMap.put("RegionLevel"+CodeRegionList.get(i).getRegion_level(), CodeRegionAddRegionLevelList);
				}
				//根据UpperRegionId+RegionLevel缓存数据
				if(codeRegionMap.containsKey(CodeRegionList.get(i).getUpper_region_id()+"_"+CodeRegionList.get(i).getRegion_level())){
					List<CodeRegion> CodeRegionUpdateUpperRegionIdRegionLevelList = (List<CodeRegion>) codeRegionMap.get(CodeRegionList.get(i).getUpper_region_id()+"_"+CodeRegionList.get(i).getRegion_level());
					CodeRegionUpdateUpperRegionIdRegionLevelList.add(CodeRegionList.get(i));
					codeRegionMap.put(CodeRegionList.get(i).getUpper_region_id()+"_"+CodeRegionList.get(i).getRegion_level(), CodeRegionUpdateUpperRegionIdRegionLevelList);
				}
				else{
					List<CodeRegion> CodeRegionAddUpperRegionIdRegionLevelList = new ArrayList<CodeRegion>();
					CodeRegionAddUpperRegionIdRegionLevelList.add(CodeRegionList.get(i));
					codeRegionMap.put(CodeRegionList.get(i).getUpper_region_id()+"_"+CodeRegionList.get(i).getRegion_level(), CodeRegionAddUpperRegionIdRegionLevelList);
				}
			}
			writeDataToRedis(codeRegionMap,"code_region");
		}
		else{
			logger.info("=================没有查询到code_region中的数据============");
		}
		//缓存code_type表数据
		List<CodeType> CodeTypeList = S.get(CodeType.class).query(null);
		if(CodeTypeList != null && CodeTypeList.size()>0){
			logger.info("=======================code_type表需要插入的数据条数为："+CodeTypeList.size());
			Map<String,Object> codeTypeMap = new HashMap<String,Object>();
			codeTypeMap.put("code_type", CodeTypeList);
			writeDataToRedis(codeTypeMap,"code_type");
		}
		else{
			logger.info("=================没有查询到code_type中的数据============");
		}
		//缓存ord_mod_app表数据
		List<OrdModApp> OrdModAppList = S.get(OrdModApp.class).query(null);
		if(OrdModAppList != null && OrdModAppList.size()>0){
			logger.info("=======================ord_mod_app表需要插入的数据条数为："+OrdModAppList.size());
			Map<String,Object> ordModAppMap = new HashMap<String,Object>();
			ordModAppMap.put("ord_mod_app", OrdModAppList);
			writeDataToRedis(ordModAppMap,"ord_mod_app");
		}
		else{
			logger.info("=================没有查询到ord_mod_app中的数据============");
		}
		//缓存ord_mod_attr_check_rule表数据
		List<OrdModAttrCheckRule> OrdModAttrCheckRuleList = S.get(OrdModAttrCheckRule.class).query(null);
		if(OrdModAttrCheckRuleList != null && OrdModAttrCheckRuleList.size()>0){
			logger.info("=======================ord_mod_attr_check_rule表需要插入的数据条数为："+OrdModAttrCheckRuleList.size());
			Map<String,Object> ordModAttrCheckRuleMap = new HashMap<String,Object>();
			ordModAttrCheckRuleMap.put("ord_attr_check_rule", OrdModAttrCheckRuleList);
			writeDataToRedis(ordModAttrCheckRuleMap,"ord_attr_check_rule");
		}
		else{
			logger.info("=================没有查询到ord_mod_attr_check_rule中的数据============");
		}
		//缓存ord_attr_define表数据
		List<OrdModAttrDefine> OrdModAttrDefineList = S.get(OrdModAttrDefine.class).query(null);
		if(OrdModAttrDefineList != null && OrdModAttrDefineList.size()>0){
			logger.info("=======================ord_mod_attr_define表需要插入的数据条数为："+OrdModAttrDefineList.size());
			Map<String,Object> ordModAttrDefineMap = new HashMap<String,Object>();
			ordModAttrDefineMap.put("ord_attr_define", OrdModAttrDefineList);
			for(int i=0;i<OrdModAttrDefineList.size();i++){
				if(ordModAttrDefineMap.containsKey(OrdModAttrDefineList.get(i).getAttr_code())){
					List<OrdModAttrDefine> ordModAttrDefineUpdateList = (List<OrdModAttrDefine>) ordModAttrDefineMap.get(OrdModAttrDefineList.get(i).getAttr_code());
					ordModAttrDefineUpdateList.add(OrdModAttrDefineList.get(i));
					ordModAttrDefineMap.put(OrdModAttrDefineList.get(i).getAttr_code(), ordModAttrDefineUpdateList);
				}else{
					List<OrdModAttrDefine> ordModAttrDefineList = new ArrayList<OrdModAttrDefine>();
					ordModAttrDefineList.add(OrdModAttrDefineList.get(i));
					ordModAttrDefineMap.put(OrdModAttrDefineList.get(i).getAttr_code(), ordModAttrDefineList);
				}
			}
			writeDataToRedis(ordModAttrDefineMap,"ord_attr_define");
		}
		else{
			logger.info("=================没有查询到ord_mod_attr_define中的数据============");
		}
		//缓存ord_mod_check_rule表数据
		List<OrdModCheckRule> OrdModCheckRuleList = S.get(OrdModCheckRule.class).query(null);
		if(OrdModCheckRuleList != null && OrdModCheckRuleList.size()>0){
			logger.info("=======================ord_mod_check_rule表需要插入的数据条数为："+OrdModCheckRuleList.size());
			Map<String,Object> ordModCheckRuleMap = new HashMap<String,Object>();
			ordModCheckRuleMap.put("ord_check_rule", OrdModCheckRuleList);
			writeDataToRedis(ordModCheckRuleMap,"ord_check_rule");
		}
		else{
			logger.info("=================没有查询到ord_mod_check_rule中的数据============");
		}
		//缓存ord_mod_define表数据
		List<OrdModDefine> OrdModDefineList = S.get(OrdModDefine.class).query(null);
		if(OrdModDefineList != null && OrdModDefineList.size()>0){
			logger.info("=======================ord_mod_define表需要插入的数据条数为："+OrdModDefineList.size());
			Map<String,Object> ordModDefineMap = new HashMap<String,Object>();
			ordModDefineMap.put("ord_mod_define", OrdModDefineList);
			writeDataToRedis(ordModDefineMap,"ord_mod_define");
		}
		else{
			logger.info("=================没有查询到ord_mod_define中的数据============");
		}
		//缓存ord_mod_oper_split_rule表数据
		List<OrdModOperSplitRule> OrdModOperSplitRuleList = S.get(OrdModOperSplitRule.class).query(null);
		if(OrdModOperSplitRuleList != null && OrdModOperSplitRuleList.size()>0){
			logger.info("=======================ord_mod_oper_split_rule表需要插入的数据条数为："+OrdModOperSplitRuleList.size());
			Map<String,Object> ordModOperSplitRuleMap = new HashMap<String,Object>();
			ordModOperSplitRuleMap.put("ord_oper_split_rule", OrdModOperSplitRuleList);
			writeDataToRedis(ordModOperSplitRuleMap,"ord_oper_split_rule");
		}
		else{
			logger.info("=================没有查询到ord_mod_oper_split_rule中的数据============");
		}
		//缓存ord_para_field_relation表数据
		List<OrdModParaFieldRelation> OrdModParaFieldRelationList = S.get(OrdModParaFieldRelation.class).query(null);
		if(OrdModParaFieldRelationList != null && OrdModParaFieldRelationList.size()>0){
			logger.info("=======================ord_mod_para_field_relation表需要插入的数据条数为："+OrdModOperSplitRuleList.size());
			Map<String,Object> ordModParaFieldRelationMap = new HashMap<String,Object>();
			ordModParaFieldRelationMap.put("ord_para_field_relation", OrdModParaFieldRelationList);
			for(int i=0;i<OrdModParaFieldRelationList.size();i++){
				if(ordModParaFieldRelationMap.containsKey(OrdModParaFieldRelationList.get(i).getTable_name()+"_"+OrdModParaFieldRelationList.get(i).getField_name())){
					List<OrdModParaFieldRelation> ordModParaFieldRelationUpdateList = (List<OrdModParaFieldRelation>) ordModParaFieldRelationMap.get(OrdModParaFieldRelationList.get(i).getTable_name()+"_"+OrdModParaFieldRelationList.get(i).getField_name());
					ordModParaFieldRelationUpdateList.add(OrdModParaFieldRelationList.get(i));
					ordModParaFieldRelationMap.put(OrdModParaFieldRelationList.get(i).getTable_name()+"_"+OrdModParaFieldRelationList.get(i).getField_name(), ordModParaFieldRelationUpdateList);
				}else{
					List<OrdModParaFieldRelation> ordModParaFieldRelationList = new ArrayList<OrdModParaFieldRelation>();
					ordModParaFieldRelationList.add(OrdModParaFieldRelationList.get(i));
					ordModParaFieldRelationMap.put(OrdModParaFieldRelationList.get(i).getTable_name()+"_"+OrdModParaFieldRelationList.get(i).getField_name(), ordModParaFieldRelationList);
				}
			}
			writeDataToRedis(ordModParaFieldRelationMap,"ord_para_field_relation");
			//缓存ord_para_field_relation表 ,根据table_name,field_name

		}
		else{
			logger.info("=================没有查询到ord_mod_para_field_relation中的数据============");
		}
		//缓存ord_mod_state_rule表数据
		List<OrdModStateRule> OrdModStateRuleList = S.get(OrdModStateRule.class).query(null);
		if(OrdModStateRuleList != null && OrdModStateRuleList.size()>0){
			logger.info("=======================ord_mod_state_rule表需要插入的数据条数为："+OrdModStateRuleList.size());
			Map<String,Object> ordModStateRuleMap = new HashMap<String,Object>();
			ordModStateRuleMap.put("ord_state_rule", OrdModStateRuleList);
			writeDataToRedis(ordModStateRuleMap,"ord_state_rule");
		}
		else{
			logger.info("=================没有查询到ord_mod_state_rule中的数据============");
		}
		//缓存ord_mod_tache_rule表数据
		List<OrdModTacheRule> OrdModTacheRuleList = S.get(OrdModTacheRule.class).query(null);
		if(OrdModTacheRuleList != null && OrdModTacheRuleList.size()>0){
			logger.info("=======================ord_mod_tache_rule表需要插入的数据条数为："+OrdModTacheRuleList.size());
			Map<String,Object> ordModTacheRuleMap = new HashMap<String,Object>();
			ordModTacheRuleMap.put("ord_tache_rule", OrdModTacheRuleList);
			writeDataToRedis(ordModTacheRuleMap,"ord_tache_rule");
		}
		else{
			logger.info("=================没有查询到ord_mod_tache_rule中的数据============");
		}
		//缓存proc_flow_param表数据
		List<ProcFlowParam> ProcFlowParamList = S.get(ProcFlowParam.class).query(null);
		if(ProcFlowParamList != null && ProcFlowParamList.size()>0){
			logger.info("=======================proc_flow_param表需要插入的数据条数为："+ProcFlowParamList.size());
			Map<String,Object> procFlowParamMap = new HashMap<String,Object>();
			procFlowParamMap.put("proc_flow_param", ProcFlowParamList);
			writeDataToRedis(procFlowParamMap,"proc_flow_param");
		}
		else{
			logger.info("=================没有查询到proc_flow_param中的数据============");
		}
		//缓存proc_mod_app表数据
		List<ProcModApp> ProcModAppList = S.get(ProcModApp.class).query(null);
		if(ProcModAppList != null && ProcModAppList.size()>0){
			logger.info("=======================proc_mod_app表需要插入的数据条数为："+ProcModAppList.size());
			Map<String,Object> procModAppMap = new HashMap<String,Object>();
			procModAppMap.put("proc_mod_app", ProcModAppList);
			for(int i=0;i<ProcModAppList.size();i++){
				procModAppMap.put(ProcModAppList.get(i).getProvince_code()+"_"+ProcModAppList.get(i).getArea_code()+"_"+ProcModAppList.get(i).getOper_code(), ProcModAppList.get(i));
			}
			writeDataToRedis(procModAppMap,"proc_mod_app");
		}
		else{
			logger.info("=================没有查询到proc_mod_app中的数据============");
		}
		//缓存proc_mod_tache表数据
		List<ProcModTache> ProcModTacheList = S.get(ProcModTache.class).query(null);
		if(ProcModTacheList != null && ProcModTacheList.size()>0){
			logger.info("=======================proc_mod_tache表需要插入的数据条数为："+ProcModTacheList.size());
			Map<String,Object> procModTacheMap = new HashMap<String,Object>();
			procModTacheMap.put("proc_tache", ProcModTacheList);
			for(int i=0;i<ProcModTacheList.size();i++){
				if(procModTacheMap.containsKey(ProcModTacheList.get(i).getProvince_code()+"_"+ProcModTacheList.get(i).getArea_code())){
					List<ProcModTache> ProcModTacheUpdateList = (List<ProcModTache>) procModTacheMap.get(ProcModTacheList.get(i).getProvince_code()+"_"+ProcModTacheList.get(i).getArea_code());
					ProcModTacheUpdateList.add(ProcModTacheList.get(i));
					procModTacheMap.put(ProcModTacheList.get(i).getProvince_code()+"_"+ProcModTacheList.get(i).getArea_code(), ProcModTacheUpdateList);
				}
				else{
					List<ProcModTache> ProcModTacheAddList = new ArrayList<ProcModTache>();
					ProcModTacheAddList.add(ProcModTacheList.get(i));
					procModTacheMap.put(ProcModTacheList.get(i).getProvince_code()+"_"+ProcModTacheList.get(i).getArea_code(), ProcModTacheAddList);
				}
			}
			writeDataToRedis(procModTacheMap,"proc_tache");
		}
		else{
			logger.info("=================没有查询到proc_mod_tache中的数据============");
		}
		//缓存proc_mod_tache_btn表数据
		List<ProcModTacheBtn> ProcModTacheBtnList = S.get(ProcModTacheBtn.class).query(null);
		if(ProcModTacheBtnList != null && ProcModTacheBtnList.size()>0){
			logger.info("=======================proc_mod_tache_btn表需要插入的数据条数为："+ProcModTacheBtnList.size());
			Map<String,Object> procModTacheBtnMap = new HashMap<String,Object>();
			procModTacheBtnMap.put("proc_tache_btn", ProcModTacheBtnList);
			writeDataToRedis(procModTacheBtnMap,"proc_tache_btn");
		}
		else{
			logger.info("=================没有查询到proc_mod_tache_btn中的数据============");
		}
		//缓存proc_mod_tache_login表数据
		List<ProcModTacheLogin> ProcModTacheLoginList = S.get(ProcModTacheLogin.class).query(null);
		if(ProcModTacheLoginList != null && ProcModTacheLoginList.size()>0){
			logger.info("=======================proc_mod_tache_login表需要插入的数据条数为："+ProcModTacheLoginList.size());
			Map<String,Object> procModTacheLoginMap = new HashMap<String,Object>();
			procModTacheLoginMap.put("proc_tache_login", ProcModTacheLoginList);
			writeDataToRedis(procModTacheLoginMap,"proc_tache_login");
		}
		else{
			logger.info("=================没有查询到proc_mod_tache_login中的数据============");
		}
		//缓存proc_mod_tache_service表数据
		List<ProcModTacheService> ProcModTacheServiceList = S.get(ProcModTacheService.class).query(null);
		if(ProcModTacheServiceList != null && ProcModTacheServiceList.size()>0){
			logger.info("=======================proc_mod_tache_service表需要插入的数据条数为："+ProcModTacheServiceList.size());
			Map<String,Object> procModTacheServiceMap = new HashMap<String,Object>();
			procModTacheServiceMap.put("proc_tache_service", ProcModTacheServiceList);
			for(int i=0;i<ProcModTacheServiceList.size();i++){
				if(procModTacheServiceMap.containsKey(ProcModTacheServiceList.get(i).getTache_code()+"_"+ProcModTacheServiceList.get(i).getProvince_code()+"_"+ProcModTacheServiceList.get(i).getArea_code()+"_"+ProcModTacheServiceList.get(i).getOper_code())){
					List<ProcModTacheService> ProcModTacheServiceUpdateList = (List<ProcModTacheService>) procModTacheServiceMap.get(ProcModTacheServiceList.get(i).getTache_code()+"_"+ProcModTacheServiceList.get(i).getProvince_code()+"_"+ProcModTacheServiceList.get(i).getArea_code()+"_"+ProcModTacheServiceList.get(i).getOper_code());
					ProcModTacheServiceUpdateList.add(ProcModTacheServiceList.get(i));
					procModTacheServiceMap.put(ProcModTacheServiceList.get(i).getTache_code()+"_"+ProcModTacheServiceList.get(i).getProvince_code()+"_"+ProcModTacheServiceList.get(i).getArea_code()+"_"+ProcModTacheServiceList.get(i).getOper_code(), ProcModTacheServiceUpdateList);
				}
				else{
					List<ProcModTacheService> ProcModTacheServiceAddList = new ArrayList<ProcModTacheService>();
					ProcModTacheServiceAddList.add(ProcModTacheServiceList.get(i));
					procModTacheServiceMap.put(ProcModTacheServiceList.get(i).getTache_code()+"_"+ProcModTacheServiceList.get(i).getProvince_code()+"_"+ProcModTacheServiceList.get(i).getArea_code()+"_"+ProcModTacheServiceList.get(i).getOper_code(), ProcModTacheServiceAddList);
				}
			}
			writeDataToRedis(procModTacheServiceMap,"proc_tache_service");
		}
		else{
			logger.info("=================没有查询到proc_mod_tache_service中的数据============");
		}
		//缓存proc_view_info表数据
		List<ProcViewInfo> procViewInfoList = S.get(ProcViewInfo.class).query(null);
		if(procViewInfoList != null && procViewInfoList.size()>0){
			logger.info("=================================proc_view_info表需要缓存的数据条数为："+procViewInfoList.size());
			Map<String,Object> procViewInfoMap = new HashMap<String,Object>();
			procViewInfoMap.put("proc_view_info", procViewInfoList);
			for(int i=0;i<procViewInfoList.size();i++){
				if(procViewInfoMap.containsKey(procViewInfoList.get(i).getProc_mod_code())){
					List<ProcViewInfo> procViewInfoUpdateList = (List<ProcViewInfo>) procViewInfoMap.get(procViewInfoList.get(i).getProc_mod_code());
					procViewInfoUpdateList.add(procViewInfoList.get(i));
					procViewInfoMap.put(procViewInfoList.get(i).getProc_mod_code(), procViewInfoUpdateList);
				}
				else{
					List<ProcViewInfo> procViewInfoAddList = new ArrayList<ProcViewInfo>();
					procViewInfoAddList.add(procViewInfoList.get(i));
					procViewInfoMap.put(procViewInfoList.get(i).getProc_mod_code(), procViewInfoAddList);
				}
			}
			writeDataToRedis(procViewInfoMap,"proc_view_info");
		}
		else{
			logger.info("=================没有查询到proc_view_info中的数据============");
		}
		//缓存rule_attr_define表数据
		List<RuleAttrDefine> RuleAttrDefineList = S.get(RuleAttrDefine.class).query(null);
		if(RuleAttrDefineList != null && RuleAttrDefineList.size()>0){
			logger.info("=======================rule_attr_define表需要插入的数据条数为："+RuleAttrDefineList.size());
			Map<String,Object> ruleAttrDefineMap = new HashMap<String,Object>();
			ruleAttrDefineMap.put("rule_attr_define", ProcModTacheServiceList);
			writeDataToRedis(ruleAttrDefineMap,"rule_attr_define");
		}
		else{
			logger.info("=================没有查询到rule_attr_define中的数据============");
		}
		//缓存rule_define表数据
		List<RuleDefine> RuleDefineList = S.get(RuleDefine.class).query(null);
		if(RuleDefineList != null && RuleDefineList.size()>0){
			logger.info("=======================rule_define表需要插入的数据条数为："+RuleDefineList.size());
			Map<String,Object> ruleDefineMap = new HashMap<String,Object>();
			ruleDefineMap.put("rule_define", RuleDefineList);
			writeDataToRedis(ruleDefineMap,"rule_define");
		}
		else{
			logger.info("=================没有查询到rule_define中的数据============");
		}
		//缓存rule_instance表数据
		List<RuleInstance> RuleInstanceList = S.get(RuleInstance.class).query(null);
		if(RuleInstanceList != null && RuleInstanceList.size()>0){
			logger.info("=======================rule_instance表需要插入的数据条数为："+RuleInstanceList.size());
			Map<String,Object> ruleInstanceMap = new HashMap<String,Object>();
			ruleInstanceMap.put("rule_instance", RuleInstanceList);
			writeDataToRedis(ruleInstanceMap,"rule_instance");
		}
		else{
			logger.info("=================没有查询到rule_instance中的数据============");
		}
		//缓存rule_type表数据
		List<RuleType> RuleTypeList = S.get(RuleType.class).query(null);
		if(RuleTypeList != null && RuleTypeList.size()>0){
			logger.info("=======================rule_type表需要插入的数据条数为："+RuleTypeList.size());
			Map<String,Object> ruleTypeMap = new HashMap<String,Object>();
			ruleTypeMap.put("rule_type", RuleTypeList);
			writeDataToRedis(ruleTypeMap,"rule_type");
		}
		else{
			logger.info("=================没有查询到rule_type中的数据============");
		}

		//缓存默认区域信息
		Map<String,Object> provinceCityAreaMap = new HashMap<String,Object>();
		Map<String,Object> provinceTmpMap = new HashMap<String,Object>();
		provinceTmpMap.put("type_code", "provinceCode");
		Condition conProvince = Condition.build("queryDefaultProvince").filter(provinceTmpMap);
		List<CodeList> provinceList = S.get(CodeList.class).query(conProvince);
		if(provinceList != null && provinceList.size()>0){
			provinceCityAreaMap.put("provinceCode", provinceList.get(0).getCode_id());
			provinceCityAreaMap.put("provinceName", provinceList.get(0).getCode_name());
			//查询默认城市
			Map<String,Object> cityTmpMap = new HashMap<String,Object>();
			cityTmpMap.put("upper_region_id", provinceList.get(0).getCode_id());
			Condition conCity = Condition.build("queryCodeRegionByUpperRegionId").filter(cityTmpMap);
			List<CodeRegion> cityList = S.get(CodeRegion.class).query(conCity);
			if(cityList != null && cityList.size()>0){
				List<Map<String,Object>> cityResultList = new ArrayList<Map<String,Object>>();
				for(int i=0;i<cityList.size();i++){
					Map<String,Object> cityMap = new HashMap<String,Object>();
					cityMap.put("cityRegionCode", cityList.get(i).getRegion_code());
					cityMap.put("cityRegionName", cityList.get(i).getRegion_name());
					//查询默认区域信息
					Map<String,Object> areaTmpMap = new HashMap<String,Object>();
					areaTmpMap.put("upper_region_id", cityList.get(i).getRegion_id());
					Condition conArea = Condition.build("queryCodeRegionByUpperRegionId").filter(areaTmpMap);
					List<CodeRegion> areaList = S.get(CodeRegion.class).query(conArea);
					if(areaList != null && areaList.size()>0){
						List<Map<String,String>> areaResultList = new ArrayList<Map<String,String>>();
						for(int j=0;j<areaList.size();j++){
							Map<String,String> areaMap = new HashMap<String,String>();
							areaMap.put("areaRegionCode", areaList.get(j).getRegion_code());
							areaMap.put("areaRegionName", areaList.get(j).getRegion_name());
							areaResultList.add(areaMap);
						}
						cityMap.put("areaInfo", areaResultList);
					}
					cityResultList.add(cityMap);
				}
				provinceCityAreaMap.put("cityInfo", cityResultList);
			}
		}
		writeDataToRedis(provinceCityAreaMap,"default_region_info");

		return true;
	}

	/**
	 * 内部方法，写入数据到redis
	 * */
	public boolean writeDataToRedis(Map<String,Object> map,String key){
		RedisData redisData = new RedisData();
		redisData.setId(key);
		redisData.setMap(map);
		String resultKey = (String) S.get(RedisData.class).create(redisData);
		if(resultKey != null && (!resultKey.equals(""))){
			logger.info("===================缓存"+key+"表数据到redis成功=================");
			return true;
		}
		else{
			logger.info("===================缓存"+key+"表数据到redis失败=================");
			return false;
		}
	}

	@Override
	public boolean initDeleteDataFromRedis() {
		int result = 1;
		for(int i=0;i<keys.length;i++){
			result = S.get(RedisData.class).remove(keys[i]);
			if(result == 0){
				logger.info("===========从redis删除"+keys[i]+"表数据成功");
			}
			else{
				logger.info("===========从redis删除"+keys[i]+"表数据失败");
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean createStaticDataToRedis() {
		logger.info("================初始化缓存数据到redis==============");
		boolean result = true;
		//按照provice_code+area_code+mod_used+oper_code+tache_id缓存订单模板应用表
		List<OrdModApp> ordModAppList = S.get(OrdModApp.class).query(null);
		if(ordModAppList != null && ordModAppList.size()>0){
			logger.info("=======================OrdModApp表需要插入的数据条数为："+ordModAppList.size());
			Map<String,Object> map = new HashMap<String,Object>();
			for(OrdModApp po:ordModAppList){
				//TODO
				 String key=po.getProvince_code()+"|"+po.getArea_code()+"|"+po.getMod_used()+"|"+po.getOper_code()+"|"+po.getTache_id();
				if(po.getTele_type() !=null && !"".equals(po.getTele_type())){
					 key=po.getProvince_code()+"|"+po.getArea_code()+"|"+po.getMod_used()+"|"+po.getOper_code()+"|"+po.getTache_id()+"|"+po.getTele_type();
				}
				map.put(key, po);
			}

			writeDataToRedis(map,"queryOrdModAppOrderForm");
		}
		else{
			logger.info("=================没有查询到OrdModApp中的数据============");
		}

		//按照mod_code缓存模板参数定义表的默认字段
		List<OrdModAttrDefine> ordModAttrDefineList = S.get(OrdModAttrDefine.class).query(null);

		if(ordModAttrDefineList != null && ordModAttrDefineList.size()>0){
			logger.info("=======================OrdModAttrDefine表需要插入的数据条数为："+ordModAttrDefineList.size());
			Map<String,Object> defaultMap =  new HashMap<String,Object>();
			List<OrdModAttrDefine> defaultList =null;
			Map<String,Object> ordModAttrDefineMap =  new HashMap<String,Object>();
			List<OrdModAttrDefine> poList =null;
			Map<String,Object> allMap =  new HashMap<String,Object>();

			String modeCode;
			for(OrdModAttrDefine potemp:ordModAttrDefineList){
				modeCode=potemp.getMod_code().toString();
				defaultList=(List<OrdModAttrDefine>) defaultMap.get(modeCode+"Default");
				poList=(List<OrdModAttrDefine>) ordModAttrDefineMap.get(modeCode);
				if(defaultList==null){
					defaultList = new ArrayList<OrdModAttrDefine>();
				}
				if(poList==null){
					poList = new ArrayList<OrdModAttrDefine>();
				}
				//按照mod_code缓存模板参数定义表
				poList.add(potemp);
				Map<String,Object> attrMap = new HashMap<String, Object>();
				for (OrdModAttrDefine po1:poList){
					attrMap.put(po1.getAttr_code(), po1);
				}
				allMap.put(modeCode, attrMap);
				ordModAttrDefineMap.put(modeCode, poList);
				//按照mod_code缓存模板参数定义表的默认字段
				if("1".equals(potemp.getIs_default_value())){
					defaultList.add(potemp);
					defaultMap.put(modeCode+"Default", defaultList);
				}
			}
			writeDataToRedis(defaultMap,"queryDefaultOrdModAttrDefineBymodCode");
			writeDataToRedis(ordModAttrDefineMap,"queryordModAttrDefineBymodCode");
			writeDataToRedis(allMap,"queryAllOrdModAttrDefineBymodCode");
		}


		//缓存ord_mod_para_field_relation表数据
		List<OrdModParaFieldRelation> OrdModParaFieldRelationList = S.get(OrdModParaFieldRelation.class).query(null);
		if(OrdModParaFieldRelationList != null && OrdModParaFieldRelationList.size()>0){
			logger.info("=======================ordModParaFieldRelation表需要插入的数据条数为："+OrdModParaFieldRelationList.size());
			Map<String,Object> ordModParaFieldRelationMap = new HashMap<String,Object>();
			for(OrdModParaFieldRelation po : OrdModParaFieldRelationList){
				String attr_code=po.getAttr_code()+"-"+po.getMod_code();
				ordModParaFieldRelationMap.put(attr_code, po);
			}
			writeDataToRedis(ordModParaFieldRelationMap,"queryordModParaFieldRelationByAttrCode");
		}
		else{
			logger.info("=================没有查询到ordModParaFieldRelation中的数据============");
		}

		//缓存ord_mod_muti_table表数据
		List<OrdModMutiTable> ordModMutiTableList = S.get(OrdModMutiTable.class).query(null);
		if(ordModMutiTableList != null && ordModMutiTableList.size()>0){
			logger.info("=======================ord_mod_muti_table表需要插入的数据条数为："+OrdModParaFieldRelationList.size());
			Map<String,Object> ordModMutiTableMap = new HashMap<String,Object>();
			for(OrdModMutiTable po : ordModMutiTableList){
				String tableName=po.getTable_name();
				ordModMutiTableMap.put(tableName, po);
			}
			writeDataToRedis(ordModMutiTableMap,"ord_muti_table");
		}
		else{
			logger.info("=================没有查询到ord_mod_muti_table中的数据============");
		}


		//缓存proc_mod_tache_login表数据 根据tache_code,role_id
		List<ProcModTacheLogin> procModTacheLoginList = S.get(ProcModTacheLogin.class).query(null);
		if(procModTacheLoginList != null && procModTacheLoginList.size()>0){
			logger.info("=======================ordModParaFieldRelation表需要插入的数据条数为："+procModTacheLoginList.size());
			Map<String,Object> procModTacheLoginListMap = new HashMap<String,Object>();
			for(ProcModTacheLogin po : procModTacheLoginList){
				String tache_code = po.getTache_code()+"-"+po.getRole_id();
				procModTacheLoginListMap.put(tache_code, po);
			}
			writeDataToRedis(procModTacheLoginListMap,"queryProcModTacheLoginByTacheCode");
		}else{
			logger.info("=================没有查询到proc_mod_tache_login中的数据============");
		}
		//缓存proc_mod_tache表数据，根据tache_code
		List<ProcModTache> procModTacheList = S.get(ProcModTache.class).query(null);
		if(procModTacheList != null && procModTacheList.size()>0){
			logger.info("=======================procModTache表需要插入的数据条数为："+procModTacheList.size());
			Map<String,Object> procModTacheListMap = new HashMap<String,Object>();
			for(ProcModTache po : procModTacheList){
				String tache_code = po.getTache_code();
				procModTacheListMap.put(tache_code, po);
			}
			writeDataToRedis(procModTacheListMap,"queryProcModTacheByTacheCode");
		}else{
			logger.info("=================没有查询到proc_mod_tache中的数据============");
		}

		//缓存proc_mod_app表数据，根据oper_code
		List<ProcModApp> procModAppList = S.get(ProcModApp.class).query(null);
		if(procModAppList != null && procModAppList.size()>0){
			logger.info("=======================procModApp表需要插入的数据条数为："+procModTacheList.size());
			Map<String,Object> procModAppListMap = new HashMap<String,Object>();
			for(ProcModApp po : procModAppList){
				String oper_code = po.getOper_code();
				procModAppListMap.put(oper_code, po);
			}
			writeDataToRedis(procModAppListMap,"queryProcModAppByOperCode");
		}else{
			logger.info("=================没有查询到proc_mod_app中的数据============");
		}

		//缓存ord_mod_define表数据
		List<OrdModDefine> OrdModDefineList = S.get(OrdModDefine.class).query(null);
		if(OrdModDefineList != null && OrdModDefineList.size()>0){
			logger.info("=======================ordModDefine表需要插入的数据条数为："+OrdModDefineList.size());
			Map<String,Object> ordModDefineMap = new HashMap<String,Object>();
			for(OrdModDefine ordModDefine:OrdModDefineList){
				String modCode=ordModDefine.getMod_code();
				ordModDefineMap.put(modCode, ordModDefine);
			}
			writeDataToRedis(ordModDefineMap,"queryordModDefineByModCode");
		}
		else{
			logger.info("=================没有查询到ordModDefine中的数据============");
		}
		return result;
	}

	@Override
	public boolean deleteStaticDataToRedis(){
		String[] keys={"Seq_createSaleOrderNo_50_501","Seq_createLogId_50_*","Seq_createDeliverOrderNo_50_501","Seq_createPayOrderNo_50_501","Seq_createServiceOrderNo_50_501","Seq_createOfrOrderNo_50_501","queryOrdModAppOrderForm","queryDefaultOrdModAttrDefineBymodCode","queryordModAttrDefineBymodCode","queryAllOrdModAttrDefineBymodCode","queryordModDefineByModCode","queryordModParaFieldRelationByAttrCode","ord_muti_table","queryProcModTacheLoginByTacheCode","queryProcModTacheByTacheCode","queryOrdModAttrDefineByModCode","queryProcModAppByOperCode"};

		int result = 1;
		for(int i=0;i<keys.length;i++){
			result = S.get(RedisData.class).remove(keys[i]);
			if(result == 0){
				logger.info("===========从redis删除"+keys[i]+"表数据成功");
			}
			else{
				logger.info("===========从redis删除"+keys[i]+"表数据失败");
			}
		}
		return true;
	}

}
