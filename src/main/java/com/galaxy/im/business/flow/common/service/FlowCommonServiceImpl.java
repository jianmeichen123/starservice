package com.galaxy.im.business.flow.common.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.project.ProjectBean;
import com.galaxy.im.business.flow.common.dao.IFlowCommonDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class FlowCommonServiceImpl extends BaseServiceImpl<ProjectBean> implements IFlowCommonService{
	private Logger log = LoggerFactory.getLogger(FlowCommonServiceImpl.class);
	
	@Autowired
	private IFlowCommonDao dao;

	@Override
	protected IBaseDao<ProjectBean, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 取得项目状态
	 */
	@Override
	public Map<String, Object> projectStatus(Map<String, Object> paramMap) {
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("flag", 0);
			result.put("message", "未知错误");
			Map<String,Object> daoMap = dao.projectStatus(paramMap);
			
			if(daoMap!=null && !daoMap.isEmpty()){
				String projectStatus = CUtils.get().object2String(daoMap.get("dictCode"),"");
				if("projectStatus:0".equals(projectStatus)){
					result.put("flag",1);
				}else if("projectStatus:1".equals(projectStatus)){
					result.put("message","本项目处于投后运营状态，不能进行操作");
				}else if("projectStatus:2".equals(projectStatus)){
					result.put("message","本项目已经被否决，不能进行操作");
				}else if("projectStatus:3".equals(projectStatus)){
					result.put("message","本项目处于退出状态，不能进行操作");
				}else{
					result.put("message","本项目所处的阶段异常，请联系管理员");
				}
			}
			return result;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":projectStatus",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 否决项目
	 */
	@Override
	public Boolean vetoProject(Map<String, Object> paramMap) {
		try{
			return dao.vetoProject(paramMap)>0;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":vetoProject",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 流程进入到下一阶段
	 */
	@Override
	public boolean enterNextFlow(Map<String, Object> paramMap) {
		try{
			return dao.enterNextFlow(paramMap)>0;
		}catch(Exception e){
			log.error(FlowCommonServiceImpl.class.getName() + ":enterNextFlow",e);
			throw new ServiceException(e);
		}
	}
	
	
	
	
	
}
