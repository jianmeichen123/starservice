package com.galaxy.im.business.operationLog.dao;

import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.common.db.IBaseDao;

public interface IOperationLogsDao extends IBaseDao<OperationLogs, Long>{

	long saveOperationLog(OperationLogs operationLog);

}
