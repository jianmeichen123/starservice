package com.galaxy.im.business.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
@Service
public class ScheduleMessageServiceImpl extends BaseServiceImpl<ScheduleMessageBean>implements IScheduleMessageService {
	private Logger log = LoggerFactory.getLogger(ScheduleMessageServiceImpl.class);
	@Autowired
	private IScheduleMessageDao iScheduleMessageDao;
	
	@Autowired
	private IScheduleMessageUserDao iScheduleMessageUserDao;
	
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
		paramMap.put("isRead", 0);
		paramMap.put("isSend", 1);
		try {
			List<ScheduleMessageUserBean> mus = iScheduleMessageUserDao.selectMessageList(paramMap);//查出满足条件的未读消息列表
			Long mid =0L;
			List<Long> ids = new ArrayList<Long>();
			if(mus != null && !mus.isEmpty()){
				for(ScheduleMessageUserBean bean:mus){
					 mid = bean.getMid();
					 ids.add(mid);//将未读消息id存入list集合
				}
			
			paramMap.clear();
			paramMap.put("isRead", 1);
			paramMap.put("userId", guserid);
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
			Long mid =0L;
			List<Long> ids = new ArrayList<Long>();
			if(mus != null && !mus.isEmpty()){
				for(ScheduleMessageUserBean bean:mus){
					 mid = bean.getMid();
					 ids.add(mid);//将未读消息id存入list集合
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

	

}
