package com.galaxy.im.business.flow.ceoreview.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface ICeoreviewService extends IBaseService<Test>{

	int saveRovalScheduling(Map<String, Object> paramMap);

	int updateCeoScheduling(Map<String, Object> paramMap);

}
