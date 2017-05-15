package com.galaxy.im.bean.schedule;

public class ScheduleDetailBeanVo extends ScheduleDetailBean{

	private static final long serialVersionUID = -8099453993583921547L;
	
	//查询字段，拜访id
	private long callonId;
	//拜访对象id
	private long conId;
	//关系项目id
	private long proId;


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

}
