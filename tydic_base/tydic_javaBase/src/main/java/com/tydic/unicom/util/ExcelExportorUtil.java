package com.tydic.unicom.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tydic.unicom.util.vo.ExcelBean;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月12日 下午7:07:27
 * @ClassName ExcelExportorUtil
 * @Description 用于所有EXCLE的导出
 * @version V1.0
 */
public class ExcelExportorUtil {
	
	/**
	 * 
	 * @author heguoyong 2017年4月12日 下午8:27:41
	 * @Method: exportByExcelBean
	 * @param excelData
	 * @param request
	 * @param response
	 * @Description: 导出excel
	 */
	
	public static void exportByExcelBean(ExcelBean excelData, HttpServletResponse response) throws Exception {
		Object[] datas = null;
		if (excelData.getSheets() != null) {
			datas = excelData.getSheets().values().toArray();
			Arrays.sort(datas);
		} else {
			datas = new Object[] {excelData};
		}
		XSSFWorkbook excel = create2003Excel(datas);
		if (null != excelData.getPassword() && !"".equals(excelData.getPassword())) {
			// 校验密码
		}
		String fileName = null;
		// 生成文件
		FileOutputStream fos = null;
		fileName = excelData.getName() + "_" + System.currentTimeMillis() + ".xlsx";
		
		fos = new FileOutputStream(fileName);
		XSSFSheet sheet = excel.getSheetAt(0);
		Footer footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		excel.write(fos);
		if (fos != null) {
			fos.close();
		}
		File file = new File(fileName);
		donwload(file, response);
	}
	
