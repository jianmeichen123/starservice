package com.galaxy.im.business.callon.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.schedule.ScheduleDetailBean;
import com.galaxy.im.bean.schedule.ScheduleDetailBeanVo;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.SqlId;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class CallonDetailDaoImpl extends BaseDaoImpl<ScheduleDetailBean, Long> implements ICallonDetailDao{

private Logger log = LoggerFactory.getLogger(CallonDaoImpl.class);
	
	public CallonDetailDaoImpl(){
		super.setLogger(log);
	}

	/**
	 * 获取字典信息list
	 */
	@Override
	public List<Map<String, Object>> getSignificanceDictList() {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getSignificanceDictList"));
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getSignificanceDictList")), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取提醒通知list
	 */
	@Override
	public List<Map<String, Object>> getScheduleDictList() {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getScheduleDictList"));
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getScheduleDictList")), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取历史访谈记录个数
	 */
	@Override
	public long getTalkHistoryCounts(ScheduleDetailBeanVo detail) {
		try {
			Map<String, Object> params = BeanUtils.toMap(detail);
			return sqlSessionTemplate.selectOne(getSqlName("getTalkHistoryCounts"), params);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getTalkHistoryCounts")), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 拜访详情
	 */
	@Override
	public List<ScheduleDetailBean> getQueryById(long id) {
		try {
			return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT_BY_ID), id);
		} catch (Exception e) {
			log.error(String.format("根据ID查询对象出错！语句：%s", getSqlName(SqlId.SQL_SELECT_BY_ID)), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 运营会议历史个数
	 */
	@Override
	public long getPosMeetingCount(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName("getPosMeetingCount"), paramMap);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getPosMeetingCount")), e);
			throw new DaoException(e);
		}
	}
}
