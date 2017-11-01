package com.galaxy.im.business.rili.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
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
			return sqlSessionTemplate.selectOne(getSqlName("selectOtherScheduleById"),map);
		}catch(Exception e){
			log.error(getSqlName("selectOtherScheduleById"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取拜访对象名称
	 */
	@Override
	public ScheduleInfo selectVisitNameById(Long id) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("selectVisitNameById"),id);
		}catch(Exception e){
			log.error(getSqlName("selectVisitNameById"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 查询列表
	 */
	@Override
	public List<ScheduleInfo> selectLists(ScheduleInfo toQ) {
		try{
			Map<String, Object> params = BeanUtils.toMap(toQ);
			return sqlSessionTemplate.selectList(getSqlName("selectLists"),params);
		}catch(Exception e){
			log.error(getSqlName("selectLists"),e);
			throw new DaoException(e);
		}
	}
	
	/**
	 * 搜索拜访对象/其他日程
	 */
	@Override
	public List<ScheduleInfo> getList(Map<String, Object> map) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("getList"),map);
		}catch(Exception e){
			log.error(getSqlName("getList"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 日程消息未读个数
	 */
	@Override
	public Long queryProjectScheduleCount(Long uid) {
		long total = 0L;
		try{
			total = CUtils.get().object2Long(sqlSessionTemplate.selectOne(getSqlName("queryProjectScheduleCount"),uid));
		}catch(Exception e){
			throw new DaoException(String.format("根据项目状态统计数据量出错！语句:%s" ,  getSqlName("queryProjectScheduleCount")), e);
		}
		return total;
	}
	


}
