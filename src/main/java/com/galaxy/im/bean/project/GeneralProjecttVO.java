package com.galaxy.im.bean.project;

import com.galaxy.im.common.db.Page;
import com.galaxyinternet.framework.core.model.BaseEntity;

public class GeneralProjecttVO extends BaseEntity{
	   
private static final long serialVersionUID = 5111689293108636485L;

	private Long myCount;	 
	private Long coopCount;
	private Page<SopProjectBean> pvPage;
	
	public Long getMyCount() {
		return myCount;
	}
	public void setMyCount(Long myCount) {
		this.myCount = myCount;
	}
	public Long getCoopCount() {
		return coopCount;
	}
	public void setCoopCount(Long coopCount) {
		this.coopCount = coopCount;
	}
	public Page<SopProjectBean> getPvPage() {
		return pvPage;
	}
	public void setPvPage(Page<SopProjectBean> pvPage) {
		this.pvPage = pvPage;
	}
}
