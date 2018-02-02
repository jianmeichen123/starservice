package com.galaxy.im.business.project.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.business.project.dao.IProjectEquitiesDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
@Service
public class ProjectEquitiesServiceImpl extends BaseServiceImpl<InformationListdata> implements IProjectEquitiesService{
	private static final Logger log = LoggerFactory.getLogger(ProjectEquitiesServiceImpl.class);
	
	@Autowired
	private IProjectEquitiesDao dao;
	
	@Override
	protected IBaseDao<InformationListdata, Long> getBaseDao() {
		return dao;
	}

	@Override
	public List<Object> selectFRInfo(Map<String, Object> paramMap) {
		try {
			return dao.selectFRInfo(paramMap);
		} catch (Exception e) {
			log.error(ProjectEquitiesServiceImpl.class.getName() + "selectFRInfo",e);
			throw new ServiceException(e);
		}
		
	}

	@Override
	public QPage selectProjectShares(Map<String, Object> paramMap) {
		try {
			return dao.selectProjectShares(paramMap);
		} catch (Exception e) {
			log.error(ProjectEquitiesServiceImpl.class.getName() + "selectProjectShares",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int addProjectShares(Map<String, Object> paramMap) {
		try {
			return dao.addProjectShares(paramMap);
		} catch (Exception e) {
			log.error(ProjectEquitiesServiceImpl.class.getName() + "addProjectShares",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public InformationListdata selectInfoById(Map<String, Object> paramMap) {
		try {
			return dao.selectInfoById(paramMap);
		} catch (Exception e) {
			log.error(ProjectEquitiesServiceImpl.class.getName() + "selectInfoById",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateProjectShares(Map<String, Object> paramMap) {
		try {
			return dao.updateProjectShares(paramMap);
		} catch (Exception e) {
			log.error(ProjectEquitiesServiceImpl.class.getName() + "updateProjectShares",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int deleteProjectSharesById(Map<String, Object> paramMap) {
		try {
			return dao.deleteProjectSharesById(paramMap);
		} catch (Exception e) {
			log.error(ProjectEquitiesServiceImpl.class.getName() + "deleteProjectSharesById",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 添加法人信息
	 */
	@Override
	public int addFRInfo(Map<String, Object> paramMap) {
		try {
			int result =0;
			if (paramMap.containsKey("projectCompany")) {
				paramMap.put("contentDescribe1", paramMap.get("projectCompany"));
				paramMap.put("titleId", 1814);
				result = dao.addFRInfo(paramMap);
			} if (paramMap.containsKey("companyLegal")) {
				paramMap.remove("id");
				paramMap.put("contentDescribe1", paramMap.get("companyLegal"));
				paramMap.put("titleId", 1815);
				result = dao.addFRInfo(paramMap);
			} if (paramMap.containsKey("formationDate")) {
				paramMap.remove("id");
				paramMap.put("contentDescribe1", paramMap.get("formationDate"));
				paramMap.put("titleId", 1816);
				result = dao.addFRInfo(paramMap);
			}
			return result;
		} catch (Exception e) {
			log.error(ProjectEquitiesServiceImpl.class.getName() + "addFRInfo",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新法人信息
	 */
	@Override
	public int updateFRInfo(Map<String, Object> paramMap) {
		try {
			int result =0;
			//当三条信息有一条存在
			if (paramMap.get("pid")!=null) {
				paramMap.put("contentDescribe1", paramMap.get("projectCompany"));
				paramMap.put("id", paramMap.get("pid"));
				result = dao.updateFRInfo(paramMap);
			}else if (paramMap.containsKey("projectCompany")) {
					paramMap.put("contentDescribe1", paramMap.get("projectCompany"));
					paramMap.put("titleId", 1814);
					result = dao.addFRInfo(paramMap);
				} 
			
			if (paramMap.get("cid")!=null) {
				paramMap.put("contentDescribe1", paramMap.get("companyLegal"));
				paramMap.put("id", paramMap.get("cid"));
				result = dao.updateFRInfo(paramMap);
			}else if (paramMap.containsKey("companyLegal")) {
				paramMap.remove("id");
				paramMap.put("contentDescribe1", paramMap.get("companyLegal"));
				paramMap.put("titleId", 1815);
				result = dao.addFRInfo(paramMap);
			}
			if (paramMap.get("fid")!=null) {
				paramMap.put("contentDescribe1", paramMap.get("formationDate"));
				paramMap.put("id", paramMap.get("fid"));
				result = dao.updateFRInfo(paramMap);
			}else if (paramMap.containsKey("formationDate")) {
				paramMap.remove("id");
				paramMap.put("contentDescribe1", paramMap.get("formationDate"));
				paramMap.put("titleId", 1816);
				result = dao.addFRInfo(paramMap);
			}
			return result;
		} catch (Exception e) {
			log.error(ProjectEquitiesServiceImpl.class.getName() + "updateFRInfo",e);
			throw new ServiceException(e);
		}
	}
	
	
}
