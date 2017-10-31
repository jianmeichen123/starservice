package com.galaxy.im.business.platform.login.dao;

import java.util.Map;

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

	/**
	 * 客户端，用户，日期是否登陆
	 */
	@Override
	public UserLogonHis findUserLogonHis(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.platform.login.dao.ILoginDao.findUserLogonHis";
			return sqlSessionTemplate.selectOne(sqlName,paramMap);
		}catch(Exception e){
			log.error(LoginDaoImpl.class.getName() + "findUserLogonHis",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 更新
	 */
	@Override
	public void updateLogonHis(UserLogonHis his) {
		try{
			String sqlName = "com.galaxy.im.business.platform.login.dao.ILoginDao.updateLogonHis";
			sqlSessionTemplate.update(sqlName,his);
		}catch(Exception e){
			log.error(LoginDaoImpl.class.getName() + "updateLogonHis",e);
			throw new DaoException(e);
		}
	}

}
