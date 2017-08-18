package com.galaxy.im.business.operationLog.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class OperationLogsDaoImpl extends BaseDaoImpl<OperationLogs, Long> implements IOperationLogsDao{
	private Logger log = LoggerFactory.getLogger(OperationLogsDaoImpl.class);
	
	public OperationLogsDaoImpl(){
		super.setLogger(log);
	}
	
	@Override
	public long saveOperationLog(OperationLogs operationLog) {
		try {
			sqlSessionTemplate.insert(getSqlName("saveOperationLog"), operationLog);
			return operationLog.getId();
		} catch (Exception e) {
			log.error(String.format("添加对象出错！语句：%s", getSqlName("saveOperationLog")), e);
			throw new DaoException(e);
		}
	}

}
