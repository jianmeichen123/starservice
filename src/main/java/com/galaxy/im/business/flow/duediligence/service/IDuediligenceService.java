package com.galaxy.im.business.flow.duediligence.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IDuediligenceService extends IBaseService<Test>{

	Map<String, Object> duediligenceOperateStatus(Map<String, Object> paramMap);

}
