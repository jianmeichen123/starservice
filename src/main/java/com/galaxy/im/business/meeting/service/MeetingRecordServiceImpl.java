package com.galaxy.im.business.meeting.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.business.meeting.dao.IMeetingRecordDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class MeetingRecordServiceImpl extends BaseServiceImpl<MeetingRecordBean> implements IMeetingRecordService{
	private Logger log = LoggerFactory.getLogger(MeetingRecordServiceImpl.class);
	
	@Autowired
	IMeetingRecordDao dao;
	@Override
	protected IBaseDao<MeetingRecordBean, Long> getBaseDao() {
		return dao;
	}

	@Override
	public QPage getMeetingRecordList(Map<String, Object> paramMap) {
		try{
			return dao.getMeetingRecordList(paramMap);
		}catch(Exception e){
			log.error(MeetingRecordServiceImpl.class.getName() + "_getMeetingRecordList",e);
			throw new ServiceException(e);
		}
	}
	
}
