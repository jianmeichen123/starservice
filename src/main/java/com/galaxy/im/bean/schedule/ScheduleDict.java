package com.galaxy.im.bean.schedule;

import com.galaxy.im.common.db.PagableEntity;

public class ScheduleDict extends PagableEntity{

	private static final long serialVersionUID = 1L;

	private String code;

    private Byte type;  // 1:全天 2:非全天

    private String name; // 文字显示

    private String value;
    
    private Integer indexNum;  //排序字段

    private Long createdId;

    private Long updatedId;

    
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

    public Long getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(Long updatedId) {
        this.updatedId = updatedId;
    }

	public Integer getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}
    
    
    

}