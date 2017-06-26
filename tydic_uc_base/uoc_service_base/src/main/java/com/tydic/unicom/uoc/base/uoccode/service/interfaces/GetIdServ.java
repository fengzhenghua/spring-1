package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.SequencesPo;

public interface GetIdServ {

	public List<SequencesPo> query() throws Exception;
	
	public SequencesPo querySequencesBySeqCodeAndProvinceCodeAndAreaCode(SequencesPo sequencesPo) throws Exception;
	
	public boolean updateSequences(SequencesPo sequencesPo) throws Exception;
}
