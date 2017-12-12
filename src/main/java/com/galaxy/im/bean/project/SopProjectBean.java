package com.galaxy.im.bean.project;



import java.util.List;

import com.galaxy.im.common.NumberUtils;
import com.galaxy.im.common.db.PagableEntity;

public class SopProjectBean extends PagableEntity {

	private static final long serialVersionUID = -6962596197929111746L;
	
	private Long ideaId;						//创意ID
	private String projectName;					//项目名称
    private String projectCode;					//项目编码
    private String projectType;					//内部创建、外部投资
    private Integer stockTransfer;				//是否涉及股权转让，0表示不涉及，1表示涉及
    private String projectCareerline;			//项目所属投资事业线
    private Long projectDepartId;				//项目所属部门id
    private Long industryOwn;					//行业归属
    private Double projectValuations;			//初始估值
    private Double finalValuations;				//实际估值
    private String financeStatus;				//融资状态
    private Double projectContribution;			//初始投资额
    private Double finalContribution;			//实际投资额
    private Integer currencyUnit;				//资金单位，0表示人民币，1表示美元，2表示英镑，3表示欧元
    private Double projectShareRatio;			//所占股份百分比
	private Double finalShareRatio;				//实际所占股份百分比
	private Double serviceCharge;				//服务费
    private String projectCompany;				//公司名称
    private Long formationDate;					//公司成立时间
    private String companyLegal;				//公司法人
    private String projectCompanyCode;			//组织机构代码
    private Long createUid;						//项目创建者的用户ID
    private String createUname;					//项目创建者名
    private String createUposition;				//项目创建者的职位
    private String projectProgress;				//项目的当前所处阶段
    private String projectStatus;				//0:跟进中,1:投后运营,2:已否决,3:已退出
    private String projectDescribe;				//项目的概述
    private String projectDescribeFinancing;	//业务简要概述和项目亮点
    private String projectBusinessModel;		//项目的商业模式描述
    private String companyLocation;				//公司的发展定位
    private String userPortrait;				//用户画像，即服务客户的描述信息
    private String prospectAnalysis;			//尽情分析，即项目前景分析
    private String nextFinancingSource;			//下一轮融资路径
    private String industryAnalysis;			//行业分析
    private String operationalData;				//运营数据
    //private String faFlag;						//项目来源
    //private String faName;						//FA全名
    private String greanChannel;				//项目绿色通道标识(多通道支持,分割) 1：接触访谈，2：内部评审，3：CEO评审，4：立项会，5：投资意向书，6：尽职调查，7：投资决策会，8：投资协议，9：股权交割
    private String remark;						//备注
    private Long projectTime; 					//项目创建时间（数据库保存时间）
    private String financeMode;					//投资形式
    private String progressHistory;				//流程历史记录(逗号分割)
    private String businessTypeCode;			//业务类型编码：TZ-投资，ST-闪投
    
    private String createDate;
    private String formatContribution;
    private String formatValuations;
    private String formatUnit;
    private String formatShareRatio;
    private String formatFinalValuations;
    private String formatFinalContribution;
    private String formatFinalShareRatio;
    private Long projectDepartid;
    //in查询
    private List<Long> deptIdList;
    
    private Integer projectYjz;
    private String industry;
    
    private String projectTypeName;//项目类型名称
    
    private String projectProgressName; //项目阶段名称
    
    private String financeStatusName; //'融资状态'名称
        
    private String projectStatusName;  //项目状态名称;
    
    
    private String projectSource;			//项目来源
    private String projectSourceName;		//项目来源对应的填写项名称
    private List<Long> projectUserIds;		//项目承揽人id
    private String projectUserName;		//项目承揽人（非投资线员工名称）
    
	public Long getIdeaId() {
		return ideaId;
	}
	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public Integer getStockTransfer() {
		return stockTransfer;
	}
	public void setStockTransfer(Integer stockTransfer) {
		this.stockTransfer = stockTransfer;
	}
	public String getProjectCareerline() {
		return projectCareerline;
	}
	public void setProjectCareerline(String projectCareerline) {
		this.projectCareerline = projectCareerline;
	}
	public Long getIndustryOwn() {
		return industryOwn;
	}
	public void setIndustryOwn(Long industryOwn) {
		this.industryOwn = industryOwn;
	}
	public Double getProjectValuations() {
    	if(this.formatValuations != null && !"".equals(this.formatValuations.trim())){
			this.projectValuations = NumberUtils.toFormatNoSplitFour(this.formatValuations.trim());
		}
        return projectValuations;
    }
	public void setProjectValuations(Double projectValuations) {
		this.projectValuations = projectValuations;
	}
	public Double getFinalValuations() {
    	if(this.formatFinalValuations != null && !"".equals(this.formatFinalValuations.trim())){
			this.finalValuations = NumberUtils.toFormatNoSplitFour(this.formatFinalValuations.trim());
		}
       
		return finalValuations;
	}

