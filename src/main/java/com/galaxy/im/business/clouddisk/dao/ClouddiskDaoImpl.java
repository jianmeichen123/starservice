package com.galaxy.im.business.clouddisk.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.clouddisk.CloudDiskFiles;
import com.galaxy.im.business.flow.businessnegotiation.dao.BusinessnegotiationDaoImpl;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ClouddiskDaoImpl extends BaseDaoImpl<CloudDiskFiles, Long> implements IClouddiskDao{
	private Logger log = LoggerFactory.getLogger(BusinessnegotiationDaoImpl.class);
	private String className = ClouddiskDaoImpl.class.getName();
	
	public ClouddiskDaoImpl(){
		super.setLogger(log);
	}

	/**
	 * 保存文件信息到云端
	 */
	@Override
	public boolean insertFileInfo(Map<String,Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.bean.clouddisk.CloudDiskFiles.insertFileInfo";
			return sqlSessionTemplate.insert(sqlName, paramMap)>0;
		}catch(Exception e){
			log.error(className + "：insertFileInfo",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取云端列表
	 */
	@Override
	public List<Map<String,Object>> getCloudFileList(long userId) {
		
		
		
		return null;
	}

	/**
	 * 获取用户的云盘信息
	 */
	@Override
	public List<Map<String, Object>> getPersonCloudInfo(Map<String,Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.bean.clouddisk.CloudDiskFiles.getPersonCloudInfo";
			return sqlSessionTemplate.selectList(sqlName, paramMap);
		}catch(Exception e){
			log.error(className + "：getPersonCloudInfo",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 重命名云端文件
	 */
	@Override
	public boolean renameCloudFile(Map<String,Object> paramMap){
		try{
			String sqlName = "com.galaxy.im.bean.clouddisk.CloudDiskFiles.renameCloudFile";
			return sqlSessionTemplate.update(sqlName, paramMap)>0;
		}catch(Exception e){
			log.error(className + "：renameCloudFile",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 删除云端文件
	 */
	@Override
	public boolean deleteCloudFile(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.bean.clouddisk.CloudDiskFiles.deleteCloudFile";
			return sqlSessionTemplate.update(sqlName, paramMap)>0;
		}catch(Exception e){
			log.error(className + "：deleteCloudFile",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取已经使用的容量
	 */
	@Override
	public List<Map<String,Object>> getUsedVolumes(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.bean.clouddisk.CloudDiskFiles.getUsedVolumes";
			return sqlSessionTemplate.selectList(sqlName, paramMap);
		}catch(Exception e){
			log.error(className + "：deleteCloudFile",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 批量删除
	 */
	@Override
	public Integer deleteBatches(List<Long> ids){
		try{
			String sqlName = "com.galaxy.im.bean.clouddisk.CloudDiskFiles.deleteBatches";
			return sqlSessionTemplate.delete(sqlName, ids);
		}catch(Exception e){
			log.error(className + ":deleteBatches",e);
			throw new DaoException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
}
