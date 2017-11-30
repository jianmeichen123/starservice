package com.galaxy.im.business.meeting.dao;

import java.util.Map;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IMeetingRecordDao extends IBaseDao<MeetingRecordBean, Long>{

	QPage getMeetingRecordList(Map<String, Object> paramMap);

	Map<String, Object> getSopProjectHealth(Map<String, Object> paramMap);

	Map<String, Object> postMeetingDetail(MeetingRecordBean meetingRecord);

	int delMeetingRecord(MeetingRecordBean meetingRecord);

	MeetingRecordBean getMeetingRecord(Long id);

}
