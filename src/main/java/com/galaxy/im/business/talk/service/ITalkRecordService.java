package com.galaxy.im.business.talk.service;

import com.galaxy.im.bean.talk.TalkRecordBean;
import com.galaxy.im.bean.talk.sopFileBean;
import com.galaxy.im.common.db.service.IBaseService;

public interface ITalkRecordService extends IBaseService<TalkRecordBean>{

	//保存sopfile信息
	long saveSopFile(sopFileBean sopFileBean);

}
