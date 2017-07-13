package com.galaxy.im.business.hologram.competitioninfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.hologram.competitioninfo.dao.ICompetitionInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class CompetitionInfoServiceImpl extends BaseServiceImpl<Test> implements ICompetitionInfoService{

	@Autowired
	ICompetitionInfoDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	

}
