package com.galaxy.im.business.flow.internalreview.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class InternalreviewDaoImpl extends BaseDaoImpl<Test, Long> implements IInternalreviewDao{
	private Logger log = LoggerFactory.getLogger(InternalreviewDaoImpl.class);
	
	public InternalreviewDaoImpl(){
		super.setLogger(log);
	}
}
