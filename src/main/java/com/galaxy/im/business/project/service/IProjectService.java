package com.galaxy.im.business.project.service;

import java.util.List;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectDetailsBean;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectService extends IBaseService<ProjectBean>{
	ProjectDetailsBean getBaseProjectInfo(Long id);

}
