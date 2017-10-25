package com.galaxy.im.business.rili.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.schedule.ScheduleShared;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ShareDaoImpl extends BaseDaoImpl<Test, Long> implements IShareDao{

	private Logger log = LoggerFactory.getLogger(ShareDaoImpl.class);
	
	/**
	 * 删除共享人
	 */
	@Override
	public int delSharedUser(Map<String, Object> map) {
		try{
			String sqlName = "com.galaxy.im.business.rili.dao.IShareDao.delSharedUser";
			return sqlSessionTemplate.delete(sqlName,map);
		}catch(Exception e){
			log.error(ShareDaoImpl.class.getName() + "delSharedUser",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 批量保存共享人
	 */
	@Override
	public int saveSharedUsers(List<ScheduleShared> list) {
		try{
			String sqlName = "com.galaxy.im.business.rili.dao.IShareDao.saveSharedUsers";
			return sqlSessionTemplate.insert(sqlName,list);
		}catch(Exception e){
			log.error(ShareDaoImpl.class.getName() + "saveSharedUsers",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 共享给自己的列表
	 */
	@Override
	public List<Map<String, Object>> querySharedUsers(Map<String, Object> map) {
		try{
			String sqlName = "com.galaxy.im.business.rili.dao.IShareDao.querySharedUsers";
			return sqlSessionTemplate.selectList(sqlName,map);
		}catch(Exception e){
			log.error(ShareDaoImpl.class.getName() + "querySharedUsers",e);
			throw new DaoException(e);
		}
	}

}
