package com.galaxy.im.bean.project;

import com.galaxy.im.common.db.BaseEntity;

public class ProjectDetailsBean extends BaseEntity{
	private static final long serialVersionUID = 5822576527939850292L;
	private Long id;
	private String projectCode;
	private String projectName;
	private String projectTypeName;
	private String projectTypeCode;
	private Long createdTimeUnix;
	private String currentTime;
	private String financeStatusCode;		//融资状态Code
	private String financeStatusName;		//融资状态名称
	private String createUid;
	private String userName;
	private Integer faFlag;
	private String faName;
	
	private double projectContribution;		//计划-融资金额
	private double projectValuations;		//计划-项目估值
	private double projectShareRatio;		//计划-出让股份
	
	private double finalContribution;		//实际-投资金额
	private double finalValuations;			//实际-项目估值
	private double finalShare_ratio;		//实际-股权占比
	
	private double serviceSharge;			//加速服务费占比
	private double contributionForm;		//投资形式
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	public String getProjectTypeCode() {
		return projectTypeCode;
	}
	public void setProjectTypeCode(String projectTypeCode) {
		this.projectTypeCode = projectTypeCode;
	}
	public Long getCreatedTimeUnix() {
		return createdTimeUnix;
	}
	public void setCreatedTimeUnix(Long createdTimeUnix) {
		this.createdTimeUnix = createdTimeUnix;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getFinanceStatusCode() {
		return financeStatusCode;
	}
	public void setFinanceStatusCode(String financeStatusCode) {
		this.financeStatusCode = financeStatusCode;
	}
	public String getFinanceStatusName() {
		return financeStatusName;
	}
	public void setFinanceStatusName(String financeStatusName) {
		this.financeStatusName = financeStatusName;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getFaFlag() {
		return faFlag;
	}
	public void setFaFlag(Integer faFlag) {
		this.faFlag = faFlag;
	}
	public String getFaName() {
		return faName;
	}
	public void setFaName(String faName) {
		this.faName = faName;
	}
	public double getProjectContribution() {
		return projectContribution;
	}
	public void setProjectContribution(double projectContribution) {
		this.projectContribution = projectContribution;
	}
	public double getProjectValuations() {
		return projectValuations;
	}
	public void setProjectValuations(double projectValuations) {
		this.projectValuations = projectValuations;
	}
	public double getProjectShareRatio() {
		return projectShareRatio;
	}
	public void setProjectShareRatio(double projectShareRatio) {
		this.projectShareRatio = projectShareRatio;
	}
	public double getFinalContribution() {
		return finalContribution;
	}
	public void setFinalContribution(double finalContribution) {
		this.finalContribution = finalContribution;
	}
	public double getFinalValuations() {
		return finalValuations;
	}
	public void setFinalValuations(double finalValuations) {
		this.finalValuations = finalValuations;
	}
	public double getFinalShare_ratio() {
		return finalShare_ratio;
	}
	public void setFinalShare_ratio(double finalShare_ratio) {
		this.finalShare_ratio = finalShare_ratio;
	}
	public double getServiceSharge() {
		return serviceSharge;
	}
	public void setServiceSharge(double serviceSharge) {
		this.serviceSharge = serviceSharge;
	}
	public double getContributionForm() {
		return contributionForm;
	}
	public void setContributionForm(double contributionForm) {
		this.contributionForm = contributionForm;
	}
}
