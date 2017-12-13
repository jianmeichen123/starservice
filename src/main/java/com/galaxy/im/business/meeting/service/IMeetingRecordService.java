package com.galaxy.im.business.meeting.service;

import java.util.Map;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IMeetingRecordService extends IBaseService<MeetingRecordBean>{

	QPage getMeetingRecordList(Map<String, Object> paramMap);

	Map<String, Object> getSopProjectHealth(Map<String, Object> paramMap);

	Map<String, Object> postMeetingDetail(MeetingRecordBean meetingRecord);

	Map<String, String> transFileNames(String fileName);

	int delPostMeetingFile(SopFileBean sopfile);

	int delMeetingRecord(MeetingRecordBean meetingRecord);

	int getMeetingTypeCount(Map<String, Object> paramMap);


}
