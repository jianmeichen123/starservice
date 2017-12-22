package com.galaxy.im.business.sopfile.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.dao.FlowCommonDaoImpl;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class SopFileDaoImpl extends BaseDaoImpl<Test, Long> implements ISopFileDao{
	private Logger log = LoggerFactory.getLogger(SopFileDaoImpl.class);
	
	@Override
	public Map<String, Object> getSopFileInfo(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.getSopFileInfo";
			Map<String, Object> map =sqlSessionTemplate.selectOne(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(SopFileDaoImpl.class.getName() + "getSopFileInfo",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> searchappFileList(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.searchappFileList";
			List<Map<String, Object>> map =sqlSessionTemplate.selectList(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(SopFileDaoImpl.class.getName() + "searchappFileList",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getSopFileList(SopFileBean sopfile) {
		try{
			Map<String, Object> params = BeanUtils.toMap(sopfile);
			String sqlName = "com.galaxy.im.business.sopfile.dao.ISopFileDao.getSopFileList";
			return sqlSessionTemplate.selectList(sqlName,params);
		}catch(Exception e){
			log.error(SopFileDaoImpl.class.getName() + "getSopFileList",e);
			throw new DaoException(e);
		}
	}
	
	/**
	 * 逻辑删除会议纪要附件
	 */
	@Override
	public int delPostMeetingFile(SopFileBean sopfile) {
		try{
			Map<String, Object> params = BeanUtils.toMap(sopfile);
			String sqlName = "com.galaxy.im.business.sopfile.dao.ISopFileDao.delPostMeetingFile";
			return sqlSessionTemplate.update(sqlName,params);
		}catch(Exception e){
			log.error(SopFileDaoImpl.class.getName() + "delPostMeetingFile",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 文件名已存在的个数
	 */
	@Override
	public List<String> getFileNameList(Map<String, String> nameMap) {
		try{
			String sqlName = "com.galaxy.im.business.sopfile.dao.ISopFileDao.getFileNameList";
			return sqlSessionTemplate.selectList(sqlName,nameMap);
		}catch(Exception e){
			log.error(SopFileDaoImpl.class.getName() + "getFileNameList",e);
			throw new DaoException(e);
		}
	}

	@Override
	public long insertHistory(Map<String, Object> map) {
		String sqlName = "com.galaxy.im.business.sopfile.dao.ISopFileDao.insertHistory";
		try{
			return sqlSessionTemplate.insert(sqlName,map);
		}catch(Exception e){
			log.error(FlowCommonDaoImpl.class.getName() + ":addSopFile",e);
			throw new DaoException(e);
		}
	}

	@Override
	public int updateSopFile(SopFileBean bean) {
		try{
			Map<String, Object> params = BeanUtils.toMap(bean);
			String sqlName = "com.galaxy.im.business.sopfile.dao.ISopFileDao.updateSopFile";
			return sqlSessionTemplate.update(sqlName,params);
		}catch(Exception e){
			log.error(SopFileDaoImpl.class.getName() + "updateSopFile",e);
			throw new DaoException(e);
		}
	}

}
