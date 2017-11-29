package com.galaxy.im.business.meeting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class MeetingSchedulingServiceImpl extends BaseServiceImpl<Test> implements IMeetingSchedulingService {

	@Autowired
	IMeetingSchedulingDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
