package com.tydic.unicom.ugc.base.dataBase.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsDefineServ;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsDefinePo;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsInfo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("GoodsDefineServ")
public class GoodsDefineServImpl extends BaseServImpl<GoodsDefinePo> implements GoodsDefineServ{

	@Override
	public List<GoodsDefinePo> queryInfoGoodsDefineByRoleId(GoodsInfo po)
			throws Exception {
		Condition condition = Condition.build("queryInfoGoodsDefineByRoleId").filter(po.convertThis2Map());
		List<GoodsDefinePo> list = S.get(GoodsDefinePo.class).query(condition);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<GoodsDefinePo> queryGoodsDefinePoByPo(GoodsDefinePo po) throws Exception {
		Condition condition = Condition.build("queryGoodsDefinePoByPo").filter(po.convertThis2Map());
		List<GoodsDefinePo> list = S.get(GoodsDefinePo.class).query(condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean addGoodsDefine(GoodsDefinePo po) throws Exception {
		create(GoodsDefinePo.class, po);
		return true;
	}

	@Override
	public boolean deleteGoodsDefine(GoodsDefinePo po) throws Exception {
		remove(GoodsDefinePo.class, po);
		return true;
	}

	@Override
	public boolean updateGoodsDefine(GoodsDefinePo po) throws Exception {
		update(GoodsDefinePo.class, po);
		return true;
	}

	@Override
	public List<GoodsDefinePo> queryGoodsDefineByPage(GoodsDefinePo po, int pageNo, int pageSize) throws Exception {
		int number = (pageNo - 1) * pageSize;
		List<GoodsDefinePo> list = S.get(GoodsDefinePo.class).page(Condition.build("queryGoodsDefineByPage").filter(po.convertThis2Map()), number, pageSize);
		if (list == null || list.size() <= 0) {
			return null;
		}
		return list;
	}

	@Override
	public int queryGoodsDefineCount(GoodsDefinePo po) throws Exception {
		Condition con = Condition.build("queryGoodsDefineCount").filter(po.convertThis2Map());
		List<GoodsDefinePo> list = query(GoodsDefinePo.class, con);
		return list.size();
	}

}
