package com.galaxy.im.common.messageThread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.message.ScheduleMessageBean;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.common.exception.BusinessException;


@Service
public class SchedulePushInitTask extends BaseGalaxyTask {

	//private final static Logger logger = LoggerFactory.getLogger(SchedulePushInitTask.class);

	public static final String CACHE_KEY_MESSAGE_TODAY_PUSH = "message_for_today_to_push"; 
	
	@Autowired
	private IScheduleMessageService scheduleMessageService;
	
	/**
	 * 服务是否运行过
	 */
	public static boolean initTaskHasRuned = false;
	
	/**
	 * 每天 凌晨 00:01 点调用 ， 查询出今天需要推送的消息
	 */
	@Override
	protected void executeInteral() throws BusinessException {
		
		List<ScheduleMessageBean> sMessList = scheduleMessageService.queryTodayMessToSend();
		
		if(!SchedulePushInitTask.initTaskHasRuned) SchedulePushInitTask.initTaskHasRuned = true;
			
			SchedulePushInitTask.initTaskHasRuned = true;
			
			if(sMessList!=null){
				SchedulePushMessTask.messForCache = sMessList;
			}
	}

}
