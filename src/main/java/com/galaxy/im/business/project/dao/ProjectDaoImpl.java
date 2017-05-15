package com.galaxy.im.business.project.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class ProjectDaoImpl extends BaseDaoImpl<ProjectBean,Long> implements IProjectDao{
	private Logger log = LoggerFactory.getLogger(ProjectDaoImpl.class);
	
	public ProjectDaoImpl(){
		super.setLogger(log);
	}
	

}
