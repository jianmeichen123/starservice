package com.galaxy.im.business.soptask.controller;

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
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.business.soptask.service.ISopTaskService;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.RedisCacheImpl;
import com.galaxy.im.common.db.QPage;

@Controller
@RequestMapping("/soptask")
public class SopTaskController {
	private Logger log = LoggerFactory.getLogger(SopTaskController.class);
	
	@Autowired
	private ISopTaskService service;
	@Autowired
	IScheduleMessageService messageService;
	
	/**
	 * 待办任务列表
	 */
	@ResponseBody
	@RequestMapping("taskListByRole")
	public Object taskListByRole(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		if(bean==null){
			resultBean.setMessage("User用户信息在Session中不存在，无法执行待办任务列表查询！");
			return resultBean;
		}
		Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
		try {
			//获取部门id
			long depId = service.getDepId(CUtils.get().object2Long(user.get("id")));
			paramMap.put("property", "createdTime");
			paramMap.put("direction", "desc");
			paramMap.put("depId", depId);
			if (paramMap.get("flag")!=null && !paramMap.get("flag").equals("")) {
			//待认领
			if (paramMap.get("flag").equals("1")) {
				paramMap.put("taskStatus", "taskStatus:1");
				
			}
			//待完工
			if (paramMap.get("flag").equals("2")) {
				paramMap.put("taskStatus", "taskStatus:2");
				paramMap.put("assignUid", user.get("id"));
			}
			//已完工
			if (paramMap.get("flag").equals("3")) {
				paramMap.put("taskStatus", "taskStatus:3");
				paramMap.put("assignUid", user.get("id"));
			}
			//部门待完工
			if (paramMap.get("flag").equals("4")) {
				paramMap.put("taskStatus", "taskStatus:2");
				paramMap.put("assignUid", user.get("id"));
			}
		}
			QPage page = service.taskListByRole(paramMap);
			if ( page!=null) {
				resultBean.setStatus("OK");
				resultBean.setEntity(page);
			}
		} catch (Exception e) {
			log.error(SopTaskController.class.getName() + "taskListByRole",e);
		}
		return resultBean;
		
	}
	
	/**
	 * 待办任务详情
	 */
	@ResponseBody
	@RequestMapping("taskInfo")
	public Object taskInfo(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			Object object = service.taskInfo(paramMap);
			if (object!=null) {
				resultBean.setEntity(object);
				resultBean.setStatus("OK");
			}
		} catch (Exception e) {
			log.error(SopTaskController.class.getName() + "taskInfo",e);
		}
		return resultBean;
	}
	
	/**
	 * 认领
	 */
	@ResponseBody
	@RequestMapping("applyTask")
	public Object applyTask(@RequestBody SopTask sopTask,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		if (bean==null) {
			resultBean.setMessage("获取用户信息失败");
		}
		Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
		try {
			sopTask.setAssignUid(CUtils.get().object2Long(user.get("id")));
			sopTask.setUpdatedTime(new Date().getTime());
			//把任务状态变为待完成
			sopTask.setTaskStatus("taskType:2");
			int count = service.applyTask(sopTask);
			if (count>0) {
				resultBean.setStatus("OK");
			}
			
			//发消息
			sopTask.setMessageType("1.2.1");
			sopTask.setCreatedId(bean.getGuserid());
			sopTask.setUserName(CUtils.get().object2String(user.get("realName")));
			messageService.operateMessageSopTaskInfo(sopTask);
		} catch (Exception e) {
			log.error(SopTaskController.class.getName() + "applyTask",e);
		}
		return resultBean;
	}
	
	/**
	 * 移交/指派待办任务
	 * 移交:待完工任务 人财法经理执行
	 * 指派:待认领/待完工任务  人财法总监执行
	 */
	@ResponseBody
	@RequestMapping("taskTransfer")
	public Object taskTransfer(@RequestBody SopTask sopTask,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		if (bean==null) {
			resultBean.setMessage("获取用户信息失败");
		}
		Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
		try {
			//根据标识 flag=1代表移交 flag=2代表指派
			if (sopTask.getFlag()==1) {
				
				
			}
			sopTask.setAssignUid(CUtils.get().object2Long(user.get("id")));
			sopTask.setUpdatedTime(new Date().getTime());
			//把任务状态变为待完成
			sopTask.setTaskStatus("taskType:2");
			int count = service.applyTask(sopTask);
			if (count>0) {
				resultBean.setStatus("OK");
			}
			
			//发消息
			
		} catch (Exception e) {
			log.error(SopTaskController.class.getName() + "applyTask",e);
		}
		return resultBean;
	}
	
	
	
	
	
	
}
