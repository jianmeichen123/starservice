package com.galaxy.im.business.project.service;

import java.util.Map;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectDeliveryService extends IBaseService<InformationListdata>{

	QPage queryprodeliverypage(Map<String, Object> paramMap);

	Object selectdelivery(Map<String, Object> paramMap);

}
