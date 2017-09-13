package com.galaxy.im.business.report.operationtime.service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.information.InformationOperationTime;
import com.galaxy.im.business.report.operationtime.dao.IInformationOperationTimeDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class InformationOperationTimeServiceImpl extends BaseServiceImpl<Test> implements IInformationOperationTimeService{
	private Logger log = LoggerFactory.getLogger(InformationOperationTimeServiceImpl.class);
	
	@Autowired
	IInformationOperationTimeDao dao;
	 
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}
	
	/**
	 * 获取更新时间
	 */
	@Override
	public Map<String,Object> getInformationTime(InformationOperationTime bean) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			InformationOperationTime oldInformation = dao.getInformationTime(bean);
			if(oldInformation != null){
				 Method method = oldInformation.getClass().getMethod("get" + bean.getReflect().toUpperCase().substring(0, 1)+bean.getReflect().substring(1));
				Date value = (Date) method.invoke(oldInformation);
				if(value != null){
					map.put("updateDate", value.getTime());
					map.put("projectId", bean.getProjectId());
					map.put("reflect", bean.getReflect());
				}
			}else{
				map.put("updateDate", "NotUpdated");
			}
		}catch (Exception e) {
			log.error(InformationOperationTimeServiceImpl.class.getName() + ":getInformationTime",e);
			throw new ServiceException(e);
		}
		return map;
	}

}
