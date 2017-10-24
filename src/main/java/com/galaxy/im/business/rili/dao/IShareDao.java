package com.galaxy.im.business.rili.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.schedule.ScheduleShared;
import com.galaxy.im.common.db.IBaseDao;

public interface IShareDao extends IBaseDao<Test, Long>{

	int delSharedUser(Map<String, Object> map);

	int saveSharedUsers(List<ScheduleShared> list);

	List<Map<String, Object>> querySharedUsers(Map<String, Object> map);

}
