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

	@Override
	public Map<String, Object> getInvestmentdealOperateStatus(Map<String, Object> paramMap) {
		try{
			Boolean res=false;//是否有通过的会议
			Boolean ress=false;//投资
			Boolean resss=false;//闪投
			Map<String,Object> result = new HashMap<String,Object>();
			
			result.put("veto", false);
			result.put("investpass", false);
			result.put("flashpass", false);
			List<Map<String,Object>> dataList = dao.hasPassMeeting(paramMap);//是否存在通过的会议
			String s = dao.projectResult(paramMap);//获取项目在“会后商务谈判”阶段的结论
			if(dataList!=null && dataList.size()>0){
				String dictCode;
				Integer pcount = 0;
				for(Map<String,Object> map : dataList){
					dictCode = CUtils.get().object2String(map.get("dictCode"), "");
					pcount = CUtils.get().object2Integer(map.get("pcount"), 0);
					
					if("meetingResult:1".equals(dictCode)){//通过的会议
						if(pcount>0){
							res=true;
						}
					}else if("meetingResult:3".equals(dictCode)){//否决的会议
						if(pcount>0){
							result.put("veto", true);
						}
					}
				}
			}
			if (s!=null&&s.length()>0) {
					String businessTypeCode=s;
					if ("TZ".equals(businessTypeCode)) {//结果投资
						ress=true;
					}else if ("ST".equals(businessTypeCode)) {//结果闪投
						resss=true;
					}
			}
			if (res && ress) {//会议通过且投资
				result.put("investpass", true);
			}
			if(res && resss){//会议通过且闪投
				result.put("flashpass", true);
			}
			return result;
		}catch(Exception e){
			log.error(InternalreviewServiceImpl.class.getName() + ":getInvestmentdealOperateStatus",e);
			throw new ServiceException(e);
		}
		
	}

}
