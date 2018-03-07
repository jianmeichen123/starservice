package com.galaxy.im.bean.project;

import com.galaxy.im.common.db.Page;
import com.galaxyinternet.framework.core.model.BaseEntity;

public class GeneralProjecttVO extends BaseEntity{
	   
private static final long serialVersionUID = 5111689293108636485L;

	private Page<SopProjectBean> myPage;	//负责项目列表
	private Page<SopProjectBean> coopPage;	//协作项目列表
	
	private Page<SopProjectBean> allPage;	//搜索后的项目列表
	
	public Page<SopProjectBean> getMyPage() {
		return myPage;
	}
	public void setMyPage(Page<SopProjectBean> myPage) {
		this.myPage = myPage;
	}
	public Page<SopProjectBean> getCoopPage() {
		return coopPage;
	}
	public void setCoopPage(Page<SopProjectBean> coopPage) {
		this.coopPage = coopPage;
	}
	public Page<SopProjectBean> getAllPage() {
		return allPage;
	}
	public void setAllPage(Page<SopProjectBean> allPage) {
		this.allPage = allPage;
	}
}
