package com.galaxy.im.business.callon.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.callon.dao.ICallonDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class CallonServiceImpl extends BaseServiceImpl<ScheduleInfo> implements ICallonService{
	private Logger log = LoggerFactory.getLogger(CallonServiceImpl.class);
	
	@Autowired
	private ICallonDao callonDao;

	@Override
	protected IBaseDao<ScheduleInfo, Long> getBaseDao() {
		return callonDao;
	}

	@Override
	public QPage selectCallonList(Map<String, Object> paramMap){
		try{
			return callonDao.selectCallonList(paramMap);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_getCallonList",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean delCallonById(Long id) {
		try{
			return (callonDao.delCallonById(id)>0);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean callonEnableEditOrDel(Long id) {
		try{
			return (callonDao.callonEnableEditOrDel(id)>0);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}
	
	
	

	
}
