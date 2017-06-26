package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.SequencesPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.GetIdServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("GetIdServ")
public class GetIdServImpl extends BaseServImpl<SequencesPo> implements GetIdServ{

	Logger logger = Logger.getLogger(GetIdServImpl.class);
	
	@Override
	public List<SequencesPo> query() throws Exception{
		logger.info("====================query sequences========================");
		Condition condition = Condition.build("querySequences");
		List<SequencesPo> list = query(SequencesPo.class,condition);
		if(list !=null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean updateSequences(SequencesPo sequencesPo) throws Exception {
		update(SequencesPo.class,sequencesPo);
		return true;
	}

	@Override
	public SequencesPo querySequencesBySeqCodeAndProvinceCodeAndAreaCode(SequencesPo sequencesPo) throws Exception {
		Condition condition = Condition.build("querySequencesBySeqCodeAndProvinceCodeAndAreaCode").filter(sequencesPo.convertThis2Map());
		List<SequencesPo> list = query(SequencesPo.class,condition);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

}
