package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IProjectDao extends IBaseDao<ProjectBean,Long>{
	Map<String,Object> getBaseProjectInfo(Long id);
	Map<String,Object> getProjectInoIsNull(Long id);
	Integer projectIsYJZ(Long projectId);
	Integer projectIsShow(Long projectId);
	
	//获取项目信息
	List<SopProjectBean> getSopProjectList(SopProjectBean bean);
	//创建项目
	long saveProject(SopProjectBean bean);
	//删除项目下的所有投资方
	int deleteInvestById(SopProjectBean bean);
	//更新项目下的所有投资方的投资形式
	int updateInvestById(SopProjectBean bean);
	//编辑项目
	int updateProject(SopProjectBean bean);
	
}
