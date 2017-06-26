package com.tydic.unicom.crm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import sun.misc.BASE64Encoder;

import com.tydic.unicom.uoc.service.activiti.impl.HttpUtil;

public class TestTest {

	public static void main(String[] args) {

		/*String str = "Uoc1485228041036";
		String teststr1 = "";
		String teststr2 = "";
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			teststr1 = base64en.encode(md5.digest(str.getBytes()));
			teststr2 = base64en.encode(md5.digest(str.getBytes()));
			System.out.println("====teststr1:"+teststr1+"==teststr2"+teststr2);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		
		//下载test
		/*String DownloadUrl = "http://114.215.144.92:8000/upload/file/2017/01/23/4b78c52c2071e0c606d4e5835dd2984c.jpg";
		boolean result;
		try {
			result = testDownload(DownloadUrl);
			System.out.println("=====Download result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//上传test
		String localFilePath = "C:/Test/temp/UocTest20170205.jpg";
		try {
			testUpload(localFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//删除本地文件test
		/*String localFilePath = "C:/Test/temp/Uoc1485226850793.jpg";
		try {
			testDeleteTempFile(localFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//删除文件服务器上的文件Test
		/*String url = "http://10.77.45.95:8080/UocFileService/fileserver/webDelete?filePath=C:/Test/upload/file/2017/01/24/6aed43cac7e7943b38a232e08e715fe7.jpg&fileName=test.jpg";
		try {
			testDeleteOnService(url);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
	//删除文件服务器上的文件
	public static boolean testDeleteOnService(String url) throws Exception{
		String result = HttpUtil.httpGetClient(url);
		System.out.println("==============testDeleteOnService result :"+ result);
		return true;
	}
	
	//下载文件到本地
	public static boolean testDownload(String fileServiceUrl) throws Exception{
		String saveFile = getLocalSavePath(fileServiceUrl);
		return HttpUtil.httpDownload(fileServiceUrl, saveFile);
	}
	
	//将本地临时文件上传
	public static boolean testUpload(String localFilePath) throws Exception{
		String actionUrl = getFileServicePath();
		String result = HttpUtil.httpUploadByPath(actionUrl, localFilePath);
		System.out.println("==============Upload result :"+ result);
		return true;
	}
	
	//删除本地临时文件
	public static boolean testDeleteTempFile(String localFilePath) throws Exception{
		if(null == localFilePath || "".equals(localFilePath)){
			System.out.println("==========localFilePath 为空");
			return false;
		}
		File file = new File(localFilePath);
		if(!file.exists()){
			System.out.println("==========localFilePath 不存在");
			return false;
		}
		else{
			if(!file.isFile()){
				System.out.println("==========localFilePath 不是一个文件");
				return false;
			}
			else{
				if(file.delete()){
					System.out.println("==========删除文件成功");
					return true;
				}
				else{
					System.out.println("==========删除文件失败");
					return false;
				}
			}
		}
	}
	
	
	/**获取上传文件服务器地址*/
	public static String getFileServicePath() throws Exception{
		String fileServicePath = "";
		Properties props = new Properties();
		InputStream in = TestTest.class.getResourceAsStream("/system_param.properties");
		props.load(in);
		//获取文件服务器上传路径
		fileServicePath = props.getProperty("fileServicePath");
		return fileServicePath;
	}
	
	/**获取本地临时保存路径和文件名*/
	public static String getLocalSavePath(String fileServiceName) throws Exception{
		String pathAndName = "";
		String path = "";
		String fileName = "";
		Properties props = new Properties();
		InputStream in = TestTest.class.getResourceAsStream("/system_param.properties");
		props.load(in);
		//获取文件临时保存路径
		path += props.getProperty("localTempPath");
		//生成文件名
		int idx = fileServiceName.lastIndexOf(".");
		if(idx<0){
        	//无后缀
        	fileName = "Uoc"+System.currentTimeMillis();
        }
        else{
        	//有后缀 则进行文件名处理
        	fileName = "Uoc"+System.currentTimeMillis()+fileServiceName.substring(idx) ;
        }
		pathAndName = path+"/"+fileName;
		System.out.println("===========pathAndName:"+pathAndName);
		return pathAndName;
	}
}
