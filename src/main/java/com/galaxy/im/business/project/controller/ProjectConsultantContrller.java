package com.galaxy.im.business.project.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.business.project.service.IProjectConsultantService;
import com.galaxy.im.business.project.service.IProjectService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;
/**
 * 团队成员
* @Title: ProjectConsultantContrller.java 
* @Package com.galaxy.im.business.project.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author xiaochuang 
* @date 2017年8月31日 下午6:03:25 
* @version V1.0
 */
@Controller
@RequestMapping("/galaxy/ProjectConsultant")
public class ProjectConsultantContrller {
	private Logger log=LoggerFactory.getLogger(ProjectConsultantContrller.class);
	
	@Autowired
	private IProjectConsultantService service;
	
	@Autowired 
	private IProjectService iProjectService;
	
	/**
	 * 团队成员列表
	 */
	@ResponseBody
	@RequestMapping("queryProjectPerson")
	public Object queryProjectPerson(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("titleId", 1303);
		paramMap.put("code", "team-members");
		try {
			QPage page = service.queryProjectPerson(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
			
		} catch (Exception e) {
			log.error(ProjectConsultantContrller.class.getName() + "：queryProjectPerson",e);
		}
		return resultBean;
	}
	
	/**
	 * 团队成员详情
	 */
	@ResponseBody
	@RequestMapping("projectPersonInfo")
	public Object projectPersonInfo(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		Map<String, Object> map = new HashMap<>();
		paramMap.put("code1", "study-experience");
		paramMap.put("code2", "work-experience");
		paramMap.put("code3", "entrepreneurial-experience");
		try {
			//个人信息
			Object object = service.queryPersonInfo(paramMap);
			//学习经历
			List<Object> lis1 = service.queryStudyExperience(paramMap);
			//工作经历
			List<Object> list2 = service.queryWorkExperience(paramMap);
			//创业经历
			List<Object> list3 = service.queryEntrepreneurialExperience(paramMap);
			
			map.put("personInfo", object);
			map.put("studyExperience", lis1);
			map.put("workExperience", list2);
			map.put("entrepreneurialExperience", list3);
			
			resultBean.setMap(map);
			resultBean.setStatus("OK");
			
		} catch (Exception e) {
			log.error(ProjectConsultantContrller.class.getName() + "：teamDetailsInfo",e);
		}
		return resultBean;
	}
	
	/**
	 * 添加/编辑团队成员
	 */
	@ResponseBody
	@RequestMapping("editProjectPerson")
	public Object editProjectPerson(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("personName", CUtils.get().object2String(paramMap.get("personName")).trim());
		paramMap.put("code", "team-members");
		if(paramMap.get("projectId")==null){
			resultBean.setMessage("必要的参数丢失");
			return resultBean;
		}
		SessionBean sessionBean = CUtils.get().getBeanBySession(request);
		SopProjectBean p = iProjectService.getProjectInfoById(CUtils.get().object2Long(paramMap.get("projectId")));
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && sessionBean.getGuserid().doubleValue() != p.getCreateUid().doubleValue()){
			resultBean.setMessage("没有权限修改该项目的团队成员信息!");
			return resultBean;
		}
		try {
			if (paramMap.containsKey("otherPersonDuties")) {
				paramMap.put("personDuties", paramMap.get("personDuties")+"-"+paramMap.get("otherPersonDuties"));
			}
			//添加
			if (paramMap.get("id")==null) {
				paramMap.put("titleId", 1303);
				paramMap.put("createId", sessionBean.getGuserid());
				paramMap.put("createdTime",new Date().getTime());
				int count = service.addProjectPerson(paramMap);
				if (count>0) {
					resultBean.setStatus("OK");
				}else {
					resultBean.setStatus("ERROR");
				}
			}else {
				InformationListdata bean = service.selectInfoById(paramMap);
				//验证内容是否存在
				if (bean==null) {
					resultBean.setMessage("当前信息不存在或已被删除,请重新操作!");
					return resultBean;
				}else{
					//更新
					paramMap.put("updateId", sessionBean.getGuserid());
					paramMap.put("updateTime",new Date().getTime());
					int count =service.updateProjectPerson(paramMap);
					if (count>0) {
						resultBean.setStatus("OK");
					}else {
						resultBean.setStatus("ERROR");
					}
				}
			}
		} catch (Exception e) {
			log.error(ProjectConsultantContrller.class.getName() + "：editProjectPerson",e);
		}
		return resultBean;
	}
	
	/**
	 * 删除团队信息
	 */
	@ResponseBody
	@RequestMapping("deleteProjectPerson")
	public Object deleteProjectPerson(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		if(paramMap.get("projectId")==null){
			resultBean.setMessage("必要的参数丢失");
			return resultBean;
		}
		SessionBean sessionBean = CUtils.get().getBeanBySession(request);
		SopProjectBean p = iProjectService.getProjectInfoById(CUtils.get().object2Long(paramMap.get("projectId")));
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && sessionBean.getGuserid().doubleValue() != p.getCreateUid().doubleValue()){
			resultBean.setMessage("没有权限删除该项目的团队成员信息!");
			return resultBean;
		}
		try {
			if (paramMap.get("id")!=null) {
				InformationListdata bean = service.selectInfoById(paramMap);
				//验证内容是否已经被更改过
				if (bean==null) {
					resultBean.setMessage("当前信息不存在或已被删除,请重新操作!");
					return resultBean;
				}else{
					int count = service.deleteProjectPersonById(paramMap);
					if (count>0) {
						resultBean.setStatus("OK");
					}else {
						resultBean.setStatus("ERROR");
					}
				}
			}
		} catch (Exception e) {
			log.error(ProjectEquitiesController.class.getName() + "：deleteProjectPerson",e);
		}
		return resultBean;
	}
	
	
	
	
	
	
	
	
	
}
