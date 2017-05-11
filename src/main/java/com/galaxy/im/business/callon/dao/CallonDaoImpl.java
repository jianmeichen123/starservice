package com.galaxy.im.business.callon.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class CallonDaoImpl extends BaseDaoImpl<ScheduleInfo, Long> implements ICallonDao{
	private Logger log = LoggerFactory.getLogger(CallonDaoImpl.class);
	
	public CallonDaoImpl(){
		super.setLogger(log);
	}
}
