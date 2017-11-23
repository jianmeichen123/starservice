package com.galaxy.im.bean.soptask;

import java.util.List;
import java.util.Map;

import com.galaxy.im.common.db.PagableEntity;

public class SopTaskRecord extends PagableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5722220380709825823L;
	private Long taskId;					//关联的待办任务ID
	private Long beforeUid;					//指派 /放弃/移交人ID
	private Long beforeDepartmentId;		//指派 /放弃/移交人部门ID
	private Long afterUid;					//任务接收人Id
	private Long afterDepartmentId;			//任务接收部门ID级
	private String reason;					//原因
	private Integer recordType;				//记录状态：1表示移交、2表示指派、3表示放弃
	private Integer isDel;					//0:未删除  1:已删除
	private Long createdUid;				//创建人id
	private Long updatedUid;				//更新人id
	private int flag;     //1代表移交 2 代表指派
	private String fileWorktype; //fileWorktype=2人事 fileWorktype=3法务 fileWorktype=4财务
	private String assignUname;			//移交/指派给此人
	
	
	private List<Map<String, Object>> taskIds;  				//待办任务id 和项目id
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getRecordType() {
		return recordType;
	}
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Long getCreatedUid() {
		return createdUid;
	}
	public void setCreatedUid(Long createdUid) {
		this.createdUid = createdUid;
	}
	public Long getUpdatedUid() {
		return updatedUid;
	}
	public void setUpdatedUid(Long updatedUid) {
		this.updatedUid = updatedUid;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getFileWorktype() {
		return fileWorktype;
	}
	public void setFileWorktype(String fileWorktype) {
		this.fileWorktype = fileWorktype;
	}
	public List<Map<String, Object>> getTaskIds() {
		return taskIds;
	}
	public void setTaskIds(List<Map<String, Object>> taskIds) {
		this.taskIds = taskIds;
	}
	public String getAssignUname() {
		return assignUname;
	}
	public void setAssignUname(String assignUname) {
		this.assignUname = assignUname;
	}
	
	}
	

	

