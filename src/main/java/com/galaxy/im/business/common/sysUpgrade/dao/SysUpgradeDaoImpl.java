package com.galaxy.im.business.common.sysUpgrade.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.message.SystemMessageUserBean;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class SysUpgradeDaoImpl extends BaseDaoImpl<Test, Long> implements ISysUpgradeDao{

	private Logger log = LoggerFactory.getLogger(SysUpgradeDaoImpl.class);

	/**
	 * 手动关闭系统升级消息
	 */
	@Override
	public int closeSysUpgradeMessage(SystemMessageUserBean bean) {
		try{
			String sqlName = "com.galaxy.im.business.common.sysUpgrade.dao.ISysUpgradeDao.closeSysUpgradeMessage";
			return sqlSessionTemplate.insert(sqlName,bean);
		}catch(Exception e){
			log.error(SysUpgradeDaoImpl.class.getName() + "closeSysUpgradeMessage",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 系统消息提示
	 */
	@Override
	public Map<String, Object> getSysUpgradeMessage(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.common.sysUpgrade.dao.ISysUpgradeDao.getSysUpgradeMessage";
			return sqlSessionTemplate.selectOne(sqlName,paramMap);
		}catch(Exception e){
			log.error(SysUpgradeDaoImpl.class.getName() + "getSysUpgradeMessage",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取提示信息
	 */
	@Override
	public Map<String, Object> getSysMessage(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.common.sysUpgrade.dao.ISysUpgradeDao.getSysMessage";
			return sqlSessionTemplate.selectOne(sqlName,paramMap);
		}catch(Exception e){
			log.error(SysUpgradeDaoImpl.class.getName() + "getSysMessage",e);
			throw new DaoException(e);
		}
	}

}
