package com.galaxy.im.business.common.config.service;

import com.galaxy.im.bean.common.Config;
import com.galaxy.im.common.db.service.IBaseService;

public interface ConfigService extends IBaseService<Config>{
	
	/**
	 * 为了防止并发添加项目，生成的code一样，必须每次生成就自动加一
	 * @return
	 * @throws Exception
	 */
	public Config createCode() throws Exception;
	
	public Config getByKey(String key, boolean createIfNotExist) throws Exception;
	
}