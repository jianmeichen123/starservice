package com.galaxy.im.business.schedule.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ScheduleDaoImpl extends BaseDaoImpl<ScheduleInfo, Long> implements IScheduleDao{

	private Logger log = LoggerFactory.getLogger(ScheduleDaoImpl.class);

	/**
	 * 日程时间冲突list
	 */
	@Override
	public List<Map<String, Object>> getCTSchedule(Map<String, Object> map) {
		try{
			List<Map<String,Object>> list = null;
			if(map!=null){
				list = sqlSessionTemplate.selectList(getSqlName("getCTSchedule"),map);
			}    
			return list;
		}catch(Exception e){
			log.error(getSqlName("getCTSchedule"),e);
			throw new DaoException(e);
		}
	}

}
