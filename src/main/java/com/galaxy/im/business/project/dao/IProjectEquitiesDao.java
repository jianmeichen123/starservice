package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IProjectEquitiesDao extends IBaseDao<InformationListdata, Long>{

	List<Object> selectFRInfo(Map<String, Object> paramMap);

	QPage selectProjectShares(Map<String, Object> paramMap);

	int addProjectShares(Map<String, Object> paramMap);

	InformationListdata selectInfoById(Map<String, Object> paramMap);

	int updateProjectShares(Map<String, Object> paramMap);

	int deleteProjectSharesById(Map<String, Object> paramMap);

	int addFRInfo(Map<String, Object> paramMap);

	int updateFRInfo(Map<String, Object> paramMap);

	int saveInfomationListData(InformationListdata data);

	int updateInfomationListData(InformationListdata data);

}
