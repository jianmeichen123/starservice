package com.galaxy.im.business.flow.common.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class FlowCommonDaoImpl extends BaseDaoImpl<ProjectBean, Long> implements IFlowCommonDao{
	private Logger log = LoggerFactory.getLogger(FlowCommonDaoImpl.class);
	
	public FlowCommonDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public Map<String, Object> projectStatus(Map<String, Object> paramMap) {
		try{
			Map<String,Object> tt = sqlSessionTemplate.selectOne(getSqlName("projectStatus"));
			return tt;
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", "com.galaxy.im.business.flow.common.dao.IFlowCommonDao.projectStatus"), e);
			throw new DaoException(e);
		}
	}
	
	
}
