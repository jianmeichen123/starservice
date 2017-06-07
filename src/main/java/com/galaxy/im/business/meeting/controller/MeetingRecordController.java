package com.galaxy.im.business.meeting.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.meeting.service.IMeetingRecordService;
import com.galaxy.im.business.talk.service.ITalkRecordService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;

@Controller
@RequestMapping("/meeting")
public class MeetingRecordController {
	private Logger log = LoggerFactory.getLogger(MeetingRecordController.class);

	@Autowired
	IMeetingRecordService service;
	@Autowired
	ITalkRecordService talkService;
	
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
	public Object addMeetingRecord(HttpServletRequest request,HttpServletResponse response,@RequestBody MeetingRecordBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			int updateCount = 0;
			Long id = 0L;
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			
			if(bean!=null){
				//会议记录存在，进行更新操作，否则保存
				if(bean.getId()!=null && bean.getId()!=0){
					//保存sop_file
					if(!"".equals(bean.getFileKey()) && bean.getFileKey()!=null){
						SopFileBean sopFileBean =new SopFileBean();
						sopFileBean.setFileKey(bean.getFileKey());
						sopFileBean.setFileLength(bean.getFileLength());
						sopFileBean.setBucketName(bean.getBucketName());
						sopFileBean.setFileName(bean.getFileName());
						long sopId =talkService.saveSopFile(sopFileBean);
						//获取sopfile 主键
						if(sopId!=0){
							bean.setFileId(sopId);
						}
					}
					id=bean.getId();
					updateCount = service.updateById(bean);
				}else{
					//保存
					bean.setMeetingDate(DateUtil.convertStringtoD(bean.getMeetingDateStr()));
					bean.setCreatedId(sessionBean.getGuserid());
					id = service.insert(bean);
				}
			}
			
			if(updateCount!=0 || id!=0L){
				resultBean.setStatus("OK");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("meetingRecordId", id);
				resultBean.setMap(map);
			}
		}catch(Exception e){
			log.error(MeetingRecordController.class.getName() + "_addMeetingRecord",e);
		}
		return resultBean;
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
