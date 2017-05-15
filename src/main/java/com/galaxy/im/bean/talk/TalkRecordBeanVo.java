package com.galaxy.im.bean.talk;

public class TalkRecordBeanVo extends TalkRecordBean{

	private static final long serialVersionUID = 2008579345385628627L;

	//历史记录list查询字段，拜访id
	private long callonId;
	//拜访对象id
	private long conId;
	//关系项目id
	private long proId;
	
	
	//历史记录详情的查询字段：访谈记录id，项目名称
	private long talkRecordId;
	private String projectName;

	public long getConId() {
		return conId;
	}

	public void setConId(long conId) {
		this.conId = conId;
	}

	public long getProId() {
		return proId;
	}

	public void setProId(long proId) {
		this.proId = proId;
	}

	public long getCallonId() {
		return callonId;
	}

	public void setCallonId(long callonId) {
		this.callonId = callonId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getTalkRecordId() {
		return talkRecordId;
	}

	public void setTalkRecordId(long talkRecordId) {
		this.talkRecordId = talkRecordId;
	}
}
