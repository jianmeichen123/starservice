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
}
