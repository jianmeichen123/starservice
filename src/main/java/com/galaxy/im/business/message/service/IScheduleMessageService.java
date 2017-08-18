package com.galaxy.im.business.message.service;

import java.util.Map;

import com.galaxy.im.bean.message.ScheduleMessageBean;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IScheduleMessageService extends IBaseService<ScheduleMessageBean>{

	QPage queryPerMessAndConvertPage(Map<String, Object> paramMap);

	long updateToRead(Map<String, Object> paramMap);

	int perMessageToRead(Long guserid);

	int perMessageToClear(Long guserid);

	Integer selectMuserAndMcontentCount(Map<String, Object> paramMap);



}
