package com.galaxy.im.business.report.legalinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.report.legalinfo.dao.ILegalInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class LegalInfoServiceImpl extends BaseServiceImpl<Test> implements ILegalInfoService{

	@Autowired
	ILegalInfoDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
