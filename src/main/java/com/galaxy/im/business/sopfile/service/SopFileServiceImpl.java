package com.galaxy.im.business.sopfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.sopfile.dao.ISopFileDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class SopFileServiceImpl extends BaseServiceImpl<Test> implements ISopFileService{

	@Autowired
	ISopFileDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
