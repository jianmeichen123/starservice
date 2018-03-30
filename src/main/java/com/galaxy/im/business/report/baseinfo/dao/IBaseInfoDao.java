package com.galaxy.im.business.report.baseinfo.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.im.bean.Test;
import com.galaxy.im.bean.project.InformationResult;
import com.galaxy.im.bean.report.InformationDictionary;
import com.galaxy.im.bean.report.InformationFixedTable;
import com.galaxy.im.bean.report.InformationListdata;
import com.galaxy.im.bean.report.InformationListdataRemark;
import com.galaxy.im.bean.report.InformationTitle;
import com.galaxy.im.common.db.IBaseDao;

public interface IBaseInfoDao extends IBaseDao<Test, Long>{

	List<InformationTitle> selectChildsByPid(Map<String, Object> params);

	List<InformationListdata> getInformationListdata(InformationListdata listdataQuery);

	List<InformationDictionary> getInformationDictionary();

	List<InformationResult> getInformationResult(InformationResult resultQuery);

	List<InformationFixedTable> getInformationFixedTable(InformationFixedTable fixedTableQuery);

	List<InformationListdataRemark> getInformationListdataRemark(InformationListdataRemark headerQuery);

}
