package com.galaxy.im.business.flow.projectapproval.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ProjectapprovalDaoImpl extends BaseDaoImpl<Test, Long> implements IProjectapprovalDao{
	private Logger log = LoggerFactory.getLogger(ProjectapprovalDaoImpl.class);
	
	public ProjectapprovalDaoImpl(){
		super.setLogger(log);
	}

	/**
	 * 项目相关会议的结论和个数
	 */
	@Override
	public List<Map<String, Object>> approvalOperateStatus(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.projectapproval.dao.IProjectapprovalDao.approvalOperateStatus";
			List<Map<String, Object>> map =sqlSessionTemplate.selectList(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(ProjectapprovalDaoImpl.class.getName() + "：approvalOperateStatus",e);
			throw new DaoException(e);
		}
	}
	
	/**
	 * 更新立项会排期的会议结果和排期结果调整为已否决
	 */
	@Override
	public int updateMeetingScheduling(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.projectapproval.dao.IProjectapprovalDao.updateMeetingScheduling";
			return sqlSessionTemplate.update(sqlName,paramMap);
		}catch(Exception e){
			log.error(ProjectapprovalDaoImpl.class.getName() + "：updateMeetingScheduling",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 
	 */
	@Override
	public int getMeetingCount(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.projectapproval.dao.IProjectapprovalDao.getMeetingCount";
			return CUtils.get().object2Integer(sqlSessionTemplate.selectOne(sqlName,paramMap));
		}catch(Exception e){
			log.error(ProjectapprovalDaoImpl.class.getName() + "：getMeetingCount",e);
			throw new DaoException(e);
		}
	
	}
}