	public void setFinalValuations(Double finalValuations) {
		this.finalValuations = finalValuations;
	}
	public String getFinanceStatus() {
		return financeStatus;
	}
	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}
	public Double getProjectContribution() {
    	if(this.formatContribution != null && !"".equals(this.formatContribution.trim())){
			this.projectContribution = NumberUtils.toFormatNoSplitFour(this.formatContribution.trim());
		}
        return projectContribution;
    }
	public void setProjectContribution(Double projectContribution) {
		this.projectContribution = projectContribution;
	}
	public Double getFinalContribution() {
		if(this.formatFinalContribution != null && !"".equals(this.formatFinalContribution.trim())){
			this.finalContribution = NumberUtils.toFormatNoSplitFour(this.formatFinalContribution.trim());
		}
		return finalContribution;
	}

	public void setFinalContribution(Double finalContribution) {
		this.finalContribution = finalContribution;
	}
	public Integer getCurrencyUnit() {
    	if(this.formatUnit != null && !"".equals(this.formatUnit.trim())){
			this.currencyUnit = Integer.parseInt(this.formatUnit.trim());
		}
        return currencyUnit;
    }
	public void setCurrencyUnit(Integer currencyUnit) {
		this.currencyUnit = currencyUnit;
	}
	public Double getProjectShareRatio() {
    	if(this.formatShareRatio != null && !"".equals(this.formatShareRatio.trim())){
			this.projectShareRatio = NumberUtils.toFormatNoSplitFour(this.formatShareRatio.trim());
		}
        return projectShareRatio;
    }
	public void setProjectShareRatio(Double projectShareRatio) {
		this.projectShareRatio = projectShareRatio;
	}
	public Double getFinalShareRatio() {
		if(this.formatFinalShareRatio != null && !"".equals(this.formatFinalShareRatio.trim())){
			this.finalShareRatio = NumberUtils.toFormatNoSplitFour(this.formatFinalShareRatio.trim());
		}
		
		return finalShareRatio;
	}

	public void setFinalShareRatio(Double finalShareRatio) {
		this.finalShareRatio = finalShareRatio;
	}
	public Double getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getProjectCompany() {
		return projectCompany;
	}
	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}
	public Long getFormationDate() {
		return formationDate;
	}
	public void setFormationDate(Long formationDate) {
		this.formationDate = formationDate;
	}
	public String getCompanyLegal() {
		return companyLegal;
	}
	public void setCompanyLegal(String companyLegal) {
		this.companyLegal = companyLegal;
	}
	public String getProjectCompanyCode() {
		return projectCompanyCode;
	}
	public void setProjectCompanyCode(String projectCompanyCode) {
		this.projectCompanyCode = projectCompanyCode;
	}
	public Long getCreateUid() {
		return createUid;
	}
	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}
	public String getCreateUname() {
		return createUname;
	}
	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}
	public String getCreateUposition() {
		return createUposition;
	}
	public void setCreateUposition(String createUposition) {
		this.createUposition = createUposition;
	}
	public String getProjectProgress() {
		return projectProgress;
	}
	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getProjectDescribe() {
		return projectDescribe;
	}
	public void setProjectDescribe(String projectDescribe) {
		this.projectDescribe = projectDescribe;
	}
	public String getProjectDescribeFinancing() {
		return projectDescribeFinancing;
	}
	public void setProjectDescribeFinancing(String projectDescribeFinancing) {
		this.projectDescribeFinancing = projectDescribeFinancing;
	}
	public String getProjectBusinessModel() {
		return projectBusinessModel;
	}
	public void setProjectBusinessModel(String projectBusinessModel) {
		this.projectBusinessModel = projectBusinessModel;
	}
	public String getCompanyLocation() {
		return companyLocation;
	}
	public void setCompanyLocation(String companyLocation) {
		this.companyLocation = companyLocation;
	}
	public String getUserPortrait() {
		return userPortrait;
	}
	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}
	public String getProspectAnalysis() {
		return prospectAnalysis;
	}
	public void setProspectAnalysis(String prospectAnalysis) {
		this.prospectAnalysis = prospectAnalysis;
	}
	public String getNextFinancingSource() {
		return nextFinancingSource;
	}
	public void setNextFinancingSource(String nextFinancingSource) {
		this.nextFinancingSource = nextFinancingSource;
	}
	public String getIndustryAnalysis() {
		return industryAnalysis;
	}
	public void setIndustryAnalysis(String industryAnalysis) {
		this.industryAnalysis = industryAnalysis;
	}
	public String getOperationalData() {
		return operationalData;
	}
	public void setOperationalData(String operationalData) {
		this.operationalData = operationalData;
	}
	
	public String getGreanChannel() {
		return greanChannel;
	}
	public void setGreanChannel(String greanChannel) {
		this.greanChannel = greanChannel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getProjectTime() {
		return projectTime;
	}
	public void setProjectTime(Long projectTime) {
		this.projectTime = projectTime;
	}
	public String getFinanceMode() {
		return financeMode;
	}
	public void setFinanceMode(String financeMode) {
		this.financeMode = financeMode;
	}
	public String getProgressHistory() {
		return progressHistory;
	}
	public void setProgressHistory(String progressHistory) {
		this.progressHistory = progressHistory;
	}
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}
	public Long getProjectDepartId() {
		return projectDepartId;
	}
	public void setProjectDepartId(Long projectDepartId) {
		this.projectDepartId = projectDepartId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFormatValuations() {
		return formatValuations;
	}
	public void setFormatValuations(String formatValuations) {
		this.formatValuations = formatValuations;
	}
	public String getFormatUnit() {
		return formatUnit;
	}
	public void setFormatUnit(String formatUnit) {
		this.formatUnit = formatUnit;
	}
	public String getFormatShareRatio() {
		return formatShareRatio;
	}
	public void setFormatShareRatio(String formatShareRatio) {
		this.formatShareRatio = formatShareRatio;
	}
	public String getFormatContribution() {
		return formatContribution;
	}
	public void setFormatContribution(String formatContribution) {
		this.formatContribution = formatContribution;
	}
	public String getFormatFinalValuations() {
		return formatFinalValuations;
	}
	public void setFormatFinalValuations(String formatFinalValuations) {
		this.formatFinalValuations = formatFinalValuations;
	}
	public String getFormatFinalContribution() {
		return formatFinalContribution;
	}
	public void setFormatFinalContribution(String formatFinalContribution) {
		this.formatFinalContribution = formatFinalContribution;
	}
	public String getFormatFinalShareRatio() {
		return formatFinalShareRatio;
	}
	public void setFormatFinalShareRatio(String formatFinalShareRatio) {
		this.formatFinalShareRatio = formatFinalShareRatio;
	}
	public Long getProjectDepartid() {
		return projectDepartid;
	}
	public void setProjectDepartid(Long projectDepartid) {
		this.projectDepartid = projectDepartid;
	}
	public List<Long> getDeptIdList() {
		return deptIdList;
	}
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	public Integer getProjectYjz() {
		return projectYjz;
	}
	public void setProjectYjz(Integer projectYjz) {
		this.projectYjz = projectYjz;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	public String getProjectProgressName() {
		return projectProgressName;
	}
	public void setProjectProgressName(String projectProgressName) {
		this.projectProgressName = projectProgressName;
	}
	public String getFinanceStatusName() {
		return financeStatusName;
	}
	public void setFinanceStatusName(String financeStatusName) {
		this.financeStatusName = financeStatusName;
	}
	public String getProjectStatusName() {
		return projectStatusName;
	}
	public void setProjectStatusName(String projectStatusName) {
		this.projectStatusName = projectStatusName;
	}
	public String getProjectSource() {
		return projectSource;
	}
	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}
	public String getProjectSourceName() {
		return projectSourceName;
	}
	public void setProjectSourceName(String projectSourceName) {
		this.projectSourceName = projectSourceName;
	}
	public List<Long> getProjectUserIds() {
		return projectUserIds;
	}
	public void setProjectUserIds(List<Long> projectUserIds) {
		this.projectUserIds = projectUserIds;
	}
	public String getProjectUserName() {
		return projectUserName;
	}
	public void setProjectUserName(String projectUserName) {
		this.projectUserName = projectUserName;
	}
	
}