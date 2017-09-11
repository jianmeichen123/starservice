package com.galaxy.im.business.flow.interview.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IInterviewDao extends IBaseDao<Test, Long>{
	List<Map<String,Object>> hasPassInterview(Map<String,Object> paramId);

	int getInterviewCount(Map<String, Object> paramMap);
}
