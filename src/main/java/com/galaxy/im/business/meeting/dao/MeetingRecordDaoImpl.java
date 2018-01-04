package com.galaxy.im.business.meeting.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class MeetingRecordDaoImpl extends BaseDaoImpl<MeetingRecordBean, Long> implements IMeetingRecordDao{
	private Logger log = LoggerFactory.getLogger(MeetingRecordDaoImpl.class);
	
	/**
	 * 会议纪要列表
	 */
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

	/**
	 * 获取健康分析情况
	 */
	@Override
	public Map<String, Object> getSopProjectHealth(Map<String, Object> paramMap) {
		try {
			Map<String,Object> res = sqlSessionTemplate.selectOne(getSqlName("getSopProjectHealth"),paramMap);
			return res;
		} catch (Exception e) {
			log.error(MeetingRecordDaoImpl.class.getName() + "_getSopProjectHealth",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 运营会议纪要详情
	 */
	@Override
	public Map<String, Object> postMeetingDetail(MeetingRecordBean meetingRecord) {
		try{
			Map<String, Object> params = BeanUtils.toMap(meetingRecord);
			return sqlSessionTemplate.selectOne(getSqlName("postMeetingDetail"),params);
		}catch(Exception e){
			log.error(MeetingRecordDaoImpl.class.getName() + "postMeetingDetail",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 删除会议
	 */
	@Override
	public int delMeetingRecord(MeetingRecordBean meetingRecord) {
		try {
			return sqlSessionTemplate.delete(getSqlName("delMeetingRecord"), meetingRecord);
		} catch (Exception e) {
			log.error(String.format(getSqlName("delMeetingRecord")), e);
			throw new DaoException(e);
		}
	}
	
	//查询运营会议
	@Override
	public MeetingRecordBean getMeetingRecord(Long id) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName("getMeetingRecordBean"), id);
		} catch (Exception e) {
			log.error(String.format(getSqlName("getMeetingRecordBean")), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取会议类型的个数
	 */
	@Override
	public int getMeetingTypeCount(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName("getMeetingTypeCount"), paramMap);
		} catch (Exception e) {
			log.error(String.format(getSqlName("getMeetingTypeCount")), e);
			throw new DaoException(e);
		}
	}

	//更新会议
	@Override
	public int updateCreateUid(MeetingRecordBean mr) {
		try {
			 return sqlSessionTemplate.update(getSqlName("updateCreateUid"), mr);
		} catch (Exception e) {
			log.error(String.format("根据ID更新对象出错！语句：%s", getSqlName("updateCreateUid")), e);
			throw new DaoException(e);
		}
	}
	

}
