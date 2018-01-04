package com.galaxy.im.common.webconfig.interceptor.operationLog;

/**
 * @Description: sop流程中操作日志的规范类
 */
public enum OperationLogType {
	
	//项目访谈、更新
	interview_add("/projectTalk/addProjectTalk/"+UrlNumber.one, OperType.ADD.getOperationType(), "接触访谈",SopStage.TOUCH_INTERVIEW.getStageName()), 
    interview_update("/projectTalk/addProjectTalk/"+UrlNumber.two, OperType.UPDATE.getOperationType(), "接触访谈",SopStage.TOUCH_INTERVIEW.getStageName()), 
	
    //项目会议添加、更新
    meeting_1_add("/meeting/addMeetingRecord/"+UrlNumber.one, OperType.ADD.getOperationType(), "内部评审会议记录",SopStage.INNER_REVIEW_SCHEDULE.getStageName()), 
   	meeting_1_update("/meeting/addMeetingRecord/"+UrlNumber.two, OperType.UPDATE.getOperationType(), "内部评审会议记录",SopStage.INNER_REVIEW_SCHEDULE.getStageName()), 
   	
    meeting_2_add("/meeting/addMeetingRecord/"+UrlNumber.three, OperType.ADD.getOperationType(), "CEO评审会议记录",SopStage.CEO_REVIEW_SCHEDULE.getStageName()), 
   	meeting_2_update("/meeting/addMeetingRecord/"+UrlNumber.four, OperType.UPDATE.getOperationType(), "CEO评审会议记录",SopStage.CEO_REVIEW_SCHEDULE.getStageName()), 
   	
    meeting_3_add("/meeting/addMeetingRecord/"+UrlNumber.five, OperType.ADD.getOperationType(), "立项会会议记录",SopStage.PROJECT_SCHEDULE.getStageName()), 
   	meeting_3_update("/meeting/addMeetingRecord/"+UrlNumber.six, OperType.UPDATE.getOperationType(), "立项会会议记录",SopStage.PROJECT_SCHEDULE.getStageName()), 
   	
    meeting_4_add("/meeting/addMeetingRecord/"+UrlNumber.seven, OperType.ADD.getOperationType(), "会后商务谈判会议记录",SopStage.SW_TP.getStageName()), 
   	meeting_4_update("/meeting/addMeetingRecord/"+UrlNumber.eight, OperType.UPDATE.getOperationType(), "会后商务谈判会议记录",SopStage.SW_TP.getStageName()), 
   	
   	meeting_5_add("/meeting/addMeetingRecord/"+UrlNumber.nine, OperType.ADD.getOperationType(), "投决会会议记录",SopStage.VOTE_DECISION_MEETING.getStageName()), 
   	meeting_5_update("/meeting/addMeetingRecord/"+UrlNumber.ten, OperType.UPDATE.getOperationType(), "投决会会议记录",SopStage.VOTE_DECISION_MEETING.getStageName()),
  	
