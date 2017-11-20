package com.galaxy.im.business.soptask.dao;

import java.util.Map;

import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface ISopTaskDao extends IBaseDao<SopTask, Long>{

	//待办列表
	QPage taskListByRole(Map<String, Object> paramMap);

	//获取部门id
	long getDepId(Long id);

	//待办任务详情
	Object taskInfo(Map<String, Object> paramMap);

}
