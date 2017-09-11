package com.galaxy.im.business.flow.projectapproval.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;

public interface IProjectapprovalDao extends IBaseDao<Test, Long>{

	List<Map<String, Object>> approvalOperateStatus(Map<String, Object> paramMap);

	int updateMeetingScheduling(Map<String, Object> paramMap);

	int getMeetingCount(Map<String, Object> paramMap);

}
