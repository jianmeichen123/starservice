package com.galaxy.im.common.messageThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.galaxy.im.bean.message.ScheduleMessageBean;


@Component
public class ScheduleMessageGenerator implements InitializingBean,ApplicationContextAware
{
	private ApplicationContext context;
	private List<ScheduleMessageHandler> handlers;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.context = applicationContext;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		 Map<String, ScheduleMessageHandler> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ScheduleMessageHandler.class, true, false);
		 if(map != null)
		 {
			 handlers = new ArrayList<ScheduleMessageHandler>(map.values());
			 //OrderComparator.sort(handlers);
		 }
	}

	public ScheduleMessageBean process(Object info)
	{
		if(handlers != null) {
			for(ScheduleMessageHandler handler : handlers) {
				if(handler.support(info)) {
					ScheduleMessageBean message = new ScheduleMessageBean();
					message.setStatus((byte) 1);    // 0:可用 1:禁用  2:删除
					handler.handle(message,info);
					return message;
				}
			}
		}
		return null;
	}
}
