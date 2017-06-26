package com.tydic.unicom.vdsBase.service.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.vdsBase.po.Pageable;
import com.tydic.unicom.webUtil.Page;

public interface BaseServ<T> {
	/**
	 * 
	 * @author yangfei 2014年8月19日 下午4:43:21
	 * @Method: BaseServ 
	 * @Description: TODO 根据sqlName 得到Condition
	 * @param @param sqlName
	 * @param @return
	 * @return 
	 * @throws
	 */
	Condition getCondition(String sqlName);
	/**
	 * 
	 * @author yangfei 2014年8月19日 下午4:44:39
	 * @Method: BaseServ 
	 * @Description: TODO 根据sqlName filter得到Condition
	 * @param @param sqlName
	 * @param @param filter
	 * @param @return
	 * @return 
	 * @throws
	 */
	Condition getCondition(String sqlName,Map<String, Object> filter);
	
	Condition getCondition(Pageable<T> pageable,String sqlName);
	
	Condition getCondition(Pageable<T> pageable,Map<String,Object> paramMap, String sqlName);
	
	Serializable create(Class<T> clazz,T t);
	
	List<T> query(Class<T> clazz,Condition condition);
	
	Integer remove(Class<T> clazz,String ids);
	Integer remove(Class<T> clazz, T t);
	
	Integer update(Class<T> clazz,T t);
	
	Object get(Class<T> clazz,Serializable serializable);
	
	Page<T> page(Class<T> clazz,Pageable<T> pageable,String sqlName,String countSqlName);
	
	Page<T> page(Class<T> clazz,Pageable<T> pageable,Map<String,Object> paramMap,String sqlName,String countSqlName);
	
	Integer count(Pageable<T> pageable,String sqlName);
	
	Integer count(Pageable<T> pageable, Map<String,Object> paramMap,String countSqlName);
	
	//public Page<T> toPage(Pageable<T> pageable,List<T> rowDatas);
}
