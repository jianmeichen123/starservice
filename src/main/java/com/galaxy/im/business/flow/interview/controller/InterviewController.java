package com.galaxy.im.business.flow.interview.controller;

import org.springframework.stereotype.Controller;
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
	
	@RequestMapping("test")
	@ResponseBody
	public Object test(){
		ResultBean<Object> result = new ResultBean<Object>();
		
		return result;
	}

}
