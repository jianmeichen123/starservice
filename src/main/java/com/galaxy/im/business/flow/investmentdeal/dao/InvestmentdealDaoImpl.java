package com.galaxy.im.business.flow.investmentdeal.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class InvestmentdealDaoImpl extends BaseDaoImpl<Test, Long> implements IInvestmentdealDao{
	private Logger log = LoggerFactory.getLogger(InvestmentdealDaoImpl.class);
	
	public InvestmentdealDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public int updateInvestmentdeal(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.investmentdeal.dao.IInvestmentdealDao.updateInvestmentdeal";
			return sqlSessionTemplate.update(sqlName, paramMap);
		}catch(Exception e){
			log.error(InvestmentdealDaoImpl.class.getName() + "：updateInvestmentdeal",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Map<String, Object> businessnegotiation(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.investmentdeal.dao.IInvestmentdealDao.businessnegotiation";
			return sqlSessionTemplate.selectOne(sqlName, paramMap);
		}catch(Exception e){
			log.error(InvestmentdealDaoImpl.class.getName() + "：businessnegotiation",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 项目是否存在通过／否决的会议记录
	 */
	@Override
	public List<Map<String, Object>> hasPassMeeting(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.internalreview.dao.IInternalreviewDao.hasPassMeeting";
		try{
			return sqlSessionTemplate.selectList(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> projectResult(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.investmentdeal.dao.IInvestmentdealDao.projectResult";
		try{
			return sqlSessionTemplate.selectList(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}
}
