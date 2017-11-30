package com.galaxy.im.business.talk.controller;

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
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.talk.ProjectTalkBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
import com.galaxy.im.business.talk.service.IProjectTalkService;
import com.galaxy.im.business.talk.service.ITalkRecordService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;

@Controller
@RequestMapping("/projectTalk")
public class ProjectTalkController {
	private Logger log = LoggerFactory.getLogger(ProjectTalkController.class);

	@Autowired
	IProjectTalkService service;
	@Autowired
	ITalkRecordService talkService;
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 项目访谈记录列表
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("getProjectTalkList")
	@ResponseBody
	public Object getProjectTalkList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			QPage page = service.getProjectTalkList(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
		}catch(Exception e){
			log.error(ProjectTalkController.class.getName() + "_getProjectTalkList",e);
		}
		return resultBean;
	}
	
	/**
	 * 添加/编辑项目访谈记录
	 * @param 
	 * @return
	 * @author liuli
	 */
	@RequestMapping("addProjectTalk")
	@ResponseBody
	public Object addProjectTalk(HttpServletRequest request,HttpServletResponse response,@RequestBody ProjectTalkBean bean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			int updateCount = 0;
			Long id = 0L;
			Long deptId = 0L;
			int prograss = 0;
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			if(bean!=null){
				paramMap.put("projectId", bean.getProjectId());
				Map<String, Object> projectStatus = fcService.getProjectStatus(paramMap);
				SopProjectBean p = fcService.getSopProjectInfo(paramMap);
				if(projectStatus.containsKey("flag") && 
						CUtils.get().object2Integer(projectStatus.get("flag"))==1){
					//项目访谈记录存在，进行更新操作，否则保存
					if(bean.getId()!=null && bean.getId()!=0){
						prograss=1;
						//保存sop_file
						if(!"".equals(bean.getFileKey()) && bean.getFileKey()!=null){
							
							//通过用户id获取一些信息
							List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(),request,response);
							if(list!=null){
								for(Map<String, Object> vMap:list){
									deptId = CUtils.get().object2Long( vMap.get("deptId"));
								}
							}
							Map<String,String> nameMap = transFileNames(bean.getFileName());
							SopFileBean sopFileBean =new SopFileBean();
							if(p!=null){
								//项目id，当前阶段，所属事业线
								sopFileBean.setProjectId(p.getId());
								sopFileBean.setProjectProgress(p.getProjectProgress());
							}
							sopFileBean.setCareerLine(deptId);
							sopFileBean.setFileKey(bean.getFileKey());
							sopFileBean.setFileLength(bean.getFileLength());
							sopFileBean.setBucketName(bean.getBucketName());
							sopFileBean.setFileName(nameMap.get("fileName"));
							sopFileBean.setFileSuffix(nameMap.get("fileSuffix"));
							sopFileBean.setFileType(StaticConst.FILE_TYPE_2);
							sopFileBean.setMeetinRecordId(bean.getId());
							sopFileBean.setFileUid(sessionBean.getGuserid());
							sopFileBean.setFileStatus(StaticConst.FILE_STATUS_2);
							sopFileBean.setCreatedTime(new Date().getTime());
							sopFileBean.setFileSource("1");
							sopFileBean.setFileValid(1);
							
							long sopId =talkService.saveSopFile(sopFileBean);
							//获取sopfile 主键
							if(sopId!=0){
								bean.setFileId(sopId);
							}
						
						}
						id=bean.getId();
						updateCount = service.updateById(bean);
					}else{
						//保存
						bean.setViewDate(DateUtil.convertStringtoD(bean.getViewDateStr()));
						bean.setCreatedId(sessionBean.getGuserid());
						id = service.insert(bean);
					}
				}else{
					resultBean.setMessage("项目当前状态或进度已被修改，请刷新");	
				}
				if(updateCount!=0 || id!=0L){
					resultBean.setStatus("OK");
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("talkRecordId", id);
					resultBean.setMap(map);
					// 记录操作日志
					UrlNumber uNum = null;
					if(prograss==0){
						uNum = UrlNumber.one;
					}else{
						uNum = UrlNumber.two;
					}
					ControllerUtils.setRequestParamsForMessageTip(request, p.getProjectName(), p.getId(), null, uNum);
				}
			}
		}catch(Exception e){
			log.error(ProjectTalkController.class.getName() + "_addProjectTalk",e);
		}
		return resultBean;
	}
	
	/**
	 * 项目访谈详情
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("projectTalkDetails")
	@ResponseBody
	public Object projectTalkDetails(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			ProjectTalkBean bean = new ProjectTalkBean();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			if(paramMap!=null && paramMap.containsKey("talkRecordId")){
				bean =service.queryById(CUtils.get().object2Long(paramMap.get("talkRecordId")));
			}
			resultBean.setStatus("ok");
			resultBean.setEntity(bean);
		} catch (Exception e) {
			log.error(ProjectTalkController.class.getName() + "_projectTalkDetails",e);
		}
		return resultBean;
	}
	
	
	private Map<String, String> transFileNames(String fileName) {
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
}
