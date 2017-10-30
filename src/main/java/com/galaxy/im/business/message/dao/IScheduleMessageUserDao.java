package com.galaxy.im.business.message.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.message.ScheduleMessageUserBean;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IScheduleMessageUserDao  extends IBaseDao<ScheduleMessageUserBean, Long>{

	QPage selectMuserAndMcontentList(Map<String, Object> paramMap);

	long updateToRead(Map<String, Object> paramMap);

	List<ScheduleMessageUserBean> selectMessageList(Map<Object, Object> paramMap);

	int perMessageToRead(Map<Object, Object> paramMap);

	int perMessageToClear(Map<Object, Object> paramMap);

	Integer selectMuserAndMcontentCount(Map<String, Object> paramMap);

	void saveMessageUser(List<ScheduleMessageUserBean> toInserts);

	List<ScheduleMessageUserBean> selectMessageUserList(ScheduleMessageUserBean muQ);

	void updateMessageUserById(ScheduleMessageUserBean toU);

	void deleteMessageUser(ScheduleMessageUserBean scheduleMessageUser);

	
}
