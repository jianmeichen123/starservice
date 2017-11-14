package com.galaxy.im.business.message.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.message.ScheduleMessageBean;
import com.galaxy.im.bean.message.ScheduleMessageUserBean;
import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.message.dao.IScheduleMessageDao;
import com.galaxy.im.business.message.dao.IScheduleMessageUserDao;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
import com.galaxy.im.common.messageThread.GalaxyThreadPool;
import com.galaxy.im.common.messageThread.ScheduleMessageGenerator;
import com.galaxy.im.common.messageThread.SchedulePushMessTask;
@Service
public class ScheduleMessageServiceImpl extends BaseServiceImpl<ScheduleMessageBean>implements IScheduleMessageService {
	private Logger log = LoggerFactory.getLogger(ScheduleMessageServiceImpl.class);
	
	@Autowired
	private IScheduleMessageDao iScheduleMessageDao;
	
	@Autowired
	private IScheduleMessageUserDao iScheduleMessageUserDao;
	
	@Autowired
	ScheduleMessageGenerator messageGenerator;
	
	@Autowired
	SchedulePushMessTask schedulePushMessTask;
	
	@Override
	protected IBaseDao<ScheduleMessageBean, Long> getBaseDao() {
		return iScheduleMessageDao;
	}
	
	/**
	 * 消息列表
	 */
	@Override
	public QPage queryPerMessAndConvertPage(Map<String, Object> paramMap) {
		try {
			return iScheduleMessageUserDao.selectMuserAndMcontentList(paramMap);

		} catch (Exception e) {
			log.error(ScheduleMessageServiceImpl.class.getName() + "queryPerMessAndConvertPage",e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 消息总数
	 */
	@Override
	public Integer selectMuserAndMcontentCount(Map<String, Object> paramMap) {
		try {
			return iScheduleMessageUserDao.selectMuserAndMcontentCount(paramMap);
		} catch (Exception e) {
			log.error(ScheduleMessageServiceImpl.class.getName() + "selectMuserAndMcontentCount",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 单条更新已读
	 */
	@Override
	public long updateToRead(Map<String, Object> paramMap) {
		try {
			return iScheduleMessageUserDao.updateToRead(paramMap);
		} catch (Exception e) {
			log.error(ScheduleMessageServiceImpl.class.getName() + "updateToRead",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 个人消息  设为全部已读
	 * 1.查询出 消息user 表中      个人的   可用   未读  未删除  的数据
	 * 2.查询出消息内容列表          状态为可用的消息
	 * @return 
     */
	@Override
	public int perMessageToRead(Long guserid) {
		Map<Object, Object> paramMap = new HashMap<>();
		int count = 0;
		paramMap.put("userId", guserid);
		paramMap.put("isUse", 0);
		paramMap.put("isDel", 0);
		paramMap.put("status", 0);
		paramMap.put("isSend", 1);
		try {
			List<ScheduleMessageUserBean> mus = iScheduleMessageUserDao.selectMessageList(paramMap);//查出满足条件的未读消息列表
			Long id =0L;
			List<Long> ids = new ArrayList<Long>();
			if(mus != null && !mus.isEmpty()){
				for(ScheduleMessageUserBean bean:mus){
					 id = bean.getId();
					 ids.add(id);//将未读消息id存入list集合
				}
			
			paramMap.clear();
			paramMap.put("userId", guserid);
			paramMap.put("isRead", 1);
			paramMap.put("ids", ids);
			paramMap.put("updatedTime", new Date().getTime());
			count = iScheduleMessageUserDao.perMessageToRead(paramMap);
			}	
			
		} catch (Exception e) {
			log.error(ScheduleMessageServiceImpl.class.getName() + "perMessageToRead",e);
			throw new ServiceException(e);
		}
		return count;
	}
	/**
	 * 清空消息
	 */
	@Override
	public int perMessageToClear(Long guserid) {
		Map<Object, Object> paramMap = new HashMap<>();
		int count = 0;
		paramMap.put("userId", guserid);
		paramMap.put("isUse", 0);//可用
		paramMap.put("status", 0);//可用
		paramMap.put("isDel", 0);
		paramMap.put("isSend", 1);
		try {
			List<ScheduleMessageUserBean> mus = iScheduleMessageUserDao.selectMessageList(paramMap);//查出满足条件的消息列表
			Long id =0L;
			List<Long> ids = new ArrayList<Long>();
			if(mus != null && !mus.isEmpty()){
				for(ScheduleMessageUserBean bean:mus){
					id = bean.getId();
					 ids.add(id);//将未读消息id存入list集合
				}
			
			paramMap.clear();
			paramMap.put("isDel", 1);
			paramMap.put("userId", guserid);
			paramMap.put("ids", ids);
			paramMap.put("updatedTime", new Date().getTime());
			count = iScheduleMessageUserDao.perMessageToClear(paramMap);
			}	
		} catch (Exception e) {
			log.error(ScheduleMessageServiceImpl.class.getName() + "perMessageToClear",e);
			throw new ServiceException(e);
		}
		return count;
	}

	/**
	 * 新增（日程 、、 拜访）操作完成后
	 * 生成对应消息
	 * ScheduleMessage    ScheduleMessageUser
     */
	@Override
	public void operateMessageBySaveInfo(Object scheduleInfo) {
		final Object info = scheduleInfo;
		
		GalaxyThreadPool.getExecutorService().execute(new Runnable() {
			@Override
			public void run() {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 0);
				
				long edate = calendar.getTimeInMillis();
				
				ScheduleMessageBean message = messageGenerator.process(info);
				message.setCreatedTime(new Date().getTime());
				if(message.getSendTime()==null){
					return;
				}
				Long mid = iScheduleMessageDao.saveMessage(message);
				
				List<ScheduleMessageUserBean> toInserts = new ArrayList<ScheduleMessageUserBean>();
				for(ScheduleMessageUserBean toU : message.getToUsers()){
					toU.setIsSend((byte) 0);
					toU.setIsRead((byte) 0);
					toU.setIsDel((byte) 0);
					toU.setMid(mid);
					toU.setCreatedTime(new Date().getTime());
					toInserts.add(toU);
				}
				iScheduleMessageUserDao.saveMessageUser(toInserts);
				
				//通知消息 ：  已经添加新的消息
				if(message.getSendTime().longValue() <= edate){
					message.setToUsers(toInserts);
					schedulePushMessTask.setHasSaved(message);
				}
			}
		});
	}

	/**
	 * 删除（日程 、、 拜访）操作完成后
	 * 生成对应消息
	 * ScheduleMessage    ScheduleMessageUser
     */
	@Override
	public void operateMessageByDeleteInfo(Object scheduleInfo, String messageType) {
		
		final Object info = scheduleInfo;
		final String mType = messageType;
		
		GalaxyThreadPool.getExecutorService().execute(new Runnable() {
			@Override
			public void run() {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 0);
				
				long edate = calendar.getTimeInMillis();
				
				if(mType.startsWith("1")){
					//日程
					ScheduleInfo info_model = (ScheduleInfo) info;
					
					// 消息内容
					ScheduleMessageBean mq = new ScheduleMessageBean();
					mq.setStatus((byte) 1);
					mq.setType(mType);
					mq.setRemarkId(info_model.getId());
					List<ScheduleMessageBean> list =iScheduleMessageDao.selectMessageList(mq);
					if(list.size()>0){
						ScheduleMessageBean message = list.get(0);
						if(message!=null){
							//主从判断
							Long info_pid = info_model.getParentId();
							if(info_pid != null){
								ScheduleMessageUserBean scheduleMessageUser = new ScheduleMessageUserBean();
								scheduleMessageUser.setMid(message.getId());
								scheduleMessageUser.setUid(info_model.getCreatedId());
								iScheduleMessageUserDao.deleteMessageUser(scheduleMessageUser);
								
								//通知消息 ：  已经添加新的消息
								if(message.getSendTime().longValue() <= edate){
									Map<String, List<Long>> delMap = new HashMap<String, List<Long>>();
									
									List<Long> mids = new ArrayList<Long>();
									mids.add(message.getId());
									
									List<Long> muids = new ArrayList<Long>();
									muids.add(info_model.getCreatedId());
									
									delMap.put(SchedulePushMessTask.DEL_MAP_KEY_MID, mids);
									delMap.put(SchedulePushMessTask.DEL_MAP_KEY_MUID, muids);
									
									schedulePushMessTask.setHasDeled(delMap);
								}
							}else{
								iScheduleMessageDao.deleteMessageById(message.getId());
								
								ScheduleMessageUserBean scheduleMessageUser = new ScheduleMessageUserBean();
								scheduleMessageUser.setMid(message.getId());
								iScheduleMessageUserDao.deleteMessageUser(scheduleMessageUser);
								
								//通知消息 ：  已经添加新的消息
								if(message.getSendTime().longValue() <= edate){
									Map<String, List<Long>> delMap = new HashMap<String, List<Long>>();
									List<Long> mids = new ArrayList<Long>();
									mids.add(message.getId());
									delMap.put(SchedulePushMessTask.DEL_MAP_KEY_MID, mids);
									schedulePushMessTask.setHasDeled(delMap);
								}
							}
						}
					}
				}
			}
		});
	}

	/**
	 * 修改（日程 、、 拜访）操作完成后
	 * 生成对应消息
	 * ScheduleMessage    ScheduleMessageUser
     */
	@Override
	public void operateMessageByUpdateInfo(Object scheduleInfo, String messageType) {
		final Object info = scheduleInfo;
		final String mType = messageType;
		
		GalaxyThreadPool.getExecutorService().execute(new Runnable() {
			@Override
			public void run() {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 0);
				long edate = calendar.getTimeInMillis();
				
				if(mType.startsWith("1")){//日程
					
					ScheduleInfo info_model = (ScheduleInfo) info;
					
					// 消息内容
					ScheduleMessageBean mq = new ScheduleMessageBean();
					mq.setStatus((byte) 1);
					mq.setType(mType);
					mq.setRemarkId(info_model.getId());
					
					List<ScheduleMessageBean> messagelist =iScheduleMessageDao.selectMessageList(mq);
					
					if(messagelist!=null && messagelist.size()>0){
						ScheduleMessageBean message = messagelist.get(0);
						if( message!= null){
							
							if(message.getStatus().intValue() == 1){
								//主从判断
								Long info_pid = info_model.getParentId();
								if(info_pid != null){
									ScheduleMessageUserBean scheduleMessageUser = new ScheduleMessageUserBean();
									scheduleMessageUser.setMid(message.getId());
									scheduleMessageUser.setUid(info_model.getCreatedId());
									iScheduleMessageUserDao.deleteMessageUser(scheduleMessageUser);
									
									//通知消息 ：  已经添加新的消息
									if(message.getSendTime().longValue() <= edate){
										Map<String, List<Long>> delMap = new HashMap<String, List<Long>>();
										
										List<Long> mids = new ArrayList<Long>();
										mids.add(message.getId());
										
										List<Long> muids = new ArrayList<Long>();
										muids.add(info_model.getCreatedId());
										
										delMap.put(SchedulePushMessTask.DEL_MAP_KEY_MID, mids);
										delMap.put(SchedulePushMessTask.DEL_MAP_KEY_MUID, muids);
										
										schedulePushMessTask.setHasDeled(delMap);
									}
								}else{
									iScheduleMessageDao.deleteMessageById(message.getId());
									
									ScheduleMessageUserBean scheduleMessageUser = new ScheduleMessageUserBean();
									scheduleMessageUser.setMid(message.getId());
									iScheduleMessageUserDao.deleteMessageUser(scheduleMessageUser);
									
									
									//通知消息 ：  已经添加新的消息
									if(message.getSendTime().longValue() <= edate){
										Map<String, List<Long>> delMap = new HashMap<String, List<Long>>();
										
										List<Long> mids = new ArrayList<Long>();
										mids.add(message.getId());
										
										delMap.put(SchedulePushMessTask.DEL_MAP_KEY_MID, mids);
										
										schedulePushMessTask.setHasDeled(delMap);
									}
								}
								
								//新增消息
								ScheduleMessageBean messageAdd = messageGenerator.process(info);
								messageAdd.setCreatedTime(new Date().getTime());
								Long mid = iScheduleMessageDao.saveMessage(messageAdd);
								
								List<ScheduleMessageUserBean> toInserts = new ArrayList<ScheduleMessageUserBean>();
								for(ScheduleMessageUserBean toU : messageAdd.getToUsers()){
									toU.setIsSend((byte) 0);
									toU.setIsRead((byte) 0);
									toU.setIsDel((byte) 0);
									toU.setMid(mid);
									toU.setCreatedTime(new Date().getTime());
									toInserts.add(toU);
								}
								iScheduleMessageUserDao.saveMessageUser(toInserts);
								//通知消息 ：  已经添加新的消息
								if(messageAdd.getSendTime().longValue() <= edate){
									messageAdd.setToUsers(toInserts);
									schedulePushMessTask.setHasSaved(messageAdd);
								}
							}
						}
					}else{
						//新增消息
						ScheduleMessageBean messageAdd = messageGenerator.process(info);
						messageAdd.setCreatedTime(new Date().getTime());
						Long mid = iScheduleMessageDao.saveMessage(messageAdd);
						
						List<ScheduleMessageUserBean> toInserts = new ArrayList<ScheduleMessageUserBean>();
						for(ScheduleMessageUserBean toU : messageAdd.getToUsers()){
							toU.setIsSend((byte) 0);
							toU.setIsRead((byte) 0);
							toU.setIsDel((byte) 0);
							toU.setMid(mid);
							toU.setCreatedTime(new Date().getTime());
							toInserts.add(toU);
						}
						iScheduleMessageUserDao.saveMessageUser(toInserts);
						
						//通知消息 ：  已经添加新的消息
						if(messageAdd.getSendTime().longValue() <= edate){
							messageAdd.setToUsers(toInserts);
							schedulePushMessTask.setHasSaved(messageAdd);
						}
					}
				}
			}
		});
	}

	@SuppressWarnings("unused")
	@Override
	public List<ScheduleMessageBean> queryTodayMessToSend() {
		
		List<ScheduleMessageBean> results = new ArrayList<ScheduleMessageBean>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		long bdate = calendar.getTimeInMillis();
		String btime = DateUtil.longString(bdate);
		
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		
		long edate = calendar.getTimeInMillis();
		String etime = DateUtil.longString(edate);
		
		// 消息内容查询
		ScheduleMessageBean mQ = new ScheduleMessageBean();
		//mQ.setBtime(bdate);
		mQ.setEtime(edate);
		//mQ.setSendTimeNotNull(true);
		mQ.setStatus((byte) 1);
		mQ.setProperty("send_time");
		mQ.setDirection("asc");
		List<ScheduleMessageBean> mess = iScheduleMessageDao.selectMessageList(mQ);
		// 消息内容  -> 消息人 查询
		if(mess != null && !mess.isEmpty()){
			// 消息 ids
			List<Long> mids = new ArrayList<Long>();
			for(ScheduleMessageBean tempM : mess){
				mids.add(tempM.getId());
			}
			// 根据消息 ids  查询 muser
			ScheduleMessageUserBean muQ = new ScheduleMessageUserBean();
			muQ.setIsUse((byte)0);    //0:可用    1:禁用
			muQ.setIsSend((byte)0);   //0:未发送  1+:已发送
			muQ.setIsDel((byte)0);    //0:未删除  1:已删除
			muQ.setIsRead((byte)0);   //0:未读    1:已读
			muQ.setMids(mids);
			List<ScheduleMessageUserBean> mus = iScheduleMessageUserDao.selectMessageUserList(muQ);
			if(mus != null && !mus.isEmpty()){
				for(ScheduleMessageBean tempM : mess){
					for(ScheduleMessageUserBean tempU : mus){
						if(tempU.getMid().longValue() == tempM.getId().longValue()){
							if(tempM.getToUsers() == null){
								List<ScheduleMessageUserBean> tmus = new ArrayList<ScheduleMessageUserBean>();
								tmus.add(tempU);
								tempM.setToUsers(tmus);
							}else{
								tempM.getToUsers().add(tempU);
							}
						}
					}
					results.add(tempM);
				}
			}
		}
		return results;
	}

	

}
