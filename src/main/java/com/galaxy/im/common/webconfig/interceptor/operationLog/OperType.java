package com.galaxy.im.common.webconfig.interceptor.operationLog;

/**
 * @Description: 操作类型 <br/>
 * @author keifer
 * @date 2016年3月16日
 */
public enum OperType {

	ADD("添加"), SUBMIT("提交"), DOWNLOAD("下载"), UPLOAD("上传"),DELETE("删除"),
	UPDATE("更新"), REMINDER("催办"),CLAIMT("领取"),FINISH("完成"),
	APPLY("移交"), REVOKE("撤销移交"),RECIEIVE("接收"),REFUSE("拒接"),BREAK("否决"),GIVEUP("放弃"),ASSIGNED("指派");
	
	private String operationType;

	private OperType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationType() {
		return operationType;
	}

}
