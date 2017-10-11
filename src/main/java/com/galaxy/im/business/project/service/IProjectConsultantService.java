package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectConsultantService extends IBaseService<InformationListdata>{

	QPage queryProjectPerson(Map<String, Object> paramMap);

	Object queryPersonInfo(Map<String, Object> paramMap);

	List<Object> queryStudyExperience(Map<String, Object> paramMap);

	List<Object> queryWorkExperience(Map<String, Object> paramMap);

	List<Object> queryEntrepreneurialExperience(Map<String, Object> paramMap);

	int addProjectPerson(Map<String, Object> paramMap);

	InformationListdata selectInfoById(Map<String, Object> paramMap);

	int updateProjectPerson(Map<String, Object> paramMap);

	int deleteProjectPersonById(Map<String, Object> paramMap);

}
