package com.galaxy.im.business.callon.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import com.galaxy.im.bean.contracts.ContractsBean;
import com.galaxy.im.bean.schedule.ScheduleDetailBean;
import com.galaxy.im.bean.schedule.ScheduleDetailBeanVo;
import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.callon.service.ICallonDetailService;
import com.galaxy.im.business.callon.service.ICallonService;
import com.galaxy.im.business.contracts.service.IContractsService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.IRedisCache;
import com.galaxy.im.common.db.QPage;
import com.galaxy.im.common.html.QHtmlClient;

@Controller
@RequestMapping("/callon")
public class CallonController {
	private Logger log = LoggerFactory.getLogger(CallonController.class);
	
	@Autowired
	private IRedisCache<String,Object> cache;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IContractsService contractsService;
	
	
	@Autowired
	private ICallonDetailService detailService;
	
	@Autowired
	private ICallonService callonService;

	/**
	 * 保存/编辑拜访计划
	 * @param paramString
	 * @return
	 */
	@RequestMapping("saveCallonPlan")
	@ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response,@RequestBody ScheduleInfo infoBean){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			int updateCount = 0;
			Long id = 0L;
			SessionBean bean = CUtils.get().getBeanBySession(request);
			if(infoBean!=null && infoBean.getId()!=null && infoBean.getId()!=0){
				//保存用户ID
				infoBean.setUpdatedId(bean.getGuserid());
				updateCount = callonService.updateById(infoBean);
				pushUpdateCallon(request,infoBean);
			}else{
				//保存用户ID
				infoBean.setCreatedId(bean.getGuserid());
				id = callonService.insert(infoBean);
				pusAddCallon(request,id,infoBean);
			}
			if(updateCount!=0 || id!=0L){
				resultBean.setFlag(1);
			}
			resultBean.setStatus("OK");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultBean;
	}
	
	/**
	 * 删除拜访计划
	 * @param paramString
	 * @return
	 */
	@RequestMapping("delCallonPlan")
	@ResponseBody
	public Object deletePlan(HttpServletRequest request,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			//long planId = CUtils.get().object2Long(map.get("id"), 0L);
			if(map!=null){
				map.put("updatedTime", DateUtil.getMillis(new Date()));
				SessionBean sessionBean = CUtils.get().getBeanBySession(request);
				map.put("updatedId", sessionBean.getGuserid());
				boolean flag = callonService.delCallonById(map);
				
				Long id = CUtils.get().object2Long(map.get("id"), 0L);
				if(id!=0){
					pushDeleteCallon(request, id);
				}
				
				if(flag){
					resultBean.setFlag(1);
				}
			}
			resultBean.setStatus("OK");
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 判断拜访计划能否删除或编辑
	 */
	@RequestMapping("callonEnableEditOrDel")
	@ResponseBody
	public Object callonEnableEditOrDel(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try{
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			Long planId = CUtils.get().object2Long(map.get("id"), 0L);
			
			resultBean.setStatus("OK");
			if(planId!=0){
				boolean flag = callonService.callonEnableEditOrDel(planId);
				if(!flag){
					resultBean.setFlag(1);
				}
			}
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 拜访计划列表
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getCallonList")
	@ResponseBody
	public Object getCallonList(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			
			if(paramMap!=null && paramMap.containsKey("selectKey")){
				String likeString = CUtils.get().object2String(paramMap.get("selectKey"), "");
				if(CUtils.get().stringIsNotEmpty(likeString)){
					paramMap.put("selectKey", "%" + likeString + "%");
				}
			}
			
			QPage page = callonService.selectCallonList(paramMap);
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
			
		}catch(Exception e){
		}
		return resultBean;
	}
	
	/**
	 * 拜访详情
	 * @param detail
	 * @return
	 */
	@RequestMapping("getCallonDetails")
	@ResponseBody
	public Object getCallonDetails(@RequestBody ScheduleDetailBeanVo detail){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		List<String> list = null;
		try{
			//获取缓存里项目移交
			boolean res = cache.hasKey(StaticConst.transfer_projects_key);
			if(res){
				//获取项目移交id
				String transferId =cache.get(StaticConst.transfer_projects_key).toString();
				//将获取到的项目id存到list里
				transferId = transferId.replace(" ", "");
				if(transferId.startsWith("[")){
					transferId = transferId.substring(1);
				}
				if(transferId.endsWith("]")){
					transferId = transferId.substring(0,transferId.length()-1);
				}
				String[] array = transferId.split(",");
				if(array!=null && array.length>0){
					list=Arrays.asList(array);
				}
			}
			//拜访详情
			List<ScheduleDetailBean> listBean = detailService.getQueryById(detail.getCallonId());
			ScheduleDetailBean bean = listBean.get(0);
			if(bean!=null){
				//关联项目不为空，取项目的历史访谈记录
				if(!"".equals(bean.getProjectName()) && bean.getProjectName()!=null){
					detail.setProId(bean.getProjectId());
					//访谈记录个数
					long count = detailService.queryCount(detail);
					bean.setInterviewCount(count);
					//判断项目是否移交
					if(list!=null && list.size()>0 && list.contains(String.valueOf(bean.getProjectId()))){
						bean.setTransferFlag(1);
					}
				}
				//访谈对象不为空，取访谈对象的历史访谈记录
				else if(!"".equals(bean.getContactName()) && bean.getContactName()!=null){
					detail.setConId(bean.getContactId());
					long count = detailService.queryCount(detail);
					bean.setInterviewCount(count);
				}
				//都不满足，访谈记录为0
				else{
					bean.setInterviewCount(0);
				}
				//拜访标识
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String nowDate = dateFormat.format(new Date());
				Date systime = dateFormat.parse(nowDate);
				Date startTime = dateFormat.parse(bean.getStartTime());
				if (startTime.getTime() > systime.getTime()) {
	                bean.setInterviewFalg(0);
	                bean.setInterviewContent("未访谈");
	            } else if (startTime.getTime() < systime.getTime()) {
	            	bean.setInterviewFalg(1);
	                bean.setInterviewContent("已访谈");
	            }
				
				
			}
			resultBean.setStatus("ok");
			resultBean.setEntity(bean);
		}catch(Exception e){
			log.error(CallonController.class.getName() + "：getCallonDetails",e);
		}
		return resultBean;
	}
	
	/**
	 * 拜访共享列表
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getShareUserList")
	@ResponseBody
	public Object getShareUserList(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		String userId = "";
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);	
			if(paramMap!=null && paramMap.containsKey("userId")){
				userId = CUtils.get().object2String(paramMap.get("userId"));
			}
			
			//调用客户端
			Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
			String url = env.getProperty("power.server") + StaticConst.getShareUserList;
			Map<String,Object> qMap = new HashMap<String,Object>();
			qMap.put("userId", userId);
			JSONArray valueJson=null;
			List<Map<String, Object>> list = null;
			String result = QHtmlClient.get().post(url, headerMap, qMap);
			if("error".equals(result)){
				log.error(CallonController.class.getName() + "_getShareUserList：获取拜访共享列表时出错","此时服务器返回状态码非200");
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
					log.error(CallonController.class.getName() + "_getShareUserList：获取拜访共享列表时出错","服务器返回正常，但是对方添加数据失败");
				}
			}
			resultBean.setMapList(list);
			resultBean.setStatus("OK");
		}catch(Exception e){
			log.error(CallonController.class.getName() + "_getShareUserList",e);
		}
		return resultBean;
	}
	
	//------------------------------- 私有方法，用于新增、更新、删除拜访计划的消息推送的调用操作 --------------------------------//
	private void pusAddCallon(HttpServletRequest request,Long callonId,ScheduleInfo infoBean){
		//调用客户端
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("stars.server") + StaticConst.pushAddSchedule;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("messageType", "1.4.1");
		qMap.put("id", callonId);
		qMap.put("startTime", infoBean.getStartTime());
		qMap.put("isAllday", 0);
		qMap.put("wakeupId", infoBean.getWakeupId());
		
		//此处需要查询数据库，取得联系人的名称再保存
		ContractsBean contractsBean = contractsService.queryById(infoBean.getCallonPerson());
		if(contractsBean!=null){
			qMap.put("schedulePerson", contractsBean.getName());
		}else{
			qMap.put("schedulePerson", "没有找到对应的联系人");
		}
		
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(CallonController.class.getName() + "_save：添加拜访记录-推送消息时出错","此时服务器返回状态码非200");
		}else{
			boolean flag = true;
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson!=null && resultJson.containsKey("result")){
				JSONObject statusJson = resultJson.getJSONObject("result");
				if(statusJson!=null && statusJson.containsKey("status") && "OK".equals(statusJson.getString("status"))){
					flag = false;
				}
			}
			if(flag){
				log.error(CallonController.class.getName() + "_save：添加拜访记录-推送消息时出错","服务器返回正常，但是对方添加数据失败");
			}
		}
	}
	
	private void pushUpdateCallon(HttpServletRequest request,ScheduleInfo infoBean){
		//调用客户端
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("stars.server") + StaticConst.pushUpdateSchedule;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("messageType", "1.4.2");
		qMap.put("id", infoBean.getId());
		qMap.put("startTime", infoBean.getStartTime());
		qMap.put("isAllday", 0);
		qMap.put("wakeupId", infoBean.getWakeupId());
		qMap.put("visitType", "1.4");
		
		//此处需要查询数据库，取得联系人的名称再保存
		ContractsBean contractsBean = contractsService.queryById(infoBean.getCallonPerson());
		if(contractsBean!=null){
			qMap.put("schedulePerson", contractsBean.getName());
		}else{
			qMap.put("schedulePerson", "没有找到对应的联系人");
		}
		
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(CallonController.class.getName() + "_save：修改拜访记录-推送消息时出错","此时服务器返回状态码非200");
		}else{
			boolean flag = true;
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson!=null && resultJson.containsKey("result")){
				JSONObject statusJson = resultJson.getJSONObject("result");
				if(statusJson!=null && statusJson.containsKey("status") && "OK".equals(statusJson.getString("status"))){
					flag = false;
				}
			}
			if(flag){
				log.error(CallonController.class.getName() + "_save：修改拜访记录-推送消息时出错","服务器返回正常，但是对方添加数据失败");
			}
		}
	}
	
	private void pushDeleteCallon(HttpServletRequest request,Long callonId){
		//调用客户端
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("stars.server") + StaticConst.pushDeleteSchedule;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("messageType", "1.4.3");
		qMap.put("id", callonId);
		qMap.put("visitType", "1.4");
		
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(CallonController.class.getName() + "_save：删除拜访记录-推送消息时出错","此时服务器返回状态码非200");
		}else{
			boolean flag = true;
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson!=null && resultJson.containsKey("result")){
				JSONObject statusJson = resultJson.getJSONObject("result");
				if(statusJson!=null && statusJson.containsKey("status") && "OK".equals(statusJson.getString("status"))){
					flag = false;
				}
			}
			if(flag){
				log.error(CallonController.class.getName() + "_save：删除拜访记录-推送消息时出错","服务器返回正常，但是对方添加数据失败");
			}
		}
	}
	
	/**
	 * 获取历史访谈记录个数
	 * @param detail
	 * @return
	 */
	@RequestMapping("getTalkHistoryCounts")
	@ResponseBody
	public Object getTalkHistoryCounts(@RequestBody ScheduleDetailBeanVo detail){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try{
			long count = detailService.getTalkHistoryCounts(detail);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("interviewCount", count);
			resultBean.setStatus("ok");
			resultBean.setMap(map);
		}catch(Exception e){
			log.error(CallonController.class.getName() + "_getTalkHistoryCounts",e);
		}
		return resultBean;
	}

}
