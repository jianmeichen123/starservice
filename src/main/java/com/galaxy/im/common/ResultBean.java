package com.galaxy.im.common;

import java.util.List;
import java.util.Map;

public class ResultBean<T> {
	private String status;
	private int errorCode;
	private String message;
	private T entity;
	private List<T> entityList;
	
	public ResultBean(){
		this.status = "ERROR";
		this.errorCode = -1;
		this.message = "";
	}
	
	private Map<String,Object> map;
	private List<Map<String,Object>> mapList;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public List<T> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<T> entityList) {
		this.entityList = entityList;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public List<Map<String, Object>> getMapList() {
		return mapList;
	}
	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}
}