   	//	业务尽职调查报告      		fileWorktype:1  
   	//	尽职调查启动会报告    	fileWorktype:18 
   	//	尽职调查总结会报告    	fileWorktype:19
  	p6_f1_ywjd_up("/flow/duediligence/uploadDuediligence/"+UrlNumber.one, OperType.UPLOAD.getOperationType(), 
  			"业务尽职调查报告",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
  	p6_f1_ywjd_edit("/flow/duediligence/uploadDuediligence/"+UrlNumber.two, OperType.UPDATE.getOperationType(), 
  			"业务尽职调查报告",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
  	p6_f18_jzqd_up("/flow/duediligence/uploadDuediligence/"+UrlNumber.three, OperType.UPLOAD.getOperationType(), 
  			"尽职调查启动会报告",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
  	p6_f18_jzqd_edit("/flow/duediligence/uploadDuediligence/"+UrlNumber.four, OperType.UPDATE.getOperationType(), 
  			"尽职调查启动会报告",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
  	p6_f19_jzzj_up("/flow/duediligence/uploadDuediligence/"+UrlNumber.five, OperType.UPLOAD.getOperationType(), 
  			"尽职调查总结会报告",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
  	p6_f19_jzzj_edit("/flow/duediligence/uploadDuediligence/"+UrlNumber.six, OperType.UPDATE.getOperationType(), 
  			"尽职调查总结会报告",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
  	
  	//  投资意向书        fileWorktype:5
  	p5_f5_tzxys_up("/flow/investmentintent/uploadInvestmentintent/"+UrlNumber.one, OperType.UPLOAD.getOperationType(), 
  			"投资意向书",SopStage.INVESTMENT_INTENT.getStageName()),
  	p5_f5_tzxys_edit("/flow/investmentintent/uploadInvestmentintent/"+UrlNumber.two, OperType.UPDATE.getOperationType() , 
  			"投资意向书",SopStage.INVESTMENT_INTENT.getStageName()),
  	
  	//	投资协议             fileWorktype:6
  	//	股权转让协议     fileWorktype:7
  	p8_f6_tzxy_up("/flow/investmentPolicy/uploadInvestmentPolicy/"+UrlNumber.one, OperType.UPLOAD.getOperationType(), 
  			"投资协议",SopStage.INVESTMENT_AGREEMENT.getStageName()),
  	p8_f6_tzxy_edit("/flow/investmentPolicy/uploadInvestmentPolicy/"+UrlNumber.two, OperType.UPDATE.getOperationType(), 
  			"投资协议",SopStage.INVESTMENT_AGREEMENT.getStageName()),
  	p8_f7_gqzr_up("/flow/investmentPolicy/uploadInvestmentPolicy/"+UrlNumber.three, OperType.UPLOAD.getOperationType(), 
  			"股权转让协议",SopStage.INVESTMENT_AGREEMENT.getStageName()),
  	p8_f7_gqzr_edit("/flow/investmentPolicy/uploadInvestmentPolicy/"+UrlNumber.four, OperType.UPDATE.getOperationType(), 
  			"股权转让协议",SopStage.INVESTMENT_AGREEMENT.getStageName()),
  	
	//  立项报告     	fileWorktype:17  
	p5_f17_lxhbg_up("/flow/projectapproval/uploadApprovalReport/"+UrlNumber.one, OperType.UPLOAD.getOperationType(), 
			"立项报告",SopStage.PROJECT_SCHEDULE.getStageName()),
	p5_f17_lxhbg_edit("/flow/projectapproval/uploadApprovalReport/"+UrlNumber.two, OperType.UPDATE.getOperationType() , 
			"立项报告",SopStage.PROJECT_SCHEDULE.getStageName()),
	
	//	工商转让凭证             fileWorktype:8
  	//	资金拨付凭证             fileWorktype:9
  	p9_f8_gszrpz_up("/flow/stockequity/uploadStockequity/"+UrlNumber.one, OperType.UPLOAD.getOperationType(), 
  			"工商转让凭证",SopStage.EQUITY_DELIVERY_STAGE.getStageName()),
  	p9_f8_gszrpz_edit("/flow/stockequity/uploadStockequity/"+UrlNumber.two, OperType.UPDATE.getOperationType(), 
  			"工商转让凭证",SopStage.EQUITY_DELIVERY_STAGE.getStageName()),
  	p9_f9_zjbfpz_up("/flow/stockequity/uploadStockequity/"+UrlNumber.three, OperType.UPLOAD.getOperationType(), 
  			"资金拨付凭证",SopStage.EQUITY_DELIVERY_STAGE.getStageName()),
  	p9_f9_zjbfpz_edit("/flow/stockequity/uploadStockequity/"+UrlNumber.four, OperType.UPDATE.getOperationType(), 
  			"资金拨付凭证",SopStage.EQUITY_DELIVERY_STAGE.getStageName()),
	
	//点击‘启动内部评审’
	SUBMIT_INNER_REVIEW_SCHEDULE("/flow/interview/startInternalreview", OperType.SUBMIT.getOperationType(), "内部评审",SopStage.TOUCH_INTERVIEW.getStageName()), 
	//点击‘申请CEO评审排期’
	SUBMIT_CEO_SCHEDULE("/flow/internalreview/startCeoReview", OperType.SUBMIT.getOperationType(), "CEO评审排期",SopStage.INNER_REVIEW_SCHEDULE.getStageName()), 
	//点击申请立项会排期
	ADD_PROJECT_SCHEDULE("/flow/ceoreview/startRovalReview", OperType.SUBMIT.getOperationType(), "立项会排期",SopStage.CEO_REVIEW_SCHEDULE.getStageName()), 
	//进入会后商务谈判
	TO_SWTP("/flow/projectapproval/startBusinessNegotiation", OperType.SUBMIT.getOperationType(), "会后商务谈判",SopStage.PROJECT_SCHEDULE.getStageName()), 
	//点击申请投决会排期	
	APPLY_VOTE_SCHEDULE("/flow/duediligence/startInvestmentdeal", OperType.SUBMIT.getOperationType(), "投决会排期",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
	//签订投资意向书（投资）
	TO_TZ("/flow/businessnegotiation/startInvestmentIntent", OperType.SUBMIT.getOperationType(), "签订投资意向书（投资）",SopStage.SWTP.getStageName()),	
	//签订投资协议书（闪投）
	TO_ST("/flow/businessnegotiation/startInvestmentPolicy", OperType.SUBMIT.getOperationType(), "签订投资协议书（闪投）",SopStage.SWTP.getStageName()),	
	//签订投资意向书（投资）-> 尽职调查
	TZ2JADC("/flow/investmentintent/startDuediligence", OperType.SUBMIT.getOperationType(), "尽职调查",SopStage.INVESTMENT_INTENT.getStageName()),	
	//签订投资协议书（闪投）-> 尽职调查
	ST2JADC("/flow/investmentPolicy/startDuediligence", OperType.SUBMIT.getOperationType(), "尽职调查",SopStage.INVESTMENT_AGREEMENT.getStageName()),	
	//投决会->投资协议
	TJH2JADC("/flow/investmentdeal/startInvestmentpolicy", OperType.SUBMIT.getOperationType(), "签订投资协议书",SopStage.VOTE_DECISION_MEETING.getStageName()),
	//投决会 -> 股权交割 
	TJH2GQJG("/flow/investmentdeal/startStockequity", OperType.SUBMIT.getOperationType(), "股权交割",SopStage.VOTE_DECISION_MEETING.getStageName()),
	//投资协议 -> 股权交割
	TZXY2GQJG("/flow/investmentPolicy/startStockequity", OperType.SUBMIT.getOperationType(), "股权交割",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//投后运营
	THYY("/flow/stockequity/startIntervestoperate", OperType.SUBMIT.getOperationType(), "投后运营",SopStage.EQUITY_DELIVERY_STAGE.getStageName()),
	
	//否决项目日志
	P1_BREAK_PROJECT("/flow/interview/votedown", OperType.BREAK.getOperationType(), null,"否决项目"),
	P2_BREAK_PROJECT("/flow/businessnegotiation/votedown", OperType.BREAK.getOperationType(), null,"否决项目"),
	P3_BREAK_PROJECT("/flow/ceoreview/votedown", OperType.BREAK.getOperationType(), null,"否决项目"),
	P4_BREAK_PROJECT("/flow/duediligence/votedown", OperType.BREAK.getOperationType(), null,"否决项目"),
	P5_BREAK_PROJECT("/flow/internalreview/votedown", OperType.BREAK.getOperationType(), null,"否决项目"),
	P6_BREAK_PROJECT("/flow/investmentdeal/votedown", OperType.BREAK.getOperationType(), null,"否决项目"),
	P7_BREAK_PROJECT("/flow/projectapproval/votedown", OperType.BREAK.getOperationType(), null,"否决项目"),
	
	//代办任务（领取，移交，指派，放弃）
	CLAIMT_TASK("/soptask/applyTask", OperType.CLAIMT.getOperationType(), null,null),
	APPLY_TASK("/soptask/taskTransfer/"+UrlNumber.one, OperType.APPLY.getOperationType(), null,null),
	GIVEUP_TASK("/soptask/abandonTask", OperType.GIVEUP.getOperationType(),null,null),
	ASSIGNED_TASK("/soptask/taskTransfer/"+UrlNumber.two, OperType.ASSIGNED.getOperationType(),null,null),
	
	//投后运营会议纪要
	pos_meeting_add("/posmeeting/addPosMeetingRecord/"+UrlNumber.one, OperType.ADD.getOperationType(), "投后运营会议记录",SopStage.EQUITY_DELIVERY_END.getStageName()), 
	pos_meeting_update("/posmeeting/addPosMeetingRecord/"+UrlNumber.two, OperType.UPDATE.getOperationType(), "投后运营会议记录",SopStage.EQUITY_DELIVERY_END.getStageName()),
	
	//项目删除
	project_del("/project/delProject", OperType.DELETE.getOperationType(),"项目删除",null),
	project_apply("/project/projectTransfer"+UrlNumber.one, OperType.APPLY.getOperationType(),"项目移交",null),
	project_assigned("/project/projectTransfer"+UrlNumber.two, OperType.ASSIGNED.getOperationType(),"项目指派",null),
   	;
	
	
	//-------------------------------------------操作部分-----------------------------------------------------------------
	private OperationLogType(String uniqueKey, String type, String content, String sopstage) {
		this.uniqueKey = uniqueKey;
		this.type = type;
		this.content = content;
		this.sopstage = sopstage;
	}

	public static OperationLogType getObject(String uniqueKey) {
		OperationLogType[] types = OperationLogType.values();
		OperationLogType result = null;
		for (OperationLogType type : types) {
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

	private String uniqueKey;
	private String type;			//操作类型
	private String content;			//内容
	private String sopstage;		//sop阶段

	public String getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public String getSopstage() {
		return sopstage;
	}

}
