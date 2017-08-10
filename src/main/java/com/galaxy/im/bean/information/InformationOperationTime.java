package com.galaxy.im.bean.information;

import java.util.Date;

import com.galaxy.im.common.db.BaseEntity;

public class InformationOperationTime extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Long projectId;
	
	private int reportType;
	
    private Date informationTime;

    private Date projectTime;

    private Date teamTime;

    private Date operationDataTime;

    private Date competeTime;

    private Date stratagemTime;

    private Date financeTime;

    private Date lawTime;

    private Date financingTime;

    private Date exitEvaluationTime;

    private Date investmentProgramTime;

    private Date otherBusinessTime;

    private Date time4;

    private Date time5;

    private Date time6;

    private Date time7;

    private Date time8;

    private Date time9;

    private Date time10;

    private String reflect;
    
    private Long updateDate;

    

	

	public Long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

	public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getInformationTime() {
        return informationTime;
    }

    public void setInformationTime(Date informationTime) {
        this.informationTime = informationTime;
    }

    public Date getProjectTime() {
        return projectTime;
    }

    public void setProjectTime(Date projectTime) {
        this.projectTime = projectTime;
    }

    public Date getTeamTime() {
        return teamTime;
    }

    public void setTeamTime(Date teamTime) {
        this.teamTime = teamTime;
    }

    public Date getOperationDataTime() {
        return operationDataTime;
    }

    public void setOperationDataTime(Date operationDataTime) {
        this.operationDataTime = operationDataTime;
    }

    public Date getCompeteTime() {
        return competeTime;
    }

    public void setCompeteTime(Date competeTime) {
        this.competeTime = competeTime;
    }

    public Date getStratagemTime() {
        return stratagemTime;
    }

    public void setStratagemTime(Date stratagemTime) {
        this.stratagemTime = stratagemTime;
    }

    public Date getFinanceTime() {
        return financeTime;
    }

    public void setFinanceTime(Date financeTime) {
        this.financeTime = financeTime;
    }

    public Date getLawTime() {
        return lawTime;
    }

    public void setLawTime(Date lawTime) {
        this.lawTime = lawTime;
    }

    public Date getFinancingTime() {
        return financingTime;
    }

    public void setFinancingTime(Date financingTime) {
        this.financingTime = financingTime;
    }

    public Date getTime4() {
        return time4;
    }

    public void setTime4(Date time4) {
        this.time4 = time4;
    }

    public Date getTime5() {
        return time5;
    }

    public void setTime5(Date time5) {
        this.time5 = time5;
    }

    public Date getTime6() {
        return time6;
    }

    public void setTime6(Date time6) {
        this.time6 = time6;
    }

    public Date getTime7() {
        return time7;
    }

    public void setTime7(Date time7) {
        this.time7 = time7;
    }

    public Date getTime8() {
        return time8;
    }

    public void setTime8(Date time8) {
        this.time8 = time8;
    }

    public Date getTime9() {
        return time9;
    }

    public void setTime9(Date time9) {
        this.time9 = time9;
    }

    public Date getTime10() {
        return time10;
    }

    public void setTime10(Date time10) {
        this.time10 = time10;
    }

	public String getReflect() {
		return reflect;
	}

	public void setReflect(String reflect) {
		this.reflect = reflect;
	}

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public Date getExitEvaluationTime() {
		return exitEvaluationTime;
	}

	public void setExitEvaluationTime(Date exitEvaluationTime) {
		this.exitEvaluationTime = exitEvaluationTime;
	}

	public Date getInvestmentProgramTime() {
		return investmentProgramTime;
	}

	public void setInvestmentProgramTime(Date investmentProgramTime) {
		this.investmentProgramTime = investmentProgramTime;
	}

	public Date getOtherBusinessTime() {
		return otherBusinessTime;
	}

	public void setOtherBusinessTime(Date otherBusinessTime) {
		this.otherBusinessTime = otherBusinessTime;
	}
    
    
}