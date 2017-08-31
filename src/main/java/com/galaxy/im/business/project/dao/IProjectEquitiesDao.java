package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IProjectEquitiesDao extends IBaseDao<InformationListdata, Long>{

	List<Object> selectFRInfo(Map<String, Object> paramMap);

	QPage selectProjectShares(Map<String, Object> paramMap);

	void addProjectShares(Map<String, Object> paramMap);

	InformationListdata selectInfoById(Map<String, Object> paramMap);

	void updateProjectShares(Map<String, Object> paramMap);

	void deleteProjectSharesById(Map<String, Object> paramMap);

}
