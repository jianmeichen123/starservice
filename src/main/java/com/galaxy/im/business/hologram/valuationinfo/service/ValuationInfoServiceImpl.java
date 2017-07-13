package com.galaxy.im.business.hologram.valuationinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.hologram.valuationinfo.dao.IValuationInfoDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class ValuationInfoServiceImpl extends BaseServiceImpl<Test> implements IValuationInfoService{

	@Autowired
	IValuationInfoDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

}
