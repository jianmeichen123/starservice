package com.galaxy.im.business.common.dict.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.common.Dict;
import com.galaxy.im.common.db.IBaseDao;

public interface IDictDao extends IBaseDao<Dict, Long>{
	List<Map<String,Object>> selectCallonFilter();

	List<Map<String, Object>> selectResultFilter(Map<String, Object> paramMap);

	List<Map<String, Object>> selectReasonFilter(Map<String, Object> paramMap);

	List<Dict> selectByParentCode(String parentCode);

	List<Map<String, Object>> getFinanceStatusList(Map<String, Object> paramMap);

	List<Map<String, Object>> getShareholderNatureList(Map<String, Object> paramMap);

	List<Map<String, Object>> getShareholderTypeList(Map<String, Object> paramMap);

	List<Map<String, Object>> getTeamUserPosition(Map<String, Object> paramMap);

	List<Map<String, Object>> getfinanceUnitList(Map<String, Object> paramMap);

	List<Map<String, Object>> getfinancingStatusList(Map<String, Object> paramMap);

	List<Map<String, Object>> getfinancingStockList(Map<String, Object> paramMap);
}
