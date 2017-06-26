package com.tydic.unicom.vdsBase.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.vdsBase.po.BasePo;
import com.tydic.unicom.vdsBase.po.Count;
import com.tydic.unicom.vdsBase.po.Pageable;
import com.tydic.unicom.vdsBase.service.interfaces.BaseServ;
import com.tydic.unicom.webUtil.Page;

public abstract class BaseServImpl<T> implements BaseServ<T> {

	@Override
	public Condition getCondition(String sqlName) {
		return Condition.build(sqlName);
	}
	
	@Override
	public Condition getCondition(String sqlName, java.util.Map<String,Object> filter) {
		Condition condition = Condition.build(sqlName);
		condition.filter(filter);
		return condition;
	}
	@Override
	public Condition getCondition(Pageable<T> pageable, String sqlName) {
		Condition condition = getCondition(sqlName);
		condition.filter(((BasePo)pageable.getT()).getFilterMap());
		condition.like(((BasePo)pageable.getT()).getLikeMap());
		condition.sort(((BasePo)pageable.getT()).getSortMap());
		return condition;
	}
	@Override
	public Condition getCondition(Pageable<T> pageable,Map<String,Object> paramMap, String sqlName) {
		Condition condition = getCondition(sqlName);
		condition.filter(((BasePo)pageable.getT()).getFilterMap(paramMap));
		condition.like(((BasePo)pageable.getT()).getLikeMap(paramMap));
		condition.sort(((BasePo)pageable.getT()).getSortMap(paramMap));
		return condition;
	}

	@Override
	public Serializable create(Class<T> clazz, T t) {
		return S.get(clazz).create(t);
	}

	@Override
	public  List<T> query(Class<T> clazz, Condition condition) {
		return S.get(clazz).query(condition);
	}

	@Override
	public Integer remove(Class<T> clazz, String ids) {
		return S.get(clazz).remove(ids);
	}
	
	@Override
	public Integer remove(Class<T> clazz, T t) {
		return S.get(clazz).remove((Serializable)t);
	}

	@Override
	public Integer update(Class<T> clazz, T t) {
		return S.get(clazz).update(t); 
	}

	public T get(Class<T> clazz, Serializable serializable) {
		return S.get(clazz).get(serializable);
	}

	@Override
	public Page<T> page(Class<T> clazz,Pageable<T> pageable,String sqlName,String countSqlName) {
		Page<T> page = new Page<T>();
		page.setDataRows(S.get(clazz).page(getCondition(pageable,sqlName), (pageable.getPageNum() - 1) * pageable.getPageSize(), pageable.getPageSize()));
		page.setCurPage(pageable.getPageNum());
		Integer count = pageable.getTotalRows();
		if(!pageable.isSearch()){
			count = count(pageable,countSqlName);
		}
		page.setTotalPages(count%pageable.getPageSize() == 0 ? count / pageable.getPageSize() : (count / pageable.getPageSize() + 1));
		page.setTotalRecords(count);
		return page;
	}
	
	@Override
	public Page<T> page(Class<T> clazz,Pageable<T> pageable,Map<String,Object> paramMap,String sqlName,String countSqlName) {
		Page<T> page = new Page<T>();
		page.setDataRows(S.get(clazz).page(getCondition(pageable,paramMap,sqlName), (pageable.getPageNum() - 1) * pageable.getPageSize(), pageable.getPageSize()));
		page.setCurPage(pageable.getPageNum());
		Integer count = pageable.getTotalRows();
		if(!pageable.isSearch()){
			count = count(pageable,paramMap,countSqlName);
		}
		page.setTotalPages(count%pageable.getPageSize() == 0 ? count / pageable.getPageSize() : (count / pageable.getPageSize() + 1));
		page.setTotalRecords(count);
		return page;
	}
	
	@Override
	public Integer count(Pageable<T> pageable, String countSqlName) {
		return S.get(Count.class).queryFor().queryForInt(getCondition(pageable,countSqlName));
	}
	
	@Override
	public Integer count(Pageable<T> pageable, Map<String,Object> paramMap,String countSqlName) {
		return S.get(Count.class).queryFor().queryForInt(getCondition(pageable,paramMap,countSqlName));
	}

}
