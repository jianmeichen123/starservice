package com.galaxy.im.business.flow.duediligence.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class DuediligenceDaoImpl extends BaseDaoImpl<Test, Long> implements IDuediligenceDao{
	private Logger log = LoggerFactory.getLogger(DuediligenceDaoImpl.class);
	
	public DuediligenceDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public List<Map<String, Object>> duediligenceOperateStatus(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.duediligence.dao.IDuediligenceDao.duediligenceOperateStatus";
			List<Map<String, Object>> map =sqlSessionTemplate.selectList(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(DuediligenceDaoImpl.class.getName() + "：duediligenceOperateStatus",e);
			throw new DaoException(e);
		}
	}
}
