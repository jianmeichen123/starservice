package com.galaxy.im.business.flow.ceoreview.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.ceoreview.dao.ICeoreviewDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class CeoreviewServiceImpl extends BaseServiceImpl<Test> implements ICeoreviewService{

	private Logger log = LoggerFactory.getLogger(CeoreviewServiceImpl.class);
	@Autowired
	private ICeoreviewDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	@Override
	public  int saveRovalScheduling(Map<String, Object> paramMap) {
		try {
			return dao.insertRovalScheduling(paramMap);
			
		} catch (Exception e) {
			log.error(CeoreviewServiceImpl.class.getName() + ":saveRovalScheduling",e);
			throw new ServiceException(e);
		}
		
	}

	@Override
	public int updateCeoScheduling(Map<String, Object> paramMap) {
		try {
			return dao.updateCeoScheduling(paramMap);
			
		} catch (Exception e) {
			log.error(CeoreviewServiceImpl.class.getName() + ":updateCeoScheduling",e);
			throw new ServiceException(e);
		}
	}

	
}
