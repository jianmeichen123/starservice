package com.galaxy.im.business.meeting.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.meeting.MeetingSchedulingBo;
import com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class MeetingSchedulingServiceImpl extends BaseServiceImpl<Test> implements IMeetingSchedulingService {
	
	private Logger log = LoggerFactory.getLogger(MeetingSchedulingServiceImpl.class);
	
	@Autowired
	IMeetingSchedulingDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 会议排期列表（搜索，筛选）
	 */
	@Override
	public QPage queryMescheduling(MeetingSchedulingBo query) {
		try{
			return dao.queryMescheduling(query);
		}catch(Exception e){
			log.error(MeetingSchedulingServiceImpl.class.getName() + "queryMescheduling",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 排期列表的排队数目
	 */
	@Override
	public Long selectpdCount(Map<String, Object> ms) {
		try{
			return dao.selectpdCount(ms);
		}catch(Exception e){
			log.error(MeetingSchedulingServiceImpl.class.getName() + "selectpdCount",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 排期列表的排队数目
	 */
	@Override
	public Long selectltpdCount(Map<String, Object> ms) {
		try{
			return dao.selectltpdCount(ms);
		}catch(Exception e){
			log.error(MeetingSchedulingServiceImpl.class.getName() + "selectltpdCount",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Long queryCountscheduleStatusd(MeetingSchedulingBo query) {
		return dao.queryCount(query);
	}

	@Override
	public Long selectdpqCount(MeetingSchedulingBo mBo) {
		return dao.selectdpqCount(mBo);
	}

	@Override
	public List<MeetingSchedulingBo> selectMonthScheduling(MeetingSchedulingBo query) {
		return dao.selectMonthScheduling(query);
	}

	@Override
	public Long selectMonthSchedulingCount(MeetingSchedulingBo query) {
		return dao.selectMonthSchedulingCount(query);
	}

	@Override
	public List<MeetingSchedulingBo> selectDayScheduling(MeetingSchedulingBo bop) {
		return dao.selectDayScheduling(bop);
	}

}
