package com.galaxy.im.business.flow.duediligence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class DuediligenceDaoImpl extends BaseDaoImpl<Test, Long> implements IDuediligenceDao{
	private Logger log = LoggerFactory.getLogger(DuediligenceDaoImpl.class);
	
	public DuediligenceDaoImpl(){
		super.setLogger(log);
	}
}
