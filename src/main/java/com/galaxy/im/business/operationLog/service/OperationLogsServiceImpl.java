package com.galaxy.im.business.operationLog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.business.operationLog.dao.IOperationLogsDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class OperationLogsServiceImpl extends BaseServiceImpl<OperationLogs> implements IOperationLogsService{

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

}
