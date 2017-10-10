package com.galaxy.im.business.project.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.galaxy.im.business.project.service.IProjectService;
import com.galaxy.im.business.project.service.IProjectEquitiesService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;

/**
 * 股权结构
* @Title: ProjectSharesController.java 
* @Package com.galaxy.im.business.project.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author xiaochuang 
* @date 2017年8月24日 上午10:23:20 
* @version V1.0
 */
@Controller
@RequestMapping("/galaxy/projectShares")
public class ProjectEquitiesController {

	private Logger log=LoggerFactory.getLogger(ProjectEquitiesController.class);
	
	@Autowired 
	private IProjectEquitiesService service;
	
	@Autowired 
	private IProjectService iProjectService;
	
	/**
	 * 法人信息
	 */
	@ResponseBody
	@RequestMapping("/selectFRInfo")
	public Object selectFRInfo(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("tid1", 1814);
		paramMap.put("tid2", 1815);
		paramMap.put("tid3", 1816);
		try {
			List<Object> list =  service.selectFRInfo(paramMap);
			resultBean.setEntityList(list);;
			resultBean.setStatus("OK");
		} catch (Exception e) {
			log.error(ProjectEquitiesController.class.getName() + "：selectFRInfo",e);
		}
		return resultBean;
		
	}
	
	/**
	 * 股权结构列表
	 */
	@ResponseBody
	@RequestMapping("selectProjectShares")
	public Object selectProjectShares(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("titleId", 1906);
		paramMap.put("direction", "ASC");
		try {
			QPage page = service.selectProjectShares(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
		} catch (Exception e) {
			log.error(ProjectEquitiesController.class.getName() + "：selectProjectShares",e);
		}
		return resultBean;
	}
	
	/**
	 * 添加/编辑股权结构信息
	 */
	@ResponseBody
	@RequestMapping("addProjectShares")
	public Object addProjectShares(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(StringUtils.isEmpty(String.valueOf(paramMap.get("projectId")))){
				resultBean.setMessage("必要的参数丢失");
				return resultBean;
			}
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			SopProjectBean p = iProjectService.getProjectInfoById(sessionBean.getGuserid());
			//项目创建者用户ID与当前登录人ID是否一样
			if(p != null && sessionBean.getGuserid().doubleValue() != p.getCreateUid().doubleValue()){
				resultBean.setMessage("没有权限修改该项目的股权结构信息!");
				return resultBean;
			}
			try {
				//添加
				if (paramMap.get("id")==null) {
					paramMap.put("titleId", 1906);
					paramMap.put("createId", sessionBean.getGuserid());
					paramMap.put("createdTime",new Date().getTime());
					service.addProjectShares(paramMap);
					resultBean.setStatus("OK");
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
						if(!paramMap.containsKey("remark")){
							paramMap.put("remark", "");
						}
						if(!paramMap.containsKey("shareholderType")){
							paramMap.put("shareholderType", "");
						}
						if(!paramMap.containsKey("sharesRatio")){
							paramMap.put("sharesRatio", "");
						}
						if(!paramMap.containsKey("shareholderNature")){
							paramMap.put("shareholderNature", "");
						}
						if(!paramMap.containsKey("sharesOwner")){
							paramMap.put("sharesOwner", "");
						}
						service.updateProjectShares(paramMap);
						resultBean.setStatus("OK");
					}
				}
		} catch (Exception e) {
			log.error(ProjectEquitiesController.class.getName() + "：addProjectShares",e);
		}
		return resultBean;
	}
	
	/**
	 * 删除股权信息
	 */
	@ResponseBody
	@RequestMapping("deleteProjectShares")
	public Object deleteProjectShares(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		if(StringUtils.isEmpty(String.valueOf(paramMap.get("projectId")))){
			resultBean.setMessage("必要的参数丢失");
			return resultBean;
		}
		SessionBean sessionBean = CUtils.get().getBeanBySession(request);
		SopProjectBean p = iProjectService.getProjectInfoById(sessionBean.getGuserid());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && sessionBean.getGuserid().doubleValue() != p.getCreateUid().doubleValue()){
			resultBean.setMessage("没有权限修删除项目的团队成员信息!");
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
					service.deleteProjectSharesById(paramMap);
					resultBean.setStatus("OK");
				}
			}
		} catch (Exception e) {
			log.error(ProjectEquitiesController.class.getName() + "：deleteProjectShares",e);
		}
		return resultBean;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
