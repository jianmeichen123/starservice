package com.galaxy.im.common.db;

import java.util.List;
import java.util.Map;

public class QPage {
	private List<Map<String,Object>> dataList;
	private Integer total;
	
	public QPage(List<Map<String,Object>> dataList,Integer total){
		this.dataList = dataList;
		this.total = total;
	}
	
	public List<Map<String, Object>> getDataList() {
		return dataList;
	}
	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
