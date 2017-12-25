package com.galaxy.im.business.soptask.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.soptask.SopTaskRecord;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.dao.IFlowCommonDao;
import com.galaxy.im.business.operationLog.dao.IOperationLogsDao;
import com.galaxy.im.business.sopfile.dao.ISopFileDao;
import com.galaxy.im.business.soptask.dao.ISopTaskDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class SopTaskServiceImpl extends BaseServiceImpl<SopTask> implements ISopTaskService{
	private Logger log = LoggerFactory.getLogger(SopTaskServiceImpl.class);

	@Autowired
	ISopTaskDao dao;
	@Autowired
	IOperationLogsDao logDao;
	@Autowired
	IFlowCommonDao fDao;
	@Autowired
	ISopFileDao sfDao;
	
	@Override
	protected IBaseDao<SopTask, Long> getBaseDao() {
		return dao;
	}

	//待办任务列表
	@Override
	public QPage taskListByRole(Map<String, Object> paramMap) {
		try{
			return dao.taskListByRole(paramMap);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public long getDepId(Long id) {
		try{
			return dao.getDepId(id);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//待办任务详情
	@Override
	public Map<String, Object> taskInfo(Map<String, Object> paramMap) {
		try{
			return dao.taskInfo(paramMap);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//认领
	@Override
	public int applyTask(SopTask sopTask) {
		try{
			return dao.applyTask(sopTask);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//指派/移交
	@Override
	public int taskTransfer(SopTaskRecord sopTaskRecord) {
		try{
			return dao.taskTransfer(sopTaskRecord);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//修改待办任务信息
	@Override
	public int updateTask(SopTask sopTask) {
		try{
			return dao.updateTask(sopTask);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//是否上传报告
	@Override
	public SopFileBean isUpload(SopFileBean sopFileBean) {
		try{
			return dao.isUpload(sopFileBean);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//更新文件信息
	@Override
	public long updateFile(SopFileBean sopFileBean) {
		try{
			return fDao.updateSopFile(sopFileBean);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//防止重复移交
	@Override
	public SopTaskRecord selectRecord(SopTaskRecord sopTaskRecord) {
		try{
			return dao.selectRecord(sopTaskRecord);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//查询总数
	@Override
	public int selectCount(Map<String, Object> paramMap) {
		try{
			return dao.selectCount(paramMap);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//查询时否有操作日志
	@Override
	public Long getOperationLogs(Map<String, Object> paramMap) {
		try{
			return logDao.getOperationLogsCount(paramMap);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "getOperationLogs",e);
			throw new ServiceException(e);
		}
	}

	//放弃代办任务的更新
	@Override
	public int updateSopFile(SopFileBean bean) {
		try{
			return sfDao.updateSopFile(bean);
		}catch(Exception e){
			log.error(SopTaskServiceImpl.class.getName() + "updateSopFile",e);
			throw new ServiceException(e);
		}
	}
	
}
