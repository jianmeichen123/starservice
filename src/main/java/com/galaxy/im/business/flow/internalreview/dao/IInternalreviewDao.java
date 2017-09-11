package com.galaxy.im.business.flow.internalreview.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IInternalreviewDao extends IBaseDao<Test, Long>{

	List<Map<String, Object>> hasPassMeeting(Map<String, Object> paramMap);

	int insertCeoScheduling(Map<String, Object> paramMap);

	Date selectTime(Map<String, Object> paramMap);

}
