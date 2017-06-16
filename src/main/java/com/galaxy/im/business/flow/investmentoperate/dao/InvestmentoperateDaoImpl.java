package com.galaxy.im.business.flow.investmentoperate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class InvestmentoperateDaoImpl extends BaseDaoImpl<Test, Long> implements IInvestmentoperateDao{
	private Logger log = LoggerFactory.getLogger(InvestmentoperateDaoImpl.class);
	
	public InvestmentoperateDaoImpl(){
		super.setLogger(log);
	}
}
