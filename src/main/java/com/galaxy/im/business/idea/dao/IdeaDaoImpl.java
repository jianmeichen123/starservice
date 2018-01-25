package com.galaxy.im.business.idea.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.idea.IdeaBean;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class IdeaDaoImpl extends BaseDaoImpl<IdeaBean, Long> implements IIdeaDao{
	private Logger log = LoggerFactory.getLogger(IdeaDaoImpl.class);
	
	public IdeaDaoImpl(){
		super.setLogger(log);
	}
	
	@Override
	public IdeaBean queryIdeaById(Long id) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName("queryIdeaById"),id);
		} catch (Exception e) {
			log.error(getSqlName("queryIdeaById"),e);
			throw new DaoException(e);
		}
	}

	@Override
	public int updateIdeaById(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.update(getSqlName("updateIdeaById"),paramMap);
		} catch (Exception e) {
			log.error(getSqlName("updateIdeaById"),e);
			throw new DaoException(e);
		}
	}

	@Override
	public int insertIdea(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.insert(getSqlName("insertIdea"),paramMap);
		} catch (Exception e) {
			log.error(getSqlName("insertIdea"),e);
			throw new DaoException(e);
		}
	}

}
