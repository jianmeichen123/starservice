package com.galaxy.im.business.platform.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.platform.login.dao.ILoginDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
import com.galaxyinternet.model.user.UserLogonHis;

@Service
public class LoginServiceImpl extends BaseServiceImpl<Test> implements ILoginService{

	private Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Autowired
	ILoginDao dao;
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	/**
	 * 保存登陆历史
	 */
	@Override
	public void saveLogonHis(UserLogonHis bean) {
		try{
			dao.saveLogonHis(bean);
		}catch(Exception e){
			log.error(LoginServiceImpl.class.getName() + "saveLogonHis",e);
			throw new ServiceException(e);
		}
	}

}
