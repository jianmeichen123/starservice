package com.galaxy.im.business.flow.duediligence.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.duediligence.service.IDuediligenceService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;

/**
 * 尽职调查
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/duediligence")
public class DuediligenceController {
	
	@Autowired
	IDuediligenceService service;
	@Autowired
	private IFlowCommonService fcService;

	/**
	 * 申请投决会排期/否决项目的操作按钮状态
	 * 依据：需要上传完成业务尽调报告、人事尽调报告、法务尽调报告、财务尽调报告、尽调启动报告、尽调总结会报告
	 * 前端处理：如果都已经上传，则申请投决会排期的按钮变亮
	 * 否决项目：无条件
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 *  entity -> pass 布尔型  满足以上条件：true
	 *  entity -> veto 布尔型   true
	 */
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			paramMap.put("fileStatus2", StaticConst.FILE_STATUS_2);
			paramMap.put("fileStatus4", StaticConst.FILE_STATUS_4);
			Map<String,Object> m = service.duediligenceOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			
			//业务尽职调查的信息
			paramMap.put("fileWorkType", StaticConst.FILE_WORKTYPE_1);
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
			paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_6);
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
	 * 申请投决会排期
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startInvestmentdeal")
	@ResponseBody
	public Object startInvestmentdeal(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			String progressHistory="";
			Map<String,Object> map =new HashMap<String,Object>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if(sopBean!=null){
					if(sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_6)){
						//项目当前所处在尽职调查阶段,在流程历史记录拼接要进入的下个阶段
						if(!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory()!=null){
							progressHistory =sopBean.getProgressHistory()+","+StaticConst.PROJECT_PROGRESS_7;
						}else{
							progressHistory =StaticConst.PROJECT_PROGRESS_7;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_7);	//表示进入投决会
						paramMap.put("progressHistory", progressHistory);					//流程历史记录
						
						if(fcService.enterNextFlow(paramMap)){
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_7);
							//生成投决会排期记录
							MeetingScheduling bean = new MeetingScheduling();
							bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
							bean.setMeetingType(StaticConst.MEETING_TYPE_INVEST);
							bean.setStatus(StaticConst.MEETING_RESULT_2);
							bean.setScheduleStatus(0);
							bean.setCreatedTime(DateUtil.getMillis(new Date()));
							bean.setApplyTime(new Timestamp(new Date().getTime()));
							@SuppressWarnings("unused")
							Long id = fcService.insertMeetingScheduling(bean);
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
	 * 尽职调查信息
	 * @param paramString
	 * @return
	 */
	@RequestMapping("duediligenceList")
	@ResponseBody
	public Object duediligenceList(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			List<String> fileWorkTypeList = new ArrayList<String>();
			List<Integer> taskFlagList = new ArrayList<Integer>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_1);
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_2);
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_3);
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_4);
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_18);
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_19);
			//调查报告信息
			paramMap.put("fileWorkTypeList", fileWorkTypeList);
			List<Map<String,Object>> list = fcService.getSopFileList(paramMap);
			
			//人事，法务，财务的代办任务的状态和认领人
			taskFlagList.add(StaticConst.TASK_FLAG_RSJD);
			taskFlagList.add(StaticConst.TASK_FLAG_FWJD);
			taskFlagList.add(StaticConst.TASK_FLAG_CWJD);
			paramMap.put("taskFlagList", taskFlagList);
			List<Map<String,Object>> taskList = fcService.getSopTaskList(paramMap);
			if(taskList!=null){
				for(Map<String,Object> map:taskList){
					if(map.containsKey("assignUid")){
						//通过用户id获取一些信息
						List<Map<String, Object>> userList = fcService.getDeptId(CUtils.get().object2Long(map.get("assignUid")),request,response);
						if(userList!=null){
							for(Map<String, Object> vMap:userList){
								String userName=CUtils.get().object2String(vMap.get("userName"));
								map.put("assignName", userName);
							}
						}
					}
				}
			}
			
			resultMap.put("fileInfo", list);
			resultMap.put("taskInfo", taskList);
			resultBean.setMap(resultMap);
			resultBean.setStatus("ok");
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 上传/更新业务尽调报告、人事尽调报告、法务尽调报告、财务尽调报告、尽调启动报告、尽调总结会报告
	 * @param paramString
	 * @return
	 */
	@RequestMapping("uploadDuediligence")
	@ResponseBody
	public Object uploadDuediligence(HttpServletRequest request,HttpServletResponse response,@RequestBody SopFileBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		resultBean.setFlag(0);
		long id=0L;
		@SuppressWarnings("unused")
		int vid=0;
		int prograss = 0;
		try{
			Long deptId =0L;
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			//通过用户id获取一些信息
			List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(),request,response);
			if(list!=null){
				for(Map<String, Object> vMap:list){
					deptId= CUtils.get().object2Long( vMap.get("deptId"));
				}
			}
			
			paramMap.put("projectId", bean.getProjectId());
			paramMap.put("fileWorkType", bean.getFileWorkType());
			
			if(bean!=null){
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if(sopBean!=null && sopBean.getProjectStatus().equals(StaticConst.PROJECT_STATUS_0)
						&& sopBean.getProjectProgress().equals(bean.getProjectProgress())){
					//项目id，当前阶段，所属事业线
					bean.setProjectId(sopBean.getId());
					bean.setProjectProgress(sopBean.getProjectProgress());
					//事业线为用户部门id
					bean.setCareerLine(deptId);
					//文件类型
					String fileType =fcService.getFileType(bean.getFileSuffix());
					bean.setFileType(fileType);
					//文件名称拆分
					Map<String,String> nameMap = fcService.transFileNames(bean.getFileName());
					bean.setFileName(nameMap.get("fileName"));
					//文件状态：已上传
					bean.setFileStatus(StaticConst.FILE_STATUS_2);
					bean.setFileValid(1);
					bean.setCreatedTime(new Date().getTime());
					bean.setFileUid(sessionBean.getGuserid());
					//业务操作
					if(bean.getId()!=null && bean.getId()!=0){
						//更新：添加新的一条记录
						vid = fcService.updateValid(bean.getId());
						id = fcService.addSopFile(bean);
						prograss=1;
					}else{
						//上传之前:查数据库中是否存在信息，存在更新，否则新增
						Map<String,Object> info = fcService.getLatestSopFileInfo(paramMap);
						if(info!=null && info.get("id")!=null && CUtils.get().object2Long(info.get("id"))!=0){
							bean.setId(CUtils.get().object2Long(info.get("id")));
							bean.setUpdatedTime(new Date().getTime());
							id=fcService.updateSopFile(bean);
						}else{
							id =fcService.addSopFile(bean);
						}
					}
					if(id>0){
						//更新业务尽调报告待办任务
						SopTask taskBean = new SopTask();
						if (bean.getFileWorkType().equals(StaticConst.FILE_WORKTYPE_1)) {
							taskBean.setTaskName(StaticConst.TASK_NAME_YWJD);
							taskBean.setTaskFlag(StaticConst.TASK_FLAG_YWJD);
						}else if (bean.getFileWorkType().equals(StaticConst.FILE_WORKTYPE_2)) {//更新人事尽调报告待办任务
							taskBean.setTaskName(StaticConst.TASK_NAME_RSJD);
							taskBean.setTaskFlag(StaticConst.TASK_FLAG_RSJD);
						}else if (bean.getFileWorkType().equals(StaticConst.FILE_WORKTYPE_3)) {//更新财务尽职调查待办任务
							taskBean.setTaskName(StaticConst.TASK_NAME_CWJD);
							taskBean.setTaskFlag(StaticConst.TASK_FLAG_CWJD);
						}else if (bean.getFileWorkType().equals(StaticConst.FILE_WORKTYPE_4)) {//更新法务尽职调查待办任务
							taskBean.setTaskName(StaticConst.TASK_NAME_FWJD);
							taskBean.setTaskFlag(StaticConst.TASK_FLAG_FWJD);
						}
						taskBean.setProjectId(bean.getProjectId());
						taskBean.setTaskStatus(StaticConst.TASK_STATUS_YWG);
						taskBean.setTaskType(StaticConst.TASK_TYPE_XTBG);
						taskBean.setUpdatedTime(new Date().getTime());
						taskBean.setTaskDeadline(new Date());
						@SuppressWarnings("unused")
						Long taskId = fcService.updateSopTask(taskBean);
						//返回信息
						paramMap.clear();
						paramMap.put("fileId", id);
						resultBean.setMap(paramMap);
						resultBean.setStatus("ok");
						resultBean.setFlag(1);
					}
				}else{
					resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
				}
				if(id!=0L){
					//记录操作日志
					UrlNumber uNum = fcService.setNumForFile(prograss,bean);
					ControllerUtils.setRequestParamsForMessageTip(request, sopBean.getProjectName(), sopBean.getId(), null, uNum);
				}
			}
		}catch(Exception e){
		}
		return resultBean;
	}
}
