package com.galaxy.im.business.flow.investmentoperate.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class InvestmentoperateDaoImpl extends BaseDaoImpl<Test, Long> implements IInvestmentoperateDao{
	private Logger log = LoggerFactory.getLogger(InvestmentoperateDaoImpl.class);
	
	public InvestmentoperateDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public int getMeetingCount(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.investmentoperate.dao.IInvestmentoperateDao.getMeetingCount";
		try{
			int total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(sqlName,paramMap));
			return total;
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}
}
