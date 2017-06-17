package com.galaxy.im.business.flow.interview.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class InterviewDaoImpl extends BaseDaoImpl<Test, Long> implements IInterviewDao{
	private Logger log = LoggerFactory.getLogger(InterviewDaoImpl.class);
	
	public InterviewDaoImpl(){
		super.setLogger(log);
	}

	/**
	 * 项目是否存在通过／否决的访谈记录
	 */
	@Override
	public List<Map<String, Object>> hasPassInterview(Map<String, Object> paramMap) {
		String sqlName = "com.galaxy.im.business.flow.common.dao.IInterviewDao.hasPassInterview";
		try{
			return sqlSessionTemplate.selectList(sqlName,paramMap);
		}catch(Exception e){
			log.error(String.format("查询对象总数出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}


}
