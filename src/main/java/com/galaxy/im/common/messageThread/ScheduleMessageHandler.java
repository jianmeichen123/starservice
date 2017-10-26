package com.galaxy.im.common.messageThread;

import java.io.Serializable;

import com.galaxy.im.bean.message.ScheduleMessageBean;



public interface ScheduleMessageHandler extends Serializable
{
	public boolean support(Object info);
	public void handle(ScheduleMessageBean message,Object info);
}
