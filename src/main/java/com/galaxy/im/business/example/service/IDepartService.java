package com.galaxy.im.business.example.service;

import com.galaxy.im.bean.DepartBean;
import com.galaxy.im.bean.DepartBeanVO;
import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.service.IBaseService;

public interface IDepartService extends IBaseService<DepartBean> {
	DepartBean getDepartById(DepartBeanVO vo);
//	DepartBean getDepartByBean(DepartBeanVO vo);
//	DepartBean getDepartByMap(Map<String,Object> paramMap);
//	List<DepartBean> getDepartList(DepartBeanVO vo);
	void saveTest(Test testBean);
}
