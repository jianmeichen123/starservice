package com.galaxy.im.business.flow.investmentoperate.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IInvestmentoperateService extends IBaseService<Test>{

	Map<String, Object> getMeetingCount(Map<String, Object> paramMap);

}
