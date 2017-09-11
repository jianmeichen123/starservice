package com.galaxy.im.business.flow.investmentintent.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IInvestmentintentDao extends IBaseDao<Test, Long>{

	List<Map<String, Object>> investmentOperateStatus(Map<String, Object> paramMap);

}
