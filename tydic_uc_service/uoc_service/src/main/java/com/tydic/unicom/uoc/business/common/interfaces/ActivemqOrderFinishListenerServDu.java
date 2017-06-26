package com.tydic.unicom.uoc.business.common.interfaces;

public interface ActivemqOrderFinishListenerServDu {

	/**
	 * OrderFinish队列的监听
	 * */
	public void getMessageFromOrderFinish(Object message) throws Exception;
}
