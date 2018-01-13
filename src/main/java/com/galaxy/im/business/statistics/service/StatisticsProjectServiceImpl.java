package com.galaxy.im.business.statistics.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.statistics.MonthProjectDataVO;
import com.galaxy.im.bean.statistics.ScheduleCountVO;
import com.galaxy.im.business.statistics.dao.IStatisticsProjectDao;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;

@Service
public class StatisticsProjectServiceImpl extends BaseServiceImpl<Test> implements IStatisticsProjectService{

	@Autowired
	IStatisticsProjectDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	@Override
	public List<Map<String, Object>> queryCountCareerLineRankProject() {
		Map<String , Integer> topMap = new HashMap<String,Integer>();
		topMap.put("offset", 0);
		topMap.put("limit", 5);
		List<Map<String, Object>> topList = dao.getCountCareerLineRankList(topMap);
		return topList;
	}

	@Override
	public Map<String, Object> querySatisticsProjectOverview(List<String> roleCodeList, long deptId, Long guserid) {		
		Long departId = 0L;
		Long userId = 0L;
		if (roleCodeList.contains(StaticConst.HHR)){
			departId = deptId;
		}else if (roleCodeList.contains(StaticConst.CEO)){
		
		}else if (roleCodeList.contains(StaticConst.TZJL)){
			userId =  guserid;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,Object> insGzjMap = new HashMap<String,Object>();
		if(departId!=0L){
			insGzjMap.put("careerLineId", deptId); 
		}else if (userId!=0L){
			insGzjMap.put("createUid", guserid);
		}
		//外部投资
		insGzjMap.put("projectType", StaticConst.PROJECT_TYPE_2);
		insGzjMap.put("projectStatus", StaticConst.PROJECT_STATUS_0);		
		Long  insGzjNum = dao.getCountProjectNumByParams(insGzjMap);
		map.put("gjzSumInside", insGzjNum);
		
		Map<String,Object> insThyyMap = new HashMap<String,Object>();
		if(departId!=0L){
			insThyyMap.put("careerLineId", deptId); 
		}else if (userId!=0L){
			insThyyMap.put("createUid", guserid);
		}
		insThyyMap.put("projectType", StaticConst.PROJECT_TYPE_2);
		insThyyMap.put("projectStatus", StaticConst.PROJECT_STATUS_1);
		Long  insThyyNum = dao.getCountProjectNumByParams(insThyyMap);
		map.put("thyySumInside", insThyyNum);
		
		Map<String,Object> insYfjMap = new HashMap<String,Object>();
		if(departId!=0L){
			insYfjMap.put("careerLineId", deptId); 
		}else if (userId!=0L){
			insYfjMap.put("createUid", guserid);
		}
		insYfjMap.put("projectType", StaticConst.PROJECT_TYPE_2);
		insYfjMap.put("projectStatus", StaticConst.PROJECT_STATUS_2);
		Long  insYfjNum = dao.getCountProjectNumByParams(insYfjMap);
		map.put("yfjSumInside", insYfjNum);
		
		Map<String,Object> insYtcMap = new HashMap<String,Object>();
		if(departId!=0L){
			insYtcMap.put("careerLineId", deptId); 
		}else if (userId!=0L){
			insYtcMap.put("createUid", guserid);
		}
		insYtcMap.put("projectType", StaticConst.PROJECT_TYPE_2);
		insYtcMap.put("projectStatus", StaticConst.PROJECT_STATUS_3);
		Long  insYtcNum = dao.getCountProjectNumByParams(insYtcMap);
		map.put("ytcSumInside", insYtcNum);
		
		//内部创建
		Map<String,Object> extGzjMap = new HashMap<String,Object>();
		if(departId!=0L){
			extGzjMap.put("careerLineId", deptId); 
		}else if (userId!=0L){
			extGzjMap.put("createUid", guserid);
		}
		extGzjMap.put("projectType", StaticConst.PROJECT_TYPE_1);
		extGzjMap.put("projectStatus", StaticConst.PROJECT_STATUS_0);		
		Long  extGzjNum = dao.getCountProjectNumByParams(extGzjMap);
		map.put("gjzSumExternal", extGzjNum);
		
		Map<String,Object> extThyyMap = new HashMap<String,Object>();
		if(departId!=0L){
			extThyyMap.put("careerLineId", deptId); 
		}else if (userId!=0L){
			extThyyMap.put("createUid", guserid);
		}
		extThyyMap.put("projectType", StaticConst.PROJECT_TYPE_1);
		extThyyMap.put("projectStatus", StaticConst.PROJECT_STATUS_1);
		Long  extThyyNum = dao.getCountProjectNumByParams(extThyyMap);
		map.put("thyySumExternal", extThyyNum);
		
		Map<String,Object> extYfjMap = new HashMap<String,Object>();
		if(departId!=0L){
			extYfjMap.put("careerLineId", deptId); 
		}else if (userId!=0L){
			extYfjMap.put("createUid", guserid);
		}
		extYfjMap.put("projectType", StaticConst.PROJECT_TYPE_1);
		extYfjMap.put("projectStatus", StaticConst.PROJECT_STATUS_2);
		Long  extYfjNum = dao.getCountProjectNumByParams(extYfjMap);
		map.put("yfjSumExternal", extYfjNum);
		
		Map<String,Object> extYtcMap = new HashMap<String,Object>();
		if(departId!=0L){
			extYtcMap.put("careerLineId", deptId); 
		}else if (userId!=0L){
			extYtcMap.put("createUid", guserid);
		}
		extYtcMap.put("projectType", StaticConst.PROJECT_TYPE_1);
		extYtcMap.put("projectStatus", StaticConst.PROJECT_STATUS_3);
		Long  extYtcNum = dao.getCountProjectNumByParams(extYtcMap);
		map.put("ytcSumExternal", extYtcNum);
				
		return map;
	}

	@Override
	public Map<String, Object> queryCountMonthProjectChanged(List<String> roleCodeList, long deptId, Long guserid) {
		Long departId = 0L;
		Long userId = 0L;
		if (roleCodeList.contains(StaticConst.HHR)){
			departId = deptId;
		}else if (roleCodeList.contains(StaticConst.CEO)){
		
		}else if (roleCodeList.contains(StaticConst.TZJL)){
			userId =  guserid;
		}
		Map<String,Object> queryMap = new HashMap<String,Object>();
		if(departId!=0L){
			queryMap.put("projectDepartId", deptId); 
		}else if (userId!=0L){
			queryMap.put("createUid", guserid);
		}
		Map<String, Long> resultMap = queryCountProjectSum(queryMap); 
		Long monthSum = (Long)resultMap.get("monthTotal");
		Long lastMonthSum = (Long)resultMap.get("lastMonthTotal");		
		Double lastMonthTotal$D = new Double(lastMonthSum.toString());
		
		MonthProjectDataVO mpView = new MonthProjectDataVO();
		if(lastMonthSum!=0){			
			mpView.setNewProjectSum(monthSum);	      
			Double  rateChange =  (monthSum-lastMonthSum) /  lastMonthTotal$D * 100 ;	 
			String rc = new java.text.DecimalFormat("0.00").format(rateChange);
			rateChange = Double.valueOf(rc);
			if(lastMonthSum.longValue()==monthSum.longValue()){
				mpView.setNpUnchanged(true);
				mpView.setNpResult("环比持平");
			}else if(lastMonthSum>monthSum){
				mpView.setNpCf(rateChange);
				mpView.setNpResult("环比减少" + rateChange + "%");
			}else if (monthSum>lastMonthSum){
				mpView.setNpRg(rateChange);
				mpView.setNpResult("环比增长"  + rateChange + "%");
			}
		}else{
			mpView.setNewProjectSum(monthSum);  	      
		    mpView.setNpSpecialValue(true); 
		    mpView.setNpResult("");
		}
	     
		queryMap.put("projectType", StaticConst.PROJECT_TYPE_2);
	    Map<String, Long> insResultMap = queryCountProjectSum(queryMap); 
		Long insMonthSum = (Long)insResultMap.get("monthTotal");
		Long insLastMonthSum = (Long)insResultMap.get("lastMonthTotal");
		Double insLastMonthTotal$D = new Double(insLastMonthSum.toString());		
		     
		if(insLastMonthSum!=0){
			mpView.setInsideNewProjectSum(insMonthSum); 
		    Double  insRateChange =  (insMonthSum-insLastMonthSum)  /  insLastMonthTotal$D * 100 ;
		    String irc = new java.text.DecimalFormat("0.00").format(insRateChange);
		    insRateChange = Double.valueOf(irc);
		    if(insLastMonthSum.longValue()==insMonthSum.longValue()){
		    	mpView.setiNpUnchanged(true);
		    	mpView.setiNpResult("环比持平");
		    }else if(insLastMonthSum>insMonthSum){
		    	mpView.setiNpCf(insRateChange);
		    	mpView.setiNpResult("环比减少" + insRateChange + "%");
		    }else if (insMonthSum>insLastMonthSum){
		    	mpView.setiNpRg(insRateChange);
		    	mpView.setiNpResult("环比增长"  + insRateChange + "%");
		    }
		}else{
			mpView.setInsideNewProjectSum(insMonthSum);
			mpView.setInpSpecialValue(true);
			mpView.setiNpResult("");
		}
		     		    
		  queryMap.remove("projectType");
		  queryMap.put("projectType", StaticConst.PROJECT_TYPE_1);
	      Map<String, Long> extResultMap = queryCountProjectSum(queryMap); 
			Long extMonthSum = (Long)extResultMap.get("monthTotal");
			Long extLastMonthSum = (Long)extResultMap.get("lastMonthTotal");
			Double extLastMonthTotal$D = new Double(extLastMonthSum.toString());					
		     
			if(extLastMonthSum!=0){
				mpView.setExternalNewProjectSum(extMonthSum);
			      Double  extRateChange =  (extMonthSum-extLastMonthSum)  /  extLastMonthTotal$D * 100 ;			      
			      String erc = new java.text.DecimalFormat("0.00").format(extRateChange);
			      extRateChange = Double.valueOf(erc);
			      if(extLastMonthSum.longValue()==extMonthSum.longValue()){
			    	  mpView.seteNpUnchanged(true);
			    	  mpView.seteNpResult("环比持平");
			      }else if(extLastMonthSum>extMonthSum){
			    	  mpView.seteNpCf(extRateChange);
			    	  mpView.seteNpResult("环比减少" + extRateChange + "%");
			      }else if (extMonthSum>extLastMonthSum){
			    	  mpView.seteNpRg(extRateChange);
			    	  mpView.seteNpResult("环比增长"  + extRateChange + "%");
			      }
			}else{
				mpView.setExternalNewProjectSum(extMonthSum);
				mpView.setEnpSpecialValue(true);
				mpView.seteNpResult("");
			}		      
	    return BeanUtils.toMap(mpView);
	}

	private Map<String, Long> queryCountProjectSum(Map<String, Object> queryMap) {
		Calendar curCal = Calendar.getInstance();
		 curCal.set(Calendar.DAY_OF_MONTH, 1);
		 curCal.set(Calendar.HOUR_OF_DAY, 0);
		 curCal.set(Calendar.SECOND,0);
		 curCal.set(Calendar.MINUTE,0);
		 curCal.set(Calendar.MILLISECOND,0);
	     Date beginTime = curCal.getTime();
	     
	     long firstDayMonth = beginTime.getTime();
	    
	     Calendar nowHms = Calendar.getInstance();
	     Date nowDate = nowHms.getTime();
	     long todayMonth = nowDate.getTime();
	     
	     int ￥hour = nowHms.get(Calendar.HOUR_OF_DAY);	        
	      int ￥minute = nowHms.get(Calendar.MINUTE);	     	     
	      int ￥second = nowHms.get(Calendar.SECOND);	
	      int ￥milliseSecond = nowHms.get(Calendar.MILLISECOND);	 
	     
	     queryMap.put("startTime", firstDayMonth); 
	     queryMap.put("endTime", todayMonth);      
		 Long  monthTotal = dao.getCountMonthProject(queryMap);
		 
		  Calendar surcl = Calendar.getInstance();
		  surcl.add(Calendar.MONTH, -1);
		  surcl.set(Calendar.DATE, 1);
		  surcl.set(Calendar.HOUR_OF_DAY, 0);
		  surcl.set(Calendar.MINUTE,0);
		  surcl.set(Calendar.SECOND,0);		  
		  surcl.set(Calendar.MILLISECOND,0);
		  Date sgy = surcl.getTime();
		  long firstDayLastMonth = sgy.getTime();
		  	     
		  Calendar c = Calendar.getInstance();
		  int datenum=c.get(Calendar.DATE); 	    		     
		  Calendar cl = Calendar.getInstance();
		  cl.add(Calendar.MONTH, -1);
		  cl.set(Calendar.DATE, datenum);
		  //		      		    
		  cl.set(Calendar.HOUR_OF_DAY, ￥hour);
		  cl.set(Calendar.MINUTE,￥minute);
		  cl.set(Calendar.SECOND,￥second);		 
		  cl.set(Calendar.MILLISECOND,￥milliseSecond);		  
		  //
		  Date sgyCl = cl.getTime();
		  long todayLastMonth = sgyCl.getTime();
		    
		  queryMap.remove("startTime");
		  queryMap.remove("endTime");
		  queryMap.put("startTime", firstDayLastMonth); 
		  queryMap.put("endTime", todayLastMonth);      
	      Long  lastMonthTotal = dao.getCountMonthProject(queryMap);
	      queryMap.remove("startTime");
		  queryMap.remove("endTime");
	      
	     Map<String , Long> resultmap = new HashMap<String,Long>();
	     resultmap.put("monthTotal", monthTotal);
	     resultmap.put("lastMonthTotal", lastMonthTotal);
	      
	      return resultmap;
	}

	@Override
	public List<ScheduleCountVO> queryTZJLScheduleCount(Long guserid) {
		List<ScheduleCountVO> list  = dao.queryTZJLScheduleCount(guserid);
		return afterCountQuery(list);
	}

	@Override
	public List<ScheduleCountVO> queryHHRScheduleCount(Long guserid) {
		List<ScheduleCountVO> list  =  dao.queryHHRScheduleCount(guserid);
		return afterCountQuery(list);
	}

	@Override
	public List<ScheduleCountVO> queryCEOScheduleCount(Long guserid) {
		 List<ScheduleCountVO> list = dao.queryCEOScheduleCount(guserid);
		 return afterCountQuery(list);
	}

	private List<ScheduleCountVO> afterCountQuery(List<ScheduleCountVO> list) {
		List<String> typeList= new ArrayList<String>();
		List<String> contantsList = new ArrayList<String>();
		contantsList.add(StaticConst.MEETING_TYPE_CEO);
		contantsList.add(StaticConst.MEETING_TYPE_APPROVAL);
		contantsList.add(StaticConst.MEETING_TYPE_INVEST);
		
		if(!list.isEmpty()){
			 for(ScheduleCountVO vo:list){
				 typeList.add(vo.getMeetingType());
		     }
		}
		@SuppressWarnings("unchecked")
		List<String> sublist = ListUtils.subtract(contantsList,typeList);
		for(String type:sublist){
			ScheduleCountVO vo = new ScheduleCountVO();
			vo.setMeetingType(type);
			vo.setNumber(0);
			list.add(vo);
		}
		return list; 
	}
	
	@Override
	public List<Map<String, Object>> selectStageSummary(Map<String, Object> map1) {
		return dao.selectStageSummary(map1);
	}
	
	

}
