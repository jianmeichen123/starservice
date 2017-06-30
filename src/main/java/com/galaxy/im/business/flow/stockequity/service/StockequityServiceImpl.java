package com.galaxy.im.business.flow.stockequity.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.common.service.FlowCommonServiceImpl;
import com.galaxy.im.business.flow.stockequity.dao.IStockequityDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class StockequityServiceImpl extends BaseServiceImpl<Test> implements IStockequityService{
	private Logger log = LoggerFactory.getLogger(StockequityServiceImpl.class);
	@Autowired
	private IStockequityDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	/**
	 * 是否上传完成工商转让凭证
	 */
	@Override
	public Map<String, Object> operateStatus(Map<String, Object> paramMap) {
		try{
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
							result.put("pass", true);
						}
					}
				}
			}
			return result;
		}catch(Exception e){
			log.error(StockequityServiceImpl.class.getName() + ":operateStatus",e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 流程进入到下一阶段
	 */
	@Override
	public boolean enterNextFlow(Map<String, Object> paramMap) {
		try{
			return dao.enterNextFlow(paramMap)>0;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":enterNextFlow",e);
			throw new ServiceException(e);
		}
	}
}
