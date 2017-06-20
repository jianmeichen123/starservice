package com.galaxy.im.business.flow.interview.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.interview.service.IInterviewService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;

/**
 * 接触访谈
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/interview")
public class InterviewController {
	@Autowired
	private IInterviewService service;
	
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 判断项目能否被否决/被通过-操作按钮状态
	 * 依据：所处阶段中该项目的会议记录是否存在已否决/已通过的会议
	 * 前端处理：如果存在，则“否决”/“通过”的按钮变亮
	 * 
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 * 	entity -> pass 布尔型  存在通过的访谈：true
	 *  entity -> veto 布尔型  存在否决的访谈：true
	 */
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> m = service.hasPassInterview(paramMap);
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
						//否决
						if(fcService.vetoProject(paramMap)){
							rMap.put("flag", 1);
							rMap.put("message", "否决项目成功");
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
	 * 启动内部评审
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startCeoReview")
	@ResponseBody
	public Object startCeoReview(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				paramMap.put("projectProgress", "projectProgress:2");	//表示进入CEO评审阶段
				if(fcService.enterNextFlow(paramMap)){
					resultBean.setFlag(1);
				}
			}
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}

}