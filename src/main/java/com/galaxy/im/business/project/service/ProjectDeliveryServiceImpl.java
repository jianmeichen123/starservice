package com.galaxy.im.business.project.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.business.project.dao.IProjectDeliveryDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
@Service
public class ProjectDeliveryServiceImpl extends BaseServiceImpl<InformationListdata> implements IProjectDeliveryService{
	private static final Logger log = LoggerFactory.getLogger(ProjectDeliveryServiceImpl.class);
	
	@Autowired
	private IProjectDeliveryDao dao;
	
	@Override
	protected IBaseDao<InformationListdata, Long> getBaseDao() {
		return dao;
	}

	@Override
	public QPage queryprodeliverypage(Map<String, Object> paramMap) {
		try {
			return dao.queryprodeliverypage(paramMap);
		} catch (Exception e) {
			log.error(ProjectDeliveryServiceImpl.class.getName() + "queryprodeliverypage",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Object selectdelivery(Map<String, Object> paramMap) {
		try {
			return dao.selectdelivery(paramMap);
		} catch (Exception e) {
			log.error(ProjectDeliveryServiceImpl.class.getName() + "selectdelivery",e);
			throw new ServiceException(e);
		}
	}
	
}
