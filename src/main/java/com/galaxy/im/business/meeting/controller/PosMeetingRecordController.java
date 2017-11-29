package com.galaxy.im.business.meeting.controller;

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
import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.meeting.service.IMeetingRecordService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
import com.galaxy.im.business.sopfile.service.ISopFileService;
import com.galaxy.im.business.talk.service.ITalkRecordService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;

@Controller
@RequestMapping("/posmeeting")
public class PosMeetingRecordController {
	private Logger log = LoggerFactory.getLogger(PosMeetingRecordController.class);
	
	@Autowired
	IMeetingRecordService service;
	@Autowired
	ISopFileService sopFileService;
	
	@Autowired
	ITalkRecordService talkService;
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 运营分析-会议详情
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("postMeetingDetail")
	@ResponseBody
	public Object postMeetingDetail(@RequestBody MeetingRecordBean meetingRecord){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try{
			if(null == meetingRecord.getId()){
				resultBean.setMessage("缺少重要参数！");
				return resultBean;
			}
			
			//查询会议相关
			meetingRecord.setRecordType(2);
			Map<String,Object> record= service.postMeetingDetail(meetingRecord);
			if(null == record){
				resultBean.setMessage("未查到结果");
				return resultBean;
			}
			//查询附件
			SopFileBean sopfile = new SopFileBean();
		    sopfile.setMeetingId(meetingRecord.getId());
			List<Map<String,Object>> sopFileList = new ArrayList<Map<String,Object>>();
			sopFileList = sopFileService.getSopFileList(sopfile);
			record.put("files", sopFileList);
			resultBean.setEntity(record);
			resultBean.setStatus("OK");
			return resultBean;
		}catch(Exception e){
			log.error(PosMeetingRecordController.class.getName() + "postMeetingDetail",e);
		}
		return resultBean;
	}
	
	/**
	 * 删除运营分析相关附件(逻辑删除)
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("delPostMeetingFile")
	@ResponseBody
	public Object delPostMeetingFile(@RequestBody MeetingRecordBean meetingRecord){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try{
			if(null == meetingRecord.getId()){
				resultBean.setMessage("缺少重要参数！");
				return resultBean;
			}
			
			//查询附件
			SopFileBean sopfile = new SopFileBean();
		    sopfile.setMeetingId(meetingRecord.getId());
		    sopfile.setFileKey(meetingRecord.getFileKey());
		    int result = service.delPostMeetingFile(sopfile);
		    if(result>0){
		    	resultBean.setStatus("OK");
		    	resultBean.setMessage("成功删除");
		    }
			return resultBean;
		}catch(Exception e){
			log.error(PosMeetingRecordController.class.getName() + "delPostMeetingFile",e);
		}
		return resultBean;
	}
	
	/**
	 * 添加/编辑运营会议纪要
	 * @param 
	 * @return
	 * @author liuli
	 */
	@RequestMapping("addPosMeetingRecord")
	@ResponseBody
	public Object addPosMeetingRecord(HttpServletRequest request,HttpServletResponse response,@RequestBody MeetingRecordBean bean){
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
				SopProjectBean p = fcService.getSopProjectInfo(paramMap);
				if(p.getProjectStatus().equals(StaticConst.PROJECT_STATUS_1) && 
					p.getProjectProgress().equals(StaticConst.PROJECT_PROGRESS_10)){
					//记录存在，进行更新操作，否则保存
					if(bean.getId()!=null && bean.getId()!=0){
						prograss = 1;
						//保存sop_file
						if(!"".equals(bean.getFileKey()) && bean.getFileKey()!=null){
							//通过用户id获取一些信息
							List<Map<String, Object>> list = fcService.getDeptId(sessionBean.getGuserid(),request,response);
							if(list!=null){
								for(Map<String, Object> vMap:list){
									deptId = CUtils.get().object2Long( vMap.get("deptId"));
								}
							}
							Map<String,String> nameMap = service.transFileNames(bean.getFileName());
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
							sopFileBean.setFileType(StaticConst.FILE_TYPE_1);
							sopFileBean.setMeetingId(bean.getId());
							sopFileBean.setFileUid(sessionBean.getGuserid());
							sopFileBean.setFileStatus(StaticConst.FILE_STATUS_2);
							sopFileBean.setCreatedTime(new Date().getTime());
							sopFileBean.setFileSource("1");
							sopFileBean.setFileValid(1);
							sopFileBean.setRecordType(2);
							long sopId =talkService.saveSopFile(sopFileBean);
							//获取sopfile 主键
							if(sopId!=0){
								bean.setFileId(sopId);
							}
						}
						id=bean.getId();
						if(bean.getMeetingDateStr()!=null){
							bean.setMeetingDate(DateUtil.convertStringtoD(bean.getMeetingDateStr()));
						}
						updateCount = service.updateById(bean);
					}else{
						//保存
						bean.setMeetingDate(DateUtil.convertStringtoD(bean.getMeetingDateStr()));
						bean.setCreatedId(sessionBean.getGuserid());
						bean.setRecordType(2);
						id = service.insert(bean);
					}
				}else{
					resultBean.setMessage("项目当前状态或进度未处于投后运营阶段");	
				}
				
				//操作日志区分
				UrlNumber uNum = null;
				if(prograss ==0){
					uNum = UrlNumber.one;
				}else{
					uNum = UrlNumber.two;
				}
				    	    
				if(updateCount!=0 || id!=0L){
					resultBean.setStatus("OK");
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("meetingRecordId", id);
					resultBean.setMap(map);
					//记录操作日志
					ControllerUtils.setRequestParamsForMessageTip(request, p.getProjectName(), p.getId(), null,uNum);
				}
			}
		}catch(Exception e){
			log.error(PosMeetingRecordController.class.getName() + "addPosMeetingRecord",e);
		}
		return resultBean;
	}
}
