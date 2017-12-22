package com.galaxy.im.business.sopfile.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.db.IBaseDao;

public interface ISopFileDao extends IBaseDao<Test, Long>{

	Map<String, Object> getSopFileInfo(Map<String, Object> paramMap);

	List<Map<String, Object>> searchappFileList(Map<String, Object> paramMap);

	List<Map<String, Object>> getSopFileList(SopFileBean sopfile);

	int delPostMeetingFile(SopFileBean sopfile);

	List<String> getFileNameList(Map<String, String> nameMap);

	long insertHistory(Map<String, Object> map);

	int updateSopFile(SopFileBean bean);

}
