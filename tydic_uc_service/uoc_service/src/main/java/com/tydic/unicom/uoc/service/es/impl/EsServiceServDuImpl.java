package com.tydic.unicom.uoc.service.es.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tydic.dicdata.core.es.dml.DmlAccessor;
import com.tydic.dicdata.core.es.dml.DmlAccessorImpl;
import com.tydic.dicdata.core.es.dml.model.DataRowRequest;
import com.tydic.dicdata.core.es.dml.model.DataSearchRequest;
import com.tydic.dicdata.core.es.dml.model.DataSearchResultResponse;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.uoc.service.es.interfaces.EsServiceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;
@Service("EsServiceServDu")
public class EsServiceServDuImpl implements EsServiceServDu{

	private static Logger logger = Logger.getLogger(EsServiceServDuImpl.class);

	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;

	@Override
	public boolean insert(String id, String indexName, String typeName,Map<String, Object> map) throws Exception {
		logger.info("==============插入单条数据(es)=============");
		boolean result = false;
		String addr = propertiesParamVo.getEsServiceAddress();
		System.out.println("===========addr==="+addr);
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		result = accessor.insert(new DataRowRequest(indexName, typeName, id).source(map), true);
		accessor.close();
		return result;
	}

	@Override
	public boolean batchInsert(String indexName, String typeName,List<Map<String, Object>> list) throws Exception {
		logger.info("===================多条数据插入（es）=====================");
		boolean result = false;
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		List<DataRowRequest> rows = new ArrayList<DataRowRequest>();
		for(int i=0;i<list.size();i++){
			rows.add(new DataRowRequest(indexName, typeName, list.get(i).get("id").toString()).source(list.get(i)));
		}
		result = accessor.batchInsert(rows, true);
		accessor.close();
		return result;
	}
	

	@Override
	public boolean delete(String id, String indexName, String typeName) throws Exception {
		logger.info("==============单条数据删除（es）===============");
		boolean result = false;
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		result = accessor.remove(new DataRowRequest(indexName, typeName, id));
		accessor.close();
		return result;
	}

	@Override
	public boolean batchDelete(String indexName, String typeName, List<String> listId) throws Exception {
		logger.info("================多条数据删除（es）=================");
		boolean result = false;
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		List<DataRowRequest> rows = new ArrayList<DataRowRequest>();
		for(int i=0;i<listId.size();i++){
			rows.add(new DataRowRequest(indexName, typeName, listId.get(i)));
		}
		result = accessor.batchRemove(rows);
		accessor.close();
		return result;
	}

	@Override
	public boolean isExist(String indexName, String typeName, String id) throws Exception {
		logger.info("===========单条数据是否存在（es）============");
		boolean result = false;
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		result = accessor.existRow(new DataRowRequest(indexName, typeName, id));
		accessor.close();
		return result;
	}
	
	@Override
	public boolean update(String id, String indexName, String typeName,Map<String, Object> map) throws Exception {
		logger.info("==============单条数据更新(es)============");
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		accessor.update(new DataRowRequest(indexName, typeName, id).source(map), true);
		accessor.close();
		return true;
	}

	@Override
	public Map<String, Object> get(String id, String indexName,String typeName) throws Exception {
		logger.info("==============单条数据获取，根据id(es)============");
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		DataRowRequest tes = new DataRowRequest(indexName, typeName, id);
		Map<String, Object> resultMap = accessor.getMap(tes);
		accessor.close();
		if(resultMap != null){
			return resultMap;
		}
		else{
			return null;
		}
	}

