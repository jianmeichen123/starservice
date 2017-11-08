package com.galaxy.im.business.contracts.service;

import com.galaxy.im.bean.contracts.ContractsBean;
import com.galaxy.im.common.db.service.IBaseService;

public interface IContractsService extends IBaseService<ContractsBean>{

	ContractsBean selectPersonByName(ContractsBean bean);

	Long savePerson(ContractsBean bean);

}
