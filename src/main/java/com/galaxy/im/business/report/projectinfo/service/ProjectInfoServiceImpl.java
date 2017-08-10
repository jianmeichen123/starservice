package com.galaxy.im.business.report.projectinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.report.projectinfo.dao.IProjectInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class ProjectInfoServiceImpl extends BaseServiceImpl<Test> implements IProjectInfoService{

	@Autowired
	IProjectInfoDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
