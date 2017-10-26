package com.galaxy.im.bean.message;

import java.util.List;
import java.util.Set;

import com.galaxy.im.common.db.BaseEntity;
import com.galaxy.im.common.db.PagableEntity;
public class ScheduleMessageBean extends PagableEntity{
	private static final long serialVersionUID = 1L;

	private byte category; //0:操作消息 1:系统消息

	private String type;   //消息类型  日程(1.1:会议  1.2:拜访  1.3:其它)
	private Long remarkId;//存的是日程的id
	
	private String content; // 消息内容

	private String remark;  // 消息备注(拒绝原因、)

	private Long sendTime;  // 消息发送时间

	private Byte status;   // 0:可用 1:禁用
	
	private Long createdUid;

	private String createdUname;

	
	private Set<Long> ids;
	private List<ScheduleMessageUserBean> toUsers;
	
	private Long btime;  // 消息发送时间
	private Long etime;  // 消息发送时间
	private Boolean sendTimeNotNull;
	
	
	
	public Byte getCategory() {
		return category;
	}

	public void setCategory(byte category) {
		this.category = category;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getCreatedUid() {
		return createdUid;
	}

	public void setCreatedUid(Long createdUid) {
		this.createdUid = createdUid;
	}

	public String getCreatedUname() {
		return createdUname;
	}

	public void setCreatedUname(String createdUname) {
		this.createdUname = createdUname == null ? null : createdUname.trim();
	}

	public List<ScheduleMessageUserBean> getToUsers() {
		return toUsers;
	}

	public void setToUsers(List<ScheduleMessageUserBean> toUsers) {
		this.toUsers = toUsers;
	}

	public Set<Long> getIds() {
		return ids;
	}

	public void setIds(Set<Long> ids) {
		this.ids = ids;
	}


	public Long getBtime() {
		return btime;
	}

	public void setBtime(Long btime) {
		this.btime = btime;
	}

	public Long getEtime() {
		return etime;
	}

	public void setEtime(Long etime) {
		this.etime = etime;
	}

	public Boolean getSendTimeNotNull() {
		return sendTimeNotNull;
	}

	public void setSendTimeNotNull(Boolean sendTimeNotNull) {
		this.sendTimeNotNull = sendTimeNotNull;
	}

/*	@Override
	public String toString() {
		
		return GSONUtil.toJson(this);
	}*/
}
