package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.FinanceHistoryBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IFinanceHistoryDao extends IBaseDao<FinanceHistoryBean,Long>{
	
	//融资历史列表
	List<Map<String,Object>> getFinanceHistory(Map<String,Object> paramMap);
	//融资历史详情
	Map<String, Object> getFinanceHistoryDetails(Map<String, Object> paramMap);
	//添加融资历史
	Long addFinanceHistory(Map<String, Object> paramMap);
	//编辑融资历史
	Long updateFinanceHistory(Map<String, Object> paramMap);
}
