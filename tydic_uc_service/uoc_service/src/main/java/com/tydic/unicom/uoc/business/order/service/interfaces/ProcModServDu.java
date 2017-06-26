package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcInstTaskDealRecordVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ProcModServDu {

	/**
	 * UOC0017 流程应用维护
	 */
	public UocMessage syncProcOrdApp(ParaVo paraVo) throws Exception;

	/**
	 * UOC0018 环节配置
	 */
	public UocMessage syncProcModTache(ParaVo paraVo) throws Exception;

	/**
	 * UOC0031 环节查询
	 */
	public UocMessage searchProcMod(ParaVo paraVo) throws Exception;
	/**
	 * UOC0030 流程应用查询
	 */
	public UocMessage searchProcModApp(ParaVo paraVo) throws Exception;
	/**
	 * UOC0037 获取流程图信息
	 */
	public UocMessage getProcViewInfo(ParaVo paraVo) throws Exception;

	/**
	 * UOCXXXX 环节关联工号表查询，分页
	 */
	public UocMessage searchProcModTacheLoginList(ParaVo paraVo) throws Exception;

	/**
	 * UOCXXXX 环节关联服务表查询，分页
	 */
	public UocMessage searchProcModTacheServiceList(ParaVo paraVo) throws Exception;

	/**
	 * UOCXXXX 环节关联功能表查询，分页
	 */
	public UocMessage searchProcModTacheBtnList(ParaVo paraVo) throws Exception;

	/**
	 * UOCXXXX 环节配置表查询，分页
	 */
	public UocMessage searchProcModTacheList(ParaVo paraVo) throws Exception;

	public ProcInstTaskDealRecordVo queryLastTaskDealRecord(String order_no, String tache_code, String order_type, String sale_order_no,
			String finish_flag, String oper_code) throws Exception;
}
