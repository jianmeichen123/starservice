package com.galaxy.im.business.rili.controller;

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
import com.galaxy.im.business.rili.service.IScheduleService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/schedule")
public class scheduleController {
	private Logger log = LoggerFactory.getLogger(scheduleController.class);

	@Autowired
	IScheduleService service;
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
			log.error(scheduleController.class.getName() + "_ctSchedule",e);
		}
		return resultBean;
		
	}
}
