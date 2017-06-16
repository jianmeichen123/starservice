package com.galaxy.im.business.flow.investmentintent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.investmentintent.dao.IInvestmentintentDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class InvestmentintentServiceImpl extends BaseServiceImpl<Test> implements IInvestmentintentService{
	@Autowired
	private IInvestmentintentDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	
}
