package com.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.myproject.utils.MyException;

/**
 * 读取Excel中的数据
 * @author lzq
 */
public class ReadExcelTest {
	Log logger = LogFactory.getLog(getClass());
	
	@Test
	public void getExcel() {
		String path = "E:/WorkSpaceAll/WorkSpace180428/common/excel/测试空.xlsx";
		InputStream in = null;
		try {
			File file = new File(path);
			in = new FileInputStream(file);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
			// 获取每一个工作簿
			for(int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if(xssfSheet == null) {
					continue;
				}
				// 获取当前工作簿
				logger.info("***************************共有" + (xssfSheet.getLastRowNum() + 1) + "行数据***************************");
				for(int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					logger.info("############执行到[" + (rowNum + 1) +"]行！############");
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if(xssfRow != null) {
						XSSFCell one = xssfRow.getCell(0);
						System.out.print(one.getStringCellValue() + "\t");
						XSSFCell two = xssfRow.getCell(1);
						two.setCellType(Cell.CELL_TYPE_STRING);
						System.out.print(two.getStringCellValue() + "\t");
						XSSFCell three = xssfRow.getCell(2);
						three.setCellType(Cell.CELL_TYPE_STRING);
						System.out.print(three.getStringCellValue() + "\t");
						XSSFCell four = xssfRow.getCell(3);
						four.setCellType(Cell.CELL_TYPE_STRING);
						System.out.print(four.getStringCellValue() + "\t");
						System.out.println();
					}else {
						throw new MyException("Excel无内容");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			System.err.println("啦啦啦啦啦德玛西亚");
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO 关闭输入流
				e.printStackTrace();
			}
		}
	}
	
	   /**
     * 保存文件到磁盘中
     * @param request
     * @return 返回保存的文件路径
	 * @throws Exception 
     * @throws I2SmspServiceException
     */
    public String saveFile(HttpServletRequest request) throws Exception  {

        MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
        MultipartFile mutFile = mhsr.getFile("uploadfile");
        if (mutFile == null) {
            throw new MyException("", "添加上传文件");
        }

        String fileName = mutFile.getOriginalFilename();
        if (!checkFileName(fileName)) {
            throw new Exception("文件名异常：" + fileName);
        }

        String dirpath = "usr/tmp/local/excel";
        String filepath = dirpath + fileName;

        InputStream input = null;
        FileOutputStream fos = null;
        try {
            File file = new File(dirpath);
            if (file.exists() == false) {
                if (!file.mkdirs()) {
                    throw new MyException("创建文件夹失败" + file.getAbsolutePath());
                }
            }
            input = mutFile.getInputStream();
            fos = new FileOutputStream(new File(filepath));

            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            logger.error("Error when create message", e);
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                logger.error("Error when create message", e);
            }
        }
        return filepath;
    }
    
    /**
     * 校验文件名
     */
    private static String[] errS = {"..",File.separator,"\n"};
    public static boolean checkFileName(String fileName){
    	if(StringUtils.isEmpty(fileName) || fileName.startsWith(".")){
    		return false;
    	}
    	for(String s : errS){
    		if(fileName.indexOf(s) != -1){
    			return false;
    		}
    	}
    	return true;
    }
	
}
