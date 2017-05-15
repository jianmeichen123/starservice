package com.galaxy.im.business.callon.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class CallonDaoImpl extends BaseDaoImpl<ScheduleInfo, Long> implements ICallonDao{
	private Logger log = LoggerFactory.getLogger(CallonDaoImpl.class);
	
	public CallonDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public QPage selectCallonList(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
				contentList = sqlSessionTemplate.selectList(getSqlName("selectCallonList"),getPageMap(paramMap));
				total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("countCallonList"),getPageMap(paramMap)));
			}
			
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(getSqlName("selectCallonList"),e);
			throw new DaoException(e);
		}
	}

	@Override
	public int delCallonById(Long id) {
		try{
			return sqlSessionTemplate.update(getSqlName("delCallonById"),id);
		}catch(Exception e){
			log.error(getSqlName("delCallonById"),e);
			throw new DaoException(e);
		}
	}

	@Override
	public int callonEnableEditOrDel(Long id) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("callonEnableEditOrDel"), id);
		}catch(Exception e){
			log.error(getSqlName("callonEnableEditOrDel"),e);
			throw new DaoException(e);
		}
	}
	
	
	
	

	
	
}
