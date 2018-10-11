package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
		List<String> a = new ArrayList<String>();
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in ));
		String read = null;//字符串
		int count = 0;//替换字符串的个数
		int num = 0;//出现的次数
	    System.out.println("请输入字符串：");
		try {
			read = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("请输入替换字符串的个数：");
		try {
			count = Integer.parseInt(input.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NumberFormatException e) { 
			System.out.println("请输入正确的数字！"); 
			return;
		}
		num = stringCount(read,"{}");
		if(num == 0){
			System.out.println("字符串中没有可替换的{}！");
		}else{
			System.out.println("请输入替换的字符串：");
			for (int j = 0; j < count; j++) {
				System.out.print("请输入第"+(j+1)+"个要替换的字符串：");
				try {
						a.add(input.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			System.out.println("替换的字符串是：");
			for(int i=0;i<a.size();i++){
		        String aa = a.get(i);
		        System.out.print(aa+"  ");
		    }
			System.out.println("");
			StringBuilder sb = replace(read,a,"{}",num,count);
			System.out.println("最终替换结果："+sb);	
		}
		
	}

	public static int stringCount(String str,String key) {
		int index=0;
		int count=0;
		while ((index=str.indexOf(key))!=-1) {
			str=str.substring(index+key.length());
			count++;
		}
			return count;
	}
	
	public static StringBuilder replace(String str,List<String> list,String key,int num,int count){
		StringBuilder sb = new StringBuilder(str);
		int index= 0;
		int i = 0;
		int length = 0;
		int lastindex = str.lastIndexOf(key);
		int len = str.length();
		
		while((index=str.indexOf(key))!=-1 && i<count){
			if(i<=(num-2)){
				sb.replace(index+length, index+length+2, list.get(i));
				length += index+(list.get(i).length())-1;
				i++;
				str=sb.substring(length);
			}else{
				if(lastindex!=(len-1)){
					sb.replace(index+length, index+length+2,list.get(i));
				}else{
					sb.replace(index+length, index+length+1,list.get(i));
					sb = sb.deleteCharAt(sb.length()-1);
				}
				break;
			}
		}
		return sb;
	}
 
}
