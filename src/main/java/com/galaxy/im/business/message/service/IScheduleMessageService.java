package com.galaxy.im.business.message.service;

import java.util.List;
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

	/**
	 * 新增  修改  删除（日程 、、）操作完成后
	 * 消息同步修改
	 * ScheduleMessage    ScheduleMessageUser
     */
	void operateMessageBySaveInfo(Object scheduleInfo);
	void operateMessageByDeleteInfo(Object scheduleInfo, String messageType);
	void operateMessageByUpdateInfo(Object scheduleInfo, String messageType);
	
	//代办，项目消息推送
	void operateMessageSopTaskInfo(Object model);
	//获取发送消息
	List<ScheduleMessageBean> queryTodayMessToSend();

}
