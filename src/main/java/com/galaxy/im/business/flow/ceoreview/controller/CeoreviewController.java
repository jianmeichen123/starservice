package com.galaxy.im.business.flow.ceoreview.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.business.flow.ceoreview.service.ICeoreviewService;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.internalreview.service.IInternalreviewService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

/**
 * CEO评审
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/ceoreview")
public class CeoreviewController {
	@Autowired
	private IInternalreviewService service;
	
	@Autowired
	private IFlowCommonService fcService;
	
	@Autowired
	private ICeoreviewService icService;
	
	/**
	 * 判断项目能否被否决/被通过-操作按钮状态
	 * 依据：所处阶段中该项目的会议记录是否存在已否决/已通过的会议
	* @Title: projectOperateStatus  
	* @author xiaochuang 
	* @throws
	 */
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			paramMap.put("meetingType", StaticConst.MEETING_TYPE_CEO);
			Map<String,Object> m = service.hasPassMeeting(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			//会议最新信息
			Map<String,Object> map = fcService.getLatestMeetingRecordInfo(paramMap);
			if(CUtils.get().mapIsNotEmpty(map)){
				result.setMap(map);
			}else {
				paramMap.clear();
				paramMap.put("meetingType", StaticConst.MEETING_TYPE_CEO);
				result.setMap(paramMap);
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
							icService.updateCeoScheduling(paramMap);  //修改ceo评审排期状态为已否决
						}
					}else{
						rMap.put("flag", 0);
						rMap.put("message", CUtils.get().object2String(statusMap.get("message"), "项目当前状态或进度已被修改，请刷新"));
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
	 * 启动立项会评审,排期
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startRovalReview")
	@ResponseBody
	public Object startRovalReview(@RequestBody String paramString) {
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> map = new HashMap<>();
		resultBean.setFlag(0);
		try {
			String progressHistory = "";
			Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
			if (CUtils.get().mapIsNotEmpty(paramMap)) {
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if (sopBean != null) {
					if (sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_3)) {
						//项目当前所处在ceo评审阶段,在流程历史记录拼接要进入的下个阶段
						if (!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory() != null) {
							progressHistory = sopBean.getProgressHistory() + "," + StaticConst.PROJECT_PROGRESS_4;
						} else {
							progressHistory = StaticConst.PROJECT_PROGRESS_4;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_4); // 表示进入立项会阶段
						paramMap.put("progressHistory", progressHistory); // 流程历史记录
						if (fcService.enterNextFlow(paramMap)) {
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_4);
							MeetingScheduling bean = new MeetingScheduling();
							bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
							bean.setMeetingType(StaticConst.MEETING_TYPE_APPROVAL);
							bean.setMeetingCount(0);
							bean.setStatus(StaticConst.MEETING_RESULT_2);
							bean.setScheduleStatus(0);
							bean.setCreatedTime(DateUtil.getMillis(new Date()));
							bean.setApplyTime(new Timestamp(new Date().getTime()));
							fcService.insertMeetingScheduling(bean);// 立项会排期
							paramMap.put("scheduleStatus", 2);
							paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
							icService.updateCeoScheduling(paramMap); // 修改ceo评审排期状态为已通过
							resultBean.setMap(map);
							resultBean.setStatus("OK");
						}else{
							resultBean.setMessage("项目当前状态或进度已被修改，请刷新");
						}
					} else {
						resultBean.setMessage("项目当前状态已被修改，无法进入立项会阶段");
					}
				}
			}
		} catch (Exception e) {
		}
		return resultBean;
	}

}
