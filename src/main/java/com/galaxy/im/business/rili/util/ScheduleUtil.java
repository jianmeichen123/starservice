package com.galaxy.im.business.rili.util;

import java.util.ArrayList;
import java.util.List;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.common.db.PagableEntity;


public class ScheduleUtil extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private String dateKey;  //按日查时 ： a:深夜  b:上午  c:下午  d:晚上
	
	private List<ScheduleInfo> schedules;


	public String getDateKey() {
		return dateKey;
	}

	public void setDateKey(String dateKey) {
		this.dateKey = dateKey;
	}

	public List<ScheduleInfo> getSchedules() {
		return schedules == null ? new ArrayList<ScheduleInfo>() : schedules;
	}

	public void setSchedules(List<ScheduleInfo> schedules) {
		this.schedules = schedules;
	}


	
}




