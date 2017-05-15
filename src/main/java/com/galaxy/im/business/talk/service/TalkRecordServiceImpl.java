package com.galaxy.im.business.talk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.bean.talk.sopFileBean;
import com.galaxy.im.business.talk.dao.ITalkRecordDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class TalkRecordServiceImpl extends BaseServiceImpl<TalkRecordBean> implements ITalkRecordService{

	@Autowired
	private ITalkRecordDao dao;

	@Override
	protected IBaseDao<TalkRecordBean, Long> getBaseDao() {
		return dao;
	}


	/**
	 * 保存sopfile文件信息
	 */
	@Override
	public long saveSopFile(sopFileBean sopFileBean) {
		
		return dao.saveSopFile(sopFileBean);
	}
	

}
