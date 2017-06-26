package com.tydic.unicom.uoc.base.uoccode.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModTachePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModTacheServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ProcModTacheServ")
public class ProcModTacheServImpl extends BaseServImpl<ProcModTachePo> implements ProcModTacheServ{


	@Override
	public ProcModTachePo queryProcModTacheByTacheCode(
			ProcModTachePo procModTachePo) {		
		Condition con = Condition.build("queryProcModTacheByTacheCode").filter(procModTachePo.convertThis2Map());
		List<ProcModTachePo> list = query(ProcModTachePo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public boolean create(ProcModTachePo procModTachePo) throws Exception {
		create(ProcModTachePo.class, procModTachePo);
		return true;
	}

	@Override
	public boolean update(ProcModTachePo procModTachePo) throws Exception {
		update(ProcModTachePo.class, procModTachePo);
		return true;
	}

	@Override
	public boolean delete(ProcModTachePo procModTachePo) throws Exception {
		remove(ProcModTachePo.class, procModTachePo);
		return true;
	}

	@Override
	public List<ProcModTachePo> queryProcModTacheList(
			ProcModTachePo procModTachePo, int pageNo, int pageSize)
					throws Exception {
		Condition con = Condition. build("queryProcModTacheList" ).filter( procModTachePo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<ProcModTachePo> list = S.get(ProcModTachePo.class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

	@Override
	public int queryProcModTacheListCount(ProcModTachePo procModTachePo)
			throws Exception {
		Condition con = Condition. build("queryProcModTacheListCount" ).filter( procModTachePo.convertThis2Map());
		List<ProcModTachePo> list=query(ProcModTachePo.class,con );
		return list .size();
	}

	@Override
	public List<ProcModTachePo> queryProcModTacheAll() throws Exception {
		Condition condition = Condition.build("queryProcModTacheAll");
		List<ProcModTachePo> list = query(ProcModTachePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
