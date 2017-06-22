package com.galaxy.im.business.flow.projectapproval.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IProjectapprovalService extends IBaseService<Test>{

	Map<String, Object> approvalOperateStatus(Map<String, Object> paramMap);

}
