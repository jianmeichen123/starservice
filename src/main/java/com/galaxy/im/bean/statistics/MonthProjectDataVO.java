package com.galaxy.im.bean.statistics;

import java.io.Serializable;
/**
 * 本月项目数据环比变化统计视图Bean
 * @author LZJ
 * @ClassName  : MonthProjectDataVO  
 * @Version  版本   
 * @ModifiedBy修改人  
 * @Copyright  Galaxyinternet  
 * @date  2016年7月5日 上午10:53:28
 */
public class MonthProjectDataVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4773958600352172529L;

	private Long newProjectSum; //本月新建项目总计
	private Double npRg; //本月新建项目--环比增长率
	private Double npCf; //本月新建项目--减少增长率
	private Boolean npUnchanged;  //本月新建项目--是否持平
	private String npResult; //本月新建项目统计结果
	private Boolean npSpecialValue; //本月新建项目统计结果-特殊-APP不显示
	
	private Long insideNewProjectSum; //本月新建内部项目总计
	private Double iNpRg; //本月新建内部项目--环比增长率
	private Double iNpCf; //本月新建内部项目--减少增长率
	private Boolean iNpUnchanged;  //本月新建内部项目--是否持平
	private String iNpResult; //本月新建内部项目统计结果
	private Boolean inpSpecialValue;//本月新建内部项目统计结果-特殊-APP不显示
	
	private Long externalNewProjectSum; //本月新建外部项目总计
	private Double eNpRg; //本月新建外部项目--环比增长率
	private Double eNpCf; //本月新建外部项目--减少增长率
	private Boolean eNpUnchanged;  //本月新建外部项目--是否持平
	private String eNpResult; //本月新建外部项目统计结果
	private Boolean enpSpecialValue;//本月新建外部项目统计结果-特殊-APP不显示
	
	public Long getNewProjectSum() {
		return newProjectSum;
	}
	public void setNewProjectSum(Long newProjectSum) {
		this.newProjectSum = newProjectSum;
	}
	public Long getInsideNewProjectSum() {
		return insideNewProjectSum;
	}
	public void setInsideNewProjectSum(Long insideNewProjectSum) {
		this.insideNewProjectSum = insideNewProjectSum;
	}
	public Long getExternalNewProjectSum() {
		return externalNewProjectSum;
	}
	public void setExternalNewProjectSum(Long externalNewProjectSum) {
		this.externalNewProjectSum = externalNewProjectSum;
	}
	public Boolean getNpUnchanged() {
		return npUnchanged;
	}
	public void setNpUnchanged(Boolean npUnchanged) {
		this.npUnchanged = npUnchanged;
	}
	public Boolean getiNpUnchanged() {
		return iNpUnchanged;
	}
	public void setiNpUnchanged(Boolean iNpUnchanged) {
		this.iNpUnchanged = iNpUnchanged;
	}
	public Boolean geteNpUnchanged() {
		return eNpUnchanged;
	}
	public void seteNpUnchanged(Boolean eNpUnchanged) {
		this.eNpUnchanged = eNpUnchanged;
	}
	public String getNpResult() {
		return npResult;
	}
	public void setNpResult(String npResult) {
		this.npResult = npResult;
	}
	public String getiNpResult() {
		return iNpResult;
	}
	public void setiNpResult(String iNpResult) {
		this.iNpResult = iNpResult;
	}
	public String geteNpResult() {
		return eNpResult;
	}
	public void seteNpResult(String eNpResult) {
		this.eNpResult = eNpResult;
	}
	public Double getNpRg() {
		return npRg;
	}
	public void setNpRg(Double npRg) {
		this.npRg = npRg;
	}
	public Double getNpCf() {
		return npCf;
	}
	public void setNpCf(Double npCf) {
		this.npCf = npCf;
	}
	public Double getiNpRg() {
		return iNpRg;
	}
	public void setiNpRg(Double iNpRg) {
		this.iNpRg = iNpRg;
	}
	public Double getiNpCf() {
		return iNpCf;
	}
	public void setiNpCf(Double iNpCf) {
		this.iNpCf = iNpCf;
	}
	public Double geteNpRg() {
		return eNpRg;
	}
	public void seteNpRg(Double eNpRg) {
		this.eNpRg = eNpRg;
	}
	public Double geteNpCf() {
		return eNpCf;
	}
	public void seteNpCf(Double eNpCf) {
		this.eNpCf = eNpCf;
	}
	public Boolean getNpSpecialValue() {
		return npSpecialValue;
	}
	public void setNpSpecialValue(Boolean npSpecialValue) {
		this.npSpecialValue = npSpecialValue;
	}
	public Boolean getInpSpecialValue() {
		return inpSpecialValue;
	}
	public void setInpSpecialValue(Boolean inpSpecialValue) {
		this.inpSpecialValue = inpSpecialValue;
	}
	public Boolean getEnpSpecialValue() {
		return enpSpecialValue;
	}
	public void setEnpSpecialValue(Boolean enpSpecialValue) {
		this.enpSpecialValue = enpSpecialValue;
	}
	

}
