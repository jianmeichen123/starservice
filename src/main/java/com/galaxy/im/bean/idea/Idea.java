package com.galaxy.im.bean.idea;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.db.PagableEntity;

public class Idea extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="创意名称不能为空")
	private String ideaName;
	@NotEmpty(message="创意编码不能为空")
	private String ideaCode;
	private Long departmentId;
	private Long createdUid;
	private Long updatedUid;
	private String ideaProgress;
	private String ideaDesc;
	private String ideaDescHtml;
	private String ideaSource;
	private Long projectId;
	private Long claimantUid;	
	private String departmentDesc;
	private String createdUname;
	private String ideaProgressDesc;
	private String projectName;
	private String claimantUname;
	private List<Long> createdUids;
	private String createdDate;
	private String createdDateFrom;
	private String createdDateThrough;
	private Long createdTimeFrom;
	private Long createdTimeThrough;
	private String hhrName;
	private List<Long> ids;
	private String projectProgressDesc;
	private String createBySelf;

	//2016/10/13 修改创意创建项目是判断是否已经生成项目了
	private Integer ideaProject;
	
	
	private String isforindex;
	
	private String departmentEditable;
	private String latestLog;
	/**
	 * 查询多个状态,逗号分隔。<br>
	 * e.g. ideaProgress:1,ideaProgress:2
	 */
	private String ideaProgressStr;
	/**
	 * 创建人或认领人-投资经理已认领、待认领查询使用
	 */
	private Long relatedUid;
	
	public String getLatestLog() {
		return latestLog;
	}
	public void setLatestLog(String latestLog) {
		this.latestLog = latestLog;
	}
	private String createDate;
	
	public String getIdeaName() {
		return ideaName;
	}
	public void setIdeaName(String ideaName) {
		this.ideaName = ideaName;
	}
	public String getIdeaCode() {
		return ideaCode;
	}
	public void setIdeaCode(String ideaCode) {
		this.ideaCode = ideaCode;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
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
	public String getIdeaProgress() {
		return ideaProgress;
	}
	public void setIdeaProgress(String ideaProgress) {
		this.ideaProgress = ideaProgress;
	}
	public String getIdeaDesc() {
		return ideaDesc;
	}
	public void setIdeaDesc(String ideaDesc) {
		this.ideaDesc = ideaDesc;
	}
	public String getIdeaSource() {
		return ideaSource;
	}
	public void setIdeaSource(String ideaSource) {
		this.ideaSource = ideaSource;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getClaimantUid() {
		return claimantUid;
	}
	public void setClaimantUid(Long claimantUid) {
		this.claimantUid = claimantUid;
	}
	public String getDepartmentDesc() {
		return departmentDesc;
	}
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
	public String getCreatedUname() {
		return createdUname;
	}
	public void setCreatedUname(String createdUname) {
		this.createdUname = createdUname;
	}
	public String getIdeaProgressDesc() {
		return ideaProgressDesc;
	}
	public void setIdeaProgressDesc(String ideaProgressDesc) {
		this.ideaProgressDesc = ideaProgressDesc;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClaimantUname() {
		return claimantUname;
	}
	public void setClaimantUname(String claimantUname) {
		this.claimantUname = claimantUname;
	}
	public List<Long> getCreatedUids() {
		return createdUids;
	}
	public void setCreatedUids(List<Long> createdUids) {
		this.createdUids = createdUids;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Long getCreatedTimeFrom() {
		return createdTimeFrom;
	}
	public void setCreatedTimeFrom(Long createdTimeFrom) {
		this.createdTimeFrom = createdTimeFrom;
	}
	public Long getCreatedTimeThrough() {
		return createdTimeThrough;
	}
	public void setCreatedTimeThrough(Long createdTimeThrough) {
		this.createdTimeThrough = createdTimeThrough;
	}
	public String getHhrName() {
		return hhrName;
	}
	public void setHhrName(String hhrName) {
		this.hhrName = hhrName;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public String getProjectProgressDesc() {
		return projectProgressDesc;
	}
	public void setProjectProgressDesc(String projectProgressDesc) {
		this.projectProgressDesc = projectProgressDesc;
	}
	public String getIdeaDescHtml() {
		return ideaDescHtml;
	}
	public void setIdeaDescHtml(String ideaDescHtml) {
		this.ideaDescHtml = ideaDescHtml;
	}
	@Override
	public void setCreatedTime(Long createdTime) {
		// TODO Auto-generated method stub
		super.setCreatedTime(createdTime);
		if (createdTime != null) {
			this.createDate = DateUtil.longToString(createdTime);
		}
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreatedDateFrom() {
		return createdDateFrom;
	}
	public void setCreatedDateFrom(String createdDateFrom) {
		this.createdDateFrom = createdDateFrom;
	}
	public String getCreatedDateThrough() {
		return createdDateThrough;
	}
	public void setCreatedDateThrough(String createdDateThrough) {
		this.createdDateThrough = createdDateThrough;
	}
	public String getCreateBySelf() {
		return createBySelf;
	}
	public void setCreateBySelf(String createBySelf) {
		this.createBySelf = createBySelf;
	}
	public String getDepartmentEditable() {
		return departmentEditable;
	}
	public void setDepartmentEditable(String departmentEditable) {
		this.departmentEditable = departmentEditable;
	}
	public String getIsforindex() {
		return isforindex;
	}
	public void setIsforindex(String isforindex) {
		this.isforindex = isforindex;
	}
	public String getIdeaProgressStr() {
		return ideaProgressStr;
	}
	public void setIdeaProgressStr(String ideaProgressStr) {
		this.ideaProgressStr = ideaProgressStr;
	}
	public Long getRelatedUid() {
		return relatedUid;
	}
	public void setRelatedUid(Long relatedUid) {
		this.relatedUid = relatedUid;
	}
	public Integer getIdeaProject() {
		return ideaProject;
	}
}
