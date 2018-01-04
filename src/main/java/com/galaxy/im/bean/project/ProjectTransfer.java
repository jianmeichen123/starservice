package com.galaxy.im.bean.project;


import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.galaxy.im.common.DateUtil;
import com.galaxyinternet.framework.core.model.PagableEntity;

public class ProjectTransfer extends PagableEntity {
	private static final long serialVersionUID = 1L;

	private Long projectId; //项目id
	private Long beforeUid; //项目移交人
	private Long beforeDepartmentId;//移交部门Id
	private Long afterUid;  //项目接收人
	private Long afterDepartmentId;  //项目接受部门 
	private String transferReason;//项目移交原因
	private Integer recordStatus; //移交状态
    private String undoReason; //
    private String refuseReason;//决绝原因
   	private String createDate;//创建时间
   	private String updateDate;//修改时间
   	
   	private String afrerUName;					//项目接收人名称
   	private int flag;     						//1代表移交 2 代表指派
   	private List<Map<String, Object>> projects;	//项目名称，项目id
   	
   	
    public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getBeforeUid() {
		return beforeUid;
	}

	public void setBeforeUid(Long beforeUid) {
		this.beforeUid = beforeUid;
	}

	public Long getBeforeDepartmentId() {
		return beforeDepartmentId;
	}

	public void setBeforeDepartmentId(Long beforeDepartmentId) {
		this.beforeDepartmentId = beforeDepartmentId;
	}

	public Long getAfterUid() {
		return afterUid;
	}

	public void setAfterUid(Long afterUid) {
		this.afterUid = afterUid;
	}

	public Long getAfterDepartmentId() {
		return afterDepartmentId;
	}

	public void setAfterDepartmentId(Long afterDepartmentId) {
		this.afterDepartmentId = afterDepartmentId;
	}

	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getUndoReason() {
		return undoReason;
	}

	public void setUndoReason(String undoReason) {
		this.undoReason = undoReason;
	}



	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDate() {
		return createDate;
	}
    
    @Override
    public void setCreatedTime(Long createdTime) {
    	this.createdTime = createdTime;
    	if(createdTime != null){
    		this.createDate = DateUtil.longToString(createdTime);
    	}
    }
    
    public String getUpdateDate() {
		return updateDate;
	}
    public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdatedTime(Long updatedTime) {
    	this.updatedTime = updatedTime;
    	if(updatedTime != null){
    		this.updateDate = DateUtil.longToString(updatedTime);
    	}
    }

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<Map<String, Object>> getProjects() {
		return projects;
	}

	public void setProjects(List<Map<String, Object>> projects) {
		this.projects = projects;
	}

	public String getAfrerUName() {
		return afrerUName;
	}

	public void setAfrerUName(String afrerUName) {
		this.afrerUName = afrerUName;
	}
   	
  
}