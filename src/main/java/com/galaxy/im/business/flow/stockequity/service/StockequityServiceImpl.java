package com.galaxy.im.business.flow.stockequity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.stockequity.dao.IStockequityDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class StockequityServiceImpl extends BaseServiceImpl<Test> implements IStockequityService{
	@Autowired
	private IStockequityDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	
}
