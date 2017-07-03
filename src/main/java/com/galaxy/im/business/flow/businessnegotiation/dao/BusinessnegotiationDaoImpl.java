package com.galaxy.im.business.flow.businessnegotiation.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class BusinessnegotiationDaoImpl extends BaseDaoImpl<Test, Long> implements IBusinessnegotiationDao{
	private Logger log = LoggerFactory.getLogger(BusinessnegotiationDaoImpl.class);
	
	public BusinessnegotiationDaoImpl(){
		super.setLogger(log);
	}
	
	/**
	 * 项目相关会议的结论和个数
	 */
	@Override
	public List<Map<String, Object>> businessOperateStatus(Map<String, Object> paramMap) {
		try{
			String sqlName = "com.galaxy.im.business.flow.businessnegotiation.dao.IBusinessnegotiationDao.businessOperateStatus";
			List<Map<String, Object>> map =sqlSessionTemplate.selectList(sqlName,paramMap);
			return map;
		}catch(Exception e){
			log.error(BusinessnegotiationDaoImpl.class.getName() + "：businessOperateStatus",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 修改项目阶段，流程历史记录和业务类型编码
	 */
	@Override
	public int updateProjectStatus(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.businessnegotiation.dao.IBusinessnegotiationDao.updateProjectStatus";
		try{
			return sqlSessionTemplate.update(sqlName,paramMap);
		}catch(Exception e){
			log.error(BusinessnegotiationDaoImpl.class.getName() + "：updateProjectStatus",e);
			throw new DaoException(e);
		}
	}
	
}
