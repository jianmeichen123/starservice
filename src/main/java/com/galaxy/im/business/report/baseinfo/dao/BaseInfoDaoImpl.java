package com.galaxy.im.business.report.baseinfo.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.report.InformationDictionary;
import com.galaxy.im.bean.report.InformationFixedTable;
import com.galaxy.im.bean.report.InformationListdata;
import com.galaxy.im.bean.report.InformationListdataRemark;
import com.galaxy.im.bean.report.InformationTitle;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class BaseInfoDaoImpl extends BaseDaoImpl<Test, Long> implements IBaseInfoDao{
	
	private Logger log = LoggerFactory.getLogger(BaseInfoDaoImpl.class);

	/**
	 * 根据父信息  code 、 id 查询子 code 集合
	 * 
	 */
	@Override
	public List<InformationTitle> selectChildsByPid(Map<String, Object> params) {
		try{
			String sqlName = "com.galaxy.im.business.report.baseinfo.dao.IBaseInfoDao.selectChildsByPid";
			List<InformationTitle> contentList = sqlSessionTemplate.selectList(sqlName,params);
			return contentList;
		}catch(Exception e){
			log.error(BaseInfoDaoImpl.class.getName() + "selectChildsByPid",e);
			throw new DaoException(e);
		}
	}
	
	@Override
	public List<InformationDictionary> getInformationDictionary() {
		try{
			String sqlName = "com.galaxy.im.business.report.baseinfo.dao.IBaseInfoDao.getInformationDictionary";
			List<InformationDictionary> contentList = sqlSessionTemplate.selectList(sqlName);
			return contentList;
		}catch(Exception e){
			log.error(BaseInfoDaoImpl.class.getName() + "getInformationDictionary",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<InformationListdata> getInformationListdata(InformationListdata listdataQuery) {
		try{
			Map<String, Object> params = BeanUtils.toMap(listdataQuery);
			String sqlName = "com.galaxy.im.business.report.baseinfo.dao.IBaseInfoDao.getInformationListdata";
			List<InformationListdata> contentList = sqlSessionTemplate.selectList(sqlName,params);
			return contentList;
		}catch(Exception e){
			log.error(BaseInfoDaoImpl.class.getName() + "getInformationListdata",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<InformationResult> getInformationResult(InformationResult resultQuery) {
		try{
			Map<String, Object> params = BeanUtils.toMap(resultQuery);
			String sqlName = "com.galaxy.im.business.report.baseinfo.dao.IBaseInfoDao.getInformationResult";
			List<InformationResult> contentList = sqlSessionTemplate.selectList(sqlName,params);
			return contentList;
		}catch(Exception e){
			log.error(BaseInfoDaoImpl.class.getName() + "getInformationResult",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<InformationFixedTable> getInformationFixedTable(InformationFixedTable fixedTableQuery) {
		try{
			Map<String, Object> params = BeanUtils.toMap(fixedTableQuery);
			String sqlName = "com.galaxy.im.business.report.baseinfo.dao.IBaseInfoDao.getInformationFixedTable";
			List<InformationFixedTable> contentList = sqlSessionTemplate.selectList(sqlName,params);
			return contentList;
		}catch(Exception e){
			log.error(BaseInfoDaoImpl.class.getName() + "getInformationFixedTable",e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<InformationListdataRemark> getInformationListdataRemark(InformationListdataRemark headerQuery) {
		try{
			Map<String, Object> params = BeanUtils.toMap(headerQuery);
			String sqlName = "com.galaxy.im.business.report.baseinfo.dao.IBaseInfoDao.getInformationListdataRemark";
			List<InformationListdataRemark> contentList = sqlSessionTemplate.selectList(sqlName,params);
			return contentList;
		}catch(Exception e){
			log.error(BaseInfoDaoImpl.class.getName() + "getInformationListdataRemark",e);
			throw new DaoException(e);
		}
	}

}
