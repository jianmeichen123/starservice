package com.galaxy.im.business.callon.dao;

import java.util.Map;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface ICallonDao extends IBaseDao<ScheduleInfo, Long>{
	QPage selectCallonList(Map<String, Object> paramMap);
	int delCallonById(Map<String,Object> paramMap);
	int callonEnableEditOrDel(Long id);
	//查询访谈记录
	TalkRecordBean getTalkRecordBean(Long id);
	//删除访谈记录
	int delTalkRecordBean(TalkRecordBean tBean);
	//删除运营会议
	int deleteMeetingRecordBean(MeetingRecordBean mBean);
	//查询运营会议
	MeetingRecordBean getMeetingRecordBean(Long id);
}
