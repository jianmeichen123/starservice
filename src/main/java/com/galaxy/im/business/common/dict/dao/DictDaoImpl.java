package com.galaxy.im.business.common.dict.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.common.Dict;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class DictDaoImpl extends BaseDaoImpl<Dict, Long> implements IDictDao{
	private Logger log = LoggerFactory.getLogger(DictDaoImpl.class);
	
	public DictDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public List<Map<String, Object>> selectCallonFilter() {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectCallonFilter"));	//.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT), params);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("selectCallonFilter")), e);
			throw new DaoException(e);
		}
		
	}

	@Override
	public List<Map<String, Object>> selectResultFilter(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectResultFilter"),paramMap);	
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("selectResultFilter")), e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> selectReasonFilter(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectReasonFilter"),paramMap);	
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("selectReasonFilter")), e);
			throw new DaoException(e);
		}
	}
	
	

}
