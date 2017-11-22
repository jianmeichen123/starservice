package com.galaxy.im.bean.operationLog;

import com.galaxy.im.common.db.BaseEntity;
import com.galaxy.im.common.webconfig.interceptor.operationLog.RecordType;

public class RecordEntity extends BaseEntity implements RecordBean {

	private static final long serialVersionUID = 1L;
	protected byte recordType = RecordType.PROJECT.getType();
	
	private Long recordId; 	//记录id
	@Override
	public byte getRecordType() {
		return recordType;
	}

	@Override
	public void setRecordType(byte recordType) {
		this.recordType = recordType;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

}
