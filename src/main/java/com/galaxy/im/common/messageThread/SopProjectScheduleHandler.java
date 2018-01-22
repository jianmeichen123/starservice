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
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.CUtils;

@Component
public class SopProjectScheduleHandler implements SopTaskScheduleMessageHandler
{
	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(VisitScheduleHandler.class);
	
	
	private String sop_project_1  = "1.1.1";
	private String sop_project_2  = "1.1.2";
	private String sop_project_3  = "1.1.3";
	private String sop_project_4  = "1.1.4";
	
	private Map<String,String> map = new HashMap<String,String>();
	public SopProjectScheduleHandler(){
		map.put(sop_project_1,  "项目删除");
		map.put(sop_project_2,  "项目移交");
		map.put(sop_project_3,  "项目指派 ");
		map.put(sop_project_4,  "项目分享 ");
	}
	

	public boolean support(Object info,String type) {
		//SopProjectBean message = (SopProjectBean) info;
		return  type != null && (map.containsKey(type) || type.startsWith("1.1"));
	}
	
	public void handle(List<ScheduleMessageBean> list,Object info) {
		
		SopProjectBean model = (SopProjectBean) info;
		
		long sendTime = new Date().getTime()+ (long) 4 * 60 * 1000;
	
		if(model.getMessageType().equals(sop_project_1)){
			//项目删除
			for(Map<String, Object> map:model.getProjects()){
				StringBuffer content = new StringBuffer();
				ScheduleMessageBean message =getScheduleMessageInfo(model,map,0);
				content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
				content.append("删除了您的项目");
				content.append("\"").append("<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				content.append(",删除原因：\"").append("<msg>").append(model.getDeleteReason()).append("</msg>\"");
				message.setSendTime(sendTime);
				message.setContent(content.toString());
				list.add(message);
			}
		}else if(model.getMessageType().equals(sop_project_2)){
			//项目移交
			for(Map<String, Object> map:model.getProjects()){
				//该任务的接收人
				StringBuffer content = new StringBuffer();
				ScheduleMessageBean message1 =getScheduleMessageInfo(model,map,0);
				content.append("\"<uname>").append(model.getUserName()).append("</uname>\"(");
				content.append("<dname>").append(model.getUserDeptName()).append("</dname>)");
				content.append("将项目\"");
				content.append("<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				content.append("移交给你");
				message1.setSendTime(sendTime);
				message1.setContent(content.toString());
				list.add(message1);
			}
		}else if(model.getMessageType().equals(sop_project_3)){
			//项目指派
			for(Map<String, Object> map:model.getProjects()){
				//该任务的接收人
				StringBuffer content = new StringBuffer();
				ScheduleMessageBean message1 =getScheduleMessageInfo(model,map,0);
				content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
				content.append("将项目\"");
				content.append("<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				content.append("指派给你");
				message1.setSendTime(sendTime);
				message1.setContent(content.toString());
				list.add(message1);
			}
		}else if(model.getMessageType().equals(sop_project_4)){
			//项目分享
			for(Map<String, Object> map:model.getProjects()){
				//该任务的接收人
				StringBuffer content = new StringBuffer();
				ScheduleMessageBean message1 =getScheduleMessageInfo(model,map,1);
				content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
				content.append("向您分享了外部项目\"");
				content.append("<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				message1.setSendTime(sendTime);
				message1.setContent(content.toString());
				list.add(message1);
			}
		}
	}

	
	//初始化消息公用部分 model
	private ScheduleMessageBean getScheduleMessageInfo(SopProjectBean model, Map<String, Object> map,int flag) {
		ScheduleMessageBean message = new ScheduleMessageBean();
			
		message.setRemarkId(CUtils.get().object2String(map.get("projectId")));
		
		message.setStatus((byte) 1);    // 0:可用 1:禁用  2:删除
		//0:操作消息  1:系统消息
		message.setCategory((byte) 1);  
		//消息类型
		message.setType(model.getMessageType());         
		
		message.setCreatedUid(model.getUserId());
		message.setCreatedUname(model.getUserName());
		
		List<ScheduleMessageUserBean> toUsers = new ArrayList<ScheduleMessageUserBean>();
		
		if(flag==0){
			//接收人
			ScheduleMessageUserBean toU = new ScheduleMessageUserBean();
			toU.setUid(model.getCreateUid());
			toU.setUname(model.getCreateUname());
			toUsers.add(toU);
		}else if(flag==1){
			//分享给的用户
			for(Map<String, Object> m:model.getUsers()){
				ScheduleMessageUserBean toU = new ScheduleMessageUserBean();
				toU.setUid(CUtils.get().object2Long(m.get("userId")));
				toU.setUname(CUtils.get().object2String(m.get("userName")));
				toUsers.add(toU);
			}
		}
		
		//消息接收人
		message.setToUsers(toUsers);
		return message;
	}

}
