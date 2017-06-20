package com.galaxy.im.business.meeting.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class MeetingRecordDaoImpl extends BaseDaoImpl<MeetingRecordBean, Long> implements IMeetingRecordDao{
	private Logger log = LoggerFactory.getLogger(MeetingRecordDaoImpl.class);
	
	@Override
	public QPage getMeetingRecordList(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
				contentList = sqlSessionTemplate.selectList(getSqlName("getMeetingRecordList"),getPageMap(paramMap));
				total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("countMeetingRecordList"),getPageMap(paramMap)));
			}    
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(MeetingRecordDaoImpl.class.getName() + "_getMeetingRecordList",e);
			throw new DaoException(e);
		}
	}

}