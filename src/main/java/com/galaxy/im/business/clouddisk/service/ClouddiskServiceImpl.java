package com.galaxy.im.business.clouddisk.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.clouddisk.CloudDiskFiles;
import com.galaxy.im.business.clouddisk.dao.IClouddiskDao;
import com.galaxy.im.business.flow.businessnegotiation.service.BusinessnegotiationServiceImpl;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.DaoException;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class ClouddiskServiceImpl extends BaseServiceImpl<CloudDiskFiles> implements IClouddiskService{
	private Logger log = LoggerFactory.getLogger(BusinessnegotiationServiceImpl.class);
	
	@Autowired
	IClouddiskDao dao; 
	
	@Override
	protected IBaseDao<CloudDiskFiles, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 保存文件信息到云端
	 */
	@Override
	public boolean saveFileInfo(Map<String,Object> paramMap) {
		try{
			return dao.insertFileInfo(paramMap);
			
		}catch(DaoException daoE){
			throw daoE;
		}catch(Exception e){
			log.error(ClouddiskServiceImpl.class.getName() + ":saveFileInfo",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Object getCloudFileList(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取个人信息
	 */
	@Override
	public Map<String,Object> getPersonCloudInfo(Map<String,Object> paramMap){
		Map<String,Object> resMap = null;
		try{
			resMap = new HashMap<String,Object>();
			List<Map<String,Object>> dataList = dao.getPersonCloudInfo(paramMap);
			if(dataList!=null && dataList.size()>0){
				//计算已经使用量
				Long usedVolume = 0L;
				for(int i=0;i<dataList.size();i++){
					usedVolume += CUtils.get().object2Long(dataList.get(i).get("usedFileSize"), 0L);
				}
				
				resMap.put("typeList", dataList);
				resMap.put("gogalVolume", StaticConst.CLOUD_VOLUME_DOSE/1024/1024/1024 + "G");
				
				//已经使用比
				BigDecimal bd = new BigDecimal(StaticConst.CLOUD_VOLUME_DOSE);
				BigDecimal b1 = new BigDecimal(usedVolume);
				String usedString = b1.divide(bd,6,BigDecimal.ROUND_UP).toString();
				
				resMap.put("usedRate", usedString);
				
				//已经使用 resMap.put("usedVolume", usedVolume);
				BigDecimal ub1 = new BigDecimal(usedVolume);
				if(usedVolume>1024*1024*1024){
					BigDecimal ub2 = new BigDecimal(1024*1024*1024);
					resMap.put("usedVolume", ub1.divide(ub2,2,BigDecimal.ROUND_UP).toString() + "G");
				}else if(usedVolume>1024*1024){
					BigDecimal ub2 = new BigDecimal(1024*1024);
					resMap.put("usedVolume", ub1.divide(ub2,2,BigDecimal.ROUND_UP).toString() + "M");
				}else if(usedVolume>1024){
					BigDecimal ub2 = new BigDecimal(1024);
					resMap.put("usedVolume", ub1.divide(ub2,2,BigDecimal.ROUND_UP).toString() + "K");
				}else{
					resMap.put("usedVolume", usedVolume + "B");
				}
			}
		}catch(DaoException daoE){
			throw daoE;
		}catch(Exception e){
			log.error(ClouddiskServiceImpl.class.getName() + ":getPersonCloudInfo",e);
			throw new ServiceException(e);
		}
		return resMap;
	}

	/**
	 * 获取用户已经使用的容量大小
	 */
	@Override
	public Long getUsedVolumes(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> dataList = dao.getUsedVolumes(paramMap);
			if(dataList!=null && dataList.size()>0){
				if(dataList.get(0)!=null && dataList.get(0).containsKey("fileSize")){
					return CUtils.get().object2Long(dataList.get(0).get("fileSize"),0L);
				}
			}
		}catch(DaoException daoE){
			throw daoE;
		}catch(Exception e){
			log.error(ClouddiskServiceImpl.class.getName() + ":getUsedVolumes",e);
			throw new ServiceException(e);
		}
		return -1L;
	}

	/**
	 * 重命名云端文件
	 */
	@Override
	public boolean renameCloudFile(Map<String, Object> paramMap) {
		try{
			return dao.renameCloudFile(paramMap);
		}catch(DaoException daoE){
			throw daoE;
		}catch(Exception e){
			log.error(ClouddiskServiceImpl.class.getName() + ":renameCloudFile",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 删除云端文件
	 */
	@Override
	public boolean deleteCloudFile(Map<String, Object> paramMap) {
		try{
			return dao.deleteCloudFile(paramMap);
		}catch(DaoException daoE){
			throw daoE;
		}catch(Exception e){
			log.error(ClouddiskServiceImpl.class.getName() + ":deleteCloudFile",e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 批量删除云端文件
	 */
	@Override
	public Integer deleteBatches(Map<String,Object> paramMap) {
		try{
			return dao.deleteBatches(paramMap);
		}catch(DaoException daoE){
			throw daoE;
		}catch(Exception e){
			log.error(ClouddiskServiceImpl.class.getName() + ":deleteBatches",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取云端文件全部文件的key
	 */
	@Override
	public List<String> getCloudFileKeys(Map<String, Object> paramMap) {
		try{
			return dao.getCloudFileKeys(paramMap);
		}catch(DaoException daoE){
			throw daoE;
		}catch(Exception e){
			log.error(ClouddiskServiceImpl.class.getName() + ":getCloudFileKeys",e);
			throw new ServiceException(e);
		}
	}

	
}
