package com.tydic.unicom.uoc.service.es.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTest {

	public static void main(String[] args) {
		boolean result = false;
		try {
			// query();
			//单条数据删除
			/*result = delete();
			System.out.println("=============delete======"+result);*/
			//单条数据插入
			/*result = insertEs();
			System.out.println("=============insertEs======"+result);*/
			//单条数据获取
			/*Map<String, Object> dataMap = get();
			List<Map<String,Object>> infosaleorderMap = (List<Map<String, Object>>) dataMap.get("info_sale_order");
			System.out.println("<>"+dataMap.get("sale_order_no")+"<>"+infosaleorderMap.get(1).get("accept_system"));*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean insertEs() throws Exception{
		EsServiceServDuImpl es = new EsServiceServDuImpl();
		String id = "201701190006";
		String indexName = "uoc_test1";
		String typeName = "01-18";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("sale_order_no", id);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		String createTime1 = EsDateUtil.formatDateToEs("2016-01-12 12:12:12");
		String finalTime1 = EsDateUtil.formatDateToEs("2017-11-12 12:12:12");

		String createTime2 = EsDateUtil.formatDateToEs("2016-02-12 12:12:12");
		String finalTime2 = EsDateUtil.formatDateToEs("2017-12-12 12:12:12");
		map1.put("accept_system", "uoc_uoc");
		map1.put("create_time", createTime1);
		map1.put("finish_time", finalTime1);
		map2.put("accept_system", "yun xiao shou");
		map2.put("create_time", createTime2);
		map2.put("finish_time", finalTime2);
		list.add(map1);
		list.add(map2);
		map.put("info_sale_order", list);
		return es.insert(id, indexName, typeName, map);
	}

	public static boolean delete() throws Exception{
		EsServiceServDuImpl es = new EsServiceServDuImpl();
		String id = "201701190002";
		String indexName = "uoc_test1";
		String typeName = "01-18";
		return es.delete(id, indexName, typeName);
	}

	public static Map<String, Object> get() throws Exception{
		EsServiceServDuImpl es = new EsServiceServDuImpl();
		String id = "201701190002";
		String indexName = "uoc_test1";
		String typeName = "01-18";
		return es.get(id, indexName, typeName);
	}

	/*public static void query() throws Exception{

		QueryBuilder builer = QueryBuilders.boolQuery()
	        .must(QueryBuilders.matchPhraseQuery("info_sale_order.accept_system", "uoc_uoc"))
	        .must(QueryBuilders.matchPhraseQuery("id", "201701190006"))
	        .must(QueryBuilders.rangeQuery("info_sale_order.create_time").from(getDateTime("2016-01-11 12:12:12")).to(getDateTime("2016-03-12 12:12:12")))
//	        .mustNot(QueryBuilders.termQuery("isRealMen", false))
//	        .should(QueryBuilders.termQuery("now_home", "山西省太原市"))
	        ;

		DmlAccessor accessor = new DmlAccessorImpl("101.200.72.48:9300");
		String indexName = "uoc_test1";
		String typeName = "01-18";
		DataSearchResultResponse result = accessor.query(new DataSearchRequest(indexName, typeName).dslQuery(builer));
		if(result.getItems() != null && result.getItems().size()>0){
			System.out.println("======================not null");
			System.out.println("=============="+result.getItems().get(0).getValues().get("sale_order_no"));
		}
		else{
			System.out.println("==============null");
		}

	}*/

	/*
	 * public static void query() throws Exception{ EsServiceServDuImpl es = new
	 * EsServiceServDuImpl(); String indexName = "uoc_test1"; String typeName =
	 * "01-18"; List<Map<String,Object>> list = new
	 * ArrayList<Map<String,Object>>(); Map<String,Object> map1 = new
	 * HashMap<String,Object>(); Map<String,Object> map2 = new
	 * HashMap<String,Object>(); map1.put("isDateTime", "yes");
	 * map1.put("paramName", "info_sale_order.create_time");
	 * map1.put("paramValueFrom", getDateTime("2016-01-11 12:12:12"));
	 * map1.put("paramValueTo", getDateTime("2016-02-09 12:12:12"));
	 *
	 * map2.put("isDateTime", "no"); map2.put("paramName",
	 * "info_sale_order.accept_system"); map2.put("paramValue", "uoc_uoc");
	 *
	 * list.add(map1); list.add(map2); List<DataSearchResultItem> resultList =
	 * es.query(indexName, typeName, list); if(resultList != null &&
	 * resultList.size()>0){
	 * System.out.println("============resultList size"+resultList.size());
	 * for(int i=0;i<resultList.size();i++){
	 * System.out.println("============resultList "
	 * +resultList.get(i).getValues().get("sale_order_no")); } } else{
	 * System.out.println("===============null====="); } }
	 */

	public static long getDateTime(String str) throws Exception{
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long timestamp = cal.getTimeInMillis();
		System.out.println("=============="+timestamp);
		return timestamp;
	}

}
