package com.galaxy.im.business.flow.businessnegotiation.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IBusinessnegotiationService extends IBaseService<Test>{

	Map<String, Object> businessOperateStatus(Map<String, Object> paramMap);

	boolean updateProjectStatus(Map<String, Object> paramMap);

}
