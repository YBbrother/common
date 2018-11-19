package common;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SunShine {
	public static void main(String[] args) {
		/*int i = 876543;
		Integer ss = 876543;
		System.out.println(i == ss);
		Integer var = 888;
		System.out.println(var.equals(888));*/
		//arr();
		
		String productNames = "fwi,jfewo,jiw,";
		String s = productNames.substring(0, productNames.length() - 1);
		System.out.println(productNames);
		System.out.println(s);
	}
	
	public static void arr() {
		int[] a = new int[3];
		a[1] = 3;
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
			
		}
		
		
	}
	
	public void base64() {
		
		/*   		String regex=".*[a-zA-Z]+.*";
		String s = "a你好38081me";
		//char[] ch = s.toCharArray();
		String[] ss = s.split("");
		int count = 0;
		for(int i = 0; i < ss.length; i++) {
			if(ss[i].length() == 0) {
				continue;
			}
			 Matcher m=Pattern.compile(regex).matcher(ss[i]);  
		     System.out.println(m.matches());  
			if(m.matches()) {
				count++;
			}
			if(ss[i].matches("^[0-9]*$")) {
				System.out.println(ss[i]);
			}
		}
		System.out.println(count);*/
		
		String str = "测试一下";
		String ret = null;
		ret = new BASE64Encoder().encode(str.getBytes());
		System.out.println("加密前:"+str+" 加密后:"+ret);
		str = "5L2g5aW9";
		try {
			String result = new String(new BASE64Decoder().decodeBuffer(str),"UTF-8");
			System.out.println(result+"///////////");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("解密前:"+str+" 解密后:"+ret);
	}
    	
}