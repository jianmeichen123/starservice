package com.galaxy.im.business.report.operationtime.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.information.InformationOperationTime;
import com.galaxy.im.common.db.service.IBaseService;

public interface IInformationOperationTimeService extends IBaseService<Test>{

	//获取更新时间
	Map<String, Object> getInformationTime(InformationOperationTime bean);

}
