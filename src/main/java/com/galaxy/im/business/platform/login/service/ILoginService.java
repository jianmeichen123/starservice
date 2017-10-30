package com.galaxy.im.business.platform.login.service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;
import com.galaxyinternet.model.user.UserLogonHis;

public interface ILoginService extends IBaseService<Test>{

	void saveLogonHis(UserLogonHis userLogonHis);

}
