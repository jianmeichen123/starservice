package com.galaxy.im.business.callon.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.business.callon.dao.ICallonDao;
import com.galaxy.im.business.sopfile.dao.ISopFileDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class CallonServiceImpl extends BaseServiceImpl<ScheduleInfo> implements ICallonService{
	private Logger log = LoggerFactory.getLogger(CallonServiceImpl.class);
	
	@Autowired
	private ICallonDao callonDao;
	
	@Autowired
	ISopFileDao dao;

	@Override
	protected IBaseDao<ScheduleInfo, Long> getBaseDao() {
		return callonDao;
	}

	@Override
	public QPage selectCallonList(Map<String, Object> paramMap){
		try{
			return callonDao.selectCallonList(paramMap);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_getCallonList",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean delCallonById(Map<String,Object> paramMap) {
		try{
			return (callonDao.delCallonById(paramMap)>0);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean callonEnableEditOrDel(Long id) {
		try{
			return (callonDao.callonEnableEditOrDel(id)>0);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//查询访谈记录
	@Override
	public TalkRecordBean getTalkRecordBean(Long id) {
		try{
			return callonDao.getTalkRecordBean(id);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//删除访谈记录
	@Override
	public int delTalkRecordBean(TalkRecordBean tBean) {
		try{
			return callonDao.delTalkRecordBean(tBean);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}


	//删除文件
	@Override
	public int deleteSopFileBean(SopFileBean sFileBean) {
		try{
			return dao.delPostMeetingFile(sFileBean);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//删除运营会议
	@Override
	public int deleteMeetingRecordBean(MeetingRecordBean mBean) {
		try{
			return callonDao.deleteMeetingRecordBean(mBean);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	//查询运营会议
	@Override
	public MeetingRecordBean getMeetingRecordBean(Long id) {
		try{
			return callonDao.getMeetingRecordBean(id);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}
	
	
	

	
}
