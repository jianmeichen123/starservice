package com.galaxy.im.bean.invest;

import com.galaxy.im.common.db.BaseEntity;

public class InvestBean extends BaseEntity {

	/**
	 * 投资方
	 */
	private static final long serialVersionUID = 2692500358849192889L;
	private String deliveryType;						//0-独投;1-领投;2-合投
	private String deliveryName;					//合投机构名称
	private Double deliveryAmount;					//投资金额
	private Double deliveryShareRatio;			//股权占比
	private Long projectId;							//项目ID
	private Long createUid;							//创建人
	
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public Double getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(Double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	public Double getDeliveryShareRatio() {
		return deliveryShareRatio;
	}
	public void setDeliveryShareRatio(Double deliveryShareRatio) {
		this.deliveryShareRatio = deliveryShareRatio;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getCreateUid() {
		return createUid;
	}
	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


	
	
	
}
