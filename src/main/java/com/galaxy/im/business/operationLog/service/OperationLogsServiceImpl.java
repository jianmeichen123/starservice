package com.galaxy.im.business.operationLog.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.business.operationLog.dao.IOperationLogsDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class OperationLogsServiceImpl extends BaseServiceImpl<OperationLogs> implements IOperationLogsService{
	private Logger log = LoggerFactory.getLogger(OperationLogsServiceImpl.class);
	
	@Autowired
	IOperationLogsDao dao;
	@Override
	protected IBaseDao<OperationLogs, Long> getBaseDao() {
		return dao;
	}
	
	@Override
	public long saveOperationLog(OperationLogs operationLog) {
		return dao.saveOperationLog(operationLog);
	}

	/**
	 * 获取操作日志信息列表
	 */
	@Override
	public QPage getOperationLogList(Map<String, Object> paramMap) {
		try{
			return dao.getOperationLogList(paramMap);
		}catch(Exception e){
			log.error(OperationLogsServiceImpl.class.getName() + "getOperationLogList",e);
			throw new ServiceException(e);
		}
	}

}
