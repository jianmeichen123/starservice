package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IProjectConsultantDao extends IBaseDao<InformationListdata, Long>{

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
