package com.galaxy.im.business.flow.interview.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.interview.service.IInterviewService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

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
			Map<String,Object> map = service.getInterviewCount(paramMap);
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
			paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_1);
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
							result.setStatus("OK");
						}else{
							rMap.put("message", "项目当前状态或进度已被修改，请刷新");
						}
					}else{
						rMap.put("flag", 0);
						rMap.put("message", CUtils.get().object2String(statusMap.get("message"), "未知错误"));
					}
				}
			}
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
	@RequestMapping("startInternalreview")
	@ResponseBody
	public Object startCeoReview(@RequestBody String paramString) {
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try {
			String progressHistory = "";
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
			if (CUtils.get().mapIsNotEmpty(paramMap)) {
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if (sopBean != null) {
					if (sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_1)) {
						// 项目当前所处在接触访谈阶段,在流程历史记录拼接要进入的下个阶段
						if (!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory() != null) {
							progressHistory = sopBean.getProgressHistory() + "," + StaticConst.PROJECT_PROGRESS_2;
						} else {
							progressHistory = StaticConst.PROJECT_PROGRESS_2;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_2); // 表示进入内部评审阶段
						paramMap.put("progressHistory", progressHistory); // 流程历史记录
						if (fcService.enterNextFlow(paramMap)) {
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_2);
							resultBean.setMap(map);
							resultBean.setStatus("OK");
						}else{
							resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
						}
					} else {
						resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
					}

				}
			}
		} catch (Exception e) {
		}
		return resultBean;
	}

}
