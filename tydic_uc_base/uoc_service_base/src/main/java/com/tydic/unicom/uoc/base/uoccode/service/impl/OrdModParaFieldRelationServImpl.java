package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModParaFieldRelationPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModParaFieldRelationServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("OrdModParaFieldRelationServ")
public class OrdModParaFieldRelationServImpl extends BaseServImpl<OrdModParaFieldRelationPo> implements OrdModParaFieldRelationServ{

	Logger logger = Logger.getLogger(OrdModParaFieldRelationServImpl.class);

	@Override
	public List<OrdModParaFieldRelationPo> queryOrdModParaFieldRelationByModeCode(OrdModParaFieldRelationPo ordModParaFieldRelationPo) {
		logger.info("=====================query by uoc_service_base=====================");
		Condition condition = Condition.build("query").filter(ordModParaFieldRelationPo.convertThis2Map());
		List<OrdModParaFieldRelationPo> list = S.get(OrdModParaFieldRelationPo.class).query(condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean create(OrdModParaFieldRelationPo ordModParaFieldRelationPo) {
		create(OrdModParaFieldRelationPo.class, ordModParaFieldRelationPo);
		return true;
	}

	@Override
	public boolean update(OrdModParaFieldRelationPo ordModParaFieldRelationPo) {
		update(OrdModParaFieldRelationPo.class, ordModParaFieldRelationPo);
		return true;
	}

	@Override
	public boolean delete(OrdModParaFieldRelationPo ordModParaFieldRelationPo) {
		remove(OrdModParaFieldRelationPo.class, ordModParaFieldRelationPo);
		return true;
	}

	@Override
	public List<OrdModParaFieldRelationPo> queryOrdModParaFieldRelationList(
			OrdModParaFieldRelationPo ordModParaFieldRelationPo, int pageNo,
			int pageSize) throws Exception {
		Condition con = Condition. build("queryOrdModParaFieldRelationList" ).filter( ordModParaFieldRelationPo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<OrdModParaFieldRelationPo> list = S.get(OrdModParaFieldRelationPo. class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;

	}

	@Override
	public int queryOrdModParaFieldRelationListCount(
			OrdModParaFieldRelationPo ordModParaFieldRelationPo)
					throws Exception {
		Condition con = Condition. build("queryOrdModParaFieldRelationListCount" ).filter( ordModParaFieldRelationPo.convertThis2Map());
		List<OrdModParaFieldRelationPo> list=query(OrdModParaFieldRelationPo. class,con );
		return list .size();

	}

	@Override
	public List<OrdModParaFieldRelationPo> queryOrdModParaFieldRelationAll() throws Exception {
		Condition condition = Condition.build("queryOrdModParaFieldRelationAll");
		List<OrdModParaFieldRelationPo> list = query(OrdModParaFieldRelationPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
