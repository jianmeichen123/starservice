package com.galaxy.im.business.idea.dao;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.idea.IdeaBean;
import com.galaxy.im.common.db.IBaseDao;

public interface IIdeaDao extends IBaseDao<Test, Long>{

	/*
	 * 根据id查询
	 */
	IdeaBean queryIdeaById(Long id);

	/*
	 * 更新创意
	 */
	int updateIdeaById(Map<String, Object> paramMap);
	/*
	 * 添加创意
	 */
	int insertIdea(Map<String, Object> paramMap);

}
