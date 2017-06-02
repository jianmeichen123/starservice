package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.FinanceHistoryBean;
import com.galaxy.im.common.db.service.IBaseService;

public interface IFinanceHistoryService extends IBaseService<FinanceHistoryBean>{
	List<Map<String,Object>> getFinanceHistory(Map<String,Object> paramMap);
	Long addFinanceHistory(FinanceHistoryBean historyBean);
	Integer updateFinanceHistory(FinanceHistoryBean historyBean);
}
