package com.galaxy.im.business.flow.stockequity.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IStockequityDao extends IBaseDao<Test, Long>{

	List<Map<String, Object>> operateStatus(Map<String, Object> paramMap);

}
