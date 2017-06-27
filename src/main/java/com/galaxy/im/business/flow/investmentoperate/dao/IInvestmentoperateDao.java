package com.galaxy.im.business.flow.investmentoperate.dao;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IInvestmentoperateDao extends IBaseDao<Test, Long>{

	int getMeetingCount(Map<String, Object> paramMap);

}
