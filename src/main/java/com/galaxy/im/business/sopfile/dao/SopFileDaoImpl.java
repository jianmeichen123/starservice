package com.galaxy.im.business.sopfile.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class SopFileDaoImpl extends BaseDaoImpl<Test, Long> implements ISopFileDao{

	@Override
	public Map<String, Object> getBusinessPlanFile(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
