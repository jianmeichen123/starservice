package com.galaxy.im.business.flow.interview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.common.ResultBean;

/**
 * 接触访谈
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/interview")
public class InterviewController {
	
	/**
	 * 判断项目能否被否决/被通过
	 * 依据：所处阶段中该项目的会议记录是否存在已否决/已通过的会议
	 * 前端处理：如果存在，则“否决”/“通过”的按钮变亮
	 * @return
	 */
	@RequestMapping("projectOperateStatus")
	@ResponseBody
	public Object projectOperateStatus(){
		ResultBean<Object> result = new ResultBean<Object>();
		
		return result;
	}
	
	/**
	 * 否决项目
	 * @param paramString
	 * @return
	 */
	@RequestMapping("votedown")
	@ResponseBody
	public Object votedown(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			
			
			//project_status
			
		}catch(Exception e){
		}
		return result;
	}

}
