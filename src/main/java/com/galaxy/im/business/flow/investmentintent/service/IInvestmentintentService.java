package com.galaxy.im.business.flow.investmentintent.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IInvestmentintentService extends IBaseService<Test>{

	Map<String, Object> investmentOperateStatus(Map<String, Object> paramMap);

}
