package com.galaxy.im.business.clouddisk.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.clouddisk.CloudDiskFiles;
import com.galaxy.im.common.db.IBaseDao;

public interface IClouddiskDao extends IBaseDao<CloudDiskFiles, Long>{
	boolean insertFileInfo(Map<String,Object> paramMap);
	List<Map<String, Object>> getPersonCloudInfo(Map<String,Object> paramMap);
	boolean renameCloudFile(Map<String,Object> paramMap);
	boolean deleteCloudFile(Map<String,Object> paramMap);
	List<Map<String,Object>> getUsedVolumes(Map<String,Object> paramMap);
	List<Map<String,Object>> getCloudFileList(long userId);
	Integer deleteBatches(List<Long> ids);
	
}
