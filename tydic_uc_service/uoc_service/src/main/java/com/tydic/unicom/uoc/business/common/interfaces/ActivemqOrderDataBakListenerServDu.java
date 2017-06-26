package com.tydic.unicom.uoc.business.common.interfaces;

public interface ActivemqOrderDataBakListenerServDu {

	/**
	 * OrderDataBak队列的监听
	 * */
	public void getMessageFromOrderDataBak(Object message) throws Exception;
	
}
