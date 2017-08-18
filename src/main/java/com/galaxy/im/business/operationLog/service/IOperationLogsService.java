package com.galaxy.im.business.operationLog.service;

import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.common.db.service.IBaseService;

public interface IOperationLogsService extends IBaseService<OperationLogs>{

	long saveOperationLog(OperationLogs operationLog);

	

}