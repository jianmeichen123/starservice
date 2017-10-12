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
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.ProjectBeanVo;
import com.galaxy.im.bean.project.ProjectBo;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.business.common.config.service.ConfigService;
import com.galaxy.im.business.common.dict.service.IDictService;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.project.service.IFinanceHistoryService;
import com.galaxy.im.business.project.service.IProjectService;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.Page;
import com.galaxy.im.common.db.PageRequest;
import com.galaxy.im.common.enums.EnumUtil;

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
			project.setCreatedId(bean.getGuserid());
			
			//分页查询
			Page<ProjectBean> pageProject = service.queryPageList(project,
					new PageRequest(project.getPageNum(),
							project.getPageSize(), 
							Direction.fromString(project.getDirection()),
							project.getProperty()));
			//查询结果放在List<ProjectBean>
			List<ProjectBean> projectList = new ArrayList<ProjectBean>();
			for(ProjectBean p : pageProject.getContent()){
				projectList.add(p);
			}
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
			paramMap.put("projectId", projectId);
			//基础信息(数据来源 全息报告)
			Map<String,Object> QXinfoMap = service.selectBaseProjectInfo(paramMap);
			//基础信息(数据来源项目表)
			Map<String,Object> infoMap = service.getBaseProjectInfo(projectId);
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
			
			result.setEntity(resultMap);
			result.setStatus("OK");
			
		}catch(Exception e){
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
						resultBean.setMessage("项目名重复!");
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
					//bean.setCreatedTime(DateUtil.convertStringToDate(p.getCreateDate().trim(), "yyyy-MM-dd").getTime());
					int num = service.updateProject(bean);
					//全息报告result表数据更新
					editInformationResult(bean,userId);
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
						resultBean.setMessage("项目名重复!");
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
						bean.setCreatedTime(DateUtil.convertStringToDate(bean.getCreateDate().trim(), "yyyy-MM-dd").getTime());
						long id = service.saveProject(bean);
						//新建项目存入全息报告中的信息
						editInformationResult(bean,userId);
						if (id > 0) {
							map.put("id", id);
							resultBean.setStatus("OK");
							resultBean.setMessage("项目添加成功!");
							resultBean.setMap(map);									
						}
					}
				}
			}
		}catch(Exception e){
			log.error(ProjectController.class.getName() + "_addProject",e);
		}
		return resultBean;
	}
	
	
	void editInformationResult(SopProjectBean bean,Long userId){
		String array[] = {"1916","1108","1917","1943","3004","3010","3011","3012"};
			Map<String, Object> hashmap = new HashMap<>();
			hashmap.put("projectId", bean.getId());
			for(int i=0;i<array.length;i++){
			hashmap.put("titleId", array[i]);
			InformationResult result1 = service.findResultInfoById(hashmap);
			if (result1!=null&&result1.getId()!=null) {
				result1.setId(result1.getId());
				if (array[i].equals("1916")) {
					result1.setContentDescribe1(CUtils.get().object2String(bean.getProjectContribution()));
				}else if (array[i].equals("1108")) {
					result1.setContentChoose(CUtils.get().object2String(bean.getFinanceStatus()));
				}else if (array[i].equals("1917")) {
					result1.setContentDescribe1(CUtils.get().object2String(bean.getProjectShareRatio()));
				}else if (array[i].equals("1943")) {
					result1.setContentDescribe1(CUtils.get().object2String(bean.getProjectValuations()));
				}else if (array[i].equals("3004")) {
					result1.setContentDescribe1(CUtils.get().object2String(bean.getFinalContribution()));
				}else if (array[i].equals("3010")) {
					result1.setContentDescribe1(CUtils.get().object2String(bean.getFinalShareRatio()));
				}else if (array[i].equals("3011")) {
					result1.setContentDescribe1(CUtils.get().object2String(bean.getServiceCharge()));
				}else if (array[i].equals("3012")) {
					result1.setContentDescribe1(CUtils.get().object2String(bean.getFinalValuations()));
				}
				result1.setUpdateId(CUtils.get().object2String(userId));
				result1.setUpdatedTime(new Date().getTime());
				fcService.updateInformationResult(result1);
			}else{
				InformationResult result2 = new InformationResult();
				result2.setProjectId(CUtils.get().object2String(bean.getId()));
				result2.setTitleId(array[i]);
				if (array[i].equals("1916")) {
					result2.setContentDescribe1(CUtils.get().object2String(bean.getProjectContribution()));
				}else if (array[i].equals("1108")) {
					result2.setContentChoose(CUtils.get().object2String(bean.getFinanceStatus()));
				}else if (array[i].equals("1917")) {
					result2.setContentDescribe1(CUtils.get().object2String(bean.getProjectShareRatio()));
				}else if (array[i].equals("1943")) {
					result2.setContentDescribe1(CUtils.get().object2String(bean.getProjectValuations()));
				}else if (array[i].equals("3004")) {
					result2.setContentDescribe1(CUtils.get().object2String(bean.getFinalContribution()));
				}else if (array[i].equals("3010")) {
					result2.setContentDescribe1(CUtils.get().object2String(bean.getFinalShareRatio()));
				}else if (array[i].equals("3011")) {
					result2.setContentDescribe1(CUtils.get().object2String(bean.getServiceCharge()));
				}else if (array[i].equals("3012")) {
					result2.setContentDescribe1(CUtils.get().object2String(bean.getFinalValuations()));
				}
				result2.setCreateId(CUtils.get().object2String(userId));
				result2.setCreatedTime(new Date().getTime());
				fcService.addInformationResult(result2);
			}
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
		try {
			String deptName="";
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
			//排序字段
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(new Order(Direction.DESC, "updated_time"));
			orderList.add(new Order(Direction.DESC, "created_time"));			
			Sort sort = new Sort(orderList);
			
			if(projectBo.getSflag()==1){
				//跟进中
				projectBo.setProjectStatus("projectStatus:0");
				//genProjectBean = service.querygjzProjectList(projectBo, new PageRequest(projectBo.getPageNum(), projectBo.getPageSize() , sort));
			}
			if(projectBo.getSflag()==2){
				//投后运营
				projectBo.setProjectStatus("projectStatus:1");
				//genProjectBean = service.querythyyList(projectBo, new PageRequest(projectBo.getPageNum(), projectBo.getPageSize() , sort));
			}
			if(projectBo.getSflag()==3){
				//否决
				projectBo.setProjectStatus("projectStatus:2");
				//genProjectBean = service.queryfjList(projectBo, new PageRequest(projectBo.getPageNum(), projectBo.getPageSize() , sort));
			}
			/*if(projectBo.getSflag()==4){
				if(projectBo.getKeyword()!=null){
					projectBo.setCeeword(projectBo.getKeyword().toUpperCase());
				}
				projectBo.setCreateUid(null);
				genProjectBean = service.queryPageList(projectBo,  new PageRequest(projectBo.getPageNum(), projectBo.getPageSize(),sort));
			}*/
			//查询列表
			genProjectBean = service.queryPageList(projectBo,  new PageRequest(projectBo.getPageNum(), projectBo.getPageSize(),sort));
			
			Page<SopProjectBean> page = genProjectBean.getPvPage();
			List<SopProjectBean> dataList = page.getContent();
			//内容处理
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
					//map.put("financeStatusName", getNameByCode(CUtils.get().object2String(map.get("financeStatus")),"financeStatus"));
					map.put("financeStatusName", CUtils.get().object2String(map.get("financeStatus")));
				}
				//项目状态编码
				if(StringUtils.isNotBlank(CUtils.get().object2String(map.get("projectStatus")))){
					map.put("projectStatusName", getNameByCode(CUtils.get().object2String(map.get("projectStatus")),"projectStatus"));
				}
			 }
			 page.setContent(dataList);
			 genProjectBean.setPvPage(page);
			 Long gjzNum = service.queryProjectgjzCount(projectBo);
			 Long thyyNum = service.queryProjectthyyCount(projectBo);
			 Long yfjNum = service.queryProjectfjCount(projectBo);
			
			 genProjectBean.setGjzCount(gjzNum);
			 genProjectBean.setThyyCount(thyyNum);
			 genProjectBean.setYfjCount(yfjNum);
			if(page!=null){
				resultBean.setStatus("ok");
				resultBean.setMap(BeanUtils.toMap(genProjectBean));
			}
		} catch (Exception e) {
			log.error(ProjectController.class.getName() + "selectProjectList",e);
		}
		return resultBean;
	}

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

}
