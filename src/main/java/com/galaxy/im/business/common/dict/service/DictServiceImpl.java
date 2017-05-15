package com.galaxy.im.business.common.dict.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.common.Dict;
import com.galaxy.im.business.common.dict.dao.IDictDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class DictServiceImpl extends BaseServiceImpl<Dict> implements IDictService{
	private Logger log = LoggerFactory.getLogger(DictServiceImpl.class);
	
	@Autowired
	private IDictDao dictDao;

	@Override
	protected IBaseDao<Dict, Long> getBaseDao() {
		return dictDao;
	}

	@Override
	public Map<String, Object> selectCallonFilter() {
		try{
			List<Map<String,Object>> dataList = dictDao.selectCallonFilter();
			Map<String,Object> resultMap = null;
			
			if(dataList!=null && dataList.size()>0){
				List<Map<String,Object>> significanceList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> callonProgressList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> recordMissingList = new ArrayList<Map<String,Object>>();
				
				for(Map<String,Object> map : dataList){
					if("significance".equals(CUtils.get().object2String(map.get("parentCode")))){
						map.remove("parentCode");
						//设置默认值
						if(1==CUtils.get().object2Integer(map.get("dictValue"),0)){
							map.put("defValue", 1);
						}else{
							map.put("defValue", 0);
						}
						significanceList.add(map);
					}else if("callonProgress".equals(CUtils.get().object2String(map.get("parentCode")))){
						map.remove("parentCode");
						callonProgressList.add(map);
					}else if("recordMissing".equals(CUtils.get().object2String(map.get("parentCode")))){
						map.remove("parentCode");
						recordMissingList.add(map);
					}
				}
				resultMap = new HashMap<String,Object>();
				resultMap.put("significance", significanceList);
				resultMap.put("callonProgress", callonProgressList);
				resultMap.put("recordMissing", recordMissingList);
			}
			
			return resultMap;
		}catch(Exception e){
			log.error(DictServiceImpl.class.getName() + "_selectCallonFilter",e);
			throw new ServiceException(e);
		}
	}
	
	
	

}
