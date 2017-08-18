package com.galaxy.im.business.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.message.ScheduleMessageUserBean;
import com.galaxy.im.business.message.dao.IScheduleMessageUserDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
@Service
public class ScheduleMessageUserServiceImpl extends BaseServiceImpl<ScheduleMessageUserBean>implements IScheduleMessageUserService{
	private Logger log = LoggerFactory.getLogger(ScheduleMessageUserServiceImpl.class);
	@Autowired
	private IScheduleMessageUserDao dao;
	
	@Override
	protected IBaseDao<ScheduleMessageUserBean, Long> getBaseDao() {
		return dao;
	}
}
