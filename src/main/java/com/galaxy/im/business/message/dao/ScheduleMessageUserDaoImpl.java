package com.galaxy.im.business.message.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.message.ScheduleMessageUserBean;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;
@Repository
public class ScheduleMessageUserDaoImpl extends BaseDaoImpl<ScheduleMessageUserBean, Long>implements IScheduleMessageUserDao{
private Logger log = LoggerFactory.getLogger(ScheduleMessageUserDaoImpl.class);
	
	public ScheduleMessageUserDaoImpl(){
		super.setLogger(log);
	}

	/**
	 * 个人消息 列表
	 */
	@Override
	public QPage selectMuserAndMcontentList(Map<String, Object> paramMap) {
		try {
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
			 contentList = sqlSessionTemplate.selectList(getSqlName("selectMuserAndMcontentList") ,getPageMap(paramMap));
			 total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("selectMuserAndMcontentCount"),getPageMap(paramMap)));
			}
			return new QPage(contentList,total);
		} catch (Exception e) {
			log.error(getSqlName("selectMuserAndMcontentList"),e);
			throw new DaoException(e);
		}
	}
	
	/**
	 * 消息总数
	 */
	@Override
	public Integer selectMuserAndMcontentCount(Map<String, Object> paramMap) {
		try {
			
			Integer total = 0;
			if(paramMap!=null){
			 total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("selectMuserAndMcontentCount"),paramMap));
			}
			return total;
		} catch (Exception e) {
			log.error(getSqlName("selectMuserAndMcontentCount"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 单条已读
	 */
	@Override
	public long updateToRead(Map<String, Object> paramMap) {
		try {
			int count = 0;
				if (paramMap!=null) {
					count = sqlSessionTemplate.update(getSqlName("updateToRead"), paramMap);
				}
			return count;
		} catch (Exception e) {
			log.error(getSqlName("updateToRead"),e);
			throw new DaoException(e);
		}

	}
	/**
	 * 消息列表
	 */
	@Override
	public List<ScheduleMessageUserBean> selectMessageList(Map<Object, Object> paramMap) {
		try {
			List<ScheduleMessageUserBean> contentList = null;
			if(paramMap!=null){
			 contentList = sqlSessionTemplate.selectList(getSqlName("selectMessageList"), paramMap);
			}
			return contentList;
		} catch (Exception e) {
			log.error(getSqlName("selectMessageList"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 全部设为已读
	 * @return 
	 */
	@Override
	public int perMessageToRead(Map<Object, Object> paramMap) {
		int count = 0;
		try {
			if (paramMap!=null) {
			count =  sqlSessionTemplate.update(getSqlName("perMessageToRead"), paramMap);
			}
			
		} catch (Exception e) {
			log.error(getSqlName("perMessageToRead"),e);
			throw new DaoException(e);
		}
		return count;
	}
	/**
	 * 清空消息
	 */
	@Override
	public int perMessageToClear(Map<Object, Object> paramMap) {
		int count = 0;
		try {
			if (paramMap!=null) {
			count =  sqlSessionTemplate.update(getSqlName("perMessageToClear"), paramMap);
			}
		} catch (Exception e) {
			log.error(getSqlName("perMessageToClear"),e);
			throw new DaoException(e);
		}
		return count;
	}

	/**
	 * 保存消息
	 */
	@Override
	public void saveMessageUser(List<ScheduleMessageUserBean> list) {
		try {
			sqlSessionTemplate.insert(getSqlName("saveMessageUser"), list);
		} catch (Exception e) {
			log.error(getSqlName("saveMessageUser"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 查询信息
	 */
	@Override
	public List<ScheduleMessageUserBean> selectMessageUserList(ScheduleMessageUserBean muQ) {
		try {
			Map<String, Object> params = BeanUtils.toMap(muQ);
			List<ScheduleMessageUserBean> contentList = null;
			if(muQ!=null){
			 contentList = sqlSessionTemplate.selectList(getSqlName("selectMessageUserList"), params);
			}
			return contentList;
		} catch (Exception e) {
			log.error(getSqlName("selectMessageUserList"),e);
			throw new DaoException(e);
		}
	}

	
	
}
