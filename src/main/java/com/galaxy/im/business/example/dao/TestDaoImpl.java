package com.galaxy.im.business.example.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class TestDaoImpl extends BaseDaoImpl<Test, Long> implements ITestDao{
private Logger log = LoggerFactory.getLogger(TestDaoImpl.class);
	
	public TestDaoImpl(){
		super.setLogger(log);
	}
	
}
