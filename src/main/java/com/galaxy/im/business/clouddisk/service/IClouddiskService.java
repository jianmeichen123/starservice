package com.galaxy.im.business.clouddisk.service;

import java.util.Map;

import com.galaxy.im.bean.clouddisk.CloudDiskFiles;
import com.galaxy.im.common.db.service.IBaseService;

public interface IClouddiskService extends IBaseService<CloudDiskFiles>{
	boolean saveFileInfo(Map<String,Object> paramMap);
	Map<String,Object> getPersonCloudInfo(Map<String,Object> paramMap);
	boolean renameCloudFile(Map<String,Object> paramMap);
	boolean deleteCloudFile(Map<String, Object> paramMap);
	Long getUsedVolumes(Map<String,Object> paramMap);
	
	Object getCloudFileList(long userId);
	
	Object isVolumnEnough();
}
