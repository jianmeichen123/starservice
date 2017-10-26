package com.galaxy.im.business.rili.dao;

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

	/**
	 * 判断当天日程是否超过20条
	 */
	@Override
	public List<Map<String, Object>> getCountSchedule(Map<String, Object> map) {
		try{
			List<Map<String,Object>> list = null;
			if(map!=null){
				list = sqlSessionTemplate.selectList(getSqlName("getCountSchedule"),map);
			}    
			return list;
		}catch(Exception e){
			log.error(getSqlName("getCountSchedule"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 删除日程
	 */
	@Override
	public int delCallonById(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.update(getSqlName("delCallonById"),paramMap);
		}catch(Exception e){
			log.error(getSqlName("delCallonById"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 根据id查询详情
	 */
	@Override
	public Map<String, Object> selectOtherScheduleById(Map<String, Object> map) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("selectById"),map);
		}catch(Exception e){
			log.error(getSqlName("selectById"),e);
			throw new DaoException(e);
		}
	}
	


}
