package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModDefineServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdModDefineServ")
public class OrdModDefineServImpl extends BaseServImpl<OrdModDefinePo> implements OrdModDefineServ{

	@Override
	public boolean create(OrdModDefinePo ordModDefinePo) {
		create(OrdModDefinePo.class, ordModDefinePo);
		return true;
	}

	@Override
	public boolean update(OrdModDefinePo ordModDefinePo) {
		update(OrdModDefinePo.class, ordModDefinePo);
		return true;
	}

	@Override
	public boolean delete(OrdModDefinePo ordModDefinePo) {
		remove(OrdModDefinePo.class, ordModDefinePo);
		return true;
	}

	@Override
	public OrdModDefinePo queryOrdModDefineByModCode(OrdModDefinePo ordModDefinePo){
		return get(OrdModDefinePo.class,ordModDefinePo);
	}

	@Override
	public List<OrdModDefinePo> queryOrdModDefineList(
			OrdModDefinePo ordModDefinePo, int pageNo, int pageSize)
					throws Exception {
		Condition con = Condition. build("queryOrdModDefineList" ).filter( ordModDefinePo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<OrdModDefinePo> list = S.get(OrdModDefinePo.class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

	@Override
	public int queryOrdModDefineListCount(OrdModDefinePo ordModDefinePo)
			throws Exception {
		Condition con = Condition. build("queryOrdModDefineListCount").filter( ordModDefinePo.convertThis2Map());
		List<OrdModDefinePo> list=query(OrdModDefinePo.class,con );
		return list .size();
	}

	@Override
	public List<OrdModDefinePo> queryOrdModDefineAll() throws Exception {
		Condition condition = Condition.build("queryOrdModDefineAll");
		List<OrdModDefinePo> list = query(OrdModDefinePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}


}
