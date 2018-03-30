package com.galaxy.im.business.report.baseinfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.report.InformationDictionary;
import com.galaxy.im.bean.report.InformationFixedTable;
import com.galaxy.im.bean.report.InformationListdata;
import com.galaxy.im.bean.report.InformationListdataRemark;
import com.galaxy.im.bean.report.InformationTitle;
import com.galaxy.im.business.report.baseinfo.dao.IBaseInfoDao;
import com.galaxy.im.common.CUtils;
import com.galaxy.im.common.StaticConst;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;
import com.galaxy.im.common.html.QHtmlClient;

@Service
public class BaseInfoServiceImpl extends BaseServiceImpl<Test> implements IBaseInfoService{

	@Autowired
	IBaseInfoDao dao;
	@Autowired
	private Environment env;
	
	@Override
	protected IBaseDao<Test, Long> getBaseDao() {
		return dao;
	}

	@Override
	public List<InformationTitle> searchWithData(String titleId, String projectId) {
		List<InformationTitle> list = null;
		Map<String,InformationTitle> titleMap = getChildTitleMap(titleId);
		Set<String> titleIds = titleMap.keySet();
		list = new ArrayList<>(titleMap.values());
		if(list != null)
		{
			for(InformationTitle item : list)
			{
				if(item.getChildList() != null)
				{
					item.getChildList().clear();
				}
				if(item.getResultList() != null)
				{
					item.getResultList().clear();
				}
				if(item.getDataList() != null)
				{
					item.getDataList().clear();
				}
				if(item.getFixedTableList() != null)
				{
					item.getFixedTableList().clear();
				}
			}
		}
		
		//查询result
		InformationResult resultQuery = new InformationResult();
		resultQuery.setProjectId(projectId);
		resultQuery.setTitleIds(titleIds);
		resultQuery.setProperty("title_id");
		resultQuery.setDirection(Direction.ASC.toString());

		Map<Long,String> dict = new HashMap<Long,String>();
		
		List<InformationDictionary> allValue = dao.getInformationDictionary();
		for(InformationDictionary avalue : allValue){
			dict.put(avalue.getId(), avalue.getName());
		}

		List<InformationResult> resultList = dao.getInformationResult(resultQuery);
		if(resultList != null && resultList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationResult> tempList = null;
			for(InformationResult item : resultList)
			{
				if(item.getContentChoose() != null)
				{
					if(dict != null)
					{
						if(item.getContentChoose().equals("尚未获投") || item.getContentChoose().equals("不明确")||
								item.getContentChoose().equals("外部独立合伙人")||item.getContentChoose().equals("自开发")||
								item.getContentChoose().equals("创业者")){
							item.setValueName(item.getContentChoose());
						}else{
							item.setValueName(dict.get(Long.valueOf(item.getContentChoose())));
						}
					}
				}
				
				title = titleMap.get(item.getTitleId());
				if(title != null)
				{
					if(title.getResultList() == null)
					{
						tempList = new ArrayList<>();
						title.setResultList(tempList);
					}
					else
					{
						tempList = title.getResultList();
					}
					tempList.add(item);
				}
			}
		}
		//查询FixedTable
		InformationFixedTable fixedTableQuery = new InformationFixedTable();
		fixedTableQuery.setProjectId(projectId);
		fixedTableQuery.setTitleIds(titleIds);
		fixedTableQuery.setProperty("title_id");
		fixedTableQuery.setDirection(Direction.ASC.toString());
		List<InformationFixedTable> fixedTableList = dao.getInformationFixedTable(fixedTableQuery);
		if(fixedTableList != null && fixedTableList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationFixedTable> tempList = null;
			for(InformationFixedTable item : fixedTableList)
			{
				if(item.getContent() != null)
				{
					if(dict != null)
					{
						item.setValueName(dict.get(Long.valueOf(item.getContent())));
					}
				}
				title = titleMap.get(item.getTitleId());
				if(title != null)
				{
					if(title.getFixedTableList() == null)
					{
						tempList = new ArrayList<>();
						title.setFixedTableList(tempList);
					}
					else
					{
						tempList = title.getFixedTableList();
					}
					tempList.add(item);
				}
			}
		}
		//查询表格头
		InformationListdataRemark headerQuery = new InformationListdataRemark();
		headerQuery.setTitleIds(titleIds);
		List<InformationListdataRemark> headerList = dao.getInformationListdataRemark(headerQuery);
		if(headerList != null && headerList.size() > 0)
		{
			InformationTitle title = null;
			for(InformationListdataRemark item : headerList)
			{
				if(item.getSubCode() == null){
					title = titleMap.get(item.getTitleId()+"");
					if(title != null)
					{
						title.setTableHeader(item);
					}
				}
			}
		}
		//查询表格
		InformationListdata listdataQuery = new InformationListdata();
		listdataQuery.setProjectId(Long.valueOf(projectId));
		listdataQuery.setTitleIds(titleIds);
		listdataQuery.setProperty("created_time");
		listdataQuery.setDirection(Direction.ASC.toString());
		List<InformationListdata> listdataList = dao.getInformationListdata(listdataQuery);
		if(listdataList != null && listdataList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationListdata> tempList = null;
			for(InformationListdata item : listdataList)
			{
				//获取用户名称----20170823
			    if(item.getCreateId()!=null&&item.getCreateId()!=0){
			    	if(!"".equals(getUserName(item.getCreateId())) && getUserName(item.getCreateId())!=null){
			    		item.setCreateName(getUserName(item.getCreateId()));
			    	}
			    }
			    if(item.getUpdateId()!=null&&item.getUpdateId()!=0){
			    	if(!"".equals(getUserName(item.getUpdateId())) && getUserName(item.getUpdateId())!=null){
			    		item.setUpdateName(getUserName(item.getUpdateId()));
			    	}
			    }

				title = titleMap.get(item.getTitleId()+"");
				if(title != null)
				{
					if(title.getDataList() == null)
					{
						tempList = new ArrayList<>();
						title.setDataList(tempList);;
					}
					else
					{
						tempList = title.getDataList();
					}
					tempList.add(item);
				}
			}
		}
		return list;
	}
	
