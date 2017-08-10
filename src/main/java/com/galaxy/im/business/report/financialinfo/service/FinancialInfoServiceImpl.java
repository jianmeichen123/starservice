package com.galaxy.im.business.report.financialinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.report.financialinfo.dao.IFinancialInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class FinancialInfoServiceImpl extends BaseServiceImpl<Test> implements IFinancialInfoService{

	@Autowired
	IFinancialInfoDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
