package com.galaxy.im.business.schedule.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.schedule.dao.IScheduleDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class ScheduleServiceImpl extends BaseServiceImpl<ScheduleInfo> implements IScheduleService {

	private Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Autowired
	IScheduleDao dao;
	
	@Override
	protected IBaseDao<ScheduleInfo, Long> getBaseDao() {
		return dao;
	}
	
	/**
	 * 时间冲突
	 */
	@Override
	public List<Map<String, Object>> ctSchedule(Map<String, Object> map) {
		try{
			List<Map<String, Object>> list = dao.getCTSchedule(map);
			return list;
		}catch(Exception e){
			log.error(ScheduleServiceImpl.class.getName() + "_ctSchedule",e);
			throw new ServiceException(e);
		}
	}
	

}
