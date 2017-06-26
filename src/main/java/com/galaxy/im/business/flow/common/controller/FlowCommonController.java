package com.galaxy.im.business.flow.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 各流程阶段-共用的一些接口
 * 
 * @author liyanqiao
 */
@Controller
@RequestMapping("/flow/common")
public class FlowCommonController {
	
	/**
	 * 公用接口：上传/更新文件的接口
	 * @param paramString
	 * @return
	 */
	@RequestMapping("uploadSopFile")
	@ResponseBody
	public Object uploadSopFile(@RequestBody String paramString){
		
		return paramString;
		
	}
}