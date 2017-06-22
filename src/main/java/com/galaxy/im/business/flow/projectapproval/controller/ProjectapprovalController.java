package com.galaxy.im.business.flow.projectapproval.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.flow.projectapproval.service.IProjectapprovalService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;

/**
 * 立项会
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/projectapproval")
public class ProjectapprovalController {
	
	@Autowired
	IProjectapprovalService service;
	//@Autowired
	//private IFlowCommonService fcService;
	
	/**
	 * 判断项目能否被否决/被通过-操作按钮状态
	 * 依据：所处阶段中该项目的会议记录是否存在已否决/须一条“闪投”或“投资”或“转向”结果的会议记录
	 * 前端处理：如果存在，则“否决项目”/“进入会后商务谈判”的按钮变亮
	 * 
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 * 	entity -> pass 布尔型  存在“闪投”或“投资”或“转向”的会议：true
	 *  entity -> veto 布尔型  存在否决的会议：true
	 */
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> m = service.approvalOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			result.setStatus("OK");
		}catch(Exception e){
		}
		return result;
	}

}
