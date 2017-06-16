package com.galaxy.im.business.flow.interview.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class InterviewDaoImpl extends BaseDaoImpl<Test, Long> implements IInterviewDao{
	private Logger log = LoggerFactory.getLogger(InterviewDaoImpl.class);
	
	public InterviewDaoImpl(){
		super.setLogger(log);
	}

}
