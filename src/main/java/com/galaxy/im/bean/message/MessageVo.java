package com.galaxy.im.bean.message;

import java.util.List;

import com.galaxy.im.common.db.PagableEntity;

public class MessageVo extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private String messageType;		//消息类型
	private List<String> ids;		//ids:项目id，代办任务id等
	private Long userId;			//当前操作人id
	
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
