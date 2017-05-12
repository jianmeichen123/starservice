package com.galaxy.im.business.callon.dao;

import java.util.Map;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface ICallonDao extends IBaseDao<ScheduleInfo, Long>{
	QPage selectCallonList(Map<String, Object> paramMap);
	int delCallonById(Long id);
	int callonEnableEditOrDel(Long id);
}
