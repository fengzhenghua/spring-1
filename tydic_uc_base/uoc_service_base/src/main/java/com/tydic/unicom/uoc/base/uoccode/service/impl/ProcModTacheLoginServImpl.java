package com.tydic.unicom.uoc.base.uoccode.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheLoginPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModTacheLoginServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ProcModTacheLoginServ")
public class ProcModTacheLoginServImpl extends BaseServImpl<ProcModTacheLoginPo> implements ProcModTacheLoginServ{

	@Override
	public List<ProcModTacheLoginPo> queryProcModTacheLoginByTacheCode(
			ProcModTacheLoginPo procModTacheLoginPo) {		
		List<ProcModTacheLoginPo> list = query(ProcModTacheLoginPo.class,Condition.build("queryProcModTacheLoginByTacheCode").filter(procModTacheLoginPo.convertThis2Map()));
		if(list== null){
			return null;
		}
		return list;
	}

	@Override
	public boolean create(ProcModTacheLoginPo procModTacheLoginPo)
			throws Exception {
		create(ProcModTacheLoginPo.class, procModTacheLoginPo);
		return true;
	}

	@Override
	public boolean update(ProcModTacheLoginPo procModTacheLoginPo)
			throws Exception {
		update(ProcModTacheLoginPo.class, procModTacheLoginPo);
		return true;
	}

	@Override
	public boolean delete(ProcModTacheLoginPo procModTacheLoginPo)
			throws Exception {
		remove(ProcModTacheLoginPo.class, procModTacheLoginPo);
		return true;
	}

	@Override
	public List<ProcModTacheLoginPo> queryProcModTacheLoginList(
			ProcModTacheLoginPo procModTacheLoginPo, int pageNo, int pageSize)
					throws Exception {
		Condition con = Condition. build("queryProcModTacheLoginList" ).filter( procModTacheLoginPo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<ProcModTacheLoginPo> list = S.get(ProcModTacheLoginPo.class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

	@Override
	public int queryProcModTacheLoginListCount(
			ProcModTacheLoginPo procModTacheLoginPo) throws Exception {
		Condition con = Condition. build("queryProcModTacheLoginListCount" ).filter( procModTacheLoginPo.convertThis2Map());
		List<ProcModTacheLoginPo> list=query(ProcModTacheLoginPo.class,con );
		return list .size();
	}

	@Override
	public List<ProcModTacheLoginPo> queryProcModTacheLoginAll() throws Exception {
		Condition condition = Condition.build("queryProcModTacheLoginAll");
		List<ProcModTacheLoginPo> list = query(ProcModTacheLoginPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
