
package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModAppPo;

public interface OrdModAppServ {


	/**
	 * 操作订单模板应用表
	 * 
	 * @param ordModAppPo
	 * @return OrdModAppPo
	 * @throws 
	 */
	public boolean create(OrdModAppPo ordModAppPo);
	
	public boolean update(OrdModAppPo ordModAppPo);
	
	public boolean delete(OrdModAppPo ordModAppPo);
	
	public List<OrdModAppPo> queryOrdModAppList(OrdModAppPo ordModAppPo,int pageNo,int pageSize);
	
	public int queryOrdModAppListCount(OrdModAppPo ordModAppPo)throws Exception;
	
	public OrdModAppPo query(OrdModAppPo ordModAppPo);
		
	public List<OrdModAppPo> queryAllOrdModApp(OrdModAppPo po) throws Exception;
	
	/**
	 * 查询OrdModApp全部数据
	 * */
	public List<OrdModAppPo> queryOrdModAppAll() throws Exception;
}



