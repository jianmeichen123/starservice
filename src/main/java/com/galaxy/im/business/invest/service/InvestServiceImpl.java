package com.galaxy.im.business.invest.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.invest.InvestBean;
import com.galaxy.im.business.invest.dao.IInvestDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
@Service
public class InvestServiceImpl extends BaseServiceImpl<InvestBean> implements IInvestService{
	private Logger log = LoggerFactory.getLogger(InvestServiceImpl.class);
	
	@Autowired
	private IInvestDao dao;
	@Override
	protected IBaseDao<InvestBean, Long> getBaseDao() {
		return dao;
	}
	@Override
	public QPage selectInvestList(Map<String, Object> paramMap){
		try{
			return dao.selectInvestList(paramMap);
		}catch(Exception e){
			log.error(InvestServiceImpl.class.getName() + "getInvestList",e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public int deleteByIdAndPid(Map<String, Object> paramMap) {
		try{
			return dao.deleteByIdAndPid(paramMap);
		}catch(Exception e){
			log.error(InvestServiceImpl.class.getName() + "deleteByIdAndPid",e);
			throw new ServiceException(e);
		}
	}

}
