package com.galaxy.im.business.flow.investmentoperate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.investmentoperate.dao.IInvestmentoperateDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class InvestmentoperateServiceImpl extends BaseServiceImpl<Test> implements IInvestmentoperateService{
	@Autowired
	private IInvestmentoperateDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	
}
