package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.project.GeneralProjecttVO;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.ProjectTransfer;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.db.PageRequest;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectService extends IBaseService<ProjectBean>{
	//获取项目基础信息
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
	
	//全部
	GeneralProjecttVO queryPageList(ProjectBo projectBo, PageRequest pageable);
	//跟进中个数
	Long queryProjectgjzCount(ProjectBo projectBo);
	//投后运营个数
	Long queryProjectthyyCount(ProjectBo projectBo);
	//否决个数
	Long queryProjectfjCount(ProjectBo projectBo);
	//项目总个数
	Long queryProjectSumCount(ProjectBo projectBo);
	//项目基本信息
	Map<String, Object> selectBaseProjectInfo(Map<String, Object> paramMap);
	//通过id查看信息
	InformationResult findResultInfoById(Map<String, Object> hashmap);
	//插入信息到全息报告表
	int addInformationResult(List<InformationResult> list);
	//更新信息到全息报告表
	int updateInformationResult(InformationResult result);
	//删除项目承揽人
	int delProjectUserIds(Map<String, Object> map);
	//获取项目来源关联的名称titleId
	String findInputTitleId(Map<String, Object> hashmap);
	//登陆用户匹配承揽人
	List<Map<String, Object>> getMatchingInfo(Map<String, Object> m);
	//保存，移交、指派的记录信息
	int saveProjectTransfer(ProjectTransfer bean);
	
}
