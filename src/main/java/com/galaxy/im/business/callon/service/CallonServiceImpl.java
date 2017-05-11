package com.galaxy.im.business.callon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.callon.dao.ICallonDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class CallonServiceImpl extends BaseServiceImpl<ScheduleInfo> implements ICallonService{
	
	@Autowired
	private ICallonDao callonDao;

	@Override
	protected IBaseDao<ScheduleInfo, Long> getBaseDao() {
		return callonDao;
	}

	
}
