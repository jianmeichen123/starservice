package com.galaxy.im.business.talk.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class TalkRecordDaoImpl extends BaseDaoImpl<TalkRecordBean, Long> implements ITalkRecordDao{

private Logger log = LoggerFactory.getLogger(TalkRecordDaoImpl.class);
	
	public TalkRecordDaoImpl(){
		super.setLogger(log);
	}
}
