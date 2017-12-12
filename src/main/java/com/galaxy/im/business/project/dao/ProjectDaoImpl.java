package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ProjectDaoImpl extends BaseDaoImpl<ProjectBean,Long> implements IProjectDao{
	private Logger log = LoggerFactory.getLogger(ProjectDaoImpl.class);
	
	public ProjectDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public Map<String,Object> getBaseProjectInfo(Long id) {
		try{
			Map<String,Object> bean = sqlSessionTemplate.selectOne(getSqlName("getBaseProjectInfo"),id);
			return bean;
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_getBaseProjectInfo",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Map<String, Object> getProjectInoIsNull(Long id) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("getProjectInoIsNull"),id);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_getProjectInoIsNull",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Integer projectIsYJZ(Long projectId) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("projectIsYJZ"),projectId);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_projectIsYJZ",e);
			throw new DaoException(e);
		}
	}
	
	@Override
	public Integer projectIsShow(Long projectId) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("projectIsShow"),projectId);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_projectIsShow",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 获取项目信息
	 */
	@Override
	public List<SopProjectBean> getSopProjectList(SopProjectBean bean) {
		try{
			Map<String, Object> params = BeanUtils.toMap(bean);
			return sqlSessionTemplate.selectList(getSqlName("getSopProjectList"),params);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_getSopProjectList",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 创建项目
	 */
	@Override
	public long saveProject(SopProjectBean bean) {
		try{
			sqlSessionTemplate.insert(getSqlName("saveProject"),bean);
			return bean.getId();
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_saveProject",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 删除项目下的所有投资方
	 */
	@Override
	public int deleteInvestById(SopProjectBean bean) {
		try {
			return sqlSessionTemplate.delete(getSqlName("deleteInvestById"), bean);
		} catch (Exception e) {
			throw new DaoException(String.format("删除对象出错！语句：%s", getSqlName("_deleteInvestById")), e);
		}
	}
	
	/**
	 * 更新项目下的所有投资方的投资形式
	 */
	@Override
	public int updateInvestById(SopProjectBean bean) {
		try {
			return sqlSessionTemplate.update(getSqlName("updateInvestById"), bean);
		} catch (Exception e) {
			throw new DaoException(String.format("编辑对象出错！语句：%s", getSqlName("_updateInvestById")), e);
		}
	}

	/**
	 * 编辑项目
	 */
	@Override
	public int updateProject(SopProjectBean bean) {
		try{
			return sqlSessionTemplate.update(getSqlName("updateProject"),bean);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_updateProject",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 更加项目id查询项目信息
	 */
	@Override
	public SopProjectBean getProjectInfoById(Long id) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("getProjectInfoById"),id);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "_getProjectInfoById",e);
			throw new DaoException(e);
		}
	}

	/**
	 * 项目是否处于接触访谈阶段
	 */
	@Override
	public int projectIsInterview(Long id) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("projectIsInterview"),id);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "projectIsInterview",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Map<String, Object> selectBaseProjectInfo(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("selectBaseProjectInfo"),paramMap);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "selectBaseProjectInfo",e);
			throw new DaoException(e);
		}
	}

	@Override
	public int updateProjects(Map<String, Object> hashmap) {
		try{
			return sqlSessionTemplate.update(getSqlName("updateProjects"),hashmap);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "updateProjects",e);
			throw new DaoException(e);
		}
	}

	@Override
	public InformationResult findResultInfoById(Map<String, Object> hashmap) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("findResultInfoById"),hashmap);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "findResultInfoById",e);
			throw new DaoException(e);
		}
	}

	@Override
	public int addInformationResult(List<InformationResult> list) {
		try{
			return sqlSessionTemplate.insert(getSqlName("addInformationResult"),list);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "addInformationResult",e);
			throw new DaoException(e);
		}
	}

	@Override
	public int updateInformationResult(InformationResult result) {
		try{
			return sqlSessionTemplate.update(getSqlName("updateInformationResult"),result);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "updateInformationResult",e);
			throw new DaoException(e);
		}
	}

	@Override
	public int delProjectUserIds(Map<String, Object> map) {
		try{
			return sqlSessionTemplate.delete(getSqlName("delProjectUserIds"),map);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "delProjectUserIds",e);
			throw new DaoException(e);
		}
	}

	@Override
	public long findInputTitleId(Map<String, Object> hashmap) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("findInputTitleId"),hashmap);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "findInputTitleId",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Map<String, Object> selectProjectSoureInfo(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectOne(getSqlName("selectProjectSoureInfo"),paramMap);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "selectProjectSoureInfo",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<Map<String, Object>> selectProjectUserInfo(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("selectProjectUserInfo"),paramMap);
		}catch(Exception e){
			log.error(ProjectDaoImpl.class.getName() + "selectProjectUserInfo",e);
			throw new DaoException(e);
		}
	}

}
