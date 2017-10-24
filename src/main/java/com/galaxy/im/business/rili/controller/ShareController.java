package com.galaxy.im.business.rili.controller;

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
import com.galaxy.im.bean.schedule.ScheduleShared;
import com.galaxy.im.business.rili.service.IShareService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;

/**
 * 共享人相关接口
 * @author liuli
 *
 */
@Controller
@RequestMapping("/share")
public class ShareController {
	
	private Logger log = LoggerFactory.getLogger(ShareController.class);
	
	@Autowired
	IShareService service;
	
	/**
	 * 共享给自己的列表
	 * @param record
	 * @return
	 */
	@RequestMapping("querySharedUsers")
	@ResponseBody
	public Object querySharedUsers(HttpServletRequest request,HttpServletResponse response){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			SessionBean bean = CUtils.get().getBeanBySession(request);
			List<Map<String, Object>> mapList = service.querySharedUsers(request,response,bean.getGuserid());
			resultBean.setMapList(mapList);
			resultBean.setStatus("OK");
		}catch (Exception e) {
			log.error(ShareController.class.getName() + "querySharedUsers",e);
		}
		return resultBean;
	}
	
	/**
	 * 自己共享人的列表
	 * @param record
	 * @return
	 */
	@RequestMapping("queryMySharedUsers")
	@ResponseBody
	public Object queryMySharedUsers(HttpServletRequest request,HttpServletResponse response,@RequestBody ScheduleShared query){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			SessionBean bean = CUtils.get().getBeanBySession(request);
			List<Map<String, Object>> mapList = service.queryMySharedUsers(request,response,bean.getGuserid(),query.getToUname());
			resultBean.setMapList(mapList);
			resultBean.setStatus("OK");
		}catch (Exception e) {
			log.error(ShareController.class.getName() + "queryMySharedUsers",e);
		}
		return resultBean;
	}
	
	/**
	 * 添加共享人
	 * @param record
	 * @return
	 */
	@RequestMapping("saveSharedUsers")
	@ResponseBody
	public Object saveSharedUsers(HttpServletRequest request,HttpServletResponse response,@RequestBody ScheduleShared query){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			SessionBean bean = CUtils.get().getBeanBySession(request);
			
			int result = service.saveSharedUsers(bean.getGuserid(),query);
			if(result>0){
				resultBean.setStatus("OK");
				resultBean.setMessage("添加成功");
			}
		}catch (Exception e) {
			log.error(ShareController.class.getName() + "saveSharedUsers",e);
		}
		return resultBean;
		
	}
	
	/**
	 * 删除共享人
	 * @param record
	 * @return
	 */
	@RequestMapping("delSharedUser")
	@ResponseBody
	public Object delSharedUser(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			if(map.containsKey("id")){
				int result = service.delSharedUser(map);
				if(result>0){
					resultBean.setStatus("OK");
					resultBean.setMessage("删除成功");
				}
			}
		}catch (Exception e) {
			log.error(ShareController.class.getName() + "delSharedUser",e);
		}
		return resultBean;
	}
	
	/**
	 * 共享人列表
	 * @param record
	 * @return
	 */
	@RequestMapping("queryDeptUinfo")
	@ResponseBody
	public Object queryDeptUinfo(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		return paramString;
		
	}

	/**
	 * 查询所有事业部下的人
	 * @param record
	 * @return
	 */
	@RequestMapping("queryAppPerson")
	@ResponseBody
	public Object queryAppPerson(HttpServletRequest request,HttpServletResponse response,@RequestBody String paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		resultBean.setStatus("error");
		try {
			SessionBean bean = CUtils.get().getBeanBySession(request);
			Map<String,Object> map = CUtils.get().jsonString2map(paramString);
			if(map.containsKey("uname")){
				List<Map<String, Object>> mapList = service.queryAppPerson(request,response,bean.getGuserid(),map);
				resultBean.setMapList(mapList);
				resultBean.setStatus("OK");
			}
		}catch (Exception e) {
			log.error(ShareController.class.getName() + "queryAppPerson",e);
		}
		return resultBean;
	}

}
