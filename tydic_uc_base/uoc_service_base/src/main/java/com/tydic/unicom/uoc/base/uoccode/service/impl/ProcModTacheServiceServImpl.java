package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheServicePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModTacheServiceServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ProcModTacheServiceServ")
public class ProcModTacheServiceServImpl extends BaseServImpl<ProcModTacheServicePo> implements ProcModTacheServiceServ{

	@Override
	public boolean create(ProcModTacheServicePo procModTacheServicePo)
			throws Exception {
		create(ProcModTacheServicePo.class, procModTacheServicePo);
		return true;
	}

	@Override
	public boolean update(ProcModTacheServicePo procModTacheServicePo)
			throws Exception {
		update(ProcModTacheServicePo.class, procModTacheServicePo);
		return true;
	}

	@Override
	public boolean delete(ProcModTacheServicePo procModTacheServicePo)
			throws Exception {
		remove(ProcModTacheServicePo.class, procModTacheServicePo);
		return true;
	}


	@Override
	public List<ProcModTacheServicePo> queryProcModTacheServiceList(
			ProcModTacheServicePo procModTacheServicePo, int pageNo,
			int pageSize) throws Exception {
		Condition con = Condition. build("queryProcModTacheServiceList" ).filter( procModTacheServicePo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<ProcModTacheServicePo> list = S.get(ProcModTacheServicePo.class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

	@Override
	public int queryProcModTacheServiceListCount(
			ProcModTacheServicePo procModTacheServicePo) throws Exception {
		Condition con = Condition. build("queryProcModTacheServiceListCount" ).filter( procModTacheServicePo.convertThis2Map());
		List<ProcModTacheServicePo> list=query(ProcModTacheServicePo.class,con );
		return list .size();
	}


	@Override
	public List<ProcModTacheServicePo> queryProcModTacheServiceByTacheCodeAndProvinceAndArea(ProcModTacheServicePo procModTacheServicePo) throws Exception {
		
		Condition condition = Condition.build("queryProcModTacheServiceByTacheCodeAndProvinceAndArea").filter(procModTacheServicePo.convertThis2Map());
		List<ProcModTacheServicePo> list = query(ProcModTacheServicePo.class,condition);
		if(list!=null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public List<ProcModTacheServicePo> queryProcModTacheServiceAll() throws Exception {
		Condition condition = Condition.build("queryProcModTacheServiceAll");
		List<ProcModTacheServicePo> list = query(ProcModTacheServicePo.class,condition);
		if(list!=null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}


}
