package com.galaxy.im.business.hologram.baseinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.hologram.baseinfo.dao.IBaseInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class BaseInfoServiceImpl extends BaseServiceImpl<Test> implements IBaseInfoService{

	@Autowired
	IBaseInfoDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
