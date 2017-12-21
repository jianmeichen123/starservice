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
import com.galaxy.im.common.CUtils;

@Component
public class SopTaskScheduleHandler implements SopTaskScheduleMessageHandler
{
	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(VisitScheduleHandler.class);
	
	
	private String sop_task_1  = "1.2.1";
	private String sop_task_2  = "1.2.2";
	private String sop_task_3  = "1.2.3";
	private String sop_task_4  = "1.2.4";
	private String sop_task_5  = "1.2.5";
	private String sop_task_6  = "1.2.6";
	
	private Map<String,String> map = new HashMap<String,String>();
	public SopTaskScheduleHandler(){
		map.put(sop_task_1,  "认领   代办任务 ");
		map.put(sop_task_2,  "移交  代办任务 ");
		map.put(sop_task_3,  "放弃   代办任务 ");
		map.put(sop_task_4,  "指派   代办任务 ");
		map.put(sop_task_5,  "尽职调查   代办任务 ");
		map.put(sop_task_6,  "股权交割   代办任务 ");
	}
	

	public boolean support(Object info) {
		SopTask message = (SopTask) info;
		return  message != null && (map.containsKey(message.getMessageType()) || message.getMessageType().startsWith("1.2"));
	}
	
	public void handle(List<ScheduleMessageBean> list,Object info) {
		
		SopTask model = (SopTask) info;
		
		long sendTime = new Date().getTime();
		
		//转taskname
		if(model.getProjects()!=null){
			for(Map<String, Object> map:model.getProjects()){
				if(map.containsKey("taskName") && map.get("taskName")!=null){
					if(CUtils.get().object2String(map.get("taskName")).contains("人事")){
						map.put("taskName", "人事尽调任务");
					}else if(CUtils.get().object2String(map.get("taskName")).contains("法务")){
						map.put("taskName", "法务尽调任务");
					}else if(CUtils.get().object2String(map.get("taskName")).contains("财务")){
						map.put("taskName", "财务尽调任务");
					}else if(CUtils.get().object2String(map.get("taskName")).contains("工商")){
						map.put("taskName", "工商转让凭证任务");
					}else if(CUtils.get().object2String(map.get("taskName")).contains("资金")){
						map.put("taskName", "资金拨付凭证任务");
					}
				}
			}
		}
		if(model.getMessageType().equals(sop_task_1)){
			//认领
			for(Map<String, Object> map:model.getProjects()){
				StringBuffer content = new StringBuffer();
				ScheduleMessageBean message =getScheduleMessageInfo(model,1,map);
				content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
				content.append("认领了");
				content.append("\"").append("<pname>").append(model.getProjectName()).append("</pname>\"");
				content.append("的").append(CUtils.get().object2String(map.get("taskName")));
				message.setSendTime(sendTime);
				message.setContent(content.toString());
				list.add(message);
			}
		}else if(model.getMessageType().equals(sop_task_2)){
			//移交
			for(Map<String, Object> map:model.getProjects()){
				//该任务的接收人
				StringBuffer content = new StringBuffer();
				StringBuffer con = new StringBuffer();
				ScheduleMessageBean message1 =getScheduleMessageInfo(model,0,map);
				content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
				content.append("向您移交了").append("\"");
				content.append("<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				content.append("的").append(CUtils.get().object2String(map.get("taskName")));
				message1.setSendTime(sendTime);
				message1.setContent(content.toString());
				//该项目的投资经理
				ScheduleMessageBean message2 =getScheduleMessageInfo(model,1,map);
				con.append("\"<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				con.append("的").append(CUtils.get().object2String(map.get("taskName"))).append("负责人变更为").append("\"");
				con.append("<uname>").append(model.getAssignUid()).append("</uname>\"");
				message2.setSendTime(sendTime);
				message2.setContent(con.toString());
				list.add(message1);
				list.add(message2);
			}
		}else if(model.getMessageType().equals(sop_task_3)){
			//放弃
			for(Map<String, Object> map:model.getProjects()){
				StringBuffer content = new StringBuffer();
				content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
				content.append("放弃了").append("\"");
				content.append("<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				content.append("的").append(CUtils.get().object2String(map.get("taskName")));
				//该项目的投资经理
				ScheduleMessageBean message1 =getScheduleMessageInfo(model,1,map);
				message1.setSendTime(sendTime);
				message1.setContent(content.toString());
				list.add(message1);
				//所有人（按部门识别）
				ScheduleMessageBean message2 =getScheduleMessageInfo(model,2,null);
				message2.setSendTime(sendTime);
				message2.setContent(content.toString());
				list.add(message2);
			}
		}else if(model.getMessageType().equals(sop_task_4)){
			//指派
			for(Map<String, Object> map:model.getProjects()){
				StringBuffer content = new StringBuffer();
				StringBuffer con = new StringBuffer();
				//该任务的接收人（被指派人）
				ScheduleMessageBean message1 =getScheduleMessageInfo(model,0,map);
				content.append("\"<uname>").append(model.getUserName()).append("</uname>\"");
				content.append("向您指派了").append("\"");
				content.append("<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				content.append("的").append(CUtils.get().object2String(map.get("taskName")));
				message1.setSendTime(sendTime);
				message1.setContent(content.toString());
				//该项目的投资经理
				ScheduleMessageBean message2 =getScheduleMessageInfo(model,1,map);
				con.append("\"<pname>").append(CUtils.get().object2String(map.get("projectName"))).append("</pname>\"");
				con.append("的").append(CUtils.get().object2String(map.get("taskName"))).append("负责人为").append("\"");
				con.append("<uname>").append(model.getAssignUid()).append("</uname>\"");
				message2.setSendTime(sendTime);
				message2.setContent(con.toString());
				list.add(message1);
				list.add(message2);
			}
		}else if(model.getMessageType().equals(sop_task_5)){
			//放弃
			StringBuffer content = new StringBuffer();
			content.append("\"<pname>").append(model.getProjectName()).append("</pname>\"");
			content.append("进入了尽调阶段，请您关注");
			//所有人（按部门识别）
			ScheduleMessageBean message =getScheduleMessageInfo(model,2,null);
			message.setSendTime(sendTime);
			message.setContent(content.toString());
			list.add(message);
		}
		else if(model.getMessageType().equals(sop_task_6)){
			//放弃
			StringBuffer content = new StringBuffer();
			content.append("\"<pname>").append(model.getProjectName()).append("</pname>\"");
			content.append("进入了股权交割阶段，请您关注");
			//所有人（按部门识别）
			ScheduleMessageBean message =getScheduleMessageInfo(model,2,null);
			message.setSendTime(sendTime);
			message.setContent(content.toString());
			list.add(message);
		}
	}

	
	//初始化消息公用部分 model:代办任务。flag：区分是接收人，项目创建人，部门所有人，map：多选择
	private ScheduleMessageBean getScheduleMessageInfo(SopTask model,int flag, Map<String, Object> map) {
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
			ScheduleMessageUserBean toU = new ScheduleMessageUserBean();
			toU.setUid(CUtils.get().object2Long(map.get("projectCreatedId")));
			toU.setUname(CUtils.get().object2String(map.get("projectCreatedName")));
			toUsers.add(toU);
		}else if(flag==2){
			//所有人（按部门识别）
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
