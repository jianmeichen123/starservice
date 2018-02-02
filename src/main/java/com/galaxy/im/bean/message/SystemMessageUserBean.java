package com.galaxy.im.bean.message;

import com.galaxy.im.common.db.PagableEntity;

public class SystemMessageUserBean extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private Long messageId;			//消息id
	private String messageOs;		//平台类型
	private Long userId;			//用户id
	private int isRead;				//是否已读
	private int isDel;				//是否删除
	private Long createId;			//创建人id
	private Long createTime;		//创建时间
	private Long updateId;			//更新人id
	private Long updateTime;		//更新时间
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public String getMessageOs() {
		return messageOs;
	}
	public void setMessageOs(String messageOs) {
		this.messageOs = messageOs;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

}
