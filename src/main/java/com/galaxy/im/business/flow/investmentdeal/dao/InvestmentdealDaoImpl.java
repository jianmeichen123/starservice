package com.galaxy.im.business.flow.investmentdeal.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class InvestmentdealDaoImpl extends BaseDaoImpl<Test, Long> implements IInvestmentdealDao{
	private Logger log = LoggerFactory.getLogger(InvestmentdealDaoImpl.class);
	
	public InvestmentdealDaoImpl(){
		super.setLogger(log);
	}
}
