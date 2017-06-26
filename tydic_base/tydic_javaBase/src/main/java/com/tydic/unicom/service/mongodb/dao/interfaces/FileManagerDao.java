package com.tydic.unicom.service.mongodb.dao.interfaces;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.mongodb.gridfs.GridFSDBFile;


/**
 * <p></p>
 * @author JinXue 2014-9-10 下午3:58:45
 * @ClassName FileManagerDao
 * @Description 管理文件（上传文件、下载文件）
 * @version V1.0  
 */
public interface FileManagerDao {
	
	/** 
	 * @Method: saveFile 
	 * @Description: 保存文件
	 * @param @param map  保存字段；文件名：fileName,别名：aliases,文件类型：contentType等（根据业务可以自行增加字段，但每个集合下尽量字段相同，且大小写敏感）
	 * @param @param file 上传文件
	 * @param @param collectionName 集合名 (第一个保存，则自动创建以该名称命名的集合)
	 * @param @return 文件ID
	 * @param @throws Exception
	 * @return String 
	 * @throws 
	 */
	/*public String saveFile(Map map, File file, String collectionName)
			throws Exception;*/
	/** 
	 * @Method: getGridFSDBFileOne 
	 * @Description: 根据集合名和文件名检索一个文件
	 * @param @param collectionName 集合名
	 * @param @param filename 文件名
	 * @param @return 
	 * @param @throws Exception
	 * @return GridFSDBFile
	 * @throws 
	 */
	/*public GridFSDBFile getGridFSDBFileOne(String collectionName, String filename)
			throws Exception;*/

	
	/** 
	 * @Method: getGridFSDBFileList 
	 * @Description: 根据集合名和查询字段检索一类文件
	 * @param @param collectionName  集合名
	 * @param @param query 文件名：filename,别名：aliases,文件类型：contentType等 （大小写敏感）查询字段
	 * @param @return 
	 * @param @throws Exception
	 * @return List<GridFSDBFile>
	 * @throws 
	 */
	/*public List<GridFSDBFile> getGridFSDBFileList(String collectionName, Map query)
			throws Exception;*/
	
	/** 
	 * @Method: getInputStreamOne 
	 * @Description: 根据集合名和文件名检索一个文件的输入流
	 * @param @param collectionName 集合名
	 * @param @param filename 文件名
	 * @param @return
	 * @param @throws Exception
	 * @return InputStream
	 * @throws 
	 */
	/*public InputStream getInputStreamOne(String collectionName, String filename)throws Exception;*/
	
	/** 
	 * @Method: getInputStreamList 
	 * @Description: 根据集合名和查询字段检索一类文件输入流集合
	 * @param @param collectionName 集合名
	 * @param @param query 文件名：filename,别名：aliases,文件类型：contentType等（大小写敏感）  查询字段
	 * @param @return
	 * @param @throws Exception
	 * @return List<InputStream>
	 * @throws 
	 */
	/*public List<InputStream> getInputStreamList(String collectionName,Map query)throws Exception;*/
	
	/** 
	 * @Method: getGridFSDBFileOneById 
	 * @Description: TODO 根据集合名和文件ID值检索唯一文件
	 * @param @param collectionName 集合名
	 * @param @param id 文件ID
	 * @param @return 可获取文件的相应字段
	 * @param @throws Exception
	 * @return GridFSDBFile
	 * @throws 
	 */
	/*public GridFSDBFile getGridFSDBFileOneById(String collectionName,String id)throws Exception;*/
	
	/** 
	 * @Method: getInputStreamListById 
	 * @Description: TODO 根据集合名和文件ID值检索唯一文件输入流
	 * @param @param collectionName 集合名
	 * @param @param id 文件ID
	 * @param @return 文件输入流InputStream
	 * @param @throws Exception
	 * @return InputStream
	 * @throws 
	 */
	/*public InputStream getInputStreamById(String collectionName, String id)throws Exception;*/
	
	/** 
	 * @Method: saveFile 
	 * @Description: TODO 保存文件
	 * @param @param file 保存的文件
	 * @param @return	文件ID
	 * @param @throws Exception
	 * @return String 
	 * @throws 
	 */
	public String saveFile(File file)throws Exception;
	/**
	 * @author JinXue 2014年11月18日 上午11:06:26
	 * @Method: saveFile 
	 * @Description: TODO 保存文件
	 * @param byteFile 保存文件的二进制
	 * @return	文件ID
	 * @throws Exception 
	 * @throws 
	 */
	public String saveFile(byte[] byteFile)throws Exception;
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
	 * @Description: TODO	根据fileId查找文件
	 * @param @param fileId 文件ID
	 * @param @return	文件输入流
	 * @param @throws Exception
	 * @return InputStream 
	 * @throws 
	 */
	public InputStream getInputStreamById(String fileId)throws Exception;
	
	/**
	 * @author JinXue 2014年11月18日 上午11:15:52
	 * @Method: getByteArrayById 
	 * @Description: TODO 根据fileId查找文件
	 * @param fileId 文件ID
	 * @return	文件二进制
	 * @throws Exception 
	 * @throws 
	 */
	public byte[] getByteArrayById(String fileId)throws Exception;
}

