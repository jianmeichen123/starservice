package com.galaxy.im.business.flow.investmentdeal.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.investmentdeal.service.IInvestmentdealService;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.RedisCacheImpl;

/**
 * 投决会
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/investmentdeal")
public class InvestmentdealController {

	private Logger log = LoggerFactory.getLogger(InvestmentdealController.class);
	@Autowired
	private IFlowCommonService fcService;
	
	@Autowired
	private IInvestmentdealService iiService;
	@Autowired
	IScheduleMessageService messageService;

	/**
	 * 判断项目操作按钮状态
	 * 依据：1.需要一条结论为“通过”的会议记录；同时判定该项目在“会后商务谈判”阶段的结论是“投资”；
	 * 2,需要一条结论为“通过”的会议记录；同时判定该项目在“会后商务谈判”阶段的结论是“闪投”；
	 * 3.需要一条结论为"否定"的会议记录
	 * 前端处理：满足条件1则"签订投资协议"钮变亮;满足条件2则"进入股权交割"按钮变亮;满足条件3则"否决项目"按钮变亮;
	 * 
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 *  entity ->  "investpass": true, 存在“会后商务谈判”阶段的结论是“投资”
	 * 	entity ->  "flashpass": true 存在通过的会议且“会后商务谈判”阶段的结论是“闪投”
	 *  entity ->  ""veto": true,"  不存在通过的会议
	 */
	
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			paramMap.put("meetingType",StaticConst.MEETING_TYPE_INVEST);
			Map<String,Object> m = iiService.getInvestmentdealOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			//会议最新信息
			Map<String,Object> map = fcService.getLatestMeetingRecordInfo(paramMap);
			if(CUtils.get().mapIsNotEmpty(map)){
				result.setMap(map);
			}else {
				paramMap.clear();
				paramMap.put("meetingType", StaticConst.MEETING_TYPE_INVEST);
				result.setMap(paramMap);
			}
			result.setStatus("OK");
		}catch(Exception e){
			log.error(InvestmentdealController.class.getName() + "projectOperateStatus",e);
		}
		return result;
	}
	
	/**
	 * 否决项目
	 */
	@RequestMapping("votedown")
	@ResponseBody
	public Object votedown(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>();
		int flag = 0;
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> rMap = new HashMap<String,Object>();
			rMap.put("flag",0);
			paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_7);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				SopProjectBean p = fcService.getSopProjectInfo(paramMap);
				//验证该项目的状态，查看能否进行操作
				Map<String,Object> statusMap = fcService.projectStatus(paramMap);
				if(CUtils.get().mapIsNotEmpty(statusMap)){
					flag = CUtils.get().object2Integer(statusMap.get("flag"));
					if(flag==1){
						//否决
						if(fcService.vetoProject(paramMap)){
							rMap.put("flag", 1);
							result.setMessage("否决项目成功");
							result.setStatus("OK");
							//全息报告数据同步
							reportSync(p,0);
							paramMap.put("scheduleStatus", 3);
							paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
							iiService.updateInvestmentdeal(paramMap);  //修改投决会评审排期状态为已否决
							//记录操作日志
							ControllerUtils.setRequestParamsForMessageTip(request,p.getProjectName(), p.getId(),null, false, null, null, null);
						}else{
							result.setMessage("项目当前状态或进度已被修改，请刷新");
						}
					}else{
						rMap.put("flag", 0);
						result.setMessage(CUtils.get().object2String(statusMap.get("message")));
					}
				}
			}
			result.setEntity(rMap);
		}catch(Exception e){
			log.error(InvestmentdealController.class.getName() + "votedown",e);
		}
		return result;
	}
	
	/**
	 * 签订投资协议
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startInvestmentpolicy")
	@ResponseBody
	public Object startInvestmentpolicy(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String paramString) {
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> map = new HashMap<>();
		resultBean.setFlag(0);
		try {
			@SuppressWarnings("unused")
			long deptId=0l;
			String progressHistory = "";
			Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
			if (CUtils.get().mapIsNotEmpty(paramMap)) {
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if (sopBean != null) {
					if (sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_7)) {
						// 项目当前所处在投决会阶段,在流程历史记录拼接要进入的下个阶段
						if (!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory() != null) {
							progressHistory = sopBean.getProgressHistory() + "," + StaticConst.PROJECT_PROGRESS_8;
						} else {
							progressHistory = StaticConst.PROJECT_PROGRESS_8;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_8); // 表示进入投资协议阶段
						paramMap.put("progressHistory", progressHistory); // 流程历史记录
						if (fcService.enterNextFlow(paramMap)) {
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_8);
							// 生成投资协议代办任务
							SessionBean sessionBean = CUtils.get().getBeanBySession(request);
							// 获取用户所属部门id
							List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(), request, response);
							if(list!=null){
								for(Map<String, Object> vMap:list){
									deptId= CUtils.get().object2Long( vMap.get("deptId"));
								}
							}
							//给投资经理生成代码任务
							/*SopTask bean = new SopTask();
							bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
							bean.setTaskName(StaticConst.TASK_NAME_TZXY);
							bean.setTaskType(StaticConst.TASK_TYPE_XTBG);
							bean.setTaskFlag(StaticConst.TASK_FLAG_TZXY);
							bean.setTaskStatus(StaticConst.TASK_STATUS_DWG);
							bean.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
							bean.setAssignUid(sessionBean.getGuserid());
							bean.setDepartmentId(deptId);
							bean.setCreatedTime(new Date().getTime());
							@SuppressWarnings("unused")
							Long id = fcService.insertsopTask(bean);*/
							// 修改投决会排期状态为已通过
							paramMap.put("scheduleStatus", 2);
							paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
							iiService.updateInvestmentdeal(paramMap);
							resultBean.setMap(map);
							resultBean.setStatus("OK");
							//全息报告数据同步
							reportSync(sopBean,1);
							//记录操作日志
							ControllerUtils.setRequestParamsForMessageTip(request, sopBean.getProjectName(), sopBean.getId(),"");
						}else{
							resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
						}
					} else {
						resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
					}
				}
			}
		} catch (Exception e) {
			log.error(InvestmentdealController.class.getName() + "startInvestmentpolicy",e);
		}
		return resultBean;
	}
	
	//全息报告数据同步
	@SuppressWarnings("unused")
	private void reportSync(SopProjectBean sopBean,int flag) {
		InformationResult result=null;
		Map<String,Object> map = new HashMap<String,Object>();
		List<InformationResult> list =new ArrayList<InformationResult>();
		String choose="";
		//会议最新信息
		map.put("projectId", sopBean.getId());
		map.put("meetingType", StaticConst.MEETING_TYPE_INVEST);
		
		List<String> resultList = new ArrayList<String>();
		resultList.add("meeting4Result:1");
		map.put("resultList", resultList);
		
		if(flag==0){
			choose="1177";
		}else{
			Map<String,Object> res = fcService.getMeetingRecordInfo(map);
			if(res.containsKey("meetingResultCode") && res.get("meetingResultCode")!=null){
				if(res.get("meetingResultCode").equals("meeting4Result:1")){
					choose="1173";
				}
			}
		}
		
		map.put("parentId", "7028");
		map.put("titleId", "1114");
		list = fcService.getReportInfo(map);
		if(list.isEmpty()){
			//添加
			result =new InformationResult();
			result.setProjectId(CUtils.get().object2String(sopBean.getId()));
			result.setTitleId("1114");
			result.setContentChoose(choose);
			result.setCreatedTime(new Date().getTime());
			result.setCreateId(CUtils.get().object2String(sopBean.getCreatedId()));
			long id = fcService.addInformationResult(result);
		}else{
			for(InformationResult bean : list){
				if(bean.getContentChoose()!=null){
					bean.setContentChoose(choose);
					bean.setUpdatedTime(new Date().getTime());
					bean.setUpdateId(CUtils.get().object2String(sopBean.getCreatedId()));
					long id = fcService.updateInformationResult(bean);
				}
			}
		}
	}

	/**
	 * 股权交割
	 * 
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startStockequity")
	@ResponseBody
	public Object startStockequity(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String paramString) {
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> map = new HashMap<>();
		resultBean.setFlag(0);
		try {
			//获取登录用户信息
			@SuppressWarnings("unchecked")
			RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
			SessionBean b = CUtils.get().getBeanBySession(request);
			if (b==null) {
				resultBean.setMessage("获取用户信息失败");
				return resultBean;
			}
			Map<String, Object> user = BeanUtils.toMap(cache.get(b.getSessionid()));
			
			String progressHistory = "";
			Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
			if (CUtils.get().mapIsNotEmpty(paramMap)) {
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if (sopBean != null) {
					if (sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_7)) {
						// 项目当前所处在投决会阶段,在流程历史记录拼接要进入的下个阶段
						if (!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory() != null) {
							progressHistory = sopBean.getProgressHistory() + "," + StaticConst.PROJECT_PROGRESS_9;
						} else {
							progressHistory = StaticConst.PROJECT_PROGRESS_9;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_9); // 表示进入股权交割阶段
						paramMap.put("progressHistory", progressHistory); // 流程历史记录
						if (fcService.enterNextFlow(paramMap)) {
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_9);
							// 给法务生成“工商转让凭证”的待办任务
							SopTask beanLaw = new SopTask();
							int lawDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_LAW, request, response);
							beanLaw.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
							beanLaw.setTaskName(StaticConst.TASK_NAME_GSBG);
							beanLaw.setTaskType(StaticConst.TASK_TYPE_XTBG);
							beanLaw.setTaskFlag(StaticConst.TASK_FLAG_GSBG);
							beanLaw.setTaskStatus(StaticConst.TASK_STATUS_DRL);
							beanLaw.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
							beanLaw.setDepartmentId(CUtils.get().object2Long(lawDeptId));
							beanLaw.setCreatedTime(new Date().getTime());
							@SuppressWarnings("unused")
							Long law = fcService.insertsopTask(beanLaw);
							// 给财务生成“资金拨付凭证”的待办任务
							SopTask beanFd = new SopTask();
							int fdDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_FD, request, response);
							beanFd.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
							beanFd.setTaskName(StaticConst.TASK_NAME_ZJBF);
							beanFd.setTaskType(StaticConst.TASK_TYPE_XTBG);
							beanFd.setTaskFlag(StaticConst.TASK_FLAG_ZJBF);
							beanFd.setTaskStatus(StaticConst.TASK_STATUS_DRL);
							beanFd.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
							beanFd.setDepartmentId(CUtils.get().object2Long(fdDeptId));
							beanFd.setCreatedTime(new Date().getTime());
							Long fd = fcService.insertsopTask(beanFd);
							// 修改投决会排期状态为已通过
							paramMap.put("scheduleStatus", 2);
							paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
							iiService.updateInvestmentdeal(paramMap);
							resultBean.setMap(map);
							resultBean.setStatus("OK");
							//全息报告数据同步
							reportSync(sopBean,1);
							//记录操作日志
							ControllerUtils.setRequestParamsForMessageTip(request, sopBean.getProjectName(), sopBean.getId(),"");
							
							//消息
							List<Map<String, Object>> userList=new ArrayList<Map<String, Object>>();
							List<Map<String, Object>> lawDeptIdList = fcService.getUserListByDeptId(lawDeptId);
							List<Map<String, Object>> fdDeptIdList = fcService.getUserListByDeptId(fdDeptId);
							userList.addAll(lawDeptIdList);
							userList.addAll(fdDeptIdList);
							SopTask sopTask =new SopTask();
							sopTask.setId(fd);
							sopTask.setProjectName(sopBean.getProjectName());
							sopTask.setUsers(userList);
							sopTask.setMessageType("1.2.6");
							sopTask.setAssignUname(CUtils.get().object2String(user.get("userName")));
							sopTask.setCreatedId(b.getGuserid());
							sopTask.setUserName(CUtils.get().object2String(user.get("realName")));
							messageService.operateMessageSopTaskInfo(sopTask,sopTask.getMessageType());
						}else{
							resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
						}
					} else {
						resultBean.setMessage("项目当前状态已被修改，无法进入股权交割阶段");
					}
				}
			}
		} catch (Exception e) {
			log.error(InvestmentdealController.class.getName() + "startStockequity",e);
		}
		return resultBean;
	}

}
