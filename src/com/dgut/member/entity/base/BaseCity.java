package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Set;

import com.dgut.member.entity.MemberInfo;
import com.dgut.member.entity.Province;

public abstract class BaseCity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//城市编号
	private Integer id;
	//城市名
	private String name;
	//索引
	private Province province;
	
	private java.lang.Integer priority;
	
	private Set<MemberInfo> memberInfoSet;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Province getProvince() {
		return province;
	}
	
	public void setProvince(Province province) {
		this.province = province;
	}
	
	
	
	
	public java.lang.Integer getPriority() {
		return priority;
	}
	public void setPriority(java.lang.Integer priority) {
		this.priority = priority;
	}
	public Set<MemberInfo> getMemberInfoSet() {
		return memberInfoSet;
	}
	public void setMemberInfoSet(Set<MemberInfo> memberInfoSet) {
		this.memberInfoSet = memberInfoSet;
	}

	
}
