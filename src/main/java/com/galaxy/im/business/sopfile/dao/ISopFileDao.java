package com.galaxy.im.business.sopfile.dao;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface ISopFileDao extends IBaseDao<Test, Long>{

	Map<String, Object> getBusinessPlanFile(Map<String, Object> paramMap);

}
