package com.galaxy.im.bean.message;

import com.galaxy.im.common.db.PagableEntity;

public class SystemMessageUserBean extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private Long messageId;			//消息id
	private String messageOs;		//平台类型
	private Long userId;
	private int isRead;
	private int isDel;
	private Long createId;
	private Long createTime;
	private Long updateId;
	private Long updateTime;

}
