package com.galaxy.im.business.flow.investmentPolicy.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class InvestmentPolicyDaoImpl extends BaseDaoImpl<Test, Long> implements IInvestmentPolicyDao{
	private Logger log = LoggerFactory.getLogger(InvestmentPolicyDaoImpl.class);
	
	public InvestmentPolicyDaoImpl(){
		super.setLogger(log);
	}
	
	/**
	 * 获取项目上传的所有文件的和个数
	 */
	@Override
	public List<Map<String, Object>> investmentpolicy(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.investmentPolicy.dao.IInvestmentPolicyDao.investmentpolicy";
			List<Map<String, Object>> map =sqlSessionTemplate.selectList(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(InvestmentPolicyDaoImpl.class.getName() + "：investmentpolicy",e);
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
	
	/**
	 * 获取项目在“会后商务谈判”阶段的结论
	 */
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
