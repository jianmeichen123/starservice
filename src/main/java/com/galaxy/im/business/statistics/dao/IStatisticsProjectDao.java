package com.galaxy.im.business.statistics.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.statistics.ScheduleCountVO;
import com.galaxy.im.common.db.IBaseDao;

public interface IStatisticsProjectDao extends IBaseDao<Test, Long>{

	Long getCountProjectNumByParams(Map<String, Object> insGzjMap);

	List<Map<String, Object>> getCountCareerLineRankList(Map<String, Integer> topMap);

	Long getCountMonthProject(Map<String, Object> queryMap);

	List<ScheduleCountVO> queryTZJLScheduleCount(Long guserid);

	List<ScheduleCountVO> queryHHRScheduleCount(Long guserid);

	List<ScheduleCountVO> queryCEOScheduleCount(Long guserid);

	List<Map<String, Object>> selectStageSummary(Map<String, Object> map1);

}
