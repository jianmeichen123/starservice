package com.galaxy.im.bean.soptask;

import java.util.Date;
import java.util.List;

import com.galaxy.im.common.db.PagableEntity;


public class SopTask extends PagableEntity{

	private static final long serialVersionUID = -1972208554227527130L;
	
	private Long projectId;			//项目id
	private String taskName;		//任务名称
	private String taskType;		//任务类型
	private Integer taskFlag;		//任务标识
	private Integer taskOrder;		//任务优先级
	private Date taskDeadline;		//完成日期
	private Long departmentId;		//所属部门id
	private Long assignUid;			//认领人id
	private String assignUname;		//认领人名称
	private String taskStatus;		//任务状态
	private String remark;			//备注
	private String userName;     	//用户名
	private String projectName;     //项目名称
	private List<Long> projectCreatedId;  //项目创建人
	private List<String> projectCreatedName;//项目创建人名称
	private int flag;     			//1代表移交 2 代表指派
	
     
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Integer getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Integer taskFlag) {
		this.taskFlag = taskFlag;
	}
	public Integer getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(Integer taskOrder) {
		this.taskOrder = taskOrder;
	}
	public Date getTaskDeadline() {
		return taskDeadline;
	}
	public void setTaskDeadline(Date taskDeadline) {
		this.taskDeadline = taskDeadline;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getAssignUid() {
		return assignUid;
	}
	public void setAssignUid(Long assignUid) {
		this.assignUid = assignUid;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public String getAssignUname() {
		return assignUname;
	}
	public void setAssignUname(String assignUname) {
		this.assignUname = assignUname;
	}
	public List<Long> getProjectCreatedId() {
		return projectCreatedId;
	}
	public void setProjectCreatedId(List<Long> projectCreatedId) {
		this.projectCreatedId = projectCreatedId;
	}
	public List<String> getProjectCreatedName() {
		return projectCreatedName;
	}
	public void setProjectCreatedName(List<String> projectCreatedName) {
		this.projectCreatedName = projectCreatedName;
	}
    
}
