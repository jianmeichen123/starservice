package com.galaxy.im.common;

import java.util.Date;
import java.util.HashMap;

public class TMap<K,V> extends HashMap<K, V>{
	private static final long serialVersionUID = -8326062313207818462L;
	
	public Integer getInteger(Object key,Integer... integers){
		Integer defaultValue = arrayIsNotEmpty(integers)?integers[0]:0;
		return CUtils.get().object2Integer(super.get(key),defaultValue);
	}
	
	public String getString(Object key,String... strings){
		String defaultValue = arrayIsNotEmpty(strings)?strings[0]:null;
		return CUtils.get().object2String(super.get(key),defaultValue);
	}
	
	public Double getDouble(Object key,Double... doubles){
		Double defaultValue = arrayIsNotEmpty(doubles)?doubles[0]:0;
		return CUtils.get().object2Double(super.get(key),defaultValue);
	}
	
	public Float getFloat(Object key,Float... floats){
		Float defaultValue = arrayIsNotEmpty(floats)?floats[0]:0F;
		return CUtils.get().object2Float(super.get(key),defaultValue);
	}
	
	public Long getLong(Object key,Long... longs){
		Long defaultValue = arrayIsNotEmpty(longs)?longs[0]:0L;
		return CUtils.get().object2Long(super.get(key),defaultValue);
	}
	
	public Boolean getBoolean(Object key,Boolean... booleans){
		Boolean defaultValue = arrayIsNotEmpty(booleans)?booleans[0]:false;
		return CUtils.get().object2Boolean(super.get(key),defaultValue);
	}
	
	public Date getDate(Object key,Date... dates){
		Date defaultValue = arrayIsNotEmpty(dates)?dates[0]:null;
		return CUtils.get().object2Date(super.get(key),defaultValue);
	}
	
	public Short getShort(Object key,Short... shorts){
		Short defaultValue = arrayIsNotEmpty(shorts)?shorts[0]:0;
		return CUtils.get().object2Short(super.get(key),defaultValue);
	}
	
	//------------------------ 私有方法 ----------------------------
	private boolean arrayIsNotEmpty(Object[] array){
		return (array!=null && array.length>0);
	}
	
	static class TT{
		private String id;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		
	}
	
}
