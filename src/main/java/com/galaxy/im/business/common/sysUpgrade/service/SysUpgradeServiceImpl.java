package com.galaxy.im.business.common.sysUpgrade.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.common.sysUpgrade.dao.ISysUpgradeDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class SysUpgradeServiceImpl extends BaseServiceImpl<Test> implements ISysUpgradeService{

	@Autowired
	ISysUpgradeDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	@Override
	public Map<String, Object> getSysUpgradeMessage(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
