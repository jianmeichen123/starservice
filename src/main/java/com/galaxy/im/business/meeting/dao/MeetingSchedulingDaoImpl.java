package com.galaxy.im.business.meeting.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.meeting.MeetingSchedulingBo;
import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class MeetingSchedulingDaoImpl extends BaseDaoImpl<Test, Long> implements IMeetingSchedulingDao {

	private Logger log = LoggerFactory.getLogger(MeetingSchedulingDaoImpl.class);
	
	/**
	 * 会议排期列表（搜索，筛选）
	 */
	@Override
	public QPage queryMescheduling(MeetingSchedulingBo query) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(query!=null){
				Map<String, Object> params = BeanUtils.toMap(query);
				String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.queryMescheduling";
				String sqlName1 = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.queryMeschedulingCount";
				contentList = sqlSessionTemplate.selectList(sqlName,getPageMap(params));
				total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(sqlName1,getPageMap(params)));
			}    
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(MeetingSchedulingDaoImpl.class.getName() + "queryMescheduling",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 排期列表的排队数目
	 */
	@Override
	public Long selectpdCount(Map<String, Object> ms) {
		try {
			String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.selectpdCount";
			Long count= sqlSessionTemplate.selectOne(sqlName,ms);
			return count;
		} catch (Exception e) {
			throw new DaoException(String.format("查询排队数出错！语句：%s", getSqlName("selectpdCount")),e);
		}
	}

	/**
	 * 排期列表的排队数目
	 */
	@Override
	public Long selectltpdCount(Map<String, Object> ms) {
		try {
			String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.selectltpdCount";
			Long count= sqlSessionTemplate.selectOne(sqlName,ms);
			return count;
		} catch (Exception e) {
			log.error(MeetingSchedulingDaoImpl.class.getName() + "selectltpdCount",e);
			throw new DaoException(e);
		}
	}
	
	/**
	 * 个数
	 */
	@Override
	public Long queryCount(MeetingSchedulingBo query) {
		long total = 0L;
		try{
			String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.queryMeschedulingCount";
			Map<String, Object> params = BeanUtils.toMap(query);
			total = CUtils.get().object2Long(sqlSessionTemplate.selectOne(sqlName,params));
			return total;
		}catch(Exception e){
			log.error(MeetingSchedulingDaoImpl.class.getName() + "queryCount",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 当是秘书登录时产生的待排期会议的总个数
	 */
	@Override
	public Long selectdpqCount(MeetingSchedulingBo mBo) {
		Long count = 0L;
		try {
			if (mBo!=null) {
			Map<String, Object> params = BeanUtils.toMap(mBo);
			String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.queryMeschedulingCount";
			count = CUtils.get().object2Long(sqlSessionTemplate.selectOne(sqlName,params));
			}
			return count;
		} catch (Exception e) {
			log.error(MeetingSchedulingDaoImpl.class.getName() + "queryMeschedulingCount",e);
			throw new DaoException(e);
		}
		
	}

	/**
	 * 查询月排期列表
	 */
	@Override
	public List<MeetingSchedulingBo> selectMonthScheduling(MeetingSchedulingBo query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.selectMonthScheduling";
			return sqlSessionTemplate.selectList(sqlName,params);
		} catch (Exception e) {
			log.error(MeetingSchedulingDaoImpl.class.getName() + "selectMonthScheduling",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 查询月排期总数
	 */
	@Override
	public Long selectMonthSchedulingCount(MeetingSchedulingBo query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.selectMonthSchedulingCount";
			Long count= sqlSessionTemplate.selectOne(sqlName,params);
			return count;
		} catch (Exception e) {
			log.error(MeetingSchedulingDaoImpl.class.getName() + "selectMonthSchedulingCount",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 查询当日事项
	 */
	@Override
	public List<MeetingSchedulingBo> selectDayScheduling(MeetingSchedulingBo bop) {
		try {
			Map<String, Object> params = BeanUtils.toMap(bop);
			String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.selectDayScheduling";
			return sqlSessionTemplate.selectList(sqlName,params);
		} catch (Exception e) {
			log.error(MeetingSchedulingDaoImpl.class.getName() + "selectDayScheduling",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 更新
	 */
	@Override
	public int updateMeetingScheduling(MeetingScheduling sch) {
		try {
			Map<String, Object> params = BeanUtils.toMap(sch);
			String sqlName = "com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao.updateMeetingScheduling";
			return sqlSessionTemplate.update(sqlName,params);
		} catch (Exception e) {
			log.error(MeetingSchedulingDaoImpl.class.getName() + "updateMeetingScheduling",e);
			throw new DaoException(e);
		}
	}

}
