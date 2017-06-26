package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.Map;

import com.tydic.unicom.uoc.service.mod.vo.OrdModVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface OrdModFunctionServDu {
	
	/**
	 * 查询订单模板
	 * 
	 * @param serv_order_no,query_type
	 * @return OrdModAppVo
	 * @throws Exception
	 */
	public UocMessage queryOrdMod(String order_no,String query_type,String order_type) throws Exception;
	
	/**
	 * 根据订单模板入库
	 * 
	 * @param OrdModVo
	 * @return boolean
	 * @throws Exception
	 */
	public UocMessage insertByOrdMod(OrdModVo ordModVo) throws Exception;
	
	/**
	 * 根据订单模板出库
	 * 
	 * @param OrdModVo
	 * @return OrdModVo
	 * @throws Exception
	 */
	
	public UocMessage outByOrdMod(String order_no,String mod_code,String order_type,String param_json) throws Exception;

}
