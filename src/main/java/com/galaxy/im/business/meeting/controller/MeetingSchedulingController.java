package com.galaxy.im.business.meeting.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
			query.setCreatedId(sessionBean.getGuserid());
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
	
	
	
	/**
	 * 查询会议排期的日历页面
	 */
	@ResponseBody
	@RequestMapping("queryMeetSchedulingrl")
	public Object queryMeetSchedulingrl(HttpServletRequest request, @RequestBody MeetingSchedulingBo query){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try {
			Map<String, Object> depmap = new HashMap<>();
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if(sessionBean==null){
				resultBean.setMessage("User用户信息在Session中不存在，无法执行项目列表查询！");
				return resultBean;
			}
			
			SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
			if(query.getYear()!=null&&query.getMonth()!=null){
				//获取传过来的年月的第一天
				Date d = getFirstDayOfMonth(Integer.valueOf(query.getYear()),Integer.valueOf(query.getMonth())-1);
				query.setStartTime(ss.format(d));
				//获取传过来的年月的最后一天
				Date f = getLastDayOfMonth(Integer.valueOf(query.getYear()),Integer.valueOf(query.getMonth())-1);
				query.setEndTime(ss.format(f));		
			}
			
			if (query.getUid()==null && query.getProjectDepartid()==null) {
				//需要进新接口  数目未排期的数目
				MeetingSchedulingBo mBo = new MeetingSchedulingBo();
				mBo.setScheduleStatus(0);
				Long iu = service.selectdpqCount(mBo);
				resultBean.setId(iu);//当是秘书登录时产生的待排期会议的总个数
			}
			
			
			List<MeetingSchedulingBo> listmb = service.selectMonthScheduling(query);
			for(MeetingSchedulingBo mo :listmb){						 						
				 String aa = ss.format(mo.getReserveTimeStart());
				 if(!depmap.containsKey(aa)){
					 Map<String, Object> ms = new HashMap<>();
					 query.setDayTime(aa);
					 query.setMeetingType(StaticConst.MEETING_TYPE_APPROVAL);							 
					 Long y = service.selectMonthSchedulingCount(query);
					 ms.put("lxh", StaticConst.MEETING_TYPE_APPROVAL);
					 ms.put("lxhCount", y);
					 query.setMeetingType(StaticConst.MEETING_TYPE_INVEST);
					 Long k = service.selectMonthSchedulingCount(query);
					 ms.put("tjh", StaticConst.MEETING_TYPE_INVEST);
					 ms.put("tjhCount", k);
					 query.setMeetingType(StaticConst.MEETING_TYPE_CEO);
					 Long g = service.selectMonthSchedulingCount(query);
					 ms.put("ceops", StaticConst.MEETING_TYPE_CEO);
					 ms.put("ceopsCount", g);
					 depmap.put(aa, ms);
				 }
			}
			
			//获取初始的当日事项
			MeetingSchedulingBo bop = new MeetingSchedulingBo();
			String bb = ss.format(new Date());
			bop.setDateTime(bb);
			if(query.getUid()!=null){
				bop.setUid(query.getUid());
			}
			if(query.getProjectDepartid()!=null){
				bop.setProjectDepartid(query.getProjectDepartid());
			}
			List<MeetingSchedulingBo> lisb = service.selectDayScheduling(bop);
			
			resultBean.setStatus("OK");
			resultBean.setEntity(lisb);
			resultBean.setMap(depmap);
		} catch (Exception e) {
			log.error(MeetingSchedulingController.class.getName() + "queryMeetSchedulingrl",e);
		}
		return resultBean;
	}
	
	/**
	 * 排期日历的当日事项
	 * @param request
	 * @param query
	 * @return 传入的是当日时间的string类型 dateTime "yyyy-MM-dd"
	 */
	@ResponseBody
	@RequestMapping("selectDayScheduling")
	public Object selectDayScheduling(HttpServletRequest request, @RequestBody MeetingSchedulingBo query){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try {
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			if(sessionBean==null){
				resultBean.setMessage("User用户信息在Session中不存在，无法执行项目列表查询！");
				return resultBean;
			}
			//获取初始的当日事项
			List<MeetingSchedulingBo> lisb = service.selectDayScheduling(query);
			resultBean.setStatus("OK");
			resultBean.setEntity(lisb);
		} catch (Exception e) {
			log.error(MeetingSchedulingController.class.getName() + "selectDayScheduling",e);
		}
		return resultBean;
	}
	
	
	
	//获取指定年月的第一天
	 public static Date getFirstDayOfMonth(Integer year, Integer month) {
	        Calendar calendar = Calendar.getInstance();
	        if (year == null) {
	            year = calendar.get(Calendar.YEAR);
	        }
	        if (month == null) {
	            month = calendar.get(Calendar.MONTH);
	        }
	        calendar.set(year, month, 1);
	        return calendar.getTime();
	    }
	//获取指定年月的最后一天
	 public static Date getLastDayOfMonth(Integer year, Integer month) {
	        Calendar calendar = Calendar.getInstance();
	        if (year == null) {
	            year = calendar.get(Calendar.YEAR);
	        }
	        if (month == null) {
	            month = calendar.get(Calendar.MONTH);
	        }
	        calendar.set(year, month, 1);
	        calendar.roll(Calendar.DATE, -1);
	        return calendar.getTime();
	    }


}
