package com.galaxy.im.bean.invest;

import com.galaxy.im.common.db.BaseEntity;

public class InvestBean extends BaseEntity {

	/**
	 * 投资方
	 */
	private static final long serialVersionUID = 2692500358849192889L;
	private int deliveryType;						//0-独投;1-领投;2-合投
	private String deliveryName;					//合投机构名称
	private Double deliveryAmount;					//投资金额
	private Double deliveryShareRatio;			//股权占比
	private int projectId;							//项目ID
	private int createUid;							//创建人
	public int getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(int deliveryType) {
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
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getCreateUid() {
		return createUid;
	}
	public void setCreateUid(int createUid) {
		this.createUid = createUid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


	
	
	
}
