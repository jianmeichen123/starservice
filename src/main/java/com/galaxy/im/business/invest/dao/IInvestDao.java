package com.galaxy.im.business.invest.dao;

import java.util.Map;

import com.galaxy.im.bean.invest.InvestBean;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IInvestDao extends IBaseDao<InvestBean, Long>{

	QPage selectInvestList(Map<String, Object> paramMap);

	int deleteByIdAndPid(Map<String, Object> paramMap);
}
