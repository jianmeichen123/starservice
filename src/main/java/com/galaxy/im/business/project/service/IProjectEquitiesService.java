package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectEquitiesService extends IBaseService<InformationListdata>{

	List<Object> selectFRInfo(Map<String, Object> paramMap);

	QPage selectProjectShares(Map<String, Object> paramMap);

	int addProjectShares(Map<String, Object> paramMap);

	InformationListdata selectInfoById(Map<String, Object> paramMap);

	int updateProjectShares(Map<String, Object> paramMap);

	int deleteProjectSharesById(Map<String, Object> paramMap);

	int addFRInfo(Map<String, Object> paramMap);

	int updateFRInfo(Map<String, Object> paramMap);

	int saveInfomationListData(InformationListdata data);

}
