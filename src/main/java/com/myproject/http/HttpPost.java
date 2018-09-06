package com.myproject.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class HttpPost {
	
	
	
	public void sendHttpReq() {
		try {
			String urlStr = "http://127.0.0.1:8080/common/getData";
			URL urlHttp = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) urlHttp.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-javascript;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			Map map = new HashMap();
			map.put("condition", true);
			map.put("start", 1);
			map.put("limit", 10);
			String json = JSON.toJSONString(map);
			String parm = json;
			dataout.writeBytes(parm);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = bf.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
			bf.close();
			
			connection.disconnect();
		/*	Map mapResult = JSON.parseObject(sb.toString());
			ApiResult apiResult = new ApiResult(true, mapResult);*/
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
