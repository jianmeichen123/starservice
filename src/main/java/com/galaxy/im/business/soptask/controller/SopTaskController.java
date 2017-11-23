package com.galaxy.im.business.soptask.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.soptask.SopTaskRecord;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
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
	@Autowired
	private IFlowCommonService fcService;
	
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
		Map<String,Object> paramMap = new HashMap<String,Object>();
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
			sopTask.setTaskName("人事尽调任务");
			sopTask.setMessageType("1.2.1");
			sopTask.setAssignUid(5L);
			sopTask.setAssignUname("xxxx");
			sopTask.setCreatedId(bean.getGuserid());
			sopTask.setUserName(CUtils.get().object2String(user.get("realName")));
			messageService.operateMessageSopTaskInfo(sopTask);
			
			//记录操作日志，项目名称，项目id，项目阶段，任务id，原因
			List<Map<String, Object>> mapList= new ArrayList<Map<String, Object>>();
			for(Map<String, Object> map:sopTask.getProjects()){
				paramMap.put("projectId", map.get("projectId"));
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				map.put("recordId", 2306);
				map.put("projectProgressName", sopBean.getProjectProgressName());
				mapList.add(map);
			}
			ControllerUtils.setRequestBatchParamsForMessageTip(request,mapList);
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
	public Object taskTransfer(@RequestBody SopTaskRecord sopTaskRecord,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		if (bean==null) {
			resultBean.setMessage("获取用户信息失败");
			return resultBean;
		}
		Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
		try {
			//根据标识 flag=1代表移交 flag=2代表指派
			int count = 0;
			int updateCount = 0;
			if (sopTaskRecord.getFlag()==1) {
					List<Map<String, Object>> taskIds = sopTaskRecord.getTaskIds();
					if (taskIds!=null) {
						for(Map<String, Object> map : taskIds){
							if (map.get("taskId")!=null&&user.get("id")!=null&&map.get("projectId")!=null) {
							sopTaskRecord.setTaskId(CUtils.get().object2Long(map.get("taskId")));
							sopTaskRecord.setBeforeUid(CUtils.get().object2Long(user.get("id")));
							//获取部门id
							long depId = service.getDepId(CUtils.get().object2Long(user.get("id")));
							sopTaskRecord.setBeforeDepartmentId(depId);
							sopTaskRecord.setRecordType(sopTaskRecord.getFlag());
							sopTaskRecord.setCreatedTime(new Date().getTime());
							sopTaskRecord.setIsDel(0);
							//防止重复移交
							SopTaskRecord sRecord = service.selectRecord(sopTaskRecord);
							if (sRecord==null) {
								//保存移交内容
								 count = service.taskTransfer(sopTaskRecord);
							}
							
							
							//修改待办任务的信息
							SopTask sopTask = new SopTask();
							sopTask.setId(CUtils.get().object2Long(map.get("taskId")));
							sopTask.setUpdatedTime(new Date().getTime());
							sopTask.setAssignUid(sopTaskRecord.getAfterUid());
							 updateCount = service.updateTask(sopTask);
							
							//查询人事经理A是否已上传了人事/财务/法务尽调报告
							SopFileBean sopFileBean = new SopFileBean();
							sopFileBean.setProjectId( CUtils.get().object2Long(map.get("projectId")));
							//fileWorktype=2人事 fileWorktype=3法务 fileWorktype=4财务
							sopFileBean.setFileWorkType(sopTaskRecord.getFileWorktype());
							sopFileBean.setFileUid(CUtils.get().object2Long(user.get("id")));
							SopFileBean bean2 = service.isUpload(sopFileBean);
							//A将报告移交给B
							if (bean2!=null&&!bean2.equals("")) {
								//修改文件的认领人为B
								sopFileBean.setBelongUid(sopTaskRecord.getAfterUid());
								sopFileBean.setId(bean2.getId());
								service.updateFile(sopFileBean);
							}
						}
						
						}
					}
					if (count>0 && updateCount>0) {
						resultBean.setStatus("OK");
					}
					//发消息
					
					
				}else{
					List<Map<String, Object>> taskIds = sopTaskRecord.getTaskIds();
					if (taskIds!=null) {
						for(Map<String, Object> map : taskIds){
							if (map.get("taskId")!=null&&user.get("id")!=null&&map.get("projectId")!=null) {
							sopTaskRecord.setTaskId(CUtils.get().object2Long(map.get("taskId")));
							sopTaskRecord.setBeforeUid(CUtils.get().object2Long(user.get("id")));
							//获取部门id
							long depId = service.getDepId(CUtils.get().object2Long(user.get("id")));
							sopTaskRecord.setBeforeDepartmentId(depId);
							sopTaskRecord.setRecordType(sopTaskRecord.getFlag());
							sopTaskRecord.setCreatedTime(new Date().getTime());
							sopTaskRecord.setIsDel(0);
							//防止重复指派
							SopTaskRecord sRecord = service.selectRecord(sopTaskRecord);
							if (sRecord==null) {
								//保存指派内容
								 count = service.taskTransfer(sopTaskRecord);
							}
							//修改待办任务的信息
							SopTask sopTask = new SopTask();
							sopTask.setId(CUtils.get().object2Long(map.get("taskId")));
							sopTask.setUpdatedTime(new Date().getTime());
							sopTask.setAssignUid(sopTaskRecord.getAfterUid());
							sopTask.setTaskStatus("taskStatus:2");//将任务状态都变成待完工
							 updateCount = service.updateTask(sopTask);
							
							//查询人事经理A是否已上传了人事/财务/法务尽调报告
							SopFileBean sopFileBean = new SopFileBean();
							sopFileBean.setProjectId( CUtils.get().object2Long(map.get("projectId")));
							//fileWorktype=2人事 fileWorktype=3法务 fileWorktype=4财务
							sopFileBean.setFileWorkType(sopTaskRecord.getFileWorktype());
							sopFileBean.setFileUid(CUtils.get().object2Long(user.get("id")));
							SopFileBean bean2 = service.isUpload(sopFileBean);
							//A将报告移交给B
							if (bean2!=null&&!bean2.equals("")) {
								//修改文件的认领人为B
								sopFileBean.setBelongUid(sopTaskRecord.getAfterUid());
								sopFileBean.setId(bean2.getId());
								service.updateFile(sopFileBean);
							}
							
						}
						}
					}
					
					if (count>0&&updateCount>0) {
						resultBean.setStatus("OK");
					}
					
					//发消息
					
					
					
					
	
				}

		} catch (Exception e) {
			log.error(SopTaskController.class.getName() + "taskTransfer",e);
		}
		return resultBean;
	}
	
	/**
	 * 放弃待办任务
	 */
	@ResponseBody
	@RequestMapping("abandonTask")
	public Object abandonTask(@RequestBody SopTaskRecord sopTaskRecord,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		if (bean==null) {
			resultBean.setMessage("获取用户信息失败");
			return resultBean;
		}
		Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
		int count =0;
		int updateCount = 0;
		try {
			if (sopTaskRecord.getFlag()==0) {
			List<Map<String, Object>> taskIds = sopTaskRecord.getTaskIds();
				for(Map<String, Object> map : taskIds){
					if (map.get("taskId")!=null&&user.get("id")!=null&&map.get("projectId")!=null) {
						sopTaskRecord.setTaskId(CUtils.get().object2Long(map.get("taskId")));
						sopTaskRecord.setBeforeUid(CUtils.get().object2Long(user.get("id")));
						//获取部门id
						long depId = service.getDepId(CUtils.get().object2Long(user.get("id")));
						sopTaskRecord.setBeforeDepartmentId(depId);
						sopTaskRecord.setRecordType(sopTaskRecord.getFlag());
						sopTaskRecord.setCreatedTime(new Date().getTime());
						sopTaskRecord.setIsDel(0);
						//防止重复放弃
						SopTaskRecord sRecord = service.selectRecord(sopTaskRecord);
						if (sRecord==null) {
							//保存放弃内容
							 count = service.taskTransfer(sopTaskRecord);
						}
						//修改待办任务的信息
						SopTask sopTask = new SopTask();
						sopTask.setId(CUtils.get().object2Long(map.get("taskId")));
						sopTask.setUpdatedTime(new Date().getTime());
						sopTask.setAssignUid(null);
						sopTask.setTaskStatus("taskStatus:1");//将任务状态都变成待认领
						 updateCount = service.updateTask(sopTask);
						
						 
						//查询人事经理A是否已上传了人事/财务/法务尽调报告
						SopFileBean sopFileBean = new SopFileBean();
						sopFileBean.setProjectId( CUtils.get().object2Long(map.get("projectId")));
						//fileWorktype=2人事 fileWorktype=3法务 fileWorktype=4财务
						sopFileBean.setFileWorkType(sopTaskRecord.getFileWorktype());
						sopFileBean.setFileUid(CUtils.get().object2Long(user.get("id")));
						SopFileBean bean2 = service.isUpload(sopFileBean);
						//A将报告移交给B
						if (bean2!=null&&!bean2.equals("")) {
							//将此报告设为无用
							sopFileBean.setFileValid(0);
							sopFileBean.setId(bean2.getId());
							service.updateFile(sopFileBean);
						}
						
					}
					
				}
			}
			if (count>0&&updateCount>0) {
				resultBean.setStatus("OK");
			}
				
				
				//发消息
			
		} catch (Exception e) {
			log.error(SopTaskController.class.getName() + "abandonTask",e);
		}
		return resultBean;
	}
	
	
	
	
	
	
}
