package com.galaxy.im.business.common.dict.service;

import java.util.Map;

import com.galaxy.im.bean.common.Dict;
import com.galaxy.im.common.db.service.IBaseService;

public interface IDictService extends IBaseService<Dict>{
	Map<String,Object> selectCallonFilter();

}
