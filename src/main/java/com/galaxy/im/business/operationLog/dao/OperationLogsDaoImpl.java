package com.galaxy.im.business.operationLog.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.operationLog.OperationLogs;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class OperationLogsDaoImpl extends BaseDaoImpl<OperationLogs, Long> implements IOperationLogsDao{
	private Logger log = LoggerFactory.getLogger(OperationLogsDaoImpl.class);
	
	public OperationLogsDaoImpl(){
		super.setLogger(log);
	}
	
	/**
	 * 保存操作日志
	 */
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

	/**
	 * 获取操作日志列表
	 */
	@Override
	public QPage getOperationLogList(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
				contentList = sqlSessionTemplate.selectList(getSqlName("getOperationLogList"),getPageMap(paramMap));
				total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("countOperationLogList"),getPageMap(paramMap)));
			}    
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(OperationLogsDaoImpl.class.getName() + "getOperationLogList",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 操作日志个数
	 */
	@Override
	public int getOperationLogsCount(Map<String, Object> paramMap) {
		try{
			int total = 0;
			if(paramMap!=null){
				total = sqlSessionTemplate.selectOne(getSqlName("countOperationLogList"),getPageMap(paramMap));
			}    
			return total;
		}catch(Exception e){
			log.error(OperationLogsDaoImpl.class.getName() + "getOperationLogsCount",e);
			throw new DaoException(e);
		}
	}

}
