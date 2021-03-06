package com.galaxy.im.business.project.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.Config;
import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.project.GeneralProjecttVO;
import com.galaxy.im.bean.project.InformationListdata;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectBeanVo;
import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.ProjectTransfer;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.business.common.config.service.ConfigService;
import com.galaxy.im.business.common.dict.service.IDictService;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
import com.galaxy.im.business.project.service.IFinanceHistoryService;
import com.galaxy.im.business.project.service.IProjectEquitiesService;
import com.galaxy.im.business.project.service.IProjectService;
import com.galaxy.im.business.rili.service.IScheduleService;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.RedisCacheImpl;
import com.galaxy.im.common.db.Page;
import com.galaxy.im.common.db.PageRequest;
import com.galaxy.im.common.enums.EnumUtil;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;

@Controller
@RequestMapping("/project")
public class ProjectController {
	private Logger log = LoggerFactory.getLogger(ProjectController.class);
	@Autowired
	IProjectService service;
	@Autowired
	ConfigService configService;
	
	@Autowired
	IFinanceHistoryService fsService;
	
	@Autowired
	private IFlowCommonService fcService;
	
	@Autowired
	private IDictService dictService;
	
	@Autowired
	IScheduleService schService;
	
	@Autowired
	IScheduleMessageService messageService;
	
