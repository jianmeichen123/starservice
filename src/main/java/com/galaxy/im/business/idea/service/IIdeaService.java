package com.galaxy.im.business.idea.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.idea.IdeaBean;
import com.galaxy.im.common.db.service.IBaseService;

public interface IIdeaService extends IBaseService<Test>{

	IdeaBean queryIdeaById(Long id);

	int updateIdeaById(Map<String, Object> paramMap);

	int insertIdea(Map<String, Object> paramMap);


}
