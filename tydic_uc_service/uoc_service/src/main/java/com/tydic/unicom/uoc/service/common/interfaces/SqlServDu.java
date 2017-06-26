package com.tydic.unicom.uoc.service.common.interfaces;

public interface SqlServDu {

	/**
	 * 传入sql进行数据库的插入操作
	 * */
	public boolean createBySql(String sql) throws Exception;
	
	/**
	 * 传入sql进行数据库删除操作
	 * */
	public boolean deleteBySql(String sql) throws Exception;
	
	/**
	 * 传入sql进行数据库更新操作
	 * */
	public boolean updateBySql(String sql) throws Exception;
}
