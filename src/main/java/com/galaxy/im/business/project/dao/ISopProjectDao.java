package com.galaxy.im.business.project.dao;

import java.util.List;

import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.Page;
import com.galaxy.im.common.db.PageRequest;

public interface ISopProjectDao extends IBaseDao<ProjectBo,Long>{

	Page<SopProjectBean> queryPageList(ProjectBo query, PageRequest pageable);
	
	Long queryCount(ProjectBo query);
	
	List<String> getProjectIdArePeople(ProjectBo projectBo);

}
