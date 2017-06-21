package com.galaxy.im.business.talk.service;

import java.util.Map;

import com.galaxy.im.bean.talk.ProjectTalkBean;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectTalkService extends IBaseService<ProjectTalkBean>{

	QPage getProjectTalkList(Map<String, Object> paramMap);

}
