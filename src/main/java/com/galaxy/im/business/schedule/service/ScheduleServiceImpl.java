package com.galaxy.im.business.schedule.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.schedule.dao.IScheduleDao;
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
	

}
