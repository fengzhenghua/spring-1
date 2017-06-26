package com.tydic.unicom.util.vo;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月12日 下午7:20:35
 * @ClassName ExcelBean
 * @Description excel实体
 * @version V1.0
 */
public class ExcelBean implements Comparable<ExcelBean> {
	
	/**
	 * 最大行数
	 */
	public static int MAX_ROW = 65535;
	
	private String password;
	
	/**
	 * 当前sheet的编号,唯一
	 */
	private int num = 0;
	
	/**
	 * excel的名字
	 */
	private String name ;
	/**
	 * 当前sheet的名称
	 */
	private String sheetName ;
	/**
	 * headerCenter
	 */
	private String headerCenter ;
	/**
	 * 当前sheet的表头
	 */
	private String[] tableHeader = new String[] {"测试数据"};
	/**
	 * 表格提示
	 */
	private String[] tablePrompt = new String[] {"测试数据"};
	/**
	 * 列的格式
	 */
	private Object[] columnsFormat;
	/**
	 * 当前sheet的数据
	 */
	private List<Object[]> sheetData;
	/**
	 * 多个sheet情况下使用，如果只有一个sheet，那么请输入为null
	 */
	private HashMap<Integer, ExcelBean> sheets;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public String getHeaderCenter() {
		return headerCenter;
	}
	
	public void setHeaderCenter(String headerCenter) {
		this.headerCenter = headerCenter;
	}
	
	public String[] getTableHeader() {
		return tableHeader;
	}
	
	public void setTableHeader(String[] tableHeader) {
		this.tableHeader = tableHeader;
	}
	
	public String[] getTablePrompt() {
		return tablePrompt;
	}
	
	public void setTablePrompt(String[] tablePrompt) {
		this.tablePrompt = tablePrompt;
	}
	
	public Object[] getColumnsFormat() {
		return columnsFormat;
	}
	
	public void setColumnsFormat(Object[] columnsFormat) {
		this.columnsFormat = columnsFormat;
	}
	
	public List<Object[]> getSheetData() {
		return sheetData;
	}
	
	public void setSheetData(List<Object[]> sheetData) {
		this.sheetData = sheetData;
	}
	
	public HashMap<Integer, ExcelBean> getSheets() {
		return sheets;
	}
	
	public void setSheets(HashMap<Integer, ExcelBean> sheets) {
		this.sheets = sheets;
	}
	
	@Override
	public int compareTo(ExcelBean o) {
		
		return this.num - o.num;
	}
	
}
