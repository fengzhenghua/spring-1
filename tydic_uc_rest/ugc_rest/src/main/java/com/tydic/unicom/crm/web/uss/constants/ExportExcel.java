package com.tydic.unicom.crm.web.uss.constants;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.tydic.unicom.crm.web.commTools.PropertiesUtil;

import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;


/**
 * excel导出工具类，支持单一表头
 *
 * @param <T>
 * @author 任性, 干tmd这个项目的项目经理, 别问哥是谁
 */

public class ExportExcel<T extends Serializable> {


    //定义Excel hander  
    private Workbook excelHander = new HSSFWorkbook();

    //需要展示的数据集合  
    private List<T> listData = new ArrayList<T>();


    //表头名称
    private List<String> headNames = new ArrayList<String>();

    //列名称
    private List<String> colNames = new ArrayList<String>();

    //excel的列的长度
    private Integer colLength;

    //每个工作区最多展示的行数
    private Integer maxColumn = 5000;

    //查询条件
    private String queryCondition = "";

    //查询该报表员工姓名
    private String queryStaff = "";

    //列的Map集合
    private Map<String, String> colsMap = new HashMap<String, String>();

    //第一个工作区--多余的列--会加入条件和表头多了两列
    private int firstExCol = 2;
    //其它工作区多余的列--只需加入表头
    private int otherExCol = 1;

    private int reportExCol =1;
    
    /**
     * 缓存成员变量
     */
    private List<String> fieldNameCaches = new ArrayList<String>();

    /**
     * 初始化报表
     *
     * @param data           需要展示的数据
     * @param headNames      传入格式 "head1,head2,head3" 以逗号分隔
     * @param colNames       列名传入格式   "col1,col2,col3"以逗号分隔
     * @param queryCondition 查询条件
     * @param queryStaff     查询员工
     */
    public ExportExcel(List<T> data, String headNames, String colNames, String queryCondition, String queryStaff) {
        this.listData = data;
        this.headNames = this.getListString(headNames, ",");
        this.colNames = this.getListString(colNames, ",");
        this.colLength = this.colNames.size();
        this.queryCondition = queryCondition;
        this.queryStaff = queryStaff;
    }


    /**
     * 生成Excel 文件
     *
     * @param
     * @return
     * @throws IllegalAccessException
     */
    public void createExcel() throws Exception {
    	String province_code=PropertiesUtil.getPropertiesForUrl("province_code");
        int total = this.listData.size(), rowNum = 0;
        List<ExcelSplit> excelSplits = null;
        if("cq".equals(province_code)){
        	excelSplits = this.createExcelSplit(total, this.reportExCol, this.otherExCol);
        } else {
        	excelSplits = this.createExcelSplit(total, this.firstExCol, this.otherExCol);
        }
        Sheet sheet = null;
        for (int i = 0; i < excelSplits.size(); i++) {
            rowNum = 0;
            ExcelSplit es = excelSplits.get(i);
            int star = es.getStar();
            int end = es.getEnd();
            int exCol = es.getExCol();
            String sheetName = es.getSheetName();
            sheet = excelHander.createSheet(sheetName);
            if (i == 0) {
            	if("cq".equals(province_code)){
            		this.createHeader(sheet, 0);
            	} else {
            		this.createQuestion(sheet, 0);
                	this.createHeader(sheet, 1);
            	}
            } else {
                this.createHeader(sheet, 0);
            }
            for (int j = star; j < end; j++, rowNum++) {
                this.setCellValueToRow(this.listData.get(j), sheet, (rowNum + exCol));
            }
        }

    }


    /**
     * 生成问题
     *
     * @param sheet
     * @param rowNum
     */

    private void createQuestion(Sheet sheet, int rowNum) {
        Row questionRow = sheet.createRow(rowNum);
        /**
         * 设定合并单元格区域范围
         *  firstRow  0-based
         *  lastRow   0-based
         *  firstCol  0-based
         *  lastCol   0-based
         */
        CellRangeAddress cra = new CellRangeAddress(0, 0, 1, this.colLength - 1);
        //在sheet里增加合并单元格
        sheet.addMergedRegion(cra);

        Cell cell0 = questionRow.createCell(0);
        cell0.setCellValue("导出人:" + this.queryStaff);
    }

