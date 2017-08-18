package com.galaxy.im.bean.message;

import java.util.List;

import com.galaxy.im.common.db.BaseEntity;

/**
 * 消息-user 关联
 */
public class ScheduleMessageUserBean extends BaseEntity	{
	private static final long serialVersionUID = 1L;

	private Long mid; // 消息 id

    private Long uid; // 接收人 id

    private String uname; 

    private Byte typeRole; //会议(1:组织人 2:受邀人) 拜访(3:去拜访者 4:被拜访人)

    private byte isUse;  //0:可用    1:禁用

    private Byte isSend; //0:未发送  1+:已发送

    private Byte isRead; //0:未读    1:已读

    private Byte isDel;  //0:未删除  1:已删除

    
    
    
    private List<Long> ids;
    
    private List<Long> mids;
    
    private Byte status;       // 0:可用 1:禁用
    private String type;       //消息类型  日程(1.1:会议  1.2:拜访  1.3:其它)
	private Long remarkId;     //存的是日程的id
	private String content;    // 消息内容
	private Long sendTime;  // 消息发送时间
    private ScheduleMessageBean message;
    
    private String messageisRead;  //2017/5/25 消息添加 所有的是否有已读未读
    
    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public Byte getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(Byte typeRole) {
        this.typeRole = typeRole;
    }

    public byte getIsUse() {
        return isUse;
    }

    public void setIsUse(byte isUse) {
        this.isUse = isUse;
    }

    public Byte getIsSend() {
        return isSend;
    }

    public void setIsSend(Byte isSend) {
        this.isSend = isSend;
    }

    public Byte getIsRead() {
        return isRead;
    }

    public void setIsRead(Byte isRead) {
        this.isRead = isRead;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public ScheduleMessageBean getMessage() {
		return message;
	}

	public void setMessage(ScheduleMessageBean message) {
		this.message = message;
	}

	public List<Long> getMids() {
		return mids;
	}

	public void setMids(List<Long> mids) {
		this.mids = mids;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
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
		this.content = content;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public String getMessageisRead() {
		return messageisRead;
	}

	public void setMessageisRead(String messageisRead) {
		this.messageisRead = messageisRead;
	}

}
