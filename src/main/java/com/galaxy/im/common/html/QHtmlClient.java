package com.galaxy.im.common.html;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import com.galaxy.im.common.CUtils;

public class QHtmlClient {
	private static QHtmlClient client = null;
	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
	
	
	public static synchronized QHtmlClient get(){
		if(client==null){
			client = new QHtmlClient();
		}
		return client;
	}
	
	public String post(String url,Map<String,Object> headerMap,Map<String,Object> paramMap){
		CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
		httpClient.start();
		String responseContent = "error";
		
		final HttpPost request = new HttpPost(url);
		
		//设置请示头
		request.addHeader("accept", "application/json"); 
		if(headerMap!=null && !headerMap.isEmpty()){
			Set<String> keys = headerMap.keySet();
			for(String key : keys){
				request.setHeader(key, CUtils.get().object2String(headerMap.get(key)));
			}
		}
		
		StringEntity se = null;
		try{
			if(paramMap!=null && !paramMap.isEmpty()){
				String temp = CUtils.get().object2JSONString(paramMap); 
				se = new StringEntity(temp,"utf-8");
				se.setContentType(CONTENT_TYPE_TEXT_JSON);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		request.setEntity(se);
		List<Future<HttpResponse>> respList = new LinkedList<Future<HttpResponse>>();
		respList.add(httpClient.execute(request, null));
		
		try{
			int statusCode = respList.get(0).get().getStatusLine().getStatusCode();
			if(statusCode==200){
				HttpEntity entity = respList.get(0).get().getEntity();
				InputStream inputStream = entity.getContent();
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, "utf8"));
				
				String line = null;
				StringBuffer sb = new StringBuffer();
				while((line = bufferReader.readLine())!=null){
					sb.append(line);
				}
				responseContent = sb.toString();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			httpClient.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return responseContent;
		
	}
	
	public Map<String,Object> getHeaderMap(HttpServletRequest request){
		Map<String,Object> headerMap = new HashMap<String,Object>();
		if(request!=null){
			Enumeration<String> keys = request.getHeaderNames();
			String key = null;
			
			while(keys.hasMoreElements()){
				key = keys.nextElement();
				if(("sessionid,gt,content-type,guserid").indexOf(key)>-1){
					headerMap.put(key, request.getHeader(key));
				}
			}
		}
		
		return headerMap;
		
	}
	
//	public static void main(String[] args){
//		QHtmlClient client = new QHtmlClient();
//		
//		String url = "http://mini/starservice/callon/getCallonList";
//		Map<String,Object> headerMap = new HashMap<String,Object>();
//		headerMap.put("Content-Type", "application/json");
//		headerMap.put("gt", "iOS");
//		headerMap.put("guserId", "104");
//		headerMap.put("sessionId", "V0VCMzk5MDE0OTQ4MTcxMTQzMzQ0MTA3NjIzMDQ5OTg2NzI0");
//		
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("pageNum", 0);
//		paramMap.put("pageSize", 10);
//		paramMap.put("direction", "asc");
//		paramMap.put("property", "id");
//		paramMap.put("selectKey", "忘");
//		
//		
//		client.post(url, headerMap,paramMap);
//		
//		
//		
//	}
	

}
