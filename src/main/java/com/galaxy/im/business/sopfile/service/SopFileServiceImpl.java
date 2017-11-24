package com.galaxy.im.business.sopfile.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.sopfile.dao.ISopFileDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class SopFileServiceImpl extends BaseServiceImpl<Test> implements ISopFileService{
	private Logger log = LoggerFactory.getLogger(SopFileServiceImpl.class);

	
	@Autowired
	ISopFileDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	@Override
	public Map<String, Object> getSopFileInfo(Map<String, Object> paramMap) {
		try{
			return dao.getSopFileInfo(paramMap);
		}catch(Exception e){
			log.error(SopFileServiceImpl.class.getName() + "getSopFileInfo",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Map<String, Object>> searchappFileList(Map<String, Object> paramMap) {
		try{
			return dao.searchappFileList(paramMap);
		}catch(Exception e){
			log.error(SopFileServiceImpl.class.getName() + "searchappFileList",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getSopFileList(SopFileBean sopfile) {
		try{
			return dao.getSopFileList(sopfile);
		}catch(Exception e){
			log.error(SopFileServiceImpl.class.getName() + "getSopFileList",e);
			throw new ServiceException(e);
		}
	}

}
