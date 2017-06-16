package com.galaxy.im.business.flow.investmentPolicy.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class InvestmentPolicyDaoImpl extends BaseDaoImpl<Test, Long> implements IInvestmentPolicyDao{
	private Logger log = LoggerFactory.getLogger(InvestmentPolicyDaoImpl.class);
	
	public InvestmentPolicyDaoImpl(){
		super.setLogger(log);
	}
}
