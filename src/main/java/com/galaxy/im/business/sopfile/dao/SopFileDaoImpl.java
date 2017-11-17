package com.galaxy.im.business.sopfile.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
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

}
