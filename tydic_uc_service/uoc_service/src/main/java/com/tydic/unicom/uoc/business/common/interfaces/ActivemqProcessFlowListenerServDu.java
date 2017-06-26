package com.tydic.unicom.uoc.business.common.interfaces;

public interface ActivemqProcessFlowListenerServDu {

	/**
	 * ProcessFlow队列的监听
	 * */
	public void getMessageFromProcessFlow(Object message) throws Exception;
}
