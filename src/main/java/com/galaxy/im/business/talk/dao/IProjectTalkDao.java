package com.galaxy.im.business.talk.dao;

import java.util.Map;

import com.galaxy.im.bean.talk.ProjectTalkBean;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface IProjectTalkDao extends IBaseDao<ProjectTalkBean, Long>{

	QPage getProjectTalkList(Map<String, Object> paramMap);

	int updateCreateUid(ProjectTalkBean ir);

}
