package com.galaxy.im.business.meeting.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.meeting.MeetingSchedulingBo;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IMeetingSchedulingService extends IBaseService<Test>{

	QPage queryMescheduling(MeetingSchedulingBo query);

	Long selectpdCount(Map<String, Object> ms);

	Long selectltpdCount(Map<String, Object> ms);

	Long queryCountscheduleStatusd(MeetingSchedulingBo query);

	Long queryCountscheduleStatusy(MeetingSchedulingBo query);

}
