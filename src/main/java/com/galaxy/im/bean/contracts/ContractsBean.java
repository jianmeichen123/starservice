package com.galaxy.im.bean.contracts;

import com.galaxy.im.common.db.PagableEntity;

public class ContractsBean extends PagableEntity{
	private static final long serialVersionUID = 1887839691660971786L;
	private String name;
	private String phone1;
	private String phone2;
	private String phone3;
	private String email;
	private String post;
	private String company;
	private String address;
	private String firstpinyin;
	private int isDel;
	private Long uId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFirstpinyin() {
		return firstpinyin;
	}
	public void setFirstpinyin(String firstpinyin) {
		this.firstpinyin = firstpinyin;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
}
