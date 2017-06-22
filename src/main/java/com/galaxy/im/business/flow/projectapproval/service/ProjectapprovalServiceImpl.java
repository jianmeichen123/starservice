package com.galaxy.im.business.flow.projectapproval.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.projectapproval.dao.IProjectapprovalDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class ProjectapprovalServiceImpl extends BaseServiceImpl<Test> implements IProjectapprovalService{
	private Logger log = LoggerFactory.getLogger(ProjectapprovalServiceImpl.class);
	
	@Autowired
	private IProjectapprovalDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 判断项目是否有否决的会议记录
	 * 必须一条“闪投”或“投资”或“转向”结果的会议记录 pass：true，否则false
	 */
	@Override
	public Map<String, Object> approvalOperateStatus(Map<String, Object> paramMap) {
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("pass", false);
			result.put("veto", false);
			List<Map<String,Object>> dataList = dao.approvalOperateStatus(paramMap);
			if(dataList!=null && dataList.size()>0){
				String dictCode;
				Integer pcount = 0;
				for(Map<String,Object> map : dataList){
					dictCode = CUtils.get().object2String(map.get("dictCode"), "");
					pcount = CUtils.get().object2Integer(map.get("pcount"), 0);
					//闪投，投资，转向
					if("meeting3Result:2".equals(dictCode)||"meeting3Result:3".equals(dictCode)||"meeting3Result:5".equals(dictCode)){
						if(pcount>0){
							result.put("pass", true);
						}
					}
					//否决
					else if("meeting3Result:6".equals(dictCode)){
						if(pcount>0){
							result.put("veto", true);
						}
					}
				}
			}
			return result;
		}catch(Exception e){
			log.error(ProjectapprovalServiceImpl.class.getName() + ":approvalOperateStatus",e);
			throw new ServiceException(e);
		}
	}
	
	

}
