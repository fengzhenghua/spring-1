package com.tydic.unicom.webUtil;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Page<T> implements Serializable {
	/** 
     * 总页数 
     */ 
    protected int totalPages; 
    /** 
     * 显示第几页 
     */ 
    protected int curPage; 
    /** 
     * 总记录数 
     */ 
    protected int totalRecords; 
    /** 
     * 保存实际的数据 
     */ 
    /** 
     * 当前页条数
     */ 
    protected int pageSize; 
    
    public int getPageSize() {
    	return pageSize;
    }
	
    public void setPageSize(int pageSize) {
    	this.pageSize = pageSize;
    }
	protected List<T> dataRows;
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<T> getDataRows() {
		return dataRows;
	}
	public void setDataRows(List<T> dataRows) {
		this.dataRows = dataRows;
	} 
    

}
