package com.galaxy.im.business.flow.investmentdeal.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IInvestmentdealDao extends IBaseDao<Test, Long>{

	int updateInvestmentdeal(Map<String, Object> paramMap);


	Map<String, Object> businessnegotiation(Map<String, Object> paramMap);
	
	List<Map<String, Object>> hasPassMeeting(Map<String, Object> paramMap);


	List<Map<String, Object>> projectResult(Map<String, Object> paramMap);



}
