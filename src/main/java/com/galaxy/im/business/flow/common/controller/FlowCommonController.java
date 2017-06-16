package com.galaxy.im.business.flow.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;

/**
 * 各流程阶段-共用的一些接口
 * 
 * @author liyanqiao
 */
@Controller
@RequestMapping("/flow/common")
public class FlowCommonController {
	@Autowired
	private IFlowCommonService service;
	
	/**
	 * 判断项目是否已经被否决或推进到下一阶段
	 */
	@RequestMapping("projectStatus")
	@ResponseBody
	public Object projectStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> resultMap = service.projectStatus(paramMap);
			if(resultMap!=null && !resultMap.isEmpty()){
				result.setStatus("OK");
				result.setMap(resultMap);
			}
		}catch(Exception e){
		}
		return result;
	}

}
