package com.galaxy.im.common.messageThread;

import java.util.ArrayList;
import java.util.Date;
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
		
		long sendTime = new Date().getTime();
		/*if(sendTime<System.currentTimeMillis()){
			return;
		}*/
		
		StringBuffer content = new StringBuffer();
		StringBuffer con = new StringBuffer();
		if(model.getMessageType().equals(sop_task_1)){
			//认领
			ScheduleMessageBean message =getScheduleMessageInfo(model,0);
			content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
			content.append("认领了");
			content.append("\"").append("<pname>").append(model.getProjectName()).append("</pname>\"");
			content.append("的尽调任务");
			message.setSendTime(sendTime);
			message.setContent(content.toString());
			list.add(message);
		}else if(model.getMessageType().equals(sop_task_2)){
			//移交
			//该任务的接收人
			ScheduleMessageBean message1 =getScheduleMessageInfo(model,0);
			content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
			content.append("向您移交了将");
			content.append("\"").append("<pname>").append(model.getProjectName()).append("</pname>\"");
			content.append("的").append(model.getTaskName());
			message1.setSendTime(sendTime);
			message1.setContent(content.toString());
			//该项目的投资经理
			ScheduleMessageBean message2 =getScheduleMessageInfo(model,1);
			con.append("\"<pname>").append(model.getUserName()).append("</pname>\"");
			con.append("的").append(model.getTaskName()).append("负责人变更为");
			con.append("\"").append("<uname>").append(model.getProjectName()).append("</uname>\"");
			message2.setSendTime(sendTime);
			message2.setContent(con.toString());
			list.add(message1);
			list.add(message2);
		}else if(model.getMessageType().equals(sop_task_3)){
			//放弃
			ScheduleMessageBean message =getScheduleMessageInfo(model,0);
			content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
			content.append("放弃了");
			content.append("\"").append("<pname>").append(model.getProjectName()).append("</pname>\"");
			content.append("的").append(model.getTaskName());
			message.setSendTime(sendTime);
			message.setContent(content.toString());
			list.add(message);
		}else if(model.getMessageType().equals(sop_task_4)){
			//指派
			//该任务的接收人（被指派人）
			ScheduleMessageBean message1 =getScheduleMessageInfo(model,0);
			content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
			content.append("向您指派了将");
			content.append("\"").append("<pname>").append(model.getProjectName()).append("</pname>\"");
			content.append("的").append(model.getTaskName());
			message1.setSendTime(sendTime);
			message1.setContent(content.toString());
			//该项目的投资经理
			ScheduleMessageBean message2 =getScheduleMessageInfo(model,1);
			con.append("\"<pname>").append(model.getUserName()).append("</pname>\"");
			con.append("的").append(model.getTaskName()).append("负责人为");
			con.append("\"").append("<uname>").append(model.getProjectName()).append("</uname>\"");
			message2.setSendTime(sendTime);
			message2.setContent(con.toString());
			list.add(message1);
			list.add(message2);
		}
	}

	
	//初始化消息公用部分
	private ScheduleMessageBean getScheduleMessageInfo(SopTask model,int flag) {
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
		
		List<ScheduleMessageUserBean> toUsers = new ArrayList<ScheduleMessageUserBean>();
		
		if(flag==0){
			//接收人
			ScheduleMessageUserBean toU = new ScheduleMessageUserBean();
			toU.setUid(model.getAssignUid());
			toU.setUname(model.getAssignUname());
			toUsers.add(toU);
		}else if(flag==1){
			//项目创建人
			for(int i=0;i<model.getProjectCreatedId().size();i++){
				ScheduleMessageUserBean toU = new ScheduleMessageUserBean();
				toU.setUid(model.getProjectCreatedId().get(i));
				toU.setUname(model.getProjectCreatedName().get(i));
				toUsers.add(toU);
			}
		}
		//消息接收人
		message.setToUsers(toUsers);
		return message;
	}

}
