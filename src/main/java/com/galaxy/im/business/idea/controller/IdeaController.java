package com.galaxy.im.business.idea.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.idea.service.IIdeaService;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/idea")
public class IdeaController {
	private Logger log = LoggerFactory.getLogger(IdeaController.class);

	@Autowired
	IIdeaService service;
	
	/**
	 * 创意列表，搜索
	 * @param paramString
	 * @return
	 */
	@RequestMapping("search")
	@ResponseBody
	public Object search(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			
		}catch(Exception e){
			log.error(IdeaController.class.getName() + "search",e);
		}
		return resultBean;
	}
}
