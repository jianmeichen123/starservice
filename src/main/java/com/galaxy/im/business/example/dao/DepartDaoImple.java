package com.galaxy.im.business.example.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.DepartBean;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class DepartDaoImple extends BaseDaoImpl<DepartBean,Long> implements IDepartDao{
	private Logger log = LoggerFactory.getLogger(DepartDaoImple.class);
	
	public DepartDaoImple(){
		super.setLogger(log);
	}
}
