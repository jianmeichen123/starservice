package com.galaxy.im.business.callon.service;

import java.util.Map;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface ICallonService extends IBaseService<ScheduleInfo>{
	QPage selectCallonList(Map<String, Object> paramMap);
	boolean delCallonById(Long id);
	boolean callonEnableEditOrDel(Long id);
}
