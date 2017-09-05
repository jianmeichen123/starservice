package com.galaxy.im.bean.project;

import java.util.Set;

import com.galaxyinternet.framework.core.model.PagableEntity;


public class InformationResult extends PagableEntity{

	private static final long serialVersionUID = 1L;

	private String titleId;

    private String contentChoose;

    private String contentDescribe1;
    
    private String contentDescribe2;

    private Integer sort;
    
    private String projectId;

    private String isValid;

    private String createTime;

    private String createId;

    private String updateTime;

    private String updateId;
    
    private Set<String> titleIds;

    
    private Long valueId;
    private String valueName;
    
    
    public Long getValueId() {
		return valueId;
	}

	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public Set<String> getTitleIds()
	{
		return titleIds;
	}

	public void setTitleIds(Set<String> titleIds)
	{
		this.titleIds = titleIds;
	}

	public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId == null ? null : titleId.trim();
    }

    public String getContentChoose() {
        return contentChoose;
    }

    public void setContentChoose(String contentChoose) {
        this.contentChoose = contentChoose == null ? null : contentChoose.trim();
    }

    public String getContentDescribe1() {
        return contentDescribe1;
    }

    public void setContentDescribe1(String contentDescribe1) {
        this.contentDescribe1 = contentDescribe1 == null ? null : contentDescribe1.trim();
    }
    
    public String getContentDescribe2() {
        return contentDescribe2;
    }

    public void setContentDescribe2(String contentDescribe2) {
        this.contentDescribe2 = contentDescribe2 == null ? null : contentDescribe2.trim();
    }

    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }
}