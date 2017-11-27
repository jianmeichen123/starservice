package com.galaxy.im.business.meeting.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.meeting.dao.IMeetingRecordDao;
import com.galaxy.im.business.sopfile.dao.ISopFileDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class MeetingRecordServiceImpl extends BaseServiceImpl<MeetingRecordBean> implements IMeetingRecordService{
	private Logger log = LoggerFactory.getLogger(MeetingRecordServiceImpl.class);
	
	@Autowired
	IMeetingRecordDao dao;
	@Autowired
	ISopFileDao fileDao;
	@Override
	protected IBaseDao<MeetingRecordBean, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 会议纪要列表
	 */
	@Override
	public QPage getMeetingRecordList(Map<String, Object> paramMap) {
		try{
			return dao.getMeetingRecordList(paramMap);
		}catch(Exception e){
			log.error(MeetingRecordServiceImpl.class.getName() + "_getMeetingRecordList",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<String, Object> getSopProjectHealth(Map<String, Object> paramMap) {
		return dao.getSopProjectHealth(paramMap);
	}

	/**
	 * 运营会议纪要详情
	 */
	@Override
	public Map<String, Object> postMeetingDetail(MeetingRecordBean meetingRecord) {
		try{
			return dao.postMeetingDetail(meetingRecord);
		}catch(Exception e){
			log.error(MeetingRecordServiceImpl.class.getName() + "postMeetingDetail",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 拼接文件名
	 */
	@Override
	public Map<String, String> transFileNames(String fileName) {
		Map<String, String> retMap = new HashMap<String, String>();
		int dotPos = fileName.lastIndexOf(".");
		if(dotPos == -1){
			retMap.put("fileName", fileName);
			retMap.put("fileSuffix", "");
		}else{
			retMap.put("fileName", fileName.substring(0, dotPos));
			retMap.put("fileSuffix", fileName.substring(dotPos+1));
		}
		return retMap;
	}

	/**
	 * 逻辑删除会议纪要附件
	 */
	@Override
	public int delPostMeetingFile(SopFileBean sopfile) {
		try{
			return fileDao.delPostMeetingFile(sopfile);
		}catch(Exception e){
			log.error(MeetingRecordServiceImpl.class.getName() + "delPostMeetingFile",e);
			throw new ServiceException(e);
		}
	}
	
}
