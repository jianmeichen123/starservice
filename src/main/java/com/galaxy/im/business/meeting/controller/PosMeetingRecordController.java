package com.galaxy.im.business.meeting.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.meeting.service.IMeetingRecordService;
import com.galaxy.im.business.sopfile.service.ISopFileService;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/posmeeting")
public class PosMeetingRecordController {
	private Logger log = LoggerFactory.getLogger(PosMeetingRecordController.class);
	
	@Autowired
	IMeetingRecordService service;
	@Autowired
	ISopFileService sopFileService;
	
	/**
	 * 运营分析-会议详情
	 * @param paramString
	 * @return
	 */
	@RequestMapping("postMeetingDetail")
	@ResponseBody
	public Object postMeetingDetail(@RequestBody MeetingRecordBean meetingRecord){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try{
			if(null == meetingRecord.getId()){
				resultBean.setMessage("缺少重要参数！");
				return resultBean;
			}
			
			//查询会议相关
			meetingRecord.setRecordType(2);
			Map<String,Object> record= service.postMeetingDetail(meetingRecord);
			if(null == record){
				resultBean.setMessage("输入参数不正确！未查到结果");
				return resultBean;
			}
			//查询附件
			SopFileBean sopfile = new SopFileBean();
		    sopfile.setMeetingId(meetingRecord.getId());
			List<Map<String,Object>> sopFileList = new ArrayList<Map<String,Object>>();
			sopFileList = sopFileService.getSopFileList(sopfile);
			record.put("files", sopFileList);
			resultBean.setEntity(record);
			resultBean.setStatus("OK");
			return resultBean;
		}catch(Exception e){
			log.error(PosMeetingRecordController.class.getName() + "postMeetingDetail",e);
		}
		return resultBean;
	}
}
