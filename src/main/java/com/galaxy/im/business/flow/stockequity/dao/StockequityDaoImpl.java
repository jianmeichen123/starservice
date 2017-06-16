package com.galaxy.im.business.flow.stockequity.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class StockequityDaoImpl extends BaseDaoImpl<Test, Long> implements IStockequityDao{
	private Logger log = LoggerFactory.getLogger(StockequityDaoImpl.class);
	
	public StockequityDaoImpl(){
		super.setLogger(log);
	}
}
