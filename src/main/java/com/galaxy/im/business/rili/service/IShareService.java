package com.galaxy.im.business.rili.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.schedule.ScheduleShared;
import com.galaxy.im.common.db.service.IBaseService;

public interface IShareService extends IBaseService<Test>{

	int delSharedUser(Map<String, Object> map);

	int saveSharedUsers(Long guserid, ScheduleShared query);

	List<Map<String, Object>> querySharedUsers(HttpServletRequest request, HttpServletResponse response, Long guserid);

	List<Map<String, Object>> queryMySharedUsers(HttpServletRequest request, HttpServletResponse response, Long guserid,
			String toUname);

	List<Map<String, Object>> queryAppPerson(HttpServletRequest request, HttpServletResponse response, Long guserid,
			Map<String, Object> map);

	List<Map<String, Object>> queryDeptUinfo(HttpServletRequest request, HttpServletResponse response, Long guserid,
			Map<String, Object> map);

}
