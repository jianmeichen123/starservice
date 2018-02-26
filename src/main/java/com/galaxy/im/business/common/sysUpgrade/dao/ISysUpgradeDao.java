package com.galaxy.im.business.common.sysUpgrade.dao;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.message.SystemMessageUserBean;
import com.galaxy.im.common.db.IBaseDao;

public interface ISysUpgradeDao extends IBaseDao<Test, Long>{

	int closeSysUpgradeMessage(SystemMessageUserBean bean);

	Map<String, Object> getSysUpgradeMessage(Map<String, Object> paramMap);

	Map<String, Object> getSysMessage(Map<String, Object> paramMap);

}
