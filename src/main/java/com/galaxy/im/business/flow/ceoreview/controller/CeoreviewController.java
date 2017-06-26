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

import com.galaxy.im.business.flow.ceoreview.service.ICeoreviewService;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.flow.internalreview.service.IInternalreviewService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;

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
			Map<String,Object> m = service.hasPassMeeting(paramMap);
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
							paramMap.put("scheduleStatus", 3);
							paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
							icService.updateCeoScheduling(paramMap);  //修改ceo评审排期状态为已否决
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
	 * 启动立项会评审,排期
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startRovalReview")
	@ResponseBody
	public Object startRovalReview(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				paramMap.put("projectProgress", "projectProgress:4");	//表示进入立项会阶段
				if(fcService.enterNextFlow(paramMap)){
					paramMap.put("meetingType", "meetingType:3");
					paramMap.put("meetingCount", 0);
					paramMap.put("status", "meetingResult:2");
					paramMap.put("scheduleStatus", 0);
					paramMap.put("applyTime", new Timestamp(new Date().getTime()));
					paramMap.put("createdTime", new Date().getTime());
					icService.saveRovalScheduling(paramMap);  //立项会排期
					paramMap.put("scheduleStatus", 2);
					paramMap.put("updatedTime", DateUtil.getMillis(new Date()));
					icService.updateCeoScheduling(paramMap);  //修改ceo评审排期状态为已通过
					resultBean.setFlag(1);
				}
			}
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}

}
