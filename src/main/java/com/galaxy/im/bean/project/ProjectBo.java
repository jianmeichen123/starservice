package com.galaxy.im.bean.project;

import java.util.List;

public class ProjectBo extends SopProjectBean {
	
	private static final long serialVersionUID = 1L;

	private String extendFiled;// 业务对象中扩展的字段
	
	private String nameLike;//模糊查询条件匹配
	private String resultCloseFilter;//过滤已关闭项目
	
	private List<String>  ids;//业务扩展字段---项目ids
	
	private String partnerName;//合伙人姓名
	
	private String meetingType;
	
	
	private Long createDateFrom;
	private Long createDateThrough;
	
	private String rComplany;//查询字段
	
	private Double bComplany;//最大查询值
	
	private Double aComplany;//最小查询值
	
	private String cascOrDes;//升降序的字段
	
	private String ascOrDes;//加入的是升降序  desc asc
	
	private String proType;//我的项目|所属事业线项目;1:我的项目;2:事业线项目
	
	private List<String> projectProgressList;
	
	private boolean fromIdea;
	
	private String sorting;//多个排序字段
	
	private String thyyFlag;
	
	private Integer sflag;
	
	private Long uId;
	
	/**
	 * 新增投资经理查询全部项目
	 */
	private String quanbu;
	
	/**
	 * 模糊查询关键字
	 */
	protected String ceeword;
	
	private List<String> projectIdList;			//项目id集合
	
	
	
	public String getCeeword() {
		return ceeword;
	}

	public void setCeeword(String ceeword) {
		this.ceeword = ceeword;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getrComplany() {
		return rComplany;
	}

	public void setrComplany(String rComplany) {
		this.rComplany = rComplany;
	}

	public Double getbComplany() {
		return bComplany;
	}

	public void setbComplany(Double bComplany) {
		this.bComplany = bComplany;
	}

	public Double getaComplany() {
		return aComplany;
	}

	public void setaComplany(Double aComplany) {
		this.aComplany = aComplany;
	}

	public String getCascOrDes() {
		return cascOrDes;
	}

	public void setCascOrDes(String cascOrDes) {
		this.cascOrDes = cascOrDes;
	}

	public String getAscOrDes() {
		return ascOrDes;
	}

	public void setAscOrDes(String ascOrDes) {
		this.ascOrDes = ascOrDes;
	}

	public Long getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Long createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Long getCreateDateThrough() {
		return createDateThrough;
	}

	public void setCreateDateThrough(Long createDateThrough) {
		this.createDateThrough = createDateThrough;
	}

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
	}

	public String getNameLike() {
		return nameLike == null ? null : nameLike.trim();
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike == null ? null : nameLike.trim();
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}
	
	public String getResultCloseFilter() {
		return resultCloseFilter;
	}

	public void setResultCloseFilter(String resultCloseFilter) {
		this.resultCloseFilter = resultCloseFilter;
	}

	public List<String> getProjectProgressList() {
		return projectProgressList;
	}

	public void setProjectProgressList(List<String> projectProgressList) {
		this.projectProgressList = projectProgressList;
	}

	public boolean isFromIdea() {
		return fromIdea;
	}

	public void setFromIdea(boolean fromIdea) {
		this.fromIdea = fromIdea;
	}

	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

	public String getThyyFlag() {
		return thyyFlag;
	}

	public void setThyyFlag(String thyyFlag) {
		this.thyyFlag = thyyFlag;
	}

	public Integer getSflag() {
		return sflag;
	}

	public void setSflag(Integer sflag) {
		this.sflag = sflag;
	}

	public String getQuanbu() {
		return quanbu;
	}

	public void setQuanbu(String quanbu) {
		this.quanbu = quanbu;
	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public List<String> getProjectIdList() {
		return projectIdList;
	}

	public void setProjectIdList(List<String> projectIdList) {
		this.projectIdList = projectIdList;
	}
	
	
	
	
}