package com.galaxy.im.business.flow.investmentdeal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.investmentdeal.dao.IInvestmentdealDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class InvestmentdealServiceImpl extends BaseServiceImpl<Test> implements IInvestmentdealService{
	@Autowired
	private IInvestmentdealDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	
	

}
