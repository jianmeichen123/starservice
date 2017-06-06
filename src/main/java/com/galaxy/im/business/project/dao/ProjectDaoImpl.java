package com.galaxy.im.business.project.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ProjectDaoImpl extends BaseDaoImpl<ProjectBean,Long> implements IProjectDao{
	private Logger log = LoggerFactory.getLogger(ProjectDaoImpl.class);
	
	public ProjectDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public Map<String,Object> getBaseProjectInfo(Long id) {
		try{
			Map<String,Object> bean = sqlSessionTemplate.selectOne(getSqlName("getBaseProjectInfo"),id);
			return bean;
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_getBaseProjectInfo",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Map<String, Object> getProjectInoIsNull(Long id) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("getProjectInoIsNull"),id);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_getProjectInoIsNull",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Integer projectIsYJZ(Long projectId) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("projectIsYJZ"),projectId);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_projectIsYJZ",e);
			throw new DaoException(e);
		}
	}
	
	@Override
	public Integer projectIsShow(Long projectId) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("projectIsShow"),projectId);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_projectIsShow",e);
			throw new DaoException(e);
		}
	}

	
}
