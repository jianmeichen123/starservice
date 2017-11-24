package com.galaxy.im.business.sopfile.service;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.db.service.IBaseService;

public interface ISopFileService extends IBaseService<Test>{

	Map<String, Object> getSopFileInfo(Map<String, Object> paramMap);

	List<Map<String, Object>> searchappFileList(Map<String, Object> paramMap);

	List<Map<String, Object>> getSopFileList(SopFileBean sopfile);

}
