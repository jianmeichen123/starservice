package com.galaxy.im.business.flow.businessnegotiation.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.business.flow.businessnegotiation.service.IBusinessnegotiationService;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.html.QHtmlClient;

/**
 * 商务谈判
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/businessnegotiation")
public class BusinessnegotiationController {
	private Logger log = LoggerFactory.getLogger(BusinessnegotiationController.class);
	
	@Autowired
	private Environment env;
	@Autowired
	IBusinessnegotiationService service;
	@Autowired
	private IFlowCommonService fcService;
	
	/**
	 * 项目否决/投资意向书（投资）/投资协议（闪投）操作按钮状态
	 * 依据：所处阶段中该项目的会议记录，1:是否存在"否决"的会议,2:是否存在"闪投"的会议,3:是否存在"投资"的会议
	 * 前端处理：如果存在，则项目否决/投资意向书（投资）/投资协议（闪投）的按钮变亮
	 * 
	 * @param
	 * 	projectId   项目ID-使用JSON的方式传递
	 * @return
	 * 	entity -> flashpass   布尔型  存在"闪投"的会议：true
	 *  entity -> investpass  布尔型  存在"投资"的会议：true
	 *  entity -> veto        布尔型  存在否决的会议：true
	 */
	@RequestMapping("poButonStatus")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> m = service.businessOperateStatus(paramMap);
			if(CUtils.get().mapIsNotEmpty(m)){
				result.setEntity(m);
			}
			result.setStatus("OK");
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 否决项目
	 * @param paramString
	 * @return
	 */
	@RequestMapping("votedown")
	@ResponseBody
	public Object votedown(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		int flag = 0;
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			Map<String,Object> rMap = new HashMap<String,Object>();
			rMap.put("flag",0);
			rMap.put("message", "未知错误");
			
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				//验证该项目的状态，查看能否进行操作
				Map<String,Object> statusMap = fcService.projectStatus(paramMap);
				if(CUtils.get().mapIsNotEmpty(statusMap)){
					flag = CUtils.get().object2Integer(statusMap.get("flag"));
					if(flag==1){
						//否决项目
						if(fcService.vetoProject(paramMap)){
							rMap.put("flag", 1);
							rMap.put("message", "否决项目成功");
						}
					}else{
						rMap.put("flag", 0);
						rMap.put("message", CUtils.get().object2String(statusMap.get("message"), "未知错误"));
					}
				}
			}
			result.setStatus("OK");
			result.setEntity(rMap);
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 进入投资协议（闪投）
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startInvestmentPolicy")
	@ResponseBody
	public Object startInvestmentPolicy(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				paramMap.put("projectProgress", "projectProgress:8");	//表示进入投资协议（闪投）
				paramMap.put("greanChannel", 10);						//绿色通道修改为商务谈判
				if(service.updateProjectStatus(paramMap)){
					resultBean.setFlag(1);
					SessionBean sessionBean = CUtils.get().getBeanBySession(request);
					//获取用户所属部门id
					long deptId = getDeptId(sessionBean.getGuserid(),request,response);
					//生成投资协议代办任务
					SopTask bean = new SopTask();
					bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
					bean.setTaskName(StaticConst.TASK_NAME_TZXY);
					bean.setTaskType(StaticConst.TASK_TYPE_XTBG);
					bean.setTaskFlag(StaticConst.TASK_FLAG_TZXY);
					bean.setTaskStatus(StaticConst.TASK_STATUS_DRL);
					bean.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
					bean.setAssignUid(sessionBean.getGuserid());
					bean.setDepartmentId(deptId);
					bean.setCreatedTime(new Date().getTime());
					@SuppressWarnings("unused")
					Long id = fcService.insertsopTask(bean);
				}
			}
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}

	/**
	 * 进入投资意向书（投资）
	 * @param paramString
	 * @return
	 */
	@RequestMapping("startInvestmentIntent")
	@ResponseBody
	public Object startInvestmentIntent(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				paramMap.put("projectProgress", "projectProgress:5");	//表示进入投资意向书（投资）
				if(service.updateProjectStatus(paramMap)){
					resultBean.setFlag(1);
					//生成投资意向书的代办任务
					SessionBean sessionBean = CUtils.get().getBeanBySession(request);
					//获取用户所属部门id
					long deptId = getDeptId(sessionBean.getGuserid(),request,response);
					//生成投资协议代办任务
					SopTask bean = new SopTask();
					bean.setProjectId(CUtils.get().object2Long(paramMap.get("projectId")));
					bean.setTaskName(StaticConst.TASK_NAME_SCTZYXS);
					bean.setTaskType(StaticConst.TASK_TYPE_XTBG);
					bean.setTaskFlag(StaticConst.TASK_FLAG_SCTZYXS);
					bean.setTaskStatus(StaticConst.TASK_STATUS_DRL);
					bean.setTaskOrder(StaticConst.TASK_ORDER_NORMAL);
					bean.setAssignUid(sessionBean.getGuserid());
					bean.setDepartmentId(deptId);
					bean.setCreatedTime(new Date().getTime());
					@SuppressWarnings("unused")
					Long id = fcService.insertsopTask(bean);
				}
			}
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 权限---获取用户所在部门id
	 * @param guserid
	 * @param request
	 * @param response
	 * @return
	 */
	private long getDeptId(Long guserid, HttpServletRequest request, HttpServletResponse response) {
		//调用客户端
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("power.server") + StaticConst.getCreadIdInfo;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("createdId",guserid);
		JSONArray valueJson=null;
		List<Map<String, Object>> list = null;
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(BusinessnegotiationController.class.getName() + "getDeptId：获取创建人信息时出错","此时服务器返回状态码非200");
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
				log.error(BusinessnegotiationController.class.getName() + "getDeptId：获取创建人信息时出错","服务器返回正常，获取数据失败");
			}
		}
		if(list!=null){
			for(Map<String, Object> vMap:list){
				guserid= CUtils.get().object2Long( vMap.get("deptId"));
			}
		}else{
			guserid=0l;
		}
		return guserid;
	}
}
