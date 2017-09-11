package com.galaxy.im.business.talk.dao;

import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.db.IBaseDao;

public interface ITalkRecordDao extends IBaseDao<TalkRecordBean, Long>{

	/**
	 * 保存sopfile信息
	 * @param sopFileBean
	 * @return
	 */
	long saveSopFile(SopFileBean sopFileBean);

}
