package com.galaxy.im.common;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class TJSON {
	private static TJSON tJson = null;
	
	public static synchronized TJSON get(){
		if(tJson==null){
			tJson = new TJSON();
		}
		return tJson;
	}
	
	public JSONObject parseJSONObject(Object jsonString){
		return JSON.parseObject(CUtils.get().object2String(jsonString));
	}
	
	public JSONArray parseJSONArray(Object jsonString){
		return JSON.parseArray(CUtils.get().object2String(jsonString));
	}
	
	public <T> T toBean(Object jsonString,Class<T> tClasses){
		return JSON.parseObject(CUtils.get().object2String(jsonString), tClasses);
	}
	
	public <K,V> Map<K,V> toMap(Object jsonString){
		return JSON.parseObject(CUtils.get().object2String(jsonString),new TypeReference<Map<K,V>>(){});
	}

}
