package com.galaxy.im.business.project.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.project.service.IProjectDeliveryService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;

/**
 * 交割前事项
* @Title: ProjectBeforeDelivery.java 
* @Package com.galaxy.im.business.project.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author xiaochuang 
* @date 2017年9月1日 下午2:51:12 
* @version V1.0
 */
@Controller
@RequestMapping("galaxy/delivery")
public class ProjectDeliveryController {
	private Logger log=LoggerFactory.getLogger(ProjectDeliveryController.class);
	
	@Autowired
	private IProjectDeliveryService service;
	
	
	/**
	 * 交割前事项列表
	 * @Title: queryprodeliverypage  
	 */
	@ResponseBody
	@RequestMapping("queryprodeliverypage")
	public Object queryprodeliverypage(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("titleId", 1810);
		paramMap.put("property", "id");
		paramMap.put("direction", "desc");
		try {
			QPage page = service.queryprodeliverypage(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
			
		} catch (Exception e) {
			log.error(ProjectDeliveryController.class.getName() + "：queryprodeliverypage",e);
		}
		return resultBean;
	}
	
	/**
	 * 交割前事项详情
	 */
	@ResponseBody
	@RequestMapping("selectdelivery")
	public Object selectdelivery(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			Object bean = service.selectdelivery(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(bean);
			
		} catch (Exception e) {
			log.error(ProjectDeliveryController.class.getName() + "：selectdelivery",e);
		}
		return resultBean;
		
	}
	
	
	
	
	
}
