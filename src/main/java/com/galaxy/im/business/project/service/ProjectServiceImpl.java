package com.galaxy.im.business.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectDetailsBean;
import com.galaxy.im.business.project.dao.IProjectDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectBean> implements IProjectService{
	private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	@Autowired
	private IProjectDao dao;

	@Override
	protected IBaseDao<ProjectBean, Long> getBaseDao() {
		return dao;
	}

	@Override
	public ProjectDetailsBean getBaseProjectInfo(Long id) {
		try{
			return dao.getBaseProjectInfo(id);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_ProjectServiceImpl",e);
			throw new ServiceException(e);
		}
	}

	
}
