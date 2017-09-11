package com.galaxy.im.business.flow.duediligence.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IDuediligenceDao extends IBaseDao<Test, Long>{

	List<Map<String, Object>> duediligenceOperateStatus(Map<String, Object> paramMap);

}
