package com.galaxy.im.business.flow.ceoreview.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class CeoreviewDaoImpl extends BaseDaoImpl<Test, Long> implements ICeoreviewDao{
	private Logger log = LoggerFactory.getLogger(CeoreviewDaoImpl.class);
	
	public CeoreviewDaoImpl(){
		super.setLogger(log);
	}
}
