package com.galaxy.im.business.common.sysUpgrade.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.message.SystemMessageUserBean;
import com.galaxy.im.business.common.sysUpgrade.dao.ISysUpgradeDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class SysUpgradeServiceImpl extends BaseServiceImpl<Test> implements ISysUpgradeService{
	private Logger log = LoggerFactory.getLogger(SysUpgradeServiceImpl.class);

	@Autowired
	ISysUpgradeDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 系统升级消息
	 */
	@Override
	public Map<String, Object> getSysUpgradeMessage(Map<String, Object> paramMap) {
		try{
			return dao.getSysUpgradeMessage(paramMap);
		}catch(Exception e){
			log.error(SysUpgradeServiceImpl.class.getName() + "getSysUpgradeMessage",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 手动关闭系统升级消息
	 */
	@Override
	public int closeSysUpgradeMessage(SystemMessageUserBean bean) {
		try{
			return dao.closeSysUpgradeMessage(bean);
		}catch(Exception e){
			log.error(SysUpgradeServiceImpl.class.getName() + "closeSysUpgradeMessage",e);
			throw new ServiceException(e);
		}
	}

}
