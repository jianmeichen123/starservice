package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.FinanceHistoryBean;
import com.galaxy.im.business.project.dao.IFinanceHistoryDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class FinanceHistoryServiceImpl extends BaseServiceImpl<FinanceHistoryBean> implements IFinanceHistoryService{
	private Logger log = LoggerFactory.getLogger(FinanceHistoryServiceImpl.class);
	
	@Autowired
	private IFinanceHistoryDao dao;
	
	
	@Override
	protected IBaseDao<FinanceHistoryBean, Long> getBaseDao() {
		return dao;
	}


	@Override
	public List<Map<String, Object>> getFinanceHistory(Map<String, Object> paramMap) {
		try{
			List<Map<String, Object>> tt = dao.getFinanceHistory(paramMap);
			return tt;
		}catch(Exception e){
			log.error(FinanceHistoryServiceImpl.class.getName() + "_getFinanceHistory",e);
			throw new ServiceException(e);
		}
	}


	@Override
	public Long addFinanceHistory(FinanceHistoryBean historyBean) {
		try{
			return dao.insert(historyBean);
		}catch(Exception e){
			log.error(FinanceHistoryServiceImpl.class.getName() + "_addFinanceHistory",e);
			throw new ServiceException(e);
		}
	}


	@Override
	public Integer updateFinanceHistory(FinanceHistoryBean historyBean) {
		try{
			return dao.updateById(historyBean);
		}catch(Exception e){
			log.error(FinanceHistoryServiceImpl.class.getName() + "_updateFinanceHistory",e);
			throw new ServiceException(e);
		}
	}
	
	

	
	
}
