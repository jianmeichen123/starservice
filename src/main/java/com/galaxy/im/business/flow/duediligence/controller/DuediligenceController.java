package com.galaxy.im.business.flow.duediligence.controller;

import java.sql.Timestamp;
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

import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.duediligence.service.IDuediligenceService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

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
	 * 申请投决会排期
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startInvestmentdeal")
	@ResponseBody
	public Object startInvestmentdeal(@RequestBody String paramString){
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
						}
						resultBean.setMap(map);
						resultBean.setStatus("OK");
					}else{
						resultBean.setMessage("项目当前状态已被修改，无法申请投决会排期");	
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
	public Object duediligenceList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			List<String> fileWorkTypeList = new ArrayList<String>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(paramMap!=null && paramMap.containsKey("fileWorkType")&&paramMap.get("fileWorkType").equals("尽职调查")){
				fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_1);
				fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_2);
				fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_3);
				fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_4);
				fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_18);
				fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_19);
			}
			paramMap.put("fileWorkTypeList", fileWorkTypeList);
			List<Map<String,Object>> list = fcService.getSopFileList(paramMap);
			resultBean.setMapList(list);
			resultBean.setStatus("ok");
		}catch(Exception e){
		}
		return resultBean;
	}
}
