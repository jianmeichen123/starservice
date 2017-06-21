package com.galaxy.im.business.talk.controller;

import java.util.HashMap;
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
import com.galaxy.im.bean.talk.ProjectTalkBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.talk.service.IProjectTalkService;
import com.galaxy.im.business.talk.service.ITalkRecordService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;

@Controller
@RequestMapping("/projectTalk")
public class ProjectTalkController {
	private Logger log = LoggerFactory.getLogger(ProjectTalkController.class);

	@Autowired
	IProjectTalkService service;
	@Autowired
	ITalkRecordService talkService;
	
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
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			
			if(bean!=null){
				//项目访谈记录存在，进行更新操作，否则保存
				if(bean.getId()!=null && bean.getId()!=0){
					//保存sop_file
					if(!"".equals(bean.getFileKey()) && bean.getFileKey()!=null){
						SopFileBean sopFileBean =new SopFileBean();
						sopFileBean.setFileKey(bean.getFileKey());
						sopFileBean.setFileLength(bean.getFileLength());
						sopFileBean.setBucketName(bean.getBucketName());
						sopFileBean.setFileName(bean.getFileName());
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
			}
			
			if(updateCount!=0 || id!=0L){
				resultBean.setStatus("OK");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("talkRecordId", id);
				resultBean.setMap(map);
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
}
