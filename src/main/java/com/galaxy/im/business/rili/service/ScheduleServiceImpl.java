package com.galaxy.im.business.rili.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.rili.dao.IScheduleDao;
import com.galaxy.im.common.CUtils;
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
	

}
