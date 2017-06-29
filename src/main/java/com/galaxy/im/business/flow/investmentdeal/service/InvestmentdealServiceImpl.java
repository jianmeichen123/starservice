package com.galaxy.im.business.flow.investmentdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.im.bean.Test;
import com.galaxy.im.business.flow.internalreview.service.InternalreviewServiceImpl;
import com.galaxy.im.business.flow.investmentdeal.dao.IInvestmentdealDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.exception.ServiceException;

@Service
public class InvestmentdealServiceImpl extends BaseServiceImpl<Test> implements IInvestmentdealService{
	private Logger log = LoggerFactory.getLogger(InvestmentdealServiceImpl.class);
	@Autowired
	private IInvestmentdealDao dao;

	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	@Override
	public int updateInvestmentdeal(Map<String, Object> paramMap) {
		return dao.updateInvestmentdeal(paramMap);
	}

	/*@Override
	public int businessnegotiation(Map<String, Object> paramMap) {
		return dao.businessnegotiation(paramMap);
	}*/


	
	@Override
	public Map<String, Object> getInvestmentdealOperateStatus(Map<String, Object> paramMap) {
		try{
			Boolean res=false;
			Boolean ress=false;
			Boolean resss=false;
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("pass", false);
			result.put("veto", false);
			result.put("inverstpass", false);
			result.put("shotpass", false);
			List<Map<String,Object>> dataList = dao.hasPassMeeting(paramMap);
			List<Map<String,Object>> list = dao.projectResult(paramMap);
			if(dataList!=null && dataList.size()>0){
				String dictCode;
				Integer pcount = 0;
				for(Map<String,Object> map : dataList){
					dictCode = CUtils.get().object2String(map.get("dictCode"), "");
					pcount = CUtils.get().object2Integer(map.get("pcount"), 0);
					
					if("meetingResult:1".equals(dictCode)){
						if(pcount>0){
							res=true;
							result.put("pass", true);
						}
					}else if("meetingResult:3".equals(dictCode)){
						if(pcount>0){
							result.put("veto", true);
							
						}
					}
				}
			}
			
			if (list!=null&&list.size()>0) {
				String results;
				for(Map<String,Object> map : list){
					results=CUtils.get().object2String(map.get("results"), "");
					
					if ("meeting5Result:4".equals(results)) {//投资
						ress=true;
					}else if ("meeting5Result:3".equals(results)) {//闪投
						resss=true;
					}
				}
			}
			
			if (res && ress) {//会议通过且投资
				result.put("inverstpass", true);
			}
			if(res && resss){//会议通过且闪投
				result.put("shotpass", true);
			}
			
			return result;
		}catch(Exception e){
			log.error(InternalreviewServiceImpl.class.getName() + ":getInvestmentdealOperateStatus",e);
			throw new ServiceException(e);
		}
		
	}

}
