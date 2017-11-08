package com.galaxy.im.business.contracts.dao;

import com.galaxy.im.bean.contracts.ContractsBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IContractsDao extends IBaseDao<ContractsBean, Long>{

	ContractsBean selectPersonByName(ContractsBean bean);

	Long savePerson(ContractsBean bean);

}
