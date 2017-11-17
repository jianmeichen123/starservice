package com.galaxy.im.business.operationLog.service;

import java.util.Map;

import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IOperationLogsService extends IBaseService<OperationLogs>{

	long saveOperationLog(OperationLogs operationLog);

	QPage getOperationLogList(Map<String, Object> paramMap);

	

}