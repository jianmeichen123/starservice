package com.galaxy.im.business.platform.login.dao;

import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxyinternet.model.user.UserLogonHis;

public interface ILoginDao extends IBaseDao<Test, Long>{

	void saveLogonHis(UserLogonHis bean);

	UserLogonHis findUserLogonHis(Map<String, Object> paramMap);

	void updateLogonHis(UserLogonHis his);


}
