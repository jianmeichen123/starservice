package com.galaxy.im.business.message.controller;

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
import com.galaxy.im.bean.message.MessageVo;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.business.flow.common.service.FlowCommonServiceImpl;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.business.rili.service.IScheduleService;
import com.galaxy.im.business.soptask.service.ISopTaskService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.html.QHtmlClient;
/**
 * 日程消息
 */
@Controller
@RequestMapping("/galaxy/schedule/message")
public class ScheduleMessageController {
	Logger log = LoggerFactory.getLogger(ScheduleMessageController.class);
	
	@Autowired
	private IScheduleMessageService service;
	@Autowired
	private IFlowCommonService fcService;
	@Autowired
	IScheduleMessageService messageService;
	@Autowired
	private Environment env;
	@Autowired
	private ISopTaskService taskService;
	
	@Autowired
	IScheduleService schService;
	
	
	/**
	 * 个人消息 列表查询
     */
	@ResponseBody
	@RequestMapping("/querySchedule")
	public Object querySchedule(HttpServletRequest request,@RequestBody String paramString ){
		
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			/**
			 * 从session中获取用户信息
			 */
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			//结果查询  封装
			paramMap.put("userId", sessionBean.getGuserid());
			paramMap.put("direction", "desc");
			QPage page = service.queryPerMessAndConvertPage(paramMap);
			if ( page!=null) {
				resultBean.setStatus("OK");
				resultBean.setEntity(page);
			}
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：querySchedule",e);
		}
		return resultBean;
	}
	
	
	
	/**
	 * 消息 已读
	 */
	@ResponseBody
	@RequestMapping("/toRead")
	public Object toRead(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			paramMap.put("isRead", 1);
			paramMap.put("updatedTime", new Date().getTime());
			long count = service.updateToRead(paramMap);
			if (count>0) {
				resultBean.setStatus("OK");
			}
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：toRead",e);
		}
		return resultBean;
	}
	
	/**
	 * 个人消息列表  设为全部已读
	 * 1.查询出 消息user 表中      个人的   可用   未读  未删除  的数据
	 * 2.查询出消息内容列表          状态为可用的消息
     */
	@ResponseBody
	@RequestMapping("perMessageToRead")
	public Object perMessageToRead(HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			/**
			 * 从session中获取用户信息
			 */
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			int count = service.perMessageToRead(sessionBean.getGuserid());
			if (count>0) {
				resultBean.setStatus("OK");
			}
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：perMessageToRead",e);
		}
		return resultBean;
	}
	
	/**
	 * 个人消息列表  清空
	 * 1.查询出 消息user 表中      个人的  未删除  的数据
	 * 2.查询出消息内容列表          状态为可用的消息
     */
	@ResponseBody
	@RequestMapping("/perMessageToClear")
	public Object perMessageToClear(HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			/**
			 * 从session中获取用户信息
			 */
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			int count = service.perMessageToClear(sessionBean.getGuserid());
			if (count>0) {
				resultBean.setStatus("OK");
			}else{
				resultBean.setMessage("没有可清除的消息!");
			}
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：perMessageToClear",e);
		}
		return resultBean;
	}
	
	/**
	 * 对外接口：推送消息
     */
	@ResponseBody
	@RequestMapping("/saveSchedule")
	public Object saveSchedule(HttpServletRequest request,HttpServletResponse response,@RequestBody MessageVo messageVo){
		
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			//获取登录用户信息
			/*SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if (sessionBean==null) {
				resultBean.setMessage("获取用户信息失败");
			}*/
			long deptId=0l;
			String userName="";
			String userDeptName="";
			//获取用户所属部门id
			List<Map<String, Object>> list = fcService.getDeptId(messageVo.getUserId(),request,response);
			if(list!=null){
				for(Map<String, Object> vMap:list){
					deptId= CUtils.get().object2Long( vMap.get("deptId"));
					userName=CUtils.get().object2String(vMap.get("userName"));
					userDeptName=CUtils.get().object2String(vMap.get("deptName"));
				}
			}
			
			
			/*@SuppressWarnings("unchecked")
			RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
			Map<String, Object> user = BeanUtils.toMap(cache.get("realName"));*/
			
			List<Map<String, Object>> userList=new ArrayList<Map<String, Object>>();
			List<String> ids= messageVo.getIds();
			SopProjectBean sopBean=null;
			SopTask sopTask=null;
			List<Map<String, Object>> projects=new ArrayList<Map<String, Object>>();
			
			if(messageVo.getMessageType().startsWith("1.1")){
				//项目
				for(int i=0;i<ids.size();i++){
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("projectId",ids.get(i));
					
				    sopBean = fcService.getSopProjectInfo(paramMap);
					sopBean.setMessageType(messageVo.getMessageType());
					sopBean.setUserId(messageVo.getUserId());
					sopBean.setUserName(userName);
					sopBean.setUserDeptName(userDeptName);
					paramMap.put("projectName", sopBean.getProjectName());
					projects.add(paramMap);
				}
				sopBean.setProjects(projects);
				messageService.operateMessageSopTaskInfo(sopBean,sopBean.getMessageType());
			}else if(messageVo.getMessageType().startsWith("1.2")){
				if(messageVo.getMessageType().equals("1.2.5")){
					//尽职调查
					int lawDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_LAW,request,response);
					int fdDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_FD,request,response);
					int hrDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_HR,request,response);
					List<Map<String, Object>> lawDeptIdList = fcService.getUserListByDeptId(lawDeptId);
					List<Map<String, Object>> fdDeptIdList = fcService.getUserListByDeptId(fdDeptId);
					List<Map<String, Object>> hrDeptIdList = fcService.getUserListByDeptId(hrDeptId);
					userList.addAll(lawDeptIdList);
					userList.addAll(fdDeptIdList);
					userList.addAll(hrDeptIdList);
					
					for(int i=0;i<ids.size();i++){
						Map<String,Object> paramMap = new HashMap<String,Object>();
						paramMap.put("projectId",ids.get(i));
						
					    sopBean = fcService.getSopProjectInfo(paramMap);
						sopTask =new SopTask();
						sopTask.setId(sopBean.getId());
						sopTask.setProjectName(sopBean.getProjectName());
						sopTask.setUsers(userList);
						sopTask.setMessageType("1.2.5");
						sopTask.setCreatedId(messageVo.getUserId());
						sopTask.setUserName(userName);
					}
				}else if(messageVo.getMessageType().equals("1.2.6")){
					//股权交割
					int lawDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_LAW,request,response);
					int fdDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_FD,request,response);
					List<Map<String, Object>> lawDeptIdList = fcService.getUserListByDeptId(lawDeptId);
					List<Map<String, Object>> fdDeptIdList = fcService.getUserListByDeptId(fdDeptId);
					userList.addAll(lawDeptIdList);
					userList.addAll(fdDeptIdList);
					
					for(int i=0;i<ids.size();i++){
						Map<String,Object> paramMap = new HashMap<String,Object>();
						paramMap.put("projectId",ids.get(i));
						
					    sopBean = fcService.getSopProjectInfo(paramMap);
						sopTask =new SopTask();
						sopTask.setId(sopBean.getId());
						sopTask.setProjectName(sopBean.getProjectName());
						sopTask.setUsers(userList);
						sopTask.setMessageType("1.2.6");
						sopTask.setCreatedId(messageVo.getUserId());
						sopTask.setUserName(userName);
					}
				}else{
					//代办任务的认领，移交，指派，放弃
					//用户列表
					Map<String,Object> vmap = new HashMap<String,Object>();
					vmap.put("depId", deptId);
					Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
					//事业部下的所有用户
					String urlU = env.getProperty("power.server") + StaticConst.getUsersByDepId;
					JSONArray rr=null;
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
									if(!CUtils.get().object2String(map.get("userId")).equals(CUtils.get().object2String(messageVo.getUserId()))){
										userList.add(map);
									}
								}
							}
						}
					}	
					//发消息
					for(int i=0;i<ids.size();i++){
						Map<String,Object> paramMap = new HashMap<String,Object>();
						
						sopTask= taskService.getTaskInfoById(CUtils.get().object2Long(ids.get(i)));
						
						paramMap.put("projectId",sopTask.getProjectId());
						sopBean = fcService.getSopProjectInfo(paramMap);
						
						sopTask.setMessageType(messageVo.getMessageType());
						sopTask.setCreatedId(messageVo.getUserId());
						sopTask.setUserName(userName);
						paramMap.put("projectName", sopBean.getProjectName());
						paramMap.put("projectCreatedId", sopBean.getCreateUid());
						paramMap.put("projectCreatedName", sopBean.getCreateUname());
						paramMap.put("taskName", sopTask.getTaskName());
						paramMap.put("taskId", sopTask.getId());
						projects.add(paramMap);
					}
					sopTask.setUsers(userList);
					sopTask.setProjects(projects);
				}
				messageService.operateMessageSopTaskInfo(sopTask,sopTask.getMessageType());
			}
			resultBean.setStatus("OK");
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "saveSchedule",e);
		}
		return resultBean;
	}
	
	/**
	 * 未读个数
	 */
	@ResponseBody
	@RequestMapping("/getUnReadCount")
	public Object getUnReadCount(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			if(paramMap.containsKey("uid")){
				//日程消息未读个数
				Long count = schService.queryProjectScheduleCount(CUtils.get().object2Long(paramMap.get("uid")));
				resultBean.setStatus("OK");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("count", count);
				resultBean.setMap(map);
			}
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "getUnReadCount",e);
		}
		return resultBean;
	}
	
	/**
	 * 消息 已读按时间
	 */
	@ResponseBody
	@RequestMapping("/toReadByTime")
	public Object toReadByTime(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			if(paramMap.containsKey("time") && paramMap.containsKey("uid")){
				paramMap.put("isRead", 1);
				paramMap.put("updatedTime", new Date().getTime());
				long count = service.updateToRead(paramMap);
				if (count>0) {
					resultBean.setStatus("OK");
				}
			}
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "toReadByTime",e);
		}
		return resultBean;
	}

	
}
