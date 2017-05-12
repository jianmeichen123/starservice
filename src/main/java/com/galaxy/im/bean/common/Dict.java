package com.galaxy.im.bean.common;

import com.galaxy.im.common.db.BaseEntity;

public class Dict extends BaseEntity{
	private static final long serialVersionUID = -8178831584280296013L;
	private Long id;
	private String parentCode;
	private String name;
	private Integer dictValue;
	private String dictCode;
	private Integer dictSort;
	private String text;
	private Integer isDelete;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDictValue() {
		return dictValue;
	}
	public void setDictValue(Integer dictValue) {
		this.dictValue = dictValue;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public Integer getDictSort() {
		return dictSort;
	}
	public void setDictSort(Integer dictSort) {
		this.dictSort = dictSort;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}
