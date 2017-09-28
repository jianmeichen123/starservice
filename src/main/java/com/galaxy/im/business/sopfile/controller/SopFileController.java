package com.galaxy.im.business.sopfile.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.sopfile.service.ISopFileService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;

@Controller
@RequestMapping("/sopFile")
public class SopFileController {

	private Logger log = LoggerFactory.getLogger(SopFileController.class);
	
	@Autowired
	ISopFileService service;
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 获取商业计划书
	 * @param request
	 * @param response
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getBusinessPlanFile")
	@ResponseBody
	public Object getBusinessPlanFile(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<ProjectBean> resultBean = new ResultBean<ProjectBean>();
		resultBean.setStatus("error");
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(!paramMap.containsKey("projectId")){
				resultBean.setMessage("传入的projectId为空");
			}
			paramMap.put("fileWorkType", StaticConst.FILE_WORKTYPE_12);
			
			Map<String,Object> map = service.getBusinessPlanFile(paramMap);
			if(map!=null && map.containsKey("createdTime")){
				map.put("createDate", DateUtil.longToString(CUtils.get().object2Long(map.get("createdTime"))));
			}
			resultBean.setStatus("OK");
			resultBean.setMap(map);
		}catch(Exception e){
			log.error(SopFileController.class.getName() + "getBusinessPlanFile",e);
		}
		return resultBean;
	}
	
	/**
	 * 上传商业计划书
	 * @param request
	 * @param response
	 * @param paramString
	 * @return
	 */
	@RequestMapping("uploadBizplan")
	@ResponseBody
	public Object uploadBizplan(HttpServletRequest request,HttpServletResponse response,@RequestBody SopFileBean bean){
		ResultBean<ProjectBean> resultBean = new ResultBean<ProjectBean>();
		resultBean.setStatus("error");
		long id=0L;
		try{
			Long deptId =0L;
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("projectId", bean.getProjectId());
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			//通过用户id获取一些信息
			List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(),request,response);
			if(list!=null){
				for(Map<String, Object> vMap:list){
					deptId= CUtils.get().object2Long( vMap.get("deptId"));
				}
			}
			SopProjectBean sopBean = fcService.getSopProjectInfo(paramMap);
			
			bean.setProjectId(sopBean.getId());
			bean.setProjectProgress(sopBean.getProjectProgress());
			bean.setCareerLine(deptId);
			bean.setFileWorkType(StaticConst.FILE_WORKTYPE_12);
			String fileType =fcService.getFileType(bean.getFileSuffix());
			bean.setFileType(fileType);
			bean.setFileStatus(StaticConst.FILE_STATUS_2);
			bean.setFileValid(1);
			bean.setCreatedTime(new Date().getTime());
			bean.setFileUid(sessionBean.getGuserid());
			if(sopBean.getProjectType().equals(StaticConst.PROJECT_TYPE_1)){//外部投资
				bean.setFileSource("2");
			}else if (sopBean.getProjectType().equals(StaticConst.PROJECT_TYPE_2)){//内部创建
				bean.setFileSource("1");
			}
			//保存
			id =fcService.addSopFile(bean);
			
			if(id!=0L){
				paramMap.clear();
				paramMap.put("id", id);
				resultBean.setMap(paramMap);
				resultBean.setStatus("ok");
			}
		}catch(Exception e){
			log.error(SopFileController.class.getName() + "uploadBizplan",e);
		}
		return resultBean;
	}
	
	/**
	 * 项目文档列表和筛选
	 * @param request
	 * @param response
	 * @param paramString
	 * @return
	 */
	@RequestMapping("searchappFileList")
	@ResponseBody
	public Object searchappFileList(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<ProjectBean> resultBean = new ResultBean<ProjectBean>();
		resultBean.setStatus("error");
		try{
			List<String> fileWorktypeList = new ArrayList<String>();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(!paramMap.containsKey("fileWorktype")){
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_1);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_2);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_3);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_4);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_5);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_6);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_7);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_8);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_9);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_17);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_18);
				fileWorktypeList.add(StaticConst.FILE_WORKTYPE_19);
			}else{
				fileWorktypeList.add(CUtils.get().object2String(paramMap.get("fileWorktype")));
			}
			paramMap.put("fileWorktypeList", fileWorktypeList);
			List<Map<String, Object>> map = service.searchappFileList(paramMap);
			for(Map<String, Object> m :map){
				if(m.containsKey("createdTime")){
					m.put("createDate", DateUtil.longToString(CUtils.get().object2Long(m.get("createdTime"))));
				}
				if(m.containsKey("updatedTime")){
					m.put("updatedDate", DateUtil.longToString(CUtils.get().object2Long(m.get("updatedTime"))));
				}
			}
			resultBean.setStatus("OK");
			resultBean.setMapList(map);
		}catch(Exception e){
			log.error(SopFileController.class.getName() + "searchappFileList",e);
		}
		return resultBean;
	}
	
	
}
