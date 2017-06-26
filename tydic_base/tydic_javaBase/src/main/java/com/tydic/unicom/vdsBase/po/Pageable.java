package com.tydic.unicom.vdsBase.po;

import java.io.Serializable;

public class Pageable<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int pageNum = 1;
	public int pageSize = 10;
	private boolean search = false;
	private int totalRows = 0;
	public T t;
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	public boolean isSearch() {
		return search;
	}
	public void setSearch(boolean search) {
		this.search = search;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

}
