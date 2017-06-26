package com.tydic.unicom.uoc.base.common.impl;

public class Constant {

	/**
	 * 业务常量定义
	 */
	//新增表数据
	public static final String ADD = "100";
	//更新表数据
	public static final String UPDATE = "101";
	//删除表数据
	public static final String DELETE = "102";
	//查询表数据
	public static final String QUERY = "103";
	
	//@uoc_code
	//表名ord_mod_attr_define 模板参数定义表
	public static final String ORD_MOD_ATTR_DEFINE = "ordModAttrDefine";

	//表名 ord_mod_para_field_relation 订单参数与数据库表字段关系表
	public static final String ORD_MOD_PARA_FIELD_RELATION = "ordModParaFieldRelation";
	
	//表名ord_mod_attr_check_rule 订单模板参数校验规则表
	public static final String ORD_MOD_ATTR_CHECK_RULE = "ordModAttrCheckRule";
	
	//表名ord_mod_define 订单模板定义表
	public static final String ORD_MOD_DEFINE = "ordModDefine";
	
	//表名ord_mod_check_rule 订单模板校验 规则表
	public static final String ORD_MOD_CHECK_RULE = "ordModCheckRule";
				
	//表名ord_mod_state_rule 服务订单状态关系表
	public static final String ORD_MOD_STATE_RULE = "ordModStateRule";
	
	//表名ord_mod_tache_rule 服务订单环节关系表
	public static final String ORD_MOD_TACHE_RULE = "ordModTacheRule";
	
	//表名ord_mod_app 订单模板应用表
	public static final String ORD_MOD_APP = "ordModApp";
	
	//表名proc_mod_app 流程模板应用表
	public static final String PROC_MOD_APP = "procModApp";		
	
	//表名proc_mod_tache_login 环节关联工号表
	public static final String PROC_MOD_TACHE_LOGIN = "procModTacheLogin";
	
	//表名proc_mod_tache_service 环节关联服务表
	public static final String PROC_MOD_TACHE_SERVICE = "procModTacheService";
		
	//表名proc_mod_tache_btn 环节功能表
	public static final String PROC_MOD_TACHE_BTN = "procModTacheBtn";
	
	//表名proc_mod_tache 环节配置表
	public static final String PROC_MOD_TACHE = "procModTache";
	
	//@uoc_inst
	//表名 info_service_order 服务订单
	public static final String INFO_SERVICE_ORDER = "infoServiceOrder";
	//生成身份证相片标准
	public static final int ID_PIC_HEIGHT =126;
	
	public static final int ID_PIC_WIDTH =102;
	//截取分库定义常量
	public static final int SUB_ORDER_AREA_LENGTH =15;
	public static final int SUB_ORDER_YEAR_LENGTH =2;
}
