package com.galaxy.im.business.statistics.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.bean.common.SessionBean;
import com.galaxy.im.bean.statistics.ScheduleCountVO;
import com.galaxy.im.business.flow.common.service.IFlowCommonService;
import com.galaxy.im.business.statistics.service.IStatisticsProjectService;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.DateUtil;
import com.galaxy.im.common.ResultBean;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.html.QHtmlClient;


@Controller
@RequestMapping("/statistics")
public class StatisticsProjectController {
	
	private Logger log = LoggerFactory.getLogger(StatisticsProjectController.class);

	@Autowired
	IStatisticsProjectService service;
	@Autowired
	private IFlowCommonService fcService;
	@Autowired
	private Environment env;
	
	/**
	 * 合伙人\投资经理\CEO项目数据统计
	 * @return
	 */
	@ResponseBody
	@RequestMapping("computerRate")
	public Object sheduling(HttpServletRequest request,HttpServletResponse response){
		ResultBean<Object> resultBean = new ResultBean<>();
		resultBean.setStatus("error");
		try{
			long deptId=0l;
			Map<String,Object> map = new HashMap<String,Object>();
			SessionBean sessionBean = CUtils.get().getBeanBySession(request);
			//获取用户所属部门id
			List<Map<String, Object>> deptList = fcService.getDeptId(sessionBean.getGuserid(),request,response);
			if(deptList!=null){
				for(Map<String, Object> vMap:deptList){
					deptId= CUtils.get().object2Long( vMap.get("deptId"));
				}
			}
			//获取用户角色code
			List<String> roleCodeList = selectRoleCodeByUserId(sessionBean.getGuserid(), request, response);
			if(roleCodeList==null || roleCodeList.size()==0){
				resultBean.setMessage("当前用户未配置任何角色，将不执行项目统计功能！");
				return resultBean;
			}
			//项目总览
			Map<String,Object> projectoverview = service.querySatisticsProjectOverview(roleCodeList, deptId,sessionBean.getGuserid());
			map.put("projectOverview", projectoverview);
			//事业部top5
			List<Map<String, Object>> topRankList = service.queryCountCareerLineRankProject();
			map.put("careerLineList", topRankList);
			//本月数据
			Map<String,Object> mpdvo = service.queryCountMonthProjectChanged(roleCodeList, deptId,sessionBean.getGuserid());
			map.put("monthProjectData", mpdvo);
			
			//统计登录用户的今日排期 各个会议的个数
			List<ScheduleCountVO> countList = new ArrayList<ScheduleCountVO>();	
			List<Map<String, Object>> projectList = new ArrayList<Map<String, Object>>();
			
			if (roleCodeList.contains(StaticConst.TZJL)){  						//投资经理查询
				countList = service.queryTZJLScheduleCount(sessionBean.getGuserid());
				map.put("countList", countList);
			}else if(roleCodeList.contains(StaticConst.HHR)){					//合伙人查询
				countList = service.queryHHRScheduleCount(sessionBean.getGuserid());
				map.put("countList", countList);
			}else if(roleCodeList.contains(StaticConst.CEO)||
					roleCodeList.contains(StaticConst.CEOMS)||
					roleCodeList.contains(StaticConst.DSZ)||
					roleCodeList.contains(StaticConst.DMS)){					//ceo秘书查询
				countList = service.queryCEOScheduleCount(sessionBean.getGuserid());
				map.put("countList", countList);
			}
			//项目类表
			String rsdatet = DateUtil.getFormatDateTime(new Date(),"yyyy-MM-dd") + " 23:59:59";
		    long  s = DateUtil.convertStringtoD(rsdatet).getTime();
		    String rsdate = DateUtil.getFormatDateTime(new Date(),"yyyy-01-01") + " 00:00:00";
		    long  a = DateUtil.convertStringtoD(rsdate).getTime();
			
			if(roleCodeList.contains(StaticConst.HHR)){					//合伙人查询
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("projectDepartid", deptId);
				map1.put("startTime", a);
				map1.put("endTime", s);
				projectList = service.selectStageSummary(map1);
				map.put("projectList", projectList);
			}else if(roleCodeList.contains(StaticConst.CEO)||roleCodeList.contains(StaticConst.DSZ)){	
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("startTime", a);
				map1.put("endTime", s);
				projectList = service.selectStageSummary(map1);
				map.put("projectList", projectList);
			}
			resultBean.setMap(map);
			resultBean.setStatus("OK");
		}catch(Exception e){
			log.error(StatisticsProjectController.class.getName() + ":computerRate",e);
		}
		return resultBean;
	}
	
	//通过用户id，获取用户角色code
	private List<String> selectRoleCodeByUserId(Long guserid,HttpServletRequest request, HttpServletResponse response) {
		//调用客户端
		Map<String,Object> headerMap = QHtmlClient.get().getHeaderMap(request);
		String url = env.getProperty("power.server") + StaticConst.getRoleCodeByUserId;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("userId",guserid);
		JSONArray valueJson=null;
		List<String> list = null;
		String result = QHtmlClient.get().post(url, headerMap, qMap);
		if("error".equals(result)){
			log.error(StatisticsProjectController.class.getName() + "获取信息时出错","此时服务器返回状态码非200");
		}else{
			boolean flag = true;
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson!=null && resultJson.containsKey("value")){
				valueJson = resultJson.getJSONArray("value");
				if(resultJson.containsKey("success") && "true".equals(resultJson.getString("success"))){
					flag = false;
				}
				list=CUtils.get().jsonString2list(valueJson);
			}
			if(flag){
				log.error(StatisticsProjectController.class.getName() + "获取信息时出错","服务器返回正常，获取数据失败");
			}
		}
		return list;
	}
	
}
