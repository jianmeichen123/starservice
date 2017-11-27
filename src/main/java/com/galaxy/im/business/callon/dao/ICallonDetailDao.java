package com.galaxy.im.business.callon.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.schedule.ScheduleDetailBean;
import com.galaxy.im.bean.schedule.ScheduleDetailBeanVo;
import com.galaxy.im.common.db.IBaseDao;

public interface ICallonDetailDao extends IBaseDao<ScheduleDetailBean, Long>{

	//获取字典表信息
	List<Map<String, Object>> getSignificanceDictList();
	//获取提醒通知信息
	List<Map<String, Object>> getScheduleDictList();
	//获取访谈历史记录个数
	long getTalkHistoryCounts(ScheduleDetailBeanVo detail);
	//拜访详情
	List<ScheduleDetailBean> getQueryById(long callonId);
	//运营会议历史个数
	long getPosMeetingCount(Map<String, Object> paramMap);


}
