package com.galaxy.im.common.messageThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.message.ScheduleMessageBean;
import com.galaxy.im.bean.message.ScheduleMessageUserBean;
import com.galaxy.im.business.message.dao.IScheduleMessageDao;
import com.galaxy.im.business.message.dao.IScheduleMessageUserDao;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.common.exception.BusinessException;
import com.tencent.xinge.XGPush;


/**
 * 每 30 秒调用，发送消息
 */
@Service
public class SchedulePushMessTask extends BaseGalaxyTask {
	private final static Logger logger = LoggerFactory.getLogger(SchedulePushMessTask.class);
	
	public static List<ScheduleMessageBean> messForCache = new ArrayList<ScheduleMessageBean>();
	
	/**
	 * 定义跳出 runForMess . for 的超时时间， 默认0秒
	 */
	//private static final long TO_BREAK_SENDFOR_TIME = (long)  0 * 1000;
	
	/**
	 * 定义消息 可以延后发送的时间， 00:1分钟发送 + 延后 5分钟
	 */
	//private static final long TO_LAZY_TIME_BY_MESSAGE = (long) 95 * 60 * 1000;
	
	/**
	 * 服务 是否正在在检测   
	 */
	private static boolean hasRunedToCheck = false;
	/**
	 * 服务器重新启动获取一次
	 */
	public static boolean startRuned = false;
	/**
	 * 等待服务 运行时间 5毫秒
	 */
	private static long waitServerTime = 5;
	
	/**
	 * mess删除 - map.key
	 */
	public static final String DEL_MAP_KEY_MID = "mid";
	public static final String DEL_MAP_KEY_MUID = "muid";
	
