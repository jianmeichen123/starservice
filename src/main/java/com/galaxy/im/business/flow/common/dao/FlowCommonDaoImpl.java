package com.galaxy.im.business.flow.common.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class FlowCommonDaoImpl extends BaseDaoImpl<ProjectBean, Long> implements IFlowCommonDao{
	private Logger log = LoggerFactory.getLogger(FlowCommonDaoImpl.class);
	
	public FlowCommonDaoImpl(){
		super.setLogger(log);
	}

	/**
	 * 取得当前项目进度
	 */
	@Override
	public Map<String, Object> projectStatus(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.projectStatus";
		try{
			return sqlSessionTemplate.selectOne(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 否决项目
	 */
	@Override
	public Integer vetoProject(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.vetoProject";
		try{
			return sqlSessionTemplate.update(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 进入到下一阶段
	 */
	@Override
	public Integer enterNextFlow(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.enterNextFlow";
		try{
			return sqlSessionTemplate.update(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 创建代办任务
	 */
	@Override
	public Long insertsopTask(SopTask bean) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.insertsopTask";
		try{
			long id = sqlSessionTemplate.insert(sqlName,bean);
			return id;
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + ":insertsopTask",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取最新会议记录信息
	 */
	@Override
	public Map<String, Object> getLatestMeetingRecordInfo(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.getLatestMeetingRecordInfo";
		try{
			return sqlSessionTemplate.selectOne(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取最新上传文件信息
	 */
	@Override
	public Map<String, Object> getLatestSopFileInfo(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.getLatestSopFileInfo";
		try{
			return sqlSessionTemplate.selectOne(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}
	
	
}
