package com.galaxy.im.business.rili.controller;

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
import com.galaxy.im.bean.schedule.ScheduleDict;
import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.business.rili.service.IScheduleDictService;
import com.galaxy.im.business.rili.service.IScheduleService;
import com.galaxy.im.business.rili.util.AccountDate;
import com.galaxy.im.business.rili.util.ScheduleUtil;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.cache.redis.RedisCacheImpl;


@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	private Logger log = LoggerFactory.getLogger(ScheduleController.class);

	@Autowired
	IScheduleService service;
	
	@Autowired
	private IScheduleDictService scheduleDictService;
	
	@Autowired
	IScheduleMessageService messageService;
	
	/**
	 * 时间是否冲突 或 时间冲突数
	 * @param record
	 * @return
	 */
	@RequestMapping("ctSchedule")
	@ResponseBody
	public Object ctSchedule(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			//获取登录用户信息
			SessionBean bean = CUtils.get().getBeanBySession(request);
			Map<String,Object> m = new HashMap<String,Object>();
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			if(map!=null){
				map.put("userId",bean.getGuserid());
				List<Map<String,Object>> list = service.ctSchedule(map);
				if(list!=null && list.size()>0){
					if(list.size()==1){
						Map<String,Object> mm=list.get(0);
						resultBean.setStatus("ok");
						resultBean.setMessage("日程\""+mm.get("name")+"\"");
					}else{
						resultBean.setStatus("ok");
						resultBean.setMessage(list.size()+"个日程");
					}
					m.put("ctCount", list.size());
					resultBean.setMap(m);
				}else{
					resultBean.setStatus("error");
				}
			}
		}catch (Exception e) {
			log.error(ScheduleController.class.getName() + "_ctSchedule",e);
		}
		return resultBean;
	}
	
	/**
	 * 判断日程是否超过20条
	 * @param record
	 * @return
	 */
	@RequestMapping("getCountSchedule")
	@ResponseBody
	public Object getCountSchedule(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			//获取登录用户信息
			SessionBean bean = CUtils.get().getBeanBySession(request);
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			map.put("createdId", bean.getGuserid());
			String ss = service.getCountSchedule(map);
			if(ss==null){
				resultBean.setStatus("OK");
				resultBean.setFlag(0); //未超过20条
			}else{
				resultBean.setStatus("OK");
				resultBean.setFlag(1);
				resultBean.setMessage(ss); //已超过20条
			}
		}catch (Exception e) {
			log.error(ScheduleController.class.getName() + "getCountSchedule",e);
		}
		return resultBean;
		
	}
	
	/**
	 * 添加其他日程
	 */
	@ResponseBody
	@RequestMapping("saveOtherSchedule")
	public Object saveOtherSchedule(@RequestBody ScheduleInfo scheduleInfo,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
		if(scheduleInfo.getName()==null){
			resultBean.setMessage("日程名称不能为空");
			return resultBean;
		}
		try {
			int updateCount = 0;
			Long id = 0L;
			if(scheduleInfo!=null && scheduleInfo.getId()!=null && scheduleInfo.getId()!=0){
				//更新其他日程
				scheduleInfo.setUpdatedId(bean.getGuserid());
				updateCount = service.updateById(scheduleInfo);
				if(updateCount!=0){
					resultBean.setFlag(1);
					resultBean.setStatus("OK");
				}
				//更新其他日程后发消息
				scheduleInfo.setCreatedId(bean.getGuserid());
				scheduleInfo.setUserName(CUtils.get().object2String(user.get("realName")));
				scheduleInfo.setMessageType("1.3.2");
				messageService.operateMessageByUpdateInfo(scheduleInfo, "1.3");
			}else{
				//保存其他日程
				scheduleInfo.setCreatedId(bean.getGuserid());
				id = service.insert(scheduleInfo);
				if(id!=0L){
					resultBean.setFlag(1);
					resultBean.setStatus("OK");
				}
				//创建其他日程后发消息
				scheduleInfo.setMessageType("1.3.1");
				scheduleInfo.setId(id);
				scheduleInfo.setUserName(CUtils.get().object2String(user.get("realName")));
				messageService.operateMessageBySaveInfo(scheduleInfo);
			}
		
		} catch (Exception e) {
			log.error(ScheduleController.class.getName() + "_saveOtherSchedule",e);
		}
		return resultBean;
	}
	
	/**
	 * 删除其他日程
	 */
	@ResponseBody
	@RequestMapping("deleteOtherScheduleById")
	public Object deleteOtherSchedule(@RequestBody String id,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setFlag(0);
		try {
			Map<String,Object> map = CUtils.get().jsonString2map(id);
			ScheduleInfo sInfo = service.queryById(CUtils.get().object2Long(map.get("id")));
			/*Map<String, Object> ss = service.selectOtherScheduleById(map);
			if(ss!=null){
				//判断是否过期
				Long st= DateUtil.stringToLong(CUtils.get().object2String(ss.get("startTime")), "yyyy-MM-dd HH:mm");
				if(System.currentTimeMillis()>st){
					resultBean.setMessage("此日程过期了");
					return resultBean;
				}	
			}*/
			//逻辑删除
			if(map!=null){
				map.put("updatedTime", DateUtil.getMillis(new Date()));
				SessionBean sessionBean = CUtils.get().getBeanBySession(request);
				map.put("updatedId", sessionBean.getGuserid());
				boolean flag = service.deleteOtherScheduleById(map);
				
				if(flag){
					resultBean.setFlag(1);
					resultBean.setStatus("OK");
				}
				//逻辑删除后发消息
				sInfo.setIsDel(1);
				messageService.operateMessageByDeleteInfo(sInfo, "1.3");
			}
		} catch (Exception e) {
			log.error(ScheduleController.class.getName() + "_deleteOtherSchedule",e);
		}
		return resultBean;
	}
	
	/**
	 * 其他日程详情
	 */
	@ResponseBody
	@RequestMapping("selectOtherScheduleById")
	public Object selectOtherScheduleById(@RequestBody String paramString,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
		try {
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			Map<String, Object> mapList = service.selectOtherScheduleById(map);
			if(mapList==null){
				resultBean.setMessage("日称不存在");
				return resultBean;
			}
			//为了防止共享的人查看详情
			if(!mapList.get("createdId").equals(user.get("id"))){
				resultBean.setMessage("没有查看权限");
				return resultBean;				
			}
			if(mapList.get("startTime")!=null){
				
				String starttime = dateStrformat(CUtils.get().object2String(mapList.get("startTime")));
				mapList.put("startTime", starttime);
			}
			if(mapList.get("endTime")!=null){
				
				String endTime = dateStrformat(CUtils.get().object2String(mapList.get("endTime")));
				mapList.put("endTime", endTime);
			}
			if (mapList.get("wakeupId")!=null) {
				ScheduleDict scheduleDict = scheduleDictService.queryById(CUtils.get().object2Long(mapList.get("wakeupId")));
				mapList.put("remind", scheduleDict.getName());
			}
			resultBean.setMap(mapList);;
			resultBean.setStatus("OK");
		} catch (Exception e) {
			log.error(ScheduleController.class.getName() + "_selectOtherScheduleById",e);
		}
		return resultBean;
	}
	
	/**
	 * 查询字典表
	 * @param ScheduleDict
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectScheduleDict")
	public Object selectScheduleDict(@RequestBody ScheduleDict scheduleDict,HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		if(scheduleDict.getDirection()==null){
			scheduleDict.setDirection("asc");
		}
		if(scheduleDict.getProperty()==null){
			scheduleDict.setProperty("index_num");
		}
		try {
			List<ScheduleDict> list = scheduleDictService.queryList(scheduleDict);
			resultBean.setEntity(list);
			resultBean.setStatus("OK");
		} catch (Exception e) {
			log.error(ScheduleController.class.getName() + "_selectScheduleDict",e);
		}
		return resultBean;
	}
	
	/**
	 * 日程条件查询
	 * 按条件  日、周、月      
	 * @param query : {year:2014, month:12, day:12, createdId:111111}
	 *                day != null ： 按日查
	 *                month != null ： 按月查
	 *                year != null ： 按年查
	 *                createdId = null : 查询本人
	*/
	@ResponseBody
	@RequestMapping("querySchedule")
	public Object querySchedule(HttpServletRequest request,@RequestBody ScheduleInfo query){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try {
			@SuppressWarnings("unchecked")
			RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
			//获取登录用户信息
			SessionBean bean = CUtils.get().getBeanBySession(request);
			Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
			
			if(query.getYear()!=null && query.getMonth()!=null){
				String lastMouthDay = AccountDate.getLastDayOfMonth(query.getYear(), query.getMonth());
				query.setLastMouthDay(lastMouthDay);
			}
			if(query.getProperty()==null)  query.setProperty("start_time,created_time"); 
			if(query.getDirection()==null) query.setDirection("asc");
			if(query.getCreatedId()==null) query.setCreatedId(CUtils.get().object2Long(user.get("id")));
			Map<String,String> qtime = DateUtil.getBeginEndTimeStr(query.getYear(),query.getMonth(),query.getDay());
			if(qtime != null){
				query.setBqStartTime(qtime.get("beginTimeStr"));
				query.setBqEndTime(qtime.get("endTimeStr"));
			}
			
			//搜索
			if (query.getName()!=null) {
				Map<String,Object> map = new HashMap<>();
				map.put("name",query.getName());
				map.put("isDel", 0);
				if(query.getCreatedId()==null){
					query.setCreatedId(CUtils.get().object2Long(user.get("id")));
				}
				map.put("createdId", query.getCreatedId());
				List<ScheduleInfo> list = service.getList(map);
				if (list!=null && list.size()>0) {
					resultBean.setEntity(list);
					resultBean.setStatus("OK");
				}else{
					resultBean.setMessage("暂无数据");
					resultBean.setStatus("OK");
				}
				
			}else{
				//结果查询  封装
				List<ScheduleUtil> qList = service.queryAndConvertList(query);
				resultBean.setEntity(qList);
				resultBean.setStatus("OK");
			}
		} catch (Exception e) {
			log.error(ScheduleController.class.getName() + "_querySchedule",e);
		}
		return resultBean;
	}
	
	/**
	 * 转化日期
	 * @param dateStr
	 * @return
	 */
	public static String dateStrformat(String dateStr){  //2016-05-27 16:00:00   19
		
		if( dateStr.indexOf("-") != -1){
			dateStr = dateStr.replaceAll("-", "/");
		}
		String dateStrs = dateStr.substring(0,16);
		
		return dateStrs;
	}
	
	/**
	 * 返回日程/拜访计划的列表用于-同步日历（同步苹果日历）
	 */
	@ResponseBody
	@RequestMapping("syncSchedule")
	public Object syncSchedule(HttpServletRequest request,@RequestBody ScheduleInfo query){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		//获取登录用户信息
		SessionBean bean = CUtils.get().getBeanBySession(request);
		Map<String, Object> user = BeanUtils.toMap(cache.get(bean.getSessionid()));
		try {
			
			if(query.getProperty()==null)  query.setProperty("start_time,created_time"); 
			if(query.getDirection()==null) query.setDirection("asc");
			if(query.getCreatedId()==null) query.setCreatedId(CUtils.get().object2Long(user.get("id")));
			
			String time = query.getDateTime();//特定哪天
			
			Long startDay = CUtils.get().object2Long(query.getStartTime());//前多少天
			Long endDay = CUtils.get().object2Long(query.getEndTime());//后多少天
			
			long startTime = DateUtil.stringToLong(time,"yyyy-MM-dd HH:mm:ss")-(startDay*24*60*60*1000L);
			long endTime = DateUtil.stringToLong(time,"yyyy-MM-dd HH:mm:ss")+(endDay*24*60*60*1000L);
			
			query.setStartTime(DateUtil.longToString(startTime, "yyyy-MM-dd HH:mm:ss"));
			query.setEndTime(DateUtil.longToString(endTime, "yyyy-MM-dd HH:mm:ss"));
			
			//结果查询  封装
			List<ScheduleUtil> qList = service.selectList(query);
			resultBean.setEntity(qList);
			resultBean.setStatus("OK");
		} catch (Exception e) {
			log.error(ScheduleController.class.getName() + "syncSchedule",e);
		}
		return resultBean;
		
	}
}
