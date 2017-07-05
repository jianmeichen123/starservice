package com.galaxy.im.common.enums;

public enum CodeEnum {
	/**
	 * 2017/3/3修改 枚举
	 */
/*	o2oDS(11, "O2O及电商", 10),
	bigdata(8, "大数据云计算", 11),
	internetEducation(14, "互联网教育", 12),
	internetSteel(25, "互联网钢铁", 13),
	socialMedia(16, "媒体社交", 14),
	internetAgriculture(12, "互联网农业", 15),
	internetChemical(24, "互联网化工", 16),
	internetDigital(5, "数字娱乐", 17),
	internetCatering(7, "互联网餐饮", 18),
	internetClothing(15, "互联网服装", 19),
	internetLand(26, "互联网房地产", 20),
	intelligentDevices(13, "智能设备", 21),
	internetFinance(6, "互联网金融", 22),
	internetCar(17, "互联网汽车", 23),
	internetOfThings(16, "物联网", 24),
	internetMedical(9, "互联网医疗", 25), 
	internetTourism(10, "互联网旅游", 26),
	artificialIntelligence(13, "人工智能事业部", 27),
	enterpriseService(28, "企业服务", 28),
	rongKuai(29, "融快事业部", 29),
	
	
   virtualReality(31, "虚拟现实", 31),
	
	
	RKo2o(2000, "融快-O2O及电商", 61),
	RKjr(2001, "融快-互联网金融", 62),
	RKly(2002, "融快-互联网旅游", 63),
	RKcy(2003, "融快-互联网餐饮", 64),
	RKjy(2004, "融快-互联网教育", 65),
	RKny(2005, "融快-互联网农业", 66),
	RKyl(2006, "融快-数字娱乐", 67),
	RKzn(2007, "融快-人工智能", 68),
	RKgh(2008, "融快-互联网钢化", 69),
	RKfz(2009, "融快-互联网服装", 70),
	RKdc(2010, "融快-互联网房地产", 71),
	RKdsj(2011, "融快-云计算大数据", 72),
	RKqyfw(2012, "融快-企业服务", 73),
	RKmt(2013, "融快-媒体社交", 74),
	RKjk(2014, "融快-互联网健康", 75),
	RKqc(2015, "融快-互联网汽车", 76),
	RKzt(2016, "融快-中台业务部", 77),
	
	
	CBLo2o(3000, "创保联-O2O及电商", 31),
	CBLjr(3001, "创保联-互联网金融", 32),
	CBLly(3002, "创保联-互联网旅游", 33),
	CBLcy(3003, "创保联-互联网餐饮", 34),
	CBLjy(3004, "创保联-互联网教育", 35),
	CBLny(3005, "创保联-互联网农业", 36),
	CBLyl(3006, "创保联-数字娱乐", 37),
	CBLzn(3007, "创保联-人工智能", 38),
	CBLgh(3008, "创保联-互联网钢化", 39),
	CBLfz(3009, "创保联-互联网服装", 40),
	CBLdc(3010, "创保联-互联网房地产", 41),
	CBLdsj(3011, "创保联-云计算大数据", 42),
	CBLqyfw(3012, "创保联-企业服务", 43),
	CBLmt(3013, "创保联-媒体社交", 44),
	CBLjk(3014, "创保联-互联网健康", 45),
	CBLqc(3015, "创保联-互联网汽车", 46);*/
	//o2oDS(11, "O2O及电商", 10),
	o2oDS(11, "电子商务", 10),
	
	//socialMedia(16, "媒体社交", 14),
	socialMedia(16, "媒体营销", 14),
	
	internetTourism(10, "互联网旅游", 26),
	
	/**
	 * 部门调整补充:
	成长投资-人工智能  -->	 大数据部
	成长投资-金融   -->    金融科技
	*/
	bigSJ(2011, "大数据部", 80),
	
	internetAgriculture(12, "互联网农业", 15),
	
	//bigdata(8, "大数据云计算", 11),
	bigdata(8, "云计算服务部", 11),
	
	//virtualReality(31, "虚拟现实", 31),
	virtualReality(31, "虚拟现实/增强", 31),
	
