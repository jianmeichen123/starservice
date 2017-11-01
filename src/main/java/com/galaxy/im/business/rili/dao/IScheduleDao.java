package com.galaxy.im.business.rili.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.common.db.IBaseDao;

public interface IScheduleDao extends IBaseDao<ScheduleInfo, Long>{

	//获取日程冲突list
	List<Map<String, Object>> getCTSchedule(Map<String, Object> map);
	//判断是否超过20条
	List<Map<String, Object>> getCountSchedule(Map<String, Object> map);
	//删除日程
	int delCallonById(Map<String, Object> paramMap);
	//根据id查询详情
	Map<String, Object> selectOtherScheduleById(Map<String, Object> map);
	//查询拜访对象
	ScheduleInfo selectVisitNameById(Long id);
	//查询列表
	List<ScheduleInfo> selectLists(ScheduleInfo toQ);
	//搜索拜访对象/其他日程
	List<ScheduleInfo> getList(Map<String, Object> map);
	//日程消息未读个数
	Long queryProjectScheduleCount(Long uid);

}
