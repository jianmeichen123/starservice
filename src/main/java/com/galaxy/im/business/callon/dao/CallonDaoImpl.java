package com.galaxy.im.business.callon.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class CallonDaoImpl extends BaseDaoImpl<ScheduleInfo, Long> implements ICallonDao{
	private Logger log = LoggerFactory.getLogger(CallonDaoImpl.class);
	
	public CallonDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public QPage selectCallonList(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
				contentList = sqlSessionTemplate.selectList(getSqlName("selectCallonList"),getPageMap(paramMap));
				total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("countCallonList"),getPageMap(paramMap)));
			}    
			
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(getSqlName("selectCallonList"),e);
			throw new DaoException(e);
		}
	}

	@Override
	public int delCallonById(Map<String,Object> paramMap) {
		try{
			return sqlSessionTemplate.update(getSqlName("delCallonById"),paramMap);
		}catch(Exception e){
			log.error(getSqlName("delCallonById"),e);
			throw new DaoException(e);
		}
	}

	@Override
	public int callonEnableEditOrDel(Long id) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("callonEnableEditOrDel"), id);
		}catch(Exception e){
			log.error(getSqlName("callonEnableEditOrDel"),e);
			throw new DaoException(e);
		}
	}

	//查询访谈记录
	@Override
	public TalkRecordBean getTalkRecordBean(Long id) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName("getTalkRecordBean"), id);
		} catch (Exception e) {
			log.error(String.format(getSqlName("getTalkRecordBean")), e);
			throw new DaoException(e);
		}
	}

	//删除访谈记录
	@Override
	public int delTalkRecordBean(TalkRecordBean tBean) {
		try {
			return sqlSessionTemplate.delete(getSqlName("delTalkRecordBean"), tBean);
		} catch (Exception e) {
			log.error(String.format( getSqlName("delTalkRecordBean")), e);
			throw new DaoException(e);
		}
	}

	//删除运营会议
	@Override
	public int deleteMeetingRecordBean(MeetingRecordBean mBean) {
		try {
			return sqlSessionTemplate.delete(getSqlName("deleteMeetingRecordBean"), mBean);
		} catch (Exception e) {
			log.error(String.format(getSqlName("deleteMeetingRecordBean")), e);
			throw new DaoException(e);
		}
	}

	//查询运营会议
	@Override
	public MeetingRecordBean getMeetingRecordBean(Long id) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName("getMeetingRecordBean"), id);
		} catch (Exception e) {
			log.error(String.format(getSqlName("getMeetingRecordBean")), e);
			throw new DaoException(e);
		}
	}
	

	
}
