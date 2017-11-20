package com.galaxy.im.business.soptask.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.business.soptask.dao.ISopTaskDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class SopTaskServiceImpl extends BaseServiceImpl<SopTask> implements ISopTaskService{
	private Logger log = LoggerFactory.getLogger(SopTaskServiceImpl.class);

	@Autowired
	ISopTaskDao dao;
	
	@Override
	protected IBaseDao<SopTask, Long> getBaseDao() {
		return dao;
	}

	//待办任务列表
	@Override
	public QPage taskListByRole(Map<String, Object> paramMap) {
		try{
			return dao.taskListByRole(paramMap);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public long getDepId(Long id) {
		try{
			return dao.getDepId(id);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}
	
	
	
	
}
