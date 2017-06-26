package com.tydic.unicom.service.mongodb.service.interfaces;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.mongodb.gridfs.GridFSDBFile;


/**
 * <p></p>
 * @author JinXue 2014-9-10 下午4:09:14
 * @ClassName FileManagerServ
 * @Description TODO
 * @version V1.0  
 */
public interface FileManagerServ{
	
	
	/** 
	 * @Method: saveFile 
	 * @Description: TODO 保存文件
	 * @param @param map  保存字段；文件名：fileName,别名：aliases,文件类型：contentType等（根据业务可以自行增加字段，但每个集合下尽量字段相同，且大小写敏感）
	 * @param @param file 上传文件
	 * @param @param collectionName 集合名
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws 
	 */
	/*public String saveFile(Map map,File file,String collectionName)throws Exception;*/
	
	/** 
	 * @Method: removeFile 
	 * @Description: TODO 删除文件
	 * @param @param collectionName	集合名称
	 * @param @param Id	文件ID
	 * @param @throws Exception
	 * @return void
	 * @throws 
	 */
	/*public void removeFile(String collectionName,String Id)throws Exception;*/
	
	/** 
	 * @Method: getGridFSDBFileOne 
	 * @Description: TODO 根据集合名和文件名检索一个文件
	 * @param @param collectionName 集合名
	 * @param @param filename 文件名
	 * @param @return
	 * @param @throws Exception
	 * @return GridFSDBFile
	 * @throws 
	 */
	/*public GridFSDBFile getGridFSDBFileOne(String collectionName,String filename)throws Exception;*/
	
	
	/** 
	 * @Method: getGridFSDBFileList 
	 * @Description: TODO 根据集合名和查询字段检索一类文件
	 * @param @param collectionName 集合名
	 * @param @param query 查询字段  文件名：filename,别名：aliases,文件类型：contentType,用户ID：userId ,业务类型：serviceType等（大小写敏感）
	 * @param @return
	 * @param @throws Exception
	 * @return List<GridFSDBFile>
	 * @throws 
	 */
	/*public List<GridFSDBFile> getGridFSDBFileList(String collectionName,Map query)throws Exception;*/

	/** 
	 * @Method: getInputStreamOne 
	 * @Description: TODO 根据集合名和文件名检索一个文件的输入流
	 * @param @param collectionName 集合名
	 * @param @param filename 文件名
	 * @param @return
	 * @param @throws Exception
	 * @return InputStream
	 * @throws 
	 */
	/*public InputStream getInputStreamOne(String collectionName,String filename)throws Exception;*/

	/** 
	 * @Method: getInputStreamList 
	 * @Description: TODO 根据集合名和查询字段检索一类文件 的输入流集合
	 * @param @param collectionName 集合名
	 * @param @param query 查询字段     文件名：filename,别名：aliases,文件类型：contentType,用户ID：userId ,业务类型：serviceType等
	 * @param @return
	 * @param @throws Exception
	 * @return List<InputStream>
	 * @throws 
	 */
	/*public List<InputStream> getInputStreamList(String collectionName,Map query)throws Exception;*/

	/** 
	 * @Method: getGridFSDBFileOneById 
	 * @Description: TODO 根据集合名和文件ID 检索唯一文件
	 * @param @param collectionName 集合名
	 * @param @param id 文件ID
	 * @param @return
	 * @param @throws Exception
	 * @return GridFSDBFile
	 * @throws 
	 */
	/*public GridFSDBFile getGridFSDBFileOneById(String collectionName,String id)throws Exception;*/
	
	/** 
	 * @Method: getInputStreamListById 
	 * @Description: TODO 根据集合名和文件ID 检索唯一文件输入流
	 * @param @param collectionName 集合名
	 * @param @param id 文件ID
	 * @param @return
	 * @param @throws Exception
	 * @return InputStream
	 * @throws 
	 */
	/*public InputStream getInputStreamById(String collectionName, String id)throws Exception;*/
	/** 
	 * @Method: saveFile 
	 * @Description: TODO	文件保存
	 * @param @param file	文件
	 * @param @return	文件ID
	 * @param @throws Exception
	 * @return String
	 * @throws 
	 */
	public String saveFile(File file)throws Exception;
	
	/**
	 * @author JinXue 2014年11月18日 上午11:30:57
	 * @Method: saveFile 
	 * @Description: TODO 保存文件
	 * @param byteFile 文件的二进制
	 * @return 文件ID
	 * @throws Exception 
	 * @throws 
	 */
	public String saveFile(byte[] byteFile)throws Exception;
	
	/**
	 * @author JinXue 2014年11月18日 上午11:30:57
	 * @Method: saveFile 
	 * @Description: TODO 保存文件
	 * @param byteFile 文件的二进制
	 * @return 文件ID
	 * @throws Exception 
	 * @throws 
	 */
	public String saveFile(InputStream inputStream)throws Exception;
	/** 
	 * @Method: removeFile 
	 * @Description: TODO 删除文件 
	 * @param @param fileId 文件ID
	 * @param @throws Exception
	 * @return void
	 * @throws 
	 */
	public void removeFile(String fileId)throws Exception;
	/** 
	 * @Method: getInputStreamById 
	 * @Description: TODO	通过文件ID获取文件的输入流
	 * @param @param fileId 文件ID
	 * @param @return 文件的输入流
	 * @param @throws Exception
	 * @return InputStream
	 * @throws 
	 */
	public InputStream getInputStreamById(String fileId)throws Exception;
	/** 
	 * @Method: getInputStreamById 
	 * @Description: TODO	通过文件ID
	 * @param @param fileId 文件ID
	 * @param @return 文件的二进制
	 * @param @throws Exception
	 * @return byte[]
	 * @throws 
	 */	public byte[] getBytesById(String fileId)throws Exception;
}

