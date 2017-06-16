package com.galaxy.im.business.flow.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.interview.dao.IInterviewDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class InterviewServiceImpl extends BaseServiceImpl<Test> implements IInterviewService{
	//private Logger log = LoggerFactory.getLogger(CallonDetailServiceImpl.class);
	
	@Autowired
	private IInterviewDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}


}