	private Map<String,InformationTitle> getChildTitleMap(String parentId)
	{
		Map<String,InformationTitle> titleMap = null;
		List<InformationTitle> list = selectByTlist(selectChildsByPid(Long.valueOf(parentId)));
		titleMap = new HashMap<>();
		popTitleMap(list, titleMap);
		return titleMap;
	}
	
	private void popTitleMap(List<InformationTitle> list, Map<String,InformationTitle> map)
	{
		if(list != null && list.size() >0)
		{
			for(InformationTitle item : list)
			{
				map.put(item.getId()+"", item);
				if(item.getChildList() != null)
				{
					popTitleMap(item.getChildList(),map);
				}
			}
		}
	}
	
	/**
	 * 根据父id 查询其子集
	 */
	public List<InformationTitle> selectChildsByPid(Long pid) {
		Direction direction = Direction.ASC;
		String property = "index_no";
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId",pid);
		params.put("isValid",0);
		params.put("isShow","t");
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		List<InformationTitle> ptitleList = dao.selectChildsByPid(params);
		
		ptitleList = ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
		
		for(InformationTitle title : ptitleList){
			if(title.getSign() != null && title.getSign().intValue() == 2){
				title.setName(title.getName()+"：");
			}
		}
		return ptitleList;
	}
	
	public List<InformationTitle> selectByTlist(List<InformationTitle> tList) {
		for(InformationTitle title : tList){
			List<InformationTitle> ptitleList = selectChildsByPid(title.getId());
			if(ptitleList !=null && !ptitleList.isEmpty()){
				selectByTlist(ptitleList);
				title.setChildList(ptitleList);
			}
		}
		return tList;
	}
	
	private String getUserName(Long createId) {
		String createName="";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createdId", createId);
		//调用客户端
		String url = env.getProperty("power.server") + StaticConst.getCreadIdInfo;
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("userId", createId);
		String result = QHtmlClient.get().post(url, null, qMap);
		JSONObject resultJson = JSONObject.parseObject(result);
	   	JSONArray array = resultJson.getJSONArray("value");
	   	List<Map<String, Object>> list=CUtils.get().jsonString2list(array);
	   	if(list!=null){
			for(Map<String, Object> vMap:list){
				createName = CUtils.get().object2String(vMap.get("userName"));
			}
		}
		return createName;
	}

}
