package com.galaxy.im.business.hologram.strategyinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.hologram.strategyinfo.dao.IStrategyInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class StrategyInfoServiceImpl extends BaseServiceImpl<Test> implements IStrategyInfoService{

	@Autowired
	IStrategyInfoDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
