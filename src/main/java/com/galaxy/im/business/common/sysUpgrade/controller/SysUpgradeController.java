package com.galaxy.im.business.common.sysUpgrade.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.common.sysUpgrade.service.ISysUpgradeService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/system")
public class SysUpgradeController {
	private Logger log = LoggerFactory.getLogger(SysUpgradeController.class);

	@Autowired
	ISysUpgradeService service;
	
	/**
	 * 获取系统升级消息
	 * @return
	 */
	@RequestMapping("getSysUpgradeMessage")
	@ResponseBody
	public Object getSysUpgradeMessage(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(!paramMap.isEmpty() && paramMap!=null){
				Map<String,Object> map = service.getSysUpgradeMessage(paramMap);
			}
		}catch(Exception e){
			log.error(SysUpgradeController.class.getName() + "getSysUpgradeMessage",e);
		}
		return resultBean;
	}
	
	/**
	 * 手动关闭系统升级消息
	 * @return
	 */
	@RequestMapping("closeSysUpgradeMessage")
	@ResponseBody
	public Object closeSysUpgradeMessage(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			
		}catch(Exception e){
			log.error(SysUpgradeController.class.getName() + "closeSysUpgradeMessage",e);
		}
		return resultBean;
	}
}
