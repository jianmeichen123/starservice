package com.galaxy.im.business.project.dao;

import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IProjectDeliveryDao extends IBaseDao<InformationListdata, Long>{

	QPage queryprodeliverypage(Map<String, Object> paramMap);

	Object selectdelivery(Map<String, Object> paramMap);

}
