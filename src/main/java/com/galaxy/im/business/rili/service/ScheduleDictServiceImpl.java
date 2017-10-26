package com.galaxy.im.business.rili.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.schedule.ScheduleDict;
import com.galaxy.im.business.rili.dao.IScheduleDictDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;


@Service
public class ScheduleDictServiceImpl extends BaseServiceImpl<ScheduleDict> implements IScheduleDictService{
	@Autowired
	IScheduleDictDao dao;
	
	@Override
	protected IScheduleDictDao getBaseDao() {
		return dao;
	}
}