	@Autowired 
	private IProjectEquitiesService equitilesService;
	
	
	/**
	 * 拜访中的关联项目名称
	 * @param request
	 * @param response
	 * @param project
	 * @return
	 */
	@RequestMapping("getProjectList")
	@ResponseBody
	public Object getProjectList(HttpServletRequest request,HttpServletResponse response,@RequestBody ProjectBeanVo project){
		ResultBean<ProjectBean> resultBean = new ResultBean<ProjectBean>();
		resultBean.setStatus("error");
		try{
			SessionBean bean = CUtils.get().getBeanBySession(request);
			if(project.getpName()!=null){
				project.setCreatedId(null);
			}else{
				project.setCreatedId(bean.getGuserid());
			}
			project.setLoginUserId(bean.getGuserid());
			//分页查询
			Page<ProjectBean> pageProject = service.queryPageList(project,
					new PageRequest(project.getPageNum(),
							project.getPageSize(), 
							Direction.fromString(project.getDirection()),
							project.getProperty()));
			//查询结果放在List<ProjectBean>
			
			List<ProjectBean> projectList=pageProject.getContent();
			//页面
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageNum", pageProject.getPageable().getPageNumber());
			map.put("pageSize", pageProject.getPageable().getPageSize());
			map.put("total", pageProject.getTotal());
			
			resultBean.setStatus("OK");
			resultBean.setEntityList(projectList);
			resultBean.setMap(map);
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "_getProjectList",e);
		}
		return resultBean;
	}
	
	/**
	 * 获取项目基础信息接口
	 * @param id
	 * @return
	 * @author liyanqiao
	 */
	@RequestMapping("getBaseProjectInfo")
	@ResponseBody
	public Object getBaseProjectInfo(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			Long projectId = CUtils.get().object2Long(paramMap.get("id"));
			
			paramMap.put("titleId1", 1916);
			paramMap.put("titleId2", 1917);
			paramMap.put("titleId3", 1943);
			paramMap.put("titleId4", 3004);
			paramMap.put("titleId5", 3010);
			paramMap.put("titleId6", 3011);
			paramMap.put("titleId7", 3012);
			paramMap.put("titleId8", 1108);
			paramMap.put("titleId9", 1120);
			paramMap.put("titleId10", 1118);
			paramMap.put("projectId", projectId);
			//基础信息(数据来源项目表)
			Map<String,Object> infoMap = service.getBaseProjectInfo(projectId);
			
			if(infoMap!=null && !infoMap.isEmpty()){
				//基础信息(数据来源 全息报告)
				Map<String,Object> QXinfoMap = service.selectBaseProjectInfo(paramMap);
				if( QXinfoMap!=null && !QXinfoMap.isEmpty()){
					infoMap.putAll(QXinfoMap);
				}
				infoMap.put("projectYjz", service.projectIsYJZ(projectId));		//判断该项目是否处于移交中
				resultMap.put("infoMap", infoMap);
					
				//融资历史-最新一条
				paramMap.put("isOne", "true");
				List<Map<String,Object>> historyMap = fsService.getFinanceHistory(paramMap);
				if(historyMap!=null && historyMap.size()>0){
					resultMap.put("historyMap", historyMap.get(0));
				}
				
				//用户画像等理否为空
				Map<String,Object> nullMap = service.getProjectInoIsNull(projectId);
				if(nullMap!=null && !nullMap.isEmpty()){
					resultMap.put("nullMap", nullMap);
				}
			}
			
			if(resultMap!=null &&!resultMap.isEmpty()){
				result.setEntity(resultMap);
				result.setStatus("OK");
				result.setFlag(0);
			}else{
				result.setStatus("OK");
				result.setFlag(1);
			}
			
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "getBaseProjectInfo",e);
		}
		return result;
	}
	
	@RequestMapping("projectIsShow")
	@ResponseBody
	public Object isYJZ(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<>();
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(paramMap!=null && paramMap.containsKey("id")){
				Long id = CUtils.get().object2Long(paramMap.get("id"), 0L);
				if(id!=0){
					int success = service.projectIsShow(id);
					int res = service.projectIsInterview(id);
					map.put("isTransfer", success);
					map.put("isInterview", res);
					result.setMap(map);
					result.setStatus("OK");
				}
			}
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "isYJZ",e);
		}
		return result;
	}
	
	/**
	 * 添加/编辑项目
	 * @param 
	 * @return
	 * @author liuli
	 */
	@RequestMapping("addProject")
	@ResponseBody
	public Object addProject(HttpServletRequest request,HttpServletResponse response,@RequestBody SopProjectBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			long deptId=0l;
			String userName="";
			String arePersion="";
			Map<String,Object> map =new HashMap<String,Object>();
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			Long userId = sessionBean.getGuserid();
			
			//通过用户id获取一些信息
			List<Map<String, Object>> list = fcService.getDeptId(userId,request,response);
			if(list!=null){
				for(Map<String, Object> vMap:list){
					deptId= CUtils.get().object2Long( vMap.get("deptId"));
					userName=CUtils.get().object2String(vMap.get("userName"));
				}
			}
			
			//验证项目名是否重复
			List<SopProjectBean> projectList = service.getSopProjectList(bean);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			if(bean!=null){
				//新增和编辑的公用部分
				if (bean.getProjectValuations() == null) {
					if (bean.getProjectShareRatio() != null
							&& bean.getProjectShareRatio() > 0
							&& bean.getProjectContribution() != null
							&& bean.getProjectContribution() > 0) {
						bean.setProjectValuations(bean.getProjectContribution() * 100 / bean.getProjectShareRatio());
					}
				}
				
				if(bean.getId()!=null && bean.getId()!=0){
					//编辑：去掉自己的重复
					if(null != projectList && projectList.size()>0){
						for(int i=0;i<projectList.size();i++){
							SopProjectBean p =projectList.get(i);
							if(String.valueOf(bean.getId()).equals(String.valueOf(p.getId()))){
								projectList.remove(p);
							}
						}
					}
					
					// 执行转换
					bean.getProjectContribution();
					bean.getProjectValuations();
					bean.getCurrencyUnit();
					bean.getProjectShareRatio();
					
					if (null != projectList && projectList.size() > 0) {
						SopProjectBean en = projectList.get(0);
						arePersion="您输入的项目与【"+en.getProjectName()+"】项目重复，不能保存。&&项目承做人：";
						paramMap.put("projectId", en.getId());
						paramMap.put("areflag", 0);
						List<Map<String,Object>> map4 = service.getProjectArePeople(paramMap);
						if(map4!=null){
							if(map4.size()==1 && map4.get(0).get("areFlag").equals("0")){
								String ss =map4.get(0).get("arePeople")+"|"+map4.get(0).get("deptName");
								arePersion=arePersion+ss;
							}
						}
						resultBean.setMessage(arePersion);
						return resultBean;
					}
					SopProjectBean p = service.getProjectInfoById(bean.getId());
					if (p == null) {
						resultBean.setMessage("未找到相应的项目信息!");
						return resultBean;
					}
					// 项目创建者用户ID与当前登录人ID是否一样
					if (userId.longValue() != p.getCreateUid().longValue()) {
						resultBean.setMessage("没有权限修改项目信息!");
						return resultBean;
					}
					if (bean.getServiceCharge() == null) {
						bean.setServiceCharge(0.0000);
					}
					if(null!=bean.getIndustryOwn() && bean.getIndustryOwn().longValue()==0){
						bean.setIndustryOwn(null);
					}
					bean.setUpdatedTime(System.currentTimeMillis());
					int num = service.updateProject(bean);
					//全息报告result表数据更新
					editInformationResult(bean,userId,userName);
					if (num > 0) {
						//0：删除项目下所有投资方，其他：修改项目所有投资方信息
						@SuppressWarnings("unused")
						int res=0;
						if(bean.getFinanceMode()!=null){
							if(bean.getFinanceMode().equals("financeMode:0")){
								res = service.deleteInvestById(bean);
							}else{
								res = service.updateInvestById(bean);
							}
						}
						resultBean.setStatus("OK");
						resultBean.setMessage("项目修改成功!");
					}
				}else{
					//验证请求信息
					if (bean == null || bean.getProjectName() == null
							|| "".equals(bean.getProjectName().trim())
							|| bean.getProjectType() == null
							|| "".equals(bean.getProjectType().trim())
							|| bean.getCreateDate() == null
							|| "".equals(bean.getCreateDate().trim())
							|| bean.getIndustryOwn() == null) {
						resultBean.setMessage("必要的参数丢失!");
						return resultBean;
					}
					//新增
					if (null != projectList && projectList.size() > 0) {
						SopProjectBean en = projectList.get(0);
						arePersion="您输入的项目与【"+en.getProjectName()+"】项目重复，不能保存。&&项目承做人：";
						paramMap.put("projectId", en.getId());
						paramMap.put("areflag", 0);
						List<Map<String,Object>> map4 = service.getProjectArePeople(paramMap);
						if(map4!=null){
							if(map4.size()==1 && map4.get(0).get("areFlag").equals("0")){
								String ss =map4.get(0).get("arePeople")+"|"+map4.get(0).get("deptName");
								arePersion=arePersion+ss;
							}
						}
						resultBean.setMessage(arePersion);
						return resultBean;
					}
					//创建项目编码
					Config config = configService.createCode();
					NumberFormat nf = NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					nf.setMaximumIntegerDigits(6);
					nf.setMinimumIntegerDigits(6);
					if (deptId != 0) {
						int code = EnumUtil.getCodeByCareerline(deptId);
						String projectCode = String.valueOf(code) + nf.format(Integer.parseInt(config.getValue()));
						bean.setProjectCode(String.valueOf(projectCode));
						bean.setCurrencyUnit(0);
						bean.setStockTransfer(0);
						bean.setCreateUid(userId);
						bean.setCreateUname(userName);
						bean.setProjectDepartId(deptId);
						bean.setProjectProgress(StaticConst.PROJECT_PROGRESS_1);
						bean.setProgressHistory(StaticConst.PROJECT_PROGRESS_1);
						bean.setProjectStatus(StaticConst.PROJECT_STATUS_0);
						bean.setUpdatedTime(new Date().getTime());
						bean.setProjectTime(new Date().getTime());
						bean.setIsDelete(0);
						bean.setCreatedTime(DateUtil.convertStringToDate(bean.getCreateDate().trim(), "yyyy-MM-dd").getTime());
						long id = service.saveProject(bean);
						//新建项目存入全息报告中的信息
						editInformationResult(bean,userId,userName);
						if (id > 0) {
							map.put("id", id);
							resultBean.setStatus("OK");
							resultBean.setMessage("项目添加成功!");
							resultBean.setMap(map);	
							//生成主承做人
							InformationListdata data = new InformationListdata();
							data.setProjectId(id);
							data.setTitleId(1103L);
							data.setField1(CUtils.get().object2String(userId));
							data.setField2("100");
							data.setField5("0");
							data.setIsValid(0);
							data.setCreatedId(userId);
							data.setCreatedTime(new Date().getTime());
							data.setUpdatedId(userId);
							data.setUpdatedTime(new Date().getTime());
							equitilesService.saveInfomationListData(data);
						}
					}
				}
			}
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "_addProject",e);
		}
		return resultBean;
	}
	
	//全息报告数据处理
	void editInformationResult(SopProjectBean bean,Long userId, String userName){
		List<InformationResult> list = new ArrayList<>();
		Map<String, Object> hashmap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", bean.getId());
		hashmap.put("projectId", bean.getId());
		InformationResult result = null;
		//融资金额
		if (bean.getFormatContribution()!=null && !bean.getFormatContribution().equals("")) {
			hashmap.put("titleId", 1916);
			result = service.findResultInfoById(hashmap);
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentDescribe1(CUtils.get().object2String(bean.getFormatContribution()));
				service.updateInformationResult(result);
			}else{
					result = new InformationResult();
					result.setTitleId("1916");
					result.setCreateId(CUtils.get().object2String(userId));
					result.setCreatedTime(new Date().getTime());
					result.setProjectId(CUtils.get().object2String(bean.getId()));
					result.setContentDescribe1(CUtils.get().object2String(bean.getFormatContribution()));
					list.add(result);
			}
		} 
		//融资轮次
		if (bean.getFinanceStatus()!=null && !bean.getFinanceStatus().equals("")) {
			hashmap.put("titleId", 1108);
			result = service.findResultInfoById(hashmap);
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentChoose(CUtils.get().object2String(bean.getFinanceStatus()));
				service.updateInformationResult(result);
			}else{
				result = new InformationResult();
				result.setTitleId("1108");
				result.setCreateId(CUtils.get().object2String(userId));
				result.setCreatedTime(new Date().getTime());
				result.setProjectId(CUtils.get().object2String(bean.getId()));
				result.setContentChoose(CUtils.get().object2String(bean.getFinanceStatus()));
				list.add(result);
			}
		}
		//出让股份
		if (bean.getFormatShareRatio()!=null && !bean.getFormatShareRatio().equals("")) {
			hashmap.put("titleId", 1917);
			result = service.findResultInfoById(hashmap);
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentDescribe1(CUtils.get().object2String(bean.getFormatShareRatio()));
				service.updateInformationResult(result);
			}else{
					result = new InformationResult();
					result.setTitleId("1917");
					result.setCreateId(CUtils.get().object2String(userId));
					result.setCreatedTime(new Date().getTime());
					result.setProjectId(CUtils.get().object2String(bean.getId()));
					result.setContentDescribe1(CUtils.get().object2String(bean.getFormatShareRatio()));
					list.add(result);
			}
		}
		//融资计划项目估值
		if (bean.getFormatValuations()!=null && !bean.getFormatValuations().equals("")) {
			hashmap.put("titleId", 1943);
			result = service.findResultInfoById(hashmap);
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentDescribe1(bean.getFormatValuations());
				service.updateInformationResult(result);
			}else{
					result = new InformationResult();
					result.setTitleId("1943");
					result.setCreateId(CUtils.get().object2String(userId));
					result.setCreatedTime(new Date().getTime());
					result.setProjectId(CUtils.get().object2String(bean.getId()));
					result.setContentDescribe1(bean.getFormatValuations());
					list.add(result);
			}
		}
		//投资金额
		if (bean.getFormatFinalContribution()!=null && !bean.getFormatFinalContribution().equals("")) {
			hashmap.put("titleId", 3004);
			result = service.findResultInfoById(hashmap);
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentDescribe1(CUtils.get().object2String(bean.getFormatFinalContribution()));
				service.updateInformationResult(result);
			}else{
					result = new InformationResult();
					result.setTitleId("3004");
					result.setCreateId(CUtils.get().object2String(userId));
					result.setCreatedTime(new Date().getTime());
					result.setProjectId(CUtils.get().object2String(bean.getId()));
					result.setContentDescribe1(CUtils.get().object2String(bean.getFormatFinalContribution()));
					list.add(result);
			}
		}
		//股权占比
		if (bean.getFormatFinalShareRatio()!=null && !bean.getFormatFinalShareRatio().equals("")) {
			hashmap.put("titleId", 3010);
			result = service.findResultInfoById(hashmap);
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentDescribe1(CUtils.get().object2String(bean.getFormatFinalShareRatio()));
				service.updateInformationResult(result);
			}else{
					result = new InformationResult();
					result.setTitleId("3010");
					result.setCreateId(CUtils.get().object2String(userId));
					result.setCreatedTime(new Date().getTime());
					result.setProjectId(CUtils.get().object2String(bean.getId()));
					result.setContentDescribe1(CUtils.get().object2String(bean.getFormatFinalShareRatio()));
					list.add(result);
			}
		}
		//加速服务费占比
		if (bean.getServiceCharge()!=null && !CUtils.get().object2String(bean.getServiceCharge()).equals("0.0")) {
			hashmap.put("titleId", 3011);
			result = service.findResultInfoById(hashmap);
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentDescribe1(CUtils.get().object2String(bean.getServiceCharge()));
				service.updateInformationResult(result);
			}else{
					result = new InformationResult();
					result.setTitleId("3011");
					result.setCreateId(CUtils.get().object2String(userId));
					result.setCreatedTime(new Date().getTime());
					result.setProjectId(CUtils.get().object2String(bean.getId()));
					result.setContentDescribe1(CUtils.get().object2String(bean.getServiceCharge()));
					list.add(result);
			}
		}
		//实际项目估值
		if (bean.getFormatFinalValuations()!=null && !bean.getFormatFinalValuations().equals("")) {
			hashmap.put("titleId", 3012);
			result = service.findResultInfoById(hashmap);
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentDescribe1(bean.getFormatFinalValuations());
				service.updateInformationResult(result);
			}else{
					result = new InformationResult();
					result.setTitleId("3012");
					result.setCreateId(CUtils.get().object2String(userId));
					result.setCreatedTime(new Date().getTime());
					result.setProjectId(CUtils.get().object2String(bean.getId()));
					result.setContentDescribe1(bean.getFormatFinalValuations());
					list.add(result);
			}
		}
		//项目来源
		if (bean.getProjectSource()!=null && !bean.getProjectSource().equals("")) {
			hashmap.put("titleId", 1120);
			String tempId="";
			result = service.findResultInfoById(hashmap);
			//新选择的项目来源获取关联名称的titleId
			if(bean.getProjectSource()!=null){
				hashmap.put("inputId", bean.getProjectSource());
				tempId = service.findInputTitleId(hashmap);
			}
			if (result!=null) {
				result.setUpdatedTime(new Date().getTime());
				result.setContentChoose(bean.getProjectSource());
				service.updateInformationResult(result);
				if (tempId!=null) {
						InformationResult r = new InformationResult();
						//查询有没有
						hashmap.put("titleId", tempId);
						r = service.findResultInfoById(hashmap);
						if (r!=null) {
							r.setUpdateId(CUtils.get().object2String(userId));
							r.setUpdatedTime(new Date().getTime());
							if (bean.getProjectSourceName()==null || bean.getProjectSourceName().equals("")) {
								r.setContentDescribe1("");
							}else{
								r.setContentDescribe1(bean.getProjectSourceName());
							}
							service.updateInformationResult(r);
						}else{
							r = new InformationResult();
							r.setTitleId(tempId);
							r.setCreateId(CUtils.get().object2String(userId));
							r.setCreatedTime(new Date().getTime());
							r.setProjectId(CUtils.get().object2String(bean.getId()));
							if (bean.getProjectSourceName()==null || bean.getProjectSourceName().equals("")) {
								r.setContentDescribe1("");
							}else{
								r.setContentDescribe1(bean.getProjectSourceName());
							}
							list.add(r);
						}
				}
			}else{
					//项目来源
					result = new InformationResult();
					result.setTitleId("1120");
					result.setCreateId(CUtils.get().object2String(userId));
					result.setCreatedTime(new Date().getTime());
					result.setProjectId(CUtils.get().object2String(bean.getId()));
					result.setContentChoose(bean.getProjectSource());
					if(bean.getProjectSourceName()!=null && !bean.getProjectSourceName().equals("")){
						InformationResult r = new InformationResult();
						r.setTitleId(CUtils.get().object2String(tempId));
						r.setCreateId(CUtils.get().object2String(userId));
						r.setCreatedTime(new Date().getTime());
						r.setProjectId(CUtils.get().object2String(bean.getId()));
						r.setContentDescribe1(bean.getProjectSourceName());
						list.add(r);
					}
					list.add(result);
			}
			//添加项目承揽人之前，先清空所有的
			map.put("titleId", 1118);
			@SuppressWarnings("unused")
			int del = service.delProjectUserIds(map);
			if(bean.getProjectUserIds()!=null && bean.getProjectUserIds().size()>0){
				//项目承揽人
				List<String> ids = bean.getProjectUserIds();
				for(int i=0;i<ids.size();i++){
					InformationResult r = new InformationResult();
					r.setTitleId("1118");
					r.setCreateId(CUtils.get().object2String(userId));
					r.setCreatedTime(new Date().getTime());
					r.setProjectId(CUtils.get().object2String(bean.getId()));
					r.setContentChoose(CUtils.get().object2String(ids.get(i)));
					if(CUtils.get().object2String(ids.get(i)).equals("非投资线员工") && bean.getProjectUserName()!=null && !bean.getProjectUserName().equals("")){
						r.setContentDescribe1(bean.getProjectUserName());
					}
					list.add(r);
				}
			}else{
				//项目来源不是FA的默认创建一条承揽人信息
				if(!bean.getProjectSource().equals("2257")){
					InformationResult r = new InformationResult();
					r.setTitleId("1118");
					r.setCreateId(CUtils.get().object2String(userId));
					r.setCreatedTime(new Date().getTime());
					r.setProjectId(CUtils.get().object2String(bean.getId()));
					
					r.setContentChoose(CUtils.get().object2String(userId));
					r.setContentDescribe1(userName);
					
					list.add(r);
				}
			}
		}
		if (list.size()>0) {
			service.addInformationResult(list);
		}
	}
	
	/**
	 * 项目列表
	 * @param 
	 * @return
	 * @author liuli
	 */
	@RequestMapping("selectProjectList")
	@ResponseBody
	public Object selectProjectList(HttpServletRequest request,HttpServletResponse response,@RequestBody ProjectBo projectBo){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		GeneralProjecttVO genProjectBean = new GeneralProjecttVO();
		Page<SopProjectBean> page = null;
		List<String> createUidList = projectBo.getCreateUidList();
		try {
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if(sessionBean==null){
				resultBean.setMessage("User用户信息在Session中不存在，无法执行项目列表查询！");
				return resultBean;
			}
			
			//排序字段
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(new Order(Direction.DESC, "updated_time"));
			orderList.add(new Order(Direction.DESC, "created_time"));			
			Sort sort = new Sort(orderList);
			
			if(projectBo.getSflag()!=null && projectBo.getSflag()==1){
				List<String> projectIdList = service.getProjectIdArePeople(projectBo);
				if(projectIdList!=null && projectIdList.size()>0){
					projectBo.setProjectIdList(projectIdList);
					projectBo.setCreateUidList(null);
					page = service.queryPageList(projectBo,  new PageRequest(projectBo.getPageNum(), projectBo.getPageSize(),sort));
				}
			}else{
				page = service.queryPageList(projectBo,  new PageRequest(projectBo.getPageNum(), projectBo.getPageSize(),sort));
			}
			
			Page<SopProjectBean> pvPage = contentDeal(page,request,response);
			genProjectBean.setPvPage(pvPage);
			
			//负责
			projectBo.setCreateUidList(createUidList);
			projectBo.setProjectIdList(null);
			Long myCount = service.queryMyProjectCount(projectBo);
			genProjectBean.setMyCount(myCount);
			//协作
			Long coopCount = service.queryCoopProjectCount(projectBo);
			genProjectBean.setCoopCount(coopCount);
			 
			resultBean.setStatus("OK");
			resultBean.setMap(BeanUtils.toMap(genProjectBean));
		} catch (Exception e) {
			log.error(ProjectController.class.getName() + "selectProjectList",e);
		}
		return resultBean;
	}
	
	//内容处理
	private Page<SopProjectBean> contentDeal(Page<SopProjectBean> page, HttpServletRequest request, HttpServletResponse response) {
		String deptName="";
		if(page!=null){
			List<SopProjectBean> dataList = page.getContent();
			for(int i=0;i<dataList.size();i++){
				@SuppressWarnings("unchecked")
				Map<String,Object> map =(Map<String, Object>) dataList.get(i);
				//项目事业线
				if(StringUtils.isNotBlank(CUtils.get().object2String(map.get("projectDepartId")))){
					//通过部门id获取部门名称
					List<Map<String, Object>> list = fcService.getDeptNameByDeptId(CUtils.get().object2Long(map.get("projectDepartId")),request,response);
					if(list!=null){
						for(Map<String, Object> vMap:list){
							deptName=CUtils.get().object2String(vMap.get("deptName"));
						}
					}
					map.put("projectCareerline", deptName);
				}
				
				// 标识 项目不处于移交中
				if(StringUtils.isNotBlank(CUtils.get().object2String(map.get("id")))){
					map.put("projectYjz", service.projectIsYJZ(CUtils.get().object2Long(map.get("id"))));
				}
				// 行业归属
				if(map.containsKey("industryOwn")){
					if(StringUtils.isNotBlank(CUtils.get().object2String(map.containsKey("industryOwn")))){
						Map<String,Object> paramMap = new HashMap<String,Object>();
						paramMap.put("parentCode", "industryOwn");
						List<Map<String, Object>> entityMap = dictService.getDictionaryList(paramMap);
						for(Map<String, Object> mapL : entityMap){
							if(mapL.containsKey("code") && mapL.get("code").equals(CUtils.get().object2Long(map.get("industryOwn")))){
								//行业归属名称
								map.put("industry", CUtils.get().object2String(mapL.get("name")));
							}else{
								map.put("industry", "");
							}
						}
					}
				}else{
					map.put("industry", "");
				}
				//项目类型名称
				if(StringUtils.isNotBlank(CUtils.get().object2String(map.get("projectType")))){
					map.put("projectTypeName", getNameByCode(CUtils.get().object2String(map.get("projectType")),"projectType"));
				}
				//项目进度名称
				if(StringUtils.isNotBlank(CUtils.get().object2String(map.get("projectProgress")))){
					map.put("projectProgressName", getNameByCode(CUtils.get().object2String(map.get("projectProgress")),"projectProgress"));
				}
				//融资状态名称
				if(StringUtils.isNotBlank(CUtils.get().object2String(map.get("financeStatus")))){
					map.put("financeStatusName", CUtils.get().object2String(map.get("financeStatus")));
				}
				//项目状态编码
				if(StringUtils.isNotBlank(CUtils.get().object2String(map.get("projectStatus")))){
					map.put("projectStatusName", getNameByCode(CUtils.get().object2String(map.get("projectStatus")),"projectStatus"));
				}
			}
			page.setContent(dataList);
		}
		return page;
	}

	//根据code获取name
	private String getNameByCode(String type,String parentCode) {
		String code="";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parentCode", parentCode);
		List<Map<String, Object>> entityMap = dictService.getDictionaryList(paramMap);
		for(Map<String, Object> map : entityMap){
			if(map.containsKey("code") && map.get("code").equals(CUtils.get().object2String(type))){
				code =CUtils.get().object2String(map.get("name"));
			}
		}
		return code;
	}
	
	/**
	 * 项目总个数，跟进中，投后运营以及日程消息未读个数
	 */
	@RequestMapping("showProjectCount")
	@ResponseBody
	public Object showProjectCount(HttpServletRequest request,HttpServletResponse response,@RequestBody ProjectBo projectBo){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if(sessionBean==null){
				resultBean.setMessage("User用户信息在Session中不存在，无法执行项目列表查询！");
				return resultBean;
			}
			//获取用户角色code
			List<String> roleCodeList = fcService.selectRoleCodeByUserId(sessionBean.getGuserid(), request, response);
			if(roleCodeList==null || roleCodeList.size()==0){
				resultBean.setMessage("当前用户未配置任何角色，将不执行项目统计功能！");
				return resultBean;
			}
			//投资经理
			if(roleCodeList.contains(StaticConst.TZJL)&&(projectBo.getProjectDepartid()==null)&&(projectBo.getCreateUid()==null)&&(projectBo.getQuanbu()==null)&&(projectBo.getDeptIdList()==null)){//投资经理
				projectBo.setCreateUid(sessionBean.getGuserid()); //项目创建者
			}
			
			//个数
			Long gjzNum = service.queryProjectgjzCount(projectBo);
			Long thyyNum = service.queryProjectthyyCount(projectBo);
			Long sumNum = service.queryProjectSumCount(projectBo);
			//日程消息未读个数
			Long scheduleNum = schService.queryProjectScheduleCount(projectBo.getuId());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("sumNum", sumNum);
			map.put("gjzNum", gjzNum);
			map.put("thyyNum", thyyNum);
			map.put("scheduleNum", scheduleNum);
			resultBean.setMap(map);
			resultBean.setStatus("OK");
			
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "showProjectCount",e);
		}
		return resultBean;
	}
	
	
	/**
	 * 删除项目
	 * @param paramString
	 * @return
	 */
	@RequestMapping("delProject")
	@ResponseBody
	public Object delProject(HttpServletRequest request,@RequestBody SopProjectBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			//获取登录用户信息
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if (sessionBean==null) {
				resultBean.setMessage("获取用户信息失败");
			}
			@SuppressWarnings("unchecked")
			RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
			Map<String, Object> user = BeanUtils.toMap(cache.get(sessionBean.getSessionid()));
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("projectId",bean.getId());
			SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
			
			//删除项目
			bean.setIsDelete(1);
			int result = service.updateProject(bean);
			if(result>0){
				//同时删除
				service.receiveProjectDel(bean);
				resultBean.setStatus("OK");
				resultBean.setMessage("删除项目成功");
			}
			if(!(sessionBean.getGuserid().longValue()==sopBean.getCreateUid().longValue())){
				//发消息
				List<Map<String, Object>> projects=new ArrayList<Map<String, Object>>();
				paramMap.put("projectName", sopBean.getProjectName());
				projects.add(paramMap);
				sopBean.setProjects(projects);
				sopBean.setDeleteReason(bean.getDeleteReason());
				sopBean.setMessageType("1.1.1");
				sopBean.setUserId(sessionBean.getGuserid());
				sopBean.setUserName(CUtils.get().object2String(user.get("realName")));
				sopBean.setUserDeptName(CUtils.get().object2String(user.get("departmentName")));
				messageService.operateMessageSopTaskInfo(sopBean,sopBean.getMessageType());
			}
			
			//记录操作日志
			ControllerUtils.setRequestParamsForMessageTip(request, null, sopBean,"",null);
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "delProject",e);
		}
		return resultBean;
	}
	
	/**
	 * 移交，指派
	 * @param request
	 * @param bean
	 * @return
	 */
	@RequestMapping("projectTransfer")
	@ResponseBody
	public Object projectTransfer(HttpServletRequest request,@RequestBody ProjectTransfer bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			//获取登录用户信息
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if (sessionBean==null) {
				resultBean.setMessage("获取用户信息失败");
			}
			@SuppressWarnings("unchecked")
			RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
			Map<String, Object> user = BeanUtils.toMap(cache.get(sessionBean.getSessionid()));
			
			List<Map<String, Object>> projects = bean.getProjects();
			if(projects!=null){
				if(bean.getFlag()==1){
					//移交
					for(Map<String, Object> map : projects){
						map.put("isDelete", 0);
						SopProjectBean sopBean = fcService.getSopProjectInfo(map);
						if(sopBean!=null){
							if (CUtils.get().object2String(sessionBean.getGuserid()).equals(CUtils.get().object2String(bean.getAfterUid())))
							{
								resultBean.setMessage("不能移交给本人");
								return resultBean;
							}
						}else{
							resultBean.setMessage("项目已被删除");
							return resultBean;
						}
					}
					for(Map<String, Object> map : projects){
						SopProjectBean sopBean = fcService.getSopProjectInfo(map);
						if(sopBean!=null){
							bean.setProjectId(CUtils.get().object2Long(map.get("projectId")));
							bean.setBeforeUid(sopBean.getCreateUid());
							bean.setBeforeDepartmentId(sopBean.getProjectDepartId());
							bean.setRecordStatus(2);
							bean.setCreatedTime(new Date().getTime());
							bean.setOperateType("0");
							bean.setOperateId(sessionBean.getGuserid());
							@SuppressWarnings("unused")
							int result = service.saveProjectTransfer(bean);
							service.receiveProjectTransfer(bean);
						}
					}
					operateMethod(bean,1,user,sessionBean,request);
				}else if(bean.getFlag()==2){
					//指派
					for(Map<String, Object> map : projects){
						map.put("isDelete", 0);
						SopProjectBean sopBean = fcService.getSopProjectInfo(map);
						if(sopBean!=null){
							if(CUtils.get().object2String(sopBean.getCreateUid()).equals(CUtils.get().object2String(bean.getAfterUid()))){
								resultBean.setMessage("不能指派给项目负责人");
								return resultBean;
							}
						}else{
							resultBean.setMessage("项目已被删除");
							return resultBean;
						}
					}
					for(Map<String, Object> map : projects){
						SopProjectBean sopBean = fcService.getSopProjectInfo(map);
						if(sopBean!=null){
							bean.setProjectId(CUtils.get().object2Long(map.get("projectId")));
							bean.setBeforeUid(sopBean.getCreateUid());
							bean.setBeforeDepartmentId(sopBean.getProjectDepartId());
							bean.setRecordStatus(2);
							bean.setCreatedTime(new Date().getTime());
							bean.setOperateType("1");
							bean.setOperateId(sessionBean.getGuserid());
							@SuppressWarnings("unused")
							int result = service.saveProjectTransfer(bean);
							service.receiveProjectTransfer(bean);
						}
					}
					operateMethod(bean,2,user,sessionBean,request);
				}
				resultBean.setStatus("OK");
			}
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "projectTransfer",e);
		}
		return resultBean;
	}

	//移交，指派记录操作日志，推送消息
	private void operateMethod(ProjectTransfer bean,int flag, Map<String, Object> user, SessionBean sessionBean, HttpServletRequest request) {
		
		//发消息
		SopProjectBean sop = new SopProjectBean();
		UrlNumber uNum = null;
		if(flag==1){
			sop.setMessageType("1.1.2");
			uNum = UrlNumber.one;
		}else if(flag==2){
			sop.setMessageType("1.1.3");
			uNum = UrlNumber.two;
		}
		sop.setCreateUid(bean.getAfterUid());
		sop.setCreateUname(bean.getAfrerUName());
		sop.setProjects(bean.getProjects());
		sop.setUserId(sessionBean.getGuserid());
		sop.setUserName(CUtils.get().object2String(user.get("realName")));
		sop.setUserDeptName(CUtils.get().object2String(user.get("departmentName")));
		messageService.operateMessageSopTaskInfo(sop,sop.getMessageType());
		
		
		//记录操作日志，项目名称，项目id，项目阶段
		List<Map<String, Object>> mapList= new ArrayList<Map<String, Object>>();
		
		for(Map<String, Object> m:bean.getProjects()){
			SopProjectBean sopBean = fcService.getSopProjectInfo(m);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectId", sopBean.getId());
			map.put("projectName", sopBean.getProjectName());
			map.put("projectProgressName", sopBean.getProjectProgressName());
			map.put("recordId", sopBean.getId());
			map.put("nums", uNum);
			map.put("reason", bean.getTransferReason());
			mapList.add(map);
		}
		ControllerUtils.setRequestProjectBatchForMessageTip(request,mapList);
	}
	
	/**
	 * 获取项目承做人
	 * @param request
	 * @param bean
	 * @return
	 */
	@RequestMapping("getProjectArePeople")
	@ResponseBody
	public Object getProjectArePeople(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			if(!paramMap.isEmpty() && paramMap.containsKey("projectId")){
				List<Map<String,Object>> list = service.getProjectArePeople(paramMap);
				resultBean.setStatus("OK");
				resultBean.setMapList(list);
			}
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "getProjectArePeople",e);
		}
		return resultBean;
	}

}
