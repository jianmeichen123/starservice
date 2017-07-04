package com.galaxy.im.business.flow.projectapproval.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.projectapproval.service.IProjectapprovalService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

/**
 * 立项会
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/projectapproval")
public class ProjectapprovalController {
	
	@Autowired
	IProjectapprovalService service;
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 判断项目能否被否决/被通过-操作按钮状态
	 * 依据：所处阶段中该项目的会议记录是否存在已否决/须一条“闪投”或“投资”或“转向”结果的会议记录
	 * 前端处理：如果存在，则“否决项目”/“进入会后商务谈判”的按钮变亮
	 * 
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 * 	entity -> pass 布尔型  存在“闪投”或“投资”或“转向”的会议：true
	 *  entity -> veto 布尔型  存在否决的会议：true
	 */
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> m = service.approvalOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			//会议最新信息
			paramMap.put("meetingType", StaticConst.MEETING_TYPE_APPROVAL);
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
	 * @param paramString
	 * @return
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
						//否决项目
						if(fcService.vetoProject(paramMap)){
							rMap.put("flag", 1);
							rMap.put("message", "否决项目成功");
							//将立项会排期的会议结果和排期结果调整为已否决
							paramMap.put("status", StaticConst.MEETING_RESULT_3);
							paramMap.put("scheduleStatus", 3);
							paramMap.put("meetingType", StaticConst.MEETING_TYPE_APPROVAL);
							paramMap.put("updateTime", DateUtil.getMillis(new Date()));
							service.updateMeetingScheduling(paramMap);
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
	 * 进入会后商务谈判
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startBusinessNegotiation")
	@ResponseBody
	public Object startBusinessNegotiation(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			String progressHistory="";
			Map<String,Object> map =new HashMap<String,Object>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if(sopBean!=null){
					if(sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_4)){
						//项目当前所处在立项会阶段,在流程历史记录拼接要进入的下个阶段
						if(!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory()!=null){
							progressHistory =sopBean.getProgressHistory()+","+StaticConst.PROJECT_PROGRESS_11;
						}else{
							progressHistory =StaticConst.PROJECT_PROGRESS_11;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_11);	//表示进入会后商务谈判
						paramMap.put("progressHistory", progressHistory);					//流程历史记录
						if(fcService.enterNextFlow(paramMap)){
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_11);
							//会议个数
							paramMap.put("meetingType", StaticConst.MEETING_TYPE_APPROVAL);
							int count = service.getMeetingCount(paramMap);
							paramMap.put("status", StaticConst.MEETING_RESULT_1);
							paramMap.put("scheduleStatus", 2);
							paramMap.put("updateTime", DateUtil.getMillis(new Date()));
							paramMap.put("meetingCount",count);
							service.updateMeetingScheduling(paramMap);
						}
						resultBean.setMap(map);
						resultBean.setStatus("OK");
					}else{
						resultBean.setMessage("项目当前状态已被修改，无法进入会后商务谈判");	
					}
				}
			}
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 上传/更新项目立项报告
	 * @param paramString
	 * @return
	 */
	@RequestMapping("uploadApprovalReport")
	@ResponseBody
	public Object uploadApprovalReport(@RequestBody SopFileBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		resultBean.setFlag(0);
		long id=0L;
		try{
			paramMap.put("projectId", bean.getProjectId());
			paramMap.put("fileWorkType", bean.getFileWorkType());
			
			if(bean!=null){
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if(sopBean!=null){
					//项目id，当前阶段，所属事业线
					bean.setProjectId(sopBean.getId());
					bean.setProjectProgress(sopBean.getProjectProgress());
					bean.setCareerLine(sopBean.getProjectDepartId());
				}
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
				
				//业务操作
				if(bean.getId()!=null && bean.getId()!=0){
					//更新：添加新的一条记录
					id =fcService.addSopFile(bean);
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
					paramMap.clear();
					paramMap.put("fileId", id);
					resultBean.setMap(paramMap);
					resultBean.setStatus("ok");
					resultBean.setFlag(1);
				}
			}
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 项目立项报告信息
	 * @param paramString
	 * @return
	 */
	@RequestMapping("approvalReportList")
	@ResponseBody
	public Object approvalReportList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			List<String> fileWorkTypeList = new ArrayList<String>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_17);
				
			paramMap.put("fileWorkTypeList", fileWorkTypeList);
			List<Map<String,Object>> list = fcService.getSopFileList(paramMap);
			resultBean.setMapList(list);
			resultBean.setStatus("ok");
		}catch(Exception e){
		}
		return resultBean;
	}

}
