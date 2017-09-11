package com.galaxy.im.common.db;

public interface Pagable {
	void setPageSize(Integer pageSize);
	Integer getPageSize();
	void setPageNum(Integer pageNum);
	Integer getPageNum();
	void setDirection(String direction);
	String getDirection();
	void setProperty(String property);
	String getProperty();
}
