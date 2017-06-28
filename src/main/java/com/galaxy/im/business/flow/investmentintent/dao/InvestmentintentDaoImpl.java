package com.galaxy.im.business.flow.investmentintent.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class InvestmentintentDaoImpl extends BaseDaoImpl<Test, Long> implements IInvestmentintentDao{
	private Logger log = LoggerFactory.getLogger(InvestmentintentDaoImpl.class);
	
	public InvestmentintentDaoImpl(){
		super.setLogger(log);
	}

	/**
	 * 获取项目上传的所有文件的和个数
	 */
	@Override
	public List<Map<String, Object>> investmentOperateStatus(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.investmentintent.dao.IInvestmentintentDao.investmentOperateStatus";
			List<Map<String, Object>> map =sqlSessionTemplate.selectList(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(InvestmentintentDaoImpl.class.getName() + "：investmentOperateStatus",e);
			throw new DaoException(e);
		}
	}
	
}
