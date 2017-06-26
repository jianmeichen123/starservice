package com.galaxy.im.business.flow.ceoreview.dao;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface ICeoreviewDao extends IBaseDao<Test, Long>{

	int insertRovalScheduling(Map<String, Object> paramMap);

	int updateCeoScheduling(Map<String, Object> paramMap);

}
