package com.galaxy.im.business.flow.investmentdeal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.investmentdeal.service.IInvestmentdealService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

/**
 * 投决会
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/investmentdeal")
public class InvestmentdealController {

	
	@Autowired
	private IFlowCommonService fcService;
	
	@Autowired
	private IInvestmentdealService iiService;

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
	 *  entity ->  "inverstpass": true, 存在“会后商务谈判”阶段的结论是“投资”
	 * 	entity ->  "flashpass": true 存在通过的会议且“会后商务谈判”阶段的结论是“闪投”
	 *  entity ->  ""veto": true,"  不存在通过的会议
	 */
	
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			//Map<String,Object> m = service.hasPassMeeting(paramMap);
			Map<String,Object> m = iiService.getInvestmentdealOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			//会议最新信息
			paramMap.put("meetingType",StaticConst.MEETING_TYPE_INVEST);
			Map<String,Object> map = fcService.getLatestMeetingRecordInfo(paramMap);
			if(CUtils.get().mapIsNotEmpty(map)){
				result.setMap(map);
			}
			result.setStatus("OK");
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 否决项目
	 */
	@RequestMapping("votedown")
	@ResponseBody
	public Object votedown(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		int flag = 0;
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> rMap = new HashMap<String,Object>();
			rMap.put("flag",0);
			rMap.put("message", "未知错误");
			
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				//验证该项目的状态，查看能否进行操作
				Map<String,Object> statusMap = fcService.projectStatus(paramMap);
				if(CUtils.get().mapIsNotEmpty(statusMap)){
					flag = CUtils.get().object2Integer(statusMap.get("flag"));
					if(flag==1){
						//否决
						if(fcService.vetoProject(paramMap)){
							rMap.put("flag", 1);
							rMap.put("message", "否决项目成功");
							paramMap.put("scheduleStatus", 3);
							paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
							iiService.updateInvestmentdeal(paramMap);  //修改投决会评审排期状态为已否决
						}
					}else{
						rMap.put("flag", 0);
						rMap.put("message", CUtils.get().object2String(statusMap.get("message"), "未知错误"));
					}
				}
			}
			result.setStatus("OK");
			result.setEntity(rMap);
			
		}catch(Exception e){
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
	public Object startInvestmentpolicy(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> map = new HashMap<>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				
				
					paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_8);	//表示进入投资协议阶段
					if(fcService.enterNextFlow(paramMap)){
						
						resultBean.setFlag(1);
						map.put("projectProgress", StaticConst.PROJECT_PROGRESS_8);
						//生成投资协议代办任务
						SessionBean sessionBean = CUtils.get().getBeanBySession(request);
						//获取用户所属部门id
						long userDeptId = fcService.getDeptId(sessionBean.getGuserid(),request,response);
						SopTask bean = new SopTask();
						bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
						bean.setTaskName(StaticConst.TASK_NAME_TZXY);
						bean.setTaskType(StaticConst.TASK_TYPE_XTBG);
						bean.setTaskFlag(StaticConst.TASK_FLAG_TZXY);
						bean.setTaskStatus(StaticConst.TASK_STATUS_DRL);
						bean.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
						bean.setAssignUid(sessionBean.getGuserid());
						bean.setDepartmentId(userDeptId);
						bean.setCreatedTime(new Date().getTime());
						@SuppressWarnings("unused")
						Long id = fcService.insertsopTask(bean);
						//修改投决会排期状态为已通过
						paramMap.put("scheduleStatus", 2);
						paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
						iiService.updateInvestmentdeal(paramMap);  
					}
				}
			resultBean.setMap(map);
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}
	
	
	/**
	 * 股权交割
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startStockequity")
	@ResponseBody
	public Object startStockequity(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> map = new HashMap<>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
					paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_9);	//表示进入股权交割阶段
					if(fcService.enterNextFlow(paramMap)){
						resultBean.setFlag(1);
						map.put("projectProgress", StaticConst.PROJECT_PROGRESS_9);
						//给法务生成“工商转让凭证”的待办任务
						SopTask beanLaw = new SopTask();
						int lawDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_LAW,request,response);
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
						
						//给财务生成“资金拨付凭证”的待办任务
						SopTask beanFd = new SopTask();
						int fdDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_FD,request,response);
						beanFd.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
						beanFd.setTaskName(StaticConst.TASK_NAME_ZJBF);
						beanFd.setTaskType(StaticConst.TASK_TYPE_XTBG);
						beanFd.setTaskFlag(StaticConst.TASK_FLAG_ZJBF);
						beanFd.setTaskStatus(StaticConst.TASK_STATUS_DRL);
						beanFd.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
						beanFd.setDepartmentId(CUtils.get().object2Long(fdDeptId));
						beanFd.setCreatedTime(new Date().getTime());
						@SuppressWarnings("unused")
						Long fd = fcService.insertsopTask(beanFd);
						
						//修改投决会排期状态为已通过
						paramMap.put("scheduleStatus", 2);
						paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
						iiService.updateInvestmentdeal(paramMap);  
					}
				}
			resultBean.setMap(map);
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}
	
	

		
}
