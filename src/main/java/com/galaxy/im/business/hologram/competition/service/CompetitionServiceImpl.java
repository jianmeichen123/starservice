package com.galaxy.im.business.hologram.competition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.hologram.competition.dao.ICompetitionDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class CompetitionServiceImpl extends BaseServiceImpl<Test> implements ICompetitionService{

	@Autowired
	ICompetitionDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	

}
