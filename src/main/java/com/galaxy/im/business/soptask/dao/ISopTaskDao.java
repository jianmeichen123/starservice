package com.galaxy.im.business.soptask.dao;

import java.util.Map;

import com.galaxy.im.bean.soptask.SopTask;
import com.galaxy.im.bean.soptask.SopTaskRecord;
import com.galaxy.im.bean.talk.SopFileBean;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.QPage;

public interface ISopTaskDao extends IBaseDao<SopTask, Long>{

	//待办列表
	QPage taskListByRole(Map<String, Object> paramMap);

	//获取部门id
	long getDepId(Long id);

	//待办任务详情
	Map<String, Object> taskInfo(Map<String, Object> paramMap);

	//认领
	int applyTask(SopTask sopTask);

	//指派/移交
	int taskTransfer(SopTaskRecord sopTaskRecord);

	//修改待办任务
	int updateTask(SopTask sopTask);

	//A是否上传报告
	SopFileBean isUpload(SopFileBean sopFileBean);

	//更新文件信息
	int updateFile(SopFileBean sopFileBean);

	//防止重复移交
	SopTaskRecord selectRecord(SopTaskRecord sopTaskRecord);

}
