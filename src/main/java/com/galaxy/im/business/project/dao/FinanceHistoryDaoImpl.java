package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.FinanceHistoryBean;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class FinanceHistoryDaoImpl extends BaseDaoImpl<FinanceHistoryBean, Long> implements IFinanceHistoryDao{
	private Logger log = LoggerFactory.getLogger(FinanceHistoryDaoImpl.class);
	
	public FinanceHistoryDaoImpl(){
		super.setLogger(log);
	}
	
	/**
	 * 融资历史列表
	 */
	@Override
	public List<Map<String, Object>> getFinanceHistory(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("getFinanceHistory"),paramMap);
			
		}catch(Exception e){
			log.error(FinanceHistoryDaoImpl.class.getName() + "_getFinanceHistory",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 融资历史详情
	 */
	@Override
	public Map<String, Object> getFinanceHistoryDetails(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("getFinanceHistoryDetails"),paramMap);
			
		}catch(Exception e){
			log.error(FinanceHistoryDaoImpl.class.getName() + "getFinanceHistoryDetails",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 添加融资历史
	 */
	@Override
	public Long addFinanceHistory(Map<String, Object> paramMap) {
		try {
			sqlSessionTemplate.insert(getSqlName("addFinanceHistory"), paramMap);
			return CUtils.get().object2Long(paramMap.get("id"));
		} catch (Exception e) {
			log.error(FinanceHistoryDaoImpl.class.getName()+ "addFinanceHistory", e);
			throw new DaoException(e);
		}
	}

	/**
	 * 编辑融资历史
	 */
	@Override
	public Long updateFinanceHistory(Map<String, Object> paramMap) {
		try {
			sqlSessionTemplate.insert(getSqlName("updateFinanceHistory"), paramMap);
			return CUtils.get().object2Long(paramMap.get("id"));
		} catch (Exception e) {
			log.error(FinanceHistoryDaoImpl.class.getName()+ "updateFinanceHistory", e);
			throw new DaoException(e);
		}
	}
	
}
