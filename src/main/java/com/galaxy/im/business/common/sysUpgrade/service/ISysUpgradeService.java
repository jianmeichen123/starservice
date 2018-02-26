package com.galaxy.im.business.common.sysUpgrade.service;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.message.SystemMessageUserBean;
import com.galaxy.im.common.db.service.IBaseService;

public interface ISysUpgradeService extends IBaseService<Test>{

	Map<String, Object> getSysUpgradeMessage(Map<String, Object> paramMap);

	int closeSysUpgradeMessage(SystemMessageUserBean bean);

	Map<String, Object> getSysMessage(Map<String, Object> paramMap);

}
