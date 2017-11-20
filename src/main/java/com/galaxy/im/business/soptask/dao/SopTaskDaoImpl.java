package com.galaxy.im.business.soptask.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class SopTaskDaoImpl extends BaseDaoImpl<SopTask, Long> implements ISopTaskDao{
	private Logger log = LoggerFactory.getLogger(SopTaskDaoImpl.class);
	
	public SopTaskDaoImpl(){
		super.setLogger(log);
	}
	
	//待办任务列表
	@Override
	public QPage taskListByRole(Map<String, Object> paramMap) {
		try {
			List<Map<String,Object>> contentList = null;
			int total = 0;
			if(paramMap!=null&&paramMap.get("keyWord")==null && paramMap.get("pageNum")!=null&&paramMap.get("pageSize")!=null){
			 contentList = sqlSessionTemplate.selectList(getSqlName("taskListByRole") ,getPageMap(paramMap));
			 total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("taskListCount"),getPageMap(paramMap)));
			}else{
				contentList = sqlSessionTemplate.selectList(getSqlName("taskListByRole") ,paramMap);
				total = CUtils.get().object2Integer(sqlSessionTemplate.selectOne(getSqlName("taskListCount"),paramMap));
			}
			return new QPage(contentList,total);
		} catch (Exception e) {
			log.error(getSqlName("taskListByRole"),e);
			throw new DaoException(e);
		}
	}

	//获取部门id
	@Override
	public long getDepId(Long id) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName("selectDeptId"),id);
		} catch (Exception e) {
			log.error(getSqlName("taskListByRole"),e);
			throw new DaoException(e);
		}
	}

	//待办任务详情
	@Override
	public Object taskInfo(Map<String, Object> paramMap) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName("taskInfo"),paramMap);
		} catch (Exception e) {
			log.error(getSqlName("taskInfo"),e);
			throw new DaoException(e);
		}
	}

	//认领
	@Override
	public int applyTask(SopTask sopTask) {
		try {
			return sqlSessionTemplate.update(getSqlName("applyTask"),sopTask);
		} catch (Exception e) {
			log.error(getSqlName("applyTask"),e);
			throw new DaoException(e);
		}
	}
	

}
