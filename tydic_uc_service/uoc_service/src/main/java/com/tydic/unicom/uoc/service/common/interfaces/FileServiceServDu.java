package com.tydic.unicom.uoc.service.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface FileServiceServDu {

	/**
	 * 上传文件（通过url进行上传）
	 * */
	public UocMessage uploadFile(String fileUrl,String file_base64) throws Exception;
	
	/**
	 * 上传文件（通过本地路径进行上传）
	 * */
	public UocMessage uploadFileByLocalfilePath(String localfilePath) throws Exception;

	/**
	 * BASE0031 图像文件下载转码
	 * @param imageUrl
	 * @return
	 * @throws Exception
	 */
	public UocMessage imageCompressEncode(String imageUrl) throws Exception;
}
