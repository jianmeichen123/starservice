package com.galaxy.im.business.report.operationtime.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.information.InformationOperationTime;
import com.galaxy.im.business.report.operationtime.service.IInformationOperationTimeService;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/information")
public class InformationOperationTimeController {
	private Logger log = LoggerFactory.getLogger(InformationOperationTimeController.class);
	
	@Autowired
	IInformationOperationTimeService service;
	
	/**
	 * 7大报告的更新时间
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("getOperateTime")
	@ResponseBody
	public Object getOperateTime(@RequestBody InformationOperationTime bean){
		ResultBean<Object> resultBean = new ResultBean<>();
		resultBean.setStatus("error");
		try{
			if(bean == null || bean.getProjectId() == null || StringUtils.isEmpty(bean.getReflect())){
				resultBean.setMessage("参数丢失");
				return resultBean;
			}
			Map<String,Object> map = service.getInformationTime(bean);
			resultBean.setMap(map);
			resultBean.setStatus("OK");
			return resultBean;
		}catch(Exception e){
			log.error(InformationOperationTimeController.class.getName() + ":getOperateTime",e);
		}
		return resultBean;
	}

}