    /**
     * 创建excel头部
     *
     * @param sheet,rowNum
     * @throws IllegalAccessException
     */
    private void createHeader(Sheet sheet, int rowNum) {
        Row headRow = sheet.createRow(rowNum);
        for (int i = 0; i < this.headNames.size(); i++) {
            Cell cell = headRow.createCell(i);
            cell.setCellValue(this.headNames.get(i));
        }
    }


    /**
     * 输出Excel Row 信息
     */
    public void setCellValueToRow(T t, Sheet sheet, int rowNum) throws Exception {
        Row hssfRow = sheet.createRow(rowNum);
        Class clazz = t.getClass();
        initColsMap(t);
        Field[] fields = clazz.getSuperclass().getDeclaredFields();

        for (int i = 0; i < this.colNames.size(); i++) {
            Cell cell = hssfRow.createCell(i);
            String colName = colNames.get(i);
            String methodName = this.colsMap.get(colName);
            Object obj = clazz.getMethod(methodName, null).invoke(t, null);
            //类型转换  
            if (obj instanceof Integer) {
                cell.setCellValue((Integer) obj);
            } else if (obj instanceof String) {
                cell.setCellValue((String) obj);
            } else if (obj instanceof Date) {
                cell.setCellValue((Date) obj);
            } else if (obj instanceof Double) {
                cell.setCellValue((Double) obj);
            } else if (obj instanceof Boolean) {
                cell.setCellValue((Boolean) obj);
            } else if (obj instanceof Float) {
                cell.setCellValue((Float) obj);
            } else if (obj instanceof Long) {
                cell.setCellValue((Long) obj);
            } else {
                //throw new TypeNotPresentException("类型不支持", null);
            }

        }
    }

    private void initColsMap(T t) {
        if (this.colsMap.size() == 0) {
            dealDeclaredFields(t);
        }
        if (this.colsMap.size() != this.colLength) {
            throw new RuntimeException(t.getClass().getName() + "类的和colNames中的长度不相等"+this.colsMap.size()+"||"+this.colLength);
        }
    }

    private void dealDeclaredFields(T t) {
        Class clazz = t.getClass();
        //Field[] fields = clazz.getSuperclass().getDeclaredFields();
        Field[] fields = null;
        String supperClass = clazz.getSuperclass().getName();
        if("com.tydic.unicom.vdsBase.po.BasePo".equals(supperClass) || "com.tydic.unicom.webUtil.BaseVo".equals(supperClass)){
        	fields = clazz.getDeclaredFields();
        } else {
        	fields = clazz.getSuperclass().getDeclaredFields();
        }
//        Field[] fields = clazz.getDeclaredFields();
        //System.out.println("class_name="+ clazz.getName());
        for (Field field : fields) {
            //缓存中是否存在允许字段
            //System.out.println("field_name=" + field.getName());
            if (this.filedNameInCols(field.getName())) {
                field.setAccessible(true);
                Class clazzType = field.getType();
                String methodName = "";
                if (clazzType.getName().equals("boolean") || clazzType.getName().equals("java.lang.Boolean")) {
                    methodName = "is" + this.toFirstToUp(field.getName());
                } else {
                    methodName = "get" + this.toFirstToUp(field.getName());
                }
                this.colsMap.put(field.getName(), methodName);
            }
        }
    }

    private boolean filedNameInCols(String fileName) {
        boolean flag = false;
        for (int i = 0; i < this.colNames.size(); i++) {
          //  System.out.println("col_name=" + this.colNames.get(i));
            if (this.colNames.get(i).equals(fileName)) {
                flag = true;
                break;
            }

        }
        return flag;
    }

    /**
     * 判断Cache 是否有对应的FiledName
     *
     * @param fieldName String
     * @return boolean
     */
    private boolean isCacheFiledName(String fieldName) {
        if (fieldName == null) {
            return false;
        }
        for (String fieldNameCache : this.fieldNameCaches) {
            if (fieldName.equals(fieldNameCache)) {
                return true;
            }
        }
        return false;
    }

