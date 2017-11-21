package com.galaxy.im.business.flow.investmentoperate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.common.Dict;
import com.galaxy.im.business.common.dict.service.IDictService;
import com.galaxy.im.business.flow.investmentoperate.service.IInvestmentoperateService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.ResultBean;

/**
 * 投后运营
 * 
 * @author 
 */
@Controller
@RequestMapping("/flow/investmentoperate")
public class InvestmentoperateController {
	private Logger log = LoggerFactory.getLogger(InvestmentoperateController.class);
	@Autowired
	IInvestmentoperateService service;
	@Autowired
	IDictService dictService;
	
	/**
	 * 投后运营的操作：无
	 * 会议纪要的个数
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getMeetingCount")
	@ResponseBody
	public Object projectOperateStatus(@RequestBody String paramString){
		ResultBean<Object> result = new ResultBean<Object>();
		try{
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(paramMap!=null && paramMap.containsKey("meetingType")){
				List<String> meetingTypeList = new ArrayList<String>();
				
				if(paramMap.get("meetingType").toString().contains("pos")){
					//投后运营
					List<Dict> dictList = dictService.selectByParentCode("postMeetingType");
					for(Dict dict : dictList){
						meetingTypeList.add(dict.getDictCode());
					}
					paramMap.put("meetingTypeList", meetingTypeList);
					paramMap.put("recordType", 2);
				}else{
					//普通会议
					meetingTypeList.add(paramMap.get("meetingType").toString());
					paramMap.put("meetingTypeList",meetingTypeList);
					paramMap.put("recordType", 0);
				}
			}
			
			Map<String,Object> map = service.getMeetingCount(paramMap);
			if(CUtils.get().mapIsNotEmpty(map)){
				result.setMap(map);
			}
			result.setStatus("OK");
		}catch(Exception e){
			log.error(InvestmentoperateController.class.getName() + "projectOperateStatus",e);
		}
		return result;
	}
}
