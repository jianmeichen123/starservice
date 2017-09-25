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
public class ProjectDeliveryDaoImpl extends BaseDaoImpl<InformationListdata,Long> implements IProjectDeliveryDao{

	private Logger log = LoggerFactory.getLogger(ProjectDeliveryDaoImpl.class);
	
	public ProjectDeliveryDaoImpl(){
		super.setLogger(log);
	}

	@Override
	public QPage queryprodeliverypage(Map<String, Object> paramMap) {
		try{
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null){
			 contentList = sqlSessionTemplate.selectList(getSqlName("queryprodeliverypage") ,getPageMap(paramMap));
			 total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("selectprodeliveryCount"),getPageMap(paramMap)));
			}
			return new QPage(contentList,total);
		}catch(Exception e){
			log.error(ProjectDeliveryDaoImpl.class.getName() + "queryprodeliverypage",e);
			throw new DaoException(e);
		}
	}

	@Override
	public Object selectdelivery(Map<String, Object> paramMap) {
		try{
			return sqlSessionTemplate.selectList(getSqlName("selectdelivery"),paramMap);
		}catch(Exception e){
			log.error(ProjectDeliveryDaoImpl.class.getName() + "selectdelivery",e);
			throw new DaoException(e);
		}
	}
	
	
	
}
