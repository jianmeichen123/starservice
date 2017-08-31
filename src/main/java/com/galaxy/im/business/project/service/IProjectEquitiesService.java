package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectEquitiesService extends IBaseService<InformationListdata>{

	List<Object> selectFRInfo(Map<String, Object> paramMap);

	QPage selectProjectShares(Map<String, Object> paramMap);

	void addProjectShares(Map<String, Object> paramMap);

	InformationListdata selectInfoById(Map<String, Object> paramMap);

	void updateProjectShares(Map<String, Object> paramMap);

	void deleteProjectSharesById(Map<String, Object> paramMap);

}
