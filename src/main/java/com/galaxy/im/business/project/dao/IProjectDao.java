package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IProjectDao extends IBaseDao<ProjectBean,Long>{
	Map<String,Object> getBaseProjectInfo(Long id);
	List<Map<String,Object>> getFinanceHistory(Map<String,Object> paramMap);
	Map<String,Object> getProjectInoIsNull(Long id);
}
