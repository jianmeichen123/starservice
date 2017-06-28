package com.galaxy.im.business.flow.investmentintent.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.investmentintent.service.IInvestmentintentService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

/**
 * 投资意向书
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/investmentintent")
public class InvestmentintentController{
	
	@Autowired
	IInvestmentintentService service;
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 进入尽职调查的操作按钮状态
	 * 依据：需要上传完成“投资意向书”
	 * 前端处理：如果已经上传，则进入尽职调查的按钮变亮
	 * 
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 *  entity -> pass 布尔型  已上传投资意向书：true
	 */
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> m = service.investmentOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			
			//项目最后上传的文件信息
			paramMap.put("fileWorkType", StaticConst.FILE_WORKTYPE_5);
			Map<String,Object> map = fcService.getLatestSopFileInfo(paramMap);
			if(CUtils.get().mapIsNotEmpty(map)){
				result.setMap(map);
			}
			result.setStatus("OK");
		}catch(Exception e){
		}
		return result;
	}

}
