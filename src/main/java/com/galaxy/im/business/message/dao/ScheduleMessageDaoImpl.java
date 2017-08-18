package com.galaxy.im.business.message.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.message.ScheduleMessageBean;
import com.galaxy.im.common.db.BaseDaoImpl;
@Repository
public class ScheduleMessageDaoImpl extends BaseDaoImpl<ScheduleMessageBean, Long>implements IScheduleMessageDao {
private Logger log = LoggerFactory.getLogger(ScheduleMessageDaoImpl.class);
	
	public ScheduleMessageDaoImpl(){
		super.setLogger(log);
		
	}


}
