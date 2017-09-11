package com.galaxy.im.business.flow.investmentoperate.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.investmentoperate.dao.IInvestmentoperateDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class InvestmentoperateServiceImpl extends BaseServiceImpl<Test> implements IInvestmentoperateService{
	@Autowired
	private IInvestmentoperateDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	@Override
	public Map<String, Object> getMeetingCount(Map<String, Object> paramMap) {
		int count = dao.getMeetingCount(paramMap);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("meetingCount", count);
		return map;
	}
	
	
}
