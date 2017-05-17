package com.galaxy.im.business.contracts.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.contracts.ContractsBean;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class ContractsDaoImapl extends BaseDaoImpl<ContractsBean, Long> implements IContractsDao{
	private final Logger log = LoggerFactory.getLogger(ContractsDaoImapl.class);
	
	public ContractsDaoImapl(){
		super.setLogger(log);
	}

}
