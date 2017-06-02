package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.FinanceHistoryBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IFinanceHistoryDao extends IBaseDao<FinanceHistoryBean,Long>{
	List<Map<String,Object>> getFinanceHistory(Map<String,Object> paramMap);
}
