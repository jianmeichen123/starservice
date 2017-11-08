package com.galaxy.im.business.contracts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.contracts.ContractsBean;
import com.galaxy.im.business.contracts.dao.IContractsDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class ContractsServiceImpl extends BaseServiceImpl<ContractsBean> implements IContractsService{
	private final Logger log = LoggerFactory.getLogger(ContractsServiceImpl.class);
	
	@Autowired
	private IContractsDao dao;

	@Override
	protected IBaseDao<ContractsBean, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 查询联系人是否存在
	 */
	@Override
	public ContractsBean selectPersonByName(ContractsBean bean) {
		try{
			return dao.selectPersonByName(bean);
		}catch(Exception e){
			log.error(ContractsServiceImpl.class.getName() + "selectPersonByName",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 保存联系人
	 */
	@Override
	public Long savePerson(ContractsBean bean) {
		try{
			return dao.savePerson(bean);
		}catch(Exception e){
			log.error(ContractsServiceImpl.class.getName() + "savePerson",e);
			throw new ServiceException(e);
		}
	}

}
