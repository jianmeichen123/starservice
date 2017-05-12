package com.galaxy.im.business.common.dict.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.common.Dict;
import com.galaxy.im.common.db.IBaseDao;

public interface IDictDao extends IBaseDao<Dict, Long>{
	List<Map<String,Object>> selectCallonFilter();
}
