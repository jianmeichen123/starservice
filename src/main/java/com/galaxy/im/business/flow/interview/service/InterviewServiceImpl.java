package com.galaxy.im.business.flow.interview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.interview.dao.IInterviewDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class InterviewServiceImpl extends BaseServiceImpl<Test> implements IInterviewService{
	private Logger log = LoggerFactory.getLogger(InterviewServiceImpl.class);
	
	@Autowired
	private IInterviewDao dao;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 判断项目是否存在否决／通过的访谈
	 */
	@Override
	public Map<String, Object> hasPassInterview(Map<String, Object> paramMap) {
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("pass", false);
			result.put("veto", false);
			List<Map<String,Object>> dataList = dao.hasPassInterview(paramMap);
			if(dataList!=null && dataList.size()>0){
				String dictCode;
				Integer pcount = 0;
				for(Map<String,Object> map : dataList){
					dictCode = CUtils.get().object2String(map.get("dictCode"), "");
					pcount = CUtils.get().object2Integer(map.get("pcount"), 0);
					
					if("meetingResult:1".equals(dictCode)){
						if(pcount>0){
							result.put("pass", true);
						}
					}else if("meetingResult:3".equals(dictCode)){
						if(pcount>0){
							result.put("veto", true);
						}
					}
				}
			}
			return result;
		}catch(Exception e){
			log.error(InterviewServiceImpl.class.getName() + ":projectStatus",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取项目访谈记录个数
	 */
	@Override
	public Map<String, Object> getInterviewCount(Map<String, Object> paramMap) {
		int count = dao.getInterviewCount(paramMap);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("interviewCount", count);
		return map;
	}

		

}
