package com.galaxy.im.business.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.db.Page;
import com.galaxy.im.common.db.PageRequest;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class SopProjectDaoImpl extends BaseDaoImpl<ProjectBo,Long> implements ISopProjectDao{

	/*@Override
	public Page<SopProjectBean> querygjzProjectList(ProjectBo query, PageRequest pageable) {
		List<SopProjectBean> contentList = null;
		long total = 0L;
		try {
			contentList = sqlSessionTemplate.selectList( getSqlName("selectBygjz"),getParams(query, pageable));
			total = CUtils.get().object2Long(sqlSessionTemplate.selectOne(getSqlName("selectBygjzCount"),getParams(query, pageable)));		
			return new  Page<SopProjectBean>(contentList, pageable, total); 
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName("selectBygjz")), e);
		}
		
	}

	@Override
	public Page<SopProjectBean> querythyyList(ProjectBo query, PageRequest pageable) {
		List<SopProjectBean> contentList = null;
		long total = 0L;
		try {
			contentList = sqlSessionTemplate.selectList( getSqlName("selectByth"),getParams(query, pageable));
			total = CUtils.get().object2Long(sqlSessionTemplate.selectOne(getSqlName("selectBythCount"),getParams(query, pageable)));		
			return new  Page<SopProjectBean>(contentList, pageable, total); 
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName("selectByth")), e);
		}
	}

	@Override
	public Page<SopProjectBean> queryfjList(ProjectBo query, PageRequest pageable) {
		List<SopProjectBean> contentList = null;
		long total = 0L;
		try {
			contentList = sqlSessionTemplate.selectList( getSqlName("selectByfj"),getParams(query, pageable));
			total = CUtils.get().object2Long(sqlSessionTemplate.selectOne(getSqlName("selectByfjCount"),getParams(query, pageable)));		
			return new  Page<SopProjectBean>(contentList, pageable, total); 
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName("selectByfj")), e);
		}
	}*/

	@Override
	public Page<SopProjectBean> queryPageList(ProjectBo query, PageRequest pageable) {
		List<SopProjectBean> contentList = null;
		long total = 0L;
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			contentList = sqlSessionTemplate.selectList( getSqlName("selectPageList"),getParams(query, pageable));
			total = CUtils.get().object2Long(sqlSessionTemplate.selectOne(getSqlName("selectPageListCount"),params));		
			return new  Page<SopProjectBean>(contentList, pageable, total); 
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName("selectPageList")), e);
		}
	}

	/*@Override
	public Long queryCountgjz(ProjectBo query) {
		try{
			Long count = sqlSessionTemplate.selectOne(getSqlName("selectBygjzCount"), query);
		   return count;
		}catch(Exception e){
			throw new DaoException(String.format("根据项目状态gjz统计数据量出错！语句:%s" ,  getSqlName("selectBygjzCount")), e);
		}
	}

	@Override
	public Long queryCountthyy(ProjectBo query) {
		try{
			Long count = sqlSessionTemplate.selectOne(getSqlName("selectBythCount"), query);
		   return count;
		}catch(Exception e){
			throw new DaoException(String.format("根据项目状态th统计数据量出错！语句:%s" ,  getSqlName("selectBythCount")), e);
		}
	}

	@Override
	public Long queryCountfj(ProjectBo query) {
		try{
			Long count = sqlSessionTemplate.selectOne(getSqlName("selectByfjCount"), query);
		   return count;
		}catch(Exception e){
			throw new DaoException(String.format("根据项目状态统计数据量出错！语句:%s" ,  getSqlName("selectByfjCount")), e);
		}
	}*/

	@Override
	public Long queryCount(ProjectBo query) {
		long total = 0L;
		try{
			Map<String, Object> params = BeanUtils.toMap(query);
			total = CUtils.get().object2Long(sqlSessionTemplate.selectOne(getSqlName("selectPageListCount"),params));
		}catch(Exception e){
			throw new DaoException(String.format("根据项目状态统计数据量出错！语句:%s" ,  getSqlName("selectPageListCount")), e);
		}
		return total;
	}

}
