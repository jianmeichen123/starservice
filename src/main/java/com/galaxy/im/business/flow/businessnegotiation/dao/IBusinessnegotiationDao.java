package com.galaxy.im.business.flow.businessnegotiation.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IBusinessnegotiationDao extends IBaseDao<Test, Long>{

	List<Map<String, Object>> businessOperateStatus(Map<String, Object> paramMap);

	int updateProjectStatus(Map<String, Object> paramMap);

}
