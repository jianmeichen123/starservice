package com.galaxy.im.business.message.dao;

import java.util.List;

import com.galaxy.im.bean.message.ScheduleMessageBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IScheduleMessageDao extends IBaseDao<ScheduleMessageBean, Long>{

	Long saveMessage(ScheduleMessageBean message);

	List<ScheduleMessageBean> selectMessageList(ScheduleMessageBean mQ);

	void updateMessageById(ScheduleMessageBean mess);

	void deleteMessageById(Long id);



}
