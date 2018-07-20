package common;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;

import net.sf.json.JSONObject;

public class JsonTest {
	
	public static void main(String[] args) {
		String res = "{\"data\":{\"idNum\":\"340621199103063623\",\"merAcct\":\"gaojxtest\",\"parMerCode\":\"gaojx0926\",\"productType\":\"PI2\",\"transCode\":\"lA06520180717102531337\",\"userName\":\"雨时\"},\"respCode\":\"PI00\",\"respMsg\":\"认证一致，成功\"}";
		 JSONObject jodata = JSONObject.fromObject(res);
         String respCode = jodata.get("respCode").toString();
         String respMsg = jodata.get("respMsg").toString();
         
         JSONObject joa = JSONObject.fromObject(jodata.get("data").toString());
         joa.put("respCode", respCode);
         joa.put("respMsg", respMsg);
   
         Map<String, Object> map1 = com.alibaba.fastjson.JSONObject.parseObject(joa.toString());
         Map<String, String> jsonProperty = new LinkedHashMap<>();
         
         jsonProperty.put("用户名", "userName");
         jsonProperty.put("身份证号", "idNum");
         jsonProperty.put("商户账号", "merAcct");
         jsonProperty.put("一级商户账号", "parMerCode");
         jsonProperty.put("产品类型", "productType");
         jsonProperty.put("流水号", "transCode");
         jsonProperty.put("返回码", "respCode");
         jsonProperty.put("返回信息", "respMsg");
         
         Map<String, Object> map = new LinkedHashMap<>();      
         for (Entry<String, String> entry : jsonProperty.entrySet()) {
             for(Entry<String, Object> entry1 : map1.entrySet()) {
            	 	if(entry.getValue().equals(entry1.getKey())) {
            	 		map.put(entry1.getKey(), entry1.getValue());
            	 	}
             } 	        
         }
         
         for(Entry<String, Object> entry : map.entrySet()) {
        	 System.out.println(entry.getKey() + "=" + entry.getValue());
         }
	}

}
