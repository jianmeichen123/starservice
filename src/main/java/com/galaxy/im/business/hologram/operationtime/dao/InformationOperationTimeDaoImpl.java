package com.galaxy.im.business.hologram.operationtime.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.information.InformationOperationTime;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class InformationOperationTimeDaoImpl extends BaseDaoImpl<Test, Long> implements IInformationOperationTimeDao{
	private Logger log = LoggerFactory.getLogger(InformationOperationTimeDaoImpl.class);
	
	/**
	 * 获取更新时间
	 */
	@Override
	public InformationOperationTime getInformationTime(InformationOperationTime bean) {
		try{
			String sqlName = "com.galaxy.im.business.hologram.operationtime.dao.IInformationOperationTimeDao.getInformationTime";
			InformationOperationTime info =sqlSessionTemplate.selectOne(sqlName,bean);
			return info;
		}catch(Exception e){
			log.error(InformationOperationTimeDaoImpl.class.getName() + "：getInformationTime",e);
			throw new DaoException(e);
		}
	}

}
