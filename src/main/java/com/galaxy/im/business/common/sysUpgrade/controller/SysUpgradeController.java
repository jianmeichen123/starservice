package com.galaxy.im.business.common.sysUpgrade.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.message.SystemMessageUserBean;
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
	public Object getSysUpgradeMessage(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			SessionBean session = CUtils.get().getBeanBySession(request);
			
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			paramMap.put("userId", session.getGuserid());
			if(!paramMap.isEmpty() && paramMap!=null){
				paramMap.put("date", new Date().getTime());
				Map<String,Object> map = service.getSysUpgradeMessage(paramMap);
				resultBean.setStatus("OK");
				resultBean.setMap(map);
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
	public Object closeSysUpgradeMessage(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			SessionBean session = CUtils.get().getBeanBySession(request);
			
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(!paramMap.isEmpty() && paramMap!=null && paramMap.containsKey("messageId") && paramMap.containsKey("OsType")){
				SystemMessageUserBean bean = new SystemMessageUserBean();
				bean.setMessageId(CUtils.get().object2Long(paramMap.get("messageId")));
				bean.setMessageOs(CUtils.get().object2String(paramMap.get("OsType")));
				bean.setUserId(session.getGuserid());
				bean.setIsRead(0);
				bean.setIsDel(0);
				bean.setCreateId(session.getGuserid());
				bean.setCreateTime(new Date().getTime());
				int result = service.closeSysUpgradeMessage(bean);
				if(result>0){
					resultBean.setStatus("OK");
				}
			}
		}catch(Exception e){
			log.error(SysUpgradeController.class.getName() + "closeSysUpgradeMessage",e);
		}
		return resultBean;
	}
}
