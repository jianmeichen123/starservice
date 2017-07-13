package com.galaxy.im.business.hologram.operationaldata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.hologram.operationaldata.dao.IOperationalDataDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class OperationalDataServiceImpl extends BaseServiceImpl<Test> implements IOperationalDataService{

	@Autowired
	IOperationalDataDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
