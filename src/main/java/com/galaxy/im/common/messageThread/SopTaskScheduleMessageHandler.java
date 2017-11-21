package com.galaxy.im.common.messageThread;

import java.io.Serializable;
import java.util.List;

import com.galaxy.im.bean.message.ScheduleMessageBean;

public interface SopTaskScheduleMessageHandler extends Serializable{
	public void handle(List<ScheduleMessageBean> list, Object info);

	public boolean support(Object info);
}
