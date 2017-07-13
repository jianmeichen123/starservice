package com.galaxy.im.business.flow.stockequity.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IStockequityService extends IBaseService<Test>{

	Map<String, Object> operateStatus(Map<String, Object> paramMap);


}
