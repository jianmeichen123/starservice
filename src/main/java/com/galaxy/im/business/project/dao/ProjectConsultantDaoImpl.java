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
public class ProjectConsultantDaoImpl extends BaseDaoImpl<InformationListdata, Long> implements IProjectConsultantDao{
private Logger log = LoggerFactory.getLogger(ProjectConsultantDaoImpl.class);
	
	public ProjectConsultantDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public QPage queryProjectPerson(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
			 contentList = sqlSessionTemplate.selectList(getSqlName("queryProjectPerson") ,getPageMap(paramMap));
			 total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("selectCount"),getPageMap(paramMap)));
			}
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(ProjectConsultantDaoImpl.class.getName() + "queryProjectPerson",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Object queryPersonInfo(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("queryPersonInfo"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "queryPersonInfo",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Object> queryStudyExperience(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("queryStudyExperience"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "queryStudyExperience",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Object> queryWorkExperience(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("queryWorkExperience"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "queryWorkExperience",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Object> queryEntrepreneurialExperience(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("queryEntrepreneurialExperience"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "queryEntrepreneurialExperience",e);
			throw new DaoException(e);
		}
	}

	@Override
	public void addProjectPerson(Map<String, Object> paramMap) {
		try{
			sqlSessionTemplate.insert(getSqlName("addProjectPerson"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "addProjectPerson",e);
			throw new DaoException(e);
		}
	}

	@Override
	public InformationListdata selectInfoById(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("selectPersonInfoById"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "selectPersonInfoById",e);
			throw new DaoException(e);
		}
	}

	@Override
	public void updateProjectPerson(Map<String, Object> paramMap) {
		try{
			sqlSessionTemplate.update(getSqlName("updateProjectPerson"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "updateProjectPerson",e);
			throw new DaoException(e);
		}
		
	}

	@Override
	public void deleteProjectPersonById(Map<String, Object> paramMap) {
		try{
			sqlSessionTemplate.delete(getSqlName("deleteProjectPersonById"),paramMap);
		}catch(Exception e){
			log.error(ProjectEquitiesDaoImpl.class.getName() + "deleteProjectPersonById",e);
			throw new DaoException(e);
		}
	}
	
	
}
