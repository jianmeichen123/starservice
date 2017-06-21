package com.galaxy.im.business.talk.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.talk.ProjectTalkBean;
import com.galaxy.im.business.talk.dao.IProjectTalkDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class ProjectTalkServiceImpl extends BaseServiceImpl<ProjectTalkBean> implements IProjectTalkService{
	private Logger log = LoggerFactory.getLogger(ProjectTalkServiceImpl.class);
	
	@Autowired
	IProjectTalkDao dao;

	@Override
	protected IBaseDao<ProjectTalkBean, Long> getBaseDao() {
		return dao;
	}

	@Override
	public QPage getProjectTalkList(Map<String, Object> paramMap) {
		try{
			return dao.getProjectTalkList(paramMap);
		}catch(Exception e){
			log.error(ProjectTalkServiceImpl.class.getName() + "_getProjectTalkList",e);
			throw new ServiceException(e);
		}
	}
}
