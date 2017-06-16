package com.galaxy.im.business.flow.internalreview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.internalreview.dao.IInternalreviewDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class InternalreviewServiceImpl extends BaseServiceImpl<Test> implements IInternalreviewService{
	
	@Autowired
	private IInternalreviewDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	

}
