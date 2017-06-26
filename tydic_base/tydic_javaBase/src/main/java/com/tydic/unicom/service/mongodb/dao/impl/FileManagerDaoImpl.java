package com.tydic.unicom.service.mongodb.dao.impl;

import java.io.File;
import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.tydic.unicom.service.mongodb.dao.interfaces.FileManagerDao;

@Repository("fileManagerDao")
public class FileManagerDaoImpl implements FileManagerDao {

	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/*
	 * public String saveFile(Map map, File file, String collectionName) throws
	 * Exception { DB db = mongoTemplate.getDb(); GridFS gridFs = new
	 * GridFS(db,collectionName); GridFSInputFile gfs = gridFs.createFile(file);
	 * 获取所有map内的查询字段 Set<String> key = map.keySet();
	 * 将map内的查询字段key和value赋值到GridFSInputFile对象中，以便进行保存数据 for (Iterator<String>
	 * it = key.iterator(); it.hasNext();) { String temp = (String) it.next();
	 * gfs.put(temp, map.get(temp)); } gfs.save(); return
	 * gfs.getId().toString(); }
	 */

	/*
	 * public GridFSDBFile getGridFSDBFileOne(String collectionName, String
	 * filename) throws Exception { DB db = mongoTemplate.getDb(); GridFS gridFS
	 * = new GridFS(db, collectionName); 根据文件名查找文件 GridFSDBFile dbfile =
	 * gridFS.findOne(filename); if (dbfile != null) { return dbfile; } return
	 * null; }
	 */

	/*
	 * public List<GridFSDBFile> getGridFSDBFileList(String collectionName, Map
	 * query) throws Exception { DB db = mongoTemplate.getDb(); GridFS gridFS =
	 * new GridFS(db, collectionName); 将Map对象转换为DBObject对象类型，以便进行查询操作 DBObject
	 * dbObject = new BasicDBObject(); Set<String> key = query.keySet(); for
	 * (Iterator<String> it = key.iterator(); it.hasNext();) { String temp =
	 * (String) it.next(); dbObject.put(temp, query.get(temp)); }
	 * 根据转换后的DBObject对象查询出符合条件的数据 List<GridFSDBFile> dbfile =
	 * gridFS.find(dbObject); if (dbfile != null && dbfile.size() > 0) { return
	 * dbfile; } else { return null; } }
	 */

	/*
	 * @Override public InputStream getInputStreamOne(String collectionName,
	 * String filename) throws Exception { GridFSDBFile
	 * gridfs=getGridFSDBFileOne(collectionName,filename); if(gridfs==null){
	 * return null; } return gridfs.getInputStream(); }
	 */

	/*
	 * @Override public List<InputStream> getInputStreamList(String
	 * collectionName, Map query) throws Exception { List<GridFSDBFile>
	 * gridfs=getGridFSDBFileList(collectionName,query);
	 * if(gridfs==null||gridfs.size()<=0){ return null; } 将List<GridFSDBFile>
	 * 转换为List<InputStream>对象 List<InputStream> list=new
	 * ArrayList<InputStream>(); for (GridFSDBFile gridFSDBFile : gridfs) {
	 * list.add(gridFSDBFile.getInputStream()); } return list; }
	 */

	/*
	 * @Override public GridFSDBFile getGridFSDBFileOneById(String
	 * collectionName,String id)throws Exception{ DB db = mongoTemplate.getDb();
	 * GridFS gridFS = new GridFS(db, collectionName); GridFSDBFile
	 * gridfile=gridFS.find(new ObjectId(id)); return gridfile; }
	 * 
	 * @Override public InputStream getInputStreamById(String collectionName,
	 * String id)throws Exception{ DB db = mongoTemplate.getDb(); GridFS gridFS
	 * = new GridFS(db, collectionName); GridFSDBFile gridfile=gridFS.find(new
	 * ObjectId(id)); return gridfile.getInputStream(); }
	 */
	
	/** 
	 * @Method: removeFile 
	 * @Description: TODO 删除文件
	 * @param @param fileId 文件ID
	 * @param @throws Exception
	 * @return void
	 * @throws 
	 */
	@Override
	public void removeFile(String Id) throws Exception {
		DB db = mongoTemplate.getDb();
		GridFS gridFS = new GridFS(db);
		gridFS.remove(new ObjectId(Id));
	}
	
	/** 
	 * @Method: saveFile 
	 * @Description: TODO 保存文件
	 * @param @param file 保存的文件
	 * @param @return	文件ID
	 * @param @throws Exception
	 * @return String 
	 * @throws 
	 */
	@Override
	public String saveFile(File file) throws Exception {
		DB db = mongoTemplate.getDb();
		GridFS gridFS = new GridFS(db);
		GridFSInputFile gfs = gridFS.createFile(file);
		gfs.save();
		return gfs.getId().toString();
	}
	
	/**
	 * @author JinXue 2014年11月18日 上午11:08:28
	 * @Method: saveFile 
	 * @Description: TODO 保存文件
	 * @param byteFile 文件的二进制
	 * @return 文件ID
	 * @throws Exception 
	 * @see com.tydic.unicom.service.mongodb.dao.interfaces.FileManagerDao#saveFile(byte[]) 
	 */
	@Override
	public String saveFile(byte[] byteFile) throws Exception {
		DB db = mongoTemplate.getDb();
		GridFS gridFS = new GridFS(db);
		GridFSInputFile gfs =gridFS.createFile(byteFile);
		gfs.save();
		return gfs.getId().toString();
	}


	
	/** 
	 * @Method: getInputStreamById 
	 * @Description: TODO	根据fileId查找文件
	 * @param @param fileId 文件ID
	 * @param @return	文件输入流
	 * @param @throws Exception
	 * @return InputStream 
	 * @throws 
	 */
	@Override
	public InputStream getInputStreamById(String fileId) throws Exception {
		DB db=mongoTemplate.getDb();
		GridFS gridFS=new GridFS(db);
		GridFSDBFile gridFile=gridFS.find(new ObjectId(fileId));
		if(gridFile!=null){
			return gridFile.getInputStream();
		}else{
			return null;
		}
	}
	
	/**
	 * @author JinXue 2014年11月18日 上午11:15:52
	 * @Method: getByteArrayById 
	 * @Description: TODO 根据fileId查找文件
	 * @param fileId 文件ID
	 * @return	文件二进制
	 * @throws Exception 
	 * @throws 
	 */
	@Override
	public byte[] getByteArrayById(String fileId) throws Exception {
		DB db=mongoTemplate.getDb();
		GridFS gridFS=new GridFS(db);
		GridFSDBFile gridFile=gridFS.find(new ObjectId(fileId));	
		if(gridFile!=null){
			Long length=gridFile.getLength();
			byte[] bytes=new byte[Integer.valueOf(length.toString())];
			gridFile.getInputStream().read(bytes);
			return bytes;
		}else{
			return null;
		}
	}
     
}
	