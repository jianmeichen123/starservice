package com.galaxy.im.business.flow.internalreview.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IInternalreviewService extends IBaseService<Test>{

	Map<String, Object> hasPassMeeting(Map<String, Object> paramMap);

	int saveCeoScheduling(Map<String, Object> paramMap);


}
