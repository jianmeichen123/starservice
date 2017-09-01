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

	@Override
	public List<Dict> selectByParentCode(String parentCode) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectByParentCode"), parentCode);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("selectByParentCode")), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 融资状态取全息报告字典表里数据
	 */
	@Override
	public List<Map<String, Object>> getFinanceStatusList(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getFinanceStatusList"),paramMap);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getFinanceStatusList")), e);
			throw new DaoException(e);
		}
		
	}

	@Override
	public List<Map<String, Object>> getShareholderNatureList(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getShareholderNatureList"),paramMap);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getShareholderNatureList")), e);
			throw new DaoException(e);
		}
		
	}

	@Override
	public List<Map<String, Object>> getShareholderTypeList(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getShareholderTypeList"),paramMap);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getShareholderTypeList")), e);
			throw new DaoException(e);
		}
		
	}

	@Override
	public List<Map<String, Object>> getTeamUserPosition(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getTeamUserPosition"),paramMap);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getTeamUserPosition")), e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getfinanceUnitList(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getfinanceUnitList"),paramMap);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getfinanceUnitList")), e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getfinancingStatusList(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getfinancingStatusList"),paramMap);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getfinancingStatusList")), e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getfinancingStockList(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("getfinancingStockList"),paramMap);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName("getfinancingStockList")), e);
			throw new DaoException(e);
		}
	}
	
	

}
