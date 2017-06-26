package com.tydic.unicom.service.sequence.service.interfaces;

/**
 * Sequence对外开放服务
 * @author wangxiao
 */

public interface SequenceServ {


	/**
	 * @param seqName
	 * @return
	 */
    public long genSequence(String seqName);

      
}
