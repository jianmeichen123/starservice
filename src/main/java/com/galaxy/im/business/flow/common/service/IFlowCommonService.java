package com.galaxy.im.business.flow.common.service;

import java.util.Map;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.common.db.service.IBaseService;

public interface IFlowCommonService extends IBaseService<ProjectBean>{
	Map<String, Object> projectStatus(Map<String, Object> paramMap);

}
