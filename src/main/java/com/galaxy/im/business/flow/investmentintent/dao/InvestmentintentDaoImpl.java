package com.galaxy.im.business.flow.investmentintent.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class InvestmentintentDaoImpl extends BaseDaoImpl<Test, Long> implements IInvestmentintentDao{
	private Logger log = LoggerFactory.getLogger(InvestmentintentDaoImpl.class);
	
	public InvestmentintentDaoImpl(){
		super.setLogger(log);
	}
	
}
