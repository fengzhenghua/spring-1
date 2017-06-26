package com.tydic.unicom.service.mongodb.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.mongodb.dao.interfaces.FileManagerDao;
import com.tydic.unicom.service.mongodb.service.interfaces.FileManagerServ;

@Service("fileManagerService")
public class FileManagerServImpl implements FileManagerServ {
	@Autowired
	private FileManagerDao fileManagerDao;
	
	/** 
	 * @Method: saveFile 
	 * @Description: TODO	文件保存
	 * @param @param file	文件
	 * @param @return	文件ID
	 * @param @throws Exception
	 * @return String
	 * @throws 
	 */
	@Override
	public String saveFile(File file) throws Exception {
		return fileManagerDao.saveFile(file);
	}
	
	/** 
	 * @Method: removeFile 
	 * @Description: TODO 删除文件 
	 * @param @param fileId 文件ID
	 * @param @throws Exception
	 * @return void
	 * @throws 
	 */
	@Override
	public void removeFile(String fileId) throws Exception {
		fileManagerDao.removeFile(fileId);
	}
	
	/** 
	 * @Method: getInputStreamById 
	 * @Description: TODO	通过文件ID获取文件的输入流
	 * @param @param fileId 文件ID
	 * @param @return 文件的输入流
	 * @param @throws Exception
	 * @return InputStream
	 * @throws 
	 */
	@Override
	public InputStream getInputStreamById(String fileId) throws Exception {
		return fileManagerDao.getInputStreamById(fileId);
	}
	/**
	 * @author JinXue 2014年11月18日 上午11:30:57
	 * @Method: saveFile 
	 * @Description: TODO 保存文件
	 * @param byteFile 文件的二进制
	 * @return 文件ID
	 * @throws Exception 
	 * @throws 
	 */
	@Override
	public String saveFile(byte[] byteFile) throws Exception {
		return fileManagerDao.saveFile(byteFile);
	}
	
	/** 
	 * @Method: getInputStreamById 
	 * @Description: TODO	通过文件ID
	 * @param @param fileId 文件ID
	 * @param @return 文件的二进制
	 * @param @throws Exception
	 * @return byte[]
	 * @throws 
	 * */
	@Override
	public byte[] getBytesById(String fileId) throws Exception {
		return fileManagerDao.getByteArrayById(fileId);
	}
	/** 
	 * @Method: getInputStreamById 
	 * @Description: TODO	通过文件ID
	 * @param @param fileId 文件ID
	 * @param @return 文件的二进制
	 * @param @throws Exception
	 * @return byte[]
	 * @throws 
	@Override
	public byte[] getBytesById(String fileId) throws Exception {
		return fileManagerDao.getByteArrayById(fileId);
	}

	/*@Override
	public String saveFile(Map map, File file, String collectionName)
			throws Exception {
		return fileManagerDao.saveFile(map, file, collectionName);
	}*/

	@Override
    public String saveFile(InputStream inputStream) throws Exception {
	    /** TODO Auto-generated method stub*/
		ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = inputStream.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return saveFile(output.toByteArray());
    }

	

	/*@Override
	public GridFSDBFile getGridFSDBFileOne(String collectionName, String filename)
			throws Exception {
		return fileManagerDao.getGridFSDBFileOne(collectionName, filename);
	}*/

	/*@Override
	public List<GridFSDBFile> getGridFSDBFileList(String collectionName, Map query)
			throws Exception {
		return fileManagerDao.getGridFSDBFileList(collectionName, query);
	}*/

	/*@Override
	public InputStream getInputStreamOne(String collectionName, String filename)
			throws Exception {
		return fileManagerDao.getInputStreamOne(collectionName, filename);
	}*/

	/*@Override
	public List<InputStream> getInputStreamList(String collectionName, Map query)
			throws Exception {
		return fileManagerDao.getInputStreamList(collectionName, query);
	}*/

	/*@Override
	public GridFSDBFile getGridFSDBFileOneById(String collectionName, String id)
			throws Exception {
		return fileManagerDao.getGridFSDBFileOneById(collectionName, id);
	}*/

	/*@Override
	public InputStream getInputStreamById(String collectionName, String id)
			throws Exception {
		return fileManagerDao.getInputStreamById(collectionName, id);
	}*/

	/*@Override
	public void removeFile(String collectionName, String Id) throws Exception {
		fileManagerDao.removeFile(Id);
	}*/

}

