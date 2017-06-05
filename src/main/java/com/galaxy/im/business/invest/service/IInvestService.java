package com.galaxy.im.business.invest.service;

import java.util.Map;

import com.galaxy.im.bean.invest.InvestBean;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IInvestService extends IBaseService<InvestBean>{

	QPage selectInvestList(Map<String, Object> paramMap);
}
