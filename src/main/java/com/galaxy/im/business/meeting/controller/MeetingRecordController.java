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

import com.galaxy.im.bean.common.Dict;
import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.meeting.MeetingRecordBean;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.project.SopProjectBean;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.business.common.dict.service.IDictService;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.meeting.service.IMeetingRecordService;
import com.galaxy.im.business.operationLog.controller.ControllerUtils;
import com.galaxy.im.business.talk.service.ITalkRecordService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.webconfig.interceptor.operationLog.UrlNumber;

@Controller
@RequestMapping("/meeting")
public class MeetingRecordController {
	private Logger log = LoggerFactory.getLogger(MeetingRecordController.class);

	@Autowired
	IMeetingRecordService service;
	@Autowired
	ITalkRecordService talkService;
	@Autowired
	IDictService dictService;
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 会议记录列表
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("getMeetingRecordList")
	@ResponseBody
	public Object getMeetingRecordList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			if(paramMap!=null && paramMap.containsKey("meetingType")){
				List<String> meetingTypeList = new ArrayList<String>();
				
				if(paramMap.get("meetingType").toString().contains("pos")){
					//投后运营
					List<Dict> dictList = dictService.selectByParentCode("postMeetingType");
					for(Dict dict : dictList){
						meetingTypeList.add(dict.getDictCode());
					}
					paramMap.put("meetingTypeList", meetingTypeList);
					paramMap.put("recordType", 2);
				}else{
					//普通会议
					meetingTypeList.add(paramMap.get("meetingType").toString());
					paramMap.put("meetingTypeList",meetingTypeList);
					paramMap.put("recordType", 0);
				}
			}
			
			//健康状况
			Map<String,Object> m = service.getSopProjectHealth(paramMap);
			resultBean.setMap(m);
			
