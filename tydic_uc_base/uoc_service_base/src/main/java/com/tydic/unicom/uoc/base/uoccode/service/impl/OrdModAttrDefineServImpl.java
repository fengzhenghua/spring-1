package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModAttrDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModAttrDefineServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdModAttrDefineServ")
public class OrdModAttrDefineServImpl extends BaseServImpl<OrdModAttrDefinePo> implements OrdModAttrDefineServ {

	@Override
	public List<OrdModAttrDefinePo> queryOrdModAttrDefineByModCode(
			OrdModAttrDefinePo po) throws Exception {
		Condition condition = Condition.build("queryOrdModAttrDefineByModCode").filter(po.convertThis2Map());
		List<OrdModAttrDefinePo> list = query(OrdModAttrDefinePo.class, condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean create(OrdModAttrDefinePo po) {
		create(OrdModAttrDefinePo.class,po);
		return true;
	}

	@Override
	public boolean update(OrdModAttrDefinePo po) {
		update(OrdModAttrDefinePo.class,po);
		return true;
	}

	@Override
	public boolean delete(OrdModAttrDefinePo po) {
		remove(OrdModAttrDefinePo.class,po);
		return true;
	}

	@Override
	public List<OrdModAttrDefinePo> queryOrdModAttrDefineList(
			OrdModAttrDefinePo po, int pageNo, int pageSize) throws Exception {
		Condition con = Condition. build("queryOrdModAttrDefineList" ).filter( po.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<OrdModAttrDefinePo> list = S.get(OrdModAttrDefinePo.class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

	@Override
	public int queryOrdModAttrDefineListCount(OrdModAttrDefinePo po)
			throws Exception {
		Condition con = Condition. build("queryOrdModAttrDefineListCount" ).filter( po.convertThis2Map());
		List<OrdModAttrDefinePo> list=query(OrdModAttrDefinePo.class,con );
		return list .size();
	}
	
	@Override
	public List<OrdModAttrDefinePo> queryAllOrdModAttrDefineList(OrdModAttrDefinePo po) throws Exception{
		Condition con = Condition. build("queryAllOrdModAttrDefineList" ).filter( po.convertThis2Map());
		List<OrdModAttrDefinePo> list=query(OrdModAttrDefinePo.class,con );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;
	}

	@Override
	public List<OrdModAttrDefinePo> queryOrdModAttrDefineAll() throws Exception {
		Condition condition = Condition.build("queryOrdModAttrDefineAll");
		List<OrdModAttrDefinePo> list = query(OrdModAttrDefinePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
