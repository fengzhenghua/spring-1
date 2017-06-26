package com.tydic.unicom.apc.service.common.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.apc.service.common.interfaces.FileServiceServDu;
import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("FileServiceServDu")
public class FileServiceServDuImpl implements FileServiceServDu{

	private static Logger logger = Logger.getLogger(FileServiceServDuImpl.class);

//	@Autowired
//	@Qualifier("propertiesParamVo")
//	private PropertiesParamVo propertiesParamVo;
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;

	@Override
	public UocMessage uploadFileByLocalfilePath(String localfilePath) {
		UocMessage uocMessage = new UocMessage();
		boolean uploadFile = false;
		String apacheUrl = "";
		String fileLocalName = "";
		logger.info("=============上传本地文件到文件服务器===============");
		String fileServicePath = propertiesParamVo.getFileServicePath();
		String[] fileServicePaths = fileServicePath.split("\\|\\|");
		for(int i=0;i<fileServicePaths.length;i++){
			String resultUpload = "";
			try {
				resultUpload = HttpUtil.httpUploadByPath(fileServicePaths[i], localfilePath);
			} catch (Exception e) {
				e.printStackTrace();
				if(uploadFile){
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.setContent("上传本地文件到文件服务器成功");
					uocMessage.addArg("file_local_url", apacheUrl);
					return uocMessage;
				}
				else{
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("上传本地文件到文件服务器失败");
					return uocMessage;
				}
			}
			if(StringUtil.isEmpty(resultUpload)){
				logger.info("=======================上传本地文件到文件服务器"+(i+1)+"失败！！");
			}
			else{
				logger.info("======================上传本地文件到文件服务器"+(i+1)+"结果："+resultUpload);
				JSONObject resultUploadJson = JSONObject.fromObject(resultUpload);
				String respCode = resultUploadJson.get("RESP_CODE").toString();
				if(respCode == null || (!"0000".equals(respCode))){
					logger.info("=======================上传本地文件到文件服务器"+(i+1)+"结果解析为失败！！");
				}
				else{
					logger.info("=======================上传本地文件到文件服务器"+(i+1)+"结果解析为成功！！");
					apacheUrl = resultUploadJson.get("APACHE_FILE_INFO").toString();
					fileLocalName = resultUploadJson.get("FILE_NAME").toString();
					uploadFile = true;
				}
			}
		}
		if(!uploadFile){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("上传本地文件到文件服务器失败");
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("上传本地文件到文件服务器成功");
			uocMessage.addArg("file_local_url", apacheUrl);
			uocMessage.addArg("file_local_name", fileLocalName);
			/*logger.info("============删除本地临时文件=============");
			result = DeleteLocalTempFile(localfilePath);*/
			return uocMessage;
		}
	}
	
	
//	public UocMessage uploadFile(String fileUrl,String file_base64){
//		UocMessage uocMessage = new UocMessage();
//		boolean result = false;
//		boolean uploadFile = false;
//		String apacheUrl = "";
//		logger.info("==============上传文件（通过url）=============");
//		logger.info("==============下载文件到本地路径下=============");
//		String localSavePath = "";
//		if(file_base64 !=null && !"".equals(file_base64)){
//			fileUrl =".jpg";
//		}
//		try {
//			localSavePath = getLocalSavePath(fileUrl);
//		} catch (Exception e) {
//			e.printStackTrace();
//			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
//			uocMessage.setContent("获取本地路径失败");
//			return uocMessage;
//		}
//		if(file_base64 !=null && !"".equals(file_base64)){
//			result = GenerateImage(file_base64,localSavePath);
//		}else{
//			result = HttpUtil.httpDownload(fileUrl, localSavePath);
//		}
//		if(!result){
//			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
//			uocMessage.setContent("下载文件到本地路径失败");
//			return uocMessage;
//		}
//		logger.info("=============上传本地文件到文件服务器===============");
//		String fileServicePath = propertiesParamVo.getFileServicePath();
//		String[] fileServicePaths = fileServicePath.split("\\|\\|");
//		for(int i=0;i<fileServicePaths.length;i++){
//			String resultUpload = "";
//			try {
//				resultUpload = HttpUtil.httpUploadByPath(fileServicePaths[i], localSavePath);
//			} catch (Exception e) {
//				e.printStackTrace();
//				if(uploadFile){
//					uocMessage.setRespCode(RespCodeContents.SUCCESS);
//					uocMessage.setContent("上传本地文件到文件服务器成功");
//					uocMessage.addArg("file_local_url", apacheUrl);
//					return uocMessage;
//				}
//				else{
//					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
//					uocMessage.setContent("上传本地文件到文件服务器失败");
//					return uocMessage;
//				}
//			}
//			if(StringUtil.isEmpty(resultUpload)){
//				logger.info("=======================上传本地文件到文件服务器"+(i+1)+"失败！！");
//			}
//			else{
//				logger.info("======================上传本地文件到文件服务器"+(i+1)+"结果："+resultUpload);
//				JSONObject resultUploadJson = JSONObject.fromObject(resultUpload);
//				String respCode = resultUploadJson.get("RESP_CODE").toString();
//				if(respCode == null || (!"0000".equals(respCode))){
//					logger.info("=======================上传本地文件到文件服务器"+(i+1)+"结果解析为失败！！");
//				}
//				else{
//					logger.info("=======================上传本地文件到文件服务器"+(i+1)+"结果解析为成功！！");
//					apacheUrl = resultUploadJson.get("APACHE_FILE_INFO").toString();
//					uploadFile = true;
//				}
//			}
//		}
//		if(!uploadFile){
//			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
//			uocMessage.setContent("上传本地文件到文件服务器失败");
//			return uocMessage;
//		}
//		else{
//			uocMessage.setRespCode(RespCodeContents.SUCCESS);
//			uocMessage.setContent("上传本地文件到文件服务器成功");
//			uocMessage.addArg("file_local_url", apacheUrl);
//			logger.info("============删除本地临时文件=============");
//			try {
//				result = DeleteLocalTempFile(localSavePath);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.info("================删除本地文件异常");
//				return uocMessage;
//			}
//			return uocMessage;
//		}
//		/*String fileServicePath = propertiesParamVo.getFileServicePath();
//		String resultUpload = HttpUtil.httpUploadByPath(fileServicePath, localSavePath);
//		if(StringUtil.isEmpty(resultUpload)){
//			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
//			uocMessage.setContent("上传本地文件到文件服务器失败");
//			return uocMessage;
//		}
//		else{
//			logger.info("======================上传本地文件到文件服务器结果："+resultUpload);
//			JSONObject resultUploadJson = JSONObject.fromObject(resultUpload);
//			String respCode = resultUploadJson.get("RESP_CODE").toString();
//			if(respCode == null || (!"0000".equals(respCode))){
//				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
//				uocMessage.setContent("上传本地文件到文件服务器失败");
//				return uocMessage;
//			}
//			else{
//				uocMessage.setRespCode(RespCodeContents.SUCCESS);
//				uocMessage.setContent("上传本地文件到文件服务器成功");
//				uocMessage.addArg("file_local_url", resultUploadJson.get("APACHE_FILE_INFO").toString());
//				logger.info("============删除本地临时文件=============");
//				result = DeleteLocalTempFile(localSavePath);
//			}
//		}*/
//		//return uocMessage;
//	}

