package com.galaxy.im.business.talk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.talk.TalkRecordDetailBean;
import com.galaxy.im.business.talk.dao.ITalkRecordDetailDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class TalkRecordDetailServiceImpl extends BaseServiceImpl<TalkRecordDetailBean> implements ITalkRecordDetailService{
	
	@Autowired
	private ITalkRecordDetailDao dao;
	@Override
	protected IBaseDao<TalkRecordDetailBean, Long> getBaseDao() {
		return dao;
	}
	

}
