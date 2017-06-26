package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModAppServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdModAppServ")
public class OrdModAppServImpl extends BaseServImpl<OrdModAppPo> implements OrdModAppServ{

	@Override
	public boolean create(OrdModAppPo ordModAppPo) {
		create(OrdModAppPo.class,ordModAppPo);
		return true;
	}

	@Override
	public boolean update(OrdModAppPo ordModAppPo) {
		update(OrdModAppPo.class,ordModAppPo);
		return true;
	}

	@Override
	public boolean delete(OrdModAppPo ordModAppPo) {
		remove(OrdModAppPo.class,ordModAppPo);
		return true;
	}

	@Override
	public List<OrdModAppPo> queryOrdModAppList(OrdModAppPo ordModAppPo,int pageNo,int pageSize) {
		Condition con = Condition. build("queryOrdModAppList").filter( ordModAppPo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<OrdModAppPo> list = S.get(OrdModAppPo.class).page(con,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null;
		}
		return list ;

	}

	@Override
	public OrdModAppPo query(OrdModAppPo ordModAppPo) {

		return null;
	}

	@Override
	public int queryOrdModAppListCount(OrdModAppPo ordModAppPo)
			throws Exception {
		Condition con = Condition. build("queryOrdModAppListCount").filter( ordModAppPo.convertThis2Map());
		List<OrdModAppPo> list=query(OrdModAppPo.class,con);
		return list.size();
	}

	@Override
	public List<OrdModAppPo> queryAllOrdModApp(OrdModAppPo po) throws Exception{
		Condition con = Condition. build("queryOrdModAppListCount").filter( po.convertThis2Map());
		List<OrdModAppPo> list=query(OrdModAppPo.class,con);
		if(list == null || list.size() <= 0){
			return null;
		}
		return list ;
	}

	@Override
	public List<OrdModAppPo> queryOrdModAppAll() throws Exception {
		Condition condition = Condition.build("queryOrdModAppAll");
		List<OrdModAppPo> list=query(OrdModAppPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}


}
