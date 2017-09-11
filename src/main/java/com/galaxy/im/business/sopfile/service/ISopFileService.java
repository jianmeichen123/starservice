package com.galaxy.im.business.sopfile.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface ISopFileService extends IBaseService<Test>{

	Map<String, Object> getBusinessPlanFile(Map<String, Object> paramMap);

}
