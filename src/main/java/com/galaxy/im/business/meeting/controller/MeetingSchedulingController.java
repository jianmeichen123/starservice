package com.galaxy.im.business.meeting.controller;

import java.text.SimpleDateFormat;
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
import com.galaxy.im.bean.meeting.MeetingSchedulingBo;
import com.galaxy.im.business.meeting.service.IMeetingSchedulingService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.QPage;

@Controller
@RequestMapping("/schedule")
public class MeetingSchedulingController {
	private Logger log = LoggerFactory.getLogger(MeetingSchedulingController.class);
	
	@Autowired
	IMeetingSchedulingService service;

	/**
	 * 会议排期列表（搜索，筛选）
	 * @param paramString
	 * @return
	 */
	@RequestMapping("queryMescheduling")
	@ResponseBody
	public Object queryMescheduling(HttpServletRequest request,HttpServletResponse response,@RequestBody MeetingSchedulingBo query ){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if(sessionBean==null){
				resultBean.setMessage("User用户信息在Session中不存在，无法执行项目列表查询！");
				return resultBean;
			}
			if(query.getScheduleStatus()==1){
				 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
				    String dateString = formatter.format(new Date());  
				    query.setStartTime(dateString); 
			}
			QPage page = service.queryMescheduling(query);
			map=page.getDataList();
			if(!map.isEmpty() && map.size()>0){
				for(Map<String, Object> ms : map){
					byte Edit = 1;
					Integer sheduleStatus = CUtils.get().object2Integer(ms.get("scheduleStatus"));
					String  meetingType = CUtils.get().connectString(ms.get("meetingType"));
					if (sheduleStatus == 2 || sheduleStatus == 3) {
						Edit = 0;
					}
					if (ms.get("reserveTimeStart") != null) {
						long time = System.currentTimeMillis();
						long startTime = CUtils.get().object2Date(ms.get("reserveTimeStart")).getTime();
						if ((time > startTime) && sheduleStatus == 1) {
							Edit = 0;
						}
					}
					ms.put("isEdit", Edit);
					if(sheduleStatus==0 && meetingType.equals(StaticConst.MEETING_TYPE_CEO)){
						Long s  = service.selectpdCount(ms);
						String ss = pdcount(s.intValue());
						ms.put("pdCount", ss);
						ms.put("paiQCount", s);
					}else{
						Long s  = service.selectltpdCount(ms);
						String ss = pdcount(s.intValue());
						ms.put("pdCount", ss);
						ms.put("paiQCount", s);
					}
				}
				MeetingSchedulingBo mebo = new MeetingSchedulingBo();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("total", page.getTotal());
				//个数
				Long statusd = service.queryCountscheduleStatusd(query);
				Long statusy = service.queryCountscheduleStatusy(query);
				mebo.setCountscheduleStatusd(statusd);
				mebo.setCountscheduleStatusy(statusy);
				resultBean.setMap(m);
				resultBean.setMapList(map);
				resultBean.setStatus("OK");
				resultBean.setEntity(mebo);
			}else{
				MeetingSchedulingBo mebo = new MeetingSchedulingBo();
				mebo.setCountscheduleStatusd(0L);
				mebo.setCountscheduleStatusy(0L);
				resultBean.setStatus("OK");
				resultBean.setEntity(mebo);
				return resultBean;
			}
		}catch(Exception e){
			log.error(MeetingSchedulingController.class.getName() + "queryMescheduling",e);
		}
		return resultBean;
	}
	
	public static String pdcount(Integer s){
		if(s>=0 && s<=9){
			return "小于10";
		}else if(10<=s && s<=29){
			return "小于30";
		}else if(30<=s&&s<=49){
			return "小于50";
		}else if(50<=s&&s<=99){
			return "大于50";
		}else if(s>100){
			return "大于100";
		}
		return "";
	}
}
