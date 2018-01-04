package com.galaxy.im.business.talk.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.talk.ProjectTalkBean;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ProjectTalkDaoImpl extends BaseDaoImpl<ProjectTalkBean, Long> implements IProjectTalkDao{
	private Logger log = LoggerFactory.getLogger(ProjectTalkDaoImpl.class);

	@Override
	public QPage getProjectTalkList(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
				contentList = sqlSessionTemplate.selectList(getSqlName("getProjectTalkList"),getPageMap(paramMap));
				total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("countProjectTalkList"),getPageMap(paramMap)));
			}    
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(ProjectTalkDaoImpl.class.getName() + "_getProjectTalkList",e);
			throw new DaoException(e);
		}
	}

	@Override
	public int updateCreateUid(ProjectTalkBean entity) {
		try {
			 return sqlSessionTemplate.update(getSqlName("updateCreateUid"), entity);
		} catch (Exception e) {
			log.error(String.format("根据ID更新对象出错！语句：%s", getSqlName("updateCreateUid")), e);
			throw new DaoException(e);
		}
	}

}