	/**删除本地临时文件*/
		public boolean DeleteLocalTempFile(String localFilePath) throws Exception{
			if(null == localFilePath || "".equals(localFilePath)){
				logger.info("==========本地文件路径为空");
				return false;
			}
			File file = new File(localFilePath);
			if(!file.exists()){
				logger.info("==========本地文件路径不存在");
				return false;
			}
			else{
				if(!file.isFile()){
					logger.info("==========本地文件路径不是一个文件");
					return false;
				}
				else{
					if(file.delete()){
						logger.info("==========删除本地文件成功");
						return true;
					}
					else{
						logger.info("==========删除本地文件失败");
						return false;
					}
				}
			}
		}

	/**获取本地临时保存路径和文件名*/
//	public String getLocalSavePath(String fileServiceName) throws Exception{
//		String pathAndName = "";
//		String path = "";
//		String fileName = "";
//		//获取文件临时保存路径
//		path += propertiesParamVo.getLocalTempFilePath();
//		//生成文件名
//		int idx = fileServiceName.lastIndexOf(".");
//		if(idx<0){
//        	//无后缀
//        	fileName = "Uoc"+System.currentTimeMillis();
//        }
//        else{
//        	//有后缀 则进行文件名处理
//        	fileName = "Uoc"+System.currentTimeMillis()+fileServiceName.substring(idx) ;
//        }
//		pathAndName = path+"/"+fileName;
//		logger.info("===========本地临时文件保存路径和文件名:"+pathAndName);
//		return pathAndName;
//	}

	
//	public UocMessage imageCompressEncode(String imageUrl) throws Exception {
//		
//		return null;/*
//		UocMessage uocMessage = new UocMessage();
//
//		if (StringUtils.isEmpty(imageUrl)) {
//			uocMessage.setContent("图像文件url为空");
//			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
//			return uocMessage;
//		}
//
//		ImgCompressUtil imgComUtil = new ImgCompressUtil();
//		imgComUtil.saveToFile(imageUrl); // 从URL读取图片，并保存
//		BufferedImage compressImg = imgComUtil.resizeFix(520, 520); // 图片压缩
//		String str = imgComUtil.generateImageBase64(compressImg);// 将图片转成base64编码
//		imgComUtil.closeHttpConn();
//
//		if (StringUtils.isNotEmpty(str)) {
//			uocMessage.setContent("图像文件下载转码成功");
//			uocMessage.setRespCode(RespCodeContents.SUCCESS);
//			uocMessage.addArg("file_base64", str);
//			return uocMessage;
//		}
//
//		uocMessage.setContent("图像文件下载转码失败");
//		uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
//		return uocMessage;
//	*/}

	 /** 
     * 根据字节数组字符串进行Base64解码并生成文件 
     * @param imgStr 
     * @param savedImagePath 
     * @return 
     */  
//    public static boolean GenerateImage(String imgStr, String savedImagePath) {  
//        // 文件字节数组字符串数据为空  
//        if (imgStr == null)   
//            return false;  
//        BASE64Decoder decoder = new BASE64Decoder();  
//        try {  
//        	FileOutputStream write = new FileOutputStream(new File(savedImagePath));
//			byte[] decoderBytes = decoder.decodeBuffer(imgStr);
//			write.write(decoderBytes);
//			write.close();
//            return true;  
//        } catch (Exception e) {  
//        	 e.printStackTrace();
//        	return false;  
//        }  
//    }  
}
