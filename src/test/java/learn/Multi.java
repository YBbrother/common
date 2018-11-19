package learn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.xiaoleilu.hutool.util.StrUtil;

public class Multi {
	public static void main(String[] args) throws Exception {
		
		/*String prop = System.getProperty("java.io.tmpdir");
		System.out.println(prop);*/
		System.out.println("".length());
		/*String s1 = "已经习惯了回车和换行一次搞定\r，敲一个回车键，即是回";
		System.out.print(s1);*/
		
		// 对经过base64编码处理的姓名解码
		/*String regex="[\u4e00-\u9fa5]";
		String userName1 = "6Lev6a的Oe";
		if(userName1 != null && !"".equals(userName1)) {
			Matcher m1 = Pattern.compile(regex).matcher(userName1);
			if (!m1.find()) { 
				//则证明有汉字 
				String result = new String(new BASE64Decoder().decodeBuffer(userName1),"UTF-8");
				System.out.println(result);
			}
		}*/
/*		System.out.println(new BigDecimal(-1));
		BigDecimal bd1 = new BigDecimal("1.01");
		BigDecimal bd2 = new BigDecimal(2.02);
		System.out.println(1.01 + 2.02);
		System.out.println(bd1.add(bd2).doubleValue());*/
		
		/*String ss = "2018-09-19 10:07:07";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = sdf.parse(ss);
		String format = sdf1.format(date);
		System.out.println(format);*/
		
		// key = StrUtil.format("limit_Id_Number_{}_{}", type,message.getReqMsg().getIdNum());
		
/*		String ss = StrUtil.format("limit_Id_Number_{}_{}_{}", "aaa", "bbb", "ccc");
		System.out.println(ss);
		System.out.println(aa("jij", "gg", "", "iii"));*/
		
		/*String str = "姝ゆ柟娉曞彧鏄畝鍗曞皢鍗犱綅绗� {} 鎸夌収椤哄簭鏇挎崲涓哄弬鏁";
		String s=new String(str.getBytes("GBK"),"UTF-8");
		System.out.println(s);*/
		
/*		List<String> list = new ArrayList<String>();
		list.add("qqq");
		list.add("aaa");
		list.add("zzz");
		String[] strArr = new String[list.size()];
		strArr = list.toArray(strArr);
		System.out.println(strArr);
		for (String str : strArr) {
			System.out.println(str);
		}*/
		
/*		Map<String, Integer> map = new LinkedHashMap<>();
		map.put("aaa", 111);
		map.put("bbb", 222);
		map.put("ccc", 333);
		for (Map.Entry<String, Integer> m : map.entrySet()) {
			System.out.println(m.getKey() + " : " + m.getValue());
		}
		
		map.forEach(null);
		
		for (Map.Entry<String, Integer> p : map.entrySet()) {
			System.out.println(p.hashCode());
		}*/
		/*String redisModel = "msg{}{}";
		
		
		String[] rmArr = redisModel.split("\\{}");
		for(int i = 0; i < rmArr.length; i++) {
			System.out.println(rmArr[i]);
		}*/
		
		//System.out.println(StrUtil.format(redisModel, "qq"));
		
		/*String redisModel = "msg_{}_{}_{}_{}";
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("");
		list.add("cc");
		list.add("bb");
		list.add("cc");
		String jij = getKey(redisModel, list);
		System.out.println(jij);*/
		
		/*String redisModel = "msg_key_{}_{}_{}_{}";
		redisModel = redisModel.substring(0, redisModel.lastIndexOf("_{}"));
		System.out.println(redisModel);		*/
	/*	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gcDateStart = new GregorianCalendar();
		gcDateStart.setTime(new Date());
		System.out.println(sdf.format(gcDateStart.getTime()));
		Date ff = sdf.parse("2018-09-26 11:00:00");
		System.out.println(ff);*/
	}
	
	// "", null, aa, aa{}{}{}, aa_{}_{}, aa{}, aa_{}_{}_{}_{}_{}_{} 2个参数,  aa_{}_jj
/*	public static String getKey(String redisModel, List<String> list) {
		String key = "";
		if (redisModel == null || "".equals(redisModel) || !redisModel.contains("{}")) {
			key = redisModel;
		} else {
			if (list != null && list.size() > 0) {
				String[] rmArr = redisModel.split("\\{}");
				
				for(int i = 0; i < rmArr.length; i++) {
					if (i < list.size()) {
						key += rmArr[i] + list.get(i);
					} else {
						key += rmArr[i] + "{}";
					}
				}
			}
		}
		return key;
	}*/
	
	
/*	public static StringBuffer aa(String... ff) {
		StringBuffer sb = new StringBuffer();
		for (String ab : ff) {
			sb.append(ab);
		}
		return sb;
	}*/
	
}