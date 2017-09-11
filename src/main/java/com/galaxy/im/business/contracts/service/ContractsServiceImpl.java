package com.galaxy.im.business.contracts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.contracts.ContractsBean;
import com.galaxy.im.business.contracts.dao.IContractsDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class ContractsServiceImpl extends BaseServiceImpl<ContractsBean> implements IContractsService{
	//private final Logger log = LoggerFactory.getLogger(ContractsServiceImpl.class);
	
	@Autowired
	private IContractsDao dao;

	@Override
	protected IBaseDao<ContractsBean, Long> getBaseDao() {
		return dao;
	}

}
