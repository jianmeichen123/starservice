package com.galaxy.im.bean.clouddisk;

import com.galaxy.im.common.db.PagableEntity;

public class CloudDiskInfo extends PagableEntity{
	private static final long serialVersionUID = 3313679340823210387L;
	private long userId;
	private long allVolume;
	private long usedVolume;
	private int isValid;
	
	public CloudDiskInfo(){}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAllVolume() {
		return allVolume;
	}

	public void setAllVolume(long allVolume) {
		this.allVolume = allVolume;
	}

	public long getUsedVolume() {
		return usedVolume;
	}

	public void setUsedVolume(long usedVolume) {
		this.usedVolume = usedVolume;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
}
