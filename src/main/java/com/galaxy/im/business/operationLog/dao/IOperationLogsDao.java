package com.galaxy.im.business.operationLog.dao;

import java.util.Map;

import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IOperationLogsDao extends IBaseDao<OperationLogs, Long>{

	long saveOperationLog(OperationLogs operationLog);

	QPage getOperationLogList(Map<String, Object> paramMap);

	Long getOperationLogsCount(Map<String, Object> paramMap);

}
