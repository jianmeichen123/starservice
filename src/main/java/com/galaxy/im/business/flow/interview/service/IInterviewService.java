package com.galaxy.im.business.flow.interview.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IInterviewService extends IBaseService<Test>{
	Map<String, Object> hasPassInterview(Map<String, Object> paramMap);

	//项目访谈个数
	Map<String, Object> getInterviewCount(Map<String, Object> paramMap);
	
}
