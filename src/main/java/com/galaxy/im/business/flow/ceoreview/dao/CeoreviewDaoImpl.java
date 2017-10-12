package com.galaxy.im.business.flow.ceoreview.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class CeoreviewDaoImpl extends BaseDaoImpl<Test, Long> implements ICeoreviewDao{
	private Logger log = LoggerFactory.getLogger(CeoreviewDaoImpl.class);
	
	public CeoreviewDaoImpl(){
		super.setLogger(log);
	}
	
	/**
	 * 项目是否存在通过／否决的会议记录
	 */
	@Override
	public List<Map<String, Object>> hasPassMeeting(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.ceoreview.dao.ICeoreviewDao.hasPassMeeting";
		try{
			return sqlSessionTemplate.selectList(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	@Override
	public int insertRovalScheduling(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.ceoreview.dao.ICeoreviewDao.insertRovalScheduling";
		try{
			return sqlSessionTemplate.insert(sqlName, paramMap);
		}catch(Exception e){
			log.error(String.format("插入对象出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	@Override
	public int updateCeoScheduling(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.ceoreview.dao.ICeoreviewDao.updateCeoScheduling";
		try{
			return sqlSessionTemplate.insert(sqlName, paramMap);
		}catch(Exception e){
			log.error(String.format("修改对象出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}
}
