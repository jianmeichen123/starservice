package com.galaxy.im.business.soptask.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.soptask.SopTaskRecord;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.service.FlowCommonServiceImpl;
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
import com.galaxy.im.common.html.QHtmlClient;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;

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
	@Autowired
	private Environment env;
	
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
			}
		}
			QPage page = service.taskListByRole(paramMap);
			if (paramMap.get("flag").equals("4")) {
			if (page!=null) {
				List<Map<String, Object>> list = page.getDataList();
				for(Map<String, Object> map : list){
					if (map.get("assignUid").equals(user.get("id"))) {
						map.put("userFalg", 1);
					}else {
						map.put("userFalg", 0);
					}
				}
			  }
			}
			//待认领总数
			paramMap.put("assignUid", null);
			paramMap.put("taskStatus", "taskStatus:1");
			int notApplyCount = service.selectCount(paramMap);
			//待完工总数
			paramMap.put("taskStatus", "taskStatus:2");
			paramMap.put("assignUid", user.get("id"));
			int isApplyCount = service.selectCount(paramMap);
			//已完工总数
			paramMap.put("taskStatus", "taskStatus:3");
			paramMap.put("assignUid", user.get("id"));
			int applyCount = service.selectCount(paramMap);
			//部门待完工
			paramMap.put("taskStatus", "taskStatus:2");
			paramMap.put("assignUid", null);
			int depApplyCount = service.selectCount(paramMap);
			Map<String, Object> map = new HashMap<>();
			map.put("notApplyCount", notApplyCount);
			map.put("isApplyCount", isApplyCount);
			map.put("applyCount", applyCount);
			map.put("depApplyCount", depApplyCount);
			
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
			resultBean.setMap(map);
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
			Map<String, Object> map= service.taskInfo(paramMap);
			//获取登录用户信息
			SessionBean bean = CUtils.get().getBeanBySession(request);
			if (bean==null) {
				resultBean.setMessage("获取用户信息失败");
			}
			Map<String, Object> map2 = new HashMap<>();
			//查询人事经理A是否已上传了人事/财务/法务尽调报告
			SopFileBean sopFileBean = new SopFileBean();
			sopFileBean.setProjectId( CUtils.get().object2Long(paramMap.get("projectId")));
			//fileWorktype=2人事 fileWorktype=3法务 fileWorktype=4财务fileWorktype=8工商转让凭证(法务)fileWorktype=9资金拨付(财务)
			
			String taskName = CUtils.get().object2String(paramMap.get("taskName"));
			String fileWorkType = getFileWorkType(taskName);
			sopFileBean.setFileWorkType(fileWorkType);
			sopFileBean.setFileUid(bean.getGuserid());
			SopFileBean bean2 = service.isUpload(sopFileBean);
			if (bean2!=null) {
				//标识存在报告在详情页显示
				map2.put("isUploadReport", 1);
			}else {
				map2.put("isUploadReport", 0);
			}
			
			//查询时否存在操作日志
			paramMap.put("recordId", paramMap.get("id"));
			Long count = service.getOperationLogs(paramMap);
			if (count >0) {
				//标识存在操作日志
				map2.put("isOperationLogs", 1);
			}else{
				map2.put("isOperationLogs", 0);
			}
			
			if (map!=null) {
				resultBean.setMap(map2);
				resultBean.setEntity(map);
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
			sopTask.setIsDelete(0);
			sopTask.setTaskStatus("taskStatus:2");
			int count = service.applyTask(sopTask);
			if (count>0) {
				resultBean.setStatus("OK");
			}
			long projectId=0L;
			for(Map<String, Object> map:sopTask.getProjects()){
				projectId =CUtils.get().object2Long(map.get("projectId"));
			}
			//发消息
			paramMap.put("projectId", projectId);
			SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
			
			sopTask.setMessageType("1.2.1");
			sopTask.setAssignUname(CUtils.get().object2String(user.get("userName")));
			sopTask.setCreatedId(bean.getGuserid());
			sopTask.setUserName(CUtils.get().object2String(user.get("realName")));
			messageService.operateMessageSopTaskInfo(sopTask,sopTask.getMessageType());
			//记录操作日志，项目名称，项目id，项目阶段，任务id，原因
			List<Map<String, Object>> mapList= new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectId", sopBean.getId());
			map.put("projectName", sopBean.getProjectName());
			map.put("projectProgressName", sopBean.getProjectProgressName());
			map.put("recordId", sopTask.getId());
			for(Map<String, Object> m:sopTask.getProjects()){
				if(m.containsKey("taskName") && m.get("taskName")!=null){
					map.put("taskName", m.get("taskName"));
				}
			}
			mapList.add(map);
			//代办任务操作日志
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
							//保存移交内容
							count = service.taskTransfer(sopTaskRecord);
							
							//修改待办任务的信息
							SopTask sopTask = new SopTask();
							sopTask.setId(CUtils.get().object2Long(map.get("taskId")));
							sopTask.setUpdatedTime(new Date().getTime());
							sopTask.setAssignUid(sopTaskRecord.getAfterUid());
							sopTask.setTaskStatus("taskStatus:2");
							updateCount = service.updateTask(sopTask);
							
							//查询人事经理A是否已上传了人事/财务/法务尽调报告
							SopFileBean sopFileBean = new SopFileBean();
							sopFileBean.setProjectId( CUtils.get().object2Long(map.get("projectId")));
							//根据代办任务名称，获取对应上传的报告类型
							String fileWorkType = getFileWorkType(CUtils.get().object2String(map.get("taskName")));
							sopFileBean.setFileWorkType(fileWorkType);
							SopFileBean bean2 = service.isUpload(sopFileBean);
							//A将报告移交给B
							if (bean2!=null&&!bean2.equals("")) {
								//修改文件的认领人为B
								bean2.setFileValid(1);
								bean2.setBelongUid(sopTaskRecord.getAfterUid());
								bean2.setId(bean2.getId());
								bean2.setUpdatedTime(new Date().getTime());
								service.updateFile(bean2);
							}
						 }
						}
					}
					if (count>0 && updateCount>0) {
						resultBean.setStatus("OK");
					}
					
					//推送消息，记录操作日志
					operateMethod(sopTaskRecord,user,bean,request,1);
				}else if(sopTaskRecord.getFlag()==2){
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
							//保存指派内容
							count = service.taskTransfer(sopTaskRecord);
							
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
							//根据代办任务名称，获取对应上传的报告类型
							String fileWorkType = getFileWorkType(CUtils.get().object2String(map.get("taskName")));
							sopFileBean.setFileWorkType(fileWorkType);
							SopFileBean bean2 = service.isUpload(sopFileBean);
							//A将报告移交给B
							if (bean2!=null&&!bean2.equals("")) {
								//修改文件的认领人为B
								bean2.setFileValid(1);
								bean2.setBelongUid(sopTaskRecord.getAfterUid());
								bean2.setId(bean2.getId());
								bean2.setUpdatedTime(new Date().getTime());
								service.updateFile(bean2);
							}
						  }
						}
					}
					if (count>0&&updateCount>0) {
						resultBean.setStatus("OK");
					}
					//推送消息，记录操作日志
					operateMethod(sopTaskRecord,user,bean,request,2);
				}
		} catch (Exception e) {
			log.error(SopTaskController.class.getName() + "taskTransfer",e);
		}
		return resultBean;
	}
	
	private void operateMethod(SopTaskRecord sopTaskRecord, Map<String, Object> user,
			SessionBean bean, HttpServletRequest request, int flag) {
		//发消息
		UrlNumber uNum = null;
		SopTask sopTask =new SopTask();
		sopTask.setProjects(sopTaskRecord.getTaskIds());
		if(flag==1){
			sopTask.setMessageType("1.2.2");
			uNum = UrlNumber.one;
		}else if(flag==2){
			sopTask.setMessageType("1.2.4");
			uNum = UrlNumber.two;
		}
		sopTask.setId(sopTaskRecord.getTaskId());
		sopTask.setAssignUname(sopTaskRecord.getAssignUname());
		sopTask.setAssignUid(sopTaskRecord.getAfterUid());
		sopTask.setCreatedId(bean.getGuserid());
		sopTask.setUserName(CUtils.get().object2String(user.get("realName")));
		messageService.operateMessageSopTaskInfo(sopTask,sopTask.getMessageType());
		
		//记录操作日志，项目名称，项目id，项目阶段，任务id，原因
		List<Map<String, Object>> mapList= new ArrayList<Map<String, Object>>();
		
		for(Map<String, Object> m:sopTask.getProjects()){
			SopProjectBean sopBean = fcService.getSopProjectInfo(m);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectId", sopBean.getId());
			map.put("projectName", sopBean.getProjectName());
			map.put("projectProgressName", sopBean.getProjectProgressName());
			if(m.containsKey("taskId") && m.get("taskId")!=null){
				map.put("recordId", m.get("taskId"));
			}
			if(m.containsKey("taskName") && m.get("taskName")!=null){
				map.put("taskName", m.get("taskName"));
			}
			map.put("nums", uNum);
			map.put("reason", sopTaskRecord.getReason());
			mapList.add(map);
		}
		ControllerUtils.setRequestBatchParamsForMessageTip(request,mapList);
	}

	/**
	 * 放弃待办任务
	 */
	@ResponseBody
	@RequestMapping("abandonTask")
	public Object abandonTask(@RequestBody SopTaskRecord sopTaskRecord,HttpServletRequest request,HttpServletResponse response){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		if (bean==null) {
			resultBean.setMessage("获取用户信息失败");
			return resultBean;
		}
		long deptId=0l;
		//获取用户所属部门id
		List<Map<String, Object>> list = fcService.getDeptId(bean.getGuserid(),request,response);
		if(list!=null){
			for(Map<String, Object> vMap:list){
				deptId= CUtils.get().object2Long( vMap.get("deptId"));
			}
		}
		//用户列表
		Map<String,Object> vmap = new HashMap<String,Object>();
		vmap.put("depId", deptId);
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		//事业部下的所有用户
		String urlU = env.getProperty("power.server") + StaticConst.getUsersByDepId;
		JSONArray rr=null;
		List<Map<String, Object>> userList=new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> mList=new ArrayList<Map<String, Object>>();
		String res = QHtmlClient.get().post(urlU, headerMap, vmap);
		if("error".equals(res)){
			log.error(FlowCommonServiceImpl.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
		}else{
			JSONObject json = JSONObject.parseObject(res);
			if(json!=null && json.containsKey("value")){
				rr = json.getJSONArray("value");
				if(json.containsKey("success") && "true".equals(json.getString("success"))){
					//操作
					mList =CUtils.get().jsonString2list(rr);
					for(Map<String, Object> map:mList){
						if(!CUtils.get().object2String(map.get("userId")).equals(CUtils.get().object2String(bean.getGuserid()))){
							userList.add(map);
						}
					}
				}
			}
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
						//保存放弃内容
						count = service.taskTransfer(sopTaskRecord);
						
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
						//根据代办任务名称，获取对应上传的报告类型
						String fileWorkType = getFileWorkType(CUtils.get().object2String(map.get("taskName")));
						sopFileBean.setFileWorkType(fileWorkType);
						SopFileBean bean2 = service.isUpload(sopFileBean);
						//A将报告移交给B
						if (bean2!=null&&!bean2.equals("")) {
							//将此报告设为无用
							bean2.setFileKey(null);
							bean2.setFileLength(0);
							bean2.setBucketName(null);
							bean2.setFileName(null);
							bean2.setFileSuffix(null);
							bean2.setFileType(null);
							bean2.setFileUid(0);
							bean2.setFileStatus(StaticConst.FILE_STATUS_1);
							bean2.setId(bean2.getId());
							bean2.setUpdatedTime(new Date().getTime());
							@SuppressWarnings("unused")
							int res1 = service.updateSopFile(bean2);
						}
					}
				}
			}
			if (count>0&&updateCount>0) {
				resultBean.setStatus("OK");
			}
				
			//发消息
			SopTask sopTask =new SopTask();
			sopTask.setId(sopTaskRecord.getTaskId());
			sopTask.setProjects(sopTaskRecord.getTaskIds());
			sopTask.setUsers(userList);
			sopTask.setMessageType("1.2.3");
			sopTask.setAssignUname(CUtils.get().object2String(user.get("userName")));
			sopTask.setCreatedId(bean.getGuserid());
			sopTask.setUserName(CUtils.get().object2String(user.get("realName")));
			messageService.operateMessageSopTaskInfo(sopTask,sopTask.getMessageType());
			
			//记录操作日志，项目名称，项目id，项目阶段，任务id，原因
			List<Map<String, Object>> mapList= new ArrayList<Map<String, Object>>();
			
			for(Map<String, Object> m:sopTask.getProjects()){
				SopProjectBean sopBean = fcService.getSopProjectInfo(m);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("projectId", sopBean.getId());
				map.put("projectName", sopBean.getProjectName());
				map.put("projectProgressName", sopBean.getProjectProgressName());
				if(m.containsKey("taskId") && m.get("taskId")!=null){
					map.put("recordId", m.get("taskId"));
				}
				if(m.containsKey("taskName") && m.get("taskName")!=null){
					map.put("taskName", m.get("taskName"));
				}
				map.put("reason", sopTaskRecord.getReason());
				mapList.add(map);
			}
			ControllerUtils.setRequestBatchParamsForMessageTip(request,mapList);
		} catch (Exception e) {
			log.error(SopTaskController.class.getName() + "abandonTask",e);
		}
		return resultBean;
	}
	
	
	private String getFileWorkType(String taskName) {
		String fileWorkType="";
		if (taskName!=null && !"".equals(taskName)) {
			if (taskName.contains("财务")) {
				fileWorkType="fileWorktype:4";
			}else if (taskName.contains("资金")) {
				fileWorkType="fileWorktype:9";
			}else if (taskName.contains("法务")) {
				fileWorkType="fileWorktype:3";
			}else if (taskName.contains("工商")) {
				fileWorkType="fileWorktype:8";
			}else if (taskName.contains("人事")) {
				fileWorkType="fileWorktype:2";
			}
		}
		return fileWorkType;
	}
	
	
	
}