	enterpriseService(28, "企业服务", 28),
	
	//---internetEducation(14, "互联网教育", 12),
	//---internetEducation2(32, "互联网教育二部", 32),
	
	internetCar(17, "互联网汽车", 23),
	
	internetDigital(5, "数字娱乐", 17),
	
	/**
	 * 部门调整补充:
	成长投资-人工智能  -->	 大数据部
	成长投资-金融   -->    金融科技
	*/
	tecJR(2001, "金融科技", 81),
	
	
	//internetMedical(9, "互联网医疗", 25), 
	internetMedical(9, "互联网健康", 25), 
	
	//internetLand(26, "互联网房地产", 20),
	internetLand(26, "互联网房产", 20),
	
	//internetFinance(6, "互联网金融", 22),
	internetFinance(6, "互联网银行", 22),
	
	artificialIntelligence(13, "人工智能", 27),
	
	//internetCatering(7, "互联网餐饮", 18),
	
	//internetClothing(15, "互联网服装", 19),
	
	/*intelligentDevices(13, "智能设备", 21),*/
	
	/*internetOfThings(16, "物联网", 24),*/
	
	//internetMedical2(30, "互联网医疗二部", 30),
	
	
	/**
	 * 部门调整新加:
	互联网物流
	工业互联网
	物联网
	前沿科技
	*/
	interWL(3005, "互联网物流", 82),
	interGY(3006, "工业互联网", 83),
	netWL(3007, "物联网", 84),
	tecQY(3008, "前沿科技", 85),
	

	
	//internetSteel(25, "互联网钢铁", 13),
	//internetChemical(24, "互联网化工", 16),
	rongKuai(29, "融快事业部", 29),

	
	
/*	RKo2o(2000, "融快-O2O及电商", 61),
	RKjr(2001, "融快-互联网金融", 62),
	RKly(2002, "融快-互联网旅游", 63),
	RKcy(2003, "融快-互联网餐饮", 64),
	RKjy(2004, "融快-互联网教育", 65),
	RKny(2005, "融快-互联网农业", 66),
	RKyl(2006, "融快-数字娱乐", 67),
	RKzn(2007, "融快-人工智能", 68),
	RKgh(2008, "融快-互联网钢化", 69),
	RKfz(2009, "融快-互联网服装", 70),
	RKdc(2010, "融快-互联网房地产", 71),
	RKdsj(2011, "融快-云计算大数据", 72),
	RKqyfw(2012, "融快-企业服务", 73),
	RKmt(2013, "融快-媒体社交", 74),
	RKjk(2014, "融快-互联网健康", 75),
	RKqc(2015, "融快-互联网汽车", 76),*/
	RKzt(2016, "融快-中台业务部", 77),
	
	CBLjy(3004, "创保联-互联网教育", 35);
	/*CBLo2o(3000, "创保联-O2O及电商", 31),
	CBLjr(3001, "创保联-互联网金融", 32),
	CBLly(3002, "创保联-互联网旅游", 33),
	CBLcy(3003, "创保联-互联网餐饮", 34),
	
	CBLny(3005, "创保联-互联网农业", 36),
	CBLyl(3006, "创保联-数字娱乐", 37),
	CBLzn(3007, "创保联-人工智能", 38),
	CBLgh(3008, "创保联-互联网钢化", 39),
	CBLfz(3009, "创保联-互联网服装", 40),
	CBLdc(3010, "创保联-互联网房地产", 41),
	CBLdsj(3011, "创保联-云计算大数据", 42),
	CBLqyfw(3012, "创保联-企业服务", 43),
	CBLmt(3013, "创保联-媒体社交", 44),
	CBLjk(3014, "创保联-互联网健康", 45),
	CBLqc(3015, "创保联-互联网汽车", 46);*/
	
	
	private long id;
	private String careerLine;
	private int code;
	
	private CodeEnum(long id, String careerLine, int code){
		this.id = id;
		this.careerLine = careerLine;
		this.code = code;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCareerLine() {
		return careerLine;
	}
	public void setCareerLine(String careerLine) {
		this.careerLine = careerLine;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
