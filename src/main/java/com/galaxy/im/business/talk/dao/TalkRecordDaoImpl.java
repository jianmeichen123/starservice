package com.galaxy.im.business.talk.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class TalkRecordDaoImpl extends BaseDaoImpl<TalkRecordBean, Long> implements ITalkRecordDao{

private Logger log = LoggerFactory.getLogger(TalkRecordDaoImpl.class);
	
	public TalkRecordDaoImpl(){
		super.setLogger(log);
	}

	/**
	 * 保存sopfile信息
	 */
	@Override
	public long saveSopFile(SopFileBean sopFileBean) {
		try {
			sqlSessionTemplate.insert(getSqlName("saveSopFile"), sopFileBean);
			return sopFileBean.getId();
		} catch (Exception e) {
			log.error(String.format("添加对象出错！语句：%s", getSqlName("saveSopFile")), e);
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
			return sqlSessionTemplate.delete(getSqlName("updateById"), tBean);
		} catch (Exception e) {
			log.error(String.format( getSqlName("updateById")), e);
			throw new DaoException(e);
		}
	}
}
