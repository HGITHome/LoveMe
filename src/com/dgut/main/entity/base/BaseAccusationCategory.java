package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.Set;

import com.dgut.main.entity.AccusationType;

public class BaseAccusationCategory implements Serializable {

	/**
	 * 举报父类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String categoryName;
	
	private Integer priority;

	private Set<AccusationType> acTypes;
	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Set<AccusationType> getAcTypes() {
		return acTypes;
	}

	public void setAcTypes(Set<AccusationType> acTypes) {
		this.acTypes = acTypes;
	}
	
	

}
