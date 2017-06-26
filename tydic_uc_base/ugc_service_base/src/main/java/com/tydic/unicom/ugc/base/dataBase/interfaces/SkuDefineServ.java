package com.tydic.unicom.ugc.base.dataBase.interfaces;


import java.util.List;

import com.tydic.unicom.ugc.base.dataBase.po.SkuDefinePo;

public interface SkuDefineServ {
	
	
	public SkuDefinePo querySkuDefineBySkuId(SkuDefinePo po) throws Exception;
	
	public boolean addSkuDefinePo(SkuDefinePo po) throws Exception;
	
	public boolean deleteSkuDefinePo(SkuDefinePo po) throws Exception;
	
	public boolean updateSkuDefinePo(SkuDefinePo po) throws Exception;
	
	/**
	 * 功能：获取可选sku，通过sku_id和sku_name查询。
	 * @param po
	 * @return
	 * @throws Exception
	 */
	List<SkuDefinePo> querySkuDefineBySkuIdOrSkuName(SkuDefinePo po) throws Exception;


}
