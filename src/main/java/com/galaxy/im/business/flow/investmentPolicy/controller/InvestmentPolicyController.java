package com.galaxy.im.business.flow.investmentPolicy.controller;

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
import com.galaxy.im.business.flow.investmentPolicy.service.IInvestmentPolicyService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

/**
 * 投资协议
 * 
 * @author liyanqiao
 */
@Controller
@RequestMapping("/flow/investmentPolicy")
public class InvestmentPolicyController {

	
	@Autowired
	private IFlowCommonService fcService;
	
	@Autowired
	private IInvestmentPolicyService iPService;
	/**
	 * 判断项目操作按钮状态
	 * 依据：1.需要上传完成投资协议；同时判定该项目在“会后商务谈判”阶段的结论是“闪投”
	 * 2,需要一条结论为“通过”的会议记录；同时判定该项目在“会后商务谈判”阶段的结论是“闪投”；
	 * 前端处理：满足条件1则"进入尽职调查"按钮变亮;满足条件2则"进入股权交割"按钮变亮;
	 * 
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 * 	entity ->  "investmentpolicypass": true 上传完成了投资协议且“会后商务谈判”阶段的结论是“闪投”
	 *  entity ->  "flashpass": true, 存在通过的会议且“会后商务谈判”阶段的结论是“闪投”
	 *  
	 */
	
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> m = iPService.getInvestmentdealOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			//项目最后上传的文件信息
			paramMap.put("fileWorkType", StaticConst.FILE_WORKTYPE_6);
			Map<String,Object> map = fcService.getLatestSopFileInfo(paramMap);
			if(CUtils.get().mapIsNotEmpty(map)){
				result.setMap(map);
			}
			result.setStatus("OK");
		}catch(Exception e){
		}
		return result;
	}
	/**
	 * 进入尽职调查
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startDuediligence")
	@ResponseBody
	public Object startDuediligence(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> map = new HashMap<>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
					paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_6);	//表示进入尽职调查阶段
					if(fcService.enterNextFlow(paramMap)){
						
						resultBean.setFlag(1);
						map.put("projectProgress", StaticConst.PROJECT_PROGRESS_6);
						/*生成“尽职调查”待办任务；
						1）给投资经理自己生成业务尽职调查待办任务；
						2）给人事生成人事尽职调查待办任务；
						3）给财务生成财务尽职调查待办任务；
						4）给法务生成法务尽职调查待办任务；*/
						SessionBean sessionBean = CUtils.get().getBeanBySession(request);
						
						//给投资经理自己生成业务尽职调查待办任务
						SopTask bean = new SopTask();
						long userDeptId = fcService.getDeptId(sessionBean.getGuserid(),request,response);
						bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
						bean.setTaskName(StaticConst.TASK_NAME_YWJD);
						bean.setTaskType(StaticConst.TASK_TYPE_XTBG);
						bean.setTaskFlag(StaticConst.TASK_FLAG_YWJD);
						bean.setTaskStatus(StaticConst.TASK_STATUS_DWG);
						bean.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
						bean.setAssignUid(sessionBean.getGuserid());
						bean.setDepartmentId(userDeptId);
						bean.setCreatedTime(new Date().getTime());
						@SuppressWarnings("unused")
						Long id = fcService.insertsopTask(bean);
						
						//给人事生成人事尽职调查待办任务
						SopTask beanHr = new SopTask();
						int hrDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_HR,request,response);
						beanHr.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
						beanHr.setTaskName(StaticConst.TASK_NAME_RSJD);
						beanHr.setTaskType(StaticConst.TASK_TYPE_XTBG);
						beanHr.setTaskFlag(StaticConst.TASK_FLAG_RSJD);
						beanHr.setTaskStatus(StaticConst.TASK_STATUS_DRL);
						beanHr.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
						beanHr.setDepartmentId(CUtils.get().object2Long(hrDeptId));
						beanHr.setCreatedTime(new Date().getTime());
						@SuppressWarnings("unused")
						Long hr = fcService.insertsopTask(beanHr);
						
						//给财务生成财务尽职调查待办任务
						SopTask beanFd = new SopTask();
						int fdDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_FD,request,response);
						beanFd.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
						beanFd.setTaskName(StaticConst.TASK_NAME_CWJD);
						beanFd.setTaskType(StaticConst.TASK_TYPE_XTBG);
						beanFd.setTaskFlag(StaticConst.TASK_FLAG_CWJD);
						beanFd.setTaskStatus(StaticConst.TASK_STATUS_DRL);
						beanFd.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
						beanFd.setDepartmentId(CUtils.get().object2Long(fdDeptId));
						beanFd.setCreatedTime(new Date().getTime());
						@SuppressWarnings("unused")
						Long fd = fcService.insertsopTask(beanFd);
						
						//给法务生成法务尽职调查待办任务
						SopTask beanLaw = new SopTask();
						int lawDeptId = fcService.getDeptIdByDeptName(StaticConst.DEPT_NAME_LAW,request,response);
						beanLaw.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
						beanLaw.setTaskName(StaticConst.TASK_NAME_FWJD);
						beanLaw.setTaskType(StaticConst.TASK_TYPE_XTBG);
						beanLaw.setTaskFlag(StaticConst.TASK_FLAG_FWJD);
						beanLaw.setTaskStatus(StaticConst.TASK_STATUS_DRL);
						beanLaw.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
						beanLaw.setDepartmentId(CUtils.get().object2Long(lawDeptId));
						beanLaw.setCreatedTime(new Date().getTime());
						@SuppressWarnings("unused")
						Long law = fcService.insertsopTask(beanLaw);
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
					}
				}
			resultBean.setMap(map);
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}
	
}
