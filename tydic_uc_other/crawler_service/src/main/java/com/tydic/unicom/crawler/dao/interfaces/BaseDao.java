package com.tydic.unicom.crawler.dao.interfaces;

import java.util.List;

public interface BaseDao<T> {
	
	public boolean create(T po) throws Exception;
	public boolean delete(T po) throws Exception;
	public boolean update(T po) throws Exception;
	
	
	public T get(T po) throws Exception;
	public List<T> query(T po) throws Exception;
	

}
