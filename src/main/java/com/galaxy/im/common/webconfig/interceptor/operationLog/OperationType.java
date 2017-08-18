package com.galaxy.im.common.webconfig.interceptor.operationLog;

/**
 * 
 * @Description: 消息提醒规范类型
 *
 */
public enum OperationType {
	
	MEETING_ADD			("/meeting/addMeetingRecord",PlatformConst.PROJECT_TYPE, "添加");
	
	
	private OperationType(String uniqueKey, String type, String content, Integer module) {
		this.uniqueKey = uniqueKey;
		this.type = type;
		this.content = content;
		this.module = module;
	}

	private OperationType(String uniqueKey, String type, String content) {
		this.uniqueKey = uniqueKey;
		this.type = type;
		this.content = content;
	}

	public static OperationType getObject(String uniqueKey) {
		OperationType[] types = OperationType.values();
		OperationType result = null;
		for (OperationType type : types) {
			if (type.getUniqueKey()!=null && type.getUniqueKey().trim().length()>0){
				
				String requestNum = uniqueKey.substring(uniqueKey.lastIndexOf("/"));
				String localNum = type.getUniqueKey().substring(type.getUniqueKey().lastIndexOf("/"));
				
				if(requestNum.equals(localNum)){
					if ( uniqueKey.substring(0,uniqueKey.lastIndexOf("/")).contains(type.getUniqueKey().substring(0,type.getUniqueKey().lastIndexOf("/")))) {
						result = type;
						break;
					}
				}else if ( uniqueKey.contains(type.getUniqueKey())) {
					result = type;
					break;
				}
			}
		}
		return result;
	}

	private String type;
	private String content;
	private String uniqueKey;
	private Integer module;

	public String getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public Integer getModule() {
		return module;
	}

	public static Integer getModule(Long roleId) {
		if (null == roleId)
			return null;
		if (roleId.longValue() == 1 || roleId.longValue() == 2) {// 高管：董事长，CEO
			return PlatformConst.MODULE_BROADCAST_MESSAGE;
		}
		return null;
	}

}
