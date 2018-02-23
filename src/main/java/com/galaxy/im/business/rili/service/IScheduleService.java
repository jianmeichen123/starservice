package com.galaxy.im.business.rili.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.rili.util.ScheduleUtil;
import com.galaxy.im.common.db.service.IBaseService;

public interface IScheduleService  extends IBaseService<ScheduleInfo>{

	//时间是否冲突
	List<Map<String, Object>> ctSchedule(Map<String, Object> map);
	//判断是否超过20条
	String getCountSchedule(Map<String, Object> map);
	//删除日程
	boolean deleteOtherScheduleById(Map<String, Object> map);
	//根据id查询详情
	Map<String, Object> selectOtherScheduleById(Map<String, Object> map);
	//列表查询
	List<ScheduleUtil> queryAndConvertList(ScheduleInfo query) throws ParseException;
	//搜索拜访对象/其他日程
	List<ScheduleInfo> getList(Map<String, Object> map);
	//日程消息未读个数
	Long queryProjectScheduleCount(Long getuId);
	List<ScheduleUtil> selectList(ScheduleInfo query) throws ParseException;

}
