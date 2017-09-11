package com.galaxy.im.business.sopfile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.business.sopfile.service.ISopFileService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/sopFile")
public class SopFileController {

	private Logger log = LoggerFactory.getLogger(SopFileController.class);
	
	@Autowired
	ISopFileService service;
	
	/**
	 * 获取商业计划书
	 * @param request
	 * @param response
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getBusinessPlanFile")
	@ResponseBody
	public Object getBusinessPlanFile(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<ProjectBean> resultBean = new ResultBean<ProjectBean>();
		resultBean.setStatus("error");
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(!paramMap.containsKey("projectId")){
				resultBean.setMessage("传入的projectId为空");
			}
			
		}catch(Exception e){
			log.error(SopFileController.class.getName() + "getBusinessPlanFile",e);
		}
		return resultBean;
		
	}
}
