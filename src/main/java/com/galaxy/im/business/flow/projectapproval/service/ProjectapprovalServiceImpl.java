package com.galaxy.im.business.flow.projectapproval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.projectapproval.dao.IProjectapprovalDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class ProjectapprovalServiceImpl extends BaseServiceImpl<Test> implements IProjectapprovalService{
	@Autowired
	private IProjectapprovalDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	

}
