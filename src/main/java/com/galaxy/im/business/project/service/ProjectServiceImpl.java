package com.galaxy.im.business.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.project.GeneralProjecttVO;
import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.ProjectTransfer;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.report.InformationFile;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.talk.ProjectTalkBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.common.dict.dao.IDictDao;
import com.galaxy.im.business.flow.common.dao.IFlowCommonDao;
import com.galaxy.im.business.meeting.dao.IMeetingRecordDao;
import com.galaxy.im.business.meeting.dao.IMeetingSchedulingDao;
import com.galaxy.im.business.project.dao.IProjectDao;
import com.galaxy.im.business.project.dao.ISopProjectDao;
import com.galaxy.im.business.sopfile.dao.ISopFileDao;
import com.galaxy.im.business.soptask.dao.ISopTaskDao;
import com.galaxy.im.business.talk.dao.IProjectTalkDao;
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
	@Autowired
	private IDictDao dictDao;
	@Autowired
	IMeetingRecordDao meetDao;
	@Autowired
	ISopFileDao fileDao;
	@Autowired
	IProjectTalkDao talkDao;
	@Autowired
	ISopTaskDao taskDao;
	@Autowired
	IMeetingSchedulingDao schDao;

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
	 * 项目列表
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
		query.setProjectStatus("projectStatus:0");
		return sopdao.queryCount(query);
	}
	//投后运营查询数目
	public Long queryProjectthyyCount(ProjectBo query) {
		query.setProjectStatus("projectStatus:1");
		return sopdao.queryCount(query);
	}
	//否决查询数目
	public Long queryProjectfjCount(ProjectBo query) {
		query.setProjectStatus("projectStatus:2");
		return sopdao.queryCount(query);
	}
	//项目总个数
	@Override
	public Long queryProjectSumCount(ProjectBo query) {
		query.setProjectStatus(null);
		return sopdao.queryCount(query);
	}

	@Override
	public Map<String, Object> selectBaseProjectInfo(Map<String, Object> paramMap) {
		try{
			Map<String, Object> projectMap = new HashMap<>();
			Map<String,Object> QXinfoMap = new HashMap<>();
			//项目详情 基础信息、融资计划、实际投资
			Map<String,Object> map1 = dao.selectBaseProjectInfo(paramMap);
			if (map1!=null) {
				QXinfoMap.putAll(map1);
			}
			//项目来源
			Map<String,Object> sourceMap = dao.selectProjectSoureInfo(paramMap);
			if (sourceMap!=null && sourceMap.containsKey("projectSoureId")){
				if (!sourceMap.get("projectSoureId").equals("创业者") && !sourceMap.get("projectSoureId").equals("外部独立合伙人") &&
						!sourceMap.get("projectSoureId").equals("自开发") && CUtils.get().object2Integer(sourceMap.get("projectSoureId")) != 2262) {
					String tempId = "";
					projectMap.put("inputId", sourceMap.get("projectSoureId"));
					tempId = dao.findInputTitleId(projectMap);
					paramMap.put("titleId11", tempId);
				}
			}
		
			Map<String,Object> map2 = dao.selectProjectSoureInfo(paramMap);
				if (map2!=null) {
					if (map2 != null && map2.get("projectSoureName")!=null && !map2.get("projectSoureName").equals("")) {
						String value = CUtils.get().object2String(map2.get("projectSoure")) +"-"+ CUtils.get().object2String(map2.get("projectSoureName"));
						map2.put("showProjectSoure", value);
					}else{
						map2.put("showProjectSoure", CUtils.get().object2String(map2.get("projectSoure")));
					}
					QXinfoMap.putAll(map2);
				}
				//项目承揽人
				List<Map<String,Object>> map3 = dao.selectProjectUserInfo(paramMap);
					if (map3.size()>0) {
						StringBuilder sBuilder = new StringBuilder();
						StringBuilder sb = new StringBuilder();
						for(Map<String,Object> maps : map3){
							if(maps!=null && !maps.isEmpty()){
								if (maps.get("projectUserName")!=null && maps.get("projectUser")!=null) {
									projectMap.put("otherProjectUser", CUtils.get().object2String(maps.get("projectUserName")));
								}
								if (maps.get("projectUser")!=null) {
									if (maps.get("projectUser")!=null && maps.get("projectUserId").equals("10072")) {
										sBuilder.append(projectMap.get("otherProjectUser") + "、");
									}else {
										sBuilder.append(maps.get("projectUser") + "、");
									}
								}
								if (maps.get("projectUser")==null && maps.get("projectUserName")!=null) {
									sBuilder.append(maps.get("projectUserName") + "、");
								}
								if(maps.get("projectUserId")!=null){
									sb.append(maps.get("projectUserId") + "、");
								}
							}
						}
						if(sb.length()>0){
							sb.deleteCharAt(sb.length() - 1);
						}
						if(sBuilder.length()>0){
							sBuilder.deleteCharAt(sBuilder.length() - 1);
						}
						projectMap.put("projectUser", sBuilder);
						projectMap.put("projectUserId", sb);
						QXinfoMap.putAll(projectMap);
					}
			return QXinfoMap;
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "_ProjectServiceImpl",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public InformationResult findResultInfoById(Map<String, Object> hashmap) {
		try{
			return dao.findResultInfoById(hashmap);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "findResultInfoById",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int addInformationResult(List<InformationResult> list) {
		try{
			return dao.addInformationResult(list);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "addInformationResult",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateInformationResult(InformationResult result) {
		try{
			return dao.updateInformationResult(result);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "updateInformationResult",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int delProjectUserIds(Map<String, Object> map) {
		try{
			return dao.delProjectUserIds(map);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "delProjectUserIds",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public String findInputTitleId(Map<String, Object> hashmap) {
		try{
			return dao.findInputTitleId(hashmap);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "findInputTitleId",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getMatchingInfo(Map<String, Object> m) {
		try{
			return dictDao.getFinanceStatusList(m);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "getMatchingInfo",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 保存移交，指派的记录信息
	 */
	@Override
	public int saveProjectTransfer(ProjectTransfer bean) {
		try{
			return dao.saveProjectTransfer(bean);
		}catch(Exception e){
			log.error(ProjectServiceImpl.class.getName() + "saveProjectTransfer",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 项目移交，指派同时要修改的内容
	 */
	@Override
	public void receiveProjectTransfer(ProjectTransfer bean) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("projectId",bean.getId());
		//项目修改
		Long projectId = bean.getProjectId();
		SopProjectBean po = new SopProjectBean();
		po.setId(projectId);
		po.setCreateUid(bean.getAfterUid());
		po.setCreateUname(bean.getAfrerUName());
		po.setProjectDepartid(bean.getAfterDepartmentId());
		dao.updateProject(po);
		
		//会议修改
		MeetingRecordBean mr = new MeetingRecordBean();
		mr.setProjectId(projectId);
		mr.setCreateUid(bean.getAfterUid());
		mr.setUpdatedTime(new Date().getTime());
		meetDao.updateCreateUid(mr);
		
		//文件修改
		SopFileBean file = new SopFileBean();
		file.setProjectId(projectId);
		file.setCareerLine(bean.getAfterDepartmentId());
		fileDao.delPostMeetingFile(file);
		
		//访谈记录修改
		ProjectTalkBean ir = new ProjectTalkBean();
		ir.setProjectId(projectId);
		ir.setCreatedId(bean.getAfterUid());
		ir.setUpdatedTime(new Date().getTime());
		talkDao.updateCreateUid(ir);
		
		//交割前事项修改
		InformationListdata ild = new InformationListdata();
		ild.setProjectId(projectId);
		ild.setCreateId(bean.getAfterUid());
		ild.setTitleId(1810l);
		flowdao.updateCreateUid(ild);
		
		//交割前事项文件修改
		InformationFile iF = new InformationFile();
		iF.setProjectId(projectId);
		iF.setCreateId(bean.getAfterUid());
		iF.setTitleId(1810l);
		flowdao.updateCreateUid(iF);
	}

	/**
	 * 项目删除同时修改的内容
	 * @param bean
	 */
	@Override
	public void receiveProjectDel(SopProjectBean bean) {
		//会议修改为无效
		MeetingRecordBean mr = new MeetingRecordBean();
		mr.setProjectId(bean.getId());
		mr.setMeetValid(1);
		mr.setUpdatedTime(new Date().getTime());
		meetDao.updateCreateUid(mr);
		
		//访谈记录修改
		ProjectTalkBean ir = new ProjectTalkBean();
		ir.setProjectId(bean.getId());
		ir.setInterviewValid(1);
		ir.setUpdatedTime(new Date().getTime());
		talkDao.updateCreateUid(ir);
		
		//文件修改
		SopFileBean file = new SopFileBean();
		file.setProjectId(bean.getId());
		file.setFileValid(3);
		fileDao.delPostMeetingFile(file);
		
		//代办任务修改
		SopTask task = new SopTask();
		task.setProjectId(bean.getId());
		task.setIsDelete(1);
		taskDao.applyTask(task);
		
		//会议排期修改
		MeetingScheduling sch = new MeetingScheduling();
		sch.setProjectId(bean.getId());
		sch.setIsDelete(1);
		sch.setUpdatedTime(new Date().getTime());
		schDao.updateMeetingScheduling(sch);
	}
	
}
