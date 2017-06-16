package com.galaxy.im.business.flow.common.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.business.callon.service.CallonDetailServiceImpl;
import com.galaxy.im.business.flow.common.dao.IFlowCommonDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class FlowCommonServiceImpl extends BaseServiceImpl<ProjectBean> implements IFlowCommonService{
	private Logger log = LoggerFactory.getLogger(FlowCommonServiceImpl.class);
	
	@Autowired
	private IFlowCommonDao dao;

	@Override
	protected IBaseDao<ProjectBean, Long> getBaseDao() {
		return dao;
	}

	@Override
	public Map<String, Object> projectStatus(Map<String, Object> paramMap) {
		try{
			return dao.projectStatus(paramMap);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":projectStatus",e);
			throw new ServiceException(e);
		}
	}
	
	
	
}
