package com.galaxy.im.business.flow.ceoreview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.ceoreview.dao.ICeoreviewDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class CeoreviewServiceImpl extends BaseServiceImpl<Test> implements ICeoreviewService{

	@Autowired
	private ICeoreviewDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	
}