    public ByteArrayOutputStream getOutputStream() {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            this.excelHander.write(output);
            return output;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得输入流
     *
     * @return InputStream
     */
    private InputStream getInputStream() {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            this.excelHander.write(output);
            byte b[] = output.toByteArray();
            inputStream = new ByteArrayInputStream(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }

    /**
     * 查找对应的类自定义方法
     *
     * @param methodName
     * @return boolean
     */
    public boolean isClassMethodName(String methodName) {
        if (methodName != null) {
            if ("getClass".equals(methodName)) {
                return false;
            }
            if (methodName.startsWith("get")
                    || methodName.startsWith("is")
                    || methodName.startsWith("set")) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 将String类型色字符串拆分为List<String>
     *
     * @param listString
     * @return
     */
    public List<String> getListString(String listString, String regex) {
        List<String> tempList = new ArrayList<String>();
        if (listString != null) {
            String[] tempArray = listString.split(regex);
            for (int i = 0; i < tempArray.length; i++) {
                tempList.add(tempArray[i]);
            }
        }
        return tempList;
    }

    /**
     * 首字母转换为小写
     *
     * @param str
     * @return String
     */
    public String toFirstToLower(String str) {
        char chars[] = str.toCharArray();
        if (chars != null && chars.length > 0) {
            if (chars[0] > 'A') {
                chars[0] = (char) (chars[0] + 32);
            }
        }
        return new String(chars);

    }

    /**
     * 首字母转为大写
     *
     * @param str String
     * @return String
     */
    public String toFirstToUp(String str) {
        char chars[] = str.toCharArray();
        if (chars != null && chars.length > 0) {
            if (chars[0] >= 'a') {
                chars[0] = (char) (chars[0] - 32);
            }
        }
        return new String(chars);

    }

    private class ExcelSplit {
        int star;
        int end;
        int exCol;
        String sheetName;

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getExCol() {
            return exCol;
        }

        public void setExCol(int exCol) {
            this.exCol = exCol;
        }

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        @Override
        public String toString() {
            return this.star + "开始---" + this.end + "结束";
        }

    }

    /**
     * 计算excel需要分页的工作区
     *
     * @param total      集合总数
     * @param firstExCol
     * @param otherExCol
     * @return
     */
    private List<ExcelSplit> createExcelSplit(int total, int firstExCol, int otherExCol) {
        List<ExcelSplit> lists = new ArrayList<ExcelSplit>();
        int maxColumn = this.maxColumn;
        int tempSize = (total + maxColumn - 1) / maxColumn;

        int rTotal = total + firstExCol + (tempSize - 1) * otherExCol;

        int pageTotal = (rTotal + maxColumn - 1) / maxColumn;
        int sheetEnd = 0;
        for (int i = 0; i < pageTotal; i++) {
            ExcelSplit excel = new ExcelSplit();
            excel.setSheetName("第" + (i + 1) + "页");
            if (i == 0) {
                excel.setStar(0);
                excel.setExCol(firstExCol);
                if ((maxColumn - firstExCol) > total) {
                    sheetEnd = total;
                } else {
                    sheetEnd = maxColumn - firstExCol;
                }
            } else {
                excel.setStar(sheetEnd);
                excel.setExCol(otherExCol);
                if ((sheetEnd + maxColumn - otherExCol) > total) {
                    sheetEnd = total;
                } else {
                    sheetEnd = sheetEnd + maxColumn - otherExCol;
                }
            }
            excel.setEnd(sheetEnd);
            lists.add(excel);
        }
        return lists;
    }

    /**
     * 设置response相应头
     *
     * @param fileName
     * @param response
     */
    public void setResponseHeader(String fileName, HttpServletResponse response) {
    	String province_code=PropertiesUtil.getPropertiesForUrl("province_code");
    	if("cq".equals(province_code)){
    		response.reset();
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("UTF-8");
            fileName = fileName + ".xls";
            try {
    	        response.setHeader("Content-Disposition", "attachment;filename="
    	                + new String(fileName.getBytes("GBK"), "ISO8859-1"));
            } catch (UnsupportedEncodingException e1) {
    	        /** TODO Auto-generated catch block */
    	        e1.printStackTrace();
            }

            OutputStream sops = null;// 不同类型的文件对应不同的MIME类型
            try {
                sops = response.getOutputStream();
                this.excelHander.write(sops);

                    sops.flush();
                    sops.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	response.reset();
	        response.setContentType("multipart/form-data");
	        response.setCharacterEncoding("UTF-8");
	        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
	
	        OutputStream sops = null;// 不同类型的文件对应不同的MIME类型
	        try {
	            sops = response.getOutputStream();
	            this.excelHander.write(sops);
	
	            sops.flush();
	            sops.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
    }
    
}  