package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;
@Repository
public class ProjectEquitiesDaoImpl extends BaseDaoImpl<InformationListdata, Long> implements IProjectEquitiesDao{

	private Logger log = LoggerFactory.getLogger(ProjectEquitiesDaoImpl.class);
	
	public ProjectEquitiesDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public List<Object> selectFRInfo(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("selectFRInfo"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "selectFRInfo",e);
			throw new DaoException(e);
		}
	}

	@Override
	public QPage selectProjectShares(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
			 contentList = sqlSessionTemplate.selectList(getSqlName("selectProjectShares") ,getPageMap(paramMap));
			 total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("selectProjectSharesCount"),getPageMap(paramMap)));
			}
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "selectProjectShares",e);
			throw new DaoException(e);
		}
	}

	@Override
	public int addProjectShares(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.insert(getSqlName("addProjectShares"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "addProjectShares",e);
			throw new DaoException(e);
		}
		
	}

	@Override
	public InformationListdata selectInfoById(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("selectInfoById"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "selectInfoById",e);
			throw new DaoException(e);
		}
	}

	@Override
	public int updateProjectShares(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.update(getSqlName("updateProjectShares"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "updateProjectShares",e);
			throw new DaoException(e);
		}
		
	}

	@Override
	public int deleteProjectSharesById(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.delete(getSqlName("deleteProjectSharesById"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "deleteProjectSharesById",e);
			throw new DaoException(e);
		}
	}
	
	
	
}
