package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModAppServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcModAppServ")
public class ProcModAppServImpl extends BaseServImpl<ProcModAppPo> implements ProcModAppServ{

	@Override
	public boolean create(ProcModAppPo procModAppPo) throws Exception {
		create(ProcModAppPo.class ,procModAppPo);
		return true;
	}

	@Override
	public boolean update(ProcModAppPo procModAppPo) throws Exception {
		update(ProcModAppPo.class ,procModAppPo);
		return true;
	}

	@Override
	public boolean delete(ProcModAppPo procModAppPo) throws Exception {
		remove(ProcModAppPo.class ,procModAppPo);
		return true;
	}

	@Override
	public ProcModAppPo queryProcModAppByProvinceCodeAndAreaCodeAndOperCode(ProcModAppPo procModAppPo) throws Exception {
		Condition con = Condition.build("queryProcModAppByProvinceCodeAndAreaCodeAndOperCode").filter(procModAppPo.convertThis2Map());
		List<ProcModAppPo> list = query(ProcModAppPo.class,con);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public List<ProcModAppPo> queryProcModAppList(ProcModAppPo procModAppPo,
			int pageNo, int pageSize) throws Exception {
		Condition con = Condition. build("queryProcModAppList" ).filter( procModAppPo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<ProcModAppPo> list = S.get(ProcModAppPo.class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

	@Override
	public int queryProcModAppListCount(ProcModAppPo procModAppPo)
			throws Exception {
		Condition con = Condition. build("queryProcModAppListCount" ).filter( procModAppPo.convertThis2Map());
		List<ProcModAppPo> list=query(ProcModAppPo.class,con );
		return list .size();	
	}

	@Override
	public List<ProcModAppPo> queryProcModAppAll() throws Exception {
		Condition condition = Condition.build("queryProcModAppAll");
		List<ProcModAppPo> list = query(ProcModAppPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
