package com.galaxy.im.business.flow.businessnegotiation.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.business.flow.businessnegotiation.service.IBusinessnegotiationService;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

/**
 * 商务谈判
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/businessnegotiation")
public class BusinessnegotiationController {
	
	@Autowired
	IBusinessnegotiationService service;
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 项目否决/投资意向书（投资）/投资协议（闪投）操作按钮状态
	 * 依据：所处阶段中该项目的会议记录，1:是否存在"否决"的会议,2:是否存在"闪投"的会议,3:是否存在"投资"的会议
	 * 前端处理：如果存在，则项目否决/投资意向书（投资）/投资协议（闪投）的按钮变亮
	 * 
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 * 	entity -> flashpass   布尔型  存在"闪投"的会议：true
	 *  entity -> investpass  布尔型  存在"投资"的会议：true
	 *  entity -> veto        布尔型  存在否决的会议：true
	 */
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			paramMap.put("meetingType", StaticConst.MEETING_TYPE_BUSINESS);
			Map<String,Object> m = service.businessOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			//会议最新信息
			Map<String,Object> map = fcService.getLatestMeetingRecordInfo(paramMap);
			if(CUtils.get().mapIsNotEmpty(map)){
				result.setMap(map);
			}else {
				paramMap.clear();
				paramMap.put("meetingType", StaticConst.MEETING_TYPE_BUSINESS);
				result.setMap(paramMap);
			}
			result.setStatus("OK");
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 否决项目
	 * @param paramString
	 * @return
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
			paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_11);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				SopProjectBean p = fcService.getSopProjectInfo(paramMap);
				//验证该项目的状态，查看能否进行操作
				Map<String,Object> statusMap = fcService.projectStatus(paramMap);
				if(CUtils.get().mapIsNotEmpty(statusMap)){
					flag = CUtils.get().object2Integer(statusMap.get("flag"));
					if(flag==1){
						//否决项目
						if(fcService.vetoProject(paramMap)){
							rMap.put("flag", 1);
							result.setMessage("否决项目成功");
							result.setStatus("OK");
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
		}
		return result;
	}
	
	/**
	 * 进入投资协议（闪投）
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startInvestmentPolicy")
	@ResponseBody
	public Object startInvestmentPolicy(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			long deptId=0l;
			String progressHistory="";
			Map<String,Object> map =new HashMap<String,Object>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if(sopBean!=null){
					if(sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_11)){
						//项目当前所处在商务谈判阶段,在流程历史记录拼接要进入的下个阶段
						if(!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory()!=null){
							progressHistory =sopBean.getProgressHistory()+","+StaticConst.PROJECT_PROGRESS_8;
						}else{
							progressHistory =StaticConst.PROJECT_PROGRESS_8;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_8);		//表示进入投资协议（闪投）
						paramMap.put("businessTypeCode", "ST");									//闪投
						paramMap.put("progressHistory", progressHistory);						//流程历史记录
						//更新项目
						if(service.updateProjectStatus(paramMap)){
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_8);
							//生成投资协议代办任务
							SessionBean sessionBean = CUtils.get().getBeanBySession(request);
							//获取用户所属部门id
							List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(),request,response);
							if(list!=null){
								for(Map<String, Object> vMap:list){
									deptId= CUtils.get().object2Long( vMap.get("deptId"));
								}
							}
							SopTask bean = new SopTask();
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
							Long id = fcService.insertsopTask(bean);
							resultBean.setMap(map);
							resultBean.setStatus("OK");
							//记录操作日志
							ControllerUtils.setRequestParamsForMessageTip(request, sopBean.getProjectName(), sopBean.getId(),"");
						}else{
							resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
						}
					}else{
						resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
					}
				}
			}
		}catch(Exception e){
		}
		return resultBean;
	}

	/**
	 * 进入投资意向书（投资）
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startInvestmentIntent")
	@ResponseBody
	public Object startInvestmentIntent(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			long deptId=0l;
			String progressHistory="";
			Map<String,Object> map =new HashMap<String,Object>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if(sopBean!=null){
					if(sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_11)){
						//项目当前所处在商务谈判阶段,在流程历史记录拼接要进入的下个阶段
						if(!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory()!=null){
							progressHistory =sopBean.getProgressHistory()+","+StaticConst.PROJECT_PROGRESS_5;
						}else{
							progressHistory =StaticConst.PROJECT_PROGRESS_5;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_5);		//表示进入投资意向书（投资）
						paramMap.put("businessTypeCode", "TZ");									//投资
						paramMap.put("progressHistory", progressHistory);						//流程历史记录
						//更新项目状态
						if(service.updateProjectStatus(paramMap)){
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_5);
							//生成投资意向书的代办任务
							SessionBean sessionBean = CUtils.get().getBeanBySession(request);
							//获取用户所属部门id
							List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(),request,response);
							if(list!=null){
								for(Map<String, Object> vMap:list){
									deptId= CUtils.get().object2Long( vMap.get("deptId"));
								}
							}
							SopTask bean = new SopTask();
							bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
							bean.setTaskName(StaticConst.TASK_NAME_SCTZYXS);
							bean.setTaskType(StaticConst.TASK_TYPE_XTBG);
							bean.setTaskFlag(StaticConst.TASK_FLAG_SCTZYXS);
							bean.setTaskStatus(StaticConst.TASK_STATUS_DWG);
							bean.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
							bean.setAssignUid(sessionBean.getGuserid());
							bean.setDepartmentId(deptId);
							bean.setCreatedTime(new Date().getTime());
							@SuppressWarnings("unused")
							Long id = fcService.insertsopTask(bean);
							resultBean.setMap(map);
							resultBean.setStatus("OK");
							//记录操作日志
							ControllerUtils.setRequestParamsForMessageTip(request, sopBean.getProjectName(), sopBean.getId(),"");
						}else{
							resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
						}
					}else{
						resultBean.setMessage("项目当前状态或进度已被修改，请刷新");	
					}
				}
			}
		}catch(Exception e){
		}
		return resultBean;
	}
	
}
