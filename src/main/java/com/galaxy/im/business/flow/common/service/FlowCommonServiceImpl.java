package com.galaxy.im.business.flow.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.MeetingScheduling;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.dao.IFlowCommonDao;
import com.galaxy.im.business.statistics.controller.StatisticsProjectController;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;
import com.galaxy.im.common.html.QHtmlClient;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;

@Service
public class FlowCommonServiceImpl extends BaseServiceImpl<ProjectBean> implements IFlowCommonService{
	private Logger log = LoggerFactory.getLogger(FlowCommonServiceImpl.class);
	
	@Autowired
	private IFlowCommonDao dao;

	@Autowired
	private Environment env;
	
	@Override
	protected IBaseDao<ProjectBean, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 取得项目状态
	 */
	@Override
	public Map<String, Object> projectStatus(Map<String, Object> paramMap) {
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("flag", 0);
			Map<String,Object> daoMap = dao.projectStatus(paramMap);
			
			if(daoMap!=null && !daoMap.isEmpty()){
				String projectStatus = CUtils.get().object2String(daoMap.get("dictCode"),"");
				if("projectStatus:0".equals(projectStatus)){
					result.put("flag",1);
				}else if("projectStatus:1".equals(projectStatus)){
					result.put("message","本项目处于投后运营状态，不能进行操作");
				}else if("projectStatus:2".equals(projectStatus)){
					result.put("message","本项目已经被否决，不能进行操作");
				}else if("projectStatus:3".equals(projectStatus)){
					result.put("message","本项目处于退出状态，不能进行操作");
				}else{
					result.put("message","本项目所处的阶段异常，请联系管理员");
				}
			}
			return result;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":projectStatus",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 否决项目
	 */
	@Override
	public Boolean vetoProject(Map<String, Object> paramMap) {
		try{
			return dao.vetoProject(paramMap)>0;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":vetoProject",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 流程进入到下一阶段
	 */
	@Override
	public boolean enterNextFlow(Map<String, Object> paramMap) {
		try{
			return dao.enterNextFlow(paramMap)>0;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":enterNextFlow",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 创建代办任务
	 */
	@Override
	public Long insertsopTask(SopTask bean) {
		try{
			dao.insertsopTask(bean);
			return bean.getId();
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":insertsopTask",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取最新会议
	 */
	@Override
	public Map<String, Object> getLatestMeetingRecordInfo(Map<String, Object> paramMap) {
		try{
			Map<String,Object> result = dao.getLatestMeetingRecordInfo(paramMap);
			return result;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":getLatestMeetingRecordInfo",e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 获取最新上传文件的信息
	 */
	@Override
	public Map<String, Object> getLatestSopFileInfo(Map<String, Object> paramMap) {
		try{
			Map<String,Object> result = dao.getLatestSopFileInfo(paramMap);
			return result;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":getLatestSopFileInfo",e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 创建会议排期
	 */
	@Override
	public Long insertMeetingScheduling(MeetingScheduling bean) {
		try{
			return dao.insertMeetingScheduling(bean);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":insertMeetingScheduling",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 权限---获取用户所在部门id
	 * @param guserid
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDeptId(Long guserid, HttpServletRequest request, HttpServletResponse response) {
		//调用客户端
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("power.server") + StaticConst.getCreadIdInfo;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("createdId",guserid);
		JSONArray valueJson=null;
		List<Map<String, Object>> list = null;
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(FlowCommonServiceImpl.class.getName() + "getDeptId：获取创建人信息时出错","此时服务器返回状态码非200");
		}else{
			boolean flag = true;
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson!=null && resultJson.containsKey("value")){
				valueJson = resultJson.getJSONArray("value");
				if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
					flag = false;
				}
				list=CUtils.get().jsonString2list(valueJson);
			}
			if(flag){
				log.error(FlowCommonServiceImpl.class.getName() + "getDeptId：获取创建人信息时出错","服务器返回正常，获取数据失败");
			}
		}
		/*if(list!=null){
			for(Map<String, Object> vMap:list){
				guserid= CUtils.get().object2Long( vMap.get("deptId"));
			}
		}else{
			guserid=0l;
		}*/
		return list;
	}

	/**
	 * 获取部门id
	 * 通过不能名称
	 */
	@Override
	public int getDeptIdByDeptName(String name, HttpServletRequest request, HttpServletResponse response) {
		//调用客户端
		int deptId=0;
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("power.server") + StaticConst.getDeptIdByDeptName;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("deptName",name);
		JSONArray valueJson=null;
		List<Map<String, Object>> list = null;
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(FlowCommonServiceImpl.class.getName() + "getDeptIdByDeptName：获取信息时出错","此时服务器返回状态码非200");
		}else{
			boolean flag = true;
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson!=null && resultJson.containsKey("value")){
				valueJson = resultJson.getJSONArray("value");
				if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
					flag = false;
				}
				list=CUtils.get().jsonString2list(valueJson);
			}
			if(flag){
				log.error(FlowCommonServiceImpl.class.getName() + "getDeptId：获取信息时出错","服务器返回正常，获取数据失败");
			}
		}
		if(list!=null){
			for(Map<String, Object> vMap:list){
				deptId = CUtils.get().object2Integer(vMap.get("deptId"));
			}
		}else{
			deptId=0;
		}
		return deptId;
	}

	/**
	 * 上传文件保存
	 */
	@Override
	public Long addSopFile(SopFileBean bean) {
		try{
			return dao.addSopFile(bean);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":addSopFile",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取上传文件信息
	 */
	@Override
	public List<Map<String, Object>> getSopFileList(Map<String, Object> paramMap) {
		try{
			return dao.getSopFileList(paramMap);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":getSopFileList",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新上传文件
	 */
	@Override
	public long updateSopFile(SopFileBean bean) {
		try{
			return dao.updateSopFile(bean);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":updateSopFile",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取项目基本信息
	 */
	@Override
	public SopProjectBean getSopProjectInfo(Map<String, Object> paramMap) {
		try{
			return dao.getSopProjectInfo(paramMap);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":getSopProjectInfo",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据文件名后缀，确定文档类型
	 */
	@Override
	public String getFileType(String fileSuffix) {
		if(fileSuffix.contains("PDF") || fileSuffix.contains("pdf")
				|| fileSuffix.contains("xls") || fileSuffix.contains("xlsx") 
				|| fileSuffix.contains("XLS") || fileSuffix.contains("XLSX")){
			return StaticConst.FILE_TYPE_1; 
		}else if(fileSuffix.contains("jpeg") || fileSuffix.contains("jpg")
				||fileSuffix.contains("png") ||fileSuffix.contains("JPG") 
				|| fileSuffix.contains("JPEG") ||fileSuffix.contains("PNG")){
			return StaticConst.FILE_TYPE_4;
		}else{
			return "";
		}
	}
	
	/**
	 * 拆分文件名称
	 */
	public Map<String, String> transFileNames(String fileName) {
		Map<String, String> retMap = new HashMap<String, String>();
		int dotPos = fileName.lastIndexOf(".");
		if(dotPos == -1){
			retMap.put("fileName", fileName);
			retMap.put("fileSuffix", "");
		}else{
			retMap.put("fileName", fileName.substring(0, dotPos));
			retMap.put("fileSuffix", fileName.substring(dotPos+1));
		}
		return retMap;
	}

	/**
	 * 更新代办任务
	 */
	@Override
	public Long updateSopTask(SopTask bean) {
		try{
			return dao.updateSopTask(bean);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":updateSopTask",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取人事，法务，财务的认领状态信息
	 */
	@Override
	public List<Map<String, Object>> getSopTaskList(Map<String, Object> paramMap) {
		try{
			return dao.getSopTaskList(paramMap);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":getSopTaskList",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新文件上传valid=0
	 */
	@Override
	public int updateValid(Long id) {
		try{
			Map<String, Object> paramMap =new HashMap<String, Object>();
			paramMap.put("id", id);
			return dao.updateValid(paramMap);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":updateValid",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取项目状态
	 */
	@Override
	public Map<String, Object> getProjectStatus(Map<String, Object> paramMap) {

		try{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("flag", 0);
			Map<String,Object> daoMap = dao.projectStatus(paramMap);
			
			if(daoMap!=null && !daoMap.isEmpty()){
				String projectStatus = CUtils.get().object2String(daoMap.get("dictCode"),"");
				if("projectStatus:0".equals(projectStatus)){
					result.put("flag",1);
				}else if("projectStatus:1".equals(projectStatus)){
					result.put("flag",1);
				}else if("projectStatus:2".equals(projectStatus)){
					result.put("flag",0);
				}else if("projectStatus:3".equals(projectStatus)){
					result.put("flag",0);
				}else{
					result.put("flag",0);
				}
			}
			return result;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":getProjectStatus",e);
			throw new ServiceException(e);
		}
	
	}

	/**
	 * 文件类型区分
	 */
	@Override
	public UrlNumber setNumForFile(int prograss, SopFileBean bean) {
		UrlNumber number = null;
		switch (bean.getFileWorkType()) {
		case "fileWorktype:1":			//业务尽职调查报告
			if(prograss==0){
				number = UrlNumber.one;
			}else{
				number = UrlNumber.two;
			}	
			break;
		case "fileWorktype:18":			//尽职调查启动会报告
			if(prograss==0){
				number = UrlNumber.three;
			}else{
				number = UrlNumber.four;
			}
			break;
		case "fileWorktype:19":		    //尽职调查总结会报告
			if(prograss==0){
				number = UrlNumber.five;
			}else{
				number = UrlNumber.six;
			}
			break;
		case "fileWorktype:5":			//投资意向书
			if(prograss==0){
				number = UrlNumber.one;
			}else{
				number = UrlNumber.two;
			}
			break;
		case "fileWorktype:6":			//投资协议
			if(prograss==0){
				number = UrlNumber.one;
			}else{
				number = UrlNumber.two;
			}	
			break;
		case "fileWorktype:7":			//股权转让协议
			if(prograss==0){
				number = UrlNumber.one;
			}else{
				number = UrlNumber.two;
			}	
			break;
		case "fileWorktype:17":			//立项报告
			if(prograss==0){
				number = UrlNumber.one;
			}else{
				number = UrlNumber.two;
			}
			break;
		case "fileWorktype:8":			//工商转让凭证
			if(prograss==0){
				number = UrlNumber.one;
			}else{
				number = UrlNumber.two;
			}
			break;
		case "fileWorktype:9":			//资金拨付凭证
			if(prograss==0){
				number = UrlNumber.one;
			}else{
				number = UrlNumber.two;
			}
			break;
		default:
			break;
		}
		return number;
	}

	@Override
	public List<String> selectRoleCodeByUserId(Long guserid, HttpServletRequest request, HttpServletResponse response) {
		//调用客户端
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("power.server") + StaticConst.getRoleCodeByUserId;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("userId",guserid);
		JSONArray valueJson=null;
		List<String> list = null;
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(StatisticsProjectController.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
		}else{
			boolean flag = true;
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson!=null && resultJson.containsKey("value")){
				valueJson = resultJson.getJSONArray("value");
				if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
					flag = false;
				}
				list=CUtils.get().jsonString2list(valueJson);
			}
			if(flag){
				log.error(StatisticsProjectController.class.getName() + "获取信息时出错","服务器返回正常，获取数据失败");
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getDeptNameByDeptId(Long deptId, HttpServletRequest request,
			HttpServletResponse response) {
		//调用客户端
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("power.server") + StaticConst.getDeptInfo;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("deptId",deptId);
		JSONArray valueJson=null;
		List<Map<String, Object>> list = null;
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(FlowCommonServiceImpl.class.getName() + "getDeptId：获取创建人信息时出错","此时服务器返回状态码非200");
		}else{
			boolean flag = true;
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson!=null && resultJson.containsKey("value")){
				valueJson = resultJson.getJSONArray("value");
				if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
					flag = false;
				}
				list=CUtils.get().jsonString2list(valueJson);
			}
			if(flag){
				log.error(FlowCommonServiceImpl.class.getName() + "getDeptId：获取创建人信息时出错","服务器返回正常，获取数据失败");
			}
		}
		return list;
	}

	@Override
	public List<InformationResult> getReportInfo(Map<String, Object> map) {
		try{
			return dao.getReportInfo(map);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + "getReportInfo",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public long addInformationResult(InformationResult result) {
		try{
			return dao.addInformationResult(result);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + "addInformationResult",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public long updateInformationResult(InformationResult result) {
		try{
			return dao.updateInformationResult(result);
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + "updateInformationResult",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<String, Object> getMeetingRecordInfo(Map<String, Object> map) {
		try{
			Map<String,Object> result = dao.getMeetingRecordInfo(map);
			return result;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":getMeetingRecordInfo",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取部门下所有用户
	 */
	@Override
	public List<Map<String, Object>> getUserListByDeptId(int deptId) {
		//用户列表
		Map<String,Object> vmap = new HashMap<String,Object>();
		vmap.put("depId", deptId);
		//事业部下的所有用户
		String urlU = env.getProperty("power.server") + StaticConst.getUsersByDepId;
		JSONArray rr=null;
		List<Map<String, Object>> userList=new ArrayList<Map<String, Object>>();
		String res = QHtmlClient.get().post(urlU, null, vmap);
		if("error".equals(res)){
			log.error(FlowCommonServiceImpl.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
		}else{
			JSONObject json = JSONObject.parseObject(res);
			if(json!=null && json.containsKey("value")){
				rr = json.getJSONArray("value");
				if(json.containsKey("success") && "true".equals(json.getString("success"))){
					//操作
					userList =CUtils.get().jsonString2list(rr);
				}
			}
		}
		return userList;	
	}

	
}
