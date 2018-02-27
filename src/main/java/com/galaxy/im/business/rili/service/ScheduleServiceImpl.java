package com.galaxy.im.business.rili.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.callon.service.CallonServiceImpl;
import com.galaxy.im.business.rili.dao.IScheduleDao;
import com.galaxy.im.business.rili.util.AccountDate;
import com.galaxy.im.business.rili.util.ComparatorDate;
import com.galaxy.im.business.rili.util.ScheduleUtil;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class ScheduleServiceImpl extends BaseServiceImpl<ScheduleInfo> implements IScheduleService {

	private Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Autowired
	IScheduleDao dao;
	
	@Override
	protected IBaseDao<ScheduleInfo, Long> getBaseDao() {
		return dao;
	}
	
	/**
	 * 时间冲突
	 */
	@Override
	public List<Map<String, Object>> ctSchedule(Map<String, Object> map) {
		try{
			String bqEndTime = "";
			String eqStartTime ="";
			long isAllday = 0;
			if(map!=null && map.containsKey("endTime")){
				bqEndTime = CUtils.get().object2String(map.get("endTime"), "");
			}
			if(map!=null && map.containsKey("startTime")){
				eqStartTime = CUtils.get().object2String(map.get("startTime"), "");
			}
			if(map!=null && map.containsKey("isAllday")){
				isAllday = CUtils.get().object2Long(map.get("isAllday"), 0L);
			}
			
			if("".equals(bqEndTime) ||isAllday == 1){
				bqEndTime = eqStartTime + " 23:59";
			}

			map.put("bqEndTime", bqEndTime);
			map.put("eqStartTime", eqStartTime);
			map.put("sbTimeForAllday", eqStartTime.substring(0, 10));
			map.put("seTimeForAllday", bqEndTime.substring(0, 10));
			map.put("isDel",0);
			
			List<Map<String, Object>> list = dao.getCTSchedule(map);
			return list;
		}catch(Exception e){
			log.error(ScheduleServiceImpl.class.getName() + "_ctSchedule",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 是否超过20条
	 */
	@Override
	public String getCountSchedule(Map<String, Object> map) {
		try{
			String content = null;
			String bqEndTime = "";
			String eqStartTime ="";
			if(map!=null && map.containsKey("startTime")){
				String ss = CUtils.get().object2String(map.get("startTime")).replace("/", "-").substring(0, 10);
				bqEndTime = ss+" 23:59:59";
				eqStartTime = ss+" 00:00:00";
			}
			//非全天
			if(map!=null && map.containsKey("id")){
				map.put("idIsNotEq", CUtils.get().object2Long(map.get("id")));
			}
			map.put("bqEndTime", bqEndTime);
			map.put("eqStartTime", eqStartTime);
			map.put("isAllday", 0);
			map.put("isDel",0);
			List<Map<String, Object>> list0 = dao.getCountSchedule(map);
			//全天
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("createdId", map.get("createdId"));
			if(map!=null && map.containsKey("id")){
				map1.put("idIsNotEq", CUtils.get().object2Long(map.get("id")));
			}
			map1.put("bqEndTime", bqEndTime);
			map1.put("bqStartTime", eqStartTime);
			map1.put("isAllday", 1);
			map1.put("isDel",0);
			List<Map<String, Object>> list1 = dao.getCountSchedule(map1);
			
			list0.addAll(list1);
			if(list0 != null && !list0.isEmpty()){
				if(list0.size() >=20){
					content = "您每天最多可创建20条日程";				
				}
			}
			return content;
		}catch(Exception e){
			log.error(ScheduleServiceImpl.class.getName() + "getCountSchedule",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 删除日程
	 */
	@Override
	public boolean deleteOtherScheduleById(Map<String, Object> paramMap) {
		try{
			return (dao.delCallonById(paramMap)>0);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据id查询详情
	 */
	@Override
	public Map<String, Object> selectOtherScheduleById(Map<String, Object> map) {
		try{
			return dao.selectOtherScheduleById(map);
		}catch(Exception e){
			log.error(CallonServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	/** 
	* 日历结果分组
	* 上中下午分组
	*    深夜 00:00-05:59 d
	*    上午 06:00-11:59 c
	*    下午 12:00-17:59 b
	*    晚上 18:00-23:59 a
	* 日分组
	* 月分组
	 * @throws ParseException 
	*/
	@Override
	public List<ScheduleUtil> queryAndConvertList(ScheduleInfo query) throws ParseException {
		List<ScheduleUtil> resultList = new ArrayList<ScheduleUtil>();
	
		
		//结果查询  封装
		List<ScheduleInfo> qList = null;
		

		//有隔日的日程list
		List<ScheduleInfo> scheduleInfoList = new ArrayList<ScheduleInfo>();
		
		//有隔日的日程重新封装list按年月查询
		if(query.getDay()==null){
			
			String bqEndTime = query.getBqEndTime();
			String eqStartTime = query.getBqStartTime();
			ScheduleInfo toQ = new ScheduleInfo();
			
			toQ.setQueryForMounth("1");
			toQ.setBqEndTime(bqEndTime);
			toQ.setEqStartTime( eqStartTime);
			
			
			toQ.setDate(DateUtil.convertStringToDate(bqEndTime));
			
			toQ.setSbTimeForAllday(eqStartTime.substring(0, 10));
			toQ.setSeTimeForAllday(bqEndTime.substring(0, 10));
			
			toQ.setCreatedId(query.getCreatedId());
			toQ.setProperty(query.getProperty());
			toQ.setDirection(query.getDirection());
			//增加判断是否删除
			toQ.setIsDel(0);
			
			qList = dao.selectLists(toQ);//--------------------------------------------------------------
			if(qList!=null && !qList.isEmpty()){
				ScheduleInfo sinfo ;
				for(ScheduleInfo temp : qList){	
					if(temp.getStartTime()!=null && temp.getEndTime()!=null && !AccountDate.get(temp.getStartTime(),temp.getEndTime())){														
						Integer mouths = CUtils.get().object2Integer(query.getMonth());
						//跨日日程的操作
						List<String> sss = AccountDate.getXiuEveryday(temp.getStartTime().substring(0, 10),temp.getEndTime().substring(0, 10), query.getLastMouthDay());
						for(String ss:sss){
							if(CUtils.get().object2Integer(ss.substring(5, 7))==mouths){
								sinfo = new ScheduleInfo();
								if(temp.getStartTime().substring(0, 10).equals(ss)){
									sinfo.setStartTime(temp.getStartTime());
									sinfo.setEndTime(ss+" 23:59:00");
									sinfo.setName(temp.getName());
									sinfo.setId(temp.getId());
									sinfo.setType(temp.getType());
									sinfo.setCreatedId(temp.getCreatedId());
									sinfo.setUpdatedId(temp.getUpdatedId());
									sinfo.setIsAllday(temp.getIsAllday());
									sinfo.setRemark(temp.getRemark());
									sinfo.setCreatedTime(temp.getCreatedTime());
									sinfo.setUpdatedTime(temp.getUpdatedTime());
								}
								else if(temp.getEndTime().substring(0, 10).equals(ss)){
									sinfo.setStartTime(ss+" 00:00:00");
									sinfo.setEndTime(temp.getEndTime());
									sinfo.setName(temp.getName());
									sinfo.setId(temp.getId());	
									sinfo.setType(temp.getType());
									sinfo.setCreatedId(temp.getCreatedId());
									sinfo.setUpdatedId(temp.getUpdatedId());
									sinfo.setIsAllday(temp.getIsAllday());
									sinfo.setRemark(temp.getRemark());
									sinfo.setCreatedTime(temp.getCreatedTime());
									sinfo.setUpdatedTime(temp.getUpdatedTime());
								}else{				
									sinfo.setStartTime(ss+" 00:00:00");
									sinfo.setEndTime(ss+" 23:59:00");
									sinfo.setName(temp.getName());
									sinfo.setId(temp.getId());
									sinfo.setType(temp.getType());
									sinfo.setCreatedId(temp.getCreatedId());
									sinfo.setUpdatedId(temp.getUpdatedId());
									sinfo.setIsAllday(temp.getIsAllday());
									sinfo.setRemark(temp.getRemark());
									sinfo.setCreatedTime(temp.getCreatedTime());
									sinfo.setUpdatedTime(temp.getUpdatedTime());
									
								}	
								scheduleInfoList.add(sinfo);
								
							}
						}
					}
					
				}
				
			}		
			qList.addAll(scheduleInfoList);
			Iterator<ScheduleInfo> it = qList.iterator();
			while(it.hasNext()){
				ScheduleInfo x = it.next();
			    if(x.getStartTime()!=null && x.getEndTime()!=null && !AccountDate.get(x.getStartTime(),x.getEndTime())){
			        it.remove();
			    }		    
			}
		}
		//按天查询时日程跨日的情况
		if(query.getDay()!=null){
			
			ScheduleInfo scheduleInfo = new ScheduleInfo();
			ScheduleInfo sInfo = new ScheduleInfo();
			
			String bqEndTime = query.getBqEndTime();
			String eqStartTime = query.getBqStartTime();
			
			scheduleInfo.setDate(DateUtil.convertStringToDate(bqEndTime));

			scheduleInfo.setBqEndTime(bqEndTime);
			scheduleInfo.setEqStartTime(eqStartTime);
			scheduleInfo.setCreatedId(query.getCreatedId());
			//增加判断是否删除
			scheduleInfo.setIsDel(0);
			scheduleInfo.setProperty(query.getProperty());
			scheduleInfo.setDirection(query.getDirection());
			
			qList = dao.selectLists(scheduleInfo);//-----------------------------------------------------------
			
			sInfo.setBqEndTime(bqEndTime);
			sInfo.setBqStartTime(eqStartTime);
			sInfo.setIsAllday((byte) 1);
			sInfo.setCreatedId(query.getCreatedId());
			//增加判断是否删除
			sInfo.setIsDel(0);
			//----------------------------------------------------------------------------------------
			scheduleInfoList=dao.selectLists(sInfo);
			
			
			String sd = query.getBqEndTime().substring(0, 10);
			if(qList!=null && !qList.isEmpty()){
				
				for(ScheduleInfo temp : qList){	
					if(temp.getStartTime()!=null && temp.getEndTime()!=null){
						if(!temp.getStartTime().substring(0, 10).equals(sd)){
							
							temp.setStartTime(sd+" 00:00:00");
							
							
						}
						if(!temp.getEndTime().substring(0, 10).equals(sd)){							
							temp.setEndTime(sd+" 23:59:59");
	
						}
						
					}

					
				}
			}
			qList.addAll(scheduleInfoList);
				
		}

		//获取拜访对象得名称重新封装数据

		
		//结果封装
		if(qList!=null && !qList.isEmpty()){
			
			Map<String,List<ScheduleInfo>> dateKey_infos = new HashMap<String,List<ScheduleInfo>>();
			
			if(query.getDay()!=null && query.getMonth()!=null && query.getYear()!=null){ // 年月日  按日的00-24点查询， 上中下午分组
				Long _time = (long)6  * 60 * 60 * 1000 ;
				
				Long  t1_b = DateUtil.convertHMSToDateTime(query.getYear(), query.getMonth(), query.getDay(), 0, 0, 0); 
				Long  t2_b = t1_b + _time ;
				Long  t3_b = t2_b + _time ;
				Long  t4_b = t3_b + _time ;
				
				//Long group[] = { t4_b, t3_b, t2_b, t1_b };
				
				String code = null;
				
				for(ScheduleInfo temp : qList){
					//2017/5/11为了 获取访谈对象得名称
					if(temp.getType()==2){
						//---------------------------------------------------------------
						ScheduleInfo ss = dao.selectVisitNameById(temp.getId());
						if(ss!=null && ss.getSchedulePerson()!=null){
							temp.setSchedulePerson(ss.getSchedulePerson());
						}
					}
					if(temp.getStartTime()!=null){
						if(temp.getIsAllday()!=0 && temp.getIsAllday()==1){
							code = "e" ;
						}else if(temp.getStartTime()!=null && temp.getEndTime()!=null && temp.getStartTime().indexOf("00:00:00")!=-1 && temp.getEndTime().indexOf("23:59:59")!=-1  ){
							code = "e" ;
						}
						else {						
							Long dateKeyLong = DateUtil.stringToLong(temp.getStartTime(), "yyyy-MM-dd HH:mm:ss");
							if(dateKeyLong>=t4_b){
								code = "a" ;
							}else if(dateKeyLong>=t3_b){
								code = "b" ;
							}else if(dateKeyLong>=t2_b){
								code = "c" ;
							}else if(dateKeyLong>=t1_b){
								code = "d" ;
							}
						}
						if(dateKey_infos.containsKey(code)){
							dateKey_infos.get(code).add(temp);
						}else{
							List<ScheduleInfo> tempInfos = new ArrayList<ScheduleInfo>();
							tempInfos.add(temp);
							dateKey_infos.put(code, tempInfos);
						}
					}
				}
			}else if (query.getDay()==null){
				String format = null;
				if( query.getMonth()!=null && query.getYear()!=null){    // 年月   按月的1-31号查询， 日分组
					format = "yyyy-MM-dd";
				}else if( query.getMonth()==null && query.getYear()!=null){ // 年   按月1-12月查询，月分组
					format = "yyyy-MM";
				}
				
				for(ScheduleInfo temp : qList){
					//2017/5/11为了 获取访谈对象得名称
					if(temp.getType()==2){
						
						ScheduleInfo ss =dao.selectVisitNameById(temp.getId());
						
						if(ss!=null && ss.getSchedulePerson()!=null){
							temp.setSchedulePerson(ss.getSchedulePerson());
						}
					}
					//2017/4/17号修改 报空指针
					String stime= temp.getStartTime().substring(0,format.length());
					//String dateKey = DateUtil.dateFormat(temp.getStartTime(), format);
					String dateKey = DateUtil.dateFormat(stime, format);
					if(dateKey_infos.containsKey(dateKey)){
						dateKey_infos.get(dateKey).add(temp);
					}else{
						List<ScheduleInfo> tempInfos = new ArrayList<ScheduleInfo>();
						tempInfos.add(temp);
						dateKey_infos.put(dateKey, tempInfos);
					}
				}
			}
			for(Map.Entry<String, List<ScheduleInfo>> tempE : dateKey_infos.entrySet()){
				ScheduleUtil au = new ScheduleUtil();
				au.setDateKey(tempE.getKey());
				
				ComparatorDate ss = new ComparatorDate();
				Collections.sort(tempE.getValue(),ss);
				
				au.setSchedules(tempE.getValue());
				resultList.add(au);
			}
		}

		return resultList;
	}

	/**
	 * 搜索拜访对象/其他日程
	 */
	@Override
	public List<ScheduleInfo> getList(Map<String, Object> map) {
		try{
			return dao.getList(map);
		}catch(Exception e){
			log.error(ScheduleServiceImpl.class.getName() + "_",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 日程消息未读个数
	 */
	@Override
	public Long queryProjectScheduleCount(Long uid) {
		try {
			return dao.queryProjectScheduleCount(uid);
		} catch (Exception e) {
			log.error(ScheduleServiceImpl.class.getName() + "queryProjectScheduleCount",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 星河投日历同步
	* @Title: selectList  
	* @author xiaochuang 
	* @throws
	 */
	@Override
	public List<ScheduleUtil> selectList(ScheduleInfo query) throws ParseException{
		List<ScheduleUtil> resultList = new ArrayList<ScheduleUtil>();
		//结果查询  封装
		List<ScheduleInfo> qList = null;
		//有隔日的日程list
		List<ScheduleInfo> scheduleInfoList = new ArrayList<ScheduleInfo>();
		//有隔日的日程重新封装list按年月查询
			ScheduleInfo toQ = new ScheduleInfo();
			toQ.setStartTime(query.getStartTime());
			toQ.setEndTime(query.getEndTime());
			toQ.setCreatedId(query.getCreatedId());
			toQ.setProperty(query.getProperty());
			toQ.setDirection(query.getDirection());
			//增加判断是否删除
			toQ.setIsDel(0);
			
			qList = dao.selectListss(toQ);//--------------------------------------------------------------
			if(qList!=null && !qList.isEmpty()){
				ScheduleInfo sinfo ;
				for(ScheduleInfo temp : qList){	
					if(temp.getStartTime()!=null && temp.getEndTime()!=null && !AccountDate.get(temp.getStartTime(),temp.getEndTime())){														
						//跨日日程的操作
						List<String> sss = AccountDate.getXiuEveryday(temp.getStartTime().substring(0, 10),temp.getEndTime().substring(0, 10), query.getLastMouthDay());
						Date d1 =DateUtil.convertStringtoD(query.getStartTime()+" 00:00:00");
						Date d2 = DateUtil.convertStringtoD(query.getEndTime()+" 00:00:00");
						List<String> s1 = new ArrayList<>();
						for(int i =0;i<sss.size();i++){
							Date d3 = DateUtil.convertStringtoD(sss.get(i)+" 00:00:00");
							if (d3.compareTo(d1)>0 && d3.compareTo(d2)<0) {
								s1.add(sss.get(i));
							}
						}
						for(String ss:s1){
								sinfo = new ScheduleInfo();
								if(temp.getStartTime().substring(0, 10).equals(ss)){
									sinfo.setStartTime(temp.getStartTime());
									sinfo.setEndTime(ss+" 23:59:00");
									sinfo.setName(temp.getName());
									sinfo.setEventId(temp.getEventId());
									sinfo.setType(temp.getType());
									sinfo.setCreatedId(temp.getCreatedId());
									sinfo.setUpdatedId(temp.getUpdatedId());
									sinfo.setIsAllday(temp.getIsAllday());
									sinfo.setRemark(temp.getRemark());
									sinfo.setCreatedTime(temp.getCreatedTime());
									sinfo.setUpdatedTime(temp.getUpdatedTime());
								}
								else if(temp.getEndTime().substring(0, 10).equals(ss)){
									sinfo.setStartTime(ss+" 00:00:00");
									sinfo.setEndTime(temp.getEndTime());
									sinfo.setName(temp.getName());
									sinfo.setEventId(temp.getEventId());	
									sinfo.setType(temp.getType());
									sinfo.setCreatedId(temp.getCreatedId());
									sinfo.setUpdatedId(temp.getUpdatedId());
									sinfo.setIsAllday(temp.getIsAllday());
									sinfo.setRemark(temp.getRemark());
									sinfo.setCreatedTime(temp.getCreatedTime());
									sinfo.setUpdatedTime(temp.getUpdatedTime());
								}else{				
									sinfo.setStartTime(ss+" 00:00:00");
									sinfo.setEndTime(ss+" 23:59:00");
									sinfo.setName(temp.getName());
									sinfo.setEventId(temp.getEventId());
									sinfo.setType(temp.getType());
									sinfo.setCreatedId(temp.getCreatedId());
									sinfo.setUpdatedId(temp.getUpdatedId());
									sinfo.setIsAllday(temp.getIsAllday());
									sinfo.setRemark(temp.getRemark());
									sinfo.setCreatedTime(temp.getCreatedTime());
									sinfo.setUpdatedTime(temp.getUpdatedTime());
								}
								scheduleInfoList.add(sinfo);
						}
					}
				}
			}		
			qList.addAll(scheduleInfoList);
			Iterator<ScheduleInfo> it = qList.iterator();
			while(it.hasNext()){
				ScheduleInfo x = it.next();
			    if(x.getStartTime()!=null && x.getEndTime()!=null && !AccountDate.get(x.getStartTime(),x.getEndTime())){
			        it.remove();
			    }		    
			}
		//获取拜访对象得名称重新封装数据
		//结果封装
		if(qList!=null && !qList.isEmpty()){
			
			Map<String,List<ScheduleInfo>> dateKey_infos = new HashMap<String,List<ScheduleInfo>>();
			String format = "yyyy-MM-dd";
			for(ScheduleInfo temp : qList){
				if (temp.getStartTime()!=null && temp.getStartTime().length()>19) {
					temp.setStartTime(temp.getStartTime().substring(0, 19));;
				}
				if (temp.getEndTime()!=null && temp.getEndTime().length()>19) {
					temp.setEndTime(temp.getEndTime().substring(0, 19));
				}
				//2017/5/11为了 获取访谈对象得名称
				if(temp.getType()==2){
					
					ScheduleInfo ss =dao.selectVisitNameById(temp.getId());
					
					if(ss!=null && ss.getSchedulePerson()!=null){
						temp.setSchedulePerson(ss.getSchedulePerson());
					}
				}
				//2017/4/17号修改 报空指针
				String stime= temp.getStartTime().substring(0,format.length());
				//String dateKey = DateUtil.dateFormat(temp.getStartTime(), format);
				String dateKey = DateUtil.dateFormat(stime, format);
				if(dateKey_infos.containsKey(dateKey)){
					dateKey_infos.get(dateKey).add(temp);
				}else{
					List<ScheduleInfo> tempInfos = new ArrayList<ScheduleInfo>();
					tempInfos.add(temp);
					dateKey_infos.put(dateKey, tempInfos);
				}
			}
			for(Map.Entry<String, List<ScheduleInfo>> tempE : dateKey_infos.entrySet()){
				ScheduleUtil au = new ScheduleUtil();
				au.setDateKey(tempE.getKey());
				
				ComparatorDate ss = new ComparatorDate();
				Collections.sort(tempE.getValue(),ss);
				
				au.setSchedules(tempE.getValue());
				resultList.add(au);
			}
		}
		return resultList;
	}

}
