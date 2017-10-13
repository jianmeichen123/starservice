package com.galaxy.im.business.flow.internalreview.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.internalreview.service.IInternalreviewService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

/**
 * 内部评审
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/internalreview")
public class InternalreviewController {
	@Autowired
	private IInternalreviewService service;
	
	@Autowired
	private IFlowCommonService fcService;
	
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
			paramMap.put("meetingType", StaticConst.MEETING_TYPE_INTERNAL);
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
				paramMap.put("meetingType", StaticConst.MEETING_TYPE_INTERNAL);
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
	public Object votedown(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>();
		int flag = 0;
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> rMap = new HashMap<String,Object>();
			rMap.put("flag",0);
			paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_2);
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
							reportSync(p);
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
	 * 启动ceo评审,排期
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startCeoReview")
	@ResponseBody
	public Object startCeoReview(@RequestBody String paramString,HttpServletRequest request) {
		ResultBean<Object> resultBean = new ResultBean<Object>();
		Map<String, Object> map = new HashMap<>();
		resultBean.setFlag(0);
		try {
			String progressHistory = "";
			Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
			if (CUtils.get().mapIsNotEmpty(paramMap)) {
				SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
				if (sopBean != null) {
					if (sopBean.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_2)) {
						// 项目当前所处在内部评审阶段,在流程历史记录拼接要进入的下个阶段
						if (!"".equals(sopBean.getProgressHistory()) && sopBean.getProgressHistory() != null) {
							progressHistory = sopBean.getProgressHistory() + "," + StaticConst.PROJECT_PROGRESS_3;
						} else {
							progressHistory = StaticConst.PROJECT_PROGRESS_3;
						}
						paramMap.put("projectProgress", StaticConst.PROJECT_PROGRESS_3); // 表示进入ceo评审阶段
						paramMap.put("progressHistory", progressHistory); // 流程历史记录
						if (fcService.enterNextFlow(paramMap)) {
							resultBean.setFlag(1);
							map.put("projectProgress", StaticConst.PROJECT_PROGRESS_3);
							MeetingScheduling bean = new MeetingScheduling();
							bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
							bean.setMeetingType(StaticConst.MEETING_TYPE_CEO);
							bean.setMeetingCount(0);
							bean.setStatus(StaticConst.MEETING_RESULT_2);
							bean.setScheduleStatus(0);
							bean.setCreatedTime(DateUtil.getMillis(new Date()));
							bean.setApplyTime(new Timestamp(new Date().getTime()));
							fcService.insertMeetingScheduling(bean);// ceo排期
							resultBean.setMap(map);
							resultBean.setStatus("OK");
							//全息报告数据同步
							reportSync(sopBean);
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
		}
		return resultBean;
	}

	//全息报告数据同步
	@SuppressWarnings("unused")
	private void reportSync(SopProjectBean sopBean) {
		InformationResult result=null;
		Map<String,Object> map = new HashMap<String,Object>();
		List<InformationResult> list =new ArrayList<InformationResult>();
		String choose="";
		//会议最新信息
		map.put("projectId", sopBean.getId());
		map.put("meetingType", StaticConst.MEETING_TYPE_INTERNAL);
		map.put("invest", "meeting1Result:2");
		map.put("flash", "meeting1Result:1");
		map.put("votedown", "meeting1Result:4");
		Map<String,Object> res = fcService.getMeetingRecordInfo(map);
		if(res.containsKey("meetingResultCode") && res.get("meetingResultCode")!=null){
			if(res.get("meetingResultCode").equals("meeting1Result:1")){
				choose="1142";
			}else if(res.get("meetingResultCode").equals("meeting1Result:2")){
				choose="1143";
			}else if(res.get("meetingResultCode").equals("meeting1Result:4")){
				choose="1145";
			}
		}
		map.put("parentId", "7028");
		map.put("titleId", "1111");
		list = fcService.getReportInfo(map);
		if(list.isEmpty()){
			//添加
			result =new InformationResult();
			result.setProjectId(CUtils.get().object2String(sopBean.getId()));
			result.setTitleId("1111");
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
	
}
