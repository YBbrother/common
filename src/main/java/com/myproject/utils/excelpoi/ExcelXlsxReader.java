package com.myproject.utils.excelpoi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class ExcelXlsxReader extends DefaultHandler{
	
	/**
	 * 单元格中的数据可能的数据类型
	*/
	enum CellDataType {
	    BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
	}
	
	/**
	 * 共享字符串表
	 */
	private SharedStringsTable sst;
	
	/**
	 * 上一次的索引值
	 */
	private String lastIndex;
	 
	/**
	 * 文件的绝对路径
	 */
	private String filePath = "";
	 
	/**
	  * 工作表索引
	  */
	private int sheetIndex = 0;
	
	/**
	 * sheet名
	 */
	private String sheetName = "";
 
	/**
	 * 总行数
	 */
	private int totalRows=0;
	
	/**
	 * 一行内cell集合
	 */
	private List<String> cellList = new ArrayList<String>();
 
    /**
     * 判断整行是否为空行的标记
     */
	private boolean flag = false;
  
    /**
     * 当前行
     */
	private int curRow = 0;

	/**
	 * 当前列
	 */
	private int curCol = 0;
	
	/**
	 * 单元格的位置，如A1,B1
	 */
	String columnPst = null;
	
	/**
	 * T元素标识
	 */
    private boolean isTElement;
 
    /**
     * 异常信息，如果为空则表示没有异常
     */
    private String exceptionMessage;

    /**
     * 单元格数据类型，默认为字符串类型
     */
    private CellDataType nextDataType = CellDataType.SSTINDEX;

    private final DataFormatter formatter = new DataFormatter();

    /**
     * 单元格日期格式的索引
     */
    private short formatIndex;
 
    /**
     * 日期格式字符串
     */
    private String formatString;
	
    //定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
    private String preRef = null;
    private String ref = null;
 
    /**
     * 定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
     */
    private String maxRef = null;
	
    /**
     * 单元格
     */
    private StylesTable stylesTable;
	
    
    /**
     * 遍历工作簿中所有的电子表格
     * 并缓存在mySheetList中
     * @param file
     * @return
     * @throws Exception
     */
	public int process(String file) throws Exception {
		filePath = file;
		OPCPackage pkg = OPCPackage.open(file);
		XSSFReader xssfReader = new XSSFReader(pkg);
		stylesTable = xssfReader.getStylesTable();
		SharedStringsTable sst = xssfReader.getSharedStringsTable();
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		this.sst = sst;
		parser.setContentHandler(this);
		XSSFReader.SheetIterator sheets = (SheetIterator) xssfReader.getSheetsData();
		//遍历sheet
		while(sheets.hasNext()) {
			//标记初始行为第一行
			curRow = 1;
			sheetIndex++;
			//sheets.next()和sheets.getSheetName()不能换位置，否则sheetName报错
			InputStream sheet = sheets.next();
			sheetName = sheets.getSheetName();
			InputSource sheetSource = new InputSource(sheet);
			//解析excel的每条记录，在这个过程中startElement()、characters()、endElement()这三个函数会依次执行
			parser.parse(sheetSource);
			sheet.close();
		}
		return totalRows;
	}

	/**
	 * 第一个执行
	 */
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		//c => 单元格
		if("c".equals(name)) {
			//前一个单元格的位置
			if(preRef == null) {
				preRef = attributes.getValue("r");
			}else {
				preRef = ref;
			}
			
			//当前单元格的位置
			ref = attributes.getValue("r");
			//设定单元格类型
			this.setNextDataType(attributes);
		}
		
		//当元素为t时
		if("t".equals(name)) {
			isTElement = true;
		}else {
			isTElement = false;
		}
		
		//置空
		lastIndex = "";
	}
	
	/**
	  * 第二个执行
      * 得到单元格对应的索引值或是内容值
      * 如果单元格类型是字符串、INLINESTR、数字、日期，lastIndex则是索引值
      * 如果单元格类型是布尔值、错误、公式，lastIndex则是内容值
      * @param ch
      * @param start
      * @param length
      * @throws SAXException 
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		lastIndex += new String(ch, start, length);
	}
	
	
	/**
	 * 第三个执行
	 *  @param uri
     * @param localName
     * @param name
     * @throws SAXException
	 */
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		//t元素也包含字符串
		if(isTElement) { //这个程序没经过
			//将单元格内容加入rowlist中
			cellList.add(curCol, lastIndex);
			curCol++;
			isTElement = false;
		}else if("v".equals(name)) {
			//v => 单元格的值，如果单元格是字符串，则v标签的值为该字符串在SST中的索引
			//根据索引值获取对应的单元格值
			String value = this.getDataValue(lastIndex, "");
			//补全单元格之间的空单元格
			if(!ref.equals(preRef)) {
				int len = countNullCell(ref, preRef);
				for(int i = 0; i < len; i++) {
					cellList.add(curCol, "");
					curCol++;
				}
			}
			cellList.add(curCol, value);
			curCol++;
		}else {
			//如果标签名称为row，这说明已到行尾，调用optRows()方法
			if("row".equals(name)) {
				//默认第一行为表头，以该行单元格数目为最大数目
				if(curRow == 1) {
					maxRef = ref;
				}
				
				//补全一行尾部可能缺失的单元格
				if(maxRef != null) {
					int len = countNullCell(maxRef, ref);
					for(int i = 0; i <= len; i++) {
						cellList.add(curCol, "");
						curCol++;
					}
					
				}
				
				//该行不为空行且该行不是第一行，则发送（第一行为列名，不需要）
				if(curRow != 1) {
					ExcelReaderUtil.sendRows(filePath, sheetName, sheetIndex, curRow, cellList);
					totalRows++;
				}
				
				cellList.clear();
				curRow++;
				curCol = 0;
				preRef = null;
				ref = null;
			}
			
		}
		
		
		
	}


	/**
	 * 处理数据类型
	 * @param attributes
	 */
	public void setNextDataType(Attributes attributes) {
		//cellType为空，则表示该单元格类型为数字
		nextDataType = CellDataType.NUMBER; 
		formatIndex = -1;
		formatString = null;
		//单元格类型
		String cellType = attributes.getValue("t");
		if(cellType == null) {
			cellList.add(curCol, "");
		}
		String cellStyleStr = attributes.getValue("s");
		//获取单元格的位置，如A1,B1
		columnPst = attributes.getValue("r");
		//System.out.println("*********" + sheetName + "#" + curRow + columnPst + "\t");
		
		if("b".equals(cellType)) {  //处理布尔值
			nextDataType = CellDataType.BOOL;
		} else if ("e".equals(cellType)) {  //处理错误
			nextDataType = CellDataType.ERROR;
		} else if ("inlineStr".equals(cellType)) {
			nextDataType = CellDataType.INLINESTR;
		} else if ("s".equals(cellType)) { //处理字符串
			nextDataType = CellDataType.SSTINDEX;
		} else if ("str".equals(cellType)) {
			nextDataType = CellDataType.FORMULA;
		}
		
		//处理日期
		if(cellStyleStr != null) {
			int styleIndex = Integer.parseInt(cellStyleStr);
			XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
			formatIndex = style.getDataFormat();
			formatString = style.getDataFormatString();
			
			if(formatString.contains("m/d/yy")) {
				nextDataType = CellDataType.DATE;
				formatString = "yyyy-MM-dd hh:mm:ss";
			}
			
			if(formatString == null) {
				nextDataType = CellDataType.NULL;
				formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
				
			}
		}
	}
	
	
	/**
      * 对解析出来的数据进行类型处理
      * @param value   单元格的值，
      *                value代表解析：BOOL的为0或1， ERROR的为内容值，FORMULA的为内容值，INLINESTR的为索引值需转换为内容值，
      *                SSTINDEX的为索引值需转换为内容值， NUMBER为内容值，DATE为内容值
      * @param thisStr 一个空字符串
      * @return
      */
	public String getDataValue(String value, String thisStr) {
		switch(nextDataType) {
			// 这几个的顺序不能随便交换，交换了很可能会导致数据错误
		case BOOL:   // 布尔值
			char first = value.charAt(0);
			thisStr = first == '0' ? "FALSE" : "TRUE";
			break;
		case ERROR:   // 错误
			thisStr = "\"ERROR:" + value.toString() + '"';
			break;
		case FORMULA:  // 公式 
			thisStr = '"' + value.toString() + '"';
			break;
		case INLINESTR:
			XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
			thisStr = rtsi.toString();
			rtsi = null;
			break;
		case SSTINDEX: //字符串
			String sstIndex = value.toString();
			try {
				//根据idx索引值获取内容值
				int idx = Integer.parseInt(sstIndex);
				XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));
				thisStr = rtss.toString();
				rtss = null;
			}catch (Exception e) {
				thisStr = value.toString();
			}
			break;
		case NUMBER: //数字
			if(formatString != null) {
				thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
			}else {
				thisStr = value;
			}
			thisStr = thisStr.replace("_", "");
			break;
		case DATE:  //日期
			thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
			// 对日期字符串作特殊处理，去掉T\
			thisStr = thisStr.replace("T", " ");
			break;
			default:
				thisStr = " ";
				break;
		}
		return thisStr;
	}
	
	public int countNullCell(String ref, String preRef) {
		//excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
		String xfd = ref.replaceAll("\\d", "");
		String xfd_1 = preRef.replaceAll("\\d", "");
		
		xfd = fillChar(xfd, 3, '@', true);
		xfd_1 = fillChar(xfd_1, 3, '@', true);
		
		char[] letter = xfd.toCharArray();
		char[] letter_1 = xfd_1.toCharArray();
		int res = (letter[0] - letter_1[0]) * 26 *26 + (letter[1] - letter_1[1]) * 26 + (letter[2] - letter_1[2]);
		return res - 1;
	}
	
	public String fillChar(String str, int len, char let, boolean isPre) {
		int len_1 = str.length();
		if(len_1 < len) {
			if(isPre) {
				for(int i = 0; i < (len - len_1); i++) {
					str = let + str;
				}
			}else {
				for(int i = 0; i < (len - len_1); i++) {
					str = str + let;
				}
			}
		}
		return str;
	}
	
	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	
	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	private boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public String getMergedRegionValue(Sheet sheet, int row, int column) {	
		int sheetMergeCount = sheet.getNumMergedRegions();
	
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
	
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					String value = this.getDataValue(lastIndex, "");
					return this.getDataValue(lastIndex, "");
				}
			}
		}
			return null;
		}

}
