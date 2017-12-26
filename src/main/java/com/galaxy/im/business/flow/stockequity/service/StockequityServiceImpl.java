package com.galaxy.im.business.flow.stockequity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.stockequity.dao.IStockequityDao;
import com.galaxy.im.business.sopfile.dao.ISopFileDao;
import com.galaxy.im.business.soptask.dao.ISopTaskDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class StockequityServiceImpl extends BaseServiceImpl<Test> implements IStockequityService{
	private Logger log = LoggerFactory.getLogger(StockequityServiceImpl.class);
	@Autowired
	private IStockequityDao dao;
	@Autowired
	ISopTaskDao taskDao;
	@Autowired
	ISopFileDao fileDao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	/**
	 * 是否上传完成工商转让凭证，工商，资金代办任务状态已完成   或文件状态为已上传，已放弃
	 */
	@Override
	public Map<String, Object> operateStatus(Map<String, Object> paramMap) {
		try{
			boolean res=false;
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("pass", false); 
			List<Map<String,Object>> dataList = dao.operateStatus(paramMap);
			if(dataList!=null && dataList.size()>0){
				String dictCode;
				Integer pcount = 0;
				for(Map<String,Object> map : dataList){
					dictCode = CUtils.get().object2String(map.get("dictCode"), "");
					pcount = CUtils.get().object2Integer(map.get("pcount"), 0);
					//是否上传了"工商转让凭证"
					if("fileWorktype:8".equals(dictCode)){
						if(pcount>0){
							res=true;
						}
					}
				}
			}
			//工商，资金代办任务的状态
			List<Integer> taskFlagList = new ArrayList<Integer>();
			taskFlagList.add(StaticConst.TASK_FLAG_ZJBF);
			taskFlagList.add(StaticConst.TASK_FLAG_GSBG);
			paramMap.put("taskFlagList", taskFlagList);
			long count = taskDao.getCountByTaskStatus(paramMap);
			
			//上传文件的状态（已上传，已放弃）
			List<String> fileWorkTypeList = new ArrayList<String>();
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_8);
			fileWorkTypeList.add(StaticConst.FILE_WORKTYPE_9);
			paramMap.put("fileWorkTypeList", fileWorkTypeList);
			
			List<String> fileStatusList = new ArrayList<String>();
			fileStatusList.add(StaticConst.FILE_STATUS_2);
			fileStatusList.add(StaticConst.FILE_STATUS_4);
			paramMap.put("fileStatusList", fileStatusList);
			
			long cc =fileDao.getCountByFileStatus(paramMap);
			if ((res && count>=2) ||cc>=2) {
				result.put("pass", true);
			}
			
			return result;
		}catch(Exception e){
			log.error(StockequityServiceImpl.class.getName() + ":operateStatus",e);
			throw new ServiceException(e);
		}
	}
}
