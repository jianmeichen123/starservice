package com.galaxy.im.business.flow.projectapproval.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.projectapproval.service.IProjectapprovalService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;

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
							paramMap.put("status", "meetingResult:3");
							paramMap.put("scheduleStatus", 3);
							paramMap.put("meetingType", "meetingType:3");
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
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				paramMap.put("projectProgress", "projectProgress:11");	//表示进入会后商务谈判
				if(fcService.enterNextFlow(paramMap)){
					resultBean.setFlag(1);
					paramMap.put("meetingType", "meetingType:3");
					int count = service.getMeetingCount(paramMap);
					paramMap.put("status", "meetingResult:1");
					paramMap.put("scheduleStatus", 2);
					paramMap.put("meetingType", "meetingType:3");
					paramMap.put("updateTime", DateUtil.getMillis(new Date()));
					paramMap.put("meetingCount",count);
					service.updateMeetingScheduling(paramMap);
				}
			}
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}

}
