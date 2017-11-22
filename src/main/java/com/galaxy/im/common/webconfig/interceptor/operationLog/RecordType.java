package com.galaxy.im.common.webconfig.interceptor.operationLog;

/**
 * @Description: 数据库存储的每条记录的类型：用于区分是创意还是项目
 */
public enum RecordType {

	PROJECT((byte) 0, "项目"), 
	IDEAS((byte) 1, "创意"), 
	IDEAZIXUN((byte) 3, "创意资讯"), 
	OPERATION_MEETING((byte) 2, "运营会议"),
	TASK((byte) 4, "任务");

	private byte type;
	private String name;

	private RecordType(byte type, String name) {
		this.type = type;
		this.name = name;
	}

	public byte getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public static String getName(byte type) {
		RecordType[] rtypes = RecordType.values();
		String result = "";
		for (RecordType rtype : rtypes) {
			if (type == rtype.getType()) {
				result = rtype.getName();
				break;
			}
		}
		return result;
	}
}