	@Override
	public UocMessage query(String indexName, String typeName,List<Map<String, Object>> list,Map<String,Object> paramsMap) throws Exception {
		logger.info("===============根据条件获取多条数据(es)=============");
		UocMessage uocMessage = new UocMessage();
		BoolQueryBuilder builer = QueryBuilders.boolQuery();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			//日期查询
			if(map.get("isDateTime") != null && "yes".equals(map.get("isDateTime"))){
				builer.must(QueryBuilders.rangeQuery(map.get("paramName").toString()).from(map.get("paramValueFrom")).to(map.get("paramValueTo")));
			}
			//类似数据库in条件查询
			else if(map.get("isMultiQuery") != null && "yes".equals(map.get("isMultiQuery"))){
				builer.must(QueryBuilders.termsQuery(map.get("paramName").toString(), map.get("paramValue")));
			}
			//类似与数据库and条件查询
			else{
				builer.must(QueryBuilders.matchPhraseQuery(map.get("paramName").toString(), map.get("paramValue")));
			}
		}
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		DataSearchRequest dataSearchRequest = new DataSearchRequest(indexName, typeName).dslQuery(builer);

		if(paramsMap != null){
			//设置查询数据的条数
			if(paramsMap.get("size") != null && !"".equals(paramsMap.get("size").toString())){
				dataSearchRequest.size(Integer.parseInt(paramsMap.get("size").toString()));
			}
			//设置查询数据的开始下标
			if(paramsMap.get("from") != null && !"".equals(paramsMap.get("from").toString())){
				dataSearchRequest.from(Integer.parseInt(paramsMap.get("from").toString()));
			}
			//设置排序字段和正序倒序
			if(paramsMap.get("sort") != null && !"".equals(paramsMap.get("sort").toString())){
				//选择升序排列
				if(paramsMap.get("sort_asc_desc") != null || "desc".equals(paramsMap.get("sort_asc_desc").toString())){
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_DESC);
				}
				//选择降序排列
				else if(paramsMap.get("sort_asc_desc") != null && "asc".equals(paramsMap.get("sort_asc_desc").toString())){
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_ASC);
				}
				//sort_asc_desc为空时，默认降序排列
				else{
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_DESC);
				}
			}
		}

		DataSearchResultResponse result = accessor.query(dataSearchRequest);
		accessor.close();
		
		if(result == null){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询es数据失败");
			return uocMessage;
		}
		
		if(result.getItems() != null && result.getItems().size()>0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("查询es数据成功");
			uocMessage.addArg("total", result.getTotal());
			uocMessage.addArg("data", result.getItems());
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询es数据失败");
			return uocMessage;
		}
	}

	@Override
	public UocMessage query(String indexName, List<Map<String, Object>> list,
			Map<String, Object> paramsMap) throws Exception {
		logger.info("===============根据条件获取多条数据(es)=============");
		UocMessage uocMessage = new UocMessage();
		BoolQueryBuilder builer = QueryBuilders.boolQuery();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			//日期查询
			if(map.get("isDateTime") != null && "yes".equals(map.get("isDateTime"))){
				builer.must(QueryBuilders.rangeQuery(map.get("paramName").toString()).from(map.get("paramValueFrom")).to(map.get("paramValueTo")));
			}
			//类似数据库in条件查询
			else if(map.get("isMultiQuery") != null && "yes".equals(map.get("isMultiQuery"))){
				builer.must(QueryBuilders.termsQuery(map.get("paramName").toString(), map.get("paramValue")));
			}
			//类似与数据库and条件查询
			else{
				builer.must(QueryBuilders.matchPhraseQuery(map.get("paramName").toString(), map.get("paramValue")));
			}
		}
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		DataSearchRequest dataSearchRequest = new DataSearchRequest(indexName).dslQuery(builer);

		if(paramsMap != null){
			//设置查询数据的条数
			if(paramsMap.get("size") != null && !"".equals(paramsMap.get("size").toString())){
				dataSearchRequest.size(Integer.parseInt(paramsMap.get("size").toString()));
			}
			//设置查询数据的开始下标
			if(paramsMap.get("from") != null && !"".equals(paramsMap.get("from").toString())){
				dataSearchRequest.from(Integer.parseInt(paramsMap.get("from").toString()));
			}
			//设置排序字段和正序倒序
			if(paramsMap.get("sort") != null && !"".equals(paramsMap.get("sort").toString())){
				//选择升序排列
				if(paramsMap.get("sort_asc_desc") != null && "desc".equals(paramsMap.get("sort_asc_desc").toString())){
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_DESC);
				}
				//选择降序排列
				else if(paramsMap.get("sort_asc_desc") != null && "asc".equals(paramsMap.get("sort_asc_desc").toString())){
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_ASC);
				}
				//sort_asc_desc为空时，默认降序排列
				else{
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_DESC);
				}
			}
		}

		DataSearchResultResponse result = accessor.query(dataSearchRequest);
		accessor.close();
		
		if(result == null){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询es数据失败");
			return uocMessage;
		}
		
		if(result.getItems() != null && result.getItems().size()>0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("查询es数据成功");
			uocMessage.addArg("total", result.getTotal());
			uocMessage.addArg("data", result.getItems());
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询es数据失败");
			return uocMessage;
		}
	}

	@Override
	public UocMessage query(String[] indexName, String[] typeName,
			List<Map<String, Object>> list, Map<String, Object> paramsMap)
			throws Exception {
		logger.info("===============根据条件获取多条数据(es)=============");
		UocMessage uocMessage = new UocMessage();
		BoolQueryBuilder builer = QueryBuilders.boolQuery();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			//日期查询
			if(map.get("isDateTime") != null && "yes".equals(map.get("isDateTime"))){
				builer.must(QueryBuilders.rangeQuery(map.get("paramName").toString()).from(map.get("paramValueFrom")).to(map.get("paramValueTo")));
			}
			//类似数据库in条件查询
			else if(map.get("isMultiQuery") != null && "yes".equals(map.get("isMultiQuery"))){
				builer.must(QueryBuilders.termsQuery(map.get("paramName").toString(), map.get("paramValue")));
			}
			//类似与数据库and条件查询
			else{
				builer.must(QueryBuilders.matchPhraseQuery(map.get("paramName").toString(), map.get("paramValue")));
			}
		}
		String addr = propertiesParamVo.getEsServiceAddress();
		DmlAccessor accessor = new DmlAccessorImpl(addr);
		DataSearchRequest dataSearchRequest = new DataSearchRequest(indexName,typeName).dslQuery(builer);

		if(paramsMap != null){
			//设置查询数据的条数
			if(paramsMap.get("size") != null && !"".equals(paramsMap.get("size").toString())){
				dataSearchRequest.size(Integer.parseInt(paramsMap.get("size").toString()));
			}
			//设置查询数据的开始下标
			if(paramsMap.get("from") != null && !"".equals(paramsMap.get("from").toString())){
				dataSearchRequest.from(Integer.parseInt(paramsMap.get("from").toString()));
			}
			//设置排序字段和正序倒序
			if(paramsMap.get("sort") != null && !"".equals(paramsMap.get("sort").toString())){
				//选择升序排列
				if(paramsMap.get("sort_asc_desc") != null && "desc".equals(paramsMap.get("sort_asc_desc").toString())){
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_DESC);
				}
				//选择降序排列
				else if(paramsMap.get("sort_asc_desc") != null && "asc".equals(paramsMap.get("sort_asc_desc").toString())){
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_ASC);
				}
				//sort_asc_desc为空时，默认降序排列
				else{
					dataSearchRequest.sort(paramsMap.get("sort").toString(), DataSearchRequest.SORT_DESC);
				}
			}
		}

		DataSearchResultResponse result = accessor.query(dataSearchRequest);
		accessor.close();
		
		if(result == null){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询es数据失败");
			return uocMessage;
		}
		
		if(result.getItems() != null && result.getItems().size()>0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("查询es数据成功");
			uocMessage.addArg("total", result.getTotal());
			uocMessage.addArg("data", result.getItems());
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询es数据失败");
			return uocMessage;
		}
	}

}
