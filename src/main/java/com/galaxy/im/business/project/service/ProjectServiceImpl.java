package com.galaxy.im.business.project.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.GeneralProjecttVO;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.dao.IFlowCommonDao;
import com.galaxy.im.business.project.dao.IProjectDao;
import com.galaxy.im.business.project.dao.ISopProjectDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.Page;
import com.galaxy.im.common.db.PageRequest;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectBean> implements IProjectService{
	private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	@Autowired
	private IProjectDao dao;
	@Autowired
	IFlowCommonDao flowdao;
	@Autowired
	ISopProjectDao sopdao;

	@Override
	protected IBaseDao<ProjectBean, Long> getBaseDao() {
		return dao;
	}

	@Override
	public Map<String,Object> getBaseProjectInfo(Long id) {
		try{
			return dao.getBaseProjectInfo(id);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_ProjectServiceImpl",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<String, Object> getProjectInoIsNull(Long id) {
		try{
			return dao.getProjectInoIsNull(id);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_ProjectServiceImpl",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer projectIsYJZ(Long projectId) {
		try{
			return dao.projectIsYJZ(projectId);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_projectIsYJZ",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer projectIsShow(Long projectId) {
		try{
			return dao.projectIsShow(projectId);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_projectIsShow",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取项目信息
	 */
	@Override
	public List<SopProjectBean> getSopProjectList(SopProjectBean bean) {
		try{
			return dao.getSopProjectList(bean);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_getSopProjectList",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 创建项目
	 */
	@Override
	public long saveProject(SopProjectBean bean) {
		long id = dao.saveProject(bean);
		//创建缺失记录
		SopFileBean f = new SopFileBean();
		f.setProjectId(id);
		f.setCareerLine(CUtils.get().object2Long(bean.getProjectDepartId()));
		f.setFileStatus("fileStatus:1");
		f.setCreatedTime((new Date()).getTime());
		
		//业务尽职调查报告
		f.setFileValid(1);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_6);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_1);
		flowdao.addSopFile(f);
		f.setId(null);
		//人力资源尽职调查报告
		f.setFileValid(0);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_6);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_2);
		flowdao.addSopFile(f);
		f.setId(null);
		//法务尽职调查报告
		f.setFileValid(0);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_6);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_3);
		flowdao.addSopFile(f);
		f.setId(null);
		//财务尽职调查报告
		f.setFileValid(0);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_6);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_4);
		flowdao.addSopFile(f);
		f.setId(null);
		//投资意向书
		f.setFileValid(1);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_5);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_5);
		flowdao.addSopFile(f);
		f.setId(null);
		//投资协议
		f.setFileValid(1);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_8);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_6);
		flowdao.addSopFile(f);
		f.setId(null);
		//股权转让协议
		f.setFileValid(1);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_8);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_7);
		flowdao.addSopFile(f);
		f.setId(null);
		//工商转让凭证
		f.setFileValid(0);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_9);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_8);
		flowdao.addSopFile(f);
		f.setId(null);
		//资金拨付凭证
		f.setFileValid(0);
		f.setProjectProgress(StaticConst.PROJECT_PROGRESS_9);
		f.setFileWorkType(StaticConst.FILE_WORKTYPE_9);
		flowdao.addSopFile(f);
		f.setId(null);
		
		return id;
	}

	/**
	 * 编辑项目
	 */
	@Override
	public int updateProject(SopProjectBean bean) {
		return dao.updateProject(bean);
	}

	/**
	 * 删除项目下的所有投资方
	 */
	@Override
	public int deleteInvestById(SopProjectBean bean) {
		return dao.deleteInvestById(bean);
	}

	/**
	 * 更新项目下的所有投资方的投资形式
	 */
	@Override
	public int updateInvestById(SopProjectBean bean) {
		return dao.updateInvestById(bean);
	}

	/**
	 * 更加项目id查询项目信息
	 */
	@Override
	public SopProjectBean getProjectInfoById(Long id) {
		try{
			return dao.getProjectInfoById(id);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_getProjectInfoById",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 项目是否处于接触访谈阶段
	 */
	@Override
	public int projectIsInterview(Long id) {
		try{
			return dao.projectIsInterview(id);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_projectIsInterview",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 跟进中项目列表
	 */
	@Override
	public GeneralProjecttVO querygjzProjectList(ProjectBo query, PageRequest pageable) {
		Page<SopProjectBean> pageBean =  sopdao.querygjzProjectList(query, pageable);
		GeneralProjecttVO gpbean = new GeneralProjecttVO();
		pageBean.setPageable(null);
		gpbean.setPvPage(pageBean);
		return gpbean;
	}

	/**
	 * 投后运营项目列表
	 */
	@Override
	public GeneralProjecttVO querythyyList(ProjectBo query, PageRequest pageable) {
		Page<SopProjectBean> pageBean =  sopdao.querythyyList(query, pageable);
		GeneralProjecttVO gpbean = new GeneralProjecttVO();
		pageBean.setPageable(null);
		gpbean.setPvPage(pageBean);
		return gpbean;
	}

	/**
	 * 否决项目列表
	 */
	@Override
	public GeneralProjecttVO queryfjList(ProjectBo query, PageRequest pageable) {
		Page<SopProjectBean> pageBean =  sopdao.queryfjList(query, pageable);
		GeneralProjecttVO gpbean = new GeneralProjecttVO();
		pageBean.setPageable(null);
		gpbean.setPvPage(pageBean);
		return gpbean;
	}

	/**
	 * 全部
	 */
	@Override
	public GeneralProjecttVO queryPageList(ProjectBo query, PageRequest pageable) {
		Page<SopProjectBean> pageBean =  sopdao.queryPageList(query, pageable);
		GeneralProjecttVO gpbean = new GeneralProjecttVO();
		pageBean.setPageable(null);
		gpbean.setPvPage(pageBean);
		return gpbean;
	}

	//跟进中查询数目
	public Long queryProjectgjzCount(ProjectBo query) {
		return sopdao.queryCountgjz(query);
	}
	//投后运营查询数目
	public Long queryProjectthyyCount(ProjectBo query) {
		return sopdao.queryCountthyy(query);
	}
	//否决查询数目
	public Long queryProjectfjCount(ProjectBo query) {
		return sopdao.queryCountfj(query);
	}

	
}