			QPage page = service.getMeetingRecordList(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
		}catch(Exception e){
			log.error(MeetingRecordController.class.getName() + "_getMeetingRecordList",e);
		}
		return resultBean;
	}
	
	/**
	 * 添加/编辑会议记录
	 * @param 
	 * @return
	 * @author liuli
	 */
	@RequestMapping("addMeetingRecord")
	@ResponseBody
	public Object addMeetingRecord(HttpServletRequest request,HttpServletResponse response,@RequestBody MeetingRecordBean bean){
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
				Map<String, Object> projectStatus = fcService.projectStatus(paramMap);
				SopProjectBean p = fcService.getSopProjectInfo(paramMap);
				if(projectStatus.containsKey("flag") && 
						CUtils.get().object2Integer(projectStatus.get("flag"))==1 && 
						p.getProjectProgress().equals(bean.getProjectProgress()) ){
					//会议记录存在，进行更新操作，否则保存
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
							sopFileBean.setFileType(StaticConst.FILE_TYPE_2);
							sopFileBean.setMeetinRecordId(bean.getId());
							sopFileBean.setFileUid(sessionBean.getGuserid());
							sopFileBean.setFileStatus(StaticConst.FILE_STATUS_2);
							sopFileBean.setCreatedTime(new Date().getTime());
							sopFileBean.setFileSource("1");
							
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
						id = service.insert(bean);
					}
				}else{
					resultBean.setMessage("项目当前状态或进度已被修改，请刷新");	
				}
				UrlNumber uNum = null;
				@SuppressWarnings("unused")
				String progress = null;
				/**
				 * 操作日志分区判断
				 */
				switch(bean.getMeetingType()){
				       case "meetingType:1":
				    	    if(prograss == 0)
				    	    	uNum = UrlNumber.one;
				    	    else 
				    	    	uNum = UrlNumber.two;
				    	    progress = StaticConst.PROJECT_PROGRESS_2;
				    	    break;
				       case "meetingType:2":
				    	    if(prograss == 0)
				    	    	uNum = UrlNumber.three;
				    	    else 
				    	    	uNum = UrlNumber.four;
				    	    progress = StaticConst.PROJECT_PROGRESS_3;
				    	    break;
				       case "meetingType:3":
				    	    if(prograss == 0)
				    	    	uNum = UrlNumber.five;
				    	    else 
				    	    	uNum = UrlNumber.six;
				    	    progress = StaticConst.PROJECT_PROGRESS_4;
				    	    break;
				       case "meetingType:4":
				    	    if(prograss == 0)
				    	    	uNum = UrlNumber.nine;
				            else 
				    	    	uNum = UrlNumber.ten;
				    	    progress = StaticConst.PROJECT_PROGRESS_7;
				    	    break;
				       case "meetingType:5":
				    	    if(prograss == 0)
				    	    	uNum = UrlNumber.seven;
				            else 
				    	    	uNum = UrlNumber.eight;
				    	    progress = StaticConst.PROJECT_PROGRESS_11;
				    	    break;
				    	default :
				    		 break;
				}
				
				if(updateCount!=0 || id!=0L){
					resultBean.setStatus("OK");
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("meetingRecordId", id);
					resultBean.setMap(map);
					//全息报告数据同步
					reportSync(bean);
					//记录操作日志
					ControllerUtils.setRequestParamsForMessageTip(request, p.getProjectName(), p.getId(), null,uNum);
				}
			}
		}catch(Exception e){
			log.error(MeetingRecordController.class.getName() + "_addMeetingRecord",e);
		}
		return resultBean;
	}
	
	//全息报告数据同步
	@SuppressWarnings("unused")
	private void reportSync(MeetingRecordBean bean) {
		InformationResult result=null;
		Map<String,Object> map = new HashMap<String,Object>();
		List<InformationResult> list =new ArrayList<InformationResult>();
		Long titleId=0L;
		String choose="";
		map.put("projectId", bean.getProjectId());
		map.put("parentId", "7028");
		
		//会议最新信息
		map.put("meetingType", bean.getMeetingType());
		Map<String,Object> res = fcService.getMeetingRecordInfo(map);
				
		//内部评审，立项会，投决会的结论同步全息报告
		if(bean.getMeetingType().equals("meetingType:1")){
			titleId=1111L;
			if(res.containsKey("meetingResultCode") && res.get("meetingResultCode")!=null){
				if(res.get("meetingResultCode").equals("meeting1Result:1")){
					choose="1142";
				}else if(res.get("meetingResultCode").equals("meeting1Result:2")){
					choose="1143";
				}else if(res.get("meetingResultCode").equals("meeting1Result:3")){
					choose="1144";
				}else if(res.get("meetingResultCode").equals("meeting1Result:4")){
					choose="1145";
				}
			}
		}else if(bean.getMeetingType().equals("meetingType:3")){
			titleId=1113L;
			if(res.containsKey("meetingResultCode") && res.get("meetingResultCode")!=null){
				if(res.get("meetingResultCode").equals("meeting3Result:1")){
					choose="1164";
				}else if(res.get("meetingResultCode").equals("meeting3Result:2")){
					choose="1162";
				}else if(res.get("meetingResultCode").equals("meeting3Result:3")){
					choose="1163";
				}else if(res.get("meetingResultCode").equals("meeting3Result:4")){
					choose="1165";
				}else if(res.get("meetingResultCode").equals("meeting3Result:5")){
					choose="1166";
				}else if(res.get("meetingResultCode").equals("meeting3Result:6")){
					choose="1167";
				}
			}
		}else if(bean.getMeetingType().equals("meetingType:4")){
			titleId=1114L;
			if(res.get("meetingResultCode").equals("meeting4Result:1")){
				choose="1173";
			}else if(res.get("meetingResultCode").equals("meeting4Result:2")){
				choose="1174";
			}else if(res.get("meetingResultCode").equals("meeting4Result:3")){
				choose="1177";
			}
		}
		map.put("titleId", titleId);
		list = fcService.getReportInfo(map);
		if(list.isEmpty()){
			//添加
			result =new InformationResult();
			result.setProjectId(CUtils.get().object2String(bean.getProjectId()));
			result.setTitleId(CUtils.get().object2String(titleId));
			result.setContentChoose(choose);
			result.setCreatedTime(new Date().getTime());
			result.setCreateId(CUtils.get().object2String(bean.getCreatedId()));
			long id = fcService.addInformationResult(result);
		}else{
			for(InformationResult ir : list){
				if(ir.getContentChoose()!=null){
					ir.setContentChoose(choose);
					ir.setUpdatedTime(new Date().getTime());
					ir.setUpdateId(CUtils.get().object2String(bean.getCreatedId()));
					long id = fcService.updateInformationResult(ir);
				}
			}
		}
	}

	/**
	 * 会议记录详情
	 * @param paramString
	 * @return
	 * @author liuli
	 */
	@RequestMapping("meetingRecordDetails")
	@ResponseBody
	public Object meetingRecordDetails(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			MeetingRecordBean bean = new MeetingRecordBean();
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			if(paramMap!=null && paramMap.containsKey("meetingRecordId")){
				bean =service.queryById(CUtils.get().object2Long(paramMap.get("meetingRecordId")));
			}
			resultBean.setStatus("ok");
			resultBean.setEntity(bean);
		} catch (Exception e) {
			log.error(MeetingRecordController.class.getName() + "_meetingRecordDetails",e);
		}
		return resultBean;
	}
	
}
