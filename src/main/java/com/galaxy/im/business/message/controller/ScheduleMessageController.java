package com.galaxy.im.business.message.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.business.message.service.IScheduleMessageService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.db.QPage;
/**
 * 日程消息
 */
@Controller
@RequestMapping("/galaxy/schedule/message")
public class ScheduleMessageController {
	Logger log = LoggerFactory.getLogger(ScheduleMessageController.class);
	
	@Autowired
	private IScheduleMessageService service;
	
	
	/**
	 * 个人消息 列表查询
     */
	@ResponseBody
	@RequestMapping("/querySchedule")
	public Object querySchedule(HttpServletRequest request,@RequestBody String paramString ){
		
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			/**
			 * 从session中获取用户信息
			 */
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			//结果查询  封装
			paramMap.put("userId", sessionBean.getGuserid());
			paramMap.put("direction", "desc");
			QPage page = service.queryPerMessAndConvertPage(paramMap);
			Integer count = service.selectMuserAndMcontentCount(paramMap);
			if(count!=null && count !=0){
				//代表是 有未读的
				Map<String, Object> map = new HashMap<>();
				map.put("messageisRead", 0);
				resultBean.setMap(map);
			}
			resultBean.setStatus("OK");
			resultBean.setEntity(page);
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：querySchedule",e);
		}
		return resultBean;
	}
	
	/**
	 * 消息 已读
	 */
	@ResponseBody
	@RequestMapping("/toRead")
	public Object toRead(@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<>();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		try {
			paramMap.put("isRead", 1);
			paramMap.put("updatedTime", new Date().getTime());
			service.updateToRead(paramMap);
			resultBean.setStatus("OK");
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：toRead",e);
		}
		return resultBean;
	}
	
	/**
	 * 个人消息列表  设为全部已读
	 * 1.查询出 消息user 表中      个人的   可用   未读  未删除  的数据
	 * 2.查询出消息内容列表          状态为可用的消息
     */
	@ResponseBody
	@RequestMapping("perMessageToRead")
	public Object perMessageToRead(HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			/**
			 * 从session中获取用户信息
			 */
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			service.perMessageToRead(sessionBean.getGuserid());
			resultBean.setStatus("OK");
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：perMessageToRead",e);
		}
		return resultBean;
	}
	
	/**
	 * 个人消息列表  清空
	 * 1.查询出 消息user 表中      个人的  未删除  的数据
	 * 2.查询出消息内容列表          状态为可用的消息
     */
	@ResponseBody
	@RequestMapping("/perMessageToClear")
	public Object perMessageToClear(HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>();
		try {
			/**
			 * 从session中获取用户信息
			 */
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			service.perMessageToClear(sessionBean.getGuserid());
			resultBean.setStatus("OK");
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：perMessageToClear",e);
		}
		return resultBean;
	}

	
}
