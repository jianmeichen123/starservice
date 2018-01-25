package com.galaxy.im.business.idea.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.idea.IdeaBean;
import com.galaxy.im.business.idea.dao.IIdeaDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class IdeaServiceImpl extends BaseServiceImpl<Test> implements IIdeaService{
	private Logger log = LoggerFactory.getLogger(IdeaServiceImpl.class);
	@Autowired
	IIdeaDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	@Override
	public IdeaBean queryIdeaById(Long id) {
		try{
			return dao.queryIdeaById(id);
		}catch(Exception e){
			log.error(IdeaServiceImpl.class.getName() + ":queryIdeaById",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateIdeaById(Map<String, Object> paramMap) {
		try{
			return dao.updateIdeaById(paramMap);
		}catch(Exception e){
			log.error(IdeaServiceImpl.class.getName() + ":updateIdeaById",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int insertIdea(Map<String, Object> paramMap) {
		try{
			return dao.insertIdea(paramMap);
		}catch(Exception e){
			log.error(IdeaServiceImpl.class.getName() + ":insertIdea",e);
			throw new ServiceException(e);
		}
	}

}
