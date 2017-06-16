package com.galaxy.im.business.flow.investmentPolicy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.investmentPolicy.dao.IInvestmentPolicyDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class InvestmentPolicyServiceImpl extends BaseServiceImpl<Test> implements IInvestmentPolicyService{
	@Autowired
	private IInvestmentPolicyDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	

}
