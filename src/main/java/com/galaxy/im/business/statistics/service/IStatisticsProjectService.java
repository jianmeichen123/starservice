package com.galaxy.im.business.statistics.service;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.statistics.ScheduleCountVO;
import com.galaxy.im.common.db.service.IBaseService;

public interface IStatisticsProjectService extends IBaseService<Test>{

	List<Map<String, Object>> queryCountCareerLineRankProject();

	Map<String, Object> querySatisticsProjectOverview(List<String> roleCodeList, long deptId, Long guserid);

	Map<String, Object> queryCountMonthProjectChanged(List<String> roleCodeList, long deptId, Long guserid);

	List<ScheduleCountVO> queryTZJLScheduleCount(Long guserid);

	List<ScheduleCountVO> queryHHRScheduleCount(Long guserid);

	List<ScheduleCountVO> queryCEOScheduleCount(Long guserid);

	List<Map<String, Object>> selectStageSummary(Map<String, Object> map1);


}
