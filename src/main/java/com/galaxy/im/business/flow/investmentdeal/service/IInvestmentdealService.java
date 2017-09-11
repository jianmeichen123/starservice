package com.galaxy.im.business.flow.investmentdeal.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IInvestmentdealService extends IBaseService<Test>{

	int updateInvestmentdeal(Map<String, Object> paramMap);


	Map<String, Object> getInvestmentdealOperateStatus(Map<String, Object> paramMap);


}
