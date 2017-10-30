package com.galaxy.im.business.platform.login.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;
import com.galaxyinternet.model.user.UserLogonHis;

@Repository
public class LoginDaoImpl extends BaseDaoImpl<Test, Long> implements ILoginDao{

	private Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	
	/**
	 * 保存登陆信息
	 */
	@Override
	public void saveLogonHis(UserLogonHis bean) {
		try{
			String sqlName = "com.galaxy.im.business.platform.login.dao.ILoginDao.saveLogonHis";
			sqlSessionTemplate.insert(sqlName,bean);
		}catch(Exception e){
			log.error(LoginDaoImpl.class.getName() + "saveLogonHis",e);
			throw new DaoException(e);
		}
	}

}
