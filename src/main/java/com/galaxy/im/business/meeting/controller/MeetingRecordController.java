package com.galaxy.im.business.meeting.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.business.meeting.service.IMeetingRecordService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;

@Controller
@RequestMapping("/meeting")
public class MeetingRecordController {
	private Logger log = LoggerFactory.getLogger(MeetingRecordController.class);

	@Autowired
	IMeetingRecordService service;
	
	/**
	 * 会议记录列表
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("getMeetingRecordList")
	@ResponseBody
	public Object getMeetingRecordList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			QPage page = service.getMeetingRecordList(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
		}catch(Exception e){
			log.error(MeetingRecordController.class.getName() + "_getMeetingRecordList",e);
		}
		return resultBean;
	}
	
	/**
	 * 添加/编辑会议记录
	 * @param 
	 * @return
	 * @author liuli
	 */
	@RequestMapping("addMeetingRecord")
	@ResponseBody
	public Object addMeetingRecord(@RequestBody MeetingRecordBean bean){
		try {
			
		} catch (Exception e) {
			log.error(MeetingRecordController.class.getName() + "_addMeetingRecord",e);
		}
		return null;
	}
	
	/**
	 * 会议记录详情
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("meetingRecordDetails")
	@ResponseBody
	public Object meetingRecordDetails(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			MeetingRecordBean bean = new MeetingRecordBean();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			if(paramMap!=null && paramMap.containsKey("meetingRecordId")){
				bean =service.queryById(CUtils.get().object2Long(paramMap.get("meetingRecordId")));
			}
			resultBean.setStatus("ok");
			resultBean.setEntity(bean);
		} catch (Exception e) {
			log.error(MeetingRecordController.class.getName() + "_meetingRecordDetails",e);
		}
		return resultBean;
	}
}
