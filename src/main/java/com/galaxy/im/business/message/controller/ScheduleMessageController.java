package com.galaxy.im.business.message.controller;

import java.util.Date;
import java.util.List;
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
			if ( page!=null) {

				List<Map<String,Object>> list =page.getDataList();
				for(Map<String,Object> map :list){
					if(map.containsKey("content")&&map.get("content")!=null){
						String content=CUtils.get().object2String(map.get("content"));
						if(content.contains("time")){
							if(!content.contains(":")){
								String strs[] = content.split("<time>");
								String timsStrs[] = strs[1].split("</time>");
								String newStr=strs[0]+"<time>"+timsStrs[0]+" 00:00"+"</time>"+timsStrs[1];
								map.put("content", newStr);
							}
						}
					}
				}
				resultBean.setStatus("OK");
				resultBean.setEntity(page);
			}
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
			long count = service.updateToRead(paramMap);
			if (count>0) {
				resultBean.setStatus("OK");
			}
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
			int count = service.perMessageToRead(sessionBean.getGuserid());
			if (count>0) {
				resultBean.setStatus("OK");
			}
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
			int count = service.perMessageToClear(sessionBean.getGuserid());
			if (count>0) {
				resultBean.setStatus("OK");
			}else{
				resultBean.setMessage("没有可清除的消息!");
			}
		} catch (Exception e) {
			log.error(ScheduleMessageController.class.getName() + "：perMessageToClear",e);
		}
		return resultBean;
	}

	
}
