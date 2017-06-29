package com.galaxy.im.business.flow.investmentPolicy.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IInvestmentPolicyService extends IBaseService<Test>{

	Map<String, Object> investmentpolicy(Map<String, Object> paramMap);
	
	Map<String, Object> getInvestmentdealOperateStatus(Map<String, Object> paramMap);

}
