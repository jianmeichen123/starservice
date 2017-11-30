package com.galaxy.im.business.callon.service;

import java.util.Map;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface ICallonService extends IBaseService<ScheduleInfo>{
	QPage selectCallonList(Map<String, Object> paramMap);
	boolean delCallonById(Map<String,Object> paramMap);
	boolean callonEnableEditOrDel(Long id);
	//查询访谈记录
	TalkRecordBean getTalkRecordBean(Long id);
	//删除访谈记录
	int delTalkRecordBean(TalkRecordBean tBean);
	//删除访谈记录//运营会议下的文件
	int deleteSopFileBean(SopFileBean sFileBean);
	//查询运营会议
	MeetingRecordBean getMeetingRecordBean(Long id);
}
