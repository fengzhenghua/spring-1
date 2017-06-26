package com.tydic.unicom.ugc.base.dataBase.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.ugc.base.dataBase.interfaces.SkuAttrServ;
import com.tydic.unicom.ugc.base.dataBase.po.SkuAttrPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("SkuAttrServ")
public class SkuAttrServImpl extends BaseServImpl<SkuAttrPo> implements SkuAttrServ {

	@Override
	public List<SkuAttrPo> querySkuAttrBySkuId(SkuAttrPo po) throws Exception {
		Condition condition = Condition.build("querySkuAttrBySkuId").filter(po.convertThis2Map());
		List<SkuAttrPo> list = query(SkuAttrPo.class, condition);
		if(list != null && list.size()>0){
			return list;
		} else {
			return null;
		}
	}

	@Override
	public boolean addSkuAttrPo(SkuAttrPo po) throws Exception {
		create(SkuAttrPo.class,po);
		return true;
	}

	@Override
	public boolean deleteSkuAttrPo(SkuAttrPo po) throws Exception {
		remove(SkuAttrPo.class,po);
		return true;
	}

	@Override
	public boolean updateSkuAttrPo(SkuAttrPo po) throws Exception {
		update(SkuAttrPo.class,po);
		return true;
	}

}
