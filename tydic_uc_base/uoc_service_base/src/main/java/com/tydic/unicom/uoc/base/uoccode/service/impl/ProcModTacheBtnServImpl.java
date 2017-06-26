package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheBtnPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcModTacheBtnServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ProcModTacheBtnServ")
public class ProcModTacheBtnServImpl extends BaseServImpl<ProcModTacheBtnPo> implements ProcModTacheBtnServ{

	@Override
	public boolean create(ProcModTacheBtnPo procModTacheBtnPo) throws Exception {
		create(ProcModTacheBtnPo.class, procModTacheBtnPo);
		return true;
	}

	@Override
	public boolean update(ProcModTacheBtnPo procModTacheBtnPo) throws Exception {
		update(ProcModTacheBtnPo.class, procModTacheBtnPo);
		return true;
	}

	@Override
	public boolean delete(ProcModTacheBtnPo procModTacheBtnPo) throws Exception {
		remove(ProcModTacheBtnPo.class, procModTacheBtnPo);
		return true;
	}

	@Override
	public List<ProcModTacheBtnPo> queryProcModTacheBtnList(
			ProcModTacheBtnPo procModTacheBtnPo, int pageNo, int pageSize)
					throws Exception {
		Condition con = Condition. build("queryProcModTacheBtnList" ).filter( procModTacheBtnPo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<ProcModTacheBtnPo> list = S.get(ProcModTacheBtnPo.class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

	@Override
	public int queryProcModTacheBtnListCount(ProcModTacheBtnPo procModTacheBtnPo)
			throws Exception {
		Condition con = Condition. build("queryProcModTacheBtnListCount" ).filter( procModTacheBtnPo.convertThis2Map());
		List<ProcModTacheBtnPo> list=query(ProcModTacheBtnPo.class,con );
		return list .size();
	}

	@Override
	public List<ProcModTacheBtnPo> queryProcModTacheBtnAll() throws Exception {
		// TODO Auto-generated method stub
		Condition condition = Condition.build("queryProcModTacheBtnAll");
		List<ProcModTacheBtnPo> list = query(ProcModTacheBtnPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
