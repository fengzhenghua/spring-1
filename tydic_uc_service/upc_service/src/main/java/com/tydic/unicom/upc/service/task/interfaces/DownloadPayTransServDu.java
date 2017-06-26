package com.tydic.unicom.upc.service.task.interfaces;

public interface DownloadPayTransServDu {

	/**
	 * 下载微信支付的对帐流水
	 */
	public void commitWxTransDownload(String date) throws Exception;
	
	public void commitWxTransDownload() throws Exception;
	
	/**
	 * 下载支付宝对帐流水
	 * @param date
	 * @throws Exception
	 */
	public void commitAlipayTransDownload(String date) throws Exception;
	
	public void commitAlipayTransDownload() throws Exception;
}
