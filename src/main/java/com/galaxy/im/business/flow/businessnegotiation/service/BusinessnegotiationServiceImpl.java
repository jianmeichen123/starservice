package com.galaxy.im.business.flow.businessnegotiation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.businessnegotiation.dao.IBusinessnegotiationDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class BusinessnegotiationServiceImpl extends BaseServiceImpl<Test> implements IBusinessnegotiationService{
	private Logger log = LoggerFactory.getLogger(BusinessnegotiationServiceImpl.class);
	
	@Autowired
	IBusinessnegotiationDao dao;
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	/**
	 * flashpass:闪投
	 * investpass：投资
	 * veto：否决
	 */
	@Override
	public Map<String, Object> businessOperateStatus(Map<String, Object> paramMap) {
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("flashpass", false);
			result.put("investpass", false);
			result.put("veto", false);
			List<Map<String,Object>> dataList = dao.businessOperateStatus(paramMap);
			if(dataList!=null && dataList.size()>0){
				String dictCode;
				Integer pcount = 0;
				for(Map<String,Object> map : dataList){
					dictCode = CUtils.get().object2String(map.get("dictCode"), "");
					pcount = CUtils.get().object2Integer(map.get("pcount"), 0);
					//闪投
					if("meeting5Result:3".equals(dictCode)){
						if(pcount>0){
							result.put("flashpass", true);
						}
					}
					//投资
					else if("meeting5Result:4".equals(dictCode)){
						if(pcount>0){
							result.put("investpass", true);
						}
					}
					//否决
					else if("meeting5Result:2".equals(dictCode)){
						if(pcount>0){
							result.put("veto", true);
						}
					}
				}
			}
			return result;
		}catch(Exception e){
			log.error(BusinessnegotiationServiceImpl.class.getName() + ":businessOperateStatus",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改项目阶段，流程历史记录和业务类型编码
	 */
	@Override
	public boolean updateProjectStatus(Map<String, Object> paramMap) {
		try{
			return dao.updateProjectStatus(paramMap)>0;
		}catch(Exception e){
			log.error(BusinessnegotiationServiceImpl.class.getName() + ":updateProjectStatus",e);
			throw new ServiceException(e);
		}
	}
	

}
