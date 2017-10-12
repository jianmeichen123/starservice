package com.galaxy.im.business.project.dao;

import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.Page;
import com.galaxy.im.common.db.PageRequest;

public interface ISopProjectDao extends IBaseDao<ProjectBo,Long>{

	Page<SopProjectBean> queryPageList(ProjectBo query, PageRequest pageable);
	Long queryCount(ProjectBo query);
	
	/*Page<SopProjectBean> querygjzProjectList(ProjectBo query, PageRequest pageable);

	Page<SopProjectBean> querythyyList(ProjectBo query, PageRequest pageable);

	Page<SopProjectBean> queryfjList(ProjectBo query, PageRequest pageable);

	Long queryCountgjz(ProjectBo query);

	Long queryCountthyy(ProjectBo query);

	Long queryCountfj(ProjectBo query);*/


}