	/**
	 * 
	 * @author heguoyong 2017年4月12日 下午8:45:58
	 * @Method: createExcel
	 * @Description: 生成EXCEL
	 * @param datas
	 * @return
	 */
	private static XSSFWorkbook create2003Excel(Object[] datas) {
		XSSFWorkbook excel = new XSSFWorkbook();
		for (Object o : datas) {
			ExcelBean data = (ExcelBean)o;
			// 创建sheet
			XSSFSheet sheet = excel.createSheet(data.getSheetName());
			// 创建表头
			Header header = sheet.getHeader();
			header.setCenter(data.getHeaderCenter());
			XSSFRow headerRow = sheet.createRow(0);
			for (int i = 0; i < data.getTableHeader().length; i++) {
				XSSFCell headerCell = headerRow.createCell(i);
				headerCell.setCellValue(data.getTableHeader()[i]);
				if (data.getColumnsFormat() != null) {
					if (null != data.getColumnsFormat()[i]) {
						setHSSFValidation(sheet, (String[])data.getColumnsFormat()[i], 1, ExcelBean.MAX_ROW, i, i);
					} else {
						setHSSFPrompt(sheet, data.getTableHeader()[i], data.getTablePrompt()[i], 1, ExcelBean.MAX_ROW, i, i);
					}
				}
			}
			
			// 创建数据
			XSSFCellStyle texthssfCellStyle = excel.createCellStyle();
			XSSFDataFormat format = excel.createDataFormat();
			texthssfCellStyle.setDataFormat(format.getFormat("@"));
			XSSFCellStyle inthssfCellStyle = excel.createCellStyle();
			inthssfCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
			XSSFCellStyle floathssfCellStyle = excel.createCellStyle();
			floathssfCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			int rowIndex = 1;
			if (null != data.getSheetData()) {
				for (Object sheetData[] : data.getSheetData()) {
					XSSFRow row = sheet.createRow(rowIndex++);
					for (int i = 0; i < sheetData.length; i++) {
						// 创建第i个单元格
						XSSFCell cell = row.createCell(i);
						if (data.getColumnsFormat() != null) {
							if (null != data.getColumnsFormat()[i]) {
								if ("1".equals(data.getColumnsFormat()[i])) {
									String vString = String.valueOf(sheetData[i] == null ? "" : sheetData[i]);
									
									try {
										Integer integer = Integer.parseInt(vString);
										cell.setCellValue(integer);
										cell.setCellStyle(inthssfCellStyle);
									} catch (NumberFormatException e) {
										cell.setCellValue(vString);
										cell.setCellStyle(texthssfCellStyle);
									}
									try {
										if (vString.indexOf(".") >= 0) {
											Float float1 = Float.parseFloat(vString);
											cell.setCellValue(float1);
											cell.setCellStyle(floathssfCellStyle);
										}
									} catch (NumberFormatException e) {
										cell.setCellValue(vString);
										cell.setCellStyle(texthssfCellStyle);
									}
								} else
									if ("5".equals(data.getColumnsFormat()[i])) {
										String vString = String.valueOf(
										        (sheetData[i] == null || "".equals(sheetData[i])) ? "0" : sheetData[i]);
										if ("0".equals(vString)) {
											
										} else {
											cell.setCellValue(Integer.parseInt(vString));
										}
										cell.setCellStyle(inthssfCellStyle);
									} else
										if ("6".equals(data.getColumnsFormat()[i])) {
											String vString = String.valueOf(
											        (sheetData[i] == null || "".equals(sheetData[i])) ? "0.0" : sheetData[i]);
											if ("0.0".equals(vString)) {
												
											} else {
												cell.setCellValue(Float.parseFloat(vString));
											}
											cell.setCellStyle(floathssfCellStyle);
										} else {
											String vString = String.valueOf(sheetData[i] == null ? "" : sheetData[i]);
											
											try {
												Integer integer = Integer.parseInt(vString);
												cell.setCellValue(integer);
												cell.setCellStyle(inthssfCellStyle);
											} catch (NumberFormatException e) {
												cell.setCellValue(vString);
												cell.setCellStyle(texthssfCellStyle);
											}
										}
							} else {
								String vString = String.valueOf(sheetData[i] == null ? "" : sheetData[i]);
								
								try {
									Integer integer = Integer.parseInt(vString);
									cell.setCellValue(integer);
									cell.setCellStyle(inthssfCellStyle);
								} catch (NumberFormatException e) {
									cell.setCellValue(vString);
									cell.setCellStyle(texthssfCellStyle);
								}
							}
						} else {
							String vString = String.valueOf(sheetData[i] == null ? "" : sheetData[i]);
							
							try {
								Integer integer = Integer.parseInt(vString);
								cell.setCellValue(integer);
								cell.setCellStyle(inthssfCellStyle);
							} catch (NumberFormatException e) {
								cell.setCellValue(vString);
								cell.setCellStyle(texthssfCellStyle);
							}
						}
					}
				}
			}
		}
		return excel;
	}
	
	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 * 
	 * @param sheet 要设置的sheet.
	 * @param textlist 下拉框显示的内容
	 * @param firstRow 开始行
	 * @param endRow 结束行
	 * @param firstCol 开始列
	 * @param endCol 结束列
	 * @return 设置好的sheet.
	 */
	private static XSSFSheet setHSSFValidation(XSSFSheet sheet, String[] textlist, int firstRow, int endRow, int firstCol,
	        int endCol) {
		// 加载下拉列表内容
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		// 数据有效性对象
		return sheet;
	}
	
	/**
	 * 设置单元格上提示
	 * 
	 * @param sheet 要设置的sheet.
	 * @param promptTitle 标题
	 * @param promptContent 内容
	 * @param firstRow 开始行
	 * @param endRow 结束行
	 * @param firstCol 开始列
	 * @param endCol 结束列
	 * @return 设置好的sheet.
	 */
	private static XSSFSheet setHSSFPrompt(XSSFSheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
	        int firstCol, int endCol) {
		// 构造constraint对象
		// 四个参数分别是：起始行、终止行、起始列、终止列
		// 数据有效性对象
		return sheet;
	}
	
	private static void donwload(File file, HttpServletResponse response) throws Exception {
		response.reset();
		response.setContentType("application/x-msdownload");
		response.setCharacterEncoding("UTF-8");
		String fileName = file.getName();
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		
		OutputStream sops = response.getOutputStream();// 不同类型的文件对应不同的MIME类型
		InputStream ips = new FileInputStream(file);
		byte[] content = new byte[ips.available()];
		int length = -1;
		while ((length = ips.read(content)) != -1) {
			sops.write(content, 0, length); // 读入流,保存在Byte数组中
		}
		ips.close();
		sops.flush();
		sops.close();
	}
	
}
