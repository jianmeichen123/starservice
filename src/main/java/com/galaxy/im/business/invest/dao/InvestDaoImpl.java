package com.galaxy.im.business.invest.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.invest.InvestBean;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;
@Repository
public class InvestDaoImpl extends BaseDaoImpl<InvestBean, Long>implements IInvestDao{

	private Logger log = LoggerFactory.getLogger(InvestDaoImpl.class);
	
	public InvestDaoImpl(){
		super.setLogger(log);
		
	}
	
	@Override
	public QPage selectInvestList(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
				contentList = sqlSessionTemplate.selectList(getSqlName("selectInvestList"),getPageMap(paramMap));
				total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("countInvestList"),getPageMap(paramMap)));
			}    
			
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(getSqlName("selectInvestList"),e);
			throw new DaoException(e);
		}
	}
}
