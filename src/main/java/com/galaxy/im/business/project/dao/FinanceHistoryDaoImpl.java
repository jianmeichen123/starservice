package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.FinanceHistoryBean;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class FinanceHistoryDaoImpl extends BaseDaoImpl<FinanceHistoryBean, Long> implements IFinanceHistoryDao{
	private Logger log = LoggerFactory.getLogger(FinanceHistoryDaoImpl.class);
	
	public FinanceHistoryDaoImpl(){
		super.setLogger(log);
	}
	
	@Override
	public List<Map<String, Object>> getFinanceHistory(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("getFinanceHistory"),paramMap);
			
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_getFinanceHistory",e);
			throw new DaoException(e);
		}
	}
	
}
