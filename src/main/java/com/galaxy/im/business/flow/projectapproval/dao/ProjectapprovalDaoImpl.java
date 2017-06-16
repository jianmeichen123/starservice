package com.galaxy.im.business.flow.projectapproval.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class ProjectapprovalDaoImpl extends BaseDaoImpl<Test, Long> implements IProjectapprovalDao{
	private Logger log = LoggerFactory.getLogger(ProjectapprovalDaoImpl.class);
	
	public ProjectapprovalDaoImpl(){
		super.setLogger(log);
	}
}
