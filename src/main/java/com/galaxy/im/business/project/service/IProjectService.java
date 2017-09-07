package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.GeneralProjecttVO;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.db.PageRequest;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectService extends IBaseService<ProjectBean>{
	Map<String,Object> getBaseProjectInfo(Long id);
	Map<String,Object> getProjectInoIsNull(Long id);
	Integer projectIsYJZ(Long projectId);
	Integer projectIsShow(Long projectId);
	
	//获取项目信息
	List<SopProjectBean> getSopProjectList(SopProjectBean bean);
	//创建项目
	long saveProject(SopProjectBean bean);
	//编辑项目
	int updateProject(SopProjectBean bean);
	//删除项目下的所有投资方
	int deleteInvestById(SopProjectBean bean);
	//更新项目下的所有投资方的投资形式
	int updateInvestById(SopProjectBean bean);
	//根据项目id查看项目信息
	SopProjectBean getProjectInfoById(Long id);
	//项目是否处于接触访谈阶段
	int projectIsInterview(Long id);
	
	//跟进中项目列表
	GeneralProjecttVO querygjzProjectList(ProjectBo projectBo, PageRequest pageRequest);
	//投后运营项目列表
	GeneralProjecttVO querythyyList(ProjectBo projectBo, PageRequest pageRequest);
	//否决项目列表
	GeneralProjecttVO queryfjList(ProjectBo projectBo, PageRequest pageRequest);
	//全部
	GeneralProjecttVO queryPageList(ProjectBo projectBo, PageRequest pageable);
	Long queryProjectgjzCount(ProjectBo projectBo);
	Long queryProjectthyyCount(ProjectBo projectBo);
	Long queryProjectfjCount(ProjectBo projectBo);
	Map<String, Object> selectBaseProjectInfo(Map<String, Object> paramMap);
	int updateProjects(Map<String, Object> hashmap);
	InformationResult findResultInfoById(Map<String, Object> hashmap);
}
