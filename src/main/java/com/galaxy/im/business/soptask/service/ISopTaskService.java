package com.galaxy.im.business.soptask.service;

import java.util.Map;

import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface ISopTaskService extends IBaseService<SopTask>{
	//待办任务列表
	QPage taskListByRole(Map<String, Object> paramMap);

	//获取部门id
	long getDepId(Long object2Long);

}
