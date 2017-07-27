package com.galaxy.im.business.flow.common.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.talk.SopFileBean;
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

	/**
	 * 创建会议排期
	 */
	@Override
	public Long insertMeetingScheduling(MeetingScheduling bean) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.insertMeetingScheduling";
		try{
			long id = sqlSessionTemplate.insert(sqlName,bean);
			return id;
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + ":insertMeetingScheduling",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 上传文件保存
	 */
	@Override
	public Long addSopFile(SopFileBean bean) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.addSopFile";
		try{
			sqlSessionTemplate.insert(sqlName,bean);
			return bean.getId();
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + ":addSopFile",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取上传文件信息
	 */
	@Override
	public List<Map<String, Object>> getSopFileList(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.getSopFileList";
			List<Map<String, Object>> map =sqlSessionTemplate.selectList(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + "：getSopFileList",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 更新上传文件
	 */
	@Override
	public long updateSopFile(SopFileBean bean) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.updateSopFile";
		try{
			sqlSessionTemplate.insert(sqlName,bean);
			return bean.getId();
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + ":updateSopFile",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取项目基本信息
	 */
	@Override
	public SopProjectBean getSopProjectInfo(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.getSopProjectInfo";
		try{
			return sqlSessionTemplate.selectOne(sqlName,paramMap);
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + ":getSopProjectInfo",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 更新代办任务
	 */
	@Override
	public Long updateSopTask(SopTask bean) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.updateSopTask";
		try{
			long id = sqlSessionTemplate.update(sqlName,bean);
			return id;
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + ":updateSopTask",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取人事，财务，法务的任务认领状态信息
	 */
	@Override
	public List<Map<String, Object>> getSopTaskList(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.getSopTaskList";
			List<Map<String, Object>> map =sqlSessionTemplate.selectList(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + "：getSopTaskList",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 更新valid=0
	 */
	@Override
	public int updateValid(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.updateValid";
			return sqlSessionTemplate.update(sqlName,paramMap);
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + "：updateValid",e);
			throw new DaoException(e);
		}
	}
	
}
