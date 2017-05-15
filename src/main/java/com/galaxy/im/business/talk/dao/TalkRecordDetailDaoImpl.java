package com.galaxy.im.business.talk.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.talk.TalkRecordDetailBean;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class TalkRecordDetailDaoImpl extends BaseDaoImpl<TalkRecordDetailBean, Long> implements ITalkRecordDetailDao{

private Logger log = LoggerFactory.getLogger(TalkRecordDetailDaoImpl.class);
	
	public TalkRecordDetailDaoImpl(){
		super.setLogger(log);
	}
	
}
