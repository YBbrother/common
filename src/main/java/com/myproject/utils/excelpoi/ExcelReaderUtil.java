package com.myproject.utils.excelpoi;

import java.util.List;

public class ExcelReaderUtil {
	
	//excel2007扩展名
	public static final String EXCEL07_EXTENSION = ".xlsx";
	
	
	public static void sendRows(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList) {
		StringBuffer oneLineSb = new StringBuffer();
		oneLineSb.append(filePath);
		oneLineSb.append("--");
		oneLineSb.append("sheet" + sheetIndex);
		oneLineSb.append("::" + sheetName);
		oneLineSb.append("--");
		oneLineSb.append("row" + curRow);
		oneLineSb.append("::");
		for(String cell : cellList) {
			oneLineSb.append(cell);
			oneLineSb.append("|");
		}
		
		// 去除最后一个分隔符
		String oneLine = oneLineSb.toString();
		if(oneLine.endsWith("|")) {
			oneLine = oneLine.substring(0, oneLine.lastIndexOf("|"));
		}   
		
		System.out.println(oneLine);		
	}
	
	
	public static void readExcel(String file) throws Exception {
		int totalRows = 0;
		if(file.endsWith(EXCEL07_EXTENSION)) {
			ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader();
			totalRows = excelXlsxReader.process(file);
		}else {
			throw new Exception("文件格式错误，文件的扩展名只能是xlsx。");
		}
		System.out.println("发送的总行数：" + totalRows);
	} 
	
	public static void main(String[] args) {
		String path = "c://导入模板.xlsx";
		try {
			ExcelReaderUtil.readExcel(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
