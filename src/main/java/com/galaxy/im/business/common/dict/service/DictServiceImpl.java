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

	/**
	 * 结论
	 */
	@Override
	public Map<String, Object> selectResultFilter(Map<String, Object> paramMap) {
		Map<String,Object> resultMap = null;
		try{
			if(paramMap!=null && paramMap.containsKey("meetingType")){
				List<Map<String,Object>> dataList = dictDao.selectResultFilter(paramMap);
				
				if(dataList!=null && dataList.size()>0){
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					for(Map<String,Object> map : dataList){
						//设置默认值
						if(1==CUtils.get().object2Integer(map.get("dictValue"),0)){
							map.put("defValue", 1);
						}else{
							map.put("defValue", 0);
						}
						list.add(map);
					}
					resultMap = new HashMap<String,Object>();
					resultMap.put("meetingResult", list);
				}
			}
			return resultMap;
		}catch(Exception e){
			log.error(DictServiceImpl.class.getName() + "_selectResultFilter",e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 原因
	 */
	@Override
	public Map<String, Object> selectReasonFilter(Map<String, Object> paramMap) {
		Map<String,Object> resultMap = null;
		try{
			if(paramMap!=null && paramMap.containsKey("meetingResult")){
				List<Map<String,Object>> dataList = dictDao.selectReasonFilter(paramMap);
				
				if(dataList!=null && dataList.size()>0){
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					for(Map<String,Object> map : dataList){
						//设置默认值
						if(1==CUtils.get().object2Integer(map.get("dictValue"),0)){
							map.put("defValue", 1);
						}else{
							map.put("defValue", 0);
						}
						list.add(map);
					}
					resultMap = new HashMap<String,Object>();
					resultMap.put("resultReason", list);
				}
			}
			return resultMap;
		}catch(Exception e){
			log.error(DictServiceImpl.class.getName() + "_selectReasonFilter",e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 通过parentcode获取dict信息
	 */
	@Override
	public List<Dict> selectByParentCode(String parentCode) {
		return dictDao.selectByParentCode(parentCode);
	}

	/**
	 * 获取项目相关的选择信息
	 */
	@Override
	public List<Map<String, Object>> getDictionaryList(Map<String, Object> paramMap) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		try{
			if(paramMap!=null && paramMap.containsKey("parentCode")){
				if(CUtils.get().object2String(paramMap.get("parentCode")).equals("FNO1_1")){
					//融资状态
					list = dictDao.getFinanceStatusList(paramMap);
					if(paramMap.containsKey("flag")&& CUtils.get().object2Integer(paramMap.get("flag"))==1){
						Map<String,Object> map =new HashMap<String,Object>();
						Map<String,Object> map1 =new HashMap<String,Object>();
						map.put("name", "尚未获投");
						map.put("code", "尚未获投");
						map.put("id", "尚未获投");
						map1.put("name", "不明确");
						map1.put("code", "不明确");
						map1.put("id", "不明确");
						list.add(map);
						list.add(map1);
					}
				}else if(CUtils.get().object2String(paramMap.get("parentCode")).equals("FNO1_10")){
					//项目来源
					list = dictDao.getFinanceStatusList(paramMap);
					if(paramMap.containsKey("flag")&& CUtils.get().object2Integer(paramMap.get("flag"))==1){
						Map<String,Object> map =new HashMap<String,Object>();
						Map<String,Object> map1 =new HashMap<String,Object>();
						Map<String,Object> map2 =new HashMap<String,Object>();
						map.put("name", "创业者");
						map.put("code", "创业者");
						map.put("id", "创业者");
						map1.put("name", "自开发");
						map1.put("code", "自开发");
						map1.put("id", "自开发");
						map2.put("name", "外部独立合伙人");
						map2.put("code", "外部独立合伙人");
						map2.put("id", "外部独立合伙人");
						list.add(map);
						list.add(map1);
						list.add(map2);
					}
				}else if(CUtils.get().object2String(paramMap.get("parentCode")).equals("FNO1_11")){
					//项目承揽人
					list = dictDao.getProjectToUserList(paramMap);
					Map<String,Object> map =new HashMap<String,Object>();
					map.put("userName", "非投资线员工");
					map.put("userId", "10072");
					list.add(map);
				}else if(CUtils.get().object2String(paramMap.get("parentCode")).equals("FNO1_4")){
					//团队成员职位
					paramMap.put("code", "team-members");
					list = dictDao.getTeamUserPosition(paramMap);
				}else if(CUtils.get().object2String(paramMap.get("parentCode")).equals("shareholderNature")){
					//股东性质
					paramMap.clear();
					paramMap.put("parentCode", "FNO1_6");
					list = dictDao.getShareholderNatureList(paramMap);
				}else if(CUtils.get().object2String(paramMap.get("parentCode")).equals("shareholderType")){
					//股东类型
					paramMap.clear();
					paramMap.put("parentCode", "FNO1_6");
					list = dictDao.getShareholderTypeList(paramMap);
				}else{
					List<Dict> dataList = dictDao.selectByParentCode(CUtils.get().object2String(paramMap.get("parentCode")));
					if(dataList!=null && dataList.size()>0){
						for(Dict dict : dataList){
							Map<String,Object> map =new HashMap<String,Object>();
							map.put("name", dict.getName());
							map.put("code", dict.getDictCode());
							
							if("projectType".equals(dict.getParentCode())){
								//项目类型：投资
								if(1==CUtils.get().object2Integer(dict.getDictValue(),0)){
									map.put("defValue", 1);
								}else{
									map.put("defValue", 0);
								}
							}else if("currency".equals(dict.getParentCode())){
								//币种默认值：人民币
								if(0==CUtils.get().object2Integer(dict.getDictValue(),0)){
									map.put("defValue", 1);
								}else{
									map.put("defValue", 0);
								}
							}
							list.add(map);
						}
					}
				}
			}
		}catch(Exception e){
			log.error(DictServiceImpl.class.getName() + "getDictionaryList",e);
			throw new ServiceException(e);
		}
		return list;
	}

	/**
	 * 获取融资轮次相关的选项列表
	 */
	@Override
	public Map<String, Object> getDictionaryFinanceList(Map<String, Object> paramMap) {
		List<Map<String,Object>> list6 = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list7 = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list8 = new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap = null;
		try{
			if(paramMap!=null && paramMap.containsKey("parentCode")){
				if(CUtils.get().object2String(paramMap.get("parentCode")).equals("FNO1_7")){
					//币种
					list6 = dictDao.getfinanceUnitList(paramMap);
					//融资轮次
					list7 = dictDao.getfinancingStatusList(paramMap);
					//新老股
					list8 = dictDao.getfinancingStockList(paramMap);
				}
				resultMap = new HashMap<String,Object>();
				resultMap.put("financeUnit", list6);
				resultMap.put("financingStatus", list7);
				resultMap.put("financingStock", list8);
			}
		}catch(Exception e){
			log.error(DictServiceImpl.class.getName() + "getDictionaryFinanceList",e);
			throw new ServiceException(e);
		}
		return resultMap;
	}

}
