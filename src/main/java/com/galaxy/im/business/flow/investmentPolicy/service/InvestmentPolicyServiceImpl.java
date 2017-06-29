package com.galaxy.im.business.flow.investmentPolicy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.businessnegotiation.service.BusinessnegotiationServiceImpl;
import com.galaxy.im.business.flow.investmentPolicy.dao.IInvestmentPolicyDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class InvestmentPolicyServiceImpl extends BaseServiceImpl<Test> implements IInvestmentPolicyService{
	private Logger log = LoggerFactory.getLogger(InvestmentPolicyServiceImpl.class);
	@Autowired
	private IInvestmentPolicyDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	/**
	 * 是否上传投资意向书
	 */
	@Override
	public Map<String, Object> investmentpolicy(Map<String, Object> paramMap) {
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("pass", false);
			List<Map<String,Object>> dataList = dao.investmentpolicy(paramMap);
			if(dataList!=null && dataList.size()>0){
				String dictCode;
				Integer pcount = 0;
				for(Map<String,Object> map : dataList){
					dictCode = CUtils.get().object2String(map.get("dictCode"), "");
					pcount = CUtils.get().object2Integer(map.get("pcount"), 0);
					//是否上传了"投资协议"
					if("fileWorktype:6".equals(dictCode)){
						if(pcount>0){
							result.put("pass", true);
						}
					}
				}
			}
			return result;
		}catch(Exception e){
			log.error(BusinessnegotiationServiceImpl.class.getName() + ":businessOperateStatus",e);
			throw new ServiceException(e);
		}
	}
	
	

}
