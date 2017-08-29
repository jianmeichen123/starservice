package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.business.project.dao.InformationListdataDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
@Service
public class InformationListdataServiceImpl extends BaseServiceImpl<InformationListdata> implements InformationListdataService{
	private static final Logger log = LoggerFactory.getLogger(InformationListdataServiceImpl.class);
	
	@Autowired
	private InformationListdataDao dao;
	
	@Override
	protected IBaseDao<InformationListdata, Long> getBaseDao() {
		return dao;
	}

	@Override
	public List<Object> selectFRInfo(Map<String, Object> paramMap) {
		try {
			return dao.selectFRInfo(paramMap);
		} catch (Exception e) {
			log.error(InformationListdataServiceImpl.class.getName() + "selectFRInfo",e);
			throw new ServiceException(e);
		}
		
	}

	@Override
	public QPage selectProjectShares(Map<String, Object> paramMap) {
		try {
			return dao.selectProjectShares(paramMap);
		} catch (Exception e) {
			log.error(InformationListdataServiceImpl.class.getName() + "selectProjectShares",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void addProjectShares(Map<String, Object> paramMap) {
		try {
			dao.addProjectShares(paramMap);
		} catch (Exception e) {
			log.error(InformationListdataServiceImpl.class.getName() + "addProjectShares",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public InformationListdata selectInfoById(Map<String, Object> paramMap) {
		try {
			return dao.selectInfoById(paramMap);
		} catch (Exception e) {
			log.error(InformationListdataServiceImpl.class.getName() + "selectInfoById",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateProjectShares(Map<String, Object> paramMap) {
		try {
			dao.updateProjectShares(paramMap);
		} catch (Exception e) {
			log.error(InformationListdataServiceImpl.class.getName() + "updateProjectShares",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteProjectSharesById(Map<String, Object> paramMap) {
		try {
			dao.deleteProjectSharesById(paramMap);
		} catch (Exception e) {
			log.error(InformationListdataServiceImpl.class.getName() + "deleteProjectSharesById",e);
			throw new ServiceException(e);
		}
	}
	
	
}
