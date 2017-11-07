package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Set;

import com.dgut.member.entity.City;

public abstract class BaseProvince implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseProvince() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.Integer priority;
	private Set<City> citys ;
	public BaseProvince(Integer id, String name, Integer priority) {
		super();
		this.id = id;
		this.name = name;
		this.priority = priority;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getPriority() {
		return priority;
	}

	public void setPriority(java.lang.Integer priority) {
		this.priority = priority;
	}

	public Set<City> getCitys() {
		return citys;
	}

	public void setCitys(Set<City> citys) {
		this.citys = citys;
	}

	
	
}
