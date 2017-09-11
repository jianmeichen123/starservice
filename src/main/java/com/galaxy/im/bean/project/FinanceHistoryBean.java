package com.galaxy.im.bean.project;


import java.util.Date;

import com.galaxy.im.common.db.BaseEntity;

public class FinanceHistoryBean extends BaseEntity{
	private static final long serialVersionUID = 7539838040869585408L;
	
	private Long projectId;
	private Date financeDate;
	private String financeDateStr;
	private String financeFrom;
	private Double financeAmount;
	private String financeUnit;
	private Double financeProportion;
	private String financeStatus;
	private Long createUid;
	private Long createTime;
	private Long updatedUid;
	private Long updatedTime;
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Date getFinanceDate() {
		return financeDate;
	}
	public void setFinanceDate(Date financeDate) {
		this.financeDate = financeDate;
	}
	public String getFinanceDateStr() {
		return financeDateStr;
	}
	public void setFinanceDateStr(String financeDateStr) {
		this.financeDateStr = financeDateStr;
	}
	public String getFinanceFrom() {
		return financeFrom;
	}
	public void setFinanceFrom(String financeFrom) {
		this.financeFrom = financeFrom;
	}
	public Double getFinanceAmount() {
		return financeAmount;
	}
	public void setFinanceAmount(Double financeAmount) {
		this.financeAmount = financeAmount;
	}
	public String getFinanceUnit() {
		return financeUnit;
	}
	public void setFinanceUnit(String financeUnit) {
		this.financeUnit = financeUnit;
	}
	public Double getFinanceProportion() {
		return financeProportion;
	}
	public void setFinanceProportion(Double financeProportion) {
		this.financeProportion = financeProportion;
	}
	public String getFinanceStatus() {
		return financeStatus;
	}
	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}
	public Long getCreateUid() {
		return createUid;
	}
	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdatedUid() {
		return updatedUid;
	}
	public void setUpdatedUid(Long updatedUid) {
		this.updatedUid = updatedUid;
	}
	public Long getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}
}
