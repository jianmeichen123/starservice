package com.galaxy.im.business.project.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectDetailsBean;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ProjectDaoImpl extends BaseDaoImpl<ProjectBean,Long> implements IProjectDao{
	private Logger log = LoggerFactory.getLogger(ProjectDaoImpl.class);
	
	public ProjectDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public ProjectDetailsBean getBaseProjectInfo(Long id) {
		try{
			ProjectDetailsBean bean = sqlSessionTemplate.selectOne(getSqlName("getBaseProjectInfo"),id);
			return bean;
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_getBaseProjectInfo",e);
			throw new DaoException(e);
		}
	}
}
