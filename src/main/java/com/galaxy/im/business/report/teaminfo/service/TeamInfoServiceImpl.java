package com.galaxy.im.business.report.teaminfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.report.teaminfo.dao.ITeamInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class TeamInfoServiceImpl extends BaseServiceImpl<Test> implements ITeamInfoService{

	@Autowired
	ITeamInfoDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
