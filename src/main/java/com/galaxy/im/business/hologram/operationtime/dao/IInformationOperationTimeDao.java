package com.galaxy.im.business.hologram.operationtime.dao;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.information.InformationOperationTime;
import com.galaxy.im.common.db.IBaseDao;

public interface IInformationOperationTimeDao extends IBaseDao<Test, Long>{

	InformationOperationTime getInformationTime(InformationOperationTime bean);

}
