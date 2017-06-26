package com.tydic.unicom.ugc.base.dataBase.interfaces;

import java.util.List;

import com.tydic.unicom.ugc.base.dataBase.po.SkuAttrPo;

public interface SkuAttrServ {

	public List<SkuAttrPo> querySkuAttrBySkuId(SkuAttrPo po) throws Exception;
	
	public boolean addSkuAttrPo(SkuAttrPo po)throws Exception;
	
	public boolean deleteSkuAttrPo(SkuAttrPo po)throws Exception;
	
	public boolean updateSkuAttrPo(SkuAttrPo po)throws Exception;
}
