package com.galaxy.im.business.statistics.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.statistics.ScheduleCountVO;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class StatisticsProjectDaoImpl extends BaseDaoImpl<Test, Long> implements IStatisticsProjectDao{
	private Logger log = LoggerFactory.getLogger(StatisticsProjectDaoImpl.class);

	@Override
	public Long getCountProjectNumByParams(Map<String, Object> queryMap) {
		try {
			return sqlSessionTemplate.selectOne("selectCountProjectOverview" ,  queryMap);
		} catch (Exception e) {
			log.error(StatisticsProjectDaoImpl.class.getName() + "：getCountProjectNumByParams",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getCountCareerLineRankList(Map<String, Integer> topMap) {
		try {
			return sqlSessionTemplate.selectList("selectCountCareerLineTop" , topMap);
		} catch (Exception e) {
			log.error(StatisticsProjectDaoImpl.class.getName() + "：selectCountCareerLineTop",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Long getCountMonthProject(Map<String, Object> queryMap) {
		try {
			return sqlSessionTemplate.selectOne("selectCountCurrentMonthProjectNumber" , queryMap);
		} catch (Exception e) {
			log.error(StatisticsProjectDaoImpl.class.getName() + "：getCountMonthProject",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<ScheduleCountVO> queryTZJLScheduleCount(Long guserid) {
		try {
			return sqlSessionTemplate.selectList("selectTZJLScheduleCount" , guserid);
		} catch (Exception e) {
			log.error(StatisticsProjectDaoImpl.class.getName() + "：queryTZJLScheduleCount",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<ScheduleCountVO> queryHHRScheduleCount(Long guserid) {
		try {
			return sqlSessionTemplate.selectList("selectHHRScheduleCount" , guserid);
		} catch (Exception e) {
			log.error(StatisticsProjectDaoImpl.class.getName() + "：queryHHRScheduleCount",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<ScheduleCountVO> queryCEOScheduleCount(Long guserid) {
		try {
			return sqlSessionTemplate.selectList("selectCEOScheduleCount" , guserid);
		} catch (Exception e) {
			log.error(StatisticsProjectDaoImpl.class.getName() + "：queryCEOScheduleCount",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> selectStageSummary(Map<String, Object> map1) {
		try {
			return sqlSessionTemplate.selectList("selectStageSummary" , map1);
		} catch (Exception e) {
			log.error(StatisticsProjectDaoImpl.class.getName() + "：selectStageSummary",e);
			throw new DaoException(e);
		}
	}

}
