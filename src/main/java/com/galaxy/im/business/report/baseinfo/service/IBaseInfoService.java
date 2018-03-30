package com.galaxy.im.business.report.baseinfo.service;

import java.util.List;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.report.InformationTitle;
import com.galaxy.im.common.db.service.IBaseService;

public interface IBaseInfoService extends IBaseService<Test>{

	List<InformationTitle> searchWithData(String titleId, String projectId);

}
