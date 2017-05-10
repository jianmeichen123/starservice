package com.galaxy.im.business.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.DepartBean;
import com.galaxy.im.bean.DepartBeanVO;
import com.galaxy.im.bean.Test;
import com.galaxy.im.business.example.dao.IDepartDao;
import com.galaxy.im.business.example.dao.ITestDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.DaoException;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class DepartServiceImpl extends BaseServiceImpl<DepartBean> implements IDepartService{
	private Logger log = LoggerFactory.getLogger(DepartServiceImpl.class);
	private String className = DepartServiceImpl.class.getName();
	
	@Autowired
	private IDepartDao dao;
	
	@Autowired
	private ITestDao testDao;
	
	
	@Override
	protected IBaseDao<DepartBean, Long> getBaseDao() {
		return dao;
	}

	public DepartBean getDepartById(DepartBeanVO vo) {
		DepartBean bean = null;
		try{
			bean = dao.selectOne(vo);
		}catch(Exception e){
			log.error(className + "_getDepartById",e);
			throw new ServiceException(e);
		}
		return bean;
	}

	@Override
	public void saveTest(Test testBean) {
		try{
			testDao.insert(testBean);
			
			int bb = 100/0;
			
			
		}catch (Exception e){
			log.error(className + "_saveTest",e);
			throw new ServiceException(e);
		}
		
	}
	
	
	
	
	
	

	
}
