package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.business.project.dao.IProjectConsultantDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
@Service
public class ProjectConsultantServiceImpl extends BaseServiceImpl<InformationListdata> implements IProjectConsultantService{
	private static final Logger log = LoggerFactory.getLogger(ProjectConsultantServiceImpl.class);
	@Autowired
	IProjectConsultantDao dao;
	
	@Override
	protected IBaseDao<InformationListdata, Long> getBaseDao() {
		return dao;
	}

	@Override
	public QPage queryProjectPerson(Map<String, Object> paramMap) {
		try{
			return dao.queryProjectPerson(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "queryProjectPerson",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Object queryPersonInfo(Map<String, Object> paramMap) {
		try{
			return dao.queryPersonInfo(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "queryPersonInfo",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Object> queryStudyExperience(Map<String, Object> paramMap) {
		try{
			return dao.queryStudyExperience(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "queryStudyExperience",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Object> queryWorkExperience(Map<String, Object> paramMap) {
		try{
			return dao.queryWorkExperience(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "queryWorkExperience",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Object> queryEntrepreneurialExperience(Map<String, Object> paramMap) {
		try{
			return dao.queryEntrepreneurialExperience(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "queryEntrepreneurialExperience",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void addProjectPerson(Map<String, Object> paramMap) {
		try{
			 dao.addProjectPerson(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "addProjectPerson",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public InformationListdata selectInfoById(Map<String, Object> paramMap) {
		try{
			return dao.selectInfoById(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "selectInfoById",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateProjectPerson(Map<String, Object> paramMap) {
		try{
			 dao.updateProjectPerson(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "updateProjectPerson",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteProjectPersonById(Map<String, Object> paramMap) {
		try{
			 dao.deleteProjectPersonById(paramMap);
		}catch(Exception e){
			log.error(ProjectConsultantServiceImpl.class.getName() + "deleteProjectPersonById",e);
			throw new ServiceException(e);
		}
	}
	
	
}
