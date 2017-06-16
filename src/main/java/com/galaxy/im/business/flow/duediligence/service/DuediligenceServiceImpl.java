package com.galaxy.im.business.flow.duediligence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.duediligence.dao.IDuediligenceDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class DuediligenceServiceImpl extends BaseServiceImpl<Test> implements IDuediligenceService{
	@Autowired
	private IDuediligenceDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	
}