	//@Autowired
	//Cache cache;
	@Autowired
	private IScheduleMessageService scheduleMessageService;
	@Autowired
	private IScheduleMessageDao scheduleMessageDao;
	@Autowired
	private IScheduleMessageUserDao scheduleMessageUserDao;
	
	
	
	
	/**
	 * 是否有新增处理 外部调用， 赋值
	 */
	public synchronized void setHasSaved(ScheduleMessageBean addMess) {
		while (SchedulePushMessTask.hasRunedToCheck) { // 服务是否正在处理
			try {
				Thread.sleep(SchedulePushMessTask.waitServerTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		SchedulePushMessTask.hasRunedToCheck = true;
		try {
			
			if(SchedulePushMessTask.messForCache != null && !SchedulePushMessTask.messForCache.isEmpty()){
				
				SchedulePushMessTask.messForCache.add(addMess);
				Collections.sort(SchedulePushMessTask.messForCache, new Comparator<ScheduleMessageBean>() {
					public int compare(ScheduleMessageBean arg0, ScheduleMessageBean arg1) {
						return (int) (arg0.getSendTime().longValue() - arg1.getSendTime().longValue());
					}
				});
			}else{
				SchedulePushMessTask.messForCache.add(addMess);
			}
		}finally{
			SchedulePushMessTask.hasRunedToCheck = false;
		}
	}
	
	/**
	 * 是否有删除处理 
	 * 外部调用， 赋值
	 */
	public synchronized void setHasDeled(Map<String, List<Long>> delMap) {
		/*while (SchedulePushMessTask.hasRunedToCheck) { // 服务是否正在处理
			try {
				Thread.sleep(SchedulePushMessTask.waitServerTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		SchedulePushMessTask.hasRunedToCheck = true;
		
		try {
			 List<ScheduleMessageBean> sMessList = SchedulePushMessTask.messForCache;
			 if(sMessList != null){
				
				List<Long> mids = delMap.get(SchedulePushMessTask.DEL_MAP_KEY_MID);
				Long mid = mids.get(0);
				List<Long> muids = delMap.get(SchedulePushMessTask.DEL_MAP_KEY_MUID);

				for (ScheduleMessageBean tempM : sMessList) {
					if (tempM.getId().longValue() == mid.longValue()) {
						if(muids != null && !muids.isEmpty()) {
							for(Long muid : muids){
								for (ScheduleMessageUserBean tempU : tempM.getToUsers()) {
									if (tempU.getUid().longValue() == muid.longValue()) {
										tempM.getToUsers().remove(tempU);
										break;
									}
								}
							}
						}else{
							sMessList.remove(tempM);
						}
						
						break;
					}
				}
			}
		} finally{
			SchedulePushMessTask.hasRunedToCheck = false;
		}
		
	}
	
	/**
	 * 每 30 秒调用，发送消息
	 */
	@Override
	protected void executeInteral() throws BusinessException {
		while (SchedulePushMessTask.hasRunedToCheck) { // 服务是否正在处理
			try {
				Thread.sleep(SchedulePushMessTask.waitServerTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		SchedulePushMessTask.hasRunedToCheck = true;
		
		try {
			
			//初始化补充
			try {
				if(!SchedulePushMessTask.startRuned){
					List<ScheduleMessageBean> initList = scheduleMessageService.queryTodayMessToSend();
					
					SchedulePushMessTask.startRuned = true;
					
					if(initList!=null){
						SchedulePushMessTask.messForCache = initList;
					}
				}
			} catch (Exception e) {}
			
			//long current = System.currentTimeMillis();
			
			if(SchedulePushMessTask.messForCache != null && !SchedulePushMessTask.messForCache.isEmpty()){
				
				List<ScheduleMessageBean> thisTimeToSend = new ArrayList<ScheduleMessageBean>();
				logger.error(SchedulePushMessTask.messForCache.size()+"===========");
				for (int i = 0; i < SchedulePushMessTask.messForCache.size();) {
					
					ScheduleMessageBean mess = SchedulePushMessTask.messForCache.get(i);
					
					//if(mess.getSendTime().longValue() - current <= SchedulePushMessTask.TO_BREAK_SENDFOR_TIME){
						thisTimeToSend.add(mess);
						logger.error(mess.getContent());
						SchedulePushMessTask.messForCache.remove(i);
					//}else{
					//	break;
					//}
				}
				
				if(!thisTimeToSend.isEmpty()){
					final List<ScheduleMessageBean> toSend = thisTimeToSend;
					
					GalaxyThreadPool.getExecutorService().execute(new Runnable() {
						public void run() {
							runForMess(toSend);
						}
					});
				}
			}
		}finally{
			SchedulePushMessTask.hasRunedToCheck = false;
		}
		
	}
	
	//发送消息
	public void runForMess(List<ScheduleMessageBean> thisTimeToSend) {

		final XGPush xinge = XGPush.getInstance();
		
		for(ScheduleMessageBean tempMess : thisTimeToSend){
			

			final ScheduleMessageBean mess = tempMess;
			
			// 发送时间  《  当前时间+lazy tm  跳过不发
			//boolean toContinue = false;
			//if (mess.getSendTime().longValue() < (System.currentTimeMillis() - SchedulePushMessTask.TO_LAZY_TIME_BY_MESSAGE)) {
			//	toContinue = true;
			//}
			
			// 统一修改 消息内容可用
			
			if (mess.getToUsers()==null || mess.getToUsers().isEmpty()) {
				continue;
			}
			mess.setStatus((byte) 0);
			scheduleMessageDao.updateMessageById(mess);
			
			
			// 消息推送到移动端
			GalaxyThreadPool.getExecutorService().execute(new Runnable() {
				
				@Override
				public void run() {
					// 消息标题
					String mesTitle = null;
					if (mess.getType().equals("1.3")) {
						mesTitle = "日程提醒";
					}

					// 消息接收人id
					List<String> uIds = new ArrayList<String>();

					List<ScheduleMessageUserBean> toUsers = mess.getToUsers();
					for (ScheduleMessageUserBean tempU : toUsers) {
						uIds.add("fxXinGe"+String.valueOf(tempU.getUid()));
					}
					
					// 消息内容
					String conts = UtilOper.getMessContent(mess);
					
					// 消息发送
					org.json.JSONObject result = xinge.pushAccountList(uIds, mesTitle, conts);

					// 消息发送结果
					if (result != null) {
						String backStr = result.toString();
						String iosmarkV = backStr.substring(backStr.indexOf("ret_code\":") + 10,
								backStr.indexOf("ret_code\":") + 11);
						String andriodmarkV = backStr.substring(backStr.lastIndexOf("ret_code\":") + 10,
								backStr.lastIndexOf("ret_code\":") + 11);
						if (!iosmarkV.equals("0") || !andriodmarkV.equals("0")) {
						}else {
							ScheduleMessageUserBean toU = new ScheduleMessageUserBean();
							toU.setMid(mess.getId());
							toU.setIsSend((byte) 1);
							scheduleMessageUserDao.updateMessageUserById(toU);
						}
					}
				}
			});
		}
	}

}


