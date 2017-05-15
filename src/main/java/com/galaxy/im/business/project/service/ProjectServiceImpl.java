package com.galaxy.im.business.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.business.project.dao.IProjectDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectBean> implements IProjectService{
	
	@Autowired
	private IProjectDao dao;

	@Override
	protected IBaseDao<ProjectBean, Long> getBaseDao() {
		return dao;
	}
	
	
}
