package com.tydic.unicom.uoc.service.es.interfaces;

import java.util.List;
import java.util.Map;

import com.tydic.dicdata.core.es.dml.model.DataSearchResultItem;
import com.tydic.unicom.webUtil.UocMessage;

public interface EsServiceServDu {

	/**单条数据插入*/
	public boolean insert(String id,String indexName,String typeName,Map<String, Object> map) throws Exception;
	
	/**多条数据插入*/
	public boolean batchInsert(String indexName,String typeName,List<Map<String, Object>> list) throws Exception;
	
	/**单条数据删除*/
	public boolean delete(String id,String indexName,String typeName) throws Exception;
	
	/**多条数据删除*/
	public boolean batchDelete(String indexName,String typeName,List<String> listId) throws Exception;
	
	/**单条数据是否存在*/
	public boolean isExist(String indexName,String typeName,String id) throws Exception;
	
	/**单条数据更新*/
	public boolean update(String id,String indexName,String typeName,Map<String, Object> map) throws Exception;
	
	/**单条数据获取*/
	public Map<String,Object> get(String id,String indexName,String typeName) throws Exception;
	
	/**根据传入的条件获取多条数据*/
	public UocMessage query(String indexName,String typeName,List<Map<String,Object>> list,Map<String,Object> paramsMap) throws Exception;
	/**根据传入的条件获取多条数据,从整个indexName*/ 
	public UocMessage query(String indexName,List<Map<String,Object>> list,Map<String,Object> paramsMap) throws Exception;
	
	/**根据传入的条件获取多条数据,从整个indexName*/ 
	public UocMessage query(String[] indexName,String[] typeName ,List<Map<String,Object>> list,Map<String,Object> paramsMap) throws Exception;

	
}
