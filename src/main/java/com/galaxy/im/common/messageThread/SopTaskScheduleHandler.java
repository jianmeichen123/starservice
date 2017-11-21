package com.galaxy.im.common.messageThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.galaxy.im.bean.message.ScheduleMessageBean;
import com.galaxy.im.bean.message.ScheduleMessageUserBean;
import com.galaxy.im.bean.soptask.SopTask;

@Component
public class SopTaskScheduleHandler implements SopTaskScheduleMessageHandler
{
	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(VisitScheduleHandler.class);
	
	
	private String sop_task_1  = "1.2.1";
	private String sop_task_2  = "1.2.2";
	private String sop_task_3  = "1.2.3";
	private String sop_task_4  = "1.2.4";
	
	
	private Map<String,String> map = new HashMap<String,String>();
	public SopTaskScheduleHandler(){
		map.put(sop_task_1,  "认领   代办任务 ");
		map.put(sop_task_2,  "移交  代办任务 ");
		map.put(sop_task_3,  "放弃   代办任务 ");
		map.put(sop_task_4,  "指派   代办任务 ");
	}
	

	public boolean support(Object info) {
		SopTask message = (SopTask) info;
		return  message != null && (map.containsKey(message.getMessageType()) || message.getMessageType().startsWith("1.2"));
	}
	
	public void handle(List<ScheduleMessageBean> list,Object info) {
		
		SopTask model = (SopTask) info;
		
		long sendTime = model.getCreatedTime();
		if(sendTime<System.currentTimeMillis()){
			return;
		}
		
		StringBuffer content = new StringBuffer();
		//StringBuffer content1 = new StringBuffer();
		if(model.getMessageType().equals(sop_task_1)){
			//认领
			ScheduleMessageBean message =getScheduleMessageInfo(model);
			content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
			content.append("认领了");
			content.append("\"").append("<pname>").append(model.getProjectName()).append("</pname>\"");
			content.append("的尽调任务");
			message.setSendTime(sendTime);
			message.setContent(content.toString());
			list.add(message);
		}else if(model.getMessageType().equals(sop_task_2)){
			//移交
			ScheduleMessageBean message1 =getScheduleMessageInfo(model);
			content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
			content.append("认领了");
			content.append("\"").append("<pname>").append(model.getProjectName()).append("</pname>\"");
			content.append("的尽调任务");
			message1.setSendTime(sendTime);
			message1.setContent(content.toString());
			list.add(message1);
		}else if(model.getMessageType().equals(sop_task_3)){
			//放弃
		}else if(model.getMessageType().equals(sop_task_4)){
			//指派
		}
	}

	

	private ScheduleMessageBean getScheduleMessageInfo(SopTask model) {
		ScheduleMessageBean message = new ScheduleMessageBean();
		Long info_id = model.getId();
		
		message.setStatus((byte) 1);    // 0:可用 1:禁用  2:删除
		//0:操作消息  1:系统消息
		message.setCategory((byte) 1);  
		//消息类型
		message.setType("1.2");         
		message.setRemarkId(info_id);
		message.setCreatedUid(model.getCreatedId());
		message.setCreatedUname(model.getUserName());
		
		//消息接收人
		List<ScheduleMessageUserBean> toUsers = new ArrayList<ScheduleMessageUserBean>();
		ScheduleMessageUserBean toU = new ScheduleMessageUserBean();
		toU.setUid(message.getCreatedUid());
		toU.setUname(message.getCreatedUname());
		toUsers.add(toU);
		message.setToUsers(toUsers);
		return message;
	}

}
