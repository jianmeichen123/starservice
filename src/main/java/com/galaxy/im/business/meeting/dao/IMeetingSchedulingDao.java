package com.galaxy.im.business.meeting.dao;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.meeting.MeetingSchedulingBo;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IMeetingSchedulingDao extends IBaseDao<Test, Long>{

	QPage queryMescheduling(MeetingSchedulingBo query);

	Long selectpdCount(Map<String, Object> ms);

	Long selectltpdCount(Map<String, Object> ms);

	Long queryCount(MeetingSchedulingBo query);

}
