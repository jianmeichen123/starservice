package com.galaxy.im.business.report.operationalinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.report.operationalinfo.dao.IOperationalInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class OperationalInfoServiceImpl extends BaseServiceImpl<Test> implements IOperationalInfoService{

	@Autowired
	IOperationalInfoDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
