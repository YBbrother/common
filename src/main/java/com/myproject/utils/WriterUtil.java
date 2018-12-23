package com.myproject.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class WriterUtil {
	
	static final  Class<? extends WriterUtil> CLAZZ = WriterUtil.class;
	
	public static void write(ServletResponse response, String obj) {
		try {
			 response.setContentType("text/html; charset=utf-8");
			 PrintWriter out=response.getWriter();
			 out.println(obj);
			 out.flush();
			 out.close();
		} catch (IOException e) {
			LoggerUtils.fmtError(CLAZZ, e, "输出JSON报错。");
			e.printStackTrace();
		}
	}
	
	public static void writePlain(HttpServletResponse response, String obj) {
		try {
			 response.setContentType("text/plain; charset=utf-8");
			 PrintWriter out=response.getWriter();
			 out.println(obj);
			 out.flush();
			 out.close();
		} catch (IOException e) {
			LoggerUtils.error(CLAZZ, e.getMessage());
			e.printStackTrace();
		}
	}
}
