package com.galaxy.im.business.flow.common.dao;

import java.util.Map;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IFlowCommonDao extends IBaseDao<ProjectBean, Long>{
	Map<String,Object> projectStatus(Map<String,Object> paramMap);
	Integer vetoProject(Map<String,Object> paramMap);
	Integer enterNextFlow(Map<String,Object> paramMap);
}